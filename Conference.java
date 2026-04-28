/**
 * @author Avery Wang
 * @since November, 19, 2026
 * Inside & Out Conference Project
 * Purpose: Manages the main seating program by loading company and guest data, 
 * assigning attendees to tables/seats, satisfying the background requirements, and printing rosters
 */

						//add multiline documentation before each method*******

//imports Java libraries
import java.io.*;
import java.util.*;

public class Conference {
	
	//declaring instance variables
		//ask for user input for these values
    private int tableCount;
    private int seatsPerTable;
    private int maxCompanies;
    private int maxGuests;
    private int maxGuestsPerCompany;
    
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
    public Conference(int tableCount, int seatsPerTable, int maxCompanies) {
		//stores the user-inputted conference size values (no magic numbers)
		this.tableCount = tableCount;
		this.seatsPerTable = seatsPerTable;
        this.maxCompanies = maxCompanies;
        this.maxGuests = tableCount * seatsPerTable;
        this.maxGuestsPerCompany = tableCount;
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
			//skip lines that are empty
			if (line.

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
	public boolean canAdd(int companyId) {
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
    
    //method to seat all guests (with the largest companies seated first, more difficult)
    public void seatAll() {
		//calls to method to make sure all seats are empty
        clearSeats();
        //an array ranking the order by which companies will be seated
        int[] order = companyOrder();
        //loops through the companies and their guests, obtaining the guest object information
			//and then if the guest is from the company we are currently placing, display that guests have been seated
        for (int i = 0; i < order.length; i++) {
            for (int j = 0; j < guests.size(); j++) {
                Attendee guest = guests.get(j);
                if (guest.getCompanyId() == order[i] && !seatOne(guest)) {
                    quit("Could not seat all guests.");
                }
            }
        }
        System.out.println("Guests have been seated.");
    }
    
    //method that returns company IDs ordered by number of guests, largest first
    public int[] companyOrder() {
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
    
    /*
     * method that seats one guest at the least-full table that satisfies other seating conditions
     * the method reads in a guest object, inputted by the user
     */
    public boolean seatOne(Attendee guest) {
		//starting with a "empty" value for the number of the best table 
        int bestTable = -1;
        // starts 1 above the max table size so any satisfactory table is smaller
        int bestSize = seatsPerTable + 1;
        //loops through each of the tables of the conference
			//finds the table size for each of those tables
        for (int table = 0; table < tableCount; table++) {
            int size = tableSize(table);
            //makes sure the size is less than 1 more than the max
				//calls the tableHasCompany method to check and make sure a company isn't already located at a table
            if (size < bestSize && !tableHasCompany(table, guest.getCompanyId())) {
                bestTable = table;
                bestSize = size;
            }
        }
        //table is still empty, no one has been seated
        if (bestTable == -1) {
            return false;
        }
        //loops through each of the seats setting the guests to the seat at the best table for them
        for (int seat = 0; seat < seatsPerTable; seat++) {
            if (tables[bestTable][seat] == null) {
                tables[bestTable][seat] = guest;
                //stores the seating of the guest object as a normal number instead of in array index form
                guest.setSeat(bestTable + 1, seat + 1);
                return true;
            }
        }
        return false;
    }
            
            
    //method that prints all table rosters
    public void printByTable() {
		//loops through each table
        for (int table = 0; table < tableCount; table++) {
			//prints out table number
            System.out.println("Table " + (table + 1));
            //loops through each seat at the current table
            for (int seat = 0; seat < seatsPerTable; seat++) {
				//obtains the guest sitting at the seat and table
                Attendee guest = tables[table][seat];
                //prints that the seat is empty if there is no guest
                if (guest == null) {
                    System.out.println("Seat " + (seat + 1) + ": empty");
                } 
                //otherwise, print's the guest's name and company
                else {
                    System.out.println("Seat " + (seat + 1) + ": " + guest.getFullName()
                            + " - " + companyName(guest.getCompanyId()));
                }
            }
            System.out.println();
        }
    }
    
    //method that prints all company rosters
    public void printByCompany() {
		//loops through each company (companyId)
        for (int i = 0; i < companyIds.size(); i++) {
            int companyId = companyIds.get(i);
            System.out.println(companyNames.get(i));
            //goes through each guest and checks if they are part of the current company
            for (int j = 0; j < guests.size(); j++) {
                Attendee guest = guests.get(j);
                if (guest.getCompanyId() == companyId) {
					//if so, print out the guest name and their seat
                    System.out.println(guest.getFullName() + " - " + guest.seatText());
                }
            }
            System.out.println();
        }
    }
    
    //method that allows users to manually search guests by first name or last name
    public void search(String text) {
		//converts any inputted text to lowercase
        String search = text.toLowerCase();
        boolean found = false;
        //loops through the arrayList of guests
        for (int i = 0; i < guests.size(); i++) {
            Attendee guest = guests.get(i);
            //converts the guest's obtained name to lowercase for better comparison
            String full = guest.getFullName().toLowerCase();
            //also gets the guest info with last name first than first name
            String reverse = (guest.getLastName() + " " + guest.getFirstName()).toLowerCase();
            //if the inputted text has an index within the full name or the revered name, print out the guest's name, company name (based on company Id) and seat
            if (full.indexOf(search) >= 0 || reverse.indexOf(search) >= 0) {
                System.out.println(guest.getFullName() + " - " + companyName(guest.getCompanyId())
                        + " - " + guest.seatText());
                //boolean that ends the loop
                found = true;
            }
        }
        if (!found) {
            System.out.println("No guest found.");
        }
    }
            
    //prints company IDs for reference during manual registration
		//guest can be assigned to the correct company
    public void printCompanyList() {
        for (int i = 0; i < companyIds.size(); i++) {
            System.out.println(companyIds.get(i) + " = " + companyNames.get(i));
        }
    }
    
    //method to clear all table seats (before seating guests)
    private void clearSeats() {
		//loops through the table and seats and sets everything to null
        for (int table = 0; table < tableCount; table++) {
            for (int seat = 0; seat < seatsPerTable; seat++) {
                tables[table][seat] = null;
            }
        }
        //clears any seating already assigned to each guest
        for (int i = 0; i < guests.size(); i++) {
            guests.get(i).clearSeat();
        }
	}
            
    //checks if a table already has a guest from the company being tested
    private boolean tableHasCompany(int table, int companyId) {
        for (int seat = 0; seat < seatsPerTable; seat++) {
            Attendee guest = tables[table][seat];
            //checks to see if the any of the guests' companyID matches the companyID being tested
				//if so, return that the table cannot seat that guest because a coworker from the same is already seated
            if (guest != null && guest.getCompanyId() == companyId) {
                return true;
            }
        }
        return false;
    }
    
    //counts filled seats at one table
    private int tableSize(int table) {
        int count = 0;
        //loop that increments the counter variable if the seat at a table is not null/empty
        for (int seat = 0; seat < seatsPerTable; seat++) {
            if (tables[table][seat] != null) {
                count++;
            }
        }
        return count;
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
    
    //method that finds the index for a company ID
    private int companyIndex(int companyId) {
		//loops through the companyIds to find the index where the searched companyID matches the tested companyID
        for (int i = 0; i < companyIds.size(); i++) {
            if (companyIds.get(i) == companyId) {
                return i;
            }
        }
        //if the company ID does not have an index (does not exist yet) return null value (-1)
        return -1;
    }
    
    //returns the company name for a company ID
    private String companyName(int companyId) {
        int index = companyIndex(companyId);
        if (index == -1) {
            return "Unknown Company";
        }
        return companyNames.get(index);
    }

    //method that prints an error and quits the program if necessary
    private void quit(String message) {
        System.out.println("Error: " + message);
        System.exit(1);
    }
}
