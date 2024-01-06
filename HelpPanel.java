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


public class HelpPanel extends JPanel{
	//Properties
	private BufferedImage help;
	
	
	//Methods
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		
		g.drawImage(help, 0, 0, null);
		
	
	}
	
	
	//Constructor
	public HelpPanel(){
		super();
	
		try{
			help = ImageIO.read(new File("help.png"));
		}catch(IOException e){
			System.out.println("Unable to load image");
		}
	}


}
