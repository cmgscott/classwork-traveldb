import java.sql.*;
import java.util.ArrayList;

public class Main {
	private static ArrayList<String> theString = new ArrayList<String>();

	public static void main (String[] args) {
		String dbURL = "jdbc:sqlserver://localhost\\homesqlserver:63907;"
				+ "databaseName=scott_christin_db;integratedSecurity=true;";
		try (
				Connection conn = DriverManager.getConnection(dbURL);
				Statement stmt = conn.createStatement();
		){
			String userSelect = "SELECT * FROM UserAccount";
			GUIMain gui = new GUIMain(theString, stmt, conn, dbURL);
			System.out.println();
		} catch(SQLException e) {
			e.printStackTrace();
		}
	}
}
