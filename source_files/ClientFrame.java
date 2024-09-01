import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import java.util.Arrays;

import javax.swing.*;
import javax.swing.border.*;


public class ClientFrame extends JFrame{
	private static JPanel homePanel;
	private static JPanel HomeP;
	private static JPanel LoginP;
	private static JPanel titlepanel;
	private static JLabel titlelabel;
	private static JButton gameStart;
	private static JPanel instructionPanel;
	private static JPanel PW_Panel;
	private static JPanel ID_Panel;
	private static JLabel instructionLabel;
	private static JLabel PW_Label1;
	private static JTextField enterPW;
	private static JLabel ID_Label1;
	private static JTextField enterID;
	private static JLabel background_1;
	private static CardLayout card = new CardLayout();
	private static JButton Login;
	private static JButton startCreatAccount;
	private static JPanel SigninP;
	private static JLabel instructionLabel2;
	private static JLabel ID_Label2;
	private static JLabel PW_Label2;
	private static JTextField enterID_2;
	private static JTextField enterPW_2;
	private static JButton endCreatAccount;
	private static JPanel RoomsP;
	private static JButton logOut;
	private static JTextPane roomsText;
	private static JPanel WaitingP;
	private static JPanel BattleP;
	private static JTextPane waitingText;
	private static JButton ExitButton;
	private static JPanel panel;
	private static JPanel panel_1;
	private static JButton punchB;
	private static JButton sunPunchB;
	private static JButton energyWaveB;
	private static JButton crossArmsB;
	private static JButton closeEyesB;
	private static JButton teleportB;
	private static JPanel panel_2;
	private static JButton wonkiokB;
	private static JButton collectEnergyB;
	private static JLabel motion1;
	private static JLabel motion2;
	private static JButton JoinButton;
	private JPanel myNamePanel;
	private JPanel combatNamePanel;
	private static JLabel myNameLabel;
	private static JLabel combatNameLabel;
	private static ActionListener ExitButtonListener;
	private static JLabel timerLabel;
	
	private static String combatID;
	private static String myID;
	private static Socket mySocket;
	private static OutputStream clientOutputStream;
	private static InputStream clientInputStream;
	private static BufferedOutputStream Writer;
	private static BufferedInputStream Reader;
	private static int battleFlag = 0;
	private static int currentSlideNumber = 1;
	private static byte[] Input = new byte[1000];
	private static int currentEnergy = 0;
	
	private ImageIcon DRAGONBALL = new ImageIcon();
	private ImageIcon KEEE = new ImageIcon();
	private ImageIcon PUNCH = new ImageIcon();
	private ImageIcon TAEYANGKWON = new ImageIcon();
	private ImageIcon ENERGYPA = new ImageIcon();
	private ImageIcon WONKIOK = new ImageIcon();
	private ImageIcon CROSSARMS = new ImageIcon();
	private ImageIcon CLOSEEYES = new ImageIcon();
	private ImageIcon TELEPORT = new ImageIcon();
	private ImageIcon START = new ImageIcon();
	
	// protocol method which returns integer number to corresponding String, which indicates what
	// data is going to server
	// check line 243 for example
	public static int myProtocol(String s)
	{
		if(s.equals("Create_Account")) return 3;
		else if(s.equals("Check_Account")) return 4;
		else if(s.equals("LogOut")) return 5;
		else if(s.equals("Join")) return 6;
		else if(s.equals("ExitWaiting")) return 7;
		else if(s.equals("Combat_Ready?")) return 8;
		else if(s.equals("Motion")) return 9;
		else return 0;
	}
	
