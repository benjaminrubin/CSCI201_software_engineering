
import java.util.ArrayList;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Menu {

    @SerializedName("deep fryer")
    @Expose
    public ArrayList<String> deepFryer = null;
    @SerializedName("grill")
    @Expose
    public ArrayList<String> grill = null;
    @SerializedName("milkshake maker")
    @Expose
    public ArrayList<String> milkshakeMaker = null;
    @SerializedName("drink machine")
    @Expose
    public ArrayList<String> drinkMachine = null;
	
    
    
    public ArrayList<String> getDeepFryer() {
		return deepFryer;
	}
	
	public ArrayList<String> getGrill() {
		return grill;
	}
	
	public ArrayList<String> getMilkshakeMaker() {
		return milkshakeMaker;
	}
	
	public ArrayList<String> getDrinkMachine() {
		return drinkMachine;
	}
	
}
