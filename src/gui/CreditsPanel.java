package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import util.Sprites;

public class CreditsPanel extends JPanel {
	
	
	JLabel groupInfo1; //underlined text
	JLabel dawood;
	JLabel deniz;
	JLabel faruk;
	JLabel mert;
	JLabel salih;
	JButton backButton;
	JPanel topPanel;
	
	private JPanel soundStuff;
	
	private JLabel sound;
	private JSlider soundVolume;
	private JCheckBox mute;
	
	
	private JPanel playerInfos;
	private JLabel playerInfo;
	private JLabel playerInfo1;
	private JLabel playerInfo2;
	
	
	public CreditsPanel() {
		//This panel is hard coded so there won't be any explanations since it is self explanatory
		String player = Screen.getInstance().selectedPlayer;
		
		this.setLayout(null);
		this.setOpaque(false); //setting this panel transparent
		topPanel = new JPanel(new BorderLayout());
		topPanel.setBounds(0,0,1920,50);
		topPanel.setOpaque(false);
		
		//Player Information
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
		
		//Sound settings
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
					Screen.getInstance().MenuPlayer.setVolume((double) (-3) * (100 - soundVolume.getValue()) / 5);
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
		
		topPanel.add(soundStuff, BorderLayout.WEST);
		this.add(topPanel);
		
		
		groupInfo1 = new JLabel("GROUP 1A: 404 NAME NOT FOUND");
		groupInfo1.setFont(new Font("Comic Sans MS",Font.BOLD,40));
		groupInfo1.setForeground(Color.BLUE);
		groupInfo1.setBounds(600,100, 900, 50);
		this.add(groupInfo1);
		
		dawood = new JLabel("Dawood Muzammil Malik");
		dawood.setFont(new Font("Comic Sans MS",Font.BOLD,30));
		dawood.setForeground(Color.BLUE);
		dawood.setBounds(600,250, 900, 50);
		this.add(dawood);
		
		deniz = new JLabel("Deniz Yuksel");
		deniz.setFont(new Font("Comic Sans MS",Font.BOLD,30));
		deniz.setForeground(Color.BLUE);
		deniz.setBounds(600,350, 900, 50);
		this.add(deniz);
		
		faruk = new JLabel("Faruk Oruc");
		faruk.setFont(new Font("Comic Sans MS",Font.BOLD,30));
		faruk.setForeground(Color.BLUE);
		faruk.setBounds(600,450, 900, 50);
		this.add(faruk);
		
		mert = new JLabel("Mert Soydinc");
		mert.setFont(new Font("Comic Sans MS",Font.BOLD,30));
		mert.setForeground(Color.BLUE);
		mert.setBounds(600,550, 900, 50);
		this.add(mert);
		
		salih = new JLabel("Muhammed Salih Altun");
		salih.setFont(new Font("Comic Sans MS",Font.BOLD,30));
		salih.setForeground(Color.BLUE);
		salih.setBounds(600,650, 900, 50);
		this.add(salih);
		
		backButton = new JButton("Back");
		backButton.setFocusable(false);
		backButton.setPreferredSize(new Dimension(300,125));
		backButton.setForeground(Color.BLUE);
		backButton.setFont(new Font("Comic Sans MS",Font.BOLD,32));
		backButton.addActionListener(new ActionListener() { 
	    	  public void actionPerformed(ActionEvent e) {
	    		    Screen.getInstance().setActivePage("MainMenu");
	    		  } 
	    		} );
		backButton.setBounds(810, 900, 300, 125);
		this.add(backButton);	
	}

}
