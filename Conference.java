//Avery Wang
//Conference Project

//load in external files
import java.io.*;
import java.util.*;

public class Conference {
	//check if there are no more than 10 people from each company
	//check if there are no more than 100 people in attendance total
	//for loop to iterate through all of the guests and find those guests of company 1 and then split them into a separate array
		//then place them one by one in each of the table arrays
			//repeat for the remaining companies
	//add a user registration place
		//use the Scanner function
			//repeat the seating function above if the total number of guests is < than 100
				//if there still exists !filled table arrays
					//if the table arrays only have one person per company
	
	//declaring variables
	int maxTables = 10;
	int seatsPerTable = 10;
	int maxAttendees = 100;
	int maxCompanies = 16;
	int attendeesPerCompany;
	
	//delclaring an array to hold all attendees
	Attendee[] items;
	String filename = "confGuests.txt";
	int numLines = 0;
	
	//method to read in the guest data
		//use throws IOException because the method may throw an exception that needs to be accounted for
	public void readFile() throws IOException {
		//reads in the confGuests file bto determine how many lines there are 
			//to help with how big the guest array should be
		File guestFile = new File(filename);
		Scanner numLinesscan = new Scanner(guestFile);
		int numLines = 0;
		
		//counts the number of lines in the file to help determine array size
		while (numLinesscan.hasnextLine()) {
			numLinesscan.nextLine();
			numLines++;
		}
		numLinesscan.close();
		
		//arithmetic to help create an array with a 1.5 growth factor for in-person registrations
		int arraySize = (int)(lineCount * 1.5);
		//creates the array
		items = new Attendee[arraySize];
		
		//loop while there is a next line to create arrays for each attendee
		while (scan.hasNextLine()) {
			String line = scan.nextLine();
			//saves the substrings that have been split along the commas from the file into the array guestData
			String[] guestData = line.split(",");
			
			String firstName = guestData[1];
			String lastName = guestData[2];
			int companyNumber = Integer.parseInt(guestData[3]);
			
			Attendee a = new Attendee(firstName, lastName, companyNumber);
			items[i] = a;
			i++;
		}
		scan.close();
	}
		
		File guestsFile = new File("confGuests.txt");
		Scanner guestScan = new Scanner(guestsFile);
		String[] guestsPresent = new String[i];
		int n = guestsPresent.length;
		Conference[] attendees = new Conference[(1.5 * Double.parseDouble(n))];
		
		while (scan.hasNextLine() && i < 100) {
			Conference c = new Conference(firstNames, lastNames, companyNumber);
			companiesPresent[i] = c;
			i++;
		}
		scan.close();
	}
	
	for (int i = 0; i < 100; i++) {
		Conference[] companyNumber = new Conference[16];
	
	public void company1() {
		Conference[] company1Members = companyNumbers[0];
		
	
	
}

	
	
	
	

		
	
	
	
	
	
	
