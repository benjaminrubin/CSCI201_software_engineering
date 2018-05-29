package benjamfr_CS201L_Assignment4;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Scanner;

public class ChatClient extends Thread {

	// private BufferedReader br;
	// private PrintWriter pw;

	private Scanner input;
	private PrintStream ps;
	private BufferedReader br;
	private Socket s;

	public ChatClient() {
		while (true) {
			// // quick
			// String localhost = "localhost";
			// int port = 6788;
			// // quickend

			// first establish a connection
			input = new Scanner(System.in);
			System.out.println("Please enter an IP address:");
			String hostname = input.nextLine();
			System.out.println("Please enter a port:");
			int port = Integer.parseInt(input.nextLine());

			// Check for invalid port
			if (port < 1024) {
				System.out.println("Invalid port! Port must be higher than 1024");
				continue;
			}
			// Check if connection worked
			Socket s = null;
			try {
				s = new Socket(hostname, port);
				ps = new PrintStream(s.getOutputStream());
				br = new BufferedReader(new InputStreamReader(s.getInputStream()));
			} catch (SocketException ioe) {
				System.out.println("Unable to connect!");
				// System.out.println(ioe.getMessage());
				continue;
			} catch (IllegalArgumentException iae) {
				System.out.println("Invalid port/host!");
			} catch (IOException e) {
				System.out.println("Unable to connect!");
				continue;
			}
			break;
		}
		System.out.println("You're connected!");
		this.start();
		// reading input from the client
		while (true) {
			String line = input.nextLine();
			ps.println(line);
			ps.flush();
		}
	}

	public void receiveMessage() {
		try {
			System.out.println(this.br.readLine());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void close() {
		try {
			this.s.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// RUN METHOD
	public void run() {
		try {
			while (true) {
				String line = br.readLine();
				System.out.println(line);
			}
		} catch (IOException ioe) {
			System.out.println("ioe in ChatClient.run(): " + ioe.getMessage());
		}
	}

	// MAIN METHOD
	public static void main(String[] args) {
		ChatClient cc = new ChatClient();
		cc.close();
	}

}
