package benjamfr_CS201L_Assignment3;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
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





@WebServlet("/updateUserList")
public class updateUserList extends HttpServlet {
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
		
		// 1. get received JSON data from request
        BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream()));
        String buff = "";
        String line = br.readLine();
		
		while (line != null) {
			buff += line;
			line = br.readLine();
		}
		
		//to print out the JSON string
		System.out.println("This is what we took from the web:");
		System.out.println(buff);
        
		//we have the file name
//		String filename = getServletContext().getRealPath("/UserList.json");
		String filename = "/Users/benjaminrubin/Google Drive/USC 5th Year/2nd Semester/CS201 - Principles of Software Development/hw_benjamfr/benjamfr_CS201L_Assignment3A/WebContent/UserList.json";
		System.out.println("real path is: " + filename);
		
		//get the session object
		HttpSession session = request.getSession();
		
		// get the userlist object from the session
//		UserList userlist = (UserList) session.getAttribute("userlist");
		UserList userlist = null;
		
		//update this new userlist
		try (FileWriter writer = new FileWriter(filename)) {
			Gson gson = new GsonBuilder().create();
			
			Gson gprint = new GsonBuilder().setPrettyPrinting().create();
			JsonParser jp = new JsonParser();
			JsonElement je = jp.parse(buff);
			String prettyJsonString = gson.toJson(je);
			System.out.println(prettyJsonString);
			
			
			
			userlist = gson.fromJson(buff, UserList.class);
			gson.toJson(userlist, writer);
		} catch (IOException ioe) {
			System.out.println("There's a problem with the output file.");
		}
//		session.setAttribute("userlist", userlist);
		
	System.out.println("We updated the UserList");
		

	}
}
