/**
 * Avery Wang
 * April 26, 2026
 * Inside & Out Conference Project
 * Purpose: stores the conference attendee and his/her seat
 */
public class Attendee {
	//declaring variables for attendee information
	private int id;
    private String firstName;
    private String lastName;
    private int companyId;
    private int tableNumber;
    private int seatNumber;
	
	/* Constructor to create an Attendee object
	 * takes user ID, first name, last name, and company ID/number
	 */
	public Attendee(int id, String firstname, String lastname, int companyId) {
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.companyId = companyId;
		clearSeat();
	}
	
	//a method to clear the attendee's seat
		//-1 values to show the seats are empty
	public void clearSeat() {
        tableNumber = -1;
        seatNumber = -1;
    }




	/*
	 * getters for the atrributes
	 * returns the user ID, first name, last name, and company number
	 */
	public int getUserID() {
		return userID;
	}
	public String getFirstName() {
		return firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public int getCompanyNumber() {
		return companyNumber;
	}
	
	public void toString() {
		System.out.println("Attendee ID: " + this.userID + "Attendee name: " + this.firstname + " " + this.lastname + "Company number: " + this.companyNumber);
	}
}
	
	
