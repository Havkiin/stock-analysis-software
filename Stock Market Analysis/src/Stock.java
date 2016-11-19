import javax.swing.text.Document;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.LinkedList;

/**
 * Created by S. Paquette.
 * Reads the data from the CSV file and puts it into a linked list of Data. This is used to graph historical data and show
 * moving averages as well as the buy/sell indicators.
 * Last update: November 4, 2016.
 *
 * Implemented by M. Ding: grab stock data through Yahoo API and save the data into a CSV file.
 * Last update: November 18, 2016.
 */

public class Stock
{
	public static HashMap<String, String> stockCode = new HashMap<String, String>();

	//Input file which needs to be parsed
	String fileToParse = "Sample data.csv";
	BufferedReader fileReader = null;

	//List of Users with an account.
	private LinkedList<Data> datesWithData = new LinkedList<Data>();

	//Default constructor that will take a CSV file and start to parse it.
	/*public Stock()
	{
		parseCSVLineByLine(fileToParse);
	}*/ // commented by M. Ding. This is a test file for Iteration #1

    /**
     * Constructor that map the stock name to stock code (DOW 30)
     */
    public Stock() {
        stockCode.put("Exxon Mobil Corporation (XOM)", "XOM");
        stockCode.put("Apple Inc. (AAPL)", "AAPL");
        stockCode.put("3M Company (MMM)", "MMM");
        stockCode.put("United Technologies Corporation (UTX)", "UTX");
        stockCode.put("Intel Corporation (INTC)", "INTC");
        stockCode.put("Visa Inc. (V)", "V");
        stockCode.put("Cisco Systems, Inc. (CSCO)", "CSCO");
        stockCode.put("The Goldman Sachs Group, Inc. (GS)", "GS");
        stockCode.put("Johnson & Johnson (JNJ)", "JNJ");
        stockCode.put("International Business Machines Corporation (IBM)", "IBM");
        stockCode.put("General Electric Company (GE)", "GE");
        stockCode.put("JPMorgan Chase & Co. (JPM)", "JPM");
        stockCode.put("McDonald's Corp. (MCD)", "MCD");
        stockCode.put("The Home Depot, Inc. (HD)", "HD");
        stockCode.put("Caterpillar Inc. (CAT)", "CAT");
        stockCode.put("E. I. du Pont de Nemours and Company (DD)", "DD");
        stockCode.put("Microsoft Corporation (MSFT)", "MSFT");
        stockCode.put("Verizon Communications Inc. (VZ)", "VZ");
        stockCode.put("The Coca-Cola Company (KO)", "KO");
        stockCode.put("The Travelers Companies, Inc. (TRV)", "TRV");
        stockCode.put("The Boeing Company (BA)", "BA");
        stockCode.put("Pfizer Inc. (PFE)", "PFE");
        stockCode.put("UnitedHealth Group Incorporated (UNH)", "UNH");
        stockCode.put("Wal-Mart Stores Inc. (WMT)", "WMT");
        stockCode.put("NIKE, Inc. (NKE)", "NKE");
        stockCode.put("Chevron Corporation (CVX)", "CVX");
        stockCode.put("American Express Company (AXP)", "AXP");
        stockCode.put("The Walt Disney Company (DIS)", "DIS");
        stockCode.put("The Procter & Gamble Company (PG)", "PG");
        stockCode.put("Merck & Co., Inc. (MRK)", "MRK");
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

    /**
     * Implemented by M. Ding
     * @param stockName selected from GUI
     * @param startYear YYYY
     * @param startMonth MM (from 1 to 12)
     * @param startDay DD (from 1 to 31)
     * @param endYear YYYY
     * @param endMonth MM (from 1 to 12)
     * @param endDay DD (from 1 to 31)
     * Last update: November 18, 2016
     */
    public void getStockDataFromYahoo(String stockName,
                                      String startYear, String startMonth, String startDay,
                                      String endYear, String endMonth, String endDay) {
        URL url = null;
        InputStreamReader isr = null;
        try {
            // get data through Yahoo Finance API
            url = new URL("http://ichart.yahoo.com/table.csv?s="
                    + stockCode.get(stockName) + "&a="
                    + (Integer.parseInt(startMonth) - 1) + "&b="
                    + startDay + "&c="
                    + startYear + "&d="
                    + (Integer.parseInt(endMonth) - 1) + "&e="
                    + endDay + "&f="
                    + endYear + "&g=d");
            URLConnection connection = url.openConnection();

            isr = new InputStreamReader(connection.getInputStream(), "UTF-8");

            // write data into CSV file
            char[] buffer = new char[1024];
            int len = 0;
            File file = new File(stockCode.get(stockName) + "-"
                    + startYear + "-" + startMonth + "-" + startDay
                    + "-to-"
                    + endYear + "-" + endMonth + "-" + endDay
                    + ".csv");
            OutputStreamWriter out = new OutputStreamWriter(new FileOutputStream(file));
            while ((len = isr.read(buffer)) != -1) {
                out.write(buffer, 0, len);
            }
            out.close();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}


