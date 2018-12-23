package gui;

import engine.Engine;
import engine.EnginePartWindow;
import engine.Handler;
import gameplay.GameBoard;
import gameplay.LevelCreator;

import javax.swing.*;
import java.util.logging.Level;


public class CreatorPanel extends JPanel implements EnginePartWindow {
    /*Engine engine;*/
    LevelCreator board;

    public CreatorPanel(){


    }


    public void setBoard(LevelCreator board) {
        if(this.board != null){
            Handler.getInstance().remove(this.board);
        }
        this.board = board;
    }


}