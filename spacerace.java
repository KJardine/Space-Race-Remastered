import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import javax.swing.event.*;

public class spacerace implements ActionListener, KeyListener{
	//Properties
	JFrame theFrame = new JFrame("Space Race");
	MMPanel mmPanel = new MMPanel();
	CarPanel carPanel = new CarPanel();
	GamePanel gamePanel = new GamePanel();
	HelpPanel helpPanel = new HelpPanel();
	DemoPanel demoPanel = new DemoPanel();
	CreditsPanel creditsPanel = new CreditsPanel();
	Timer thetimer = new Timer(1000/60, this);
	//Networking Panel
	JPanel netPanel;
	SuperSocketMaster ssm;
	JButton serverBut;
	JButton clientBut;
	JTextField ipaddress;
	JTextField port;
	JLabel ipaddresslabel;
	JLabel portlabel;
	JButton nextBut;
	int PlayerNum;
	String strMessage;
	
	//MM Panel
	JButton playButton;
	JButton helpButton;
	JButton creditsButton;
	JButton quitButton;
	JLabel mmLabel;
	
	
	//Car Selection Panel
	JButton raceButton;
	JButton selectionReturn;
	
	JButton speederBut;
	JButton jugBut;
	JButton druidBut;
	JButton sportBut;
	
	JLabel speederLabel;
	JLabel jugLabel;
	JLabel druidLabel;
	JLabel sportLabel;
	
	int intCar;
	
	int intP1Ready = 0;
	int intP2Ready = 0;
	
	//Actual Game Panel
	JTextField texttosend;
	JTextArea chatArea;
	JScrollPane theScroll;
	JButton chatSend;


	//Help Panel
	JButton demoButton;
	JButton helpReturn;
	
	//Demo Panel
	JButton demoReturn;
	boolean demoPanelTorF;
	
	//Credits Panel
	JButton creditsReturn;
	
