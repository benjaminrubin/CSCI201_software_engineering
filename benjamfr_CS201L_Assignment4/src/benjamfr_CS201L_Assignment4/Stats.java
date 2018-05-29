
package benjamfr_CS201L_Assignment4;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Stats {

    @SerializedName("health")
    @Expose
    public int health;
    @SerializedName("attack")
    @Expose
    public int attack;
	@SerializedName("defense")
    @Expose
    public int defense;
    @SerializedName("speed")
    @Expose
    public int speed;
    
    public Stats(int health, int attack, int defense, int speed) {
		super();
		this.health = health;
		this.attack = attack;
		this.defense = defense;
		this.speed = speed;
	}
    
    public Stats(Stats copyme) {
 		super();
 		this.health = copyme.getHealth();
 		this.attack = copyme.getAttack();
 		this.defense = copyme.getDefense();
 		this.speed = copyme.getSpeed();
 	}

	public int getHealth() {
		return health;
	}

	public void setHealth(int health) {
		this.health = health;
	}

	public int getAttack() {
		return attack;
	}

	public int getDefense() {
		return defense;
	}

	public int getSpeed() {
		return speed;
	}
}
