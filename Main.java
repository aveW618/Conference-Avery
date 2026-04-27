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
    
	public static void main(String[] args) {
		Conference c1 = new Conference();
		c1.readFile();
		c1.manualRegistration();
	}
}	
