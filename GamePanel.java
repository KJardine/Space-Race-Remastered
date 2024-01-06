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
import java.util.Random;   
import java.lang.Thread;


public class GamePanel extends JPanel{
	//Properties
	/** images */
	BufferedImage car1;
	BufferedImage car2;
	BufferedImage car3;
	BufferedImage car4;
	BufferedImage space;
	BufferedImage P1Win;
	BufferedImage P2Win;
	BufferedImage meteor;
	
	int intP1Red = 0;
	int intP2Red = 0;
	
	String strPlayerNum = "";
	
	/** specs of chosen car P1 */
	String strP1Car = "";
	int intP1Weight = 0;
	int intP1Speed = 0;
	int intP1Move = 0;
	int intP1MoveHor = 0;
	int intP1X = 400;
	int intP1Y = 600;
	
	/** specs of chosen car P2 */
	String strP2Car = "";
	int intP2Weight = 0;
	int intP2Speed = 0;
	int intP2Move = 0;
	int intP2MoveHor = 0;
	int intP2X = 880;
	int intP2Y = 600;
	
	int intY1 = 0;
	int intY2 = 0;
	
	/** random asteriod spawn */
	Random random = new Random();
	
	/** arrays for XY coord of random asteriod */
	int intRandX1[] = new int[8];
	int intRandY1[] = new int[8];
	
	int intRandX2[] = new int[8];
	int intRandY2[] = new int[8];
	
	int intCount = 0;
	
	boolean gameOver;
	
	//Methods
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		
		g.setColor(Color.YELLOW);
		
		//Player Hitbox
		/** rectangles to act as hitbox for P1 and P2 */
		Rectangle P1 = new Rectangle(intP1X, intP1Y, 86, 114);
		Rectangle P2 = new Rectangle(intP2X, intP2Y, 86, 114);
		
		//Asteriod Hitbox
		/** rectangles to act as hitbox for asteriods */
		Rectangle A0 = new Rectangle(intRandX1[0], intRandY1[0], 100, 100);
		Rectangle A1 = new Rectangle(intRandX1[1], intRandY1[1], 100, 100);
		Rectangle A2 = new Rectangle(intRandX1[2], intRandY1[2], 100, 100);
		Rectangle A3 = new Rectangle(intRandX1[3], intRandY1[3], 100, 100);
		Rectangle A4 = new Rectangle(intRandX1[4], intRandY1[4], 100, 100);
		Rectangle A5 = new Rectangle(intRandX1[5], intRandY1[5], 100, 100);
		Rectangle A6 = new Rectangle(intRandX1[6], intRandY1[6], 100, 100);
		Rectangle A7 = new Rectangle(intRandX1[7], intRandY1[7], 100, 100);
		
		Rectangle A8 = new Rectangle(intRandX2[0], intRandY2[0], 100, 100);
		Rectangle A9 = new Rectangle(intRandX2[1], intRandY2[1], 100, 100);
		Rectangle A10 = new Rectangle(intRandX2[2], intRandY2[2], 100, 100);
		Rectangle A11 = new Rectangle(intRandX2[3], intRandY2[3], 100, 100);
		Rectangle A12 = new Rectangle(intRandX2[4], intRandY2[4], 100, 100);
		Rectangle A13 = new Rectangle(intRandX2[5], intRandY2[5], 100, 100);
		Rectangle A14 = new Rectangle(intRandX2[6], intRandY2[6], 100, 100);
		Rectangle A15 = new Rectangle(intRandX2[7], intRandY2[7], 100, 100);
		
		/** P1 P2 collision detection */
		if(P1.intersects(P2)){
			System.out.println("Hit");
			intP2X = intP2X - 100;
			intP1X = intP1X + 100;
		}
		
		/** P1 asteriod hit detection */
		if(P1.intersects(A0)||P1.intersects(A1)||P1.intersects(A2)||P1.intersects(A3)||P1.intersects(A4)||P1.intersects(A5)||P1.intersects(A6)||P1.intersects(A7)){
			intP1Y = intP1Y + 100;
			intY1 = intY1 - 100;
		}
		
		/** P2 asteriod hit detection */
		if(P2.intersects(A8)||P2.intersects(A9)||P2.intersects(A10)||P2.intersects(A11)||P2.intersects(A12)||P2.intersects(A13)||P2.intersects(A14)||P2.intersects(A15)){
			intP2Y = intP2Y + 100;
			intY2 = intY2 - 100;
		}
		
