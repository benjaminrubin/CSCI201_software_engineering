import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

public class PreparingOrders implements Runnable {

	private Restaurant restaurant;
	private ArrayList<Customer> customers;
	private Lock takeOrderLock;
	private Condition firstInLineCondition;
	public Menu menu;
	public DeepFryer df;
	public DrinkMachine dm;
	public Grill g;
	public MilkshakeMaker mm;
	public ArrayList<String> allItemsOnMenu;
	public Timetable TTT;

	// Constructor
	public PreparingOrders(Restaurant restaurant) {
		this.TTT = new Timetable();
		this.restaurant = restaurant;
		this.customers = restaurant.getCustomers();
		this.menu = restaurant.getMenu();
		this.takeOrderLock = new ReentrantLock();
		this.df = new DeepFryer();
		this.g = new Grill();
		this.mm = new MilkshakeMaker();
		this.dm = new DrinkMachine();

		this.allItemsOnMenu = new ArrayList<String>();
		for (int i = 0; i < this.menu.getDeepFryer().size(); i++) {
			this.allItemsOnMenu.add(this.menu.getDeepFryer().get(i));
		}
		for (int i = 0; i < this.menu.getGrill().size(); i++) {
			this.allItemsOnMenu.add(this.menu.getGrill().get(i));
		}
		for (int i = 0; i < this.menu.getMilkshakeMaker().size(); i++) {
			this.allItemsOnMenu.add(this.menu.getMilkshakeMaker().get(i));
		}
		for (int i = 0; i < this.menu.getDrinkMachine().size(); i++) {
			this.allItemsOnMenu.add(this.menu.getDrinkMachine().get(i));
		}
		this.run();
	}

	// ====================== Methods ========================
	public Boolean allOrdersPrepared() {
		for (int i = 0; i < this.customers.size(); i++) {
			if (!this.customers.get(i).getCustomerServed()) {
				// System.out.println("customer " + this.customers.get(i).getCustomerNumber() +
				// " is still waiting for their dishes");
				return false;
			}
		}
		return true;
	}

	public void takeMyOrder() {
		ExecutorService executors = Executors.newCachedThreadPool();
		for (int i = 0; i < customers.size(); i++) {
			try {
				takeOrderLock.lock();
				executors.execute(customers.get(i));

				Thread.sleep(1500);
				customers.get(i).TT.setStartTime();
				if (i == 0) {
					this.TTT.setStartTime();
				}
				System.out.println(customers.get(i).TT.startTime + " Starting order " + (i + 1) + "!");
			} catch (InterruptedException e) {
				e.printStackTrace();
			} finally {
				takeOrderLock.unlock();
			}
		}
		executors.shutdown();

		// Keep on looping while waiting for orders to be prepared
		while (true) {
			if (allOrdersPrepared()) {
				// Util.printMessage("All orders complete!");
				this.TTT.setEndTime();
				System.out.println("All orders complete!");
				break;
			}
			System.out.print("");
		}
		submitToDB();
	}

	public void submitToDB() {
		Connection conn = null;
		Statement st = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager
					.getConnection("jdbc:mysql://localhost/RestaurantDB?user=root&password=root&useSSL=false");
			st = conn.createStatement();
			String name = "Sheldon";
			// rs = st.executeQuery("SELECT * from Student where fname='" + name + "'");

			// Generate the SQL statements for inserting all the order times
			String sql = "INSERT into orderTimes (startTime, endTime, totalTime) values ('" + this.TTT.startTime
					+ "', '" + this.TTT.endTime + "', '" + this.TTT.totalTimeString + "')";

			// iterate through all the orders
			for (int i = 0; i < this.customers.size(); i++) {
				sql += ",('" + this.customers.get(i).TT.startTime.trim() + "', '"
						+ this.customers.get(i).TT.endTime.trim() + "', '"
						+ this.customers.get(i).TT.totalTimeString.trim() + "')";
			}
			// complete the SQL statement

			sql += ";";

//			System.out.println(sql);

			ps = conn.prepareStatement(sql);
			ps.executeUpdate();

			// Generate the SQL statements for inserting the quantities for each order

			String sql2 = "INSERT into orderQuantity (frenchFries, funnelCake, cornDog, hamburger, veggieburger, chocolateShake, strawberryShake, vanillaShake, small, medium, large, coffee) values ";

			for (int i = 0; i < this.customers.size(); i++) {
				sql2 += "(";
				for (int j = 0; j < this.allItemsOnMenu.size(); j++) {
					sql2 += this.customers.get(i).orderItemsQuantity[j] + ", ";
				}
				sql2 = sql2.substring(0, sql2.length() - 2);
				sql2 += "),";
			}
			sql2 = sql2.substring(0, sql2.length() - 1);
			sql2 += ";";

//			System.out.println(sql2);
			ps.close();
			ps = conn.prepareStatement(sql2);
			ps.executeUpdate();
			
		} catch (SQLException sqle) {
			System.out.println("SQLException: " + sqle.getMessage());
		} catch (ClassNotFoundException cnfe) {
			System.out.println("ClassNotFoundException: " + cnfe.getMessage());
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (st != null) {
					st.close();
				}
				if (ps != null) {
					ps.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException sqle) {
				System.out.println("sqle: " + sqle.getMessage());
			}
		}
	}

	public void run() {

		// =========Initialize the Orders============
		for (int i = 0; i < this.customers.size(); i++) {
			this.customers.get(i).createOrderItemsArray(this, (i + 1));
		}
		this.takeMyOrder();

	}

	// ============================== Main ==============================
	public static void main(String[] args) {
		FileReader fr;
		BufferedReader br;
		String buff = "";
		String filename = "";
		Scanner input = new Scanner(System.in);
		Restaurant restaurant = null;

		while (true) {
			try {
				System.out.println("Please enter a valid file:");
				filename = input.nextLine();

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
				input.close();

				break;
			} catch (FileNotFoundException fnfe) {
				System.out.println("That file could not be found");
			} catch (IOException ioe) {
				System.out.println("That file is not a well-formed JSON file, please try again.");
			}

		}
		// Creating a new Gson parser to parse buff into Roster object
		Gson gson = new Gson();
		try {
			restaurant = gson.fromJson(buff, Restaurant.class);
		} catch (JsonSyntaxException jsse) {
			System.out.println(jsse.getMessage());
		}

		// =============================== Beginning of Preparing
		// Orders====================================
		PreparingOrders prep = new PreparingOrders(restaurant);

	}

}
