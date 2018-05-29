
package benjamfr_CS201L_Assignment4;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Brawler {

	@SerializedName("name")
	@Expose
	public String name;
	@SerializedName("type")
	@Expose
	public String type;
	@SerializedName("stats")
	@Expose
	public Stats stats;
	@SerializedName("abilities")
	@Expose
	public ArrayList<Ability> abilities = null;

	public Brawler(Brawler copyme) {
		super();
		this.name = copyme.getName();
		this.type = copyme.getType();
		this.stats = new Stats(copyme.getStats());
		this.abilities = new ArrayList<Ability>();
		
		for(int i = 0; i < copyme.getAbilities().size(); i++) {
			this.abilities.add(copyme.abilities.get(i));
		}
		
	}

	// Getters and Setters
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Stats getStats() {
		return stats;
	}

	public void setStats(Stats stats) {
		this.stats = stats;
	}

	public ArrayList<Ability> getAbilities() {
		return abilities;
	}

	public int getNumAbilities() {
		return this.getAbilities().size();
	}

	public int getAbilityPower(int index) {
		return this.getAbilities().get(index).getDamage();
	}

	public String getAbilityType(int index) {
		return this.getAbilities().get(index).getType();
	}

	public String getAbilityName(int index) {
		return this.getAbilities().get(index).getName();
	}

	public int getAttackStat() {
		return this.getStats().getAttack();
	}

	public int getDefenseStat() {
		return this.getStats().getDefense();
	}

	public int getSpeed() {
		return this.getStats().getSpeed();
	}

	public void setHealth(int health) {
		this.getStats().setHealth(health);
	}
	
	public int getHealth() {
		return this.getStats().getHealth();
	}

}
