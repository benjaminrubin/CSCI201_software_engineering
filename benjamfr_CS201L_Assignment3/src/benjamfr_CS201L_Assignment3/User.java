
package benjamfr_CS201L_Assignment3;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class User {

    @SerializedName("username")
    @Expose
    public String username;
    @SerializedName("password")
    @Expose
    public String password;
    @SerializedName("imageURL")
    @Expose
    public String imageURL;
    @SerializedName("followers")
    @Expose
    public List<String> followers = null;
    @SerializedName("following")
    @Expose
    public List<String> following = null;
    @SerializedName("library")
    @Expose
    public Library library;

    //basic constructor
	public User() {
		super();
	}
	
	public User(String username) {
		super();
		this.username = username;
	}
	public User(String username, String password, String imageURL) {
		super();
		this.username = username;
		this.password = password;
		this.imageURL = imageURL;
	}
    
    
    public String getUsername() {
    		return username;
    }

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getImageURL() {
		return imageURL;
	}

	public void setImageURL(String imageURL) {
		this.imageURL = imageURL;
	}

	public List<String> getFollowers() {
		return followers;
	}

	public void setFollowers(List<String> followers) {
		this.followers = followers;
	}

	public List<String> getFollowing() {
		return following;
	}

	public void setFollowing(List<String> following) {
		this.following = following;
	}

	public Library getLibrary() {
		return library;
	}

	public void setLibrary(Library library) {
		this.library = library;
	}


	public void setUsername(String username) {
		this.username = username;
	}
    
}
