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

public class CreditsPanel extends JPanel{
	//Properties
	private BufferedImage credits;
	private BufferedImage background;
	Font myFont = new Font("Serif", Font.BOLD, 35);
	
	//Methods
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		
		// Drawing background
		g.drawImage(background, 0, 0, null);
		// Drawing image of 3 asian kids in
		//g.drawImage(credits, 325, 50, null);
		// Adding text 
		g.setColor(Color.WHITE);
		g.setFont(myFont);
		g.drawString("Background Image by Oleg Gamulinskiy from Pixabay", 200, 375);
	}
	
	//Constructor
	public CreditsPanel(){
		super();

		try{
			credits = ImageIO.read(new File("credits.png"));
			background = ImageIO.read(new File("space.jpg"));
		}catch(IOException e){
			System.out.println("Unable to load image");
		}
	}
}
