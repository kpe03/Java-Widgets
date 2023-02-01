import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;
import java.beans.*;
import java.net.*;
import java.io.IOException;
import java.io.InputStream;

//first: GUI
//second: implement the API
//third: action listener

public class KRP_WeatherDemo extends JPanel
implements ActionListener, PropertyChangeListener{
	//JPanel stuff!!
	JFormattedTextField cityField, currentField, feelsLikeField, windSpeedField;
	JButton goButton;
	
	JTextArea weatherTextArea;
	
	//--- API stuff!! ----
	String API_key = "1d95441a66f6e0a30218727123fdb80a";
	String cityName = "";
	double lat, lon;
	
	String API_call = "https://api.openweathermap.org/data/3.0/onecall?lat={lat}&lon={lon}&exclude={part}&appid=" + API_key + "&units=metric";
	String API_coords = "http://api.openweathermap.org/geo/1.0/direct?q=" + cityName + "&limit=1&appid=" + API_key;
	
	Date date;
	
	public static void main(String[] args) {
		KRP_WeatherDemo krp_wd = new KRP_WeatherDemo();
	}
	
	public KRP_WeatherDemo () {
	
		this.setLayout(new BorderLayout());
		
		//---- GUI setup! ----
		//panels
		JPanel titlePanel = new JPanel();
		JPanel cityPanel = new JPanel();
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
		titlePanel.add(titleLabel);
		titlePanel.add(dateLabel);
		
		//information panel (city field, label, and "go" button)
		cityPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		JLabel cityLabel = new JLabel("Enter a city name: ");
		cityField = new JFormattedTextField();
		cityField.setEditable(true);
		cityField.setColumns(20);
		goButton = new JButton("Find city");
		cityPanel.add(cityLabel);
		cityPanel.add(cityField);
		cityPanel.add(goButton, FlowLayout.RIGHT);
		
		//results panel
		weatherResultsPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		JLabel currentLabel = new JLabel("Current temperature: ");
		JLabel feelsLikeLabel = new JLabel("Feels like: ");
		JLabel windSpeedLabel = new JLabel("Wind speed: ");
		JLabel weatherLabel = new JLabel("Weather: ");
		currentLabel.setFont(appFontSmall);
		feelsLikeLabel.setFont(appFontSmall);
		windSpeedLabel.setFont(appFontSmall);
		weatherLabel.setFont(appFontSmall);
		
		currentField = new JFormattedTextField();
		currentField.setEditable(false);
		feelsLikeField = new JFormattedTextField();
		feelsLikeField.setEditable(false);
		windSpeedField = new JFormattedTextField();
		windSpeedField.setEditable(false);
		
		weatherTextArea = new JTextArea(10, 60);
		weatherTextArea.setEditable(false);
		weatherTextArea.setFont(appFontSmall);
		
		weatherResultsPanel.add(currentLabel);
		weatherResultsPanel.add(currentField);
		weatherResultsPanel.add(feelsLikeLabel);
		weatherResultsPanel.add(feelsLikeField);
		weatherResultsPanel.add(windSpeedLabel);
		weatherResultsPanel.add(windSpeedField);
		weatherResultsPanel.add(weatherLabel);
		weatherResultsPanel.add(weatherTextArea);
		
		this.add(titlePanel, BorderLayout.NORTH);
		this.add(cityPanel, BorderLayout.CENTER);
		this.add(weatherResultsPanel, BorderLayout.SOUTH);
		
		goButton.addActionListener(this);
	}
	
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == goButton) {
			//get the city entered and get coordinates
			String city = (cityField.getText());
			
			//try to make an HTTP request
			try {
				InputStream response = new URL(API_coords).openStream();
				HttpURLConnection httpConnection = (HttpURLConnection) new URL(API_coords).openConnection();
				httpConnection.setRequestMethod("POST");
				int status = httpConnection.getResponseCode();
			} 
			catch (MalformedURLException e1) {
				e1.printStackTrace();
			}
			catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}
	public void propertyChange(PropertyChangeEvent e) {
		if(e.getSource() == cityField) {
						
		}
	}
}
