/**
 * created by S. Paquette.
 * Creates and displays graphs using JFreeChart library. It mostly uses a time series type of chart for the dates. 
 * This application allows the user to view moving averages, buy/sell indicators and historical data plotted on the graph. 
 * Last updated: November 4, 2016
 */

import javax.swing.JFrame;
import javax.swing.WindowConstants;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.ui.RefineryUtilities;
import org.jfree.data.time.Day;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.xy.XYDataset;

public class Graph extends JFrame
{
	double[] datasetYAxis;
	String[] datasetXAxis;
	double[] maDataset;
	int movingAveragePeriod;

	private static final long serialVersionUID = 1L;
	XYDataset dataset;
	JFreeChart chart;
	final ChartPanel chartPanel;
	final int chartWidth = 560;
	final int chartHeight = 367;
	
	//Constructor that takes in a title, the moving average data set, the closing price data set, the dates associated and the 
	//moving average time period. Sets up the graphing application and chart settings.
	public Graph(final String title, double[] maDataset, double[] datasetYAxis, String[] datasetXAxis, int movingAveragePeriod) 
	{       
		super(title);

		this.datasetYAxis = datasetYAxis;
		this.datasetXAxis = datasetXAxis;
		this.maDataset = maDataset;
		this.movingAveragePeriod = movingAveragePeriod;
		
		dataset = createDataset();
		chart = createChart(dataset);
		chartPanel = new ChartPanel(chart);
		chartPanel.setPreferredSize(new java.awt.Dimension(chartHeight,
				chartWidth));
		this.add(chartPanel);
		this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

		this.pack();
		RefineryUtilities.centerFrameOnScreen(this);
		this.setVisible(true);
	}

	//Method that creates the dataset for the graph and will have multiple time series displaying on the chart
	//for moving averages and buy/sell indicators.
	private XYDataset createDataset() {

		final TimeSeries series1 = new TimeSeries("Historical Data"); //historical data-eventually, should be buy/sell indicators
		final TimeSeries series2 = new TimeSeries("SMA (" + movingAveragePeriod + ")"); //moving average
		Day current;
		int counter = 0; //for indexing in array.

		for(int i = 0; i < datasetXAxis.length; i++)
		{
			//Turns the date (String) into a Date type so that it can be graphed on the time series.
			String[] parsingDate = datasetXAxis[i].split("-");

			int year = Integer.parseInt(parsingDate[0]);
			int month = Integer.parseInt(parsingDate[1]);
			int day = Integer.parseInt(parsingDate[2]);

			current = new Day(day, month, year);
			series1.add(current, datasetYAxis[i]);
			
			//For graphing moving average (starts on the day it was predefined and goes from there)
			if(i >= (movingAveragePeriod-1))
			{
				series2.add(current, maDataset[counter]);	
				counter++;
			}
		}

		//For displaying multiple series on one graph
		TimeSeriesCollection dataset = new TimeSeriesCollection();
		dataset.addSeries(series1);
		dataset.addSeries(series2);

		return dataset;
	}

	//Method for creating chart type and adding the data set to it. 
	private JFreeChart createChart(XYDataset dataset) 
	{
		JFreeChart chart = ChartFactory.createTimeSeriesChart(
				"Simple Moving Average Graph", 
				"Dates", 
				"Closing Prices", 
				dataset,
				true, 
				false, 
				false
				);

		return chart;
	}
}
