/**
 * @author Avery Wang
 * @since November, 19, 2026
 * Program: Inside & Out Conference Project
 * Purpose: Manages the main seating program by loading company and guest data, 
 * assigning attendees to tables/seats, satisfying the background requirements, and printing rosters
 */

import java.io.*;
import java.util.*;

/*
 * This class works to manage the overall conference seating system including loading data, assigning seats, 
 * printing rosters, and searching for attendees (all the choices from the user menu).
 * I struggled a lot with the overarching logic for this class and exactly what methods would I need to include 
 * in order to account for all the special conditions for seating (1 atendee from each company per table, etc.)
 */
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
    
     /*
     * creates a Conference object and initializes the guest list, company availaility, and seating chart
     */
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

	/*
	 * loads the default company and guest files
	 * also makes sure to account for speical exceptions
	 * prints out that info to inform the user about the values of the original guest size, company size, etc.
	 */
    public void loadFiles() throws FileNotFoundException {
        loadCompanies();
        loadGuests();
        System.out.println("Loaded " + companyIds.size() + " companies and " + guests.size() + " guests.");
    }
	
	/*
	 * a method that loads company information from the companies.txt file and stores each company ID with its matching company name
	 * I was getting runtime exceptions/errors so I had to add a line of code checking if there is info in the next line being scanned
	 */
	public void loadCompanies() throws FileNotFoundException {
		File companyTextFile = new File(companyFile);
		Scanner scan = new Scanner(companyTextFile);
		
		//makes sure there is info to read and that the number of company Ids (stored in an arrray list)
			//is less than the max number of companies allowed for this scenario
		while (scan.hasNextLine() && companyIds.size() < maxCompanies) {
			String line = scan.nextLine();
			//learned how to use the continue statement from W3 schools to account for any possible exceptions/errors
			if (line.isEmpty()) {
				continue;
			}
			String[] companyData = line.split(",");

			int companyId = Integer.parseInt(companyData[0]);
			String companyName = companyData[1];
		
			//adds company info into storage
			companyIds.add(companyId);
			companyNames.add(companyName);
		}
		scan.close();
	}	
	
	/* 
	 * methods that loads guest and their info from the confGuests.txt file
	 * creates Attendee objects and adds valid guests to the guest list
	 * I used a lot of the same code from my loadCompanies method (similar purposes and structures) making this part of code a bit easier
	 */
	public void loadGuests() throws FileNotFoundException {
		File guestTextFile = new File(guestFile);
		//actually scan the guest data
		Scanner scan = new Scanner(guestTextFile);
		
		int notAdded = 0;
		
		//read each line of the guest file while there is a next line
		while (scan.hasNextLine()) {
			String line = scan.nextLine();
            if (line.isEmpty()) {
				continue;
			}
			String[] guestData = line.split(",");
			
			//stores the split data from the guest file
			int guestId = Integer.parseInt(guestData[0]);
			String firstName = guestData[1];
			String lastName = guestData[2];
			int companyId = Integer.parseInt(guestData[3]);
			
			Attendee guest = new Attendee(guestId, firstName, lastName, companyId);
			//adds the guest if the company exists and the event/company is not full
				//calls the canAdd method
			if (canAdd(companyId)) {
				guests.add(guest);
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
		if (notAdded > 0) {
			System.out.println(notAdded + " guest(s) could not be added because the event or company limit was reached.");
		}
	}
	
	/*
	 * Method to manually add a guest and seats everyone again
	 * Difficult because it calls on multiple methods which I had to spend a lot of time to plan out and write
	 * Actually bringing together all the previously written components was the fun part 
	 */
    public void addGuest(String first, String last, int companyId) {
		//makes sure another guest can be added (calls canAdd method) and makes sure there is a user input
        if (canAdd(companyId) && first.length() > 0 && last.length() > 0) {
            Attendee guest = new Attendee(nextGuestId, first, last, companyId);
            //adds guest object to the guest Array List
            guests.add(guest);
            nextGuestId++;
            seatAll();
            System.out.println("Added " + guest.getFullName() + ".");
        } 
        else {
            System.out.println("Sorry! Could not add a guest. Check the company ID and conference capacity.");
        }
    }
    
    /*
     * method to manually add a new company to the company list (part of the user menu)
     * Parameters: company ID and company name
     * Returns false if the company can't be added, already exists, or was inputted wrong
     * Otherwise returns true
     * I originally only accounted for the max amount of companies able to be added
     * Later, I added in tests to see if the user inputted any info and if that info is not repeated
     */
    public boolean addCompany(int companyId, String companyName) {
		//checks if there is room to create another company
        if (companyIds.size() >= maxCompanies) {
            System.out.println("Sorry! Could not add a company. The company limit was reached.");
            return false;
        }
        if (companyIndex(companyId) != -1) {
			System.out.println("Sorry! That company ID already exists.");
			return false;
		}

		if (companyName.length() == 0) {		
		System.out.println("Sorry! Company name cannot be empty.");
			return false;
		}
		
        //adds the new company ID and name to the array lists
        companyIds.add(companyId);
        companyNames.add(companyName);
        System.out.println("Added company " + companyName + ".");
        return true;
    }
        
	/*
	 * method that checks total capacity of the conference, company capacity, and whether the company exists
	 * See if another guest can be added
	 * Parameters: the company ID being checked
	 * Return: true if a guest can be added, and false otherwise
	 */
	public boolean canAdd(int companyId) {
		if (companyIndex(companyId) == -1) {
			return false;
		}
		if (guests.size() >= maxGuests) {
			return false;
		}
		if (companyCount(companyId) >= maxGuestsPerCompany) {
			return false;
		}
		return true;
	}
    
    /*
     * method to seat all guests (with the largest companies seated first, more difficult to fit)
     * I needed to think about first the order I would seat guests and also how I would first create that order (a challenge)
     */
    public void seatAll() {
        clearSeats();
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
    
    /*
     * method that returns company IDs ordered by number of guests, largest first
     * helps with the method that seats the guests
     * returns the array of companyIDs
     */
    public int[] companyOrder() {
		//creates an array with the size of the companyId ArrayList (number of arrays)
        int[] order = new int[companyIds.size()];
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
     * Parameters: a guest object
     * Returns true if the best table has been found, false otherwise
     */
    public boolean seatOne(Attendee guest) {
        int bestTable = -1;
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
        //loops through each of the seats setting, the guests to the seat at the best table for them
        for (int seat = 0; seat < seatsPerTable; seat++) {
            if (tables[bestTable][seat] == null) {
                tables[bestTable][seat] = guest;
                //stores the seating of the guest object as a normal number instead of an array index form
                guest.setSeat(bestTable + 1, seat + 1);
                return true;
            }
        }
        return false;
    }
            
            
    /*
     * method that prints the seating roster organized by table
     * each seat is either shown with the assigend attendee or listed as empty
     */
    public void printByTable() {
        for (int table = 0; table < tableCount; table++) {
            System.out.println("Table " + (table + 1));
            for (int seat = 0; seat < seatsPerTable; seat++) {
                Attendee guest = tables[table][seat];
                //prints that the seat is empty if there is no guest
                if (guest == null) {
                    System.out.println("Seat " + (seat + 1) + ": empty");
                } 
                else {
                    System.out.println("Seat " + (seat + 1) + ": " + guest.getFullName()
                            + " - " + companyName(guest.getCompanyId()));
                }
            }
            System.out.println();
        }
    }
    
    /*
     * method that prints all company rosters so that the user can see which attendees belong to each company in an easier way
     */
    public void printByCompany() {
        for (int i = 0; i < companyIds.size(); i++) {
            int companyId = companyIds.get(i);
            System.out.println(companyNames.get(i));
            //goes through each guest and checks if they are part of the current company
            for (int j = 0; j < guests.size(); j++) {
                Attendee guest = guests.get(j);
                if (guest.getCompanyId() == companyId) {
                    System.out.println(guest.getFullName() + " - " + guest.seatText());
                }
            }
            System.out.println();
        }
    }
    
    /*
     * method that allows users to manually search guests by first name or last name (or both)
     * Parameters: the text of the name entered by the user
     */
    public void search(String text) {
		//converts any inputted text to lowercase
        String search = text.toLowerCase();
        boolean found = false;
        for (int i = 0; i < guests.size(); i++) {
            Attendee guest = guests.get(i);
            String full = guest.getFullName().toLowerCase();
            String reverse = (guest.getLastName() + " " + guest.getFirstName()).toLowerCase();
            //if the inputted text has an index within the full name or the revered name, print out the guest's name, company name (based on company Id) and seat
            if (full.indexOf(search) >= 0 || reverse.indexOf(search) >= 0) {
                System.out.println(guest.getFullName() + " - " + companyName(guest.getCompanyId())
                        + " - " + guest.seatText());
                found = true;
            }
        }
        if (!found) {
            System.out.println("No guest found.");
        }
    }
            
    /*
     * method that prints a list of company IDs for reference during manual registration
     * better readlibility for users
     */
    public void printCompanyList() {
        for (int i = 0; i < companyIds.size(); i++) {
            System.out.println(companyIds.get(i) + " = " + companyNames.get(i));
        }
    }
    
    /*
     * method to clear the entire seating chart
     * also removes each attendee's stored table and seat assignment
     */
    private void clearSeats() {
		//loops through the table and seats and sets everything to null
        for (int table = 0; table < tableCount; table++) {
            for (int seat = 0; seat < seatsPerTable; seat++) {
                tables[table][seat] = null;
            }
        }
        for (int i = 0; i < guests.size(); i++) {
            guests.get(i).clearSeat();
        }
	}
            
    /*
     * a method that checks if a table already has a guest from the company being tested
     * Parameters: the table number and company ID
     * Returns true if there already is a guest from the same company sitting at a table and false otherse
     */
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
    
    /* 
     * method that counts filled seats at one table
     * Parameter is the table number
     * Returns an int value for the number of filled seats at one table
     */
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
    
	/*
	 * a method to count the guests from one company currently loaded
	 * Parameters: the company ID being counted
	 * Return: the number of guests form that company as an integer value
	 */
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
     * a method searches for a company ID in the companyID array
     * Parameters: the company ID to search for
     * Return: the index of a company if it is found and -1 if not
     */
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
    
    /* 
     * method that returns if company name that matches a company ID
     * Parameters: the company ID to look up
     * Return: the matching company as a String (or a printed message if the company ID is not found
     */
    private String companyName(int companyId) {
        int index = companyIndex(companyId);
        if (index == -1) {
            return "Unknown Company";
        }
        return companyNames.get(index);
    }

    /* 
     * a method that prints an error and quits the program if necessary
     * Parameter: a String message that shares the error
     */
    private void quit(String message) {
        System.out.println("Error: " + message);
        System.exit(1);
    }
}
