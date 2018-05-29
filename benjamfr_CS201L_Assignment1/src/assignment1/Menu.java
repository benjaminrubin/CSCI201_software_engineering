package assignment1;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import java.io.FileWriter;
import com.google.gson.GsonBuilder;

public class Menu {

	// This function writes out to the JSON file that was initially passed through
	public void writeToFile(Roster roster, String filename) {

		try (FileWriter writer = new FileWriter(filename)) {
			Gson gson = new GsonBuilder().create();
			gson.toJson(roster, writer);
		} catch (IOException ioe) {
			System.out.println("There's a problem with the output file.");
		}
	}

	public void run() {
		// objects for receiving user input
		Scanner input = new Scanner(System.in);
		FileReader fr;
		BufferedReader br;
		String buff = "";
		String filename = "";

		// reading all the data into a string
		while (true) {
			try {
				System.out.print("What is the name of the input file? ");
				filename = input.nextLine();

				fr = new FileReader(filename);
				br = new BufferedReader(fr);
				String line = br.readLine();

				while (line != null) {
					buff += line;
					line = br.readLine();
				}
				if (buff.isEmpty()) {
					System.out.println("\nFile is empty, please try another one.\n");
					continue;
				}

				// closing the reader and buffer
				// the while loop will break if input was VALID
				fr.close();
				br.close();
				break;
			} catch (FileNotFoundException fnfe) {
				System.out.println("That file could not be found");
			} catch (IOException ioe) {
				System.out.println("That file is not a well-formed JSON file, please try again.");
			}

		}

		// Creating a new Gson parser to parse buff into Roster object
		Gson gson = new Gson();
		Roster roster = null;
		try {
			roster = gson.fromJson(buff, Roster.class);
		} catch (JsonSyntaxException jsse) {
			System.out.println(jsse.getMessage());
		}

		// GSON file has been read properly, fullname variables for all
		// students must be initialized with setup to use for sorting
		// by first name and last name
		roster.setup();

		// Tracks whether user needs to save new changes to a file
		boolean saved = true;

		while (true) {

			// Display menu
			System.out.println("\n\t1) Display Roster \n" + "\t2) Add Student \n" + "\t3) Remove Student \n"
					+ "\t4) Add Grade \n" + "\t5) Sort Roster \n" + "\t6) Write File \n" + "\t7) Exit \n");

			System.out.print("What would you like to do? ");
			
			// Receive input
			String receipt;
			receipt = input.nextLine();
			int choice = -1;
			
			// Tracks validity of menu choice (i.e. must be integer)
			boolean valid = true;
			try {
				choice = Integer.parseInt(receipt);
			} catch (NumberFormatException e) {
				valid = false;
			}

			// If input is invalid, outputs error message and presents menu again
			if (!valid || choice < 0 || choice > 8) {
				System.out.println("\nThat is not a valid option \n");
				continue;
			} else {
				// 1) Display Roster
				if (choice == 1) {
					System.out.println();
					roster.print();

					// 2) Add Student
				} else if (choice == 2) {
					String[] arrOfStr = null;
					String newname = "";

					System.out.print("What is the student's name? ");
					newname = input.nextLine();
					// Collects text from string regardless of number of whitespace between them
					arrOfStr = newname.split("\\s+");
					// Must contain at least two names in the input
					if (arrOfStr.length <= 1) {
						System.out.println("Invalid, must have first and last name");
						continue;
					}
					String lastname = arrOfStr[1];

					// Accounts for middle name, or multiple names in last name
					for (int i = 2; i < arrOfStr.length; i++) {
						lastname += " " + arrOfStr[i];
					}
					roster.add(arrOfStr[0], lastname);

					// File has been edited
					saved = false;

					System.out.println();

					// 3) Remove Student
				} else if (choice == 3) {
					roster.printlist();
					System.out.print("Please choose a student to remove: ");
					// Handle invalid inputs
					String num;
					num = input.nextLine();
					int chois = -1;
					boolean val = true;
					try {
						chois = Integer.parseInt(num);
					} catch (NumberFormatException e) {
						val = false;
					}
					// If input is invalid, outputs error message
					// Input must be an integer or number between 1 and roster size
					if (!val || chois < 1 || chois > roster.students.size()) {
						System.out.println("\nThat is not a valid option");
						continue;
					}
					roster.remove(chois - 1);

					// File has been edited
					saved = false;

					// 4) Add Grade
				} else if (choice == 4) {
					roster.printlist();
					System.out.print("Please choose a student to grade: ");
					// Handle invalid inputs
					String num;
					num = input.nextLine();
					int chois = -1;
					boolean val = true;
					try {
						chois = Integer.parseInt(num);
					} catch (NumberFormatException e) {
						val = false;
					}
					// If input is invalid, outputs error message
					// Input must be an integer or number between 1 and roster size
					if (!val || chois < 1 || chois > roster.students.size()) {
						System.out.println("\nThat is not a valid option");
						continue;
					}

					System.out.print("Please enter a new score: ");
					String score;
					score = input.nextLine();
					int choi = -1;
					val = true;
					try {
						choi = Integer.parseInt(score);
					} catch (NumberFormatException e) {
						val = false;
					}
					// If input is invalid, outputs error message
					// Input must be an integer or number between 0 and 100
					if (!val || choi < 0 || choi > 100) {
						System.out.println("\nThat is not a valid grade");
						continue;
					}
					roster.setGrade((chois - 1), choi);

					// File has been edited
					saved = false;

					// 5) Sort Roster
				} else if (choice == 5) {
					System.out.print("\n\t1) Alphabetically\n\t2) GPA\n\nHow would you like to sort the roster? ");
					String dec;
					dec = input.nextLine();
					int choi = -1;
					boolean val = true;
					try {
						choi = Integer.parseInt(dec);
					} catch (NumberFormatException e) {
						val = false;
					}
					// If input is invalid, outputs error message
					// Must be either 1 or 2
					if (!val || choi < 1 || choi > 2) {
						System.out.println("\nThat is not a valid option");
						continue;
					}
					roster.sort(choi);
					System.out.println();
					roster.print();

					// File has been edited
					saved = false;

					// 6) Write File
				} else if (choice == 6) {
					writeToFile(roster, filename);

					// File has been saved
					saved = true;

					// 7) Exit Program
				} else if (choice == 7) {
					if (!saved) {
						while (true) {
							System.out.print("Changes have been made since the file was last "
									+ "saved. \n\n\t 1) Yes \n\t 2) No \n\nWould you like "
									+ "to save the file before exiting? ");
							String save;
							save = input.nextLine();
							int in = -1;
							boolean val = true;
							try {
								in = Integer.parseInt(save);
							} catch (NumberFormatException e) {
								val = false;
							}
							// If input is invalid, outputs error message
							// Must be either 1 or 2
							if (!val || in < 1 || in > 2) {
								System.out.println("\nThat is not a valid option\n");
								continue;
							} else {
								if (in == 1) {
									writeToFile(roster, filename);
								}
								break;
							}
						}
					}
					System.out.println("\nThank you for using my program!\n");
					input.close();
					System.exit(0);
				}
			}
		}

	} // End of function

	public static void main(String[] args) {

		Menu menu = new Menu();
		while (true) {
			try {
				menu.run();
			} catch (java.lang.NullPointerException npe) {
				System.out.println("\nThat file is not a well-formed JSON file. Please try again.\n");
				continue;
			}
			break;
		}
	} // End of program
}
