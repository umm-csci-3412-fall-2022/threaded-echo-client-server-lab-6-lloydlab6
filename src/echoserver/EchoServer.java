package echoserver;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class EchoServer {
	
	// REPLACE WITH PORT PROVIDED BY THE INSTRUCTOR
	public static final int PORT_NUMBER = 6013;
	public static int num_clients = 0; 
	public static void main(String[] args) throws IOException, InterruptedException {
		EchoServer server = new EchoServer();
		server.start();
	}

	private void start() throws IOException, InterruptedException {
		ServerSocket serverSocket = new ServerSocket(PORT_NUMBER);
		while (true) {
			//Constructs connections			
			Socket client = serverSocket.accept();
			num_clients++;
			System.out.println("Connected " + num_clients + ": ");
			ServerConnection connection = new ServerConnection(client);

			//Creates thread for new connection
			Thread thread = new Thread(connection);

			//Starts thread
			thread.start();
		}
	}

	public class ServerConnection implements Runnable {
		Socket connection;
		InputStream input;
		OutputStream output;
		public ServerConnection(Socket client) {
			connection = client;
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
		   
				// Close the socket and reader since we're done
				connection.close();				
			} catch(IOException ioe) {
				System.out.println("Threw IOE: ");
				System.out.println(ioe);
			}	   
			
		}
	}
}