/**
 * Avery Wang
 * April 26, 2026
 * Inside & Out Conference Project 
 * Purpose: To run the option menu for my conference seating project
 */

import java.io.*;
import java.util.*;

public class Main {
	//declaring instance variables
    private static int tableCount = 10;
    private static int seatsPerTable = 10;
    private static int maxCompanies = 16;
    private static int maxGuests = tableCount * seatsPerTable;
    private static int maxGuestsPerCompany = tableCount;
    
    //declares the files to be imported
    private static String companyFile = "companies.txt";
    private static String guestFile = "confGuests.txt";

	//creates the array lists and arrays that will be used later on
    private static ArrayList<Company> companies = new ArrayList<Company>();
    private static ArrayList<Guest> guests = new ArrayList<Guest>();
    private static Guest[][] tables = new Guest[tableCount][seatsPerTable];
    
	//the main method to run the program
    public static void main(String[] args) throws FileNotFoundException {
		Scanner scan = new Scanner(System.in);
		//prints out the beginning of the program for a pretty user interface
        System.out.println("========================================");
        System.out.println("Inside & Out Conference Seating Manager");
        System.out.println("========================================");
        
        System.out.println("Loaded " + companies.size() + " companies and " + guests.size() + " guests.");
        System.out.println("Guests have been all been seated.\n");
        
           //prints out a menu of options
	}	
	 public static void showMenu() {
        System.out.println("--------- Menu ---------");
        System.out.println("1. Add a guest");
        System.out.println("2. Print rosters by table");
        System.out.println("3. Print rosters by company");
        System.out.println("4. Search for a guest");
        System.out.println("5. Re-seat all guests");
        System.out.println("0. Exit the program");
    }
