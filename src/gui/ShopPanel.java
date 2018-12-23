package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import util.Sprites;

public class ShopPanel extends JPanel {
	
	private final String LOTR_NAME = "lord of the rings";
	private final String PIGS_NAME = "three little pigs";
	private final String SW_NAME = "hello there";
	private final String SCOOBY_NAME = "scooby doo";
	private final int LOTR_PRICE = 2000;
	private final int PIGS_PRICE = 750;
	private final int SW_PRICE = 1500;
	private final int SCOOBY_PRICE = 1000;
	private final int DIA_PRICE = 5000;
	
	private JPanel p;
	private JLabel playerInfo1;
	private JLabel playerInfo2;
	private JLabel theme1Image;
	private JLabel theme2Image;
	private JLabel theme3Image;
	private JLabel theme4Image;
	private JButton theme1;
	private JButton theme2;
	private JButton theme3;
	private JButton theme4;
	private JButton buyDia;
	private JButton backButton;
	
	public ShopPanel() {
		String player = Screen.getInstance().selectedPlayer;
		//Screen.getInstance().db.setCoins(player, 20000); //test
		ArrayList<String> a =  Screen.getInstance().db.getUnlockedThemes(player);

		p = new JPanel(null);
		p.setPreferredSize(new Dimension(1920,1080));
		
		
		//Setting the image of themes
		Image kenobi = Sprites.Kenobi.getScaledInstance(200, 375, Image.SCALE_SMOOTH);
		Image gollum = Sprites.Gollum.getScaledInstance(200, 375, Image.SCALE_SMOOTH);
		Image scooby = Sprites.Scooby.getScaledInstance(200, 375, Image.SCALE_SMOOTH);
		Image pig = Sprites.Pig.getScaledInstance(200, 375, Image.SCALE_SMOOTH);
		
		Image smallCoin = Sprites.Coin.getScaledInstance(50, 50, Image.SCALE_SMOOTH);
		Image bigCoin = Sprites.Coin.getScaledInstance(100, 100, Image.SCALE_SMOOTH);
		Image diamond = Sprites.Diamond.getScaledInstance(100, 100, Image.SCALE_SMOOTH);

		ImageIcon kenobiIcon = new ImageIcon(kenobi);
		ImageIcon gollumIcon = new ImageIcon(gollum);
		ImageIcon scoobyIcon = new ImageIcon(scooby);
		ImageIcon pigIcon = new ImageIcon(pig);
		ImageIcon smallCoinIcon = new ImageIcon(smallCoin);
		ImageIcon bigCoinIcon = new ImageIcon(bigCoin);
		ImageIcon diamondIcon = new ImageIcon(diamond);
		
		theme1Image = new JLabel();
		theme1Image.setIcon(gollumIcon);
		theme1Image.setBounds(200,50,200,375);
		
		theme2Image = new JLabel();
		theme2Image.setIcon(kenobiIcon);
		theme2Image.setBounds(1000,50,200,375);
		
		theme3Image = new JLabel();
		theme3Image.setIcon(scoobyIcon);
		theme3Image.setBounds(200,550,200,375);
		
		theme4Image = new JLabel();
		theme4Image.setIcon(pigIcon);
		theme4Image.setBounds(1000,550,200,375);
		
		//Player info
		playerInfo1 = new JLabel(Screen.getInstance().db.getCoins(player) + "  ");
		playerInfo1.setForeground(Color.BLACK);
		playerInfo1.setHorizontalTextPosition(SwingConstants.LEFT);
		playerInfo1.setFont(new Font("Comic Sans MS",Font.BOLD,20));
		
		playerInfo1.setIcon(bigCoinIcon);
		playerInfo1.setBounds(1700, 20, 200, 400);
		
		playerInfo2 = new JLabel(Screen.getInstance().db.getDiamonds(player) + "  ");
		playerInfo2.setHorizontalTextPosition(SwingConstants.LEFT);
		playerInfo2.setFont(new Font("Comic Sans MS",Font.BOLD,20));
		playerInfo2.setForeground(Color.BLACK);
		playerInfo2.setIcon(diamondIcon);
		playerInfo2.setBounds(1700, 220, 200, 400);
		
		//Purchase theme buttons
		theme1 = new JButton();
		theme1.setHorizontalTextPosition(SwingConstants.LEFT);
		theme1.setText(""+LOTR_PRICE);
		theme1.setFont(new Font("Comic Sans MS",Font.PLAIN,20));
		theme1.setForeground(Color.BLUE);
		theme1.setIcon(smallCoinIcon);
		theme1.setFocusable(false);
		theme1.setBounds(200, 450, 200, 55);
		if(a.contains(LOTR_NAME))
			theme1.setEnabled(false);
		theme1.addActionListener (new ActionListener () {
	   	    public void actionPerformed(ActionEvent e) {
	   	    	int temp = Screen.getInstance().db.getCoins(player);
	   	    	//System.out.println(temp);
	   	        if(temp >= LOTR_PRICE)
	   	        {
	   	        	temp = temp - LOTR_PRICE;
	   	        	Screen.getInstance().db.setCoins(player, temp);
	   	        	Screen.getInstance().db.unlockTheme(player, LOTR_NAME);
	   	        	playerInfo1.setText(temp + " ");
	   	        	theme1.setEnabled(false);
	   	        }
	   	        //System.out.println(temp);
	   	    }
	   	});
		
		theme2 = new JButton();
		theme2.setHorizontalTextPosition(SwingConstants.LEFT);
		theme2.setText(""+SW_PRICE);
		theme2.setFont(new Font("Comic Sans MS",Font.PLAIN,20));
		theme2.setForeground(Color.BLUE);
		theme2.setIcon(smallCoinIcon);
		theme2.setFocusable(false);
		theme2.setBounds(1000, 450, 200, 55);
		if(a.contains(SW_NAME))
			theme2.setEnabled(false);
		theme2.addActionListener (new ActionListener () {
	   	    public void actionPerformed(ActionEvent e) {
	   	    	int temp = Screen.getInstance().db.getCoins(player);
	   	    	//System.out.println(temp);
	   	        if(temp >= SW_PRICE)
	   	        {
	   	        	temp = temp - SW_PRICE;
	   	        	Screen.getInstance().db.setCoins(player, temp);
	   	        	Screen.getInstance().db.unlockTheme(player, SW_NAME);
	   	        	playerInfo1.setText(temp + " ");
	   	        	theme2.setEnabled(false);
	   	        }
	   	    }
	   	});
		
		theme3 = new JButton();
		theme3.setHorizontalTextPosition(SwingConstants.LEFT);
		theme3.setText(""+SCOOBY_PRICE);
		theme3.setFont(new Font("Comic Sans MS",Font.PLAIN,20));
		theme3.setForeground(Color.BLUE);
		theme3.setIcon(smallCoinIcon);
		theme3.setFocusable(false);
		theme3.setBounds(200, 950, 200, 55);
		if(a.contains(SCOOBY_NAME))
			theme3.setEnabled(false);
		theme3.addActionListener (new ActionListener () {
	   	    public void actionPerformed(ActionEvent e) {
	   	    	int temp = Screen.getInstance().db.getCoins(player);
	   	    	//System.out.println(temp);
	   	        if(temp >= SCOOBY_PRICE)
	   	        {
	   	        	temp = temp - SCOOBY_PRICE;
	   	        	Screen.getInstance().db.setCoins(player, temp);
	   	        	Screen.getInstance().db.unlockTheme(player, SCOOBY_NAME);
	   	        	playerInfo1.setText(temp + " ");
	   	        	theme3.setEnabled(false);
	   	        }
	   	    }
	   	});
		
		theme4 = new JButton();
		theme4.setHorizontalTextPosition(SwingConstants.LEFT);
		theme4.setText(""+PIGS_PRICE);
		theme4.setFont(new Font("Comic Sans MS",Font.PLAIN,20));
		theme4.setForeground(Color.BLUE);
		theme4.setIcon(smallCoinIcon);
		theme4.setFocusable(false);
		theme4.setBounds(1000, 950, 200, 55);
		if(a.contains(PIGS_NAME))
			theme4.setEnabled(false);
		theme4.addActionListener (new ActionListener () {
	   	    public void actionPerformed(ActionEvent e) {
	   	    	int temp = Screen.getInstance().db.getCoins(player);
	   	    	//System.out.println(temp);
	   	        if(temp >= PIGS_PRICE)
	   	        {
	   	        	temp = temp - PIGS_PRICE;
	   	        	Screen.getInstance().db.setCoins(player, temp);
	   	        	Screen.getInstance().db.unlockTheme(player, PIGS_NAME);
	   	        	playerInfo1.setText(temp + " ");
	   	        	theme4.setEnabled(false);
	   	        }
	   	    }
	   	});

		//Buy diamond button
		buyDia = new JButton();
		buyDia.setHorizontalTextPosition(SwingConstants.LEFT);
		buyDia.setText(""+DIA_PRICE + " for ");
		buyDia.setFont(new Font("Comic Sans MS",Font.PLAIN,20));
		buyDia.setForeground(Color.BLUE);
		buyDia.setIcon(diamondIcon);
		buyDia.setFocusable(false);
		buyDia.setBounds(1650, 500, 250, 150);
		if(Screen.getInstance().db.getCoins(player) < DIA_PRICE)
			buyDia.setEnabled(false);
		buyDia.addActionListener (new ActionListener () {
	   	    public void actionPerformed(ActionEvent e) {
	   	    	int temp = Screen.getInstance().db.getCoins(player);
	   	    	if(temp >= DIA_PRICE)
	   	    	{
		   	    	temp = temp - DIA_PRICE;
		   	    	Screen.getInstance().db.setDiamonds(player, Screen.getInstance().db.getDiamonds(player) + 1);
		   	    	Screen.getInstance().db.setCoins(player, temp);
		   	    	playerInfo1.setText(Screen.getInstance().db.getCoins(player) + " ");
		   	    	playerInfo2.setText(Screen.getInstance().db.getDiamonds(player) + " ");
		   	    	if(Screen.getInstance().db.getCoins(player) < DIA_PRICE)
		   	    		buyDia.setEnabled(false);
		   	        }
	   	    	}
	   	    }
	   	);
		
		//Back button
		backButton = new JButton("Back");
		backButton.setFocusable(false);
		backButton.setBounds(1650, 900, 250, 150);
		backButton.setFont(new Font("Comic Sans MS",Font.PLAIN,32));
		backButton.setPreferredSize(new Dimension(250,150));
		
		backButton.setForeground(Color.BLUE);
		
		backButton.addActionListener(new ActionListener() { 
	    	  public void actionPerformed(ActionEvent e) {
	    		    Screen.getInstance().setActivePage("Play");
	    		  } 
	    		} );
		//Adding everything to a placeholder panel
		p.add(backButton);
		
		p.add(playerInfo1);
		p.add(playerInfo2);
		p.add(buyDia);
		
		p.add(theme1);
		p.add(theme2);
		p.add(theme3);
		p.add(theme4);
		
		
		p.add(theme1Image);
		p.add(theme2Image);
		p.add(theme3Image);
		p.add(theme4Image);
		
		p.setOpaque(false);
		this.add(p);//adding panel to this
		this.setOpaque(false);
	}
	
}
