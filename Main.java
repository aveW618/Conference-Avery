//Avery Wang
//April 26, 2026
//Conference Project

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
	//declaring instance variables
    private static int TABLE_COUNT = 10;
    private static int SEATS_PER_TABLE = 10;
    private static int MAX_COMPANIES = 16;
    private static int MAX_GUESTS = TABLE_COUNT * SEATS_PER_TABLE;
    private static int MAX_GUESTS_PER_COMPANY = TABLE_COUNT;
    
	public static void main(String[] args) {
		Conference c1 = new Conference();
		c1.readFile();
		c1.manualRegistration();
	}
}	
