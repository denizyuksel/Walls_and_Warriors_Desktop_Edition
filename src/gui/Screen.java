package gui;

import java.awt.*;
import java.util.ArrayList;

import javax.swing.*;

import engine.AudioPlayer;
import engine.Engine;
import gameplay.DatabaseManager;
import gameplay.GameBoard;
import gameplay.LevelCreator;
import main.Main;
import util.Sprites;

import static main.Main.BoardXCoordinate;
import static main.Main.BoardYCoordinate;

public class Screen extends JPanel{

	private static Screen screen; //This panel will be added to the frame
	private String activePage; //added to keep track of the active page and make the code more readable
	public static DatabaseManager db;
	public static String selectedPlayer;
	public static ArrayList<String> a ;
	public GamePanel game;
	public Engine engine;
	public GameBoard board;
	public LevelCreator creator;
	public AudioPlayer MenuPlayer;

	
	public Screen() {
		db = new DatabaseManager();
		//Next two commented lines are added to make sure that the database works correctly
		//Uncomment and run the program ONCE 
		//Then comment these back
		//If you have any problems with the database
		
		
		//db.removeAllSandboxLevels(); //purge all sandbox levels
		//db.deleteAllUsers();// purge all players
		activePage = "";
		MenuPlayer = Sprites.MenuBack;
		repaint();
	}
	
	public static Screen getInstance()
	{
		if(screen == null)
		{
			screen = new Screen();
			screen.setLayout(new CardLayout() {
				  @Override
				  public void show(java.awt.Container parent, String name) {
				    super.show(parent, name);
				    parent.revalidate();
				    parent.repaint();
				  }
				});
			screen.add("PlayerSelect",new PlayerSelectPanel());
		}
		
		return screen;
	}
	
	public String activePage() { //gets the active page
		return activePage;
	}

	public void setActivePage(String settedPage) { //Sets the currently active page
		if (screen != null) {
			activePage = settedPage;
			((CardLayout)screen.getLayout()).show(screen, activePage);
			JPanel selected = null;
			//Removing the currently visible panel in order to make it repaint again
			for (Component comp : screen.getComponents()) {
			    if (comp.isVisible() == true) {
			    	selected = (JPanel) comp;
			    }
			}
			if(selected != null && selected.isVisible())
				screen.remove(selected);
			
			if(activePage.equals("PlayerSelect"))	
			{
				screen.add("PlayerSelect",new PlayerSelectPanel());
				((CardLayout)screen.getLayout()).show(screen, activePage);
			}
			else if(activePage.equals("AddPlayer"))	
			{
				screen.add("AddPlayer",new AddPlayerPanel());
				((CardLayout)screen.getLayout()).show(screen, activePage);
			}
			
			else if(activePage.equals("MainMenu"))	
			{
				screen.add("MainMenu",new MainMenu());
				((CardLayout)screen.getLayout()).show(screen, activePage);
			}
			
			else if(activePage.equals("Play"))
			{
				screen.add("Play",new PlayScreen());
				((CardLayout)screen.getLayout()).show(screen, activePage);
			}
			
			else if(activePage.equals("Shop"))
			{
				screen.add("Shop",new ShopPanel());
				((CardLayout)screen.getLayout()).show(screen, activePage);
			}
			
			else if(activePage.equals("Classic"))
			{
				screen.add("Classic",new ClassicModePanel());
				((CardLayout)screen.getLayout()).show(screen, activePage);
			}
			
			else if(activePage.equals("Sandbox"))
			{
				screen.add("Sandbox",new SandboxModePanel());
				((CardLayout)screen.getLayout()).show(screen, activePage);
			}
			else if(activePage.equals("SandBoxSize"))
			{
				screen.add("SandBoxSize",new SandBoxSizePanel());
				((CardLayout)screen.getLayout()).show(screen, activePage);
			}
			
			else if(activePage.equals("Credits"))
			{
				screen.add("Credits",new CreditsPanel());
				((CardLayout)screen.getLayout()).show(screen, activePage);
			}

		}
	}
	
	//setting the active page for a classic or sandbox level
	public void setActivePage(String settedPage,char[][] map,int size,int[] walls) {
		if (screen != null) {
			activePage = settedPage;
			((CardLayout) screen.getLayout()).show(screen, activePage);
			JPanel selected = null;
			for (Component comp : screen.getComponents()) {
				if (comp.isVisible() == true) {
					selected = (JPanel) comp;
				}
			}
			screen.remove(selected);

			if (activePage.equals("PlayBoard")) {//Creating a new frame and running the game on it
				game = new GamePanel();
				board = new GameBoard(BoardXCoordinate, BoardYCoordinate, map, size, walls, db.getCurrentTheme(selectedPlayer));
				GameBoard.map =map;
				engine = new Engine();
				Main.frame.setVisible(false);
				game.setUndecorated(true);
				game.setSize(new Dimension(1920,1080));
				game.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
				engine.setWindow(game);
				game.add(engine);
				game.setVisible(true);
				game.setState(Frame.NORMAL);
				engine.start();


			}
		}
	}
	//setting the active page for creating a level
	public void setActivePage(String settedPage,int size) {
		if (screen != null) {
			activePage = settedPage;
			((CardLayout) screen.getLayout()).show(screen, activePage);
			JPanel selected = null;
			for (Component comp : screen.getComponents()) {
				if (comp.isVisible() == true) {
					selected = (JPanel) comp;
				}
			}
			screen.remove(selected);
			if (activePage.equals("CreateBoard")) {//Creating a new frame and running the game on it
				game = new GamePanel();
				//System.out.println("test");
				creator = new LevelCreator(BoardXCoordinate, BoardYCoordinate, size, db.getCurrentTheme(selectedPlayer));
				engine = new Engine();
				//Main.frame.setVisible(false);
				game.setUndecorated(true);
				game.setSize(new Dimension(1920, 1080));
				game.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
				engine.setWindow(game);
				game.add(engine);
				game.setVisible(true);
				game.setState(Frame.NORMAL);
				engine.start();
				Main.frame.setVisible(false);



			}
		}
	}
	
	@Override
	public void paint(Graphics g) {
		g.drawImage(Sprites.MenuScreen,0,0,1920,1080,null);
		setOpaque(false);
		super.paint(g);
		setOpaque(true);


	}
}


