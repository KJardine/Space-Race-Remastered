import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import javax.swing.event.*;
import java.io.*;
import java.awt.image.*;
import javax.imageio.*;

import java.awt.Graphics;
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

import static java.lang.System.*;


public class CarPanel extends JPanel{
	//Properties
	BufferedImage car;
	BufferedImage car1;
	BufferedImage car2;
	BufferedImage car3;
	BufferedImage car4;
	Font myFont = new Font("Serif", Font.BOLD, 50);
	
	String car1Name;
	String car2Name;
	String car3Name;
	String car4Name;
	String car1Image;
	String car2Image;
	String car3Image;
	String car4Image;
	int car1Weight = 0;
	int car2Weight = 0;
	int car3Weight = 0;
	int car4Weight = 0;
	int car1Speed = 0;
	int car2Speed = 0;
	int car3Speed = 0;
	int car4Speed = 0;
	
	
	//Methods
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		g.drawImage(car, 0, 0, null);
		g.setColor(Color.WHITE);
		g.setFont(myFont);
		g.drawString("Car Selection", 520, 100);
		
		// Car 1 Image
		g.drawImage(car1, 310, 300, null);
		// Car 2 Image
		g.drawImage(car2, 510, 300, null);
		// Car 3 Image
		g.drawImage(car3, 710, 300, null);
		// Car 4 Image
		g.drawImage(car4, 910, 300, null);
	}
	
	//Constructor
	public CarPanel(){
		super();
		
		BufferedReader br = null;
		try{
			File file = new File("vehicles.txt");
			FileReader fr = new FileReader(file);
			br = new BufferedReader(fr);
			String line;
			while ((line = br.readLine()) != null){
		
				
				if(car1Name == null){
					car1Name = line;
				}else if(car1Image == null){
					car1Image = line;
				}else if(car1Weight == 0){
					car1Weight = Integer.parseInt(line);
				}else if(car1Speed == 0){
					car1Speed = Integer.parseInt(line);
				}else if(car2Name == null){
					car2Name = line;
				}else if(car2Image == null){
					car2Image = line;
				}else if(car2Weight == 0){
					car2Weight = Integer.parseInt(line);
				}else if(car2Speed == 0){
					car2Speed = Integer.parseInt(line);
				}else if(car3Name == null){
					car3Name = line;
				}else if(car3Image == null){
					car3Image = line;
				}else if(car3Weight == 0){
					car3Weight = Integer.parseInt(line);
				}else if(car3Speed == 0){
					car3Speed = Integer.parseInt(line);
				}else if(car4Name == null){
					car4Name = line;
				}else if(car4Image == null){
					car4Image = line;
				}else if(car4Weight == 0){
					car4Weight = Integer.parseInt(line);
				}else if(car4Speed == 0){
					car4Speed = Integer.parseInt(line);
				}		
			}
			
			System.out.println(car1Name);
			System.out.println(car1Image);
			System.out.println(car1Weight);
			System.out.println(car1Speed);
			System.out.println(car2Name);
			System.out.println(car2Image);
			System.out.println(car2Weight);
			System.out.println(car2Speed);
			System.out.println(car3Name);
			System.out.println(car3Image);
			System.out.println(car3Weight);
			System.out.println(car3Speed);
			System.out.println(car4Name);
			System.out.println(car4Image);
			System.out.println(car4Weight);
			System.out.println(car4Speed);
		}
		catch(IOException e){e.printStackTrace();}
		finally
		{
			try{if(br != null)br.close();}
			catch(IOException e){e.printStackTrace();}
		}
		
		try{
			car = ImageIO.read(new File("space.jpg"));
		}catch(IOException e){
			System.out.println("Invalid Picture 1");
		}
		try{
			car1 = ImageIO.read(new File(car1Image));
		}catch(IOException e){
			System.out.println("Invalid Picture 2");
		}
		try{
			car2 = ImageIO.read(new File(car2Image));
		}catch(IOException e){
			System.out.println("Invalid Picture 3");
		}
		try{
			car3 = ImageIO.read(new File(car3Image));
		}catch(IOException e){
			System.out.println("Invalid Picture 4");
		}
		try{
			car4 = ImageIO.read(new File(car4Image));
		}catch(IOException e){
			System.out.println("Invalid Picture 5");
		}
		
		
		
	}
}
	
