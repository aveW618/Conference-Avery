/**
 * @author Avery Wang
 * @since November, 19, 2026
 * Inside & Out Conference Project
 * Purpose: loads guests and companies, seats attendees, prints rosters, and searches guests (performs the main functions of the program)
 */

//add multiline documentation before each method
//imports Java libraries
import java.io.*;
import java.util.*;

public class Conference {
	
	//declaring instance variables
	//ask for user input for these values, no magic numbers
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
		//loads/scans the company file
		File companyTextFile = new File(companyFile);
		Scanner scan = new Scanner(companyTextFile);
		
		//makes sure there is info to read and that the number of company Ids (stored in an arrray list)
			//is less than the max number of companies allowed for this scenario
		while (scan.hasNextLine() && companyIds.size() < maxCompanies) {
			String line = scan.nextLine();

			// Splits the line by commas (the delimiters)
			String[] companyData = line.split(",");

			// Stores the data from the split array
			int companyId = Integer.parseInt(companyData[0]);
			String companyName = companyData[1];

			// Adds the company ID and name to the array lists
			companyIds.add(companyId);
			companyNames.add(companyName);
		}
		scan.close();
	}
	
	//loads guests and their info from the confGuests.txt file
	public void loadGuests() throws FileNotFoundException {
		File guestTextFile = new File(guestFile);
		//actually scan the guest data
		Scanner scan = new Scanner(guestTextFile);
		
		//tracker of how many guests weren't able to be added/seated
		int notAdded = 0;
		
		//read each line of the guest file while there is a next line
		while (scan.hasNextLine()) {
			String line = scan.nextLine();
			
			//splits the lines by commas 
			String[] guestData = line.split(",");
			
			//stores the data from the split array
			int guestId = Integer.parseInt(guestData[0]);
			String firstName = guestData[1];
			String lastName = guestData[2];
			int companyId = Integer.parseInt(guestData[3]);
			
			//create a new Attendee object
			Attendee guest = new Attendee(guestId, firstName, lastName, companyId);
			//adds the guest if the company exists and the event/company is not full
				//calls the canAdd method
			if (canAdd(companyId)) {
				guests.add(guest);

				//updates the next available guest ID (increments it by 1)
				if (guestId >= nextGuestId) {
					nextGuestId = guestId + 1;
				}
			} 
			else {
				//increments the counter of people not added by 1
				notAdded++;
			}
		}
		scan.close();
		//prints a message if any guests could not be added
		if (notAdded > 0) {
			System.out.println(notAdded + " guest(s) could not be added because the event or company limit was reached.");
		}
	}
	
	//method to manually add a guest and seats everyone again
    public void addGuest(String first, String last, int companyId) {
		//makes sure another guest can be added (calls canAdd method) and makes sure there is a user input
        if (canAdd(companyId) && first.length() > 0 && last.length() > 0) {
			//creates a new guest object
            Attendee guest = new Attendee(nextGuestId, first, last, companyId);
            //adds that guest object to the guest Array List
            guests.add(guest);
            //increments to the next guestId
            nextGuestId++;
            //reseats everyone else
            seatAll();
            //lets the user know if they successfully added a new guest
            System.out.println("Added " + guest.getFullName() + ".");
        } 
        else {
            System.out.println("Sorry! Could not add a guest. Check the company ID and conference capacity.");
        }
    }
    
	//method that checks total capacity of the conference, company capacity, and whether the company exists
	private boolean canAdd(int companyId) {
		//checks if the company exists (if it doesn't, the program will return -1)
		if (companyIndex(companyId) == -1) {
			return false;
		}

		//checks if the whole conference is full
		if (guests.size() >= maxGuests) {
			return false;
		}

		//checks if the company already has the maximum number of guests
		if (companyCount(companyId) >= maxGuestsPerCompany) {
			return false;
		}

		//if all checks passed, the guest can be added
		return true;
	}
	
	//method to find the index of a company ID
    private int companyIndex(int companyId) {
		//loops through the arrayList of companyIds to see if any match the tested company ID
			//if so, return the index value, otherwise return -1 (i.e. null)
        for (int i = 0; i < companyIds.size(); i++) {
            if (companyIds.get(i) == companyId) {
                return i;
            }
        }
        return -1;
    }
    
    //method to seat all guests (with the largest companies seated first, more difficult)
    public void seatAll() {
		//calls to method to make sure all seats are empty
        clearSeats();
        //an array ranking the order by which companies will be seated
			//need to create a method that will actually rank the size of companies
        int[] order = companyOrder();
        //loops through the companies and their guests, obtaining the guest object information
			//and then if the guest is from the company we are currently placing, display that guests have been seated
        for (int i = 0; i < order.length; i++) {
            for (int j = 0; j < guests.size(); j++) {
                Attendee guest = guests.get(j);
                if (guest.getCompanyId() != order[i]) {
                    quit("Could not seat all guests.");
                }
            }
        }
        System.out.println("Guests have been seated.");
    }
    
    //method that returns company IDs ordered by number of guests, largest first
    private int[] companyOrder() {
		//creates an array with the size of the companyId ArrayList (number of arrays)
        int[] order = new int[companyIds.size()];
        //loops through the company Ids to set them to the corresponding position in the order array
        for (int i = 0; i < order.length; i++) {
            order[i] = companyIds.get(i);
        }
        //loop to order the companyIds by their size (largest to smallest)
        for (int i = 0; i < order.length - 1; i++) {
            int max = i;
            for (int j = i + 1; j < order.length; j++) {
                if (companyCount(order[j]) > companyCount(order[max])) {
                    max = j;
                }
            }
            int temp = order[i];
            order[i] = order[max];
            order[max] = temp;
        }
        return order;
    }
    
	//method to count the guests from one company
    private int companyCount(int companyId) {
        int count = 0;
        //loops through the guests arrayList to see how many guests match the tested companyID
			//if they do, increment the counter
        for (int i = 0; i < guests.size(); i++) {
            if (guests.get(i).getCompanyId() == companyId) {
                count++;
            }
        }
        return count;
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
