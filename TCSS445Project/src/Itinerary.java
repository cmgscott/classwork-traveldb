import java.sql.Date;

/**
 * @author CMGS
 *
 */
public class Itinerary {
	
	private int itineraryID;
	private String itineraryName;
	private Date startDate;
	private Date endDate;
	
	/**
	 * @param id
	 * @param n
	 */
	public Itinerary(int id, String n/*, Date sd, Date ed*/) {
		itineraryID = id;
		itineraryName = n;
		//startDate = sd;
		//endDate = ed;
	}

	/**
	 * @return
	 */
	public int getitineraryID() {
		return itineraryID;
	}

	/**
	 * @param itineraryID
	 */
	public void setitineraryID(int itineraryID) {
		this.itineraryID = itineraryID;
	}

	/**
	 * @return
	 */
	public String getitineraryName() {
		return itineraryName;
	}

	/**
	 * @param itineraryName
	 */
	public void setitineraryName(String itineraryName) {
		this.itineraryName = itineraryName;
	}

	/**
	 * @return
	 */
	public Date getStartDate() {
		return startDate;
	}

	/**
	 * @param startDate
	 */
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	/**
	 * @return
	 */
	public Date getEndDate() {
		return endDate;
	}

	/**
	 * @param endDate
	 */
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

}
