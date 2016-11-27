/**
 * Created by S. Paquette M.
 * Stores the data from the CSV file such as the prices for the open, high, low, close
 * volume and adjusted close for each date specified.
 * Last update: November 4, 2016.
 */

public class Data 
{
	//Variables that contain the data for each date in the file.
	private int year, month, day, volume;
	private double open, high, low, close, adjClose;
	private String date;
	
	public Data(){} //Just in case someone calls the default constructor.
	
	//Constructor takes in a date as a string and parses it into integers.
	public Data(String date)
	{
		this.date = date;
		
		String[] parsingDate = date.split("-");
		
		year = Integer.parseInt(parsingDate[0]);
		month = Integer.parseInt(parsingDate[1]);
		day = Integer.parseInt(parsingDate[2]);
	}
	
	//Setter for open prices
	public void setOpen(String open)
	{
		this.open = Double.parseDouble(open);
	}
	
	//Setter for close prices
	public void setClose(String close)
	{
		this.close = Double.parseDouble(close);
	}
	
	//Setter for high prices
	public void setHigh(String high)
	{
		this.high = Double.parseDouble(high);
	}
	
	//Setter for low prices
	public void setLow(String low)
	{
		this.low = Double.parseDouble(low);
	}
	
	//Setter for adjusted close prices
	public void setAdjClose(String adjClose)
	{
		this.adjClose = Double.parseDouble(adjClose);
	}
	
	//Setter for volume
	public void setVolume(String volume)
	{
		this.volume = Integer.parseInt(volume);
	}
	
	//All the respective getters for the variables
	public double getOpen()
	{
		return open;
	}
	
	public double getClose()
	{
		return close;
	}
	
	public double getHigh()
	{
		return high;
	}
	
	public double getLow()
	{
		return low;
	}
	
	public double getAdjClose()
	{
		return adjClose;
	}
	
	public int getVolume()
	{
		return volume;
	}
	
	public int getYear()
	{
		return year;
	}
	
	public int getMonth()
	{
		return month;
	}
	
	public int getDay()
	{
		return day;
	}
	
	public String getDate()
	{
		return date;
	}
}
