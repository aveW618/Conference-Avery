import.java.io.companies;
import.java.io.confGuests;
public class Conference {
	//load in external files
	//check if there are no more than 10 people from each company
	//check if there are no more than 100 people in attendance total
	//for loop to iterate through all of the guests and find those guests of company 1 and then split them into a separate array
		//then place them one by one in each of the table arrays
			//repeat for the remaining companies
	//add a user registration place
		//use the Scanner function
			//repeat the seating function above if the total number of guests is < than 100
				//if there still exists !filled table arrays
					//if the table arrays only have one person per company
	n = 
	Conference[] attendees = new Conference[1.5n];
	
	public void PreRegistration() throws IOException {
		File companyFile = new File("companies.txt");
		Scanner scan = new Scanner(companyFile);
		String[] companiesPresent = new String[16];
		
		int i = 0;
		while (scan.hasNext() && i < 16) {
			String line = scan.NextLine();
			companiesPresent[i] = line;
			i++;
		}
		scan.close();
		
		File guestsFile = new File("confGuests.txt");
		Scanner scan = new Scanner(guestsFile);
		String[] guestsPresent = new String[100];
		
		int i = 0;
		while (scan.hasNext() && i < 100) {
			String line = scan.NextLine();
			String[] guestData = line.split(",");
			String firstNames = guestData[1];
			String lastNames = guestData[2];
			int companyNumber = Integer.parseInt(guestData[3]);
			Conference c = new Conference(firstNames, lastNames, companyNumber);
			companiesPresent[i] = c;
			i++;
		}
		scan.close();
	}
	
	
	
	

		
	
	
	
	
	
	
