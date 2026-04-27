//Avery Wang
//April 26, 2026
//Conference Project

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

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
	}
}	
