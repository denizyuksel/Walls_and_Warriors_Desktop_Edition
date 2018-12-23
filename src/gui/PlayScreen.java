package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import gameplay.DatabaseManager;
import util.Sprites;

public class PlayScreen extends JPanel {

	private JPanel topPanel;
	private JPanel midPanel;
	private JPanel botPanel;
	private JPanel selectTheme;
	private JLabel playerInfo;
	private JButton classicButton;
	private JButton sandboxButton;
	private JButton createLevelButton;
	private JButton backButton;
	private JLabel selectThemeLabel;
	private JButton shopButton;
	private JInternalFrame f;
	private JPanel soundStuff;
	private JLabel sound;
	private JSlider soundVolume;
	private JCheckBox mute;
	private JPanel playerInfos;
	private JLabel playerInfo1;
	private JLabel playerInfo2;
	
	public PlayScreen() {
		String player = Screen.getInstance().selectedPlayer;
		midPanel = new JPanel();
		midPanel.setOpaque(false);
		//Internal frame for option pane
		f = new JInternalFrame();
		f.setVisible(false);
		this.add(f);
		midPanel.setPreferredSize(new Dimension(300, 850));
		topPanel = new JPanel(new FlowLayout());
		topPanel.setOpaque(false);
		topPanel.setLayout(new BorderLayout());
		topPanel.setPreferredSize(new Dimension(1920, 35));
		midPanel.add(Box.createRigidArea(new Dimension(1920,40)));
		
		//PLAYER INFO
		playerInfos = new JPanel(new GridLayout(1,0,0,0)); //Player information
		playerInfos.setPreferredSize(new Dimension(375, 50));
		playerInfo = new JLabel(player);
		playerInfo.setFont(new Font("Comic Sans MS",Font.BOLD,16));
		playerInfos.add(playerInfo); //Player name
		
		playerInfo1 = new JLabel(Screen.getInstance().db.getCoins(player) + "  ");
		playerInfo1.setForeground(Color.BLACK);
		playerInfo1.setHorizontalTextPosition(SwingConstants.LEFT);
		playerInfo1.setFont(new Font("Comic Sans MS",Font.BOLD,16));
		
		playerInfo1.setIcon(new ImageIcon(Sprites.Coin.getScaledInstance(35, 35, Image.SCALE_SMOOTH)));
		
		playerInfo2 = new JLabel(Screen.getInstance().db.getDiamonds(player) + "  ");
		playerInfo2.setHorizontalTextPosition(SwingConstants.LEFT);
		playerInfo2.setFont(new Font("Comic Sans MS",Font.BOLD,16));
		playerInfo2.setForeground(Color.BLACK);
		playerInfo2.setIcon(new ImageIcon(Sprites.Diamond.getScaledInstance(35, 35, Image.SCALE_SMOOTH)));
		
		playerInfos.add(playerInfo1);
		playerInfos.add(playerInfo2);
		playerInfos.setOpaque(false);
		topPanel.add(playerInfos, BorderLayout.EAST);
		
		//Sound Settings
		soundStuff = new JPanel(new GridLayout(1,0,0,0));
		soundStuff.setOpaque(false);
		soundStuff.setPreferredSize(new Dimension(325, 35));

		sound = new JLabel("Sound Level: ");
		sound.setFont(new Font("Comic Sans MS",Font.PLAIN,16));
		soundStuff.add(sound, BorderLayout.WEST);

		soundVolume = new JSlider(JSlider.HORIZONTAL, 0, 100, Screen.getInstance().db.getMusicSetting(player));
		soundVolume.setOpaque(false);


		mute = new JCheckBox();
		mute.setFocusable(false);
		mute.setFont(new Font("Comic Sans MS",Font.PLAIN,16));
		mute.setText("Mute");
		mute.setOpaque(false);
		if(Screen.getInstance().db.getMusicSetting(player) == 0)
			mute.setSelected(true);
		else
			mute.setSelected(false);
		mute.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				if (mute.isSelected()) {
					Screen.getInstance().db.setMusicSetting(player,0);
					soundVolume.setValue(0);
					Screen.getInstance().MenuPlayer.setVolume((double)(-4)*(100-soundVolume.getValue())/5);
				}
			}
		});

		soundVolume.addChangeListener(new ChangeListener() {
										  public void stateChanged(ChangeEvent arg0) {
											  Screen.getInstance().db.setMusicSetting(player,soundVolume.getValue());

											  Screen.getInstance().MenuPlayer.setVolume((double) (-3) * (100 - soundVolume.getValue()) / 5);
											  if(Screen.getInstance().db.getMusicSetting(player) == 0)
												  mute.setSelected(true);
											  else
												  mute.setSelected(false);
										  }
									  }
		);


		soundStuff.add(soundVolume, BorderLayout.WEST);

		soundStuff.add(mute, BorderLayout.WEST);

		topPanel.add(soundStuff, BorderLayout.WEST);

		topPanel.setBounds(0,5,1920,35);
		this.add(topPanel);
		
		
		//CLASSIC MODE
		midPanel.add(Box.createRigidArea(new Dimension(300,100)));
		classicButton = new JButton("Classic Mode");
		classicButton.setForeground(Color.BLUE);
		classicButton.setFont(new Font("Comic Sans MS",Font.BOLD,36));
		classicButton.setPreferredSize(new Dimension(300,125));
		classicButton.setFocusable(false);//Button properties are set
		classicButton.addActionListener(new ActionListener() { 
			public void actionPerformed(ActionEvent e) {//this part can change later on for custom JOptionPane
				Screen.getInstance().setActivePage("Classic");

		  } 
		} );
		midPanel.add(classicButton);
		midPanel.add(Box.createRigidArea(new Dimension(300,40)));//empty space between buttons
		
		//SANDBOX MODE
		
		sandboxButton = new JButton("Sandbox Mode");
		sandboxButton.setPreferredSize(new Dimension(300,125));
		sandboxButton.setForeground(Color.BLUE);
		sandboxButton.setFont(new Font("Comic Sans MS",Font.BOLD,36));
		sandboxButton.setFocusable(false);
		sandboxButton.addActionListener(new ActionListener() { 
			public void actionPerformed(ActionEvent e) {
				Screen.getInstance().setActivePage("Sandbox");
		  } 
		} );
		midPanel.add(sandboxButton);
		midPanel.add(Box.createRigidArea(new Dimension(300,40)));
		 
		
		//CREATE LEVEL
		createLevelButton = new JButton("Create a Level");
		createLevelButton.setPreferredSize(new Dimension(300,125));
		createLevelButton.setFocusable(false);
		createLevelButton.setForeground(Color.BLUE);
		createLevelButton.setFont(new Font("Comic Sans MS",Font.BOLD,36));
		midPanel.add(createLevelButton);
		midPanel.add(Box.createRigidArea(new Dimension(300,40)));
		createLevelButton.addActionListener(new ActionListener() { 
			public void actionPerformed(ActionEvent e) {
                Screen.getInstance().setActivePage("SandBoxSize");
		  } 
		} );

		//BACK 
		backButton = new JButton("Back");
		backButton.setFocusable(false);
		backButton.setPreferredSize(new Dimension(300,125));
		backButton.setForeground(Color.BLUE);
		backButton.setFont(new Font("Comic Sans MS",Font.BOLD,36));
		backButton.addActionListener(new ActionListener() { 
	    	  public void actionPerformed(ActionEvent e) {
	    		    Screen.getInstance().setActivePage("MainMenu");
	    		  } 
	    		} );
		midPanel.add(backButton);	

		
		botPanel = new JPanel(new BorderLayout());
		botPanel.setOpaque(false);
		botPanel.setPreferredSize(new Dimension(1920, 150));
		
  	
	  	selectTheme = new JPanel(new GridLayout(0, 1,10,10));
	  	selectTheme.setPreferredSize(new Dimension(200,200));
	  	selectThemeLabel = new JLabel("Please select your theme:");
	  	selectTheme.add(selectThemeLabel);
	  	
	  	
	  	ButtonGroup group = new ButtonGroup();
	  	String selectedTheme = Screen.db.getCurrentTheme(player);
	  	for(String s: Screen.db.getAllThemes())
	  	{
	  		
	  		JRadioButton j = new JRadioButton(s);
	  		j.setActionCommand(s);
	  		j.setForeground(Color.BLUE);
	  		if(s.equalsIgnoreCase("default"))
	  			j.setText("Knight");
	  		else if (s.equals( "hello there"))
	  			j.setText("Star Wars");
	  		else if (s.equals( "lord of the rings"))
	  			j.setText("Lord of the Rings");
	  		else if (s.equals( "three little pigs"))
	  			j.setText("Three Little Pigs");
	  		else if (s.equals("scooby doo"))
	  			j.setText("Scooby Doo");
	  		
	  		j.setFocusable(false);
	  		if((Screen.db.getUnlockedThemes(player).contains(s)))
	  			j.setEnabled(true);
	  		else
	  			j.setEnabled(false);
	  		
	  		if(s.equals(selectedTheme))
	  		{
	  			j.setSelected(true);
	  		}
	  			    		  		
	  		j.addActionListener(new ActionListener() { 
		    	  public void actionPerformed(ActionEvent e) {
		    		  	Screen.db.setTheme(player, s);
		    		  } 
		    		} );	  		
	        group.add(j);
	        selectTheme.add(j);
	  	}
	  	
		shopButton = new JButton("Shop");
		shopButton.setOpaque(false);
		shopButton.setFocusable(false);
		shopButton.setContentAreaFilled(false);
		
		ImageIcon chestIcon = new ImageIcon(Sprites.Chest.getScaledInstance(150, 150, Image.SCALE_SMOOTH));
		
		shopButton.setIcon(chestIcon);
		shopButton.setPreferredSize(new Dimension(150,75));
		shopButton.addActionListener(new ActionListener() { 
	    	  public void actionPerformed(ActionEvent e) {
	    		    Screen.getInstance().setActivePage("Shop");
	    		  } 
	    		} );
			
		botPanel.add(new JLabel("Please select your theme"),BorderLayout.WEST);
		botPanel.add(selectTheme, BorderLayout.WEST);
		botPanel.add(shopButton, BorderLayout.EAST);
		this.add(topPanel);
		this.add(midPanel);
		this.add(botPanel);
		this.setOpaque(false);
	}
}
