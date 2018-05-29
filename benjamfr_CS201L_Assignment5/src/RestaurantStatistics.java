import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class RestaurantStatistics {

	public static void main(String[] args) {
		Connection conn = null;
		Statement st = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager
					.getConnection("jdbc:mysql://localhost/RestaurantDB?user=root&password=root&useSSL=false");
			st = conn.createStatement();
			rs = st.executeQuery("SELECT * FROM RestaurantDB.orderTimes;");

			
			ArrayList<String> startTimes = new ArrayList<String>();
			ArrayList<String> endTimes = new ArrayList<String>();
			ArrayList<String> lengths = new ArrayList<String>();
			
			
			while (rs.next()) {
				startTimes.add(rs.getString("startTime").trim());
				endTimes.add(rs.getString("endTime").trim());
				lengths.add(rs.getString("totalTime").trim());
			}
			
		System.out.println("Execution started at " + startTimes.get(0) + ".");
		for(int i = 1; i < lengths.size(); i++) {
			System.out.println("Order " + i + " took " + lengths.get(i) + " to complete.");
		}
		System.out.println("Execution took " + lengths.get(0) + " to complete.");
		System.out.println("Execution ended at " + endTimes.get(0) + ".");


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
}
