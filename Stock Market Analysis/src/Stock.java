import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;

/**
 * Created by S. Paquette.
 * Reads the data from the CSV file and puts it into a linked list of Data. This is used to graph historical data and show 
 * moving averages as well as the buy/sell indicators.
 * Last update: November 4, 2016.
 */

public class Stock
{
	//Input file which needs to be parsed
	String fileToParse = "Sample data.csv";
	BufferedReader fileReader = null;

	//List of Users with an account.
	private LinkedList<Data> datesWithData = new LinkedList<Data>();

	//Default constructor that will take a CSV file and start to parse it.
	public Stock()
	{
		parseCSVLineByLine(fileToParse);
	}

	//Method that parses the CSV file by placing the data into a linked list to be accessed by
	//the program later on. 
	public void parseCSVLineByLine(String file)
	{
		try
		{
			String line = "";
			//Create the file reader
			fileReader = new BufferedReader(new FileReader(file));

			line = fileReader.readLine(); //Just to skip the header so there are no conflicts.
			
			//Read the file line by line
			while ((line = fileReader.readLine()) != null) 
			{
				//Get all tokens available in line
				String[] tokens = line.split(",");
				
				//Places all the data into their respective setters in the Data.
				Data newData = new Data(tokens[0]);
				newData.setOpen(tokens[1]);
				newData.setHigh(tokens[2]);
				newData.setLow(tokens[3]);
				newData.setClose(tokens[4]);
				newData.setVolume(tokens[5]);
				newData.setAdjClose(tokens[6]);

				datesWithData.add(newData);
			}
		} 
		catch (Exception e) {
			e.printStackTrace();
		} 
		finally
		{
			try {
				fileReader.close(); //Closes file.
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	//Getter method to access the linked list of Data.
	public LinkedList<Data> getAllClosingPrices()
	{	
		return datesWithData;
	}
}


