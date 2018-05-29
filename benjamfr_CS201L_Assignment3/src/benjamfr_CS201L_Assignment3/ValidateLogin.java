package benjamfr_CS201L_Assignment3;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import com.google.gson.stream.JsonReader;




@WebServlet("/ValidateLogin")
public class ValidateLogin extends HttpServlet {
	private static final long serialVersionUID = 1L;


	// This function writes out to the JSON file that was initially passed through
	public void update(UserList userlist, String filename) {

		try (FileWriter writer = new FileWriter(filename)) {
			Gson gson = new GsonBuilder().create();
			gson.toJson(userlist, writer);
		} catch (IOException ioe) {
			System.out.println("There's a problem with the output file.");
		}
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	
		String username = request.getParameter("username");
		username.trim();
		String password = request.getParameter("password");
		String image = request.getParameter("imageurl");
		
		
		//the page we will be forwarding to
		String pageToForward = "";
		String buff = "";
		FileReader fr;
		BufferedReader br;
		
		//this gets the file path of the json that is on the server which is interacted with constantly
//		String filename = getServletContext().getRealPath("/UserList.json");
		String filename = "/Users/benjaminrubin/Google Drive/USC 5th Year/2nd Semester/CS201 - Principles of Software Development/hw_benjamfr/benjamfr_CS201L_Assignment3A/WebContent/UserList.json";
	
		HttpSession session = request.getSession(false);
		session.invalidate();
		session = request.getSession(true);
		
		// reading all the data into a string
		while (true) {
			try {
				fr = new FileReader(filename);
				br = new BufferedReader(fr);
				String line = br.readLine();
				
				while (line != null) {
					buff += line;
					line = br.readLine();
				}
				if (buff.isEmpty()) {
					System.out.println("\nFile is empty, please try another one.\n");
					continue;
				}
				// closing the reader and buffer
				// the while loop will break if input was VALID
				fr.close();
				br.close();
				break;
			} catch (FileNotFoundException fnfe) {
				System.out.println("That file could not be found");
				break;
			} catch (IOException ioe) {
				System.out.println("That file is not a well-formed JSON file, please try again.");
			}
		}

		// Creating a new Gson parser to parse buff into Roster object
		Gson gson = new Gson();
		UserList userlist = null;
		try {
			Gson gprint = new GsonBuilder().setPrettyPrinting().create();
			JsonParser jp = new JsonParser();
			JsonElement je = jp.parse(buff);
//			String prettyJsonString = gson.toJson(je);
//			System.out.println(prettyJsonString);
			userlist = gson.fromJson(buff, UserList.class);
		} catch (JsonSyntaxException jsse) {
			System.out.println(jsse.getMessage());
		}
		
// Error handling
		
		//user is blank
		if (username.equals("")) {
			pageToForward = "/loginPage.jsp";
			session.setAttribute("userError", "Username can't be empty");
		}
		//username doesn't exist 
		else if (!userlist.exists(username)) {	
			pageToForward = "/loginPage.jsp";
			session.setAttribute("userError", "Username doesn't exist");
			session.setAttribute("olduser", username);
		}
		//username exists! time to check for the passwords
		else {
			//password is blank
			if (password == ""){
				pageToForward = "/loginPage.jsp";
				session.setAttribute("passError", "Entry cannot be empty");
				session.setAttribute("olduser", username);
			}
			//checking if the password is right
			else if (!userlist.verifyP(username,password)) {
				pageToForward = "/loginPage.jsp";
				session.setAttribute("passError", "Password doesn't match our records");
			}
			//password is right, let them in
			else {
				System.out.println("user logged in successfully");
				//this is just to see that this function works
				System.out.println("ttrojan's imageurl is: " + userlist.getImage("ttrojan"));
				session.setAttribute("username", username);
				session.setAttribute("userlist", userlist);
				pageToForward = "/searchPageUser.jsp";
			}
		}
		

		RequestDispatcher dispatch = getServletContext().getRequestDispatcher(pageToForward);
		dispatch.forward(request, response);

	}
}
