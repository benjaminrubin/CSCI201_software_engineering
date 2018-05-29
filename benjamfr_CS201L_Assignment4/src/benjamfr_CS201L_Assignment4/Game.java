package benjamfr_CS201L_Assignment4;

import java.io.IOException;
import java.util.Random;

public class Game {
	private String name;
	private Boolean multiplayer;
	private Boolean isfull;
	private Player playerOne;
	private Player playerTwo;
	private ChatRoom cr;

	// constructor for game
	public Game(String name, Player playerOne, int numPlayers, ChatRoom cr) {
		super();
		this.name = name;
		if (numPlayers == 1) {
			this.multiplayer = false;
		} else {
			this.multiplayer = true;
		}
		this.isfull = false;
		this.playerOne = playerOne;
		this.playerTwo = null;
		this.cr = cr;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Boolean getMultiplayer() {
		return multiplayer;
	}

	public void setMultiplayer(Boolean multiplayer) {
		this.multiplayer = multiplayer;
	}
	
	public void makeFull() {
		this.isfull = true;
	}

	public Boolean getIsfull() {
		return isfull;
	}

	public Player getPlayerOne() {
		return playerOne;
	}

	public Player getPlayerTwo() {
		return playerTwo;
	}

	public void addPlayer(Player playerTwo) {
		this.playerTwo = playerTwo;
		this.isfull = true;
	}

	public void sendToPlayerOne(String text) {
		this.playerOne.sendText(text);
	}

	public String receivePlayerOneMessage() {
		return this.playerOne.receiveText();
	}

	public void sendToPlayerTwo(String text) {
		this.playerTwo.sendText(text);
	}

	public String receivePlayerTwoMessage() {
		return this.playerTwo.receiveText();
	}

	public void brawlerChoice(Player player) {

		if (!player.isComputer()) {
			String choice = "";
			int[] brawlerIndex;
			while (true) {
				Boolean problem = false;
				player.sendText("Choose three brawlers: \n" + this.cr.brawlers.printBrawlerList());
				choice = player.receiveText();
				// extract the choice
				String[] numbers = choice.split("\\s*,\\s*");

				// make sure they chose 3 brawlers!
				if (numbers.length != 3) {
					player.sendText("The size of array is: " + numbers.length);
					player.sendText("Invalid!");
					continue;
				}

				// convert String into int
				brawlerIndex = new int[numbers.length];

				for (int i = 0; i < numbers.length; i++) {
					brawlerIndex[i] = Integer.parseInt(numbers[i]);
					if (brawlerIndex[i] > this.cr.brawlers.getBrawlers().size() || brawlerIndex[i] <= 0) {
						player.sendText("Invalid!");
						problem = true;
						break;
					}
				}
				if (problem) {
					continue;
				} else {
					break;
				}
			}
			for (int i = 0; i < brawlerIndex.length; i++) {
				player.addBrawler(new Brawler(this.cr.brawlers.getBrawlers().get(brawlerIndex[i] - 1)));
				// make sure we got the brawlers.
				// sendToPlayerOne("Brawler " + (i+1) + ": " +
				// this.playerOne.brawlers.get(i).getName());
			}
			player.sendText("Excellent!");
		}

		// the player is a computer
		else {
			// Generate Brawlers for the Computer
			Random rand = new Random();
			// Generate the player for the computer
			for (int i = 0; i < 3; i++) {
				int n = rand.nextInt(this.cr.brawlers.getBrawlersSize());
				// sendToPlayerOne("random number is: " + n);
				this.playerTwo.addBrawler(new Brawler(this.cr.brawlers.getBrawlers().get(n)));
				// sendToPlayerOne("Computer Brawler " + (i+1) + ": " +
				// this.playerTwo.brawlers.get(i).getName());
			}
		}

	}

	public int abilityChoice(Player player) {

		if (!player.isComputer()) {
			int ability = -1;
			String abilityBuff = "";

			// check that the input for ability is understandable
			while (true) {
				abilityBuff = player.receiveText();
				if (isInteger(abilityBuff)) {
					ability = Integer.parseInt(abilityBuff);
					if (ability <= 0 || ability > player.brawlers.get(0).getNumAbilities()) {
						// do nothing
						sendToPlayerOne("Invalid input.");
						continue;
					} else {
						// we have the proper ability number
						return ability;
					}
				}
			}
		}
		// player is a computer
		else {
			Random rand = new Random();
			return (rand.nextInt(player.brawlers.get(0).getNumAbilities()) + 1);
		}
	}

	public void runGame() {

		// By default, PlayerOne is guaranteed to be a user!
		// PlayerTwo can either be the computer, or another user

		Boolean p1BrawlerDead = false;
		Boolean p2BrawlerDead = false;

		while (true) {
			// if any of the players don't have any brawlers
			if (this.playerOne.brawlers.size() == 0 || this.playerTwo.brawlers.size() == 0) {
				if (this.playerOne.brawlers.size() == 0) {
					sendToPlayerOne("You are out of brawlers!\n\nYou Lose!");
					if (!this.playerTwo.isComputer()) {
						sendToPlayerTwo("Your opponent is out of brawlers!\n\nYou Win!");
					}
				} else {
					sendToPlayerOne("Your opponent is out of brawlers!\n\nYou Win!");
					if (!this.playerTwo.isComputer()) {
						sendToPlayerTwo("You are out of brawlers!\n\nYou Lose!");
					}
				}
				break;
			}

			// if one of the brawlers are dead
			if (p1BrawlerDead || p2BrawlerDead) {
				// if playerOne's brawler is dead
				if (p1BrawlerDead) {
					sendToPlayerOne("You sent out " + this.playerOne.brawlers.get(0).getName() + "! (Health: "
							+ this.playerOne.brawlers.get(0).getHealth() + ")");
					if (!playerTwo.isComputer()) {
						sendToPlayerTwo("Your opponent sent out " + this.playerTwo.brawlers.get(0).getName()
								+ " (Health: " + this.playerTwo.brawlers.get(0).getHealth() + ")!\n");
					}
					p1BrawlerDead = false;
				}
				// if playerTwo's brawler is dead
				else if (p2BrawlerDead) {
					sendToPlayerOne("Your opponent sent out " + this.playerTwo.brawlers.get(0).getName() + " (Health: "
							+ this.playerTwo.brawlers.get(0).getHealth() + ")!\n");
					if (!playerTwo.isComputer()) {
						sendToPlayerTwo("You sent out " + this.playerTwo.brawlers.get(0).getName() + "! (Health: "
								+ this.playerTwo.brawlers.get(0).getHealth() + ")");
					}
					p2BrawlerDead = false;
				}
			}
			// no brawlers are dead, both players send out their brawlers
			else {
				sendToPlayerOne("You send " + this.playerOne.brawlers.get(0).getName() + "! (Health: "
						+ this.playerOne.brawlers.get(0).getHealth() + ")");
				sendToPlayerOne("Your opponent plays " + this.playerTwo.brawlers.get(0).getName() + " (Health: "
						+ this.playerTwo.brawlers.get(0).getHealth() + ")!\n");

				if (!playerTwo.isComputer()) {
					sendToPlayerTwo("You send " + this.playerTwo.brawlers.get(0).getName() + "! (Health: "
							+ this.playerTwo.brawlers.get(0).getHealth() + ")!\n");
					sendToPlayerTwo("Your opponent sent out " + this.playerOne.brawlers.get(0).getName() + " (Health: "
							+ this.playerOne.brawlers.get(0).getHealth() + ")!\n");
				}
			}

			// UPDATE THIS FOR MULTIPLAYER
			while (!p1BrawlerDead && !p2BrawlerDead) {
				sendToPlayerOne("Choose a move:");
				for (int i = 0; i < this.playerOne.brawlers.get(0).getNumAbilities(); i++) {
					sendToPlayerOne((i + 1) + ") " + this.playerOne.brawlers.get(0).getAbilityName(i) + ", "
							+ this.playerOne.brawlers.get(0).getAbilityType(i) + ", "
							+ this.playerOne.brawlers.get(0).getAbilityPower(i));
				}
				if (!this.playerTwo.isComputer()) {
					sendToPlayerTwo("Choose a move:");
					for (int i = 0; i < this.playerTwo.brawlers.get(0).getNumAbilities(); i++) {
						sendToPlayerTwo((i + 1) + ") " + this.playerTwo.brawlers.get(0).getAbilityName(i) + ", "
								+ this.playerTwo.brawlers.get(0).getAbilityType(i) + ", "
								+ this.playerTwo.brawlers.get(0).getAbilityPower(i));
					}
				}

				// check that the input for ability is understandable from each player

				int p1Ability = abilityChoice(this.playerOne) - 1;
				int p2Ability = abilityChoice(this.playerTwo) - 1;

				// if playerOne's brawler is faster than the computer's, they go first
				if (this.playerOne.brawlers.get(0).getSpeed() >= this.playerTwo.brawlers.get(0).getSpeed()) {
					applyDamage(this.playerOne.brawlers.get(0), this.playerTwo.brawlers.get(0), p1Ability,
							this.playerOne, this.playerTwo);
					// check for any deaths
					if (this.playerTwo.brawlers.get(0).getHealth() <= 0) {
						this.playerTwo.brawlers.remove(0);
						p2BrawlerDead = true;

					}
					// defense is now offense
					else {
						applyDamage(this.playerTwo.brawlers.get(0), this.playerOne.brawlers.get(0), p2Ability,
								this.playerTwo, this.playerOne);
						if (this.playerOne.brawlers.get(0).getHealth() <= 0) {
							this.playerOne.brawlers.remove(0);
							p1BrawlerDead = true;
						}
					}
				}
				// the computer's brawler is faster
				else {
					applyDamage(this.playerTwo.brawlers.get(0), this.playerOne.brawlers.get(0), p2Ability,
							this.playerTwo, this.playerOne);
					// check for any deaths
					if (this.playerOne.brawlers.get(0).getHealth() <= 0) {
						this.playerOne.brawlers.remove(0);
						p1BrawlerDead = true;
					}
					// defense is now offense
					else {
						applyDamage(this.playerOne.brawlers.get(0), this.playerTwo.brawlers.get(0), p1Ability,
								this.playerOne, this.playerTwo);
						if (this.playerTwo.brawlers.get(0).getHealth() <= 0) {
							this.playerTwo.brawlers.remove(0);
							p2BrawlerDead = true;
						}
					}
				}
			}
		} // end of while true loop for game
		
		try {
			this.playerOne.getSt().s.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(!this.playerTwo.isComputer()) {
			try {
				this.playerOne.getSt().s.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public void applyDamage(Brawler offense, Brawler defense, int ability, Player pOffense, Player pDefense) {
		double multiplier = 1;

		if (!pOffense.isComputer()) {
			pOffense.sendText("Your " + offense.getName() + " used " + offense.getAbilityName(ability) + "!");
		}
		if (!pDefense.isComputer()) {
			pDefense.sendText("Opponent's " + offense.getName() + " used " + offense.getAbilityName(ability) + "!");
		}

		// if offense is water type
		if (offense.getType().equalsIgnoreCase("water")) {
			if (defense.getType().equalsIgnoreCase("fire")) {
				multiplier = 2;
			} else if (defense.getType().equalsIgnoreCase("lightning")) {
				multiplier = 0.5;
			}
		}

		// if offense is fire type
		if (offense.getType().equalsIgnoreCase("fire")) {
			if (defense.getType().equalsIgnoreCase("air")) {
				multiplier = 2;
			} else if (defense.getType().equalsIgnoreCase("water")) {
				multiplier = 0.5;
			}
		}

		// if offense is air type
		if (offense.getType().equalsIgnoreCase("air")) {
			if (defense.getType().equalsIgnoreCase("earth")) {
				multiplier = 2;
			} else if (defense.getType().equalsIgnoreCase("fire")) {
				multiplier = 0.5;
			}
		}

		// if offense is earth type
		if (offense.getType().equalsIgnoreCase("earth")) {
			if (defense.getType().equalsIgnoreCase("lightning")) {
				multiplier = 2;
			} else if (defense.getType().equalsIgnoreCase("air")) {
				multiplier = 0.5;
			}
		}

		// if offense is lightning type
		if (offense.getType().equalsIgnoreCase("lightning")) {
			if (defense.getType().equalsIgnoreCase("water")) {
				multiplier = 2;
			} else if (defense.getType().equalsIgnoreCase("earth")) {
				multiplier = 0.5;
			}
		}

		if (multiplier > 1) {
			if (!pOffense.isComputer()) {
				pOffense.sendText("It was super effective!");
			}
			if (!pDefense.isComputer()) {
				pDefense.sendText("It was super effective!");
			}
		} else if (multiplier < 1) {
			if (!pOffense.isComputer()) {
				pOffense.sendText("It was not very effective!");
			}
			if (!pDefense.isComputer()) {
				pDefense.sendText("It was not very effective!");
			}
		}

		// now that we have the multiplier, time to calculate the damage
		double damage = Math.floor(
				(((double) offense.getAttackStat() * (double) ((double) offense.getAbilities().get(ability).getDamage()
						/ (double) defense.getDefenseStat()) / 5) * (multiplier)));
		// pOffense.sendText("damage is " + String.valueOf(damage));

		defense.setHealth(defense.getHealth() - (int) damage);

		if (!pOffense.isComputer()) {
			pOffense.sendText("It did " + (int) damage + " damage!\n");
		}
		if (!pDefense.isComputer()) {
			pDefense.sendText("It did " + (int) damage + " damage!\n");
		}

		// if a brawler died
		if (defense.getHealth() <= 0) {
			if (!pOffense.isComputer()) {
				pOffense.sendText("Opponent's " + defense.getName() + " was defeated!");
			}
			if (!pDefense.isComputer()) {
				pDefense.sendText("Your " + defense.getName() + " was defeated!");
			}
		} else {
			if (!pOffense.isComputer()) {
				pOffense.sendText("Opponent's " + defense.getName() + " has " + defense.getHealth() + " health\n");
			}
			if (!pDefense.isComputer()) {
				pDefense.sendText("Your " + defense.getName() + " has " + defense.getHealth() + " health\n");
			}
		}
	}

	// Starting the games
	public void startSinglePlayer() {
		// Step 1: generate a player for the computer
		this.addPlayer(new Player());
		
//		this.playerTwo = new Player();

		// Part 1: Get choice of brawlers from the user
		brawlerChoice(this.playerOne);
		brawlerChoice(this.playerTwo);

		// Step 2: Start the game
		runGame();
		// if reached here, the game has ended
	}

	public void startMultiPlayer() {
		sendToPlayerOne("Welcome player one;");
		sendToPlayerTwo("Welcome player two;");

		// Ask for the brawlers from each player
		brawlerChoice(playerOne);
		brawlerChoice(playerTwo);
		runGame();

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
