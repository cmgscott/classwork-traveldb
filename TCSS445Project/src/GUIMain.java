import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.awt.event.ActionEvent;
import javax.swing.JList;
import javax.swing.AbstractListModel;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.JTabbedPane;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.border.EtchedBorder;
import javax.swing.JTextField;
import javax.swing.JSeparator;
import javax.swing.JOptionPane;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.SwingConstants;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JScrollPane;
import javax.swing.JCheckBox;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import java.awt.BorderLayout;

public class GUIMain {

	private JFrame frame;
	private ArrayList<String> values = new ArrayList<String>();
	private ArrayList<String> theString;
	private JTextField txtfldFName;
	private JTextField txtfldLName;
	private JTextField txtfldEmail;
	ArrayList<String> itenerariesAL = new ArrayList<String>();
	ArrayList<User> usersAL = new ArrayList<User>();
	Statement statement;
	Object[] options = {"Yes", "No"};
	JButton btnAddNewitinerary;
	JButton btnDeleteSelecteditinerary;
	JButton btnEditSelecteditinerary;
	User currentUser;
	Connection conn;
	String dbURL;
	JList<String> listUsers;
	JPanel pntabUsers;
	JButton btnDeleteUser;
	JButton btnAddNew;
	DefaultListModel<String> itineraryListModel;
	JList<String> listItineraries;
	Itinerary currentitinerary;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_3;
	private JTextField txtfldSearchFirstNames;
	private JTextField txtfldSearchLastNames;
	private JTextField txtfldSearchIDs;
	private JTextField txtfldSearchEmails;
	private JTable tblEntryInfoRow1;
	private JTable tblEntryInfoRow2;
	private Attraction currentAttraction;
	ArrayList<Attraction> attractionsAL;
	DefaultTableModel modelRow1;
	DefaultTableModel modelRow2;
	DefaultTableModel modelRow3;
	JCheckBox chckbxFilterAccommodation;
	JCheckBox chckbxPark;
	JCheckBox chckbxFilterMonument;
	JCheckBox chckbxFilterMuseum;
	JCheckBox chckbxFilterGeoFeature;
	JCheckBox chckbxFilterRestaurant;
	JCheckBox chckbxFilterPerfVenue;
	JCheckBox chckbxFilterAirport;
	JList<String> listEntries;
	JCheckBox chckbxFilterContinent;
	JList<String> lstAttractionsCloseTo;
	JButton btnViewRoomAvailability;
	JButton btnViewPerformanceSchedule;
	private JTextField txtfldCheckInDate;
	private JTextField txtfldCheckOutDate;
	JList<String> listAvailableRooms;
	Attraction currentHotel = null;

