package echoserver;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class EchoServer {
	
	// REPLACE WITH PORT PROVIDED BY THE INSTRUCTOR
	public static final int PORT_NUMBER = 0; 
	public static void main(String[] args) throws IOException, InterruptedException {
		EchoServer server = new EchoServer();
		server.start();
	}

	private void start() throws IOException, InterruptedException {
		ServerSocket serverSocket = new ServerSocket(PORT_NUMBER);
		while (true) {
			//Constructs two connections			
			Socket firstClient = serverSocket.accept();
			ServerConnection firstConnection = new ServerConnection(firstClient);
			Socket secondClient = serverSocket.accept();
			ServerConnection secondConnection = new ServerConnection(secondClient);

			//Creates threads for connections
			Thread firstThread = new Thread(firstConnection);
			Thread secondThread = new Thread(secondConnection);

			//Starts threads
			firstThread.start();
			secondThread.start();
			firstThread.join();
			secondThread.join();
		}
	}

	public class ServerConnection implements Runnable {
		Socket connection;
		InputStream input;
		OutputStream output;
		public ServerConnection(Socket client) {
			connection = client;
        	System.out.println("Connected:");
			try {
	       		// Construct input stream for info sent to server
				input = client.getInputStream();
				output = client.getOutputStream();		
			} catch(IOException ioe) {
				System.out.println("Threw IOE: ");
				System.out.println(ioe);
			}
		}

		@Override
		public void run() {
			try {
				// Grab information from the stream and send it back
				int inputInt;
				while((inputInt = input.read()) != -1) {
				 output.write(inputInt);
				 System.out.write(inputInt);
				} 
				output.flush();
		   
				// Close the client socket and reader since we're done
				connection.close();				
			} catch(IOException ioe) {
				System.out.println("Threw IOE: ");
				System.out.println(ioe);
			}	   
			
		}
	}
}