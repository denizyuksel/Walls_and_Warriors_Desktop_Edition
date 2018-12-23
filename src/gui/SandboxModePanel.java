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
import java.util.Arrays;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import util.Sprites;

public class SandboxModePanel extends JPanel {


	private JPanel topPanel;
	private JPanel botPanel;
	private JScrollPane midPanel;
	private JPanel levelsPanel;

	private JLabel playerInfo;

	private JLabel modeName;

	private JButton backButton;

	private JPanel selectTheme;
	private JButton shopButton;
	private JPanel soundStuff;
	private JLabel sound;
	private JSlider soundVolume;
	private JCheckBox mute;

	private JPanel playerInfos;
	private JLabel playerInfo1;
	private JLabel playerInfo2;

	public SandboxModePanel() {
		String player = Screen.getInstance().selectedPlayer;
		midPanel = new JScrollPane(null);


		this.setLayout(null);
		midPanel.setPreferredSize(new Dimension(1920, 850));
		topPanel = new JPanel();
		topPanel.setLayout(new BorderLayout());
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

		//Sound stuff
		soundStuff = new JPanel(new GridLayout(1,0,0,0));
		soundStuff.setPreferredSize(new Dimension(325, 35));
		soundStuff.setOpaque(false);

		sound = new JLabel("Sound Level: ");
		sound.setFont(new Font("Comic Sans MS",Font.PLAIN,16));
		soundStuff.add(sound, BorderLayout.WEST);

		soundVolume = new JSlider(JSlider.HORIZONTAL, 0, 100, Screen.getInstance().db.getMusicSetting(player));
		soundVolume.setOpaque(false);

		mute = new JCheckBox();
		mute.setOpaque(false);
		mute.setFocusable(false);
		mute.setFont(new Font("Comic Sans MS",Font.PLAIN,16));
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
											  //CHANGE SOUND HEREEE
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

		modeName = new JLabel("Sandbox Mode");
		modeName.setFont(new Font("Comic Sans MS",Font.BOLD,56));
		modeName.setForeground(Color.BLACK);
		modeName.setBounds(760,100,1600,50);
		this.add(modeName);

		//Levels are put in a JPanel
		levelsPanel = new JPanel(null);
		levelsPanel.setOpaque(false);
		int counter = 1;
		int xCounter = 25;
		int yCounter = 0;
		for(Object[] o: Screen.db.getAllSandboxLevels())
		{
			int temp = counter;
			JButton j = new JButton();
			j.setText(counter + "");
			j.setBounds(xCounter,yCounter,150,150);
			j.setFocusable(false);
			j.setForeground(Color.BLUE);
			j.setFont(new Font("Comic Sans MS",Font.BOLD,50));
			j.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {

					//add string
					char[][] level = (char[][]) o[0];
					int[] walls = (int[]) o[1];
					Screen.getInstance().setActivePage("PlayBoard",level,Screen.db.getBoardSize(temp),walls);
				}
			} );
			counter++;
			levelsPanel.add(j);
			if(xCounter != 1225)
				xCounter += 300;
			else
			{
				xCounter = 25;
				yCounter += 300;
			}
		}
		levelsPanel.setPreferredSize(new Dimension(1400,yCounter + 150));
		//Scroll pane is created
		midPanel = new JScrollPane(levelsPanel,ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		midPanel.setOpaque(false);
		midPanel.getViewport().setOpaque(false);
		midPanel.getVerticalScrollBar().setPreferredSize( new Dimension(0,0));
		midPanel.setBounds(260,200,1400,500);
		this.add(midPanel);


		//Back
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
		backButton.setBounds(810, 800, 300, 125);
		this.add(backButton);

		//Themes
		selectTheme = new JPanel(new GridLayout(0, 1,10,10));
		selectTheme.setPreferredSize(new Dimension(200,200));
		selectTheme.add(new JLabel("Please select your theme:"));

		ButtonGroup group = new ButtonGroup();
		ArrayList<String> unlockedThemes = Screen.db.getUnlockedThemes(player);
		String selectedTheme = Screen.db.getCurrentTheme(player);
		for(String s: Screen.db.getAllThemes())
		{

			JRadioButton j = new JRadioButton(s);
			j.setActionCommand(s);
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

		//Shop
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
		botPanel.add(new JLabel("Please select your theme"),BorderLayout.WEST);
		botPanel.add(selectTheme, BorderLayout.WEST);
		botPanel.add(shopButton, BorderLayout.EAST);
		botPanel.setBounds(0,900,1920,150);

		this.add(botPanel);
		this.setOpaque(false);
	}
}
