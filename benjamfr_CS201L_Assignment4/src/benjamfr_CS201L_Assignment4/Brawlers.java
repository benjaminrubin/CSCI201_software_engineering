
package benjamfr_CS201L_Assignment4;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Brawlers {

	@SerializedName("Brawlers")
	@Expose
	public ArrayList<Brawler> brawlers = null;

	public ArrayList<Brawler> getBrawlers() {
		return brawlers;
	}

	public int getBrawlersSize() {
		return this.brawlers.size();
	}

	public String printBrawlerList() {
		String brawlerList = "";
		for (int i = 0; i < brawlers.size(); i++) {
			brawlerList += (i + 1) + ": " + brawlers.get(i).getName() + "\n";
		}
		return brawlerList;
	}
}
