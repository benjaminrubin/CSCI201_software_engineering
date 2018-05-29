
package benjamfr_CS201L_Assignment3;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;



public class UserList {

    @SerializedName("users")
    @Expose
    public List<User> users;


public void add(String username, String password, String imageURL) {
	User user = new User(username,password,imageURL);
	this.users.add(user);
	this.sort();
	System.out.println("New student added");
}

//This function determines whether a username exists in the string
    public Boolean exists(String username)
    {
    		for (int i = 0; i < users.size(); i++) {
//    			System.out.println(users.get(i).getUsername().toLowerCase() + " and " + username.toLowerCase());
    			if (	users.get(i).getUsername().equalsIgnoreCase(username)) {
    				return true;
    			}
    		}
    		return false;
    }
    
    public Boolean verifyP(String username,String password) {
    		this.sort();
    		System.out.println("CHecking the password " + password + " for user " + username);
    	       Comparator<User> c = new Comparator<User>()  {
	    	    	   public int compare(User first, User second) {
	    	    			if (first.getUsername().equals(second.getUsername())) {
	    	    				return first.getUsername().compareTo(second.getUsername());
	    	    			}
	    	    			else {
	    	    				return first.getUsername().toLowerCase().compareTo(second.getUsername().toLowerCase());
	    	    			}
	    	    	   }
    	       };
    		
    	       
    	      int index = Collections.binarySearch(users, new User(username), c);
    	      
    	      System.out.println("Password that was found: " + users.get(index).getPassword());
    	      
    	      if(users.get(index).getPassword().equals(password)) {
    	    	  	return true;
    	      }
    	      else {
    	    	  	return false;
    	      }	
    }
    

	public List<User> getUsers() {
		return users;
	}


	public void setUsers(List<User> users) {
		this.users = users;
	}
	
	
	public String getImage(String username) {
		for (int i = 0; i < users.size(); i++) {
//			System.out.println(users.get(i).getUsername().toLowerCase() + " and " + username.toLowerCase());
			if (	users.get(i).getUsername().equalsIgnoreCase(username)) {
				return users.get(i).getImageURL();
			}
		}
		return "user_icon.png";
	}
	
	
	
	// Sorting the Userlist
	public void sort() {
		
		Comparator<User> c = new Comparator<User>()  {
	    	   public int compare(User first, User second) {
	    			if (first.getUsername().equals(second.getUsername())) {
	    				return first.getUsername().compareTo(second.getUsername());
	    			}
	    			else {
	    				return first.getUsername().toLowerCase().compareTo(second.getUsername().toLowerCase());
	    			}
	    	   }
		};
		
		// Sort by username
			Collections.sort(users, c);
	}
	
}
