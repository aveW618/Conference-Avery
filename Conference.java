/**
 * Avery Wang
 * April 26, 2026
 * Inside & Out Conference Project
 * Purpose: loads guests and companies, seats attendees, prints rosters, and searches guests (performs the main functions of the program)
 */

//imports Java libraries
import java.io.*;
import java.util.*;

public class Conference {
	
	//declaring instance variables
    private static int tableCount = 10;
    private static int seatsPerTable = 10;
    private static int maxCompanies = 16;
    private static int maxGuests = tableCount * seatsPerTable;
    private static int maxGuestsPerCompany = tableCount;
    
    //declares the files to be imported
    private static String companyFile = "companies.txt";
    private static String guestFile = "confGuests.txt";
    
    //declarations of any arraylists and arrays needed to store changing data about the attendees
		//info including companyIds, companyNames, etc.
	private ArrayList<Integer> companyIds;
    private ArrayList<String> companyNames;
    private ArrayList<Attendee> guests;
    private Attendee[][] tables;
    private int nextGuestId;
    
    //creates an empty conference
    public Conference() {
		//actually initializing the arrayLists and arrays I created from above into a new and empty Conference
			//basis for me to build up on later 
        companyIds = new ArrayList<Integer>();
        companyNames = new ArrayList<String>();
        guests = new ArrayList<Attendee>();
        tables = new Attendee[tableCount][seatsPerTable];
        nextGuestId = 1;
    }

	//loads the default company and guest files
		//prints out that info to inform the user about the original data
    public void loadFiles() throws FileNotFoundException {
        loadCompanies();
        loadGuests();
        System.out.println("Loaded " + companyIds.size() + " companies and " + guests.size() + " guests.");
    }
	
	//loads company IDs and names from the companies.txt file
	public void loadCompanies() throws FileNotFoundException {
		File companyFile = new File("companies.txt");
		Scanner scan = new Scanner(companyFile);

		while (scan.hasNextLine() && companyIds.size() < maxCompanies) {
			String line = scan.nextLine();

			// Splits the line by commas
			String[] companyData = line.split(",");

			// Stores the data from the split array
			int companyID = Integer.parseInt(companyData[0]);
			String companyName = companyData[1];

			// Adds the company ID and name to the lists
			companyIds.add(companyID);
			companyNames.add(companyName);
		}
		scan.close();
	}
	

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

	
	
	
	

		
	
	
	
	
	
	
