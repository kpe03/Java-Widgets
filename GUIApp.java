import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class GUIApp extends JFrame{

	private static final int WIDTH = 800;
	private static final int HEIGHT = 800;
	
	public static void main(String args[]) {
		GUIApp g = new GUIApp();
	}

	public GUIApp() {
		super("Producer Consumer GUI");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLayout(new GridLayout(1,1));
		
		KRP_WeatherDemo wd = new KRP_WeatherDemo();
		add(wd);
		setSize(WIDTH, HEIGHT);
		setResizable(true);
		setVisible(true);
	}

}
