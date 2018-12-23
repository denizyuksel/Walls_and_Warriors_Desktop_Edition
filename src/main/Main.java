package main;

import engine.AudioPlayer;
import engine.Engine;
import engine.Keyboard;
import gameplay.DatabaseManager;
import gameplay.GameBoard;
import gameplay.LevelCreator;
import gui.Screen;

import javax.swing.*;
import java.awt.*;
import java.util.Scanner;

public class Main {
	public static int BoardXCoordinate = 20;
	public static int BoardYCoordinate = 100;
	//public static DatabaseManager db = new DatabaseManager();
	public static Engine a;
	public static JFrame frame;
	
	public static void main(String[] args) {

		char[][] map = {{ 'I', 'I', 'E', 'E', 'E', 'E', 'E', 'I', 'I' },
				{ 'I', 'I', 'E', 'T', 'S', 'T', 'E', 'I', 'I' },
				{ 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E' },
				{ 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'B', 'E' },
				{ 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E' },
				{ 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E' },
				{ 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E' },
				{ 'E', 'E', 'E', 'B', 'E', 'B', 'E', 'E', 'E' },
				{ 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E' },
				{ 'I', 'I', 'E', 'E', 'E', 'E', 'E', 'I', 'I' },
				{ 'I', 'I', 'E', 'E', 'E', 'E', 'E', 'I', 'I' }};


		frame = new JFrame();
		//Screen s = new Screen();
		frame.add(Screen.getInstance());
		frame.setUndecorated(true);
		frame.pack();
		frame.setPreferredSize(new Dimension(1920,1080));
		frame.setVisible(true);
		//frame.setPreferredSize(new Dimension(1920,1080));
		frame.pack();
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);



	}
}