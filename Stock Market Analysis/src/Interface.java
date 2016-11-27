/**Interface class created by C. Hennebelle.
 * GUI implementation done by S. Paquette.
 * Creates GUI for user to interact with program such as opening page, login page, creation page, menu page,
 * graphing page and logout page. This class gathers user input and directs the user to the next step of the program. 
 * Last updated: November 4, 2016
 */

import javax.swing.*;

import utility.ArrayOperations;

import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.HashMap;

public class Interface extends JFrame{

	private static Database database = new Database(); //Instantiates the class that will read and store the user information.
	private static Stock stock = new Stock(); //Instantiates the class that will read and store the historical data.
	private String theUser = null; //Stores the user name of whoever is using it.

	//Universal container to load components.
	private Container contain;

	//Different panels to redraw in container.
	private JPanel openingPanel, loginPanel, createPanel, menuOptionsPanel;

	//Opening page variables with messages, login button and create button.
	private JLabel openingMessage = new JLabel("Welcome to our Stock Analysis Software!", SwingConstants.CENTER); //message
	private JLabel openingMessage2 = new JLabel("Click below to login", SwingConstants.CENTER); //message
	private JLabel openingMessage3 = new JLabel("No account? Click below to get one!", SwingConstants.CENTER); //message
	private JButton loginButton = new JButton("Login"); //Button to go to login page
	private JButton createButton = new JButton("Create"); //Button to go to creation page
	private JButton goBackMain = new JButton("Go Back"); //Button to go back to the main menu from login/create

	//Login page variables with messages, log in button, and text fields to enter answers.
	private JLabel loginOpeningMessage = new JLabel("Login Page", SwingConstants.CENTER); //Welcoming message
	private JLabel displayingUserName = new JLabel("User Name:", SwingConstants.CENTER); //Label
	private JLabel displayingPassword = new JLabel("Password:", SwingConstants.CENTER); //Label
	private JButton blogin = new JButton("Login"); //Button to go to login and authenticate the user's account.
	private JTextField txuser = new JTextField(15); //Field to enter user name
	private JPasswordField pass = new JPasswordField(15); //Field to enter password

	//Create page variables with message, create button and two text fields for the answers.
	private JLabel createOpeningMessage = new JLabel("Creating An Account Page", SwingConstants.CENTER); //Opening message
	private JButton bcreate = new JButton("Create"); //Button to create and authenticate a new account.
	private JTextField usernameChosen = new JTextField(15);  //Field to enter user name
	private JPasswordField passwordChosen = new JPasswordField(15); //Field to enter password

	//Application Option page variables with message, and many buttons for features or logout.
	private JLabel optionOpeningMessage; //Welcoming message.
	private JButton brecent = new JButton("View Most Recent");  //not used yet.
	private JButton bgraph = new JButton("View Moving Average Graphs"); //Button to get to graphing page.
	private JButton bhisdata = new JButton("View Historical Data");  //not used yet.
	private JButton blogout = new JButton("Logout");	//Button to go to logout page.

	//Graph page when choosing date and time for moving averages.
	private JLabel graphWelcomingMessage = new JLabel("Creating Moving Average Graph Page", SwingConstants.CENTER); //Welcoming message
	private JButton goBack = new JButton("Go Back"); //Button to go back to menu
	private String[] stocks = new String[30], timePeriod = {"20 days", "50 days", "100 days", "200 days"}; //Options for combo boxes
	private JButton bgraph2 = new JButton("Graph"); //Button to graph the user's preference in historical data and moving average.
	private String[] graphConditions = {"Exxon Mobil Corporation (XOM)","1 year", "20 days"}, historicalData = {"1 year", "2 years", "5 years", "All years"}; //Options for combo box
	private JTextField startYear = new JTextField(10); //Field to enter the starting year for graph
	private JTextField startMonth = new JTextField(10); //Field to enter the starting month for graph
	private JTextField startDay = new JTextField(10); //Field to enter the starting day for graph

	//Constructor that sets up the initial welcoming screen.
	public Interface() 
	{
		super("Stock Analysis Software");
		openingPanel = new JPanel();
		setSize(600,600);
		setLocation(350,100);
		openingPanel.setLayout(null);

		openingMessage.setBounds(120,50,350,50);
		openingMessage2.setBounds(110,360,350,50);
		loginButton.setBounds(210,410,150,20);
		openingMessage3.setBounds(120,450,350,50);
		createButton.setBounds(210,500,150,20);

		openingPanel.add(openingMessage);
		openingPanel.add(openingMessage2);
		openingPanel.add(loginButton);
		openingPanel.add(openingMessage3);
		openingPanel.add(createButton);

		getContentPane().add(openingPanel);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		begin();  //Will perform an action when user pressed one of two buttons (login or create) on the screen.
	}

