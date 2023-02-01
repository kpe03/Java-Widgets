import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.text.Format;
import java.text.NumberFormat;

public class KRP_Temperature extends JPanel
implements ActionListener {

	JTextArea result;
	JFormattedTextField textField, resultField;
	
	JLabel unitsLabel, finalLabel;
	
	JComboBox<String> startUnitsMenu, finalUnitsMenu;
	
	String[] units = new String[] {"Celsius", "Kelvin", "Fahrenheit"};
	String firstVal = "Base units:";
	String finalVal = "Converted units:";
	
	JButton calcButton;
	
	NumberFormat amountFormat;

	double amount = 0;
	
	public static void main(String[] args) {
	KRP_Temperature wad = new KRP_Temperature();
	}
	
	public KRP_Temperature() {
		
		this.setLayout(new BorderLayout());
		setUpFormats();
		
		//JPanel
		JPanel titlePanel = new JPanel();
		JPanel firstValPanel = new JPanel();
		JPanel secondValPanel = new JPanel();
		JPanel centerPanel = new JPanel();
		JPanel buttonPanel = new JPanel();
		//Font
		Font appFontSmall = new Font("Arial", Font.PLAIN, 14);
		Font appFontMed = new Font("Arial", Font.BOLD, 15);
		
		//title
		JLabel titleLabel = new JLabel("Temperature");
		titleLabel.setFont(appFontMed);
		titlePanel.add(titleLabel);
		
		//units -- start
		
		startUnitsMenu = new JComboBox<String>(units);
		startUnitsMenu.setFont(appFontSmall);
		unitsLabel = new JLabel(firstVal);
		
		textField = new JFormattedTextField(amountFormat);
		textField.setColumns(10);
		firstValPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
		firstValPanel.add(unitsLabel);
		firstValPanel.add(textField);
		firstValPanel.add(startUnitsMenu);
		
		//add first and second panel to center panel
		centerPanel.add(firstValPanel);
		centerPanel.add(secondValPanel);
		
		//units -- final
		finalUnitsMenu = new JComboBox<String>(units);
		finalUnitsMenu.setFont(appFontSmall);
		finalLabel = new JLabel(finalVal);
		
		resultField = new JFormattedTextField(amountFormat);
		resultField.setColumns(10);
		resultField.setEditable(false);
		resultField.setForeground(Color.red);
		secondValPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
		secondValPanel.add(finalLabel);
		secondValPanel.add(resultField);
		secondValPanel.add(finalUnitsMenu);
		
		//buttons
		buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
		JPanel leftPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		calcButton = new JButton("Convert");
		rightPanel.add(calcButton);
		buttonPanel.add(leftPanel);
		buttonPanel.add(rightPanel);
		
		this.add(titlePanel, BorderLayout.NORTH);
		this.add(centerPanel, BorderLayout.CENTER);
		this.add(buttonPanel, BorderLayout.SOUTH);
		
	}
	//Parse number input by user
	private void setUpFormats() {
		amountFormat = NumberFormat.getNumberInstance();
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == calcButton) {
			String firstVal = String.valueOf(startUnitsMenu.getSelectedItem());
			String finalVal = String.valueOf(finalUnitsMenu.getSelectedItem());
			
			amount = ((Number)textField.getValue()).doubleValue();
			
			if(firstVal == "Celsius") {
				if(finalVal == "Kelvin") {
					//convert to Kelvin
					resultField.setValue(amount + 273.15);
				}
				else if(finalVal == "Fahrenheit") {
					//convert to Fahrenheit
					resultField.setValue((amount * 9/5) + 32);
				}
				else {
					//print the entered value into result field
					resultField.setValue(amount);
					
				}
					
			}
			if(firstVal == "Kelvin") {
				if(finalVal == "Celsius") {
					resultField.setValue(amount - 273.15);
				}
				else if(finalVal == "Fahrenheit") {
					resultField.setValue((amount - 273.15) * 9/5 + 32);
				}
				else {
					//print entered value into result field
					resultField.setValue(amount);
				}
			}
			if(firstVal == "Fahrenheit") {
				if(finalVal == "Celsius") {
					resultField.setValue((amount - 32) * 5/9);
				}
				else if(finalVal == "Kelvin") {
					resultField.setValue((amount - 32) * 5/9 + 273.15);
				}
				else {
					resultField.setValue(amount);
				}
			}
		}
	}

}
