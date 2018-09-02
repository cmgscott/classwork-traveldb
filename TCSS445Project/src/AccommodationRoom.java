import java.sql.Date;

/**
 * @author CMGS
 *
 */
public class AccommodationRoom {
	
	long attractionID;
	int roomID, beds, sleeps, floorNumber, price, isAvailable;
	String roomView;
	Date date;
	
	/**
	 * @param aid
	 * @param rid
	 * @param be
	 * @param sl
	 * @param fl
	 * @param pr
	 * @param isa
	 * @param rv
	 * @param d
	 */
	public AccommodationRoom(long aid, int rid, int be, int sl, int fl, int pr, int isa, String rv, Date d) {
		attractionID = aid;
		roomID = rid;
		beds = be;
		sleeps = sl;
		floorNumber = fl;
		price = pr;
		isAvailable = isa;
		roomView = rv;
		date = d;
	}

}
