import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import javax.swing.event.*;
import java.io.*;
import java.awt.image.*;
import javax.imageio.*;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;


public class DemoPanel extends JPanel{
	//Properties
	Font myFont = new Font("Serif", Font.BOLD, 50);
	
	// Position Variables
	int intDemoVertMove = 0;
	int intDemoHoriMove = 0;
	int intDemoPosX = 600;
	int intDemoPosY = 400;
	
	
	
	//Methods
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		g.setFont(myFont);
		g.drawString("Demo Controls", 475, 100);
		g.drawRect(240, 200, 800, 400);
		
		g.fillOval(400, 500, 50, 50);
		g.fillOval(800, 400, 50, 50);
	
		g.fillRect(intDemoPosX, intDemoPosY, 50, 100);
		

	
	}
	
	
	//Constructor
	public DemoPanel(){
		super();
	
	}
}
