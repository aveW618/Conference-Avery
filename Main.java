/**
 * @author Avery Wang
 * @since November 19, 2026
 * Inside & Out Conference Project 
 * Purpose: To run the option menu for my conference seating project
 */

import java.util.*;
import java.io.*;

public class Main {
	//the main method to run the program
    public static void main(String[] args) throws FileNotFoundException{
		//gets user input
		Scanner scan = new Scanner(System.in);
		//creates a new Conference object to run in the main method
		Conference c1 = new Conference();
		
		//prints out the beginning of the program for a pretty user interface
        System.out.println("========================================");
        System.out.println("Inside & Out Conference Seating Manager");
        System.out.println("========================================");
        
        //calls the loadFiles and seatAll methods on the c1 object
        c1.loadFiles();
        c1.seatAll();
        
        //nulll value for choice at the beginning
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
				
				//assign the guest to a list of available companies
                System.out.println("Available company IDs:");
                c1.printCompanyList();
                
                //actually stores the company ID inputted by the user
                int companyId = readInt(scan, "Enter Company ID from above available list: ");
                //adds a new guest object to the arrayList
                c1.addGuest(first, last, companyId);
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
	
    // method to print out a menu of options for the program
	public static void showMenu() {
       System.out.println("--------- Menu ---------");
       System.out.println("1. Add a guest");
       System.out.println("2. Print rosters by table");
       System.out.println("3. Print rosters by company");
       System.out.println("4. Search for an attendee");
       System.out.println("5. Reseat all attendees");
       System.out.println("0. Exit the program");
    }
    
    //reads one line of text from the user
    public static String readText(Scanner scan, String prompt) {
		//prints out the prompt to read
        System.out.print(prompt);
        return scan.nextLine();
    }

    //reads a whole number from the user
    public static int readInt(Scanner scan, String prompt) {
		//prints out the prompt to be read
        System.out.print(prompt);
        int number = scan.nextInt();
        scan.nextLine();
        return number;
    }
}
