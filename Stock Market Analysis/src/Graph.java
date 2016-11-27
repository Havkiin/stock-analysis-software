/** created by S. Paquette.
 * Creates and displays graphs using JFreeChart library. It mostly uses a time series type of chart for the dates. 
 * This application allows the user to view moving averages, buy/sell indicators and historical data plotted on the graph. 
 * Last updated: November 4, 2016
 */

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.geom.Rectangle2D;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.SegmentedTimeline;
import org.jfree.chart.plot.DatasetRenderingOrder;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.CandlestickRenderer;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.ui.RefineryUtilities;
import org.jfree.data.time.Day;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.xy.AbstractXYDataset;
import org.jfree.data.xy.DefaultOHLCDataset;
import org.jfree.data.xy.OHLCDataItem;
import org.jfree.data.xy.XYDataset;

public class Graph extends JFrame
{
	MovingAverages ma = new MovingAverages();
	double[] datasetYAxis, volumeDataset;
	String[] datasetXAxis;
	double[] maDataset, highDataset, lowDataset, openDataset;
	boolean[] maTimePeriods;
	int movingAveragePeriod;

	private static final long serialVersionUID = 1L;
	XYDataset dataset;
	JFreeChart chart = null;
	final ChartPanel chartPanel;
	CandlestickRenderer renderer;
	XYItemRenderer renderer2;
	XYPlot mainPlot;
	final int chartWidth = 560;
	final int chartHeight = 367;

	//Constructor that takes in a title, the moving average data set, the closing price data set, the dates associated and the 
	//moving average time period. Sets up the graphing application and chart settings.
	public Graph(final String title, double[] datasetYAxis, String[] datasetXAxis, 
			double[] high, double[] low, double[] open, double[] volume, String stockName, boolean[] MATimePeriods) 
	{      
		super(title);

		this.datasetYAxis = datasetYAxis;
		this.datasetXAxis = datasetXAxis;
		highDataset = high;
		lowDataset = low;
		openDataset = open;
		volumeDataset = volume;
		maTimePeriods = MATimePeriods;

		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		DateAxis domainAxis  = new DateAxis("Date");
		NumberAxis rangeAxis = new NumberAxis("Price");
		renderer = new CandlestickRenderer();
		XYDataset dataset = getDataSet(stockName);

		mainPlot = new XYPlot(dataset, domainAxis, rangeAxis, renderer);

		//Do some setting up, see the API Doc
		renderer.setSeriesPaint(0, Color.BLACK);
		renderer.setDrawVolume(false);
		Stroke myStroke = new BasicStroke((float) 1.0);
		renderer.setSeriesStroke(0, myStroke);
		rangeAxis.setAutoRangeIncludesZero(false);
		domainAxis.setTimeline( SegmentedTimeline.newMondayThroughFridayTimeline() );

		//Add the otherDataSet to the plot and map it to the same axis at the original plot 
		int index = 1;
		renderer2 = new XYLineAndShapeRenderer();
		dataset = createDataset();
		mainPlot.setDataset(index, dataset);
		mainPlot.mapDatasetToRangeAxis(index, 0);

		mainPlot.setRenderer(1, renderer2);
		mainPlot.setDatasetRenderingOrder(DatasetRenderingOrder.FORWARD);

		//Now create the chart and chart panel
		chart = new JFreeChart(stockName, null, mainPlot, true);
		chartPanel = new ChartPanel(chart);
		chartPanel.setPreferredSize(new Dimension(600, 300));

		this.add(chartPanel);
		this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

		this.pack();
		RefineryUtilities.centerFrameOnScreen(this);
		this.setVisible(true);
	}

	//Method that creates the dataset for the graph and will have multiple time series displaying on the chart
	//for moving averages and buy/sell indicators.
	private XYDataset createDataset() {

		//For displaying multiple series on one graph
		TimeSeriesCollection dataset = new TimeSeriesCollection();
		int counter = 0;
		
		for(int j = 0; j < maTimePeriods.length; j++)
		{
			if(maTimePeriods[j] == true)
			{
				if(j == 0)
				{
					movingAveragePeriod = 20;
					renderer2.setSeriesPaint(counter, Color.blue);
				}
				else if (j == 1)
				{
					movingAveragePeriod = 50;
					renderer2.setSeriesPaint(counter, Color.pink);
				}
				else if(j == 2)
				{
					movingAveragePeriod = 100;
					renderer2.setSeriesPaint(counter, Color.yellow);
				}
				else if(j == 3)
				{
					movingAveragePeriod = 200;
					renderer2.setSeriesPaint(counter, Color.darkGray);
				}
				
				maDataset = ma.simpleMovingAverage(datasetYAxis, movingAveragePeriod);
					
				TimeSeries series = new TimeSeries("SMA (" + movingAveragePeriod + ")"); //moving average
				Day current;
				int counter2 = 0; //for indexing in array.

				for(int i = 0; i < datasetXAxis.length; i++)
				{
					//Turns the date (String) into a Date type so that it can be graphed on the time series.
					String[] parsingDate = datasetXAxis[i].split("-");

					int year = Integer.parseInt(parsingDate[0]);
					int month = Integer.parseInt(parsingDate[1]);
					int day = Integer.parseInt(parsingDate[2]);

					current = new Day(day, month, year);

					//For graphing moving average (starts on the day it was predefined and goes from there)
					if(i >= (movingAveragePeriod-1))
					{
						series.add(current, maDataset[counter2]);	
						counter2++;
					}
				}
				
				dataset.addSeries(series);
				
				Stroke myStroke = new BasicStroke((float) 1.0);
				renderer2.setSeriesStroke(counter, myStroke);
				renderer2.setSeriesShape(counter, new Rectangle(-1, -1));
				counter++;
			}
		}

		return dataset;
	}

	protected AbstractXYDataset getDataSet(String stockSymbol) {
		//This is the dataset we are going to create
		DefaultOHLCDataset result = null;
		//This is the data needed for the dataset
		OHLCDataItem[] data;

		//This is where we go get the data, replace with your own data source
		data = getData();

		//Create a dataset, an Open, High, Low, Close dataset
		result = new DefaultOHLCDataset(stockSymbol, data);

		return result;
	}


	protected OHLCDataItem[] getData() 
	{
		List<OHLCDataItem> dataItems = new ArrayList<OHLCDataItem>();
		DateFormat df = new SimpleDateFormat("y-M-d");

		for(int i = 0; i < datasetXAxis.length; i++)
		{
			Date date = null;
			try {
				date = df.parse(datasetXAxis[i]);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			double open = openDataset[i];
			double high = highDataset[i];
			double low = lowDataset[i];
			double close = datasetYAxis[i];
			double volume = volumeDataset[i];

			OHLCDataItem item = new OHLCDataItem(date, open, high, low, close, volume);
			dataItems.add(item);
		}
		//Data from Yahoo is from newest to oldest. Reverse so it is oldest to newest
		Collections.reverse(dataItems);

		//Convert the list into an array
		OHLCDataItem[] data = dataItems.toArray(new OHLCDataItem[dataItems.size()]);

		return data;
	}
}