	//Methods
	public void actionPerformed(ActionEvent evt){
		if(evt.getSource() == thetimer){
			gamePanel.repaint();
			demoPanel.repaint();
			if(PlayerNum == 1){
				gamePanel.intY1 = gamePanel.intY1 + gamePanel.intP1Move;
			}
			if(PlayerNum == 2){
				gamePanel.intY2 = gamePanel.intY2 + gamePanel.intP2Move;
			}
		}
		//Networking
		//Client Button
		if(evt.getSource() == clientBut){
		  serverBut.setEnabled(false); 
		  clientBut.setEnabled(false); 
		  ipaddress.setEnabled(false);
		  port.setEnabled(false);
		  ssm = new SuperSocketMaster(ipaddress.getText(), Integer.parseInt(port.getText()), this);
		  boolean gotConnect = ssm.connect();
		  if(gotConnect){
			PlayerNum = 2;
			gamePanel.strPlayerNum = String.valueOf(PlayerNum);
			System.out.println("Client Connected " +PlayerNum);
		  }else{
			serverBut.setEnabled(true); 
			clientBut.setEnabled(true); 
			ipaddress.setEnabled(true);
			port.setEnabled(true);
		  }
		 
		 //server Button
		}else if(evt.getSource() == serverBut){
		  serverBut.setEnabled(false); 
		  clientBut.setEnabled(false); 
		  ipaddress.setEnabled(false);
		  port.setEnabled(false);     
		  ssm = new SuperSocketMaster(Integer.parseInt(port.getText()), this);
		  boolean gotConnect = ssm.connect();
		  if(gotConnect){
			PlayerNum = 1;
			gamePanel.strPlayerNum = String.valueOf(PlayerNum);
			System.out.println("Server Connected " +PlayerNum);
		  }else{
			serverBut.setEnabled(true); 
			clientBut.setEnabled(true); 
			ipaddress.setEnabled(true);
			port.setEnabled(true);
		  }
		  
		}
		//act upon messages based on their heading (GAME, P1, P2 etc)
		if(evt.getSource() == ssm){
			strMessage = ssm.readText();
			if(strMessage.startsWith("GAME")){
				String[] messageArray = strMessage.split("\\s*,\\s*");
				System.out.println(strMessage);
				if(PlayerNum == 2){
					gamePanel.intP1Y = Integer.parseInt(messageArray[1]);
					gamePanel.intP1X = Integer.parseInt(messageArray[2]);
				}
				if(PlayerNum == 1){
					gamePanel.intP2Y = Integer.parseInt(messageArray[1]);
					gamePanel.intP2X = Integer.parseInt(messageArray[2]);
				}
			}
			if(strMessage.startsWith("CAR1")){
				String[] messageArray = strMessage.split("\\s*,\\s*");
				gamePanel.strP1Car = messageArray[1];
			}
			if(strMessage.startsWith("CAR2")){
				String[] messageArray = strMessage.split("\\s*,\\s*");
				gamePanel.strP2Car = messageArray[1];
			}
			if(strMessage.startsWith("METEORXY")){
				String[] messageArray = strMessage.split("\\s*,\\s*");
				gamePanel.intRandX2[Integer.parseInt(messageArray[1])] = Integer.parseInt(messageArray[2]);
				gamePanel.intRandY2[Integer.parseInt(messageArray[1])] = Integer.parseInt(messageArray[3]);
				System.out.println(gamePanel.intRandX2[Integer.parseInt(messageArray[1])] +", " +gamePanel.intRandY2[Integer.parseInt(messageArray[1])]);
			}
			if(strMessage.startsWith("P1") || strMessage.startsWith("P2")){
				chatArea.append(ssm.readText() + "\n");
				chatArea.setCaretPosition(chatArea.getDocument().getLength());
				theFrame.setFocusable(true);
				theFrame.requestFocus(); 
			}
		}
		if(evt.getSource() == chatSend){
			if(ssm != null){
				if(PlayerNum == 1){
					ssm.sendText("P1: " + texttosend.getText());
				}else if(PlayerNum == 2){
					ssm.sendText("P2: " + texttosend.getText());
				}
			}
		}
		//Change Panels based on the buttons
		if(evt.getSource() == nextBut){
			theFrame.setContentPane(mmPanel);
			theFrame.pack();
			theFrame.setVisible(true);
			theFrame.setResizable(false);
			theFrame.revalidate();
			theFrame.addKeyListener(this);
		}
		if(evt.getSource() == playButton){
			theFrame.setContentPane(carPanel);
			theFrame.pack();
			theFrame.setVisible(true);
			theFrame.setResizable(false);
			theFrame.revalidate();
			theFrame.addKeyListener(this);
			if(PlayerNum == 1){
				while(gamePanel.intCount < 8){
					gamePanel.intRandX1[gamePanel.intCount] = gamePanel.random.nextInt(1180);
					gamePanel.intRandY1[gamePanel.intCount] = -(gamePanel.random.nextInt(5820));
					System.out.println(gamePanel.intRandX1[gamePanel.intCount] + ", " + gamePanel.intRandY1[gamePanel.intCount]);
					ssm.sendText("METEORXY, "+gamePanel.intCount+","+gamePanel.intRandX1[gamePanel.intCount] + ", " + gamePanel.intRandY1[gamePanel.intCount]);
					gamePanel.intCount = gamePanel.intCount +1;
				}
			}
		}
		if(evt.getSource() == helpButton){
			theFrame.setContentPane(helpPanel);
			theFrame.pack();
			theFrame.setVisible(true);
			theFrame.setResizable(false);
			theFrame.revalidate();
			theFrame.addKeyListener(this);
		}
		if(evt.getSource() == chatSend){
			theFrame.setContentPane(gamePanel);
			theFrame.pack();
			theFrame.setVisible(true);
			theFrame.setResizable(false);
			theFrame.revalidate();
			theFrame.addKeyListener(this);
		}
		if(evt.getSource() == creditsButton){
			theFrame.setContentPane(creditsPanel);
			theFrame.pack();
			theFrame.setVisible(true);
			theFrame.setResizable(false);
			theFrame.revalidate();
			theFrame.addKeyListener(this);
		}
		if(evt.getSource() == demoButton){
			theFrame.setContentPane(demoPanel);
			theFrame.pack();
			theFrame.setVisible(true);
			theFrame.setResizable(false);
			theFrame.revalidate();
			theFrame.addKeyListener(this);
			demoPanelTorF = true;
		}
		if(evt.getSource() == quitButton){
			System.exit(0);
		}
		
		//Car Selection Buttons
		if(evt.getSource() == speederBut){
			theFrame.setContentPane(gamePanel);
			theFrame.pack();
			theFrame.setVisible(true);
			theFrame.setResizable(false);
			theFrame.revalidate();
			intCar = 1;
			System.out.println("Car: "+intCar + ", Player: "+PlayerNum);
			if(PlayerNum == 1){
				gamePanel.strP1Car = carPanel.car1Image;
				ssm.sendText("CAR1,"+carPanel.car1Image);
				gamePanel.intP1Speed = (carPanel.car1Speed)/75;
				gamePanel.intP1Weight = carPanel.car1Weight;
				gamePanel.intP1Red = 1;
			}
			if(PlayerNum == 2){
				gamePanel.strP2Car = carPanel.car1Image;
				ssm.sendText("CAR2,"+carPanel.car1Image);
				gamePanel.intP2Speed = (carPanel.car1Speed)/75;
				gamePanel.intP2Weight = carPanel.car1Weight;
				gamePanel.intP2Red = 1;
			}
		}
		if(evt.getSource() == jugBut){
			theFrame.setContentPane(gamePanel);
			theFrame.pack();
			theFrame.setVisible(true);
			theFrame.setResizable(false);
			theFrame.revalidate();
			intCar = 2;
			System.out.println("Car: "+intCar + ", Player: "+PlayerNum);
			if(PlayerNum == 1){
				gamePanel.strP1Car = carPanel.car2Image;
				ssm.sendText("CAR1,"+carPanel.car2Image);
				gamePanel.intP1Speed = (carPanel.car2Speed)/75;
				gamePanel.intP1Weight = carPanel.car2Weight;
				gamePanel.intP1Red = 1;
			}
			if(PlayerNum == 2){
				gamePanel.strP2Car = carPanel.car2Image;
				ssm.sendText("CAR2,"+carPanel.car2Image);
				gamePanel.intP2Speed = (carPanel.car2Speed)/75;
				gamePanel.intP2Weight = carPanel.car2Weight;
				gamePanel.intP2Red = 1;
			}
		}
		if(evt.getSource() == druidBut){
			theFrame.setContentPane(gamePanel);
			theFrame.pack();
			theFrame.setVisible(true);
			theFrame.setResizable(false);
			theFrame.revalidate();
			intCar = 3;
			System.out.println("Car: "+intCar + ", Player: "+PlayerNum);
			if(PlayerNum == 1){
				gamePanel.strP1Car = carPanel.car3Image;
				ssm.sendText("CAR1,"+carPanel.car3Image);
				gamePanel.intP1Speed = (carPanel.car3Speed)/75;
				gamePanel.intP1Weight = carPanel.car3Weight;
				gamePanel.intP1Red = 1;
			}
			if(PlayerNum == 2){
				gamePanel.strP2Car = carPanel.car3Image;
				ssm.sendText("CAR2,"+carPanel.car3Image);
				gamePanel.intP2Speed = (carPanel.car3Speed)/75;
				gamePanel.intP2Weight = carPanel.car3Weight;
				gamePanel.intP2Red = 1;
			}
		}
		if(evt.getSource() == sportBut){
			theFrame.setContentPane(gamePanel);
			theFrame.pack();
			theFrame.setVisible(true);
			theFrame.setResizable(false);
			theFrame.revalidate();
			intCar = 4;
			System.out.println("Car: "+intCar + ", Player: "+PlayerNum);
			if(PlayerNum == 1){
				gamePanel.strP1Car = carPanel.car4Image;
				ssm.sendText("CAR1,"+carPanel.car4Image);
				gamePanel.intP1Speed = (carPanel.car4Speed)/75;
				gamePanel.intP1Weight = carPanel.car4Weight;
				gamePanel.intP1Red = 1;
			}
			if(PlayerNum == 2){
				gamePanel.strP2Car = carPanel.car4Image;
				ssm.sendText("CAR2,"+carPanel.car4Image);
				gamePanel.intP2Speed = (carPanel.car4Speed)/75;
				gamePanel.intP2Weight = carPanel.car4Weight;
				gamePanel.intP2Red = 1;
			}
		}

		
		//Return Buttons in different panels
		if(evt.getSource() == selectionReturn || evt.getSource() == helpReturn || evt.getSource() == creditsReturn){
			theFrame.setContentPane(mmPanel);
			theFrame.pack();
			theFrame.setVisible(true);
			theFrame.setResizable(false);
			theFrame.revalidate();
		}
		if(evt.getSource() == demoReturn){
			theFrame.setContentPane(helpPanel);
			theFrame.pack();
			theFrame.setVisible(true);
			theFrame.setResizable(false);
			theFrame.revalidate();
			demoPanelTorF = false;
		}
	}
		