	// Constructor of client program, ClientFrame.
	public ClientFrame()
	{
		START = new ImageIcon(getClass().getResource("STARTIMAGE.jpg"));
		DRAGONBALL = new ImageIcon(getClass().getResource("DRAGONBALLS.jpg"));
		KEEE = new ImageIcon(getClass().getResource("KEEE.jpg"));
		PUNCH = new ImageIcon(getClass().getResource("PUNCH.png"));
		TAEYANGKWON = new ImageIcon(getClass().getResource("TAEYANGKWON.jpg"));
		ENERGYPA = new ImageIcon(getClass().getResource("ENERGYPA.jpg"));
		WONKIOK = new ImageIcon(getClass().getResource("WONKIOK.jpg"));
		CROSSARMS = new ImageIcon(getClass().getResource("CROSSARMS.jpg"));
		CLOSEEYES = new ImageIcon(getClass().getResource("CLOSEEYES.jpg"));
		TELEPORT = new ImageIcon(getClass().getResource("TELEPORT.png"));
		
		try
		{
			// Connecting to Server. Default is this computer, so if you want to connect to server computer,
			// you can simply write the IP address of server computer inside "" below.
			mySocket = new Socket("", 5123);
			clientOutputStream = mySocket.getOutputStream();
			clientInputStream = mySocket.getInputStream();
			Writer = new BufferedOutputStream(clientOutputStream);
			Reader = new BufferedInputStream(clientInputStream);
		}
		catch (UnknownHostException e)
		{
			e.printStackTrace();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		
		setTitle("DragonBallGame");
		setBackground(new Color(255, 255, 255));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 736, 563);
		
		homePanel = new JPanel();
		homePanel.setBackground(Color.BLACK);
		setContentPane(homePanel);
		homePanel.setLayout(card);
		
		
		/*
		 * panel 1 Title Page 
		 */
		HomeP = new JPanel();
		homePanel.add(HomeP, "Slide_1");
		currentSlideNumber = 1;
		HomeP.setLayout(null);
		
		background_1 = new JLabel("");
		background_1.setIcon(START);
		background_1.setBounds(0, 62, 720, 411);
		HomeP.add(background_1);
		
		titlepanel = new JPanel();
		titlepanel.setBounds(0, 0, 720, 63);
		titlepanel.setBorder(new LineBorder(Color.RED, 5));
		titlepanel.setBackground(Color.WHITE);
		HomeP.add(titlepanel);
		
		titlelabel = new JLabel("Dragon Ball Game!");
		titlelabel.setFont(new Font("Arial Black", Font.PLAIN, 30));
		titlelabel.setHorizontalAlignment(SwingConstants.CENTER);
		titlepanel.add(titlelabel);
		
		//Start_Button_Action
		gameStart = new JButton("Start!");
		gameStart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				card.show(homePanel, "Slide_2");
				currentSlideNumber = 2;
				setBounds(getBounds().x, getBounds().y, 517, 600);
			}
		});
		gameStart.setBounds(0, 472, 720, 51);
		gameStart.setBackground(Color.GREEN);
		gameStart.setFont(new Font("Arial Black", Font.PLAIN, 30));
		HomeP.add(gameStart);
		
		/*
		 * panel 2 Enter Account 
		 */
		LoginP = new JPanel();
		LoginP.setBackground(Color.CYAN);
		homePanel.add(LoginP, "Slide_2");
		LoginP.setLayout(null);
		
		instructionPanel = new JPanel();
		instructionPanel.setBorder(new LineBorder(Color.RED, 5));
		instructionPanel.setBackground(Color.WHITE);
		instructionPanel.setBounds(0, 0, 500, 50);
		LoginP.add(instructionPanel);
		instructionPanel.setLayout(new BorderLayout(0, 0));
		
		instructionLabel = new JLabel("Enter your ID/PW");
		instructionLabel.setHorizontalAlignment(SwingConstants.CENTER);
		instructionLabel.setFont(new Font("Arial Black", Font.PLAIN, 30));
		instructionPanel.add(instructionLabel);
		
		PW_Panel = new JPanel();
		PW_Panel.setBackground(Color.ORANGE);
		PW_Panel.setBounds(50, 451, 300, 40);
		LoginP.add(PW_Panel);
		PW_Panel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		PW_Label1 = new JLabel("PW");
		PW_Label1.setHorizontalAlignment(SwingConstants.CENTER);
		PW_Label1.setFont(new Font("Arial Black", Font.PLAIN, 20));
		PW_Panel.add(PW_Label1);
		
		enterPW = new JTextField();
		enterPW.setFont(new Font("Arial Black", Font.PLAIN, 20));
		PW_Panel.add(enterPW);
		enterPW.setColumns(10);
		
		ID_Panel = new JPanel();
		ID_Panel.setBackground(Color.ORANGE);
		ID_Panel.setBounds(50, 400, 300, 40);
		LoginP.add(ID_Panel);
		
		ID_Label1 = new JLabel("ID");
		ID_Label1.setFont(new Font("Arial Black", Font.PLAIN, 20));
		ID_Label1.setHorizontalAlignment(SwingConstants.CENTER);
		ID_Panel.add(ID_Label1);
		
		enterID = new JTextField();
		enterID.setFont(new Font("Arial Black", Font.PLAIN, 20));
		ID_Panel.add(enterID);
		enterID.setColumns(10);
		
		Login = new JButton("Go!");
		
		//Login - Checks Account
		Login.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				try {
					Writer.write(myProtocol("Check_Account"));
					Writer.write(enterID.getText().getBytes().length);
					Writer.write(enterPW.getText().getBytes().length);
					Writer.write(enterID.getText().getBytes());
					Writer.write(enterPW.getText().getBytes());
					Writer.flush();
					
					while(Reader.available() == 0) {
					}
					int flag = Reader.read();
					
					if(flag == 0) {
						myID = enterID.getText();
						card.show(homePanel, "Slide_4");
						currentSlideNumber = 4;
						setBounds(getBounds().x, getBounds().y, 517, 600);
						setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
					}
					else if(flag == 1) {
						JOptionPane.showMessageDialog(null, "No Such ID Exist.");
					}		
					else if(flag == 2) {
						JOptionPane.showMessageDialog(null, "Wrong Password.");
					}
					else if(flag == 3) {
						JOptionPane.showMessageDialog(null, "Already Logged In.");
					}
					else {
						JOptionPane.showMessageDialog(null, "Something REALLY WRONG.... SORRY!! Close Server and Client and redo all.");
						card.show(homePanel, "Slide_1");
						setBounds(getBounds().x, getBounds().y, 736, 562);
						currentSlideNumber = 1;
					}
				}catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		});
		Login.setBackground(Color.GREEN);
		Login.setFont(new Font("Arial Black", Font.PLAIN, 12));
		Login.setBounds(399, 411, 60, 60);
		LoginP.add(Login);
		
		startCreatAccount = new JButton("Creat Account");
		startCreatAccount.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				card.show(homePanel, "Slide_3");
				currentSlideNumber = 3;
				setBounds(getBounds().x, getBounds().y, 517, 600);
			}
		});
		startCreatAccount.setBackground(Color.ORANGE);
		startCreatAccount.setFont(new Font("Arial Black", Font.PLAIN, 10));
		startCreatAccount.setBounds(384, 76, 116, 40);
		LoginP.add(startCreatAccount);
		
		/*
		 * panel 3 : Create Account 
		 */
		SigninP = new JPanel();
		homePanel.add(SigninP, "Slide_3");
		SigninP.setLayout(null);
		
		instructionLabel2 = new JLabel("Creat Account....");
		instructionLabel2.setHorizontalAlignment(SwingConstants.CENTER);
		instructionLabel2.setFont(new Font("Arial Black", Font.PLAIN, 30));
		instructionLabel2.setBounds(69, 28, 351, 80);
		SigninP.add(instructionLabel2);
		
		ID_Label2 = new JLabel("ID");
		ID_Label2.setBackground(Color.MAGENTA);
		ID_Label2.setHorizontalAlignment(SwingConstants.CENTER);
		ID_Label2.setFont(new Font("ï¿½ï¿½ï¿½ï¿½", Font.BOLD, 25));
		ID_Label2.setBounds(70, 300, 40, 40);
		SigninP.add(ID_Label2);
		
		PW_Label2 = new JLabel("PW");
		PW_Label2.setBackground(Color.MAGENTA);
		PW_Label2.setHorizontalAlignment(SwingConstants.CENTER);
		PW_Label2.setFont(new Font("ï¿½ï¿½ï¿½ï¿½", Font.BOLD, 25));
		PW_Label2.setBounds(70, 350, 40, 40);
		SigninP.add(PW_Label2);
		
		enterID_2 = new JTextField();
		enterID_2.setBounds(147, 300, 220, 40);
		SigninP.add(enterID_2);
		enterID_2.setColumns(10);
		
		enterPW_2 = new JTextField();
		enterPW_2.setBounds(147, 350, 220, 40);
		SigninP.add(enterPW_2);
		enterPW_2.setColumns(10);
		
		endCreatAccount = new JButton("Create!");
		
		
		// CreatAccount - makes an account, after checking whether there is same ID or not.
		// Current Version has limits of client, 1000...
		
		endCreatAccount.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{
				try {
					Writer.write(myProtocol("Create_Account"));
					Writer.write(enterID_2.getText().getBytes().length);
					Writer.write(enterPW_2.getText().getBytes().length);
					Writer.write(enterID_2.getText().getBytes());
					Writer.write(enterPW_2.getText().getBytes());
					Writer.flush();
					while(Reader.available() == 0) {
					}
					
					int flag = Reader.read();
					
					if(flag == 0) {
					}
					else if(flag == 1) {
						JOptionPane.showMessageDialog(null, "More than 1000 accounts are made! Sorry ^~^.");
					}
					else if(flag == 2) {
						JOptionPane.showMessageDialog(null, "That ID is already exist.");
					}
					else {
						JOptionPane.showMessageDialog(null, "Something REALLY WRONG.... SORRY!! Close Server and Client and redo all.");
					}
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				card.show(homePanel, "Slide_2");
				currentSlideNumber = 2;
				setBounds(getBounds().x, getBounds().y, 517, 600);
			}
		});
		endCreatAccount.setFont(new Font("ï¿½ï¿½ï¿½ï¿½", Font.BOLD, 12));
		endCreatAccount.setBounds(379, 331, 97, 23);
		SigninP.add(endCreatAccount);
		
		
		/*
		 * panel 4 : Game Room 
		 */
		RoomsP = new JPanel();
		RoomsP.setBackground(Color.WHITE);
		homePanel.add(RoomsP, "Slide_4");
		RoomsP.setLayout(null);
		
		logOut = new JButton("Log out");
		logOut.setBackground(Color.CYAN);
		logOut.setFont(new Font("Bauhaus 93", Font.PLAIN, 20));
		logOut.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
					try {
						Writer.write(myProtocol("LogOut"));
						Writer.write(myID.getBytes().length);
						Writer.write(myID.getBytes());
						Writer.flush();
						while(Reader.available() == 0) {
						}
						
						int flag = Reader.read();
						
						if(flag == 0) {
							JOptionPane.showMessageDialog(null, "Logged Out Successfully.");
							setBounds(getBounds().x, getBounds().y, 517, 600);
							card.show(homePanel, "Slide_2");
							currentSlideNumber = 2;
							setBounds(getBounds().x, getBounds().y, 517, 600);
							setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
						}
						else {
							JOptionPane.showMessageDialog(null, "Something REALLY WRONG.... SORRY!! Close Server and Client and redo all.");
						}
						
					}catch (IOException e1) {
						e1.printStackTrace();
					}
			}
		});
		logOut.setBounds(368, 470, 121, 81);
		RoomsP.add(logOut);
		
		roomsText = new JTextPane();
		roomsText.setEditable(false);
		roomsText.setBackground(Color.ORANGE);
		roomsText.setFont(new Font("Arial Black", Font.PLAIN, 15));
		roomsText.setText("- Click \"Empty\" room if you want to make new room.\r\n\r\n- Only 10 rooms available.");
		roomsText.setBounds(12, 440, 254, 111);
		RoomsP.add(roomsText);
		
		JoinButton = new JButton("Join Game!");
		JoinButton.setFont(new Font("Bauhaus 93", Font.PLAIN, 25));
		JoinButton.setBounds(12, 301, 254, 98);
		JoinButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				try {
					Writer.write(myProtocol("Join"));
					Writer.write(enterID.getText().getBytes().length);
					Writer.write(enterID.getText().getBytes());
					Writer.flush();
					
					while(Reader.available() == 0) {
					}
					int flag = Reader.read();
					if(flag == 0) {
						card.show(homePanel, "Slide_5");
						ExitButton.setEnabled(true);
						currentSlideNumber = 5;
						setBounds(getBounds().x, getBounds().y, 517, 600);
					}
					else if(flag == 1) {
						card.show(homePanel, "Battle_Slide");
						motion1.setIcon(null);
						motion2.setIcon(null);
						setBounds(getBounds().x, getBounds().y, 984, 657);
						myNameLabel.setText(myID);
						Reader.read(Input);
						combatID = new String(Arrays.copyOfRange(Input, 1, 1+Input[0]));
						combatNameLabel.setText(combatID);
						currentSlideNumber = 6;
					}
					else {
						JOptionPane.showMessageDialog(null, "Something REALLY WRONG.... SORRY!! Close Server and Client and redo all.");
						card.show(homePanel, "Slide_1");
						setBounds(getBounds().x, getBounds().y, 736, 562);
						currentSlideNumber = 1;
					}
					//When no combat exist now: create room
					//when combat exist: Join room of the combat
				}catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		});
		RoomsP.add(JoinButton);
		
		
		/*
		 * panel 5 : Waiting Room 
		 */
		WaitingP = new JPanel();
		WaitingP.setBackground(Color.LIGHT_GRAY);
		homePanel.add(WaitingP, "Slide_5");
		WaitingP.setLayout(null);
		
		waitingText = new JTextPane();
		waitingText.setBackground(Color.PINK);
		waitingText.setFont(new Font("Arial Black", Font.PLAIN, 34));
		waitingText.setText("Wating for your COMBAT....");
		waitingText.setBounds(0, 54, 501, 120);
		WaitingP.add(waitingText);
		
		ExitButton = new JButton("Exit");
		ExitButtonListener = new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					Writer.write(myProtocol("ExitWaiting"));
					Writer.flush();
					ExitButton.setEnabled(false);
					card.show(homePanel, "Slide_4");
					currentSlideNumber = 4;
					setBounds(getBounds().x, getBounds().y, 517, 600);
				}catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		};
		ExitButton.addActionListener(ExitButtonListener);
		ExitButton.setBackground(Color.CYAN);
		ExitButton.setFont(new Font("Arial Black", Font.PLAIN, 30));
		ExitButton.setBounds(362, 447, 127, 104);
		WaitingP.add(ExitButton);
		
		/*
		 * panel 6 : BATTLE!!!!!!!!!!!!!!!!!!!!!! 
		 */
	
		BattleP = new JPanel();
		BattleP.setBackground(Color.WHITE);
		homePanel.add(BattleP, "Battle_Slide");
		BattleP.setLayout(null);
		
		panel = new JPanel();
		panel.setBounds(0, 470, 350, 150);
		BattleP.add(panel);
		panel.setLayout(new GridLayout(0, 3, 0, 0));
		
		punchB = new JButton("Punch");
		punchB.setEnabled(false);
		punchB.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					Writer.write(myProtocol("Motion"));
					Writer.write(2);
					motion1.setIcon(getMotionImage(2));
					punchB.setEnabled(false);
					sunPunchB.setEnabled(false);
					energyWaveB.setEnabled(false);
					crossArmsB.setEnabled(false);
					closeEyesB.setEnabled(false);
					teleportB.setEnabled(false);
					wonkiokB.setEnabled(false);
					collectEnergyB.setEnabled(false);
					Writer.flush();
					battleFlag=1;
					currentEnergy = currentEnergy-1;
				}catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		});
		punchB.setFont(new Font("Arial Black", Font.PLAIN, 20));
		panel.add(punchB);
		
		sunPunchB = new JButton("SunPunch");
		sunPunchB.setEnabled(false);
		sunPunchB.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					Writer.write(myProtocol("Motion"));
					Writer.write(3);
					motion1.setIcon(getMotionImage(3));
					punchB.setEnabled(false);
					sunPunchB.setEnabled(false);
					energyWaveB.setEnabled(false);
					crossArmsB.setEnabled(false);
					closeEyesB.setEnabled(false);
					teleportB.setEnabled(false);
					wonkiokB.setEnabled(false);
					collectEnergyB.setEnabled(false);
					Writer.flush();
					battleFlag=1;
					currentEnergy = currentEnergy-2;
				}catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		});
		sunPunchB.setFont(new Font("Arial Black", Font.PLAIN, 12));
		panel.add(sunPunchB);
		
		energyWaveB = new JButton("EnergyWave");
		energyWaveB.setEnabled(false);
		energyWaveB.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					Writer.write(myProtocol("Motion"));
					Writer.write(4);
					motion1.setIcon(getMotionImage(4));
					punchB.setEnabled(false);
					sunPunchB.setEnabled(false);
					energyWaveB.setEnabled(false);
					crossArmsB.setEnabled(false);
					closeEyesB.setEnabled(false);
					teleportB.setEnabled(false);
					wonkiokB.setEnabled(false);
					collectEnergyB.setEnabled(false);
					Writer.flush();
					battleFlag=1;
					currentEnergy = currentEnergy-3;
				}catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		});
		energyWaveB.setFont(new Font("Arial Black", Font.PLAIN, 12));
		panel.add(energyWaveB);
		
		panel_1 = new JPanel();
		panel_1.setBounds(618, 470, 350, 150);
		BattleP.add(panel_1);
		panel_1.setLayout(new GridLayout(1, 0, 0, 0));
		
		crossArmsB = new JButton("CrossArms");
		crossArmsB.setEnabled(false);
		crossArmsB.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					Writer.write(myProtocol("Motion"));
					Writer.write(6);
					motion1.setIcon(getMotionImage(6));
					punchB.setEnabled(false);
					sunPunchB.setEnabled(false);
					energyWaveB.setEnabled(false);
					crossArmsB.setEnabled(false);
					closeEyesB.setEnabled(false);
					teleportB.setEnabled(false);
					wonkiokB.setEnabled(false);
					collectEnergyB.setEnabled(false);
					Writer.flush();
					battleFlag=1;
				}catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		});
		crossArmsB.setFont(new Font("Arial Black", Font.PLAIN, 13));
		panel_1.add(crossArmsB);
		
		closeEyesB = new JButton("CloseEyes");
		closeEyesB.setEnabled(false);
		closeEyesB.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					Writer.write(myProtocol("Motion"));
					Writer.write(7);
					motion1.setIcon(getMotionImage(7));
					punchB.setEnabled(false);
					sunPunchB.setEnabled(false);
					energyWaveB.setEnabled(false);
					crossArmsB.setEnabled(false);
					closeEyesB.setEnabled(false);
					teleportB.setEnabled(false);
					wonkiokB.setEnabled(false);
					collectEnergyB.setEnabled(false);
					Writer.flush();
					battleFlag=1;
				}catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		});
		closeEyesB.setFont(new Font("Arial Black", Font.PLAIN, 13));
		panel_1.add(closeEyesB);
		
		teleportB = new JButton("Teleport");
		teleportB.setEnabled(false);
		teleportB.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					Writer.write(myProtocol("Motion"));
					Writer.write(8);
					motion1.setIcon(getMotionImage(8));
					punchB.setEnabled(false);
					sunPunchB.setEnabled(false);
					energyWaveB.setEnabled(false);
					crossArmsB.setEnabled(false);
					closeEyesB.setEnabled(false);
					teleportB.setEnabled(false);
					wonkiokB.setEnabled(false);
					collectEnergyB.setEnabled(false);
					Writer.flush();
					battleFlag=1;
					currentEnergy = currentEnergy-1;
				}catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		});
		teleportB.setFont(new Font("Arial Black", Font.PLAIN, 13));
		panel_1.add(teleportB);
		
		panel_2 = new JPanel();
		panel_2.setBounds(353, 398, 264, 222);
		BattleP.add(panel_2);
		panel_2.setLayout(new GridLayout(2, 0, 0, 0));
		
		wonkiokB = new JButton("WonKiOk");
		wonkiokB.setEnabled(false);
		wonkiokB.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					Writer.write(myProtocol("Motion"));
					Writer.write(5);
					motion1.setIcon(getMotionImage(5));
					punchB.setEnabled(false);
					sunPunchB.setEnabled(false);
					energyWaveB.setEnabled(false);
					crossArmsB.setEnabled(false);
					closeEyesB.setEnabled(false);
					teleportB.setEnabled(false);
					wonkiokB.setEnabled(false);
					collectEnergyB.setEnabled(false);
					Writer.flush();
					battleFlag=1;
					currentEnergy = currentEnergy-5;
				}catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		});
		wonkiokB.setFont(new Font("Arial Black", Font.PLAIN, 30));
		panel_2.add(wonkiokB);
		
		collectEnergyB = new JButton("Collect Energy");
		collectEnergyB.setEnabled(false);
		collectEnergyB.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					Writer.write(myProtocol("Motion"));
					Writer.write(1);
					motion1.setIcon(getMotionImage(1));
					punchB.setEnabled(false);
					sunPunchB.setEnabled(false);
					energyWaveB.setEnabled(false);
					crossArmsB.setEnabled(false);
					closeEyesB.setEnabled(false);
					teleportB.setEnabled(false);
					wonkiokB.setEnabled(false);
					collectEnergyB.setEnabled(false);
					Writer.flush();
					battleFlag=1;
					currentEnergy = currentEnergy+1;
				}catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		});
		collectEnergyB.setFont(new Font("Arial Black", Font.PLAIN, 25));
		panel_2.add(collectEnergyB);
		
		motion1 = new JLabel("");
		motion1.setIcon(null);
		motion1.setBounds(0, 58, 450, 300);
		BattleP.add(motion1);
		
		motion2 = new JLabel("");
		motion2.setIcon(null);
		motion2.setBounds(518, 58, 450, 300);
		BattleP.add(motion2);
		
		timerLabel = new JLabel("0");
		timerLabel.setBackground(new Color(0, 128, 0));
		timerLabel.setHorizontalAlignment(SwingConstants.CENTER);
		timerLabel.setFont(new Font("Arial Black", Font.PLAIN, 40));
		timerLabel.setBounds(130, 374, 88, 81);
		BattleP.add(timerLabel);
		
		myNamePanel = new JPanel();
		myNamePanel.setBackground(new Color(30, 144, 255));
		myNamePanel.setBounds(0, 0, 450, 60);
		BattleP.add(myNamePanel);
		myNamePanel.setLayout(new BorderLayout(0, 0));
		
		myNameLabel = new JLabel("my Name");
		myNameLabel.setFont(new Font("Arial Black", Font.PLAIN, 30));
		myNameLabel.setHorizontalAlignment(SwingConstants.CENTER);
		myNamePanel.add(myNameLabel);
		
		combatNamePanel = new JPanel();
		combatNamePanel.setBackground(new Color(255, 69, 0));
		combatNamePanel.setBounds(515, 0, 454, 60);
		BattleP.add(combatNamePanel);
		combatNamePanel.setLayout(new BorderLayout(0, 0));
		
		combatNameLabel = new JLabel("combat Name");
		combatNameLabel.setFont(new Font("Arial Black", Font.PLAIN, 30));
		combatNameLabel.setBackground(new Color(255, 255, 255));
		combatNameLabel.setHorizontalAlignment(SwingConstants.CENTER);
		combatNamePanel.add(combatNameLabel, BorderLayout.CENTER);
		
	}
	//Constructor End
	
	//method getMotionImage: get Motion Image by Motion code(integer)
	public ImageIcon getMotionImage(int code) {
		switch(code)
		{
		case 0: return DRAGONBALL;
		case 1: return KEEE;
		case 2: return PUNCH;
		case 3: return TAEYANGKWON;
		case 4: return ENERGYPA;
		case 5: return WONKIOK;
		case 6: return CROSSARMS;
		case 7: return CLOSEEYES;
		case 8: return TELEPORT;
		default: return null;
		}
	}
	
	//game running method - send Motion and get answer from server.
	public void gameRun() throws IOException, InterruptedException {
		//repeat progress ---------------------
		//clock start
		//get Motion of user by button (If not pressed for 10s, get default Motion -- motion code 0)
		//clock end (10 second)
		//send server data
		//receive server data
		//dual Result: ´ÙÀ½ ÅÏ ÁøÇàÀÌ¸é 0, ÀÌ±â¸é 1, Áö¸é 2 -- code getting from server.
		//battleFlag: ¹öÆ°À» ¾È´­·¶À¸¸é 0, ´­·¶À¸¸é 1 -- to disable button if pressed and check whether button is pressed after 10s.
		//-------------------------------------
		
		long startTime = 0;
		long currentTime = 0;
		long timerNow = 0;
		int dualResult = 0;
		
		while(dualResult == 0) {
			startTime=System.currentTimeMillis();
			battleFlag = 0;
			//setting button available by current Energy.
			if(currentEnergy == 0) {
				collectEnergyB.setEnabled(true);
				crossArmsB.setEnabled(true);
				closeEyesB.setEnabled(true);
			}
			else if(currentEnergy < 2) {
				collectEnergyB.setEnabled(true);
				crossArmsB.setEnabled(true);
				closeEyesB.setEnabled(true);
				punchB.setEnabled(true);
				teleportB.setEnabled(true);
			}
			else if(currentEnergy < 3) {
				sunPunchB.setEnabled(true);
				collectEnergyB.setEnabled(true);
				crossArmsB.setEnabled(true);
				closeEyesB.setEnabled(true);
				punchB.setEnabled(true);
				teleportB.setEnabled(true);
			}
			else if(currentEnergy < 5) {
				energyWaveB.setEnabled(true);
				sunPunchB.setEnabled(true);
				collectEnergyB.setEnabled(true);
				crossArmsB.setEnabled(true);
				closeEyesB.setEnabled(true);
				punchB.setEnabled(true);
				teleportB.setEnabled(true);
			}
			else {
				energyWaveB.setEnabled(true);
				sunPunchB.setEnabled(true);
				collectEnergyB.setEnabled(true);
				crossArmsB.setEnabled(true);
				closeEyesB.setEnabled(true);
				punchB.setEnabled(true);
				teleportB.setEnabled(true);
				wonkiokB.setEnabled(true);
			}
			//press button limit time: 10 seconds
			while(currentTime-startTime < 10000) {
				currentTime = System.currentTimeMillis();
				timerNow = 100 - (currentTime-startTime)/100;
				timerLabel.setText("" + timerNow);
			}
			//if no button pressed, no Motion case - send Motion code 0, which indicates that no button is pressed.
			if(battleFlag == 0) {
				Writer.write(myProtocol("Motion"));
				Writer.write(0);
			}
			Writer.flush();
			while(Reader.available() == 0) {
			}
			dualResult=Reader.read();
			motion2.setIcon(getMotionImage(Reader.read()));
		}
		if(dualResult == 1) {
			//win! show message
			JOptionPane.showMessageDialog(null, "WIN!!!!");
		}
		else if(dualResult == 2) {
			//lose... show message
			JOptionPane.showMessageDialog(null, "LOSE....TT");
		}
		Thread.sleep(1000);
		currentEnergy = 0;
		card.show(homePanel, "Slide_4");
		currentSlideNumber = 4;
		battleFlag = 0;
		setBounds(getBounds().x, getBounds().y, 517, 600);
	}
	
	// Client main
	public static void main(String[] args) throws InterruptedException, IOException
	{
		ClientFrame frame = new ClientFrame();
		frame.setVisible(true);
		while(currentSlideNumber != 0) {
			Thread.sleep(100);
			if(currentSlideNumber == 5) {
				int flag = 0;
				while(ExitButton.isEnabled()) {
					Writer.write(myProtocol("Combat_Ready?"));
					Writer.flush();
					while(Reader.available() == 0) {
						Thread.sleep(100);
					}
					flag = Reader.read();
					//combat found
					if(flag == 0) {
						card.show(homePanel, "Battle_Slide");
						motion1.setIcon(null);
						motion2.setIcon(null);
						frame.setBounds(frame.getBounds().x, frame.getBounds().y, 984, 657);
						myNameLabel.setText(myID);
						Reader.read(Input);
						combatID = new String(Arrays.copyOfRange(Input, 1, 1+Input[0]));
						combatNameLabel.setText(combatID);
						currentSlideNumber = 6;
						break;
					}
				}
			}
			//Game running
			else if(currentSlideNumber == 6) {
				frame.gameRun();
			}
		}
		return;
	//Client main end	
	}
}
