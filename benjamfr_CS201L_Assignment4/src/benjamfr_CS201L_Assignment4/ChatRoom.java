package benjamfr_CS201L_Assignment4;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Vector;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

public class ChatRoom {

	private Vector<ServerThread> serverThreads;
	private HashSet<String> gameNames;
	private ArrayList<Game> games;
	public Brawlers brawlers;

	public ChatRoom() {
		games = new ArrayList<Game>();
		serverThreads = new Vector<ServerThread>();
		gameNames = new HashSet<String>();

		int port;
		FileReader fr;
		BufferedReader br;
		String buff = "";
		String filename = "";
		Scanner input = new Scanner(System.in);

		while (true) {
			try {
				System.out.println("Please enter a valid file:");
				filename = input.nextLine();
//				filename = "a4.json";

				fr = new FileReader(filename);
				br = new BufferedReader(fr);
				String line = br.readLine();

				while (line != null) {
					buff += line;
					line = br.readLine();
				}
				if (buff.isEmpty()) {
					System.out.println("\nFile is empty, please try another one.\n");
					continue;
				}

				// closing the reader and buffer
				// the while loop will break if input was VALID
				fr.close();
				br.close();
				
				break;
			} catch (FileNotFoundException fnfe) {
				System.out.println("That file could not be found");
			} catch (IOException ioe) {
				System.out.println("That file is not a well-formed JSON file, please try again.");
			}

		}

		// Creating a new Gson parser to parse buff into Roster object
		Gson gson = new Gson();
		try {
			brawlers = gson.fromJson(buff, Brawlers.class);
		} catch (JsonSyntaxException jsse) {
			System.out.println(jsse.getMessage());
		}

		while (true) {
			try {
				System.out.println("Please enter a valid port:");
				port = Integer.parseInt(input.nextLine());
				// Check for invalid port
				if (port < 1024) {
					System.out.println("Invalid port! Port must be higher than 1024");
					continue;
				}

				System.out.println("Binding to port " + port);
				ServerSocket ss = new ServerSocket(port);
				System.out.println("Success!");
				input.close();
				while (true) {
					// accept the connections
					Socket s = ss.accept();
					// alert for newly made connection
					System.out.println("Connection from new player: " + s.getInetAddress());
					// initialize a thread for that connection
					ServerThread st = new ServerThread(s, this);
					System.out.println("initialized");
					serverThreads.add(st);
				}
			} catch (IOException ioe) {
				System.out.println("ioe in ChatRoom constructor: " + ioe.getMessage());
			}
		}
	}

	// add a game name to the list
	public void addGame(Game game) {
		// add the game's name to the game list
		this.gameNames.add(game.getName().toLowerCase());
		this.games.add(game);
		if (!game.getMultiplayer()) {
			game.startSinglePlayer();
		} else {
			while (true) {
				if (game.getIsfull()) {
					game.startMultiPlayer();
					break;
				} else {
					continue;
				}
			}
		}
		System.out.println("New game created!");

	}

	// add a player to a game
	public void addPlayer(String gameName, Player playerTwo) {
		// add the game's name to the game list
		System.out.println("we're adding a player");
		for (int i = 0; i < this.games.size(); i++) {
			System.out.println("1" + this.games.get(i).getName());
			System.out.println("2" + gameName);
			if (this.games.get(i).getName().equals(gameName)) {
				this.games.get(i).addPlayer(playerTwo);
				this.games.get(i).startMultiPlayer();
			}
		}
		System.out.println("New player added to '" + gameName + "'");
	}

	// check to see if a game name already exists
	public Boolean gameExists(String game) {
		if (gameNames.contains(game.toLowerCase())) {
			return true;
		}
		return false;
	}

	public Boolean gameIsMultiplayer(String game) {
		for (int i = 0; i < this.games.size(); i++) {
			if (this.games.get(i).getName() == game) {
				return this.games.get(i).getMultiplayer();
			}
		}
		return false;
	}

	public Boolean gameIsFull(String game) {
		for (int i = 0; i < this.games.size(); i++) {
			if (this.games.get(i).getName() == game) {
				return this.games.get(i).getIsfull();
			}
		}
		return false;
	}

	public static void main(String[] args) {
		ChatRoom cr = new ChatRoom();
	}
}