	public void stateChanged(ChangeEvent evt){
	}
	/** stop car if key releases */
	public void keyReleased(KeyEvent evt){
		
		// Demo Controls
		
		if(evt.getKeyChar() == 'w'){
			if(PlayerNum == 1){
				gamePanel.intP1Move = 0;
				ssm.sendText("GAME,"+String.valueOf(gamePanel.intP1Y)+","+String.valueOf(gamePanel.intP1X));
			}else if(PlayerNum == 2){
				gamePanel.intP2Move = 0;
				ssm.sendText("GAME,"+String.valueOf(gamePanel.intP2Y)+","+String.valueOf(gamePanel.intP2X));
			}
		}
		if(evt.getKeyChar() == 's'){
			if(PlayerNum == 1){
				gamePanel.intP1Move = 0;
				ssm.sendText("GAME,"+String.valueOf(gamePanel.intP1Y)+","+String.valueOf(gamePanel.intP1X));
			}else if(PlayerNum == 2){
				gamePanel.intP2Move = 0;
				ssm.sendText("GAME,"+String.valueOf(gamePanel.intP2Y)+","+String.valueOf(gamePanel.intP2X));
			}
		}
		if(evt.getKeyChar() == 'a'){
			if(PlayerNum == 1){
				gamePanel.intP1MoveHor = 0;
				ssm.sendText("GAME,"+String.valueOf(gamePanel.intP1Y)+","+String.valueOf(gamePanel.intP1X));
			}else if(PlayerNum == 2){
				gamePanel.intP2MoveHor = 0;
				ssm.sendText("GAME,"+String.valueOf(gamePanel.intP2Y)+","+String.valueOf(gamePanel.intP2X));
			}
		}
		if(evt.getKeyChar() == 'd'){
			if(PlayerNum == 1){
				gamePanel.intP1MoveHor = 0;
				ssm.sendText("GAME,"+String.valueOf(gamePanel.intP1Y)+","+String.valueOf(gamePanel.intP1X));
			}else if(PlayerNum == 2){
				gamePanel.intP2MoveHor = 0;
				ssm.sendText("GAME,"+String.valueOf(gamePanel.intP2Y)+","+String.valueOf(gamePanel.intP2X));
			}
		}
	}
	/** move car if key pressed  */
	public void keyPressed(KeyEvent evt){
		if(demoPanelTorF = true){
			if(evt.getKeyChar() == 'w'){
				demoPanel.intDemoPosY = demoPanel.intDemoPosY - 1;
			}else if(evt.getKeyChar() == 's'){
				demoPanel.intDemoPosY = demoPanel.intDemoPosY + 1;
			}else if(evt.getKeyChar() == 'a'){
				demoPanel.intDemoPosX = demoPanel.intDemoPosX - 1;
			}else if(evt.getKeyChar() == 'd'){
				demoPanel.intDemoPosX = demoPanel.intDemoPosX + 1;	
		}
		//Move up with w
		if(evt.getKeyChar() == 'w'){
			if(PlayerNum == 1){
				gamePanel.intP1Move = gamePanel.intP1Speed;
				ssm.sendText("GAME,"+String.valueOf(gamePanel.intP1Y)+","+String.valueOf(gamePanel.intP1X));
				
			}else if(PlayerNum == 2){
				gamePanel.intP2Move = gamePanel.intP2Speed;
				ssm.sendText("GAME,"+String.valueOf(gamePanel.intP2Y)+","+String.valueOf(gamePanel.intP2X));
			}
		}
		//move down with s
		if(evt.getKeyChar() == 's'){
			if(PlayerNum == 1){
				gamePanel.intP1Move = -gamePanel.intP1Speed;
				ssm.sendText("GAME,"+String.valueOf(gamePanel.intP1Y)+","+String.valueOf(gamePanel.intP1X));
				
			}else if(PlayerNum == 2){
				gamePanel.intP2Move = -gamePanel.intP2Speed;
				ssm.sendText("GAME,"+String.valueOf(gamePanel.intP2Y)+","+String.valueOf(gamePanel.intP2X));
			}
		}
		//move left with a
		if(evt.getKeyChar() == 'a'){
			if(PlayerNum == 1){
				gamePanel.intP1MoveHor = -gamePanel.intP1Speed;
				ssm.sendText("GAME,"+String.valueOf(gamePanel.intP1Y)+","+String.valueOf(gamePanel.intP1X));
				
			}else if(PlayerNum == 2){
				gamePanel.intP2MoveHor = -gamePanel.intP2Speed;
				ssm.sendText("GAME,"+String.valueOf(gamePanel.intP2Y)+","+String.valueOf(gamePanel.intP2X));
			}
		}
		//move right with d
		if(evt.getKeyChar() == 'd'){
			if(PlayerNum == 1){
				gamePanel.intP1MoveHor = gamePanel.intP1Speed;
				ssm.sendText("GAME,"+String.valueOf(gamePanel.intP1Y)+","+String.valueOf(gamePanel.intP1X));
				
			}else if(PlayerNum == 2){
				gamePanel.intP2MoveHor = gamePanel.intP2Speed;
				ssm.sendText("GAME,"+String.valueOf(gamePanel.intP2Y)+","+String.valueOf(gamePanel.intP2X));
			}
		}
	}
}
	public void keyTyped(KeyEvent evt){
	}
	
