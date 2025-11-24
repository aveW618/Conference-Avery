//Avery Wang
//Conference project

public class Attendee {
	//declaring variables for attendee information
	int userID;
	String firstName;
	String lastName;
	int companyNumber;
	
	/* Constructor to create an Attendee object
	 * takes user ID, first name, last name, company number, and user ID
	 */
	public Attendee(int id, String f, String l, int c) {
		userID = id;
		firstName = f;
		lastName = l;
		companyNumber = c;
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
}
	
	
