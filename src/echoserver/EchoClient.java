package echoserver;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class EchoClient {
	public static final int PORT_NUMBER = 6013;

	public static void main(String[] args) throws IOException {
		EchoClient client = new EchoClient();
		client.start();
	}

	private void start() throws IOException {
		Socket socket = new Socket("localhost", PORT_NUMBER);
		InputStream socketInputStream = socket.getInputStream();
		OutputStream socketOutputStream = socket.getOutputStream();

		// Put your code here.
	}

	/*
	 * Both Clients have much in common
	 * We create a class Client that does the majority of the logic
	 * Using specicialized methods as need be.
	 */
	private class Client implements Runnable {
		InputStream input;
		OutputStream output;
		Socket socket;

		public void run(){
			try {
				int inputInt;
				while((inputInt = input.read()) != -1) {
					output.write(inputInt);
				}
				output.flush();
			} catch(IOException ioe) {
				System.out.println("Threw IOE: ");
				System.out.println(ioe);
			}

		}
	}

	/*
	 * Writing to the server uses 
	 * System.in as its input
	 * OutputStream as its output
	 */
	public class ServerWriter extends Client {
		public ServerWriter(Socket client) {
			try {
				socket = client;
				input = System.in;
				output = client.getOutputStream();
			} catch(IOException ioe) {
				System.out.println("Threw IOE: ");
				System.out.println(ioe);
			}

		}
		
		@Override
		public void run() {
			super.run();
			try {
				socket.shutdownOutput();
			}
			catch(IOException ioe) {
				System.out.println("Threw IOE: ");
				System.out.println(ioe);
			}
		}
	}

	/*
	 * Grabbing the server's output uses
	 * InputStream as its input
	 * System.out as its output
	 */
	public class ServerReader extends Client {
		public ServerReader(Socket client) {
			try {
				socket = client;
				input = client.getInputStream();
				output = System.out;
			} catch(IOException ioe) {
				System.out.println("Threw IOE: ");
				System.out.println(ioe);
			}

		}
		
		@Override
		public void run() {
			super.run();
			try {
				socket.close();
			}
			catch(IOException ioe) {
				System.out.println("Threw IOE: ");
				System.out.println(ioe);
			}
		}
	}
}