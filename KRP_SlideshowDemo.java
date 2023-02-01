import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.*;

public class KRP_SlideshowDemo extends JPanel 
implements ActionListener{

	String imagePath = "images/";
	String imageNames[] = {imagePath + "image1.jpg", imagePath + "image2.jpg", imagePath + "image3.jpg", 
			imagePath + "image4.jpg"};
	
	JToolBar toolBar;
	
	JButton nextButton;
	JButton previousButton;
	JButton button1, button2, button3, button4;
	
	JLabel imageLabel;
	
	ImageIcon image1, image2, image3, image4;
	
	public static void main(String[] args) {
		KRP_SlideshowDemo ssd = new KRP_SlideshowDemo();
	}

	public KRP_SlideshowDemo() {
		this.setLayout(new BorderLayout());
		
		JPanel mainPanel = new JPanel();
		JPanel titlePanel = new JPanel();
		JPanel toolbarPanel = new JPanel();
		
		Font appFont = new Font("Arial", Font.BOLD, 20);
		
		//title
		JLabel titleLabel = new JLabel("My slideshow");
		titleLabel.setFont(appFont);
		titlePanel.add(titleLabel);
		
		//images
		image1 = new ImageIcon(imageNames[0]);
		image2 = new ImageIcon(imageNames[1]);
		image3 = new ImageIcon(imageNames[2]);
		image4 = new ImageIcon(imageNames[3]);
		
		//main image
		JLabel imageLabel = new JLabel(image1);
		mainPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
		mainPanel.add(imageLabel);
				
		
		//toolbar
		toolBar = new JToolBar();
		toolBar.setLayout(new FlowLayout(FlowLayout.CENTER));
		toolBar.setFloatable(true);
		previousButton = new JButton("Back");	//previous image
		button1 = new JButton();
		button1.setIcon(new ImageIcon(imageNames[0]));
		button2 = new JButton();
		button2.setIcon(new ImageIcon(imageNames[1]));
		button3 = new JButton();
		button3.setIcon(new ImageIcon(imageNames[2]));
		button4 = new JButton();
		button4.setIcon(new ImageIcon(imageNames[3]));
		nextButton = new JButton("Next");		//next image
		
		toolBar.add(previousButton);
		toolBar.add(button1);
		toolBar.add(button2);
		toolBar.add(button3);
		toolBar.add(button4);
		toolBar.add(nextButton);
		
		this.add(titlePanel, BorderLayout.NORTH);
		this.add(mainPanel, BorderLayout.CENTER);
		this.add(toolBar, BorderLayout.SOUTH);
		
		nextButton.addActionListener(this);
		previousButton.addActionListener(this);
		button1.addActionListener(this);
		button2.addActionListener(this);
		button3.addActionListener(this);
		button4.addActionListener(this);
		

	}
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == button1) {
			imageLabel.setIcon(image1);
		}
		if(e.getSource() == button2) {
			imageLabel.setIcon(image2);
		}
		if(e.getSource() == button3) {
			imageLabel.setIcon(image3);
		}
		if(e.getSource() == button4) {
			imageLabel.setIcon(image4);
		}
	}
}
	

