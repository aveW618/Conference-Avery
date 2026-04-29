/**
 * @author Avery Wang
 * @since November 19, 2026
 * Inside & Out Conference Project 
 * Purpose: Runs the user menu for the conference program; the menu allows users to add attendees, print rosters
 * search attendees, and reseat guests
 */

import java.util.*;
import java.io.*;

public class Main {
	/*
	 * Main method that runs the full conference program
	 * The method loads the files, seats the guests, and keep showing the menu until the user quits the program
	 */
    public static void main(String[] args) throws FileNotFoundException{
		//gets user input
		Scanner scan = new Scanner(System.in);
		
		//prints out the beginning of the program for a pretty user interface
        System.out.println("========================================");
        System.out.println("Inside & Out Conference Seating Manager");
        System.out.println("========================================");
        
		//reads the conference size information from the user
        int tableCount = readPositiveInt(scan, "Enter number of tables: ");
        int seatsPerTable = readPositiveInt(scan, "Enter seats per table: ");
        int maxCompanies = readPositiveInt(scan, "Enter maximum number of companies: ");
        
        //creates a new Conference object to run in the main method
		Conference c1 = new Conference(tableCount, seatsPerTable, maxCompanies);
		
        //calls the loadFiles and seatAll methods on the c1 object
        c1.loadFiles();
        c1.seatAll();
        
        //null value for choice at the beginning
        int choice = -1;
        
        //loop while a choice is inputted by the user
        while (choice != 0) {
            showMenu();
            //scanners user input
            choice = readInt(scan, "Choose: ");
            System.out.println();
        
			//if the user input is 1, manually add a guest
			if (choice == 1) {
                String first = readText(scan, "Enter attendee first name: ");
                String last = readText(scan, "Enter attendee last name: ");
				
				//lets the user choose an existing company or create a new company
                System.out.println("1. Use an existing company");
                System.out.println("2. Create a new company");
                int companyChoice = readInt(scan, "Choose company option: ");
                
                //assign the guest to a list of available companies
                if (companyChoice == 1) {
					//assign the guest to a list of available companies
					System.out.println("Available company IDs:");
					c1.printCompanyList();
                
					//actually stores the company ID inputted by the user
					int companyId = readInt(scan, "Enter Company ID from above available list: ");
					//adds a new guest object to the arrayList
					c1.addGuest(first, last, companyId);
				}
				
				//creates a new company and then assigns the geust to that company
				else if (companyChoice == 2) {
                    int companyId = readInt(scan, "Enter new Company ID: ");
                    String companyName = readText(scan, "Enter new Company name: ");
                    
                    //adds the company first, then adds the guest if the company was successfully created
                    if (c1.addCompany(companyId, companyName)) {
                        c1.addGuest(first, last, companyId);
                    }
                    //informs user if the company could be added
                    else {
                        System.out.println("Guest was not added because the new company could not be created.");
                    }
                }
                else {
                    System.out.println("Please choose a valid company option.");
                }
			}
			
			//calls the method to print the table rosters
			else if (choice == 2) {
				c1.printByTable();
				}
			//calls the method to print the company rosters
			else if (choice == 3) {
				c1.printByCompany();
			} 
			//search for a guest
			else if (choice == 4) {
				String name = readText(scan, "Search name: ");
				c1.search(name);
			} 
			//reseats all guests
			else if (choice == 5) {
				c1.seatAll();
				System.out.println("Guests were seated again.");
			} 
			//ends the program
			else if (choice == 0) {
				System.out.println("Goodbye!");
			}
			//error detected
			else {
                System.out.println("Please choose a menu option.");
			}
			System.out.println();
		}
		scan.close();
	}
	
    /*
     * Prints out the menu options that the user can choose from for the program
     */
	public static void showMenu() {
       System.out.println("--------- Menu ---------");
       System.out.println("1. Add a guest");
       System.out.println("2. Print rosters by table");
       System.out.println("3. Print rosters by company");
       System.out.println("4. Search for an attendee");
       System.out.println("5. Reseat all attendees");
       System.out.println("0. Exit the program");
    }
    
    /*
     * Reads one line of text from the user
     * This is used for names and search text snippets
     */
    public static String readText(Scanner scan, String prompt) {
		//prints out the prompt to read
        System.out.print(prompt);
        return scan.nextLine();
    }

    /*
     * Reads a whole number from the user
     * This is used for menu choices and company Ids
     */
    public static int readInt(Scanner scan, String prompt) {
		//prints out the prompt to be read
        System.out.print(prompt);
        int number = scan.nextInt();
        scan.nextLine();
        return number;
    }
    
    //Reads a positive whole number from the user, this is used for the scanning of conference size values
    public static int readPositiveInt(Scanner scan, String prompt) {
		//reads the number in
        int number = readInt(scan, prompt);
        //makes sure the number is positive before storing it
        while (number <= 0) {
            System.out.println("Please enter a positive number.");
            number = readInt(scan, prompt);
        }
        return number;
    }
}
