package benjamfr_CS201L_Assignment4;

import java.util.ArrayList;

public class Player {
	public ArrayList<Brawler> brawlers;
	private ServerThread st;

	public Player(ServerThread st) {
		super();
		this.st = st;
		brawlers = new ArrayList<Brawler>();
	}

	// constructor for Computer
	Player() {
		brawlers = new ArrayList<Brawler>();
	}

	public ServerThread getSt() {
		return st;
	}

	public void sendText(String text) {
		st.sendText(text);
	}

	public String receiveText() {
		return st.receiveText();
	}

	public void addBrawler(Brawler brawler) {
		this.brawlers.add(brawler);
	}

	public Boolean isComputer() {
		if (this.st == null) {
			return true;
		}
		return false;
	}

}
