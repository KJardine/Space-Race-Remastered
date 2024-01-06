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

 


public class MMPanel extends JPanel{
	//Properties
	BufferedImage space;
	Font myFont = new Font("Serif", Font.BOLD, 50);
	
	
	//Methods
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		g.drawImage(space, 0, 0, null);
		g.setColor(Color.WHITE);
		g.setFont(myFont);
		g.drawString("Space Race", 520, 100);
	}
	
	
	//Constructor
	public MMPanel(){
		super();
		try{
			space = ImageIO.read(new File("space.jpg"));
		}catch(IOException e){
			System.out.println("Invalid Picture");
		}
		
	
	}
}
