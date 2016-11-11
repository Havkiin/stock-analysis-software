/**
 * Created by C. Hennebelle.
 * Reads the user information from the file "userLoginFile.text" and parses it to store the information in an array that
 * will be accessed later to authenticate a user logging in or creating an account.
 * Checks to make sure that the user can log in if they have an account, or adds their account to the database (text file)
 * if they are new.
 * Last update: November 4, 2016.
 */

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Scanner;

public class Database{

	//For user input
	Scanner input = new Scanner(System.in);

	//List of Users with an account.
	private LinkedList<User> users;

	// The name of user text file with login information.
	String fileName = "UserLoginFile.txt";

	public Database () //Reads the file and puts the information in a linked list of users.
	{
		//a variable to store one line of the file each time.
		String line = null;

		try {
			// FileReader reads text files in the default encoding.
			FileReader fileReader = 
					new FileReader(fileName);

			// Always wrap FileReader in BufferedReader.
			BufferedReader bufferedReader = 
					new BufferedReader(fileReader);

			users = new LinkedList<User>(); //stores the user login information.

			while((line = bufferedReader.readLine()) != null) 
			{     				String[] loginInformation = line.split("[\\s,;]+"); //splits the line into 4 parts. 

				//information[1] is the username, information[3] is the password.
				users.add(new User(loginInformation[1], loginInformation[3]));

			}   

			bufferedReader.close();    // Always close files.  

		}//Catching errors
		catch(FileNotFoundException ex) {
			System.out.println(
					"Unable to open file '" + 
							fileName + "'");                
		}
		catch(IOException ex) {
			System.out.println(
					"Error reading file '" 
							+ fileName + "'");     
		}
	}

	//Checks if the user name can be used to create an account. If so, it adds it to the user database
	//and writes it to the file.
	public boolean createAccount(String log, String pass){

		for (User check : users) 
		{
			if (check.getLogin().equals(log)) 
				return false;
		}
		
		User newUser = new User(log, pass);
		users.add(newUser);
		addUserToDatabase(newUser);
		
		return true;
	}

	//Checks if the submitted login information is correct. If so, the login is successful.
	public boolean log_in(String log, String pass) 
	{
		User tempUser = new User(log, pass);

		for (User check : users)
			if (check.equals(tempUser)) 
				return true;

		return false;
	}
	
	//Method to write the user's account to the database file.
	public void addUserToDatabase(User user)
	{
		try {
			//For appending information to the file.
			BufferedWriter bufferedWriter =
					new BufferedWriter(new FileWriter("UserLoginFile.txt", true));

			bufferedWriter.write(user.toString()); //writes the new username and password to file.
			bufferedWriter.newLine();

			bufferedWriter.close();    // Always close files.
		}
		catch(IOException ex) {
			System.out.println(
					"Error writing to file '"
							+ fileName + "'");
		}
	}


}