	/** Create the application. */
	public GUIMain(ArrayList<String> theString, Statement theStatement, Connection theConnection, String theDBURL) {
		this.theString = theString;
		statement = theStatement;
		conn = theConnection;
		dbURL = theDBURL;
		try {
			initialize();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Initialize the contents of the frame.
	 * @throws SQLException 
	 */
	private void initialize() throws SQLException {
		frame = new JFrame();
		frame.setBounds(100, 100, 855, 549);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(0, 0, 839, 489);
		//tabbedPane.setLayout(new AbsoluteLayout());
		frame.getContentPane().add(tabbedPane);
		usersAL = User.generateUserArray(statement);
		for (int i = 0; i < usersAL.size(); i++) {
			usersAL.get(i).generateIteneraries(statement);
		}

		// make model for itinerary list
		itineraryListModel = new DefaultListModel<String>();
		int newUserID = (int) Math.random() * 999999;
		JPanel jpnlAddNewUser = new JPanel();
		JTextField txtfldUserEmail = new JTextField(15);
		JTextField txtfldFirstName = new JTextField(10);
		JTextField txtfldLastName = new JTextField(10);
		jpnlAddNewUser.add(new JLabel("User Email Address: "));
		jpnlAddNewUser.add(txtfldUserEmail);
		jpnlAddNewUser.add(new JLabel("First Name: "));
		jpnlAddNewUser.add(txtfldFirstName);
		jpnlAddNewUser.add(new JLabel("Last Name: "));
		jpnlAddNewUser.add(txtfldLastName);

		// user accounts tab
		pntabUsers = new JPanel();
		tabbedPane.addTab("Users Accounts", null, pntabUsers, null);
		pntabUsers.setLayout(null);

		JLabel lblSearchseperateBy = new JLabel("Search (seperate terms by commas)");
		lblSearchseperateBy.setBounds(225, 11, 234, 20);
		pntabUsers.add(lblSearchseperateBy);

		JSeparator separator = new JSeparator();
		separator.setBounds(225, 177, 583, 15);
		pntabUsers.add(separator);

		JLabel lblFirstName = new JLabel("First Name:");
		lblFirstName.setBounds(235, 203, 69, 14);
		pntabUsers.add(lblFirstName);

		JLabel lblLastName = new JLabel("Last Name:");
		lblLastName.setBounds(515, 203, 69, 14);
		pntabUsers.add(lblLastName);

		JLabel lblEmailAddress = new JLabel("E-mail Address:");
		lblEmailAddress.setBounds(235, 228, 110, 14);
		pntabUsers.add(lblEmailAddress);

		txtfldFName = new JTextField();
		txtfldFName.setEditable(false);
		txtfldFName.setBounds(305, 200, 200, 20);
		pntabUsers.add(txtfldFName);
		txtfldFName.setColumns(10);

		txtfldLName = new JTextField();
		txtfldLName.setEditable(false);
		txtfldLName.setColumns(10);
		txtfldLName.setBounds(590, 200, 218, 20);
		pntabUsers.add(txtfldLName);

		txtfldEmail = new JTextField();
		txtfldEmail.setEditable(false);
		txtfldEmail.setColumns(10);
		txtfldEmail.setBounds(329, 225, 479, 20);
		pntabUsers.add(txtfldEmail);

		JLabel lblIteneraries = new JLabel("Iteneraries:");
		lblIteneraries.setBounds(225, 274, 79, 14);
		pntabUsers.add(lblIteneraries);

		JButton btnSaveChanges = new JButton("Save Changes");
		JButton btnEditEntry = new JButton("Edit Entry");
		btnSaveChanges.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int saveConfirm = JOptionPane.showOptionDialog(frame, "Are you sure you want to save changes?", 
						"Save?", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[1]);
				if (saveConfirm == 0) {
					try {
						conn = DriverManager.getConnection(dbURL);
						// settings for updating a row
						statement = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
						String firstName = txtfldFName.getText();
						String lastName = txtfldLName.getText();
						String email = txtfldEmail.getText();
						int tempID = currentUser.getUserID();
						statement.executeUpdate("UPDATE UserAccount SET FirstName = '" + firstName + "', LastName = '" 
								+ lastName + "', EmailAddr = '" + email + "' WHERE UserID = " + tempID);
						// update user array
						currentUser.updateUserInfo(firstName, lastName, email, tempID);
						// update the display
						DefaultListModel<String> newListModel = new DefaultListModel<String>();
						for (int i = 0; i < usersAL.size(); i++) {
							newListModel.addElement(usersAL.get(i).getFirstName());
						}
						listUsers.setModel(newListModel);
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				}
				btnEditEntry.setVisible(true);
				btnSaveChanges.setVisible(false);
				txtfldFName.setEditable(false);
				txtfldLName.setEditable(false);
				txtfldEmail.setEditable(false);
			}
		});

		btnEditEntry.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				// make it possible to edit user info
				btnEditEntry.setVisible(false);
				btnSaveChanges.setVisible(true);
				txtfldFName.setEditable(true);
				txtfldLName.setEditable(true);
				txtfldEmail.setEditable(true);
			}
		});
		btnEditEntry.setBounds(608, 256, 200, 23);
		pntabUsers.add(btnEditEntry);
		btnSaveChanges.setBounds(689, 256, 120, 24);
		btnSaveChanges.setVisible(false);
		pntabUsers.add(btnSaveChanges);
		listItineraries = new JList<String>(itineraryListModel);
		listItineraries.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				// set itinerary pointer
				if (listItineraries.getSelectedIndex() != -1)
					currentitinerary = currentUser.getitineraryAL().get(listItineraries.getSelectedIndex());

				// enable the buttons to delete and edit the itinerary
				btnDeleteSelecteditinerary.setEnabled(true);
				btnEditSelecteditinerary.setEnabled(true);
			}
		});
		listItineraries.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		listItineraries.setBounds(225, 299, 583, 94);
		pntabUsers.add(listItineraries);

		// create jlist for displaying users
		listUsers = new JList<String>();
		// add selection listener
		listUsers.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent arg0) {

				// set current user pointer
				if (listUsers.getSelectedIndex() >= 0) {
					currentUser = usersAL.get(listUsers.getSelectedIndex()); // update pointer to current user
					// create model to update itinerary display after changes
					itineraryListModel = new DefaultListModel<String>();
					// add display items to the list model
					for (int i = 0; i < currentUser.getitineraryAL().size(); i++) {
						itineraryListModel.addElement(currentUser.getitineraryAL().get(i).getitineraryName());
					}
					// enable the delete user and add new itinerary button once a user has been selected
					// set text in users info fields
					txtfldFName.setText(usersAL.get(listUsers.getSelectedIndex()).getFirstName());
					txtfldLName.setText(usersAL.get(listUsers.getSelectedIndex()).getLastName());
					txtfldEmail.setText(usersAL.get(listUsers.getSelectedIndex()).getEmailAddr());
				} else {
					currentUser = null;
				}
				btnDeleteUser.setEnabled(true);
				btnAddNewitinerary.setEnabled(true);
				listItineraries.setModel(itineraryListModel);
			}
		});

		listUsers.setModel(new AbstractListModel<String>() {
			/**
			 * 
			 */
			private static final long serialVersionUID = -7769523881409967867L;
			public int getSize() {
				return usersAL.size();
			}
			public String getElementAt(int index) {
				return usersAL.get(index).getLastName() + ", " + usersAL.get(index).getFirstName();
			}
		});
		listUsers.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		listUsers.setBounds(10, 50, 181, 347);
		pntabUsers.add(listUsers);

		btnEditSelecteditinerary = new JButton("Edit selected itinerary");
		btnEditSelecteditinerary.setEnabled(false);
		btnEditSelecteditinerary.setBounds(608, 404, 200, 23);
		pntabUsers.add(btnEditSelecteditinerary);

		btnDeleteSelecteditinerary = new JButton("Delete selected itinerary");
		btnDeleteSelecteditinerary.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				int result = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete this itinerary? "
						+ "(Cannot be undone)");
				if (result == 0) {
					Connection conn;
					try {
						conn = DriverManager.getConnection(dbURL);
						Statement stmt = conn.createStatement();
						stmt.execute("DELETE FROM itinerary WHERE itinerary.itineraryID = " 
								+ currentUser.getitineraryAL().get(listItineraries.getSelectedIndex()).getitineraryID() 
								+ " AND itinerary.userID = " + currentUser.getUserID() + ";");

						currentUser.getitineraryAL().remove(currentitinerary);
						itineraryListModel = new DefaultListModel<String>();
						for (int i = 0; i < currentUser.getitineraryAL().size(); i++) {
							itineraryListModel.addElement(currentUser.getitineraryAL().get(i).getitineraryName());
						}
						listItineraries.setModel(itineraryListModel);
						pntabUsers.add(listItineraries);
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
			}
		});
		btnDeleteSelecteditinerary.setEnabled(false);
		btnDeleteSelecteditinerary.setBounds(401, 404, 200, 23);
		pntabUsers.add(btnDeleteSelecteditinerary);

		JSeparator separator_1 = new JSeparator();
		separator_1.setOrientation(SwingConstants.VERTICAL);
		separator_1.setBounds(205, 11, 10, 416);
		pntabUsers.add(separator_1);

		JButton btnSearch = new JButton("Search");

		btnSearch.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				// search for all four search fields

				// initialize userID string search
				StringBuilder userIDSearchString = new StringBuilder();
				userIDSearchString.append("SELECT * FROM UserAccount WHERE UserID = '");
				// collect and trim search terms
				String[] userIDSearchTerms = txtfldSearchIDs.getText().split(",");
				for (int i = 0; i < userIDSearchTerms.length; i++) {
					userIDSearchTerms[i] = userIDSearchTerms[i].trim();
					userIDSearchString.append(userIDSearchTerms[i]);
					if (i < userIDSearchTerms.length - 1) {
						userIDSearchString.append("' OR UserID = '");
					}
				}
				userIDSearchString.append("';");

				// initialize first name string search
				StringBuilder FirstNameSearchString = new StringBuilder();
				FirstNameSearchString.append("SELECT * FROM UserAccount WHERE FirstName = '");
				// collect and trim search terms
				String[] firstNameSearchTerms = txtfldSearchFirstNames.getText().split(",");
				for (int i = 0; i < firstNameSearchTerms.length; i++) {
					firstNameSearchTerms[i] = firstNameSearchTerms[i].trim();
					FirstNameSearchString.append(firstNameSearchTerms[i]);
					if (i < firstNameSearchTerms.length - 1) {
						FirstNameSearchString.append("' OR FirstName = '");
					}
				}
				FirstNameSearchString.append("';");

				// initialize last name string search
				StringBuilder lastNameSearchString = new StringBuilder();
				lastNameSearchString.append("SELECT * FROM UserAccount WHERE LastName = '");
				// collect and trim search terms
				String[] lastNameSearchTerms = txtfldSearchLastNames.getText().split(",");
				for (int i = 0; i < lastNameSearchTerms.length; i++) {
					lastNameSearchTerms[i] = lastNameSearchTerms[i].trim();
					lastNameSearchString.append(lastNameSearchTerms[i]);
					if (i < lastNameSearchTerms.length - 1) {
						lastNameSearchString.append("' OR LastName = '");
					}
				}
				lastNameSearchString.append("';");

				// initialize email string search
				StringBuilder emailSearchString = new StringBuilder();
				emailSearchString.append("SELECT * FROM UserAccount WHERE EmailAddr = '");
				// collect and trim search terms
				String[] emailSearchTerms = txtfldSearchEmails.getText().split(",");
				for (int i = 0; i < emailSearchTerms.length; i++) {
					emailSearchTerms[i] = emailSearchTerms[i].trim();
					emailSearchString.append(emailSearchTerms[i]);
					if (i < emailSearchTerms.length - 1) {
						emailSearchString.append("' OR EmailAddr = '");
					}
				}
				emailSearchString.append("';");

				// run queries
				Connection conn;

				ArrayList<User> searchResultsUsersAL = new ArrayList<User>();
				try {
					conn = DriverManager.getConnection(dbURL);
					Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
					ResultSet rsUserIDs = stmt.executeQuery(userIDSearchString.toString()); // query ids
					while (rsUserIDs.next()) {
						searchResultsUsersAL.add(new User(rsUserIDs.getString("FirstName"), 
								rsUserIDs.getString("LastName"), rsUserIDs.getString("EmailAddr"), 
								rsUserIDs.getInt("UserID")));
					}
					conn = DriverManager.getConnection(dbURL);
					stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
					ResultSet rsFirstNames = stmt.executeQuery(FirstNameSearchString.toString()); // query first names
					while (rsFirstNames.next()) {
						searchResultsUsersAL.add(new User(rsFirstNames.getString("FirstName"), 
								rsFirstNames.getString("LastName"), rsFirstNames.getString("EmailAddr"), 
								rsFirstNames.getInt("UserID")));
					}
					conn = DriverManager.getConnection(dbURL);
					stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
					ResultSet rsLastNames = stmt.executeQuery(lastNameSearchString.toString()); // query last names
					while (rsLastNames.next()) {
						searchResultsUsersAL.add(new User(rsLastNames.getString("FirstName"), 
								rsLastNames.getString("LastName"), rsLastNames.getString("EmailAddr"), 
								rsLastNames.getInt("UserID")));
					}
					conn = DriverManager.getConnection(dbURL);
					stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
					ResultSet rsEmails = stmt.executeQuery(emailSearchString.toString()); // query emails
					while (rsEmails.next()) {
						searchResultsUsersAL.add(new User(rsEmails.getString("FirstName"), 
								rsEmails.getString("LastName"), rsEmails.getString("EmailAddr"), 
								rsEmails.getInt("UserID")));
					}

					// update display
					if (searchResultsUsersAL.size() < 1) {

					} else {
						DefaultListModel<String> newListModel = new DefaultListModel<String>();
						for (int i = 0; i < searchResultsUsersAL.size(); i++) {
							if (!newListModel.contains(searchResultsUsersAL.get(i).getFirstName())) {
								newListModel.addElement(searchResultsUsersAL.get(i).getFirstName());
							}
						}
						listUsers.setModel(newListModel);
						pntabUsers.add(listUsers);
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		});
		btnSearch.setBounds(608, 143, 200, 23);
		pntabUsers.add(btnSearch);

		btnDeleteUser = new JButton("Delete");
		btnDeleteUser.setEnabled(false);
		btnDeleteUser.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				Connection conn;
				try {
					conn = DriverManager.getConnection(dbURL);
					Statement stmt = conn.createStatement();
					stmt.execute("DELETE FROM UserAccount WHERE UserAccount.UserID = " + currentUser.getUserID() + ";");

					itineraryListModel = new DefaultListModel<String>();
					itineraryListModel.removeAllElements();
					// update users array
					usersAL.remove(currentUser);
					currentUser = null;

					// update the display
					DefaultListModel<String> newListModel = new DefaultListModel<String>();
					for (int i = 0; i < usersAL.size(); i++) {
						newListModel.addElement(usersAL.get(i).getFirstName());
					}
					listUsers.setModel(newListModel);
					pntabUsers.add(listUsers);
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		});
		btnDeleteUser.setBounds(10, 404, 89, 23);
		pntabUsers.add(btnDeleteUser);

		btnAddNew = new JButton("Add new");
		btnAddNew.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				Connection conn;
				int result = JOptionPane.showConfirmDialog(null, jpnlAddNewUser, "Please enter new user details", 
						JOptionPane.OK_CANCEL_OPTION);
				if (result == 0) {
					try {
						conn = DriverManager.getConnection(dbURL);
						Statement stmt = conn.createStatement();
						stmt.execute("INSERT INTO UserAccount VALUES (" + newUserID + ", '" + txtfldUserEmail.getText() 
						+ "', '" + txtfldFirstName.getText() + "', '" + txtfldLastName.getText() + "');");
						// update users array
						usersAL.add(new User(txtfldFirstName.getText(), txtfldLastName.getText(), 
								txtfldUserEmail.getText(), newUserID));
						// update the display
						DefaultListModel<String> newListModel = new DefaultListModel<String>();
						for (int i = 0; i < usersAL.size(); i++) {
							newListModel.addElement(usersAL.get(i).getFirstName());
						}
						listUsers.setModel(newListModel);
						pntabUsers.add(listUsers);
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}

			}
		});
		btnAddNew.setBounds(102, 404, 89, 23);
		pntabUsers.add(btnAddNew);

		JLabel lblUsers = new JLabel("Users:");
		lblUsers.setBounds(10, 25, 46, 14);
		pntabUsers.add(lblUsers);

		btnAddNewitinerary = new JButton("Add new itinerary");
		btnAddNewitinerary.setEnabled(false);
		btnAddNewitinerary.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				addNewitineraryItem();
			}
		});
		btnAddNewitinerary.setBounds(225, 404, 166, 23);
		pntabUsers.add(btnAddNewitinerary);

		JLabel lblFirstNames = new JLabel("First Name(s):");
		lblFirstNames.setBounds(225, 71, 79, 14);
		pntabUsers.add(lblFirstNames);

		txtfldSearchFirstNames = new JTextField();
		txtfldSearchFirstNames.setBounds(351, 68, 457, 20);
		pntabUsers.add(txtfldSearchFirstNames);
		txtfldSearchFirstNames.setColumns(10);

		JLabel lblLastNames = new JLabel("Last Name(s):");
		lblLastNames.setBounds(225, 96, 116, 14);
		pntabUsers.add(lblLastNames);

		txtfldSearchLastNames = new JTextField();
		txtfldSearchLastNames.setBounds(351, 93, 457, 20);
		pntabUsers.add(txtfldSearchLastNames);
		txtfldSearchLastNames.setColumns(10);

		txtfldSearchIDs = new JTextField();
		txtfldSearchIDs.setBounds(351, 42, 457, 20);
		pntabUsers.add(txtfldSearchIDs);
		txtfldSearchIDs.setColumns(10);

		txtfldSearchEmails = new JTextField();
		txtfldSearchEmails.setBounds(351, 118, 457, 20);
		pntabUsers.add(txtfldSearchEmails);
		txtfldSearchEmails.setColumns(10);

		JLabel lblEmailAddresss = new JLabel("Email Address(s):");
		lblEmailAddresss.setBounds(225, 121, 116, 14);
		pntabUsers.add(lblEmailAddresss);

		JLabel lblUserids = new JLabel("UserID(s):");
		lblUserids.setBounds(225, 46, 69, 14);
		pntabUsers.add(lblUserids);

		JButton btnResetUsersList = new JButton("Reset Users List");
		btnResetUsersList.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				DefaultListModel<String> newListModel = new DefaultListModel<String>();
				for (int i = 0; i < usersAL.size(); i++) {
					newListModel.addElement(usersAL.get(i).getFirstName());
				}
				listUsers.setModel(newListModel);
				pntabUsers.add(listUsers);
			}
		});
		btnResetUsersList.setBounds(10, 427, 181, 23);
		pntabUsers.add(btnResetUsersList);

		JButton btnClearSearchFields = new JButton("Clear Search Fields");
		btnClearSearchFields.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				txtfldSearchIDs.setText("");
				txtfldSearchFirstNames.setText("");
				txtfldSearchLastNames.setText("");
				txtfldSearchEmails.setText("");
			}
		});
		btnClearSearchFields.setBounds(401, 143, 200, 23);
		pntabUsers.add(btnClearSearchFields);

		JComboBox<String> comboBox_1 = new JComboBox<String>();
		comboBox_1.setModel(new DefaultComboBoxModel<String>(new String[] {"Sort by", "Last Name (Asc)", 
				"Last Name (Desc)", "First Name (Asc)", "First Name (Desc)", "UserID (Asc)", "UserID (Desc)"}));
		comboBox_1.setBounds(81, 19, 110, 20);
		pntabUsers.add(comboBox_1);

		JPanel pntabBrowse = new JPanel();
		tabbedPane.addTab("Browse Database", null, pntabBrowse, null);
		pntabBrowse.setLayout(null);

		JScrollPane scrlpnEntries = new JScrollPane();
		scrlpnEntries.setBounds(10, 42, 228, 408);
		pntabBrowse.add(scrlpnEntries);

		// list all attractions on left panel

		// build attractions array
		attractionsAL = new ArrayList<Attraction>();
		Connection conn = DriverManager.getConnection(dbURL);
		Statement stmt = conn.createStatement();
		attractionsAL = Attraction.generateAttractionsArray(stmt, dbURL);

		// create jlist to hold attraction entries
		listEntries = new JList<String>();
		scrlpnEntries.setViewportView(listEntries);

		// add selection listener to list of entries
		listEntries.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent arg0) {
				//tblAccommodationInfo.setValueAt();
				if (listEntries.getSelectedIndex() != -1)
					currentAttraction = attractionsAL.get(listEntries.getSelectedIndex());

				// fill in row 1 info
				modelRow1 = new DefaultTableModel(new Object[][] {
					{currentAttraction.getAttractionID(), currentAttraction.getAttractionName()}}, 
						new Object[] {"f", "f"});
				tblEntryInfoRow1.setModel(modelRow1);
				tblEntryInfoRow1.setRowHeight(0, 50);
				tblEntryInfoRow1.getColumnModel().getColumn(0).setPreferredWidth(5);
				tblEntryInfoRow1.getColumnModel().getColumn(1).setPreferredWidth(160);

				// fill in row 2 info
				modelRow2 = new DefaultTableModel(new Object[][] {
					{currentAttraction.getStreetAddr(), currentAttraction.getCityName(), 
						currentAttraction.getStateName(), currentAttraction.getCountryName(), 
						currentAttraction.getPostalCode()}}, new Object[] {"f", "f", "f", "f", "f"});
				tblEntryInfoRow2.setModel(modelRow2);
				tblEntryInfoRow2.setRowHeight(0, 50);

				if (currentAttraction != null && currentAttraction.getAttractionType().equals("Accommodation")) {
					btnViewRoomAvailability.setEnabled(true);
					btnViewRoomAvailability.setVisible(true);
				} else {
					btnViewRoomAvailability.setEnabled(false);
					btnViewRoomAvailability.setVisible(false);
				}

				if (currentAttraction != null && currentAttraction.getAttractionType().equals("Performance Venue")) {
					btnViewPerformanceSchedule.setEnabled(true);
					btnViewPerformanceSchedule.setVisible(true);
				} else {
					btnViewPerformanceSchedule.setEnabled(false);
					btnViewPerformanceSchedule.setVisible(false);
				}
			}
		});
		DefaultListModel<String> entriesListModel = new DefaultListModel<String>();

		// finish list model
		for (int i = 0; i < attractionsAL.size(); i++) {
			Attraction tempAttract = attractionsAL.get(i);
			entriesListModel.addElement(tempAttract.getAttractionName());
		}
		listEntries.setModel(entriesListModel);

		JComboBox<String> comboBox = new JComboBox<String>();
		comboBox.setBounds(66, 14, 172, 20);
		comboBox.setModel(new DefaultComboBoxModel<String>(new String[] {"Continent", "Country", "Attraction Type", 
		"Flights"}));
		pntabBrowse.add(comboBox);

		JLabel lblSortBy = new JLabel("Sort by:");
		lblSortBy.setBounds(10, 17, 46, 14);
		pntabBrowse.add(lblSortBy);

		JButton btnShowAttractionsClose = new JButton("Show attractions within 10km");
		ArrayList<Double> distancesAL = new ArrayList<Double>();
		DefaultListModel<String> listModel = new DefaultListModel<String>();
		btnShowAttractionsClose.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {

				distancesAL.clear();
				listModel.removeAllElements();
				for (int i = 0; i < attractionsAL.size(); i++) {
					distancesAL.add(haversineFormula(currentAttraction.getLatitude(), currentAttraction.getLongitude(), 
							attractionsAL.get(i).getLatitude(), attractionsAL.get(i).getLongitude()));
				}
				ArrayList<Attraction> closeAttractionsAL = new ArrayList<Attraction>();
				for (int i = 0; i < distancesAL.size(); i++) {
					if (distancesAL.get(i) < 10) {
						closeAttractionsAL.add(attractionsAL.get(i));
					}
				}
				for (int i = 0; i < closeAttractionsAL.size(); i++) {
					listModel.addElement(closeAttractionsAL.get(i).getAttractionName());
				}
				lstAttractionsCloseTo.setModel(listModel);
			}
		});
		btnShowAttractionsClose.setBounds(612, 143, 212, 23);
		pntabBrowse.add(btnShowAttractionsClose);

		chckbxFilterAccommodation = new JCheckBox("Accommodation");
		chckbxFilterAccommodation.setBounds(244, 39, 142, 23);
		chckbxFilterAccommodation.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				// change list view
				try {
					changeListView();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		});
		pntabBrowse.add(chckbxFilterAccommodation);

		chckbxPark = new JCheckBox("Park");
		chckbxPark.setBounds(244, 65, 97, 23);
		chckbxFilterAccommodation.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				// change list view
				try {
					changeListView();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		});
		pntabBrowse.add(chckbxPark);

		chckbxFilterMonument = new JCheckBox("Historic Monument");
		chckbxFilterMonument.setBounds(244, 91, 136, 23);
		chckbxFilterAccommodation.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				// change list view
				try {
					changeListView();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		});
		pntabBrowse.add(chckbxFilterMonument);

		chckbxFilterMuseum = new JCheckBox("Museum");
		chckbxFilterMuseum.setBounds(244, 117, 97, 23);
		chckbxFilterAccommodation.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				// change list view
				try {
					changeListView();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		});
		pntabBrowse.add(chckbxFilterMuseum);

		chckbxFilterGeoFeature = new JCheckBox("Geographical Feature");
		chckbxFilterGeoFeature.setBounds(244, 143, 150, 23);
		chckbxFilterAccommodation.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				// change list view
				try {
					changeListView();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		});
		pntabBrowse.add(chckbxFilterGeoFeature);

		chckbxFilterRestaurant = new JCheckBox("Restaurant");
		chckbxFilterRestaurant.setBounds(244, 169, 97, 23);
		chckbxFilterAccommodation.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				// change list view
				try {
					changeListView();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		});
		pntabBrowse.add(chckbxFilterRestaurant);

		chckbxFilterPerfVenue = new JCheckBox("Performance Venue");
		chckbxFilterPerfVenue.setBounds(244, 195, 150, 23);
		chckbxFilterAccommodation.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				// change list view
				try {
					changeListView();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		});
		pntabBrowse.add(chckbxFilterPerfVenue);

		chckbxFilterAirport = new JCheckBox("Airport");
		chckbxFilterAirport.setBounds(244, 221, 97, 23);
		chckbxFilterAccommodation.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				// change list view
				try {
					changeListView();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		});
		pntabBrowse.add(chckbxFilterAirport);

		chckbxFilterContinent = new JCheckBox("Continent");
		chckbxFilterContinent.setBounds(244, 247, 97, 23);
		chckbxFilterAccommodation.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				// change list view
				try {
					changeListView();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		});
		pntabBrowse.add(chckbxFilterContinent);

		textField = new JTextField();
		textField.setBounds(248, 270, 156, 20);
		pntabBrowse.add(textField);
		textField.setColumns(10);

		JCheckBox chckbxCountry = new JCheckBox("Country");
		chckbxCountry.setBounds(244, 293, 97, 23);
		chckbxFilterAccommodation.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				try {
					changeListView();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		});
		pntabBrowse.add(chckbxCountry);

		textField_1 = new JTextField();
		textField_1.setBounds(248, 316, 156, 20);
		pntabBrowse.add(textField_1);
		textField_1.setColumns(10);

		JCheckBox chckbxState = new JCheckBox("State");
		chckbxState.setBounds(244, 340, 97, 23);
		chckbxFilterAccommodation.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				try {
					changeListView();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		});
		pntabBrowse.add(chckbxState);

		textField_2 = new JTextField();
		textField_2.setBounds(248, 364, 156, 20);
		pntabBrowse.add(textField_2);
		textField_2.setColumns(10);

		JCheckBox chckbxNewCheckBox = new JCheckBox("City");
		chckbxNewCheckBox.setBounds(244, 385, 97, 23);
		chckbxFilterAccommodation.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				try {
					changeListView();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		});
		pntabBrowse.add(chckbxNewCheckBox);

		textField_3 = new JTextField();
		textField_3.setBounds(248, 410, 156, 20);
		textField_3.setText("");
		pntabBrowse.add(textField_3);
		textField_3.setColumns(10);

		JSeparator separator_2 = new JSeparator();
		separator_2.setBounds(414, 4, 14, 439);
		separator_2.setOrientation(SwingConstants.VERTICAL);
		pntabBrowse.add(separator_2);

		JPanel pnlEntryInfoRow2 = new JPanel();
		pnlEntryInfoRow2.setBounds(424, 64, 400, 53);
		pntabBrowse.add(pnlEntryInfoRow2);
		pnlEntryInfoRow2.setLayout(new BorderLayout(0, 0));

		// display entry information

		// create table
		JPanel pnlEntryInfoRow1 = new JPanel();
		pnlEntryInfoRow1.setBounds(424, 11, 400, 53);
		pntabBrowse.add(pnlEntryInfoRow1);
		pnlEntryInfoRow1.setLayout(new BorderLayout(0, 0));

		tblEntryInfoRow1 = new JTable();
		DefaultTableModel modelRow1 = new DefaultTableModel(new Object[][] {
			{"<html>AttractionID</html>", "<html>Name</html>"}}, new Object[] {"f", "f"});
		tblEntryInfoRow1.setModel(modelRow1);
		tblEntryInfoRow1.getColumnModel().getColumn(0).setPreferredWidth(40);
		tblEntryInfoRow1.getColumnModel().getColumn(1).setPreferredWidth(125);
		tblEntryInfoRow1.setRowHeight(0, 50);
		pnlEntryInfoRow1.add(tblEntryInfoRow1, BorderLayout.CENTER);


		JLabel lblFilter = new JLabel("Filter:");
		lblFilter.setBounds(249, 18, 46, 14);
		pntabBrowse.add(lblFilter);

		tblEntryInfoRow2 = new JTable();
		DefaultTableModel modelRow2 = new DefaultTableModel(new Object[][] {
			{"<html>Street Address</html>", "<html>City</html>", "<html>Country</html>", "<html>State</html>", 
			"<html>Postal Code</html>"}}, new Object[] {"f", "f", "f", "f", "f"});
		tblEntryInfoRow2.setModel(modelRow2);
		tblEntryInfoRow2.setRowHeight(0, 50);
		pnlEntryInfoRow2.add(tblEntryInfoRow2, BorderLayout.CENTER);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(429, 221, 395, 229);
		pntabBrowse.add(scrollPane);

		lstAttractionsCloseTo = new JList<String>();
		scrollPane.setViewportView(lstAttractionsCloseTo);

		JButton btnAddToItenerary = new JButton("Add to Itenerary");
		btnAddToItenerary.setBounds(424, 143, 184, 23);
		pntabBrowse.add(btnAddToItenerary);

		btnViewRoomAvailability = new JButton("View Room Availability");
		btnViewRoomAvailability.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
			}
		});
		btnViewRoomAvailability.setEnabled(false);
		btnViewRoomAvailability.setVisible(false);
		btnViewRoomAvailability.setBounds(424, 169, 184, 23);
		pntabBrowse.add(btnViewRoomAvailability);

		btnViewPerformanceSchedule = new JButton("View Performance Schedule");
		btnViewPerformanceSchedule.setEnabled(false);
		btnViewPerformanceSchedule.setVisible(false);
		btnViewPerformanceSchedule.setBounds(612, 169, 212, 23);
		pntabBrowse.add(btnViewPerformanceSchedule);

		JPanel panel = new JPanel();
		tabbedPane.addTab("Browse Accommodation", null, panel, null);
		panel.setLayout(null);

		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(10, 30, 231, 420);
		panel.add(scrollPane_1);

		JList<String> listAccommodations = new JList<String>();
		scrollPane_1.setViewportView(listAccommodations);
		DefaultListModel<String> hotelListModel = new DefaultListModel<String>();
		DefaultListModel<String> hotelRoomListModel = new DefaultListModel<String>();
		//tblAccommodationInfo.setValueAt();
		for (int i = 0; i < attractionsAL.size(); i++) {
			if (attractionsAL.get(i).getAttractionType() != null 
					&& attractionsAL.get(i).getAttractionType().equals("Accommodation")) {
				hotelListModel.addElement(attractionsAL.get(i).getAttractionName());
			}
		}
		// add selection listener to list of entries
		listAccommodations.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent arg0) {
				//tblAccommodationInfo.setValueAt();
				if (listAccommodations.getSelectedIndex() != -1) {
					for (int i = 0; i < attractionsAL.size(); i++) {
						if (attractionsAL.get(i).getAttractionName().equals(listAccommodations.getSelectedValue())) {
							currentHotel = attractionsAL.get(i);
							break;
						}
					}
				}
				Connection conn;
				try {
					conn = DriverManager.getConnection(dbURL);
					Statement stmt = conn.createStatement();
					ArrayList<AccommodationRoom> availableRoomsAL = new ArrayList<AccommodationRoom>();
					ResultSet rsAvailableRooms = stmt.executeQuery(
							"SELECT alias.AttractionID, alias.RoomID, alias.Beds, alias.Sleeps, alias.RoomView, "
									+ "alias.FloorNumber, RoomAvailability.Date, RoomAvailability.Price, "
									+ "RoomAvailability.IsAvailable " + 
									"FROM RoomAvailability, " + 
									"    (SELECT Accommodation.AttractionID, Room.RoomID, Room.Beds, Room.Sleeps, "
									+ "Room.RoomView, Room.FloorNumber FROM Accommodation, Room WHERE "
									+ "Accommodation.AttractionID = Room.AttractionID) alias " + 
									"WHERE (RoomAvailability.AttractionID = alias.AttractionID) AND "
									+ "(RoomAvailability.RoomID = alias.RoomID);");
					while (rsAvailableRooms.next()) {
						availableRoomsAL.add(new AccommodationRoom(rsAvailableRooms.getLong("AttractionID"), 
								rsAvailableRooms.getInt("RoomID"), rsAvailableRooms.getInt("Beds"), 
								rsAvailableRooms.getInt("Sleeps"), rsAvailableRooms.getInt("FloorNumber"), 
								rsAvailableRooms.getInt("Price"), rsAvailableRooms.getInt("IsAvailable"), 
								rsAvailableRooms.getString("RoomView"), rsAvailableRooms.getDate("Date")));
					}
					for (int i = 0; i < availableRoomsAL.size(); i++) {
						if (currentHotel != null && currentHotel.attractionID == availableRoomsAL.get(i).attractionID) {
							hotelRoomListModel.addElement("" + availableRoomsAL.get(i).roomID);
						}
					}
					listAvailableRooms.setModel(hotelRoomListModel);
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		});
		listAccommodations.setModel(hotelListModel);

		JScrollPane scrollPane_2 = new JScrollPane();
		scrollPane_2.setBounds(261, 40, 540, 242);
		panel.add(scrollPane_2);

		listAvailableRooms = new JList<String>();
		scrollPane_2.setViewportView(listAvailableRooms);

		txtfldCheckInDate = new JTextField();
		txtfldCheckInDate.setBounds(359, 337, 167, 20);
		panel.add(txtfldCheckInDate);
		txtfldCheckInDate.setColumns(10);

		txtfldCheckOutDate = new JTextField();
		txtfldCheckOutDate.setText("");
		txtfldCheckOutDate.setBounds(634, 337, 167, 20);
		panel.add(txtfldCheckOutDate);
		txtfldCheckOutDate.setColumns(10);

		JLabel lblCheckinDate = new JLabel("Check-In Date");
		lblCheckinDate.setBounds(261, 340, 91, 14);
		panel.add(lblCheckinDate);

		JLabel lblCheckoutDate = new JLabel("Check-Out Date");
		lblCheckoutDate.setBounds(536, 340, 86, 14);
		panel.add(lblCheckoutDate);

		JLabel lblFilterBy = new JLabel("Filter by:");
		lblFilterBy.setBounds(261, 292, 91, 14);
		panel.add(lblFilterBy);

		JLabel lblAvailableDates = new JLabel("Available Dates");
		lblAvailableDates.setBounds(261, 317, 91, 14);
		panel.add(lblAvailableDates);

		JSeparator separator_3 = new JSeparator();
		separator_3.setBounds(251, 365, 573, 2);
		panel.add(separator_3);

		JSeparator separator_4 = new JSeparator();
		separator_4.setBounds(251, 313, 573, 2);
		panel.add(separator_4);

		JLabel lblCountry = new JLabel("Country:");
		lblCountry.setBounds(261, 378, 63, 14);
		panel.add(lblCountry);

		JLabel lblState = new JLabel("State:");
		lblState.setBounds(536, 378, 46, 14);
		panel.add(lblState);

		JLabel lblCity = new JLabel("City:");
		lblCity.setBounds(261, 403, 46, 14);
		panel.add(lblCity);

		JComboBox<String> cmbbxCountryAccommodation = new JComboBox<String>();
		cmbbxCountryAccommodation.setBounds(332, 375, 194, 20);
		panel.add(cmbbxCountryAccommodation);

		JComboBox<String> cmbbxStateAccommodation = new JComboBox<String>();
		cmbbxStateAccommodation.setBounds(594, 378, 194, 20);
		panel.add(cmbbxStateAccommodation);

		JComboBox<String> cmbbxCityAccommodation = new JComboBox<String>();
		cmbbxCityAccommodation.setBounds(332, 400, 194, 20);
		panel.add(cmbbxCityAccommodation);

		JLabel lblAvailableRooms = new JLabel("Available Rooms:");
		lblAvailableRooms.setBounds(261, 21, 159, 14);
		panel.add(lblAvailableRooms);

		JPanel panel_1 = new JPanel();
		tabbedPane.addTab("Browse Performance Venues", null, panel_1, null);
		panel_1.setLayout(null);

		JScrollPane scrollPane_3 = new JScrollPane();
		scrollPane_3.setBounds(10, 33, 238, 381);
		panel_1.add(scrollPane_3);

		JList<String> listPerformanceVenues = new JList<String>();
		scrollPane_3.setViewportView(listPerformanceVenues);

		JScrollPane scrollPane_4 = new JScrollPane();
		scrollPane_4.setBounds(323, 33, 484, 381);
		panel_1.add(scrollPane_4);

		JList<String> listPerformanceEvents = new JList<String>();
		scrollPane_4.setViewportView(listPerformanceEvents);

		JButton btnAddEventTo = new JButton("Add Event to Itinerary");
		btnAddEventTo.setBounds(621, 427, 182, 23);
		panel_1.add(btnAddEventTo);

		JButton btnAddVenueTo = new JButton("Add Venue to Itinerary");
		btnAddVenueTo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		btnAddVenueTo.setBounds(66, 427, 182, 23);
		panel_1.add(btnAddVenueTo);

		JSeparator separator_5 = new JSeparator();
		separator_5.setOrientation(SwingConstants.VERTICAL);
		separator_5.setBounds(283, 11, 18, 439);
		panel_1.add(separator_5);

		JPanel pntabReports = new JPanel();
		tabbedPane.addTab("Generate Reports", null, pntabReports, null);
		pntabReports.setLayout(null);

		JLabel lblShowTop = new JLabel("Show top");
		lblShowTop.setBounds(32, 28, 46, 14);
		pntabReports.add(lblShowTop);

		JLabel lblIn = new JLabel("in");
		lblIn.setBounds(500, 28, 46, 14);
		pntabReports.add(lblIn);

		JComboBox<String> comboBox_2 = new JComboBox<String>();
		comboBox_2.setModel(new DefaultComboBoxModel<String>(new String[] {"Number", "5", "10", "15", "25", "50", "100"}));
		comboBox_2.setBounds(104, 25, 170, 20);
		pntabReports.add(comboBox_2);

		JComboBox<String> comboBox_3 = new JComboBox<String>();
		comboBox_3.setModel(new DefaultComboBoxModel<String>(new String[] {"Attraction", "Accommodation", "Park", "Historic Monument", "Museum", "Geographical Feature", "Restaurant", "Performance Venue", "Airport"}));
		comboBox_3.setBounds(290, 25, 170, 20);
		pntabReports.add(comboBox_3);

		JComboBox<String> comboBox_4 = new JComboBox<String>();
		comboBox_4.setModel(new DefaultComboBoxModel<String>(new String[] {"Year", "2024", "2023", "2022", "2021", "2020", "2019", "2018", "2017", "2016", "2015"}));
		comboBox_4.setBounds(585, 25, 197, 20);
		pntabReports.add(comboBox_4);

		JMenuBar menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);

		JMenu mnFileMenu = new JMenu("File");
		menuBar.add(mnFileMenu);

		JMenu mnRequestMenu = new JMenu("Request");
		menuBar.add(mnRequestMenu);

		JMenu mnHelpMenu = new JMenu("Help");
		menuBar.add(mnHelpMenu);

		JMenu mnExit = new JMenu("Exit");
		mnExit.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int quitConfirm = JOptionPane.showOptionDialog(frame, "Are you sure you want to close the portal?", 
						"Quit?", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[1]);
				if (quitConfirm == 0) {
					System.exit(0);
				}
			}
		});
		menuBar.add(mnExit);
		frame.setVisible(true);
		values.add("Users");
		for (int i = 0; i < theString.size(); i++) {
			values.add(theString.get(i));
		}
	}

	/**
	 * This method uses the Haversine Function to roughly calculate distances in kilometers.
	 * @param latitude1 latitude of first attraction
	 * @param longitude1 longitude of first attraction
	 * @param latitude2 latitude of second attraction
	 * @param longitude2 longitude of second attraction
	 * @return the distance between the two attractions in kilometers
	 */
	protected double haversineFormula(int latitude1, int longitude1, int latitude2, int longitude2) {
		double distance = 2*6371*Math.asin(Math.sqrt(Math.pow(Math.sin((latitude1-latitude2)/2), 2) 
				+ Math.cos(latitude1)*Math.cos(latitude2)*Math.pow(Math.sin((longitude2-longitude1)/2), 2)));
		System.out.println(distance);
		return distance;
	}

	/**
	 * This method changes the view list
	 * @throws SQLException
	 */
	protected void changeListView() throws SQLException {
		ResultSet rsFilterAccomodation;
		ResultSet rsFilterPark;
		ResultSet rsFilterMonument;
		ResultSet rsFilterMuseum;
		ResultSet rsFilterGeoFeature;
		ResultSet rsFilterRestaurant;
		ResultSet rsFilterAirport;
		ResultSet rsFilterPerfVenue;
		// build array to display
		ArrayList<Attraction> filteredAttractionsAL = new ArrayList<Attraction>();

		List<String> filterOptions = new ArrayList<String>();
		filterOptions.add("Accommodation");
		filterOptions.add("Park");
		filterOptions.add("HistoricMonument");
		filterOptions.add("Museum");
		filterOptions.add("Restaurant");
		filterOptions.add("PerformanceVenue");
		filterOptions.add("Airport");
		DefaultListModel<String> listModel = new DefaultListModel<String>();
		//searchInts.append("SELECT AttractionID, AccommodationName, CityName, Latitude, Longitude, PhoneNumber FROM)
		List<String> listChoices = new ArrayList<String>();
		if (chckbxFilterAccommodation.isSelected()) {
			listChoices.add("0");
		}
		if (chckbxPark.isSelected()) {
			System.out.println("park selected");
			listChoices.add("1");
		}
		if (chckbxFilterMonument.isSelected()) {
			listChoices.add("2");
		}
		if (chckbxFilterMuseum.isSelected()) {
			listChoices.add("3");
		}
		if (chckbxFilterGeoFeature.isSelected()) {
			listChoices.add("4");
		}
		if (chckbxFilterRestaurant.isSelected()) {
			listChoices.add("5");
		}
		if (chckbxFilterPerfVenue.isSelected()) {
			listChoices.add("6");
		}
		if (chckbxFilterAirport.isSelected()) {
			listChoices.add("7");
		}
		if (!chckbxFilterAccommodation.isSelected()) {
			listChoices.remove("0");
		}
		if (!chckbxPark.isSelected()) {
			listChoices.remove("1");
		}
		if (!chckbxFilterMonument.isSelected()) {
			listChoices.remove("2");
		}
		if (!chckbxFilterMuseum.isSelected()) {
			listChoices.remove("3");
		}
		if (!chckbxFilterGeoFeature.isSelected()) {
			listChoices.remove("4");
		}
		if (!chckbxFilterRestaurant.isSelected()) {
			listChoices.remove("5");
		}
		if (!chckbxFilterPerfVenue.isSelected()) {
			listChoices.remove("6");
		}
		if (!chckbxFilterAirport.isSelected()) {
			listChoices.remove("7");
		}
		for (int i = 0; i < listChoices.size(); i++) {
			System.out.println(listChoices.get(i));
		}

		for (int i = 0; i < listChoices.size(); i++) {
			switch (listChoices.get(i)) {
			case "0":
				System.out.println(0);
				Connection conn = DriverManager.getConnection(dbURL);
				Statement stmt = conn.createStatement();
				rsFilterAccomodation = stmt.executeQuery(
						"SELECT AttractionID, AccommodationName, CityName, Latitude, Longitude, PhoneNumber FROM "
						+ "Accommodation");
				while (rsFilterAccomodation.next()) {
					filteredAttractionsAL.add(new Attraction(rsFilterAccomodation.getInt("AttractionID"), 
							rsFilterAccomodation.getString("AccommodationName"),
							rsFilterAccomodation.getString("CityName"), rsFilterAccomodation.getInt("Latitude"), 
							rsFilterAccomodation.getInt("Longitude"), rsFilterAccomodation.getLong("PhoneNumber"), 
							"Accommodation"));
				}
				break;
			case "1":
				System.out.println(1);
				System.out.println("did it");
				conn = DriverManager.getConnection(dbURL);
				stmt = conn.createStatement();
				rsFilterPark = stmt.executeQuery(
						"SELECT AttractionID, ParkName, CityName, Latitude, Longitude, PhoneNumber FROM Park");
				while (rsFilterPark.next()) {
					filteredAttractionsAL.add(new Attraction(rsFilterPark.getInt("AttractionID"), 
							rsFilterPark.getString("ParkName"), rsFilterPark.getString("CityName"), 
							rsFilterPark.getInt("Latitude"), rsFilterPark.getInt("Longitude"), 
							rsFilterPark.getLong("PhoneNumber"), "Park"));
				}
				break;
			case "2":
				System.out.println(2);
				conn = DriverManager.getConnection(dbURL);
				stmt = conn.createStatement();
				rsFilterMonument = stmt.executeQuery(
						"SELECT AttractionID, MonumentName, CityName, Latitude, Longitude, PhoneNumber FROM HistoricMonument");
				while (rsFilterMonument.next()) {
					filteredAttractionsAL.add(new Attraction(rsFilterMonument.getInt("AttractionID"), 
							rsFilterMonument.getString("MonumentName"), rsFilterMonument.getString("CityName"), 
							rsFilterMonument.getInt("Latitude"), rsFilterMonument.getInt("Longitude"),
							rsFilterMonument.getLong("PhoneNumber"), "Historic Monument"));
				}
				break;
			case "3":
				System.out.println(3);
				conn = DriverManager.getConnection(dbURL);
				stmt = conn.createStatement();
				rsFilterMuseum = stmt.executeQuery(
						"SELECT AttractionID, MuseumName, CityName, Latitude, Longitude, PhoneNumber FROM Museum");
				while (rsFilterMuseum.next()) {
					filteredAttractionsAL.add(new Attraction(rsFilterMuseum.getInt("AttractionID"), 
							rsFilterMuseum.getString("MuseumName"), rsFilterMuseum.getString("CityName"), 
							rsFilterMuseum.getInt("Latitude"), rsFilterMuseum.getInt("Longitude"),
							rsFilterMuseum.getLong("PhoneNumber"), "Museum"));
				}
				break;
			case "4":
				System.out.println(4);
				conn = DriverManager.getConnection(dbURL);
				stmt = conn.createStatement();
				rsFilterGeoFeature = stmt.executeQuery(
						"SELECT AttractionID, GeoFeatureName, Latitude, Longitude, PhoneNumber FROM GeographicalFeature");
				while (rsFilterGeoFeature.next()) {
					filteredAttractionsAL.add(new Attraction(rsFilterGeoFeature.getInt("AttractionID"), 
							rsFilterGeoFeature.getString("GeoFeatureName"), rsFilterGeoFeature.getString("CityName"), 
							rsFilterGeoFeature.getInt("Latitude"), rsFilterGeoFeature.getInt("Longitude"),
							rsFilterGeoFeature.getLong("PhoneNumber"), "Geographical Feature"));
				}
				break;
			case "5":
				System.out.println(5);
				conn = DriverManager.getConnection(dbURL);
				stmt = conn.createStatement();
				rsFilterRestaurant = stmt.executeQuery(
						"SELECT AttractionID, RestaurantName, CityName, Latitude, Longitude, PhoneNumber FROM Restaurant");
				while (rsFilterRestaurant.next()) {
					filteredAttractionsAL.add(new Attraction(rsFilterRestaurant.getInt("AttractionID"), 
							rsFilterRestaurant.getString("RestaurantName"), rsFilterRestaurant.getString("CityName"), 
							rsFilterRestaurant.getInt("Latitude"), rsFilterRestaurant.getInt("Longitude"),
							rsFilterRestaurant.getLong("PhoneNumber"), "Restaurant"));
				}
				break;
			case "6":
				System.out.println(6);
				conn = DriverManager.getConnection(dbURL);
				stmt = conn.createStatement();
				rsFilterPerfVenue = stmt.executeQuery(
						"SELECT AttractionID, VenueName, CityName, Latitude, Longitude, PhoneNumber FROM PerformanceVenue");
				while (rsFilterPerfVenue.next()) {
					filteredAttractionsAL.add(new Attraction(rsFilterPerfVenue.getInt("AttractionID"), 
							rsFilterPerfVenue.getString("VenueName"), rsFilterPerfVenue.getString("CityName"), 
							rsFilterPerfVenue.getInt("Latitude"), rsFilterPerfVenue.getInt("Longitude"),
							rsFilterPerfVenue.getLong("PhoneNumber"), "Performance Venue"));
				}
				break;
			case "7":
				System.out.println(7);
				conn = DriverManager.getConnection(dbURL);
				stmt = conn.createStatement();
				rsFilterAirport = stmt.executeQuery(
						"SELECT AttractionID, AirportName, CityName, Latitude, Longitude, PhoneNumber FROM Airport");
				while (rsFilterAirport.next()) {
					filteredAttractionsAL.add(new Attraction(rsFilterAirport.getInt("AttractionID"), 
							rsFilterAirport.getString("AccommodationName"), rsFilterAirport.getString("CityName"), 
							rsFilterAirport.getInt("Latitude"), rsFilterAirport.getInt("Longitude"),
							rsFilterAirport.getLong("PhoneNumber"), "Airport"));
				}
				break;
			}
			for (int j = 0; j < filteredAttractionsAL.size(); j++) {
				listModel.addElement(filteredAttractionsAL.get(j).getAttractionName());
			}
			listEntries.setModel(listModel);
		}

		// update display

	}

	protected void addNewitineraryItem() {
		int newItID = (int) (Math.random() * 9999);
		JPanel itEntryPanel = new JPanel();
		JTextField txtfldItName = new JTextField(10);
		JTextField txtfldItStartDate = new JTextField(10);
		JTextField txtfldItEndDate = new JTextField(10);
		itEntryPanel.add(new JLabel("itinerary Name: "));
		itEntryPanel.add(txtfldItName);
		itEntryPanel.add(new JLabel("Start Date: (ex YYYY-MM-DD)"));
		itEntryPanel.add(txtfldItStartDate);
		itEntryPanel.add(new JLabel("End Date: "));
		itEntryPanel.add(txtfldItEndDate);
		int jopResult = JOptionPane.showConfirmDialog(null, itEntryPanel, "Please enter new itinerary details", 
				JOptionPane.OK_CANCEL_OPTION);
		String newItStartDate;
		String newItEndDate;

		// format start and end date correctly
		StringBuilder stbldStartDate = new StringBuilder();
		stbldStartDate.append("'" + txtfldItStartDate.getText() + "'");
		StringBuilder stbldEndDate = new StringBuilder();
		stbldEndDate.append("'" + txtfldItEndDate.getText() + "'");

		// test that yes was selected, and that the dates entered are in a valid format
		while (jopResult == 0 && (!stbldStartDate.toString().matches("'[0-9]{4}-[0-9]{2}-[0-9]{2}'")) 
				&& (!stbldEndDate.toString().matches("'[0-9]{4}-[0-9]{2}-[0-9]{2}'"))) {
			if (stbldStartDate.toString().length() < 3 
					&& stbldEndDate.toString().matches("'[0-9]{4}-[0-9]{2}-[0-9]{2}'")) break;
			if (stbldEndDate.toString().length() < 3 
					&& stbldStartDate.toString().matches("'[0-9]{4}-[0-9]{2}-[0-9]{2}'")) break;
			if (stbldEndDate.toString().length() < 3 && stbldStartDate.toString().length() < 3) break;
			JOptionPane.showMessageDialog(null, "Please enter valid date");
			jopResult = JOptionPane.showConfirmDialog(null, itEntryPanel, "Please enter new itinerary details", 
					JOptionPane.OK_CANCEL_OPTION);
			if (jopResult == 2 || jopResult == -1) break;
		}
		if (jopResult == 0) {
			newItStartDate = stbldStartDate.toString();
			newItEndDate = stbldEndDate.toString();
			Connection conn;
			try {
				conn = DriverManager.getConnection(dbURL);
				statement = conn.createStatement();
				String newItName = txtfldItName.getText();
				if (newItName.length() < 3) { // the length of a string with at least one character is 3
					newItName = "NULL";
				}
				if (newItStartDate.length() < 3) {
					newItStartDate = "NULL";
				}
				if (newItEndDate.length() < 3) {
					newItEndDate = "NULL";
				}
				// TODO doesn't accept apostrophe's in itinerary name
				statement.execute("INSERT INTO itinerary VALUES (" + newItID + ", " + 
						currentUser.getUserID() + ", '" + currentUser.getEmailAddr() + "', '" + newItName + "', " + 
						newItStartDate + ", " + newItEndDate + ");");

				// add new itinerary to current users itinerary array
				Itinerary newIt = new Itinerary(newItID, newItName);
				currentUser.getitineraryAL().add(newIt);

				// redraw jlist of iteneraries
				itineraryListModel = new DefaultListModel<String>();
				for (int i = 0; i < currentUser.getitineraryAL().size(); i++) {
					itineraryListModel.addElement(currentUser.getitineraryAL().get(i).getitineraryName());
				}
				listItineraries.setModel(itineraryListModel);
				pntabUsers.add(listItineraries);

			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

	}
}
