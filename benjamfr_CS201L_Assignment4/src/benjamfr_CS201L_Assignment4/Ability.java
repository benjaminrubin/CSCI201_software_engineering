
package benjamfr_CS201L_Assignment4;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Ability {

	@SerializedName("name")
	@Expose
	public String name;
	@SerializedName("type")
	@Expose
	public String type;
	@SerializedName("damage")
	@Expose
	public int damage;

	public Ability(Ability copyme) {
		super();
		this.name = copyme.getName();
		this.type = copyme.getType();
		this.damage = copyme.getDamage();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public int getDamage() {
		return damage;
	}

	public void setDamage(int damage) {
		this.damage = damage;
	}

}
