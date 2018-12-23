package gui;

import engine.AudioPlayer;
import util.Sprites;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class MainMenu extends JPanel {

	private JPanel buttons;
	private JPanel labelPanel;
	private JButton startButton;
	private JButton creditsButton;
	private JButton exitButton;
	private JLabel playerInfo;
	private JLabel gameName;
	private JPanel soundStuff;
	private JLabel sound;
	private JSlider soundVolume;
	private JCheckBox mute;

	private JPanel playerInfos;
	private JLabel playerInfo1;
	private JLabel playerInfo2;

	public MainMenu() {
		String player = Screen.getInstance().selectedPlayer;

		int temp = Screen.db.getMusicSetting(player);
		Screen.getInstance().MenuPlayer.play();
		Screen.getInstance().MenuPlayer.setVolume((double) (-3) * (100 - temp) / 5);
		Screen.getInstance().MenuPlayer.loop(100);
		buttons = new JPanel();
		buttons.setPreferredSize(new Dimension(600, 800));
		labelPanel = new JPanel(new BorderLayout());
		labelPanel.setPreferredSize(new Dimension(1920, 35));

		// Game name
		gameName = new JLabel("WALLS & WARRIORS");
		gameName.setFont(new Font("Comic Sans MS", Font.BOLD, 55));
		gameName.setPreferredSize(new Dimension(600, 100));
		buttons.add(gameName);

		// Player Info
		playerInfos = new JPanel(new GridLayout(1, 0, 0, 0)); // Player information
		playerInfos.setPreferredSize(new Dimension(375, 50));
		playerInfo = new JLabel(player);
		playerInfo.setFont(new Font("Comic Sans MS", Font.BOLD, 16));
		playerInfos.add(playerInfo); // Player name

		playerInfo1 = new JLabel(Screen.getInstance().db.getCoins(player) + "  ");
		playerInfo1.setForeground(Color.BLACK);
		playerInfo1.setHorizontalTextPosition(SwingConstants.LEFT);
		playerInfo1.setFont(new Font("Comic Sans MS", Font.BOLD, 16));

		playerInfo1.setIcon(new ImageIcon(Sprites.Coin.getScaledInstance(35, 35, Image.SCALE_SMOOTH)));

		playerInfo2 = new JLabel(Screen.getInstance().db.getDiamonds(player) + "  ");
		playerInfo2.setHorizontalTextPosition(SwingConstants.LEFT);
		playerInfo2.setFont(new Font("Comic Sans MS", Font.BOLD, 16));
		playerInfo2.setForeground(Color.BLACK);
		playerInfo2.setIcon(new ImageIcon(Sprites.Diamond.getScaledInstance(35, 35, Image.SCALE_SMOOTH)));

		playerInfos.add(playerInfo1);
		playerInfos.add(playerInfo2);
		playerInfos.setOpaque(false);
		labelPanel.add(playerInfos, BorderLayout.EAST);

		// Sound settings
		soundStuff = new JPanel(new GridLayout(1, 0, 0, 0));
		soundStuff.setPreferredSize(new Dimension(325, 35));

		sound = new JLabel("Sound Level: ");
		sound.setFont(new Font("Comic Sans MS", Font.BOLD, 16));
		soundStuff.add(sound, BorderLayout.WEST);

		soundVolume = new JSlider(JSlider.HORIZONTAL, 0, 100, Screen.getInstance().db.getMusicSetting(player));

		mute = new JCheckBox();
		mute.setFocusable(false);
		mute.setFont(new Font("Comic Sans MS", Font.BOLD, 16));
		mute.setText("Mute");
		if (Screen.getInstance().db.getMusicSetting(player) == 0)
			mute.setSelected(true);
		else
			mute.setSelected(false);
		mute.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				if (mute.isSelected()) {
					Screen.getInstance().db.setMusicSetting(player, 0);
					soundVolume.setValue(0);
					Screen.getInstance().MenuPlayer.setVolume((double) (-3) * (100 - soundVolume.getValue()) / 5);
				}
			}
		});

		soundVolume.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				Screen.getInstance().db.setMusicSetting(player, soundVolume.getValue());
				Screen.getInstance().MenuPlayer.setVolume((double) (-3) * (100 - soundVolume.getValue()) / 5);
				if (Screen.getInstance().db.getMusicSetting(player) == 0)
					mute.setSelected(true);
				else
					mute.setSelected(false);

			}
		});

		soundStuff.add(soundVolume, BorderLayout.WEST);

		soundStuff.add(mute, BorderLayout.WEST);

		soundStuff.setOpaque(false);
		mute.setOpaque(false);
		soundVolume.setOpaque(false);

		labelPanel.add(soundStuff, BorderLayout.WEST);

		buttons.add(Box.createRigidArea(new Dimension(300, 100)));// empty space between buttons

		// Play button
		startButton = new JButton("Play");
		startButton.setPreferredSize(new Dimension(300, 125));
		startButton.setForeground(Color.BLUE);
		startButton.setFont(new Font("Comic Sans MS", Font.BOLD, 36));
		startButton.setFocusable(false);

		startButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Screen.getInstance().setActivePage("Play");
			}
		});
		buttons.add(startButton);
		buttons.add(Box.createRigidArea(new Dimension(300, 80)));// empty space between buttons

		//Credits button
		creditsButton = new JButton("Credits");
		creditsButton.setForeground(Color.BLUE);
		creditsButton.setPreferredSize(new Dimension(300, 125));
		creditsButton.setFocusable(false);
		creditsButton.setFont(new Font("Comic Sans MS", Font.BOLD, 36));
		creditsButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Screen.getInstance().setActivePage("Credits");
			}
		});
		buttons.add(creditsButton);
		buttons.add(Box.createRigidArea(new Dimension(300, 80)));// empty space between buttons

		//Exit Button
		exitButton = new JButton("Exit");
		exitButton.setForeground(Color.BLUE);
		exitButton.setFocusable(false);
		exitButton.setPreferredSize(new Dimension(300, 125));
		exitButton.setFont(new Font("Comic Sans MS", Font.BOLD, 36));
		exitButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		buttons.add(exitButton);

		labelPanel.setOpaque(false);
		buttons.setOpaque(false);

		this.add(labelPanel);

		this.add(buttons);
		this.setOpaque(false);
	}
}
