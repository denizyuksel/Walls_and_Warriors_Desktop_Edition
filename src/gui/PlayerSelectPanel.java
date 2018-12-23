package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.plaf.basic.BasicScrollBarUI;

public class PlayerSelectPanel extends JPanel {

	private JScrollPane userButtons;
	private JPanel users;
	private JPanel buttons;
	private JButton exitButton;
	private JButton createUserButton;
	private JPanel userPanel; 
	private JPanel bigPanel;
	
	public PlayerSelectPanel() {
		userPanel = new JPanel(new GridLayout(0,1,0,0));
		userPanel.setOpaque(false);
		users = new JPanel();
		users.setOpaque(false);
		buttons = new JPanel();
		buttons.setOpaque(false);
		bigPanel = new JPanel();
		bigPanel.setOpaque(false);
		bigPanel.setPreferredSize(new Dimension(400,20000));
		//All players  are put in a panel that will be put in a JScrollPane
		if(Screen.getInstance().db.getAllUsernames() != null)
		{
			for(String currentName: Screen.getInstance().db.getAllUsernames())
			{
				String lastUnlockedLevel = (Screen.getInstance().db.getLastUnlockedLevel(currentName)).toString();
				String s = currentName;
				JButton j = new JButton();
				JPanel user = new JPanel(new FlowLayout());
				user.setOpaque(false);
				j.setHorizontalTextPosition(SwingConstants.RIGHT);
				j.setText(s);
				j.setFont(new Font("Comic Sans MS",Font.PLAIN,28));
				j.setForeground(Color.RED);
				j.setPreferredSize(new Dimension(350,75));
				j.setOpaque(false);
				j.setFocusable(false);
				
				j.setAlignmentY(Component.TOP_ALIGNMENT);
				j.setContentAreaFilled(false);
				j.addActionListener(new ActionListener() { 
					public void actionPerformed(ActionEvent e) { 
						Screen.getInstance().selectedPlayer = currentName;
						Screen.getInstance().setActivePage("MainMenu");
	    		  } 
	    		} );
				user.add(j);
				
				user.setAlignmentY(Component.TOP_ALIGNMENT);
				userPanel.add(user);
			}
		}
		users.add(userPanel);
		buttons.setPreferredSize(new Dimension(350,850));
		
		//Create user button
		createUserButton = new JButton("Create a new player");
		createUserButton.setPreferredSize(new Dimension(350,50));
		createUserButton.setFont(new Font("Comic Sans MS",Font.BOLD,16));
		createUserButton.setForeground(Color.BLUE);
		createUserButton.setFocusable(false);
		createUserButton.addActionListener(new ActionListener() { 
	    	  public void actionPerformed(ActionEvent e) {
	    		    Screen.getInstance().setActivePage("AddPlayer");

	    		  } 
	    		} );
	    buttons.add(createUserButton);
	    
	    //Exit Button
	    exitButton = new JButton("Exit");
	    exitButton.setFocusable(false);
	    exitButton.setPreferredSize(new Dimension(350,50));
	    exitButton.setFont(new Font("Comic Sans MS",Font.BOLD,16));
	    exitButton.setForeground(Color.BLUE);
	    exitButton.addActionListener(new ActionListener() { 
	    	  public void actionPerformed(ActionEvent e) { 
	    		    System.exit(0);
	    		  } 
	    		} );
	    exitButton.setPreferredSize(new Dimension(350,50));
	    buttons.add(exitButton);
	    
	    
	    userButtons = new JScrollPane(users,ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);//Creating JScrollPane
	    userButtons.setOpaque(false);
	    userButtons.getViewport().setOpaque(false);
	    userButtons.getVerticalScrollBar().setPreferredSize( new Dimension(0,0));
	    userButtons.setPreferredSize(new Dimension(380, 500));
	    bigPanel.add(userButtons);
	    bigPanel.add(buttons);
	    this.add(bigPanel);
	    this.setOpaque(false);
	}
	
	
	
}
