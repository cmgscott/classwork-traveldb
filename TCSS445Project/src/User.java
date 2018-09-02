import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * This class contains all methods and data about users in the database.
 * @author Christin Scott
 *
 */
/**
 * @author CMGS
 *
 */
public class User {

	private String firstName;
	private String lastName;
	private String emailAddr;
	private int userID;
	private ArrayList<Itinerary> itineraryAL;

	/**
	 * @param f
	 * @param l
	 * @param e
	 * @param id
	 */
	public User(String f, String l, String e, int id) {
		firstName = f;
		lastName = l;
		emailAddr = e;
		userID = id;
		itineraryAL = new ArrayList<Itinerary>();
	}

	/**
	 * @param statement
	 * @return
	 * @throws SQLException
	 */
	public static ArrayList<User> generateUserArray(Statement statement) throws SQLException {
		ArrayList<User> returnArray = new ArrayList<User>();
		ResultSet usersList = statement.executeQuery("SELECT * FROM UserAccount");
		while (usersList.next()) {
			returnArray.add(new User(usersList.getString(3), usersList.getString(4), usersList.getString(2), usersList.getInt(1)));
		}
		return returnArray;
	}

	/**
	 * @param statement
	 */
	public void generateIteneraries(Statement statement) {
		ResultSet iteneraries;
		try {
			iteneraries = statement.executeQuery("SELECT * FROM Itinerary, UserAccount WHERE itinerary.UserID = " + 
		this.userID + " AND UserAccount.UserID = " + this.userID);
			if (!iteneraries.next()) {

			} else {
				do {
					int ints = iteneraries.getInt(1);
					String string = iteneraries.getString(4);
					Itinerary it = new Itinerary(ints, string);
					System.out.println(ints + ", " + string);
					itineraryAL.add(it);

				} while (iteneraries.next());
			}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		/**
		 * @return
		 */
		public String getFirstName() {
			return firstName;
		}

		/**
		 * @param firstName
		 */
		public void setFirstName(String firstName) {
			this.firstName = firstName;
		}

		/**
		 * @return
		 */
		public String getLastName() {
			return lastName;
		}

		/**
		 * @param lastName
		 */
		public void setLastName(String lastName) {
			this.lastName = lastName;
		}

		/**
		 * @return
		 */
		public String getEmailAddr() {
			return emailAddr;
		}

		/**
		 * @param emailAddr
		 */
		public void setEmailAddr(String emailAddr) {
			this.emailAddr = emailAddr;
		}

		/**
		 * @return
		 */
		public int getUserID() {
			return userID;
		}

		/**
		 * @param userID
		 */
		public void setUserID(int userID) {
			this.userID = userID;
		}

		/**
		 * @return
		 */
		public ArrayList<Itinerary> getitineraryAL() {
			return itineraryAL;
		}

		/**
		 * @param itineraryAL
		 */
		public void setitineraryAL(ArrayList<Itinerary> itineraryAL) {
			this.itineraryAL = itineraryAL;
		}

		/**
		 * @param firstName2
		 * @param lastName2
		 * @param email
		 * @param tempID
		 */
		public void updateUserInfo(String firstName2, String lastName2, String email, int tempID) {
			firstName = firstName2;
			lastName = lastName2;
			emailAddr = email;
			userID = tempID;
		}

	}