	//Constructor
	public spacerace(){
		//Networking Panel 
		netPanel = new JPanel();
		netPanel.setLayout(null);
		netPanel.setPreferredSize(new Dimension(1280, 720));
		theFrame.pack();
		serverBut = new JButton("Server");
		serverBut.setSize(300, 25);
		serverBut.setLocation(0,0);
		serverBut.addActionListener(this);
		netPanel.add(serverBut);
		clientBut = new JButton("Client");
		clientBut.setSize(300, 25);
		clientBut.setLocation(0, 25);
		clientBut.addActionListener(this);
		netPanel.add(clientBut);
		ipaddresslabel = new JLabel("ip");
		ipaddresslabel.setHorizontalAlignment(SwingConstants.CENTER);
		ipaddresslabel.setSize(300, 25);
		ipaddresslabel.setLocation(0, 50);
		netPanel.add(ipaddresslabel);
		ipaddress = new JTextField("localhost");
		ipaddress.setSize(300,25);
		ipaddress.setLocation(0, 75);
		netPanel.add(ipaddress);
		portlabel = new JLabel("port");
		portlabel.setHorizontalAlignment(SwingConstants.CENTER);
		portlabel.setSize(300, 25);
		portlabel.setLocation(0, 100);
		netPanel.add(portlabel);
		port = new JTextField("6112");
		port.setSize(300,25);
		port.setLocation(0, 125);
		netPanel.add(port);
		nextBut = new JButton("Next");
		nextBut.setSize(100, 50);
		nextBut.setLocation(1150, 650);
		nextBut.addActionListener(this);
		netPanel.add(nextBut);
		
			
		//Main Menu Panel
		mmPanel.setLayout(null);
		mmPanel.setPreferredSize(new Dimension(1280, 720));
		
		playButton = new JButton("Play");
		playButton.setSize(200, 50);
		playButton.setLocation(540, 200);
		playButton.addActionListener(this);
		mmPanel.add(playButton);
		
		helpButton = new JButton("Help");
		helpButton.setSize(200, 50);
		helpButton.setLocation(540, 375);
		helpButton.addActionListener(this);
		mmPanel.add(helpButton);
		
		creditsButton = new JButton("Credits");
		creditsButton.setSize(200, 50);
		creditsButton.setLocation(540, 550);
		creditsButton.addActionListener(this);
		mmPanel.add(creditsButton);
		
		quitButton = new JButton("Quit");
		quitButton.setSize(100, 50);
		quitButton.setLocation(20, 650);
		quitButton.addActionListener(this);
		mmPanel.add(quitButton);
		
		//Car Selection panel
		carPanel.setLayout(null);
		carPanel.setPreferredSize(new Dimension(1280, 720));
		
		selectionReturn = new JButton("Return");
		selectionReturn.setSize(100, 50);
		selectionReturn.setLocation(20, 650);
		selectionReturn.addActionListener(this);
		carPanel.add(selectionReturn);
		
		speederBut = new JButton("Choose");
		speederBut.setSize(114, 50);
		speederBut.setLocation(300, 550);
		speederBut.addActionListener(this);
		carPanel.add(speederBut);
		
		jugBut = new JButton("Choose");
		jugBut.setSize(114, 50);
		jugBut.setLocation(500, 550);
		jugBut.addActionListener(this);
		carPanel.add(jugBut);
		
		druidBut = new JButton("Choose");
		druidBut.setSize(114, 50);
		druidBut.setLocation(700, 550);
		druidBut.addActionListener(this);
		carPanel.add(druidBut);
		
		sportBut = new JButton("Choose");
		sportBut.setSize(114, 50);
		sportBut.setLocation(900, 550);
		sportBut.addActionListener(this);
		carPanel.add(sportBut);

		speederLabel = new JLabel("<html>"+carPanel.car1Name+"<br> Speed: "+carPanel.car1Speed+"<br> Weight: "+carPanel.car1Weight+"</html>");
		speederLabel.setHorizontalAlignment(SwingConstants.CENTER);
		speederLabel.setSize(114, 250);
		speederLabel.setLocation(300, 350);
		carPanel.add(speederLabel);
		speederLabel.setForeground(Color.white);
		
		jugLabel = new JLabel("<html>"+carPanel.car2Name+"<br> Speed: "+carPanel.car2Speed+"<br> Weight: "+carPanel.car2Weight+"</html>");
		jugLabel.setHorizontalAlignment(SwingConstants.CENTER);
		jugLabel.setSize(114, 250);
		jugLabel.setLocation(500, 350);
		carPanel.add(jugLabel);
		jugLabel.setForeground(Color.white);
		
		druidLabel = new JLabel("<html>"+carPanel.car3Name+"<br> Speed: "+carPanel.car3Speed+"<br> Weight: "+carPanel.car3Weight+"</html>");
		druidLabel.setHorizontalAlignment(SwingConstants.CENTER);
		druidLabel.setSize(114, 250);
		druidLabel.setLocation(700, 350);
		carPanel.add(druidLabel);
		druidLabel.setForeground(Color.white);
		
		sportLabel = new JLabel("<html>"+carPanel.car4Name+"<br> Speed: "+carPanel.car4Speed+"<br> Weight: "+carPanel.car4Weight+"</html>");
		sportLabel.setHorizontalAlignment(SwingConstants.CENTER);
		sportLabel.setSize(114, 250);
		sportLabel.setLocation(900, 350);
		carPanel.add(sportLabel);
		sportLabel.setForeground(Color.white);
		
		
		
		
		
		
		//Actual Game Panel
		gamePanel.setLayout(null);
		gamePanel.setPreferredSize(new Dimension(1280, 720));
		
		texttosend = new JTextField();
		texttosend.setEnabled(true);
		texttosend.setSize(200,50);
		texttosend.setLocation(980, 670);
		texttosend.addActionListener(this);
		gamePanel.add(texttosend);
		
		chatArea = new JTextArea();
		chatArea.setEnabled(true);
		theScroll = new JScrollPane(chatArea);
		theScroll.setSize(300, 200);
		theScroll.setLocation(980, 470);
		gamePanel.add(theScroll); 
		
		chatSend = new JButton("Send");
		chatSend.setSize(100, 50);
		chatSend.setLocation(1180, 670);
		chatSend.addActionListener(this);
		gamePanel.add(chatSend);
		
		
		
		//Help Panel
		helpPanel.setLayout(null);
		helpPanel.setPreferredSize(new Dimension(1280, 720));
		
		demoButton = new JButton("Demo Controls");
		demoButton.setSize(150, 50);
		demoButton.setLocation(1100, 650);
		demoButton.addActionListener(this);
		helpPanel.add(demoButton);
		
		helpReturn = new JButton("Return");
		helpReturn.setSize(100, 50);
		helpReturn.setLocation(20, 650);
		helpReturn.addActionListener(this);
		helpPanel.add(helpReturn);
		
		//Help Panel Demo Controls
		demoPanel.setLayout(null);
		demoPanel.setPreferredSize(new Dimension(1280, 720));
		
		demoReturn = new JButton("Return");
		demoReturn.setSize(100, 50);
		demoReturn.setLocation(20, 650);
		demoReturn.addActionListener(this);
		demoPanel.add(demoReturn);
		
		//Credits panel
		creditsPanel.setLayout(null);
		creditsPanel.setPreferredSize(new Dimension(1280, 720));
		
		creditsReturn = new JButton("Return");
		creditsReturn.setSize(100, 50);
		creditsReturn.setLocation(20, 650);
		creditsReturn.addActionListener(this);
		creditsPanel.add(creditsReturn);
		
		
		//the frame
		theFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		theFrame.setContentPane(netPanel);
		theFrame.pack();
		theFrame.addWindowListener(new java.awt.event.WindowAdapter() {
		  @Override
		  public void windowClosing(java.awt.event.WindowEvent windowEvent) {
			if(ssm != null){
			  ssm.disconnect();
			}
		  }
		});
		theFrame.setVisible(true);
		theFrame.setResizable(false);
		theFrame.addKeyListener(this);
		theFrame.setFocusable(true);
		theFrame.requestFocus(); 
		thetimer.start();
	}
	
	//main method
	public static void main(String[] args){
		new spacerace();
	}
}
