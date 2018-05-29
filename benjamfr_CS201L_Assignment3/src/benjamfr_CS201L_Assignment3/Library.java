
package benjamfr_CS201L_Assignment3;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Library {

    @SerializedName("read")
    @Expose
    public List<String> read = null;
    @SerializedName("favorite")
    @Expose
    public List<String> favorite = null;
	public List<String> getRead() {
		return read;
	}
	public void setRead(List<String> read) {
		this.read = read;
	}
	public List<String> getFavorite() {
		return favorite;
	}
	public void setFavorite(List<String> favorite) {
		this.favorite = favorite;
	}

}
