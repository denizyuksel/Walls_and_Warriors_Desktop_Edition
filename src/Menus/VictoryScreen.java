package Menus;

import gameplay.GameBoard;
import gameplay.LevelCreator;
import gameplay.Wall;
import gui.Screen;
import main.Main;
import util.Sprites;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;



public class VictoryScreen implements ActionListener {

    // window
    JWindow w;
    JLabel label1;
    JButton button4;
    // constructor
    public VictoryScreen(int coins) {
        // create a window
        w = new JWindow();
        //w = new JDialog();
        Image bigCoin = Sprites.Coin.getScaledInstance(100, 100, Image.SCALE_SMOOTH);
        ImageIcon bigCoinIcon = new ImageIcon(bigCoin);

        // create a label
        JLabel l = new JLabel("Victory",JLabel.HORIZONTAL);
        l.setFont(new Font("Comic Sans MS",Font.PLAIN,30));
        l.setForeground(Color.BLACK);


        // create a new button
        //label1 = new JLabel("Coins: " + coins + "", JLabel.HORIZONTAL);
        label1 = new JLabel(coins + "  x  ");
        label1.setHorizontalTextPosition(SwingConstants.LEFT);
        label1.setFont(new Font("Comic Sans MS",Font.PLAIN,26));
        label1.setIcon(bigCoinIcon);
        


        button4 = new JButton("Main Menu",new ImageIcon(Sprites.ButtonIcon));
        button4.setFont(new Font("Comic Sans MS",Font.PLAIN,16));
        button4.setHorizontalTextPosition(JButton.CENTER);
        button4.setVerticalTextPosition(JButton.CENTER);
        JPanel p = new JPanel();
        JPanel p4 = new JPanel();

        /*p.setBackground(Color.BLACK);
        p2.setBackground(Color.BLACK);
        p4.setBackground(Color.BLACK);*/

        p.setOpaque(false);
       p4.setOpaque(false);



        // add action listener
        //label1.addActionListener(this);

        button4.addActionListener(this);

        // create a panel
        JPanel main = new JPanel();
        JPanel realMain = new JPanel();


        // add contents to panel
        l.setPreferredSize(new Dimension(200,20));

        label1.setPreferredSize(new Dimension(200,100));
        button4.setPreferredSize(new Dimension(200,100));
        label1.setOpaque(false);
        label1.setFocusable(false);



        label1.setBackground(Color.LIGHT_GRAY);

        button4.setOpaque(false);
        button4.setFocusable(false);
        button4.setContentAreaFilled(false);
        button4.setBorderPainted(false);

        realMain.setBackground(Color.LIGHT_GRAY);
        main.setBackground(Color.LIGHT_GRAY);



        //realMain.setContentAreaFilled(false);


        p.add(label1);


        p4.add(button4);
        main.add(l);
        main.add(p);
        main.add(p4);
        main.setLayout(new GridLayout(3,1));
        //realMain.setLayout(new GridLayout(2,1));

        //realMain.add(l);
        realMain.add(main);

        w.add(realMain);

        w.setSize(300, 400);
        w.setLocation(850, 300);
        w.show();

    }
    // if button is pressed
    public void actionPerformed(ActionEvent evt)
    {
        if(evt.getSource() == label1){
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
            /*Screen.game = null;*/
            GameBoard.map = null;
            Screen.getInstance().board.stopBGM();
            Screen.getInstance().board = null;
            for(int i = LevelCreator.allWalls.size()-1; i>=0 ; i--){
                LevelCreator.allWalls.set(i,null);
                LevelCreator.allWalls.remove(i);
            }



        }

    }
    public void setVisible(boolean bol){
        w.setVisible(bol);

    }
}
