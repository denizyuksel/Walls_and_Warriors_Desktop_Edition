package gui;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import util.Sprites;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SandBoxSizePanel extends JPanel {
    private JPanel buttons ;
    private JPanel labelPanel;
    private JButton smallButton;
    private JButton mediumButton;

    private JButton largeButton;
    private JButton backButton;
    private JLabel playerInfo;
	private JLabel gameName;
	private JPanel soundStuff;
	private JLabel sound;
	private JSlider soundVolume;
	private JCheckBox mute;
	
	private JPanel playerInfos;
	private JLabel playerInfo1;
	private JLabel playerInfo2;
	
	private JPanel botPanel;
	private JButton shopButton;
	private JPanel selectTheme;
	private JLabel selectThemeLabel;
    
    public SandBoxSizePanel(){
    	//Almost same as main menu screen. Check main menu for further explanation
        String player = Screen.getInstance().selectedPlayer;
        buttons = new JPanel();
        buttons.setOpaque(false);
        buttons.setPreferredSize(new Dimension(300, 850));
        labelPanel = new JPanel(new BorderLayout());
        labelPanel.setPreferredSize(new Dimension(1920, 35));

        gameName = new JLabel("Select Board Size");
        gameName.setFont(new Font("Comic Sans MS",Font.BOLD,28));
        buttons.add(gameName);

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
		labelPanel.add(playerInfos, BorderLayout.EAST);
		

		soundStuff = new JPanel(new GridLayout(1,0,0,0));
		soundStuff.setPreferredSize(new Dimension(325, 35));

		sound = new JLabel("Sound Level: ");
		sound.setFont(new Font("Comic Sans MS",Font.BOLD,16));
		soundStuff.add(sound, BorderLayout.WEST);

		soundVolume = new JSlider(JSlider.HORIZONTAL, 0, 100, Screen.getInstance().db.getMusicSetting(player));



		mute = new JCheckBox();
		mute.setFocusable(false);
		mute.setFont(new Font("Comic Sans MS",Font.BOLD,16));
		mute.setText("Mute");
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
		
		soundStuff.setOpaque(false);
		mute.setOpaque(false);
		soundVolume.setOpaque(false);
		labelPanel.setOpaque(false);
		labelPanel.add(soundStuff, BorderLayout.WEST);
		
		buttons.add(Box.createRigidArea(new Dimension(300,100)));
		
        smallButton = new JButton("Small");
        smallButton.setPreferredSize(new Dimension(300,125));
        smallButton.setForeground(Color.BLUE);
        smallButton.setPreferredSize(new Dimension(300,125));
       // smallButton.setOpaque(false);//Button properties are set
        smallButton.setFocusable(false);
        //smallButton.setContentAreaFilled(false);
        smallButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Screen.getInstance().setActivePage("CreateBoard",0);
            }
        } );
        buttons.add(smallButton);
        buttons.add(Box.createRigidArea(new Dimension(300,40)));//empty space between buttons
        
        mediumButton = new JButton("Medium");
        mediumButton.setPreferredSize(new Dimension(300,125));
        mediumButton.setForeground(Color.BLUE);
        mediumButton.setPreferredSize(new Dimension(300,125));
        //mediumButton.setOpaque(false);
        mediumButton.setFocusable(false);
        //mediumButton.setContentAreaFilled(false);
        mediumButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Screen.getInstance().setActivePage("CreateBoard",1);
            }
        } );
		 buttons.add(mediumButton);
		 buttons.add(Box.createRigidArea(new Dimension(300,40)));
		 
        largeButton = new JButton("Large");
        largeButton.setPreferredSize(new Dimension(300,125));
        largeButton.setForeground(Color.BLUE);
        largeButton.setPreferredSize(new Dimension(300,125));
        //largeButton.setOpaque(false);
        largeButton.setFocusable(false);
        //largeButton.setContentAreaFilled(false);
        largeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Screen.getInstance().setActivePage("CreateBoard",2);
            }
        } );
        buttons.add(largeButton);
        buttons.add(Box.createRigidArea(new Dimension(300,40)));


        backButton = new JButton("Back");
        backButton.setPreferredSize(new Dimension(300,125));
        backButton.setForeground(Color.BLUE);
        //backButton.setOpaque(false);
        backButton.setFocusable(false);
        //backButton.setContentAreaFilled(false);
        backButton.setPreferredSize(new Dimension(300,125));
        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Screen.getInstance().setActivePage("Play");
            }
        } );
        buttons.add(backButton);

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
	  			//j.setEnabled(false);
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
		botPanel = new JPanel(new BorderLayout());
		botPanel.setOpaque(false);
		botPanel.setPreferredSize(new Dimension(1920, 150));	
		botPanel.add(new JLabel("Please select your theme"),BorderLayout.WEST);
		botPanel.add(selectTheme, BorderLayout.WEST);
		botPanel.add(shopButton, BorderLayout.EAST);

		
		this.setOpaque(false);
        
        this.add(labelPanel);
        this.add(buttons);
        this.add(botPanel);
        this.setOpaque(false);
        
        
        
    }
    
}
