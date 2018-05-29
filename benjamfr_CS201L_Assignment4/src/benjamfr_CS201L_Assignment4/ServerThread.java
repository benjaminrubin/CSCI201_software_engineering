package benjamfr_CS201L_Assignment4;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.net.Socket;

public class ServerThread extends Thread {

	private PrintWriter pw;
	private BufferedReader br;

	// private PrintStream ps;
	private ChatRoom cr;
	public Socket s;

	public ServerThread(Socket s, ChatRoom cr) {
		try {
			this.s = s;
			this.cr = cr;
			// these are for strings
			pw = new PrintWriter(s.getOutputStream());
			br = new BufferedReader(new InputStreamReader(s.getInputStream()));
			this.start();
		} catch (IOException ioe) {
			System.out.println("ioe in ServerThread constructor: " + ioe.getMessage());
		}
	}

	// for sending text
	public void sendText(String text) {
		pw.println(text);
		pw.flush();
	}

	public String receiveText() {
		String text = "";
		try {
			text = this.br.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return text;
	}

	public void run() {
		int gameType;
		while (true) {
			// get the type of game, and pass it onward into one of the methods
			this.sendText("Please make your game choice:\n" + "1) Start Game \n" + "2) Join Game");
			String gameTypeBuff = "";
			try {
				gameTypeBuff = this.br.readLine();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			if (isInteger(gameTypeBuff)) {
				gameType = Integer.parseInt(gameTypeBuff);
				if (gameType != 1 && gameType != 2) {
					// do nothing
					this.pw.println("Invalid input.");
				} else {
					// we have the proper gametype
					break;
				}
			}
		}

		// single player
		if (gameType == 1) {
			// start a game
			String gameName = "";
			while (true) {
				sendText("What will you name your game?");
				try {
					gameName = this.br.readLine();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				// check if the game name exists
				if (this.cr.gameExists(gameName)) {
					sendText("This game already exists!");
					continue;
				} else {
					// game doesn't exist
					break;
				}
			}
			// ask for how many players
			String numPlayersBuff = "";
			int numPlayers;
			while (true) {
				sendText("How many players? \n" + "1 or 2?");
				try {
					numPlayersBuff = this.br.readLine();
					if (isInteger(numPlayersBuff)) {
						numPlayers = Integer.parseInt(numPlayersBuff);
						if (numPlayers != 1 && numPlayers != 2) {
							// do nothing
							sendText("Invalid input.");
						} else {
							// we have the right number of players
							break;
						}
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			// if 2 players
			if (numPlayers == 2) {
				// now wait for another player to come
				sendText("Waiting for players to connect...");
				// first create the player
				Player playerOne = new Player(this);
				// second create the game
				Game game = new Game(gameName, playerOne, 2, this.cr);
				// add the game to the list of games in the chatroom
				this.cr.addGame(game);

			}
			// if 1 player
			else {
				sendText("Starting game!");
				// generate a single player game
				// first create the player
				Player playerOne = new Player(this);
				// second create the game
				Game game = new Game(gameName, playerOne, 1, this.cr);
				game.makeFull();
				// add the game to the list of games in the chatroom
				this.cr.addGame(game);
			}
		}
		// player wants to join a game
		else {
			String gameName = "";
			while (true) {
				sendText("What game will you join?");
				try {
					gameName = this.br.readLine();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				// if the game doesn't exist
				if (!this.cr.gameExists(gameName)) {
					sendText("This game doesn't exist. Let's try again..");
					continue;
					// if the game exists but is full
				} else if (this.cr.gameIsFull(gameName) && !this.cr.gameIsMultiplayer(gameName)) {
					sendText("This game is full. Let's try again..");
					continue;
				}
				// the game exists and is not full
				else {
					break;
				}
			}
			sendText("Joining...");
			// create a player for this new guy
			Player playerTwo = new Player(this);
			this.cr.addPlayer(gameName, playerTwo);
		}
	}

	public static boolean isInteger(String input) {
		try {
			Integer.parseInt(input);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
}
