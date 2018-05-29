package assignment1;

public class Name {
	private String fname;
	private String lname;

	// Default Constructor
	public Name() {
		// leave empty
	}

	// Constructor with Parameter Variables
	public Name(String fname, String lname) {
		this.fname = fname;
		this.lname = lname;
	}

	
	// Methods
	
	public void print() {
		System.out.print(lname + ", " + fname);
	}

	public String getFullName() {
		String fullname = (fname + " " + lname);
		return fullname;
	}
}