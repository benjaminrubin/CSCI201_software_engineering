
import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Restaurant {

    @SerializedName("Menu")
    @Expose
    public Menu menu;
    @SerializedName("Customers")
    @Expose
    public ArrayList<Customer> customers = null;
	
    
    
    public Menu getMenu() {
		return menu;
	}
	
	public ArrayList<Customer> getCustomers() {
		return customers;
	}


}