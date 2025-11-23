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
		
		//skips over a header line if it exists
		if (scan.hasNextLine()) {
			scan.nextLine();
		}
		
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
		
		//actually scan the guest data to create Attendee objects
		Scanner scan = new Scanner(guestFile);
		int i = 0;
		
		//skips header line if it exists
		if (scan.hasNextLine()) {
			scan.netLine();
		}
		
		//read each line of the guest file to create a 1D array of Attendee objects
		while (scan.hasNextLine()) {
			String line = scan.nextLine();
			
			//splits the lines by commas 
			String[] guestData = line.split(",");
			
			//stores the data from the split array
			String firstName = guestData[1];
			String lastName = guestData[2];
			int companyNumber = Integer.parseInt(guestData[3]);
			
			//create a new Attendee object and add it to the array for guests
			Attendee a = new Attendee(firstName, lastName, companyNumber);
			items[i] = a;
			i++;
		}
		scan.close();
	}
	
	//method to manually register new attendees
		//asks user for info and adds it to the guest array
	public void manualRegistration() {
		//Scanner method to obtain user input
		Scanner getGuestName = new Scanner(System.in);
		//prints out instructions and stores user input
		System.out.println("Register New Attendee");
		
		System.out.print("First Name: ");
		String firstName = scan.nextLine()
		
		System.out.print("Last name: ");
		
		System.out.print("Company number (1-16): ");
		int companyNumber = Integer.parseInt(scan.nextLine());
		
		//create a new Attendee object and add it to the guest array
		Attendee a = new Attendee(firstName, lastName, companyNumber);
		items[i] = a;
		i++;
	}

}

	
	
	
	

		
	
	
	
	
	
	