	public static void main (String[] args) //starts the application and GUI.
	{
		Interface i = new Interface();	
	}

	//method to get action listeners of when user either picks to log in or create an account 
	//at the beginning of the application.
	public void begin()
	{
		//This is for displaying the log in screen of the application.
		loginButton.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent ae) 
			{
				contain = getContentPane();
				loginPanel = new JPanel(null);
				loginPanel.setSize(500,200);
				loginPanel.setLocation(500,280);

				loginOpeningMessage.setBounds(120,50,350,50);
				displayingUserName.setBounds(70,150,350,50);
				displayingPassword.setBounds(70,240,350,50);
				txuser.setBounds(210,200,150,20);
				pass.setBounds(210,290,150,20);
				blogin.setBounds(250,340,80,20);
				goBackMain.setBounds(10,10,100,20);

				loginPanel.add(loginOpeningMessage);
				loginPanel.add(displayingUserName);
				loginPanel.add(displayingPassword);
				loginPanel.add(blogin);
				loginPanel.add(txuser);
				loginPanel.add(pass);
				loginPanel.add(goBackMain);

				contain.removeAll(); //removes the previous panel and adds this one (to show login screen).
				contain.add(loginPanel);
				contain.repaint();
				setVisible(true);
				actionlogin();
			}
		});

		//This is for displaying the creation screen of the application.
		createButton.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				contain = getContentPane();
				createPanel = new JPanel(null);
				createPanel.setSize(500,200);
				createPanel.setLocation(500,280);

				createOpeningMessage.setBounds(120,50,350,50);
				displayingUserName.setBounds(70,150,350,50);
				displayingPassword.setBounds(70,240,350,50);
				usernameChosen.setBounds(210,200,150,20);
				passwordChosen.setBounds(210,290,150,20);
				bcreate.setBounds(250,340,80,20);
				goBackMain.setBounds(10,10,100,20);

				createPanel.add(createOpeningMessage);
				createPanel.add(displayingUserName);
				createPanel.add(displayingPassword);
				createPanel.add(bcreate);
				createPanel.add(usernameChosen);
				createPanel.add(passwordChosen);
				createPanel.add(goBackMain);

				contain.removeAll(); //removes the previous panel and adds this one (to show creation screen).
				contain.add(createPanel);
				contain.validate();
				contain.repaint();
				setVisible(true);
				actionCreate();
			}
		});

		goBackMain.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				contain = getContentPane();
				openingPanel = new JPanel();
				setSize(600,600);
				setLocation(350,100);
				openingPanel.setLayout(null);

				openingMessage.setBounds(120,50,350,50);
				openingMessage2.setBounds(110,360,350,50);
				loginButton.setBounds(210,410,150,20);
				openingMessage3.setBounds(120,450,350,50);
				createButton.setBounds(210,500,150,20);

				openingPanel.add(openingMessage);
				openingPanel.add(openingMessage2);
				openingPanel.add(loginButton);
				openingPanel.add(openingMessage3);
				openingPanel.add(createButton);

				contain.removeAll(); //removes the previous panel and adds this one (to show creation screen).
				contain.add(openingPanel);
				contain.validate();
				contain.repaint();
				setVisible(true);
			}
		});
	}

	//method to get action listener for when user is logging in. 
	public void actionlogin()
	{
		blogin.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				String puname = txuser.getText();
				String ppaswd = pass.getText();

				if(database.log_in(puname, ppaswd)) //checks with database to validate the user name and password.
				{
					theUser = puname;
					userOptions();  //if all is good, the user is logged in and gets to see the options. 
				}
				else
				{   //Shows error to user when it is the wrong user name and/or password.
					JOptionPane.showMessageDialog(null,"Wrong Password / Username");
					txuser.setText("");
					pass.setText("");
					txuser.requestFocus();
				}
			}
		});
	}

	//method to get action listener when a user is creating an account.
	public void actionCreate(){
		bcreate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				String puname = usernameChosen.getText();
				String ppaswd = passwordChosen.getText();

				if (puname.length() > 15)
				{
					//Displays an error message to the user and starts the process over again.							
					JOptionPane.showMessageDialog(null,"Username cannot be longer than 15 characters.");
					usernameChosen.setText("");
					passwordChosen.setText("");
					usernameChosen.requestFocus();
				}
				else if (ppaswd.length() > 15)
				{
					//Displays an error message to the user and starts the process over again.							
					JOptionPane.showMessageDialog(null,"Password cannot be longer than 15 characters.");
					usernameChosen.setText("");
					passwordChosen.setText("");
					usernameChosen.requestFocus();
				}
				else if (database.createAccount(puname, ppaswd)) //Checks database if user name already exists. 
				{
					theUser = puname;
					userOptions();     //if user name has not been taken, user's account is created and is logged in.
				}
				else
				{	//Displays an error message to the user and starts the process over again.							
					JOptionPane.showMessageDialog(null,"Username has been taken! Please pick another.");
					usernameChosen.setText("");
					passwordChosen.setText("");
					usernameChosen.requestFocus();
				}

			}
		});
	}
	//method to display menu option screen when user logs in, finishes creating an account or finishes something else.
	public void userOptions()
	{
		menuOptionsPanel = new JPanel(null);
		menuOptionsPanel.setSize(600,600);
		menuOptionsPanel.setLocation(350,100);

		String message = "Welcome " + theUser + ", please feel free to pick any of the options below:";
		optionOpeningMessage = new JLabel(message);
		optionOpeningMessage.setBounds(120,50,400,50);
		bgraph.setBounds(190,280,200,20);
		blogout.setBounds(210,500,150,20);

		menuOptionsPanel.add(optionOpeningMessage);
		menuOptionsPanel.add(bgraph);
		menuOptionsPanel.add(blogout);

		contain.removeAll(); //removes the previous panel and adds this one (to show new screen).
		contain.add(menuOptionsPanel);
		contain.validate();
		contain.repaint();
		setVisible(true);
		pickingAnOption();
	}

	//method to get action listeners when selecting an option in the menu and graphing page.
	public void pickingAnOption()
	{
		//loads a logout screen when logout button is pressed.
		blogout.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				JPanel closingPanel = new JPanel(null);
				closingPanel.setSize(600,600);
				closingPanel.setLocation(350,100);

				JLabel closingMessage = new JLabel("Thank you for using our Stock Analysis Software!", SwingConstants.CENTER);
				JLabel closingMessage2 = new JLabel("We hope you enjoyed the application!", SwingConstants.CENTER);

				closingMessage.setBounds(100,50,400,50);
				closingMessage2.setBounds(100,500,400,50);

				closingPanel.add(closingMessage);
				closingPanel.add(closingMessage2);

				contain.removeAll(); //removes the previous panel and adds this one (to show logout screen).
				contain.add(closingPanel);
				contain.validate();
				contain.repaint();
				setVisible(true);				
			}
		});

		//Method to display a graphing page for the user to pick historical data and moving averages.
		bgraph.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				JLabel tellUserToEnterStock = new JLabel("Pick a stock:");
				JLabel tellUserToEnterDate = new JLabel("Enter a starting date:");
				JLabel tellUserToEnterTheYear = new JLabel("Year:");
				startYear.setText("YYYY");
				JLabel tellUserToEnterMonth = new JLabel("Month:");
				startMonth.setText("MM");
				JLabel tellUserToEnterDay = new JLabel("Day:");
				startDay.setText("DD");
				JLabel tellUserToEnterYear = new JLabel("See historical data within the last:");
				JLabel tellUserToChooseTime = new JLabel("Pick moving average predefined as:");

				for(int i = 0; i < 30; i++)
					stocks[i] = stock.getStockCodeNames()[i];

				//Create a combo box for every year in the data set and lets user choose option.
				JComboBox stockList = new JComboBox(stocks);
				stockList.addActionListener(new ActionListener() 
				{
					public void actionPerformed(ActionEvent e) 
					{
						JComboBox cb = (JComboBox)e.getSource();
						String stock = (String)cb.getSelectedItem();	
						graphConditions[0] = stock;
					}
				});

				//Create a combo box for time span of historical data (1,2,5 and all years) and lets user choose option.
				JComboBox numOfYearsList = new JComboBox(historicalData);
				numOfYearsList.addActionListener(new ActionListener() 
				{
					public void actionPerformed(ActionEvent e) 
					{
						JComboBox cb = (JComboBox)e.getSource();
						String numOfYears = (String)cb.getSelectedItem();

						graphConditions[1] = numOfYears;
					}
				});

				//Create a combo box for moving average predefined 20, 50, 100 and 200 days and lets user choose an option.
				JComboBox timeSpanList = new JComboBox(timePeriod);
				timeSpanList.addActionListener(new ActionListener() 
				{
					public void actionPerformed(ActionEvent e) 
					{
						JComboBox cb = (JComboBox)e.getSource();
						String time = (String)cb.getSelectedItem();		
						graphConditions[2] = time;
					}
				});

				JPanel graphPanel = new JPanel(null);
				graphPanel.setSize(600,600);
				graphPanel.setLocation(350,100);

				graphWelcomingMessage.setBounds(150,40,300,50);
				goBack.setBounds(10,10,100,20);
				tellUserToEnterStock.setBounds(50,100,100,50);
				stockList.setBounds(135,115,310,20);
				tellUserToEnterDate.setBounds(50,145,150,50);
				tellUserToEnterTheYear.setBounds(50,180,100,50);
				startYear.setBounds(90,195,50,20);
				tellUserToEnterMonth.setBounds(200,180,100,50);
				startMonth.setBounds(250,195,50,20);
				tellUserToEnterDay.setBounds(360,180,100,50);
				startDay.setBounds(400,195,50,20);
				tellUserToEnterYear.setBounds(50,250,400,20);
				numOfYearsList.setBounds(255,250,100,20);
				tellUserToChooseTime.setBounds(50,470,400,20);
				timeSpanList.setBounds(60,500,150,20);
				bgraph2.setBounds(350,500,200,20);

				graphPanel.add(graphWelcomingMessage);
				graphPanel.add(goBack);
				graphPanel.add(tellUserToEnterStock);
				graphPanel.add(tellUserToEnterDate);
				graphPanel.add(tellUserToEnterTheYear);
				graphPanel.add(startYear);
				graphPanel.add(tellUserToEnterMonth);
				graphPanel.add(startMonth);
				graphPanel.add(tellUserToEnterDay);
				graphPanel.add(startDay);
				graphPanel.add(tellUserToEnterYear);
				graphPanel.add(numOfYearsList);
				graphPanel.add(tellUserToChooseTime);
				graphPanel.add(stockList);
				graphPanel.add(timeSpanList);
				graphPanel.add(bgraph2);

				contain.removeAll(); //removes the previous panel and adds this one (to show new screen).
				contain.add(graphPanel);
				contain.validate();
				contain.repaint();
				setVisible(true);		
			}
		});

		//Action listener for when the user presses the graph button on the graphing page to start
		//plotting the historical data chosen by the user. It starts calculating the MA if the time time span is 
		//within the range of the data set.
		bgraph2.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				int month = 0, day = 0;
				if(startMonth.getText().charAt(0) == '0') {
					String monthString = startMonth.getText();
					month = Integer.parseInt(monthString.valueOf(1));
				}
				else
				{
					month = Integer.parseInt(startMonth.getText());
				}

				if(startDay.getText().charAt(0) == '0') {
					String dayString = startMonth.getText();
					day = Integer.parseInt(dayString.valueOf(1));
				}
				else
				{
					day = Integer.parseInt(startDay.getText());
				}

				//year-month-day format
				int year = Integer.parseInt(startYear.getText());
				String[] temp = graphConditions[1].split(" ");
				int numOfYears = 0;

				//Turns the string of options into integers to calculate the difference in years.
				if(temp[0].equals("All"))
					numOfYears = 20;
				else if(temp[0].equals("1"))
					numOfYears = 1;
				else if(temp[0].equals("2"))
					numOfYears = 2;
				else if(temp[0].equals("5"))
					numOfYears = 5;

				int yearMinusnumOfYears = year - numOfYears; //The difference between historical data time span and year chosen.
				
				if(month > 12 || month < 1)
				{
					//Shows error to user when user picked an invalid date.
					JOptionPane.showMessageDialog(null,"The input for the month is wrong");
					startYear.setText("");
					startMonth.setText("");
					startDay.setText("");
				}
				else if(day > 31 || day < 1)
				{
					JOptionPane.showMessageDialog(null,"The input for the day is wrong");
					startYear.setText("");
					startMonth.setText("");
					startDay.setText("");
				}
				else if(day >= 1 && day <= 31)
				{
					if(month == 2 && day > 28)
					{
						//Shows error to user when user picked an invalid date.
						JOptionPane.showMessageDialog(null,"Date doesn't exist");
						startYear.setText("");
						startMonth.setText("");
						startDay.setText("");
					}
					else if ((month == 4 || month == 6 || month == 9 || month == 11)
							&& day > 30)
					{	
						//Shows error to user when user picked an invalid date.
						JOptionPane.showMessageDialog(null,"Date doesn't exist");
						startYear.setText("");
						startMonth.setText("");
						startDay.setText("");
					}
					else if((yearMinusnumOfYears >= 1900 && yearMinusnumOfYears <= 2016))
					{

						//calculate the moving average
						try {
							calculatingMA(year, yearMinusnumOfYears, month, day); //Calculates the MA of the historical data.
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
				}
				else
				{
					//Shows error to user when user picked an invalid date.
					JOptionPane.showMessageDialog(null,"The input for Year, Month or Day is wrong");
					startYear.setText("");
					startMonth.setText("");
					startDay.setText("");
				}

			}
		});

		//Action listener that displays the menu page again if the user presses "go back" button.
		//Allows the user to back track to log out or choose another option.
		goBack.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				menuOptionsPanel.setSize(600,600);
				menuOptionsPanel.setLocation(350,100);

				String message = "Welcome " + theUser + ", please feel free to pick any of the options below:";
				optionOpeningMessage = new JLabel(message);

				optionOpeningMessage.setBounds(120,50,400,50);
				bgraph.setBounds(190,280,200,20);
				blogout.setBounds(210,500,150,20);

				menuOptionsPanel.add(optionOpeningMessage);
				menuOptionsPanel.add(bgraph);
				menuOptionsPanel.add(blogout);

				contain.removeAll(); //removes the previous panel and adds this one (to show new screen).
				contain.add(menuOptionsPanel);
				contain.validate();
				contain.repaint();
				setVisible(true);
			}
		});
	}

	//Method to calculate the moving average by taking the starting year and time span of the historical data.
	public void calculatingMA(int year, int startingYearOnGraph, int month, int day) throws IOException
	{
		//create a CSV file from yahoo API based on user's desired inputs
		stock.getStockDataFromYahoo(graphConditions[0], Integer.toString(startingYearOnGraph), Integer.toString(month), Integer.toString(day), 
				Integer.toString(year), Integer.toString(month), Integer.toString(day));

		//Used to figure out the number of data for this particular graph by going through the years defined by user.
		int tempCounter = 0; //stores number of data points.

		for(int i = stock.getAllClosingPrices().size() - 1; i >= 0; i--)			
		{
			tempCounter++;
		}

		MovingAverages ma = new MovingAverages();//used to access method to calculate the ma.
		String[] temp = graphConditions[2].split(" "); 
		int timeSpanMovingAverage = Integer.parseInt(temp[0]); //turn predefined number of days (string) into an integer.
		double[] closingPricesdataset = new double[tempCounter]; //Stores the array of closing prices within the data set.
		String[] dateNameofdataset = new String[tempCounter]; //Stores the dates associated with the closing prices as strings.
		int counter = 0; //index for array when adding elements.

		for(int i = stock.getAllClosingPrices().size() - 1; i >= 0; i--)			
		{
			//store every close price in data set within the graph range.
			closingPricesdataset[counter] = stock.getAllClosingPrices().get(i).getClose();

			//Store every date within the data set of the graph.
			dateNameofdataset[counter] = stock.getAllClosingPrices().get(i).getDate();

			counter++;
		}

		double[] datasetForMovingAverage = ma.simpleMovingAverage(closingPricesdataset, timeSpanMovingAverage); //calculates
		displayGraph(datasetForMovingAverage,closingPricesdataset, dateNameofdataset, timeSpanMovingAverage); //starts graphing process
	}

	//Method to display the graph by instantiating it and creating one. Takes in the array of moving averages, the 
	//array of closing prices, the dates associated with the closing prices and the time predefined for moving average.
	public void displayGraph(double[] maDataset, double[] histDataset, String[] dateNameofdataset, int timePeriod)
	{
		Graph graph = new Graph("Simple Moving Average Graph", maDataset, histDataset, dateNameofdataset, timePeriod);
	}
}