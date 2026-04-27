/**
 * Avery Wang
 * April 26, 2026
 * Inside & Out Conference Project 
 * Purpose: To run the option menu for my conference seating project
 */

import java.io.*;
import java.util.*;

public class Main {

    
    

	
    
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