		g.fillRect(intP1X, intP1Y, 86, 114);
		g.fillRect(intP2X, intP2Y, 86, 114);
		
		/** draw everything if P1 */
		if(strPlayerNum.equals("1")){
			g.drawImage(space, 0, -6480 + intY1, null);
			//asteriods
			g.fillRect(intRandX1[0], (intRandY1[0] - intP1Y)+302, 100, 100);
			g.fillRect(intRandX1[1], (intRandY1[1] - intP1Y)+302, 100, 100);
			g.fillRect(intRandX1[2], (intRandY1[2] - intP1Y)+302, 100, 100);
			g.fillRect(intRandX1[3], (intRandY1[3] - intP1Y)+302, 100, 100);
			g.fillRect(intRandX1[4], (intRandY1[4] - intP1Y)+302, 100, 100);
			g.fillRect(intRandX1[5], (intRandY1[5] - intP1Y)+302, 100, 100);
			g.fillRect(intRandX1[6], (intRandY1[6] - intP1Y)+302, 100, 100);
			g.fillRect(intRandX1[7], (intRandY1[7] - intP1Y)+302, 100, 100);
			
			g.drawImage(meteor, intRandX1[0], (intRandY1[0] - intP1Y)+302, null);
			g.drawImage(meteor, intRandX1[1], (intRandY1[1] - intP1Y)+302, null);
			g.drawImage(meteor, intRandX1[2], (intRandY1[2] - intP1Y)+302, null);
			g.drawImage(meteor, intRandX1[3], (intRandY1[3] - intP1Y)+302, null);
			g.drawImage(meteor, intRandX1[4], (intRandY1[4] - intP1Y)+302, null);
			g.drawImage(meteor, intRandX1[5], (intRandY1[5] - intP1Y)+302, null);
			g.drawImage(meteor, intRandX1[6], (intRandY1[6] - intP1Y)+302, null);
			g.drawImage(meteor, intRandX1[7], (intRandY1[7] - intP1Y)+302, null);
			
			/** draw cars based on selection */
			if(strP1Car.equals("speeder.png")){
				g.drawImage(car1, intP1X, 302, null);
			}if(strP1Car.equals("juggernaut.png")){
				g.drawImage(car2, intP1X, 302, null);
			}if(strP1Car.equals("druid.png")){
				g.drawImage(car3, intP1X, 302, null);
			}if(strP1Car.equals("sport.png")){
				g.drawImage(car4, intP1X, 302, null);
			}	
			if(strP2Car.equals("speeder.png")){
				g.drawImage(car1, intP2X, (intP2Y - intP1Y) +302, null);
			}if(strP2Car.equals("juggernaut.png")){
				g.drawImage(car2, intP2X, (intP2Y - intP1Y) +302, null);
			}if(strP2Car.equals("druid.png")){
				g.drawImage(car3, intP2X, (intP2Y - intP1Y) +302, null);
			}if(strP2Car.equals("sport.png")){
				g.drawImage(car4, intP2X, (intP2Y - intP1Y) +302, null);
			}
		}
		
