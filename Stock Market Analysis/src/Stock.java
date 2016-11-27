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
	private static HashMap<String, String> stockCode = new HashMap<String, String>();
	private String stockNames[] = new String[30]; //To get a list of names of stocks

	//Input file which needs to be parsed
	String fileToParse = "CurrentData.csv";
	BufferedReader fileReader = null;

	//List of Users with an account.
	private LinkedList<Data> datesWithData;

    /**
     * added by M. Ding
     * Constructor that map the stock name to stock code (DOW 30)
     * Ref: http://finance.yahoo.com/quote/%5EDJI/components?p=%5EDJI
     * Last update: November 18, 2016
     */
    public Stock() {
        stockCode.put("Exxon Mobil Corporation (XOM)", "XOM");
        stockNames[0] = "Exxon Mobil Corporation (XOM)";
        stockCode.put("Apple Inc. (AAPL)", "AAPL");
        stockNames[1] = "Apple Inc. (AAPL)";
        stockCode.put("3M Company (MMM)", "MMM");
        stockNames[2] = "3M Company (MMM)";
        stockCode.put("United Technologies Corporation (UTX)", "UTX");
        stockNames[3] = "United Technologies Corporation (UTX)";
        stockCode.put("Intel Corporation (INTC)", "INTC");
        stockNames[4] = "Intel Corporation (INTC)";
        stockCode.put("Visa Inc. (V)", "V");
        stockNames[5] = "Visa Inc. (V)";
        stockCode.put("Cisco Systems, Inc. (CSCO)", "CSCO");
        stockNames[6] = "Cisco Systems, Inc. (CSCO)";
        stockCode.put("The Goldman Sachs Group, Inc. (GS)", "GS");
        stockNames[7] = "The Goldman Sachs Group, Inc. (GS)";
        stockCode.put("Johnson & Johnson (JNJ)", "JNJ");
        stockNames[8] = "Johnson & Johnson (JNJ)";
        stockCode.put("International Business Machines Corporation (IBM)", "IBM");
        stockNames[9] = "International Business Machines Corporation (IBM)";
        stockCode.put("General Electric Company (GE)", "GE");
        stockNames[10] = "General Electric Company (GE)";
        stockCode.put("JPMorgan Chase & Co. (JPM)", "JPM");
        stockNames[11] = "JPMorgan Chase & Co. (JPM)";
        stockCode.put("McDonald's Corp. (MCD)", "MCD");
        stockNames[12] = "McDonald's Corp. (MCD)";
        stockCode.put("The Home Depot, Inc. (HD)", "HD");
        stockNames[13] = "The Home Depot, Inc. (HD)";
        stockCode.put("Caterpillar Inc. (CAT)", "CAT");
        stockNames[14] = "Caterpillar Inc. (CAT)";
        stockCode.put("E. I. du Pont de Nemours and Company (DD)", "DD");
        stockNames[15] = "E. I. du Pont de Nemours and Company (DD)";
        stockCode.put("Microsoft Corporation (MSFT)", "MSFT");
        stockNames[16] = "Microsoft Corporation (MSFT)";
        stockCode.put("Verizon Communications Inc. (VZ)", "VZ");
        stockNames[17] = "Verizon Communications Inc. (VZ)";
        stockCode.put("The Coca-Cola Company (KO)", "KO");
        stockNames[18] = "The Coca-Cola Company (KO)";
        stockCode.put("The Travelers Companies, Inc. (TRV)", "TRV");
        stockNames[19] = "The Travelers Companies, Inc. (TRV)";
        stockCode.put("The Boeing Company (BA)", "BA");
        stockNames[20] = "The Boeing Company (BA)";
        stockCode.put("Pfizer Inc. (PFE)", "PFE");
        stockNames[21] = "Pfizer Inc. (PFE)";
        stockCode.put("UnitedHealth Group Incorporated (UNH)", "UNH");
        stockNames[22] = "UnitedHealth Group Incorporated (UNH)";
        stockCode.put("Wal-Mart Stores Inc. (WMT)", "WMT");
        stockNames[23] = "Wal-Mart Stores Inc. (WMT)";
        stockCode.put("NIKE, Inc. (NKE)", "NKE");
        stockNames[24] = "NIKE, Inc. (NKE)";
        stockCode.put("Chevron Corporation (CVX)", "CVX");
        stockNames[25] = "Chevron Corporation (CVX)";
        stockCode.put("American Express Company (AXP)", "AXP");
        stockNames[26] = "American Express Company (AXP)";
        stockCode.put("The Walt Disney Company (DIS)", "DIS");
        stockNames[27] = "The Walt Disney Company (DIS)";
        stockCode.put("The Procter & Gamble Company (PG)", "PG");
        stockNames[28] = "The Procter & Gamble Company (PG)";
        stockCode.put("Merck & Co., Inc. (MRK)", "MRK");
        stockNames[29] = "Merck & Co., Inc. (MRK)";
        
    }

	//Method that parses the CSV file by placing the data into a linked list to be accessed by
	//the program later on.
	public void parseCSVLineByLine(String file)
	{
		try
		{
			String line = "";
			datesWithData = new LinkedList<Data>();
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
            //fileToParse = stockCode.get(stockName) + "-"
                   // + startYear + "-" + startMonth + "-" + startDay
                   // + "-to-"
                   // + endYear + "-" + endMonth + "-" + endDay
                   // + ".csv";
            File file = new File(fileToParse);
            OutputStreamWriter out = new OutputStreamWriter(new FileOutputStream(file));
            while ((len = isr.read(buffer)) != -1) {
                out.write(buffer, 0, len);
            }
            isr.close();
            out.close();
            parseCSVLineByLine(fileToParse);
            
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public HashMap<String, String> getStockCodes()
    {
    	return stockCode;
    }
    
    public String[] getStockCodeNames()
    {
    	return stockNames;
    }

}


