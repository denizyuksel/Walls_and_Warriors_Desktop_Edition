package Menus;

// Java Program to create a simple JWindow
// and add label and button to it.
import gameplay.GameBoard;
import gameplay.LevelCreator;
import gui.Screen;
import main.Main;
import util.Sprites;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
public class PauseMenu implements ActionListener {

    // window
    public JWindow w;
    JDialog w1;
    JButton button1;
    JButton button2 ;
    JButton button3 ;
    JButton button4 ;


    // constructor
    public PauseMenu()
    {
        // create a window
        w = new JWindow();
        //w = new JDialog();

        // create a label
        JLabel l = new JLabel("Game Paused",JLabel.HORIZONTAL);
        l.setFont(new Font("Comic Sans MS",Font.PLAIN,16));
        l.setForeground(Color.BLACK);


        // create a new button
        button1 = new JButton("Resume",new ImageIcon(Sprites.ButtonIcon));
        button1.setFont(new Font("Comic Sans MS",Font.PLAIN,16));
        button1.setHorizontalTextPosition(JButton.CENTER);
        button1.setVerticalTextPosition(JButton.CENTER);
        //button1.setBorder(BorderFactory.createCompoundBorder(button1.getBorder(),BorderFactory.createLineBorder(Color.GREEN,4,true)));
        button1.setBorderPainted(false);
        //button1.setRolloverIcon(new ImageIcon(Sprites.Lava));

        button2 = new JButton("How To Play",new ImageIcon(Sprites.ButtonIcon));
        button2.setHorizontalTextPosition(JButton.CENTER);
        button2.setVerticalTextPosition(JButton.CENTER);
        button2.setFont(new Font("Comic Sans MS",Font.PLAIN,16));
        button2.setBorderPainted(false);

        button4 = new JButton("Main Menu",new ImageIcon(Sprites.ButtonIcon));
        button4.setFont(new Font("Comic Sans MS",Font.PLAIN,16));
        button4.setHorizontalTextPosition(JButton.CENTER);
        button4.setVerticalTextPosition(JButton.CENTER);
        button4.setBorderPainted(false);
        JPanel p = new JPanel();

        JPanel p2 = new JPanel();

        JPanel p4 = new JPanel();


        /*p.setBackground(Color.BLACK);
        p2.setBackground(Color.BLACK);
        p4.setBackground(Color.BLACK);*/

        p.setOpaque(false);
        p2.setOpaque(false);
        p4.setOpaque(false);



        // add action listener
        button1.addActionListener(this);

        button2.addActionListener(this);
        button4.addActionListener(this);

        // create a panel
        JPanel main = new JPanel();
        JPanel realMain = new JPanel();


        // add contents to panel
        l.setPreferredSize(new Dimension(200,20));

        button1.setPreferredSize(new Dimension(200,100));
        button2.setPreferredSize(new Dimension(200,100));
        button4.setPreferredSize(new Dimension(200,100));
        button1.setOpaque(false);
        button1.setFocusable(false);
        button1.setContentAreaFilled(false);
        button1.setBackground(Color.LIGHT_GRAY);

        button2.setOpaque(false);
        button2.setFocusable(false);
        button2.setContentAreaFilled(false);

        button4.setOpaque(false);
        button4.setFocusable(false);
        button4.setContentAreaFilled(false);

        realMain.setBackground(Color.LIGHT_GRAY);
        main.setBackground(Color.LIGHT_GRAY);



        //realMain.setContentAreaFilled(false);


        p.add(button1);
        p2.add(button2);
        p4.add(button4);
        main.add(p);
        main.add(p2);
        main.add(p4);
        main.setLayout(new GridLayout(5,1));
        realMain.add(l);
        realMain.add(main);

        w.add(realMain);

        w.setSize(300, 370);
        w.setLocation(850, 300);
        w.show();
    }

    // if button is pressed
    public void actionPerformed(ActionEvent evt)
    {
        if(evt.getSource() == button1){
            w.setVisible(false);
            GameBoard.paused = false;
        }
        else if(evt.getSource() == button2){


            JOptionPane p = new JOptionPane();
            JOptionPane.showMessageDialog(w, "Place walls in a way such that the Towers, Towers moat and the blue knights are enclosed inside the walls. \n" +
                                                        "You can left-click and drag the walls to their place on the board, right-click will make them rotate 90 degrees\n" +
                                                        "Red Knights must be outside of the walls and they can not be enclosed on all four sides\n " +
                                                      "You can Press Q to enter the Remove mode where you can remove a knight form the board! but it costs a diamond... \n" +
                                                      "You can Press E to Exit Remove mode \n " +
                                                      " ","How to play?",
                    JOptionPane.PLAIN_MESSAGE);
            w.setVisible(false);
            GameBoard.paused = false;


        }

        else if(evt.getSource() == button4){

            Main.frame.setVisible(true);
            Screen.getInstance().setActivePage("MainMenu");
            Screen.getInstance().engine.stop();
            Screen.getInstance().game.remove(Screen.getInstance().engine);
            Screen.getInstance().game.setVisible(false);
            Screen.getInstance().engine.destroy();
            Screen.getInstance().engine = null;
            //game.dispatchEvent(new WindowEvent(game, WindowEvent.WINDOW_CLOSING));
            Screen.getInstance().game.dispose();
            //Screen.getInstance().game = null;
            GameBoard.map = null;
            Screen.getInstance().board.stopBGM();
            Screen.getInstance().board = null;
            for(int i = LevelCreator.allWalls.size()-1; i>=0 ; i--){
                LevelCreator.allWalls.set(i,null);
                LevelCreator.allWalls.remove(i);
            }
            //System.out.println(LevelCreator.allWalls.size());
        }

    }
    public void setVisible(boolean bol){
        w.setVisible(bol);

    }

}