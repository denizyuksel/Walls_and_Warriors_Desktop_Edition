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



public class LevelSaved implements ActionListener {

    // window
    JWindow w;

    JButton button4;
    // constructor
    public LevelSaved() {
        // create a window
        w = new JWindow();
        // create a label
        JLabel l = new JLabel("Level Saved",JLabel.HORIZONTAL);
        l.setFont(new Font("Comic Sans MS",Font.PLAIN,30));
        l.setForeground(Color.BLACK);
        button4 = new JButton("Main Menu",new ImageIcon(Sprites.ButtonIcon));
        button4.setFont(new Font("Comic Sans MS",Font.PLAIN,16));
        button4.setHorizontalTextPosition(JButton.CENTER);
        button4.setVerticalTextPosition(JButton.CENTER);
        JPanel p = new JPanel();
        JPanel p4 = new JPanel();
        p4.setOpaque(false);
        button4.addActionListener(this);
        // create a panel
        JPanel main = new JPanel();
        JPanel realMain = new JPanel();
        // add contents to panel
        l.setPreferredSize(new Dimension(200,20));
        button4.setPreferredSize(new Dimension(200,100));
        button4.setOpaque(false);
        button4.setFocusable(false);
        button4.setContentAreaFilled(false);
        button4.setBorderPainted(false);

        realMain.setBackground(Color.LIGHT_GRAY);
        main.setBackground(Color.LIGHT_GRAY);



        //realMain.setContentAreaFilled(false);


        p4.add(button4);

        main.add(p4);
        realMain.setLayout(new GridLayout(2,1));
        realMain.add(l);
        realMain.add(main);

        w.add(realMain);

        w.setSize(400, 370);
        w.setLocation(850, 300);
        w.show();

    }
    // if button is pressed
    public void actionPerformed(ActionEvent evt)
    {
        if(evt.getSource() == button4){


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
            Screen.getInstance().creator.stopBGM();
            Screen.getInstance().creator = null;
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