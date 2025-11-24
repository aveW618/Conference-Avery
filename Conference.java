//Avery Wang
//Conference Project

//load in external files
import java.io.*;
import java.util.*;

public class Conference {
	/*check if there are no more than 10 people from each company
	check if there are no more than 100 people in attendance total
	for loop to iterate through all of the guests and find those guests of company 1 and then split them into a separate array
		then place them one by one in each of the table arrays
			repeat for the remaining companies
	add a user registration place
		use the Scanner function
			repeat the seating function above if the total number of guests is < than 100
				if there still exists !filled table arrays
					if the table arrays only have one person per company
	*/
	
	//declaring variables
	int tables;
	int seatsPerTable;
	int attendees;
	int companies;
	int attendeesPerCompany;
	int attendeeCounter;
	
	//delclaring an array to hold all attendees
	Attendee[] items;
	String filename = "confGuests.txt";
	
	/*
	 * method to read in the guest data
	 * use throws IOException because the method may throw an exception that needs to be accounted for
	 * after reading in the guest array, creates a ID array with the attendee info
	*/
	public void readFile() throws IOException {
		//reads in the confGuests file bto determine how many lines there are 
			//to help with how big the guest array should be
		File guestFile = new File(filename);
		Scanner numLinesScan = new Scanner(guestFile);
		int numLines = 0;
		
		//counts the number of lines in the file to help determine array size
		while (numLinesScan.hasNextLine()) {
			numLinesScan.nextLine();
			numLines++;
		}
		numLinesScan.close();
		
		//arithmetic to help create an array with a 1.5 growth factor for in-person registrations
		int arraySize = (int)(numLines * 1.5);
		//creates the array
		items = new Attendee[arraySize];
		
		//actually scan the guest data to create Attendee objects
		Scanner scan = new Scanner(guestFile);
		int i = 0;
		
		//read each line of the guest file to create a 1D array of Attendee objects
		while (scan.hasNextLine()) {
			String line = scan.nextLine();
			
			//splits the lines by commas 
			String[] guestData = line.split(",");
			
			//stores the data from the split array
			int userID = Integer.parseInt(guestData[0]);
			String firstName = guestData[1];
			String lastName = guestData[2];
			int companyNumber = Integer.parseInt(guestData[3]);
			
			//create a new Attendee object and add it to the array for guests
			Attendee a = new Attendee(userID, firstName, lastName, companyNumber);
			items[i] = a;
			i++;
		}
		attendeeCounter = i;
		scan.close();
	}
	
	/*
	 * method to manually register new attendees
	 * loops to get user info for registering new attendees until the array is full or the user stops adding attendees
	 * adds the user's input to the guest array
	 */
	 
	public void manualRegistration() {
		//obtaining user input
		Scanner scan = new Scanner(System.in);
		
		//sets a boolean for the later while loop 
		boolean addingGuests = true;
		
		while (addingGuests && attendeeCounter < items.length) {
			//prints out instructions and stores user input
			System.out.print("Would you like to register a new attendee? (Y/N) ");
			String register = scan.nextLine();
			
			//if the user types N, change the boolean to false and exit the loop
			if (register.equals("N")) {
				addingGuests = false;
			}
			else if (register.equals("Y")) {
				System.out.print("Attendee ID: ");
				int userID = Integer.parseInt(scan.nextLine());
		
				System.out.print("First Name: ");
				String firstName = scan.nextLine();
		
				System.out.print("Last name: ");
				String lastName = scan.nextLine();
		
				System.out.print("Company number (1-16): ");
				int companyNumber = Integer.parseInt(scan.nextLine());
		
				//create a new Attendee object and add it to the guest array
				Attendee a = new Attendee(userID, firstName, lastName, companyNumber);
				items[attendeeCounter] = a;
				attendeeCounter++;
			}
		}
		//checks if the initial attendee array is full
		if (attendeeCounter >= items.length) {
			System.out.println("The conference is full. You can't add more attendees.");
	}
	}
}

	
	
	
	

		
	
	
	
	
	
	
