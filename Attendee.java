/**
 * @author Avery Wang
 * @since November 19, 2026
 * Inside & Out Conference Project
 * Purpose: Represents the info of one conference attendee including their personal information company ID, and seat assignment
 */
 
public class Attendee {
	//declaring variables for attendee information
	private int id;
    private String firstName;
    private String lastName;
    private int companyId;
    private int tableNumber;
    private int seatNumber;
	
	/* 
	 * Constructor to create an Attendee object
	 * Stores the user ID, first name, last name, and company ID/number
	 * It also clears the attendee's seat so they start without being seated
	 */
	public Attendee(int id, String firstName, String lastName, int companyId) {
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.companyId = companyId;
		clearSeat();
	}
	
	/*
	 * A method to clear the attendee's seat assignment
	 * The -1 values show that the attendee is not currently seated
	*/
	public void clearSeat() {
        tableNumber = -1;
        seatNumber = -1;
    }
    
    /*
     * Sets this attendee's assigned table and seat
     * The table and seat numbers are stored as normal numbers, not array indices
     */
    public void setSeat(int tableNumber, int seatNumber) {
        this.tableNumber = tableNumber;
        this.seatNumber = seatNumber;
    }
    
	/*
	 * getters for the atrributes
	 * returns the user ID, first name, last name, and company number
	 */
	public int getId() {
        return id;
	}
	public String getFirstName() {
		return firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public int getCompanyId() {
		return companyId;
	}
	
	//returns the attendee's full name
    public String getFullName() {
        return (firstName + " " + lastName);
    }

    /*
     * Returns the attendee's table and seat information as a text
     * If the attendee has not been seated yet, the method returns "Not Seated"
     */
    public String seatText() {
		//uses this comparison to make sure the seat is not empty
        if (tableNumber == -1) {
            return ("Not seated");
        }
        return ("Table " + tableNumber + ", Seat " + seatNumber);
    }
}
