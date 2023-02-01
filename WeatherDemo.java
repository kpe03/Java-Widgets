import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;
import java.beans.*;
import java.net.URL;
import java.net.URLConnection;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import com.google.gson.*;
import com.google.gson.reflect.*;
import com.google.gson.Gson;

/* WeatherDemo widget that displays weather from selected city. Uses OpenWeatherMap API to retrieve current weather data 
 Also uses GSON to convert JSON into a Map so that data can be easily read. */

public class KRP_WeatherDemo extends JPanel
implements ActionListener, PropertyChangeListener{
	
	//JPanel stuff!!
	JFormattedTextField cityField, currentField, feelsLikeField, windSpeedField,	//text fields to show current weather data
		tempMinField, tempMaxField;
	JButton goButton;	//JButton for search button when selecting a city
	JTextArea weatherTextArea;	
	
	//TODO implement a ComboBox with temperature units
	
	//--- API stuff!! ----
	String API_key = "1d95441a66f6e0a30218727123fdb80a";	//API key to put in url
	String cityName = "";	//hold city name
	String temperature, feelsLike, tempMin, tempMax, windSpeed, weatherMain, 
		weatherDescription;	//string variables to hold weather data
	String API_string = "https://api.openweathermap.org/data/2.5/weather?q=" + cityName + "&appid=" + API_key + "&units=" + units;
	//
	Date date;
	
	//main method
	public static void main(String[] args) {
		KRP_WeatherDemo krp_wd = new KRP_WeatherDemo();
	}
	//constructor
	public KRP_WeatherDemo () {
		//set layout
		this.setLayout(new BorderLayout());
		
		//---- GUI setup! ----
		//panels
		JPanel titlePanel = new JPanel();
		JPanel cityPanel = new JPanel();
		JPanel topPanel = new JPanel();
		JPanel mainPanel = new JPanel();
		JPanel weatherResultsPanel = new JPanel();
		JPanel weatherPanel = new JPanel();
		
		//font
		Font appFontSmall = new Font("Arial", Font.PLAIN, 14);
		Font appFontMed = new Font("Arial", Font.BOLD, 15);
		
		//title (title label, date label)
		titlePanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		JLabel titleLabel = new JLabel("Today's Weather: ");
		date = new Date();
		JLabel dateLabel = new JLabel(date.toString());
		titleLabel.setFont(appFontMed);
		dateLabel.setFont(appFontSmall);
		//add to title panel
		titlePanel.add(titleLabel);
		titlePanel.add(dateLabel);
		
		//information panel (city field, label, and "go" button)
		cityPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		JLabel cityLabel = new JLabel("Enter a city name: ");
		cityField = new JFormattedTextField();
		cityField.setEditable(true);
		cityField.setColumns(10);
		goButton = new JButton("Find city");
		cityPanel.add(cityLabel);
		cityPanel.add(cityField);
		cityPanel.add(goButton, FlowLayout.RIGHT);
		//add to bigger panel
		topPanel.setLayout(new GridLayout(2,1));
		topPanel.add(titlePanel);
		topPanel.add(cityPanel);
		
		
		//results panel
		weatherResultsPanel.setLayout(new GridLayout(3,2));
		//labels
		JLabel currentLabel = new JLabel("Current temperature: ");
		JLabel feelsLikeLabel = new JLabel("Feels like: ");
		JLabel tempMinLabel = new JLabel("Lowest temperature: ");
		JLabel tempMaxLabel = new JLabel("Highest temperature: ");
		JLabel windSpeedLabel = new JLabel("Wind speed: ");
		JLabel weatherLabel = new JLabel("Weather: ");
		//font
		currentLabel.setFont(appFontSmall);
		feelsLikeLabel.setFont(appFontSmall);
		windSpeedLabel.setFont(appFontSmall);
		weatherLabel.setFont(appFontSmall);
		//text fields
		currentField = new JFormattedTextField();
		currentField.setEditable(false);
		currentField.setFont(appFontSmall);
		feelsLikeField = new JFormattedTextField();
		feelsLikeField.setEditable(false);
		feelsLikeField.setFont(appFontSmall);
		windSpeedField = new JFormattedTextField();
		windSpeedField.setEditable(false);
		windSpeedField.setFont(appFontSmall);
		
		//add everything to Weather Results Panel
		weatherResultsPanel.add(currentLabel);
		weatherResultsPanel.add(currentField);
		weatherResultsPanel.add(feelsLikeLabel);
		weatherResultsPanel.add(feelsLikeField);
		weatherResultsPanel.add(windSpeedLabel);
		weatherResultsPanel.add(windSpeedField);
		//TO DO implement Max temp, Min temp panels
		/*
		weatherResultsPanel.add(tempMaxLabel);
		weatherResultsPanel.add(tempMaxField);
		weatherResultsPanel.add(tempMinLabel);
		weatherResultsPanel.add(tempMinField);
		*/
		//text area
		/*
		weatherTextArea = new JTextArea(10, 60);
		weatherTextArea.setEditable(false);
		weatherTextArea.setFont(appFontSmall);
		//add to weather panel
		weatherPanel.add(weatherLabel);
		weatherPanel.add(weatherTextArea);
		*/
		//set main panel and add weather results
		mainPanel.setLayout(new GridLayout(1,1));
		mainPanel.add(weatherResultsPanel);
		//add everything to main panel
		this.add(topPanel, BorderLayout.NORTH);
		this.add(mainPanel, BorderLayout.CENTER);
		
		//action listeners and property change listeners
		goButton.addActionListener(this);
		cityField.addPropertyChangeListener("value", this);
		currentField.addPropertyChangeListener("value", this);
		feelsLikeField.addPropertyChangeListener("value", this);
		windSpeedField.addPropertyChangeListener("value", this);
	}
	/*******************
	 * actionPerformed *
	 *******************/
	//when the "search" button is clicked, get the city name and make API call
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == goButton) {
			String city = (cityField.getText());
			System.out.println(city);
		
			try {
				StringBuilder result = new StringBuilder();
				//create url for the open weather map API
				URL url = new URL(API_call(city, API_key, "fahrenheit"));
				//open connection
				URLConnection connection = url.openConnection();
				//create BufferedReader to read all of the JSON data
				BufferedReader rd = new BufferedReader(new InputStreamReader(connection.getInputStream()));
				String line;
				//Buffered reader reads each line until null, then puts data into a stringbuilder object
				while((line = rd.readLine()) != null) {
					result.append(line);
				}
				rd.close();
				//System.out.println(result); //show results
				
				//create map to that contains all JSON data
				Map<String, Object> respMap = jsonToMap(result.toString());
				//Map that holds "Main" weather data
				Map<String, Object> mainMap = jsonToMap(respMap.get("main").toString());
				//Map that contains "Wind" weather data
				Map<String, Object> windMap = jsonToMap(respMap.get("wind").toString());
				
				//get needed data from Main Map
				temperature = (mainMap.get("temp")).toString();
				feelsLike = (mainMap.get("feels_like")).toString();
				tempMin = (mainMap.get("temp_min")).toString();
				tempMax = (mainMap.get("temp_max")).toString();
				
				//data from wind speed map
				windSpeed = (windMap.get("speed")).toString();
				
				
			}
			catch(IOException ioe) {
				System.out.print(ioe.getMessage());
			}

			//set the Formatted text fields with the string variables
			currentField.setValue(temperature);
			feelsLikeField.setValue(feelsLike);
			//tempMinField.setValue(tempMin);
			//tempMaxField.setValue(tempMax);
			windSpeedField.setValue(windSpeed);
			
			//put information in the text area
			//weatherTextArea.append(weatherMain + "\n" + weatherDescription);
			
			System.out.println("Temperature: " + temperature);
			System.out.println("Feels like: " + feelsLike);
			System.out.println("Wind speed: " + windSpeed);
					
		}
		
	}
	/******************
	 * propertyChange *
	 ******************/
	//for when a value is entered in the text field
	public void propertyChange(PropertyChangeEvent e) {
		if(e.getSource() == cityField) {
		}
	}
	/*************
	 * jsonToMap *
	 *************/
	//takes the data from a JSON file and puts it into a map
	public static Map<String, Object> jsonToMap(String str) {
		Map<String, Object> map = new Gson().fromJson(
				str, new TypeToken<HashMap<String, Object>>() {}.getType()
				);
		return map;
	}
	public static String API_call(String cityName, String API_key, String units) {
		String API_string = "https://api.openweathermap.org/data/2.5/weather?q=" + cityName + "&appid=" + API_key + "&units=imperial";
		return API_string;
	}
	 
}