		/** draw everything if P2 */
		if(strPlayerNum.equals("2")){
			g.drawImage(space, 0, -6480 + intY2, null);
			//asteriods
			g.fillRect(intRandX2[0], (intRandY2[0] - intP2Y)+302, 100, 100);
			g.fillRect(intRandX2[1], (intRandY2[1] - intP2Y)+302, 100, 100);
			g.fillRect(intRandX2[2], (intRandY2[2] - intP2Y)+302, 100, 100);
			g.fillRect(intRandX2[3], (intRandY2[3] - intP2Y)+302, 100, 100);
			g.fillRect(intRandX2[4], (intRandY2[4] - intP2Y)+302, 100, 100);
			g.fillRect(intRandX2[5], (intRandY2[5] - intP2Y)+302, 100, 100);
			g.fillRect(intRandX2[6], (intRandY2[6] - intP2Y)+302, 100, 100);
			g.fillRect(intRandX2[7], (intRandY2[7] - intP2Y)+302, 100, 100);
			
			g.drawImage(meteor, intRandX2[0], (intRandY2[0] - intP2Y)+302, null);
			g.drawImage(meteor, intRandX2[1], (intRandY2[1] - intP2Y)+302, null);
			g.drawImage(meteor, intRandX2[2], (intRandY2[2] - intP2Y)+302, null);
			g.drawImage(meteor, intRandX2[3], (intRandY2[3] - intP2Y)+302, null);
			g.drawImage(meteor, intRandX2[4], (intRandY2[4] - intP2Y)+302, null);
			g.drawImage(meteor, intRandX2[5], (intRandY2[5] - intP2Y)+302, null);
			g.drawImage(meteor, intRandX2[6], (intRandY2[6] - intP2Y)+302, null);
			g.drawImage(meteor, intRandX2[7], (intRandY2[7] - intP2Y)+302, null);
			
			
			/** draw cars based on selection */
			if(strP1Car.equals("speeder.png")){
				g.drawImage(car1, intP1X, (intP1Y - intP2Y) +302, null);
			}if(strP1Car.equals("juggernaut.png")){
				g.drawImage(car2, intP1X, (intP1Y - intP2Y) +302, null);
			}if(strP1Car.equals("druid.png")){
				g.drawImage(car3, intP1X, (intP1Y - intP2Y) +302, null);
			}if(strP1Car.equals("sport.png")){
				g.drawImage(car4, intP1X, (intP1Y - intP2Y) +302, null);
			}	
			if(strP2Car.equals("speeder.png")){
			g.drawImage(car1, intP2X, 302, null);
			}if(strP2Car.equals("juggernaut.png")){
			g.drawImage(car2, intP2X, 302, null);
			}if(strP2Car.equals("druid.png")){
			g.drawImage(car3, intP2X, 302, null);
			}if(strP2Car.equals("sport.png")){
			g.drawImage(car4, intP2X, 302, null);
			}
		}
		
		
		/** win  P1*/
		if(intP1Y <= -5820){
			System.out.println("Player 1 wins");
			g.drawImage(P1Win, 0, -100, null);
			g.drawImage(P1Win, 0, -100, null);
			intP1Y = -5820;
			intY1 = 0;
			Timer timer = new Timer(2000, new ActionListener() {
				public void actionPerformed(ActionEvent evt) {
					System.exit(0);
				}
			});
			timer.start();
		}
		/** win  P2*/
		if(intP2Y <= -5820){
			System.out.println("Player 2 wins");
			g.drawImage(P2Win, 0, -100, null);
			g.drawImage(P2Win, 0, -100, null);
			intP2Y = -5820;
			intY2 = 0;
			Timer timer = new Timer(2000, new ActionListener() {
				public void actionPerformed(ActionEvent evt) {
					System.exit(0);
				}
			});
			timer.start();
		}
		
		
		/** borders */
		intP1Y = intP1Y - intP1Move;
		if(intP1Y >= 600){
			intP1Y = 600;
			intY1 = 0;
		}
		intP2Y = intP2Y - intP2Move;
		if(intP2Y >= 600){
			intP2Y = 600;
			intY2 = 0;
		}
		intP1X = intP1X + intP1MoveHor;
		if(intP1X <= 0){
			intP1X = 0;
		}
		if(intP1X >= 1194){
			intP1X = 1194;
		}
		intP2X = intP2X + intP2MoveHor;
		if(intP2X <= 0){
			intP2X = 0;
		}
		if(intP2X >= 1194){
			intP2X = 1194;
		}
		
		g.drawString("Player: "+strPlayerNum, 50, 50);
	
	}
	
	
	//Constructor
	public GamePanel(){
		super();
		try{
			car1 = ImageIO.read(new File("speeder.png"));
		}catch(IOException e){
			System.out.println("Unable to load image");
		}
		try{
			car2 = ImageIO.read(new File("juggernaut.png"));
		}catch(IOException e){
			System.out.println("Unable to load image");
		}
		try{
			car3 = ImageIO.read(new File("druid.png"));
		}catch(IOException e){
			System.out.println("Unable to load image");
		}
		try{
			car4 = ImageIO.read(new File("sport.png"));
		}catch(IOException e){
			System.out.println("Unable to load image");
		}
		try{
			space = ImageIO.read(new File("space.png"));
		}catch(IOException e){
			System.out.println("Invalid Picture");
		}
		try{
			P1Win = ImageIO.read(new File("P1Wins.png"));
		}catch(IOException e){
			System.out.println("Invalid Picture");
		}
		try{
			P2Win = ImageIO.read(new File("P2Wins.png"));
		}catch(IOException e){
			System.out.println("Invalid Picture");
		}
		try{
			meteor = ImageIO.read(new File("meteor.png"));
		}catch(IOException e){
			System.out.println("Invalid Picture");
		}
		
	}
}
