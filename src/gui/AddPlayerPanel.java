package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class AddPlayerPanel extends JPanel {

	private JPanel entryPanel;
	private JPanel buttonsPanel;
	private JPanel gluePanel;
	private JButton backButton;
	private JButton saveButton;
	private JTextField playerName;
	private JLabel l;

	public AddPlayerPanel() {
		entryPanel = new JPanel();
		
		buttonsPanel = new JPanel ();
		gluePanel = new JPanel ();//to make it 
		entryPanel.setOpaque(false);
		buttonsPanel.setOpaque(false);
		gluePanel.setOpaque(false);
		
		gluePanel.setPreferredSize(new Dimension(1920,340));
		buttonsPanel.setPreferredSize(new Dimension(1920,100));
		entryPanel.setPreferredSize(new Dimension(350,100));
		
		this.add(gluePanel);
		this.add(entryPanel);
		this.add(buttonsPanel);
		l = new JLabel("Please enter your name:");
		l.setFont(new Font("Comic Sans MS",Font.BOLD,24));
		l.setForeground(Color.BLUE);
		l.setPreferredSize(new Dimension(350,50));
		
		
		backButton = new JButton("Back");
		backButton.setFont(new Font("Comic Sans MS",Font.BOLD,16));
		backButton.setForeground(Color.BLUE);
		backButton.setFocusable(false);;
		backButton.addActionListener(new ActionListener() { 
	    	  public void actionPerformed(ActionEvent e) {
	    		    Screen.getInstance().setActivePage("PlayerSelect");

	    		  } 
	    		});
		
		
		saveButton = new JButton("Save");
		saveButton.setFont(new Font("Comic Sans MS",Font.BOLD,16));
		saveButton.setForeground(Color.BLUE);
		saveButton.setFocusable(false);
		
		
		playerName = new JTextField();
		playerName.setBackground(Color.WHITE);
		playerName.setForeground(Color.BLACK);
		playerName.setFont(new Font("Comic Sans MS",Font.BOLD,16));
		playerName.setPreferredSize(new Dimension(350,50));
		
		
		backButton.setPreferredSize(new Dimension(175,40));
		saveButton.setPreferredSize(new Dimension(175,40));
		
		entryPanel.add(l);
		entryPanel.add(playerName);
		buttonsPanel.add(backButton);
		buttonsPanel.add(saveButton);
		
		backButton.addActionListener(new ActionListener() { 
	    	  public void actionPerformed(ActionEvent e) {
	    		    Screen.getInstance().setActivePage("PlayerSelect");
	    		  } 
	    		});
		
		
		saveButton.addActionListener(new ActionListener() { 
	    	  public void actionPerformed(ActionEvent e) {
	    		  	Screen.getInstance().db.addUser(playerName.getText()); 
	    		    Screen.getInstance().setActivePage("PlayerSelect");
	    		    	
	    		  } 
	    		});
		this.setOpaque(false);
	}
}
