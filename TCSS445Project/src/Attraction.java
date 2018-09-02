import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Attraction {
	
	int attractionID;
	String attractionName;
	String cityName;
	int latitude;
	int longitude;
	long phoneNumber;
	String website;
	static ArrayList<Attraction> attractionsAL = new ArrayList<Attraction>();
	String streetAddr;
	String stateName;
	String countryName;
	int postalCode;
	String attractionType;
	
	/**
	 * @param attractionID
	 * @param attractionName
	 * @param cityName
	 * @param latitude
	 * @param longitude
	 * @param phoneNumber
	 * @param type
	 */
	public Attraction(int attractionID, String attractionName, String cityName, int latitude, int longitude, long phoneNumber, String type) {
		this.attractionID = attractionID;
		this.attractionName = attractionName;
		this.cityName = cityName;
		this.latitude = latitude;
		this.longitude = longitude;
		this.phoneNumber = phoneNumber;
		this.attractionType = type;
	}
	
	/**
	 * @param attractionID
	 * @param attractionName
	 * @param cityName
	 * @param latitude
	 * @param longitude
	 * @param phoneNumber
	 * @param type
	 * @param sa
	 * @param sn
	 * @param cn
	 * @param pc
	 */
	public Attraction(int attractionID, String attractionName, String cityName, int latitude, int longitude, long phoneNumber, String type, String sa, String sn, String cn, int pc) {
		this(attractionID, attractionName, cityName, latitude, longitude, phoneNumber, type);
		streetAddr = sa;
		stateName = sn;
		
		postalCode = pc;
	}
	
	/**
	 * @param statement
	 * @param dbURL
	 * @return
	 * @throws SQLException
	 */
	public static ArrayList<Attraction> generateAttractionsArray(Statement statement, String dbURL) throws SQLException {
		
		// add accommodations to list
		ResultSet attractionsResults = statement.executeQuery(
				"SELECT alias.AttractionID, alias.AttractionName, alias.CityName, alias.Latitude, alias.Longitude, "
				+ "alias.PhoneNumber, AttractionAddress.StreetAddr, AttractionAddress.Country, "
				+ "AttractionAddress.PostCode "
				+ "FROM AttractionAddress RIGHT JOIN "
				+ "(SELECT AttractionID, AccommodationName AS AttractionName, CityName, Latitude, Longitude, "
				+ "PhoneNumber FROM Accommodation) alias "
				+ "ON alias.AttractionID = AttractionAddress.AttractionID;");

		while (attractionsResults.next()) {
			attractionsAL.add(new Attraction(attractionsResults.getInt("AttractionID"), 
					attractionsResults.getString(2), attractionsResults.getString("CityName"), 
					attractionsResults.getInt("Latitude"), attractionsResults.getInt("Longitude"), 
					attractionsResults.getLong("PhoneNumber"), "Accommodation",  
					attractionsResults.getString("StreetAddr"), attractionsResults.getString("Country"), 
					null, attractionsResults.getInt("postCode")));
		}
		// add parks
		attractionsResults = statement.executeQuery(
				"SELECT alias.AttractionID, alias.AttractionName, alias.CityName, alias.Latitude, alias.Longitude, "
				+ "alias.PhoneNumber, AttractionAddress.StreetAddr, AttractionAddress.Country, "
				+ "AttractionAddress.PostCode "
				+ "FROM AttractionAddress RIGHT JOIN "
				+ "(SELECT AttractionID, ParkName AS AttractionName, CityName, Latitude, Longitude, PhoneNumber FROM "
				+ "Park) alias "
				+ "ON alias.AttractionID = AttractionAddress.AttractionID;");

		while (attractionsResults.next()) {
			attractionsAL.add(new Attraction(attractionsResults.getInt("AttractionID"), 
					attractionsResults.getString(2), attractionsResults.getString("CityName"), 
					attractionsResults.getInt("Latitude"), attractionsResults.getInt("Longitude"), 
					attractionsResults.getLong("PhoneNumber"), "Park",  attractionsResults.getString("StreetAddr"), 
					attractionsResults.getString("Country"), null, attractionsResults.getInt("postCode")));
		}
		
		// add historic monuments
		attractionsResults = statement.executeQuery(
				"SELECT alias.AttractionID, alias.AttractionName, alias.CityName, alias.Latitude, alias.Longitude, "
				+ "alias.PhoneNumber, AttractionAddress.StreetAddr, AttractionAddress.Country, "
				+ "AttractionAddress.PostCode "
				+ "FROM AttractionAddress RIGHT JOIN "
				+ "(SELECT AttractionID, MonumentName AS AttractionName, CityName, Latitude, Longitude, PhoneNumber "
				+ "FROM HistoricMonument) alias "
				+ "ON alias.AttractionID = AttractionAddress.AttractionID;");

		while (attractionsResults.next()) {
			attractionsAL.add(new Attraction(attractionsResults.getInt("AttractionID"), 
					attractionsResults.getString(2), attractionsResults.getString("CityName"), 
					attractionsResults.getInt("Latitude"), attractionsResults.getInt("Longitude"), 
					attractionsResults.getLong("PhoneNumber"), "Historic Monument",  
					attractionsResults.getString("StreetAddr"), attractionsResults.getString("Country"), null, 
					attractionsResults.getInt("postCode")));
		}
		
		// add museum
		attractionsResults = statement.executeQuery(
				"SELECT alias.AttractionID, alias.AttractionName, alias.CityName, alias.Latitude, alias.Longitude, "
				+ "alias.PhoneNumber, AttractionAddress.StreetAddr, AttractionAddress.Country, "
				+ "AttractionAddress.PostCode "
				+ "FROM AttractionAddress RIGHT JOIN "
				+ "(SELECT AttractionID, MuseumName AS AttractionName, CityName, Latitude, Longitude, PhoneNumber "
				+ "FROM Museum) alias "
				+ "ON alias.AttractionID = AttractionAddress.AttractionID;");

		while (attractionsResults.next()) {
			attractionsAL.add(new Attraction(attractionsResults.getInt("AttractionID"), 
					attractionsResults.getString(2), attractionsResults.getString("CityName"), 
					attractionsResults.getInt("Latitude"), attractionsResults.getInt("Longitude"), 
					attractionsResults.getLong("PhoneNumber"), "Museum",  attractionsResults.getString("StreetAddr"), 
					attractionsResults.getString("Country"), null, attractionsResults.getInt("postCode")));
		}
		
        // add geographical features
		attractionsResults = statement.executeQuery(
				"SELECT alias.AttractionID, alias.GeoFeatureName, alias.CountryName, alias.Latitude, alias.Longitude, "
				+ "alias.PhoneNumber, AttractionAddress.StreetAddr, AttractionAddress.Country, "
				+ "AttractionAddress.PostCode "
				+ "FROM AttractionAddress RIGHT JOIN "
				+ "(SELECT GeographicalFeature.AttractionID, GeographicalFeature.GeoFeatureName, "
				+ "GeographicalFeatureCountry.CountryName, GeographicalFeature.Latitude, "
				+ "GeographicalFeature.Longitude, GeographicalFeature.PhoneNumber FROM GeographicalFeature, "
				+ "GeographicalFeatureCountry WHERE GeographicalFeature.AttractionID = "
				+ "GeographicalFeatureCountry.AttractionID) alias "
				+ "ON alias.AttractionID = AttractionAddress.AttractionID;");

		while (attractionsResults.next()) {
			attractionsAL.add(new Attraction(attractionsResults.getInt("AttractionID"), 
					attractionsResults.getString(2), attractionsResults.getString("CountryName"), 
					attractionsResults.getInt("Latitude"), attractionsResults.getInt("Longitude"), 
					attractionsResults.getLong("PhoneNumber"), null,  attractionsResults.getString("StreetAddr"), null, 
					attractionsResults.getString("Country"), attractionsResults.getInt("postCode")));
		}
		
		// add restaurants
		attractionsResults = statement.executeQuery(
				"SELECT alias.AttractionID, alias.AttractionName, alias.CityName, alias.Latitude, alias.Longitude, "
				+ "alias.PhoneNumber, AttractionAddress.StreetAddr, AttractionAddress.Country, "
				+ "AttractionAddress.PostCode "
				+ "FROM AttractionAddress RIGHT JOIN "
				+ "(SELECT AttractionID, RestaurantName AS AttractionName, CityName, Latitude, Longitude, PhoneNumber "
				+ "FROM Restaurant) alias "
				+ "ON alias.AttractionID = AttractionAddress.AttractionID;");

		while (attractionsResults.next()) {
			attractionsAL.add(new Attraction(attractionsResults.getInt("AttractionID"), 
					attractionsResults.getString(2), attractionsResults.getString("CityName"), 
					attractionsResults.getInt("Latitude"), attractionsResults.getInt("Longitude"), 
					attractionsResults.getLong("PhoneNumber"), "Restaurant",  
					attractionsResults.getString("StreetAddr"), attractionsResults.getString("Country"), null, 
					attractionsResults.getInt("postCode")));
		}
		
		// add performance venues
		attractionsResults = statement.executeQuery(
				"SELECT alias.AttractionID, alias.AttractionName, alias.CityName, alias.Latitude, alias.Longitude, "
				+ "alias.PhoneNumber, AttractionAddress.StreetAddr, AttractionAddress.Country, "
				+ "AttractionAddress.PostCode "
				+ "FROM AttractionAddress RIGHT JOIN "
				+ "(SELECT AttractionID, VenueName AS AttractionName, CityName, Latitude, Longitude, PhoneNumber "
				+ "FROM PerformanceVenue) alias "
				+ "ON alias.AttractionID = AttractionAddress.AttractionID;");

		while (attractionsResults.next()) {
			attractionsAL.add(new Attraction(attractionsResults.getInt("AttractionID"), 
					attractionsResults.getString(2), attractionsResults.getString("CityName"), 
					attractionsResults.getInt("Latitude"), attractionsResults.getInt("Longitude"), 
					attractionsResults.getLong("PhoneNumber"), "Performance Venue",  
					attractionsResults.getString("StreetAddr"), attractionsResults.getString("Country"), null, 
					attractionsResults.getInt("postCode")));
		}
		
		// add airports
		attractionsResults = statement.executeQuery(
				"SELECT alias.AttractionID, alias.AttractionName, alias.CityName, alias.Latitude, alias.Longitude, "
				+ "alias.PhoneNumber, AttractionAddress.StreetAddr, AttractionAddress.Country, "
				+ "AttractionAddress.PostCode "
				+ "FROM AttractionAddress RIGHT JOIN "
				+ "(SELECT AttractionID, AirportName AS AttractionName, CityName, Latitude, Longitude, PhoneNumber "
				+ "FROM Airport) alias "
				+ "ON alias.AttractionID = AttractionAddress.AttractionID;");

		while (attractionsResults.next()) {
			attractionsAL.add(new Attraction(attractionsResults.getInt("AttractionID"), 
					attractionsResults.getString(2), attractionsResults.getString("CityName"), 
					attractionsResults.getInt("Latitude"), attractionsResults.getInt("Longitude"), 
					attractionsResults.getLong("PhoneNumber"), "Airport",  attractionsResults.getString("StreetAddr"), 
					attractionsResults.getString("Country"), null, attractionsResults.getInt("postCode")));
		}
		System.out.println(attractionsAL.size());
		return attractionsAL;
	}

	/**
	 * @return
	 */
	public int getAttractionID() {
		return attractionID;
	}

	/**
	 * @param attractionID
	 */
	public void setAttractionID(int attractionID) {
		this.attractionID = attractionID;
	}

	/**
	 * @return
	 */
	public String getAttractionName() {
		return attractionName;
	}

	/**
	 * @param attractionName
	 */
	public void setAttractionName(String attractionName) {
		this.attractionName = attractionName;
	}

	/**
	 * @return
	 */
	public String getCityName() {
		return cityName;
	}

	/**
	 * @param cityName
	 */
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	/**
	 * @return
	 */
	public int getLatitude() {
		return latitude;
	}

	/**
	 * @param latitude
	 */
	public void setLatitude(int latitude) {
		this.latitude = latitude;
	}

	/**
	 * @return
	 */
	public int getLongitude() {
		return longitude;
	}

	/**
	 * @param longitude
	 */
	public void setLongitude(int longitude) {
		this.longitude = longitude;
	}

	/**
	 * @return
	 */
	public long getPhoneNumber() {
		return phoneNumber;
	}

	/**
	 * @param phoneNumber
	 */
	public void setPhoneNumber(long phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	/**
	 * @return
	 */
	public String getWebsite() {
		return website;
	}

	/**
	 * @param website
	 */
	public void setWebsite(String website) {
		this.website = website;
	}

	/**
	 * @return
	 */
	public static ArrayList<Attraction> getAttractionsAL() {
		return attractionsAL;
	}

	/**
	 * @param attractionsAL
	 */
	public static void setAttractionsAL(ArrayList<Attraction> attractionsAL) {
		Attraction.attractionsAL = attractionsAL;
	}

	/**
	 * @return
	 */
	public String getStreetAddr() {
		return streetAddr;
	}

	/**
	 * @param streetAddr
	 */
	public void setStreetAddr(String streetAddr) {
		this.streetAddr = streetAddr;
	}

	/**
	 * @return
	 */
	public String getStateName() {
		return stateName;
	}

	/**
	 * @param stateName
	 */
	public void setStateName(String stateName) {
		stateName = stateName;
	}

	/**
	 * @return
	 */
	public int getPostalCode() {
		return postalCode;
	}

	/**
	 * @param postalCode
	 */
	public void setPostalCode(int postalCode) {
		this.postalCode = postalCode;
	}

	/**
	 * @return
	 */
	public String getCountryName() {
		return countryName;
	}

	/**
	 * @param countryName
	 */
	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}

	/**
	 * @return
	 */
	public String getAttractionType() {
		return attractionType;
	}

	/**
	 * @param attractionType
	 */
	public void setAttractionType(String attractionType) {
		this.attractionType = attractionType;
	}

}
