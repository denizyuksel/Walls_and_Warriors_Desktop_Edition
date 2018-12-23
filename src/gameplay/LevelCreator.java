
package gameplay;


import java.awt.*;
import java.util.ArrayList;

import Menus.LevelSaved;
import Menus.PauseMenu;
import Menus.PauseMenu2;
import Menus.VictoryScreen;
import engine.AudioPlayer;
import engine.GameObject;
import engine.Keyboard;
import engine.Mouse;
import gui.Screen;
import main.Main;
import util.Sprites;

import javax.swing.*;

import static gameplay.GameBoard.*;
import static gameplay.Wall.*;

public class LevelCreator extends GameObject {

	public static final int WALL_THICKNESS = 35;
	public static final int CELL_THICKNESS = 4 * WALL_THICKNESS;
	protected static final int ARC_WIDTH = 32;
	protected static final int ARC_HEIGHT = 32;
	protected static final Color WALL_FIELD_COLOR = new Color(86, 86, 86);
	protected static final Color CELL_FIELD_COLOR = new Color(155, 155, 155);
	protected ArrayList<Point> redKnights = new ArrayList<>();
	protected ArrayList<Point> blueKnights= new ArrayList<>();
	protected ArrayList<Point> towers= new ArrayList<>();
	private boolean oldMB1;
	private boolean oldMB3;
	private boolean oldestMB1;
	private boolean oldEnter;
	private boolean wallExists;
	private int[][] paths;
	private int [] wallCount;
	public static ArrayList<Wall> allWalls =  new ArrayList<>();
	public static String CurrentPlayer;
	private ArrayList<Wall> placedWalls = new ArrayList<>();
	private int boardSize;
	private int unitControl;
	private Wall inPlay;
	private PauseMenu2 m;
	private LevelSaved v;
	private AudioPlayer placeKnightSound;
	private AudioPlayer removeSound;
	private AudioPlayer bgm;


	public LevelCreator(int x, int y, int size,String theme) {
		super(x, y);
		oldMB1 = false;
		oldMB3 = false;
		Screen.getInstance().MenuPlayer.setVolume(-80F);
		GameBoard.THEME = theme;
		setBGM();
		m = new PauseMenu2();
		m.setVisible(false);
		v = new LevelSaved();
		v.setVisible(false);
		GameBoard.sur = new ArrayList<>();
		boardSize = size;
		unitControl = 0;
		oldEnter = false;
		placeKnightSound = new AudioPlayer("/sound_effects/putKnight.wav");
		placeKnightSound.setVolume(-9.f);
		removeSound = new AudioPlayer("/sound_effects/maledeath2.wav");
		removeSound.setVolume(-9.f);
		paused = false;
		
		if ( size == 0) //small board
		{
			GameBoard.map = new char[9][9];
			GameBoard.BOARD_WIDTH = 5 * WALL_THICKNESS + 4 * CELL_THICKNESS;
			GameBoard.BOARD_HEIGHT = 5 * WALL_THICKNESS + 4 * CELL_THICKNESS;
		}
		
		else if ( size == 1) //medium board
		{
			GameBoard.map = new char[11][9];
			GameBoard.BOARD_WIDTH = 6 * WALL_THICKNESS + 5 * CELL_THICKNESS;
			GameBoard.BOARD_HEIGHT = 5 * WALL_THICKNESS + 4 * CELL_THICKNESS;
		}
		
		else if ( size == 2) //LARGEE board
		{
			GameBoard.map = new char[13][9];
			GameBoard.BOARD_WIDTH = 7 * WALL_THICKNESS + 6 * CELL_THICKNESS;
			GameBoard.BOARD_HEIGHT = 5 * WALL_THICKNESS + 4 * CELL_THICKNESS;
		}
		
		for (int i = 0; i < GameBoard.map.length; i++)
			for(int j = 0; j < GameBoard.map[0].length; j++)
			{
				if( (i < 2 && j < 2) | (i < 2 && j >= GameBoard.map[0].length - 2) | (i >= GameBoard.map.length - 2 && j < 2) | (i >= GameBoard.map.length && j >= GameBoard.map[0].length - 2))
					GameBoard.map[i][j] = 'I';
				else
					GameBoard.map[i][j] = 'E';
			}
		wallCount =  new int[6];
		paths = new int[GameBoard.map.length][GameBoard.map[1].length];
		setPaths();
		setBGM();
		bgm.play();
		bgm.loop(100);
	}

	public LevelCreator(int x, int y, int width, int height, int size,String theme) {
		super(x, y, width, height);
		unitControl = 0;
		oldMB1 = false;
		oldMB3 = false;
		oldEnter = false;
		Screen.getInstance().MenuPlayer.setVolume(-80F);
		GameBoard.sur = new ArrayList<>();
		m = new PauseMenu2();
		m.setVisible(false);
		v = new LevelSaved();
		v.setVisible(false);

		GameBoard.THEME = theme;
		placeKnightSound = new AudioPlayer("/sound_effect/putKnight.wav");
		placeKnightSound.setVolume(-9.f);
		removeSound = new AudioPlayer("/sound_effects/maledeath2.wav");
		removeSound.setVolume(-9.f);
		paused = false;
		if ( size == 0) //small board
		{
			GameBoard.map = new char[9][9];
		}
		
		else if ( size == 1) //medium board
		{
			GameBoard.map = new char[11][9];
		}
		
		else if ( size == 2) //LARGEE board
		{
			GameBoard.map = new char[13][9];
		}
		
		for (int i = 0; i < GameBoard.map.length; i++)
			for(int j = 0; j < GameBoard.map[0].length; j++)
			{
				if( (i < 2 && j < 2) | (i < 2 && j >= GameBoard.map[0].length - 2) | (i >= GameBoard.map.length - 2 && j < 2) | (i >= GameBoard.map.length && j >= GameBoard.map[0].length - 2))
					GameBoard.map[i][j] = 'I';
				else
					GameBoard.map[i][j] = 'E';
			}
		wallCount =  new int[6];
		paths = new int[GameBoard.map.length][GameBoard.map[1].length];
		setPaths();
		setBGM();
		bgm.play();
		//bgm.setVolume(-15F);
		bgm.loop(100);
	}
	private  void setPaths(){
		for (int i = 0; i < paths.length; i++) {
			for (int j = 0; j < paths[0].length; j++) {
				//System.out.println("Ä° : " + i + ", J: " + j);
				if(GameBoard.map[i][j] == 'W' || GameBoard.map[i][j] == 'w') {
					//System.out.println("-1 ak");
					paths[i][j] = -1;
				}
				else
					paths[i][j] = 0;
			}
		}

	}

	// This method Ticks with intervals to perform many functions
	@SuppressWarnings("Duplicates")
	@Override
	public void tick() {
		if(Keyboard.ESCAPE){

			pause();

		}
		if(paused)
			return;



		if(!Wall.blinking ) {



			if (inPlay != null && inPlay.placed) {
				placedWalls.add(inPlay);
				wallExists = false;
				inPlay = null;
			}
			// This sets the current placing mode
			if (!Mouse.MB1) {
				// blue knight will be added
				if (Keyboard.B) {
					unitControl = 1;
					if (inPlay != null) {
						inPlay.disable();
						wallExists = false;
					}
				}//red knight will be added
				else if (Keyboard.R) {
					unitControl = 2;
					if (inPlay != null) {
						inPlay.disable();
						wallExists = false;
					}
				} //tower will be added
				else if (Keyboard.T) {
					unitControl = 3;
					if (inPlay != null) {
						inPlay.disable();
						wallExists = false;
					}
				} //Walls will be added
				else if (Keyboard.ONE) {
					unitControl = 0;
					if (inPlay != null)
						inPlay.disable();
					inPlay = null;
					inPlay = createWall1(1150, 300);
					wallExists = true;

				} else if (Keyboard.TWO) {
					unitControl = 0;
					if (inPlay != null)
						inPlay.disable();
					inPlay = null;
					inPlay = createWall2(1150, 300);
					wallExists = true;
				} else if (Keyboard.THREE) {
					unitControl = 0;
					if (inPlay != null)
						inPlay.disable();
					inPlay = null;
					inPlay = createWall3(1150, 300);
					wallExists = true;
				} else if (Keyboard.FOUR) {
					unitControl = 0;
					if (inPlay != null)
						inPlay.disable();
					inPlay = null;
					inPlay = createWall4(1150, 300);
					wallExists = true;
				} else if (Keyboard.FIVE) {
					unitControl = 0;
					if (inPlay != null)
						inPlay.disable();
					inPlay = null;
					inPlay = createWall5(1150, 300);
					wallExists = true;
				} else if (Keyboard.SIX) {
					unitControl = 0;
					if (inPlay != null)
						inPlay.disable();
					inPlay = null;
					inPlay = createWall6(1150, 300);
					wallExists = true;
				} else if (Keyboard.ENTER && !oldEnter) {

					if (savePossible()) {
						for (Wall w : placedWalls) {
							wallCount[w.getShape() - 1] = wallCount[w.getShape() - 1] + 1;
						}
						for (int i = 0; i < 6; i++) {
							//System.out.println(wallCount[i]);

						}
						for(int i = 0; i < GameBoard.map.length; i++) {
							for (int j = 0; j < GameBoard.map[i].length; j++) {
								if(GameBoard.map[i][j] == 'W')
									GameBoard.map[i][j] = 'E';
							}
						}
						Screen.db.addSandboxLevel(GameBoard.map,wallCount,boardSize);
						v.setVisible(true);
						paused = true;
						bgm.stop();

					} else {
						for (int i : wallCount)
							wallCount[i] = 0;
					}
				}
			}

			if (Mouse.MB1 == true && oldMB1 == false) {
				if (unitControl == 1 || unitControl == 2)
					placeKnight(unitControl, true);
				if (unitControl == 3)
					placeTower(true);
			}


			if (Mouse.MB1 == false && oldMB1 == false && oldestMB1 == true) {
				Wall temp = null;
				for (Wall myWall : placedWalls) {
					if (myWall.placed == false) {
						myWall.disable();
						temp = myWall;
					}
				}
				for (int i = 0; i < placedWalls.size(); i++) {
					if (placedWalls.get(i).equals(temp))
						placedWalls.remove(i);
				}
			}

			if (Mouse.MB3 == true && oldMB3 == false) {
				if (unitControl == 1 || unitControl == 2)
					placeKnight(unitControl, false);
				if (unitControl == 3)
					placeTower(false);
			}
			oldMB3 = Mouse.MB3;
			oldestMB1 = oldMB1;
			oldMB1 = Mouse.MB1;
			oldEnter = Keyboard.ENTER;

		}


	}
	//Place a knight on the board
	public void placeKnight(int color, boolean boi) {// boi represents adding or removing from the board
		int x = (int) Mouse.MOUSE_X-this.x;
		int y = (int) Mouse.MOUSE_Y-this.y;

		for(Wall w:allWalls){
			if(w.getBounds().contains(x+this.x,y+this.y))
				return;
		}

		if(x < (this.x+GameBoard.BOARD_WIDTH) && y < (this.y+GameBoard.BOARD_HEIGHT) && x> this.x && y > this.y ) {
			int tempX = (int)x / (WALL_THICKNESS+CELL_THICKNESS);
			if(x-(tempX*(WALL_THICKNESS+CELL_THICKNESS))-WALL_THICKNESS > 0)
                tempX = (tempX*2)+1;
			else
			    tempX = tempX*2;

			int tempY = (int)y / (WALL_THICKNESS+CELL_THICKNESS);
			if(y-(tempY*(WALL_THICKNESS+CELL_THICKNESS))-WALL_THICKNESS > 0)
				tempY = (tempY*2)+1;
			else
			    tempY = tempY*2;
			//System.out.println("x: " + tempX + "y: " + tempY);
			if(color == 1 && (tempX * tempY % 2 == 1) && GameBoard.map[tempX][tempY] != 'I' && boi && !WallMoving) {
				GameBoard.map[tempX][tempY] = 'B';
				placeKnightSound.play();
			}
			else if (color == 2 && (tempX * tempY % 2 == 1) && GameBoard.map[tempX][tempY] != 'I' && boi && !WallMoving) {
				GameBoard.map[tempX][tempY] = 'R';
				placeKnightSound.play();
			}
			else if((tempX * tempY % 2 == 1) && GameBoard.map[tempX][tempY] != 'T' &&  GameBoard.map[tempX][tempY] != 'I' && !boi){
				GameBoard.map[tempX][tempY] = 'E';
				removeSound.play();

			}


		}	
	}
	//Place a tower on the board
	public void placeTower(boolean bru)
	{
		int x = (int) Mouse.MOUSE_X-this.x;
		int y = (int) Mouse.MOUSE_Y-this.y;

		for(Wall w:allWalls){
			if(w.getBounds().contains(x+this.x,y+this.y))
				return;
		}
		if(x < (this.x+GameBoard.BOARD_WIDTH) && y < (this.y+GameBoard.BOARD_HEIGHT) && x> this.x && y > this.y ) {
			int tempX = (int)x / (WALL_THICKNESS+CELL_THICKNESS);
			if(x-(tempX*(WALL_THICKNESS+CELL_THICKNESS))-WALL_THICKNESS > 0)
                tempX = (tempX*2)+1;
			else
			    tempX = tempX*2;

			int tempY = (int)y / (WALL_THICKNESS+CELL_THICKNESS);
			if(y-(tempY*(WALL_THICKNESS+CELL_THICKNESS))-WALL_THICKNESS > 0)
				tempY = (tempY*2)+1;
			else
			    tempY = tempY*2;
		
		if( (tempX * tempY % 2 == 1) && GameBoard.map[tempX][tempY] != 'I' && bru && !WallMoving) {
			placeKnightSound.play();
			GameBoard.map[tempX][tempY] = 'T';
		}
		else if( (tempX * tempY % 2 != 1) && GameBoard.map[tempX][tempY] != 'I' && !(tempX % 2 == 0 && tempY % 2 == 0) && bru && !WallMoving) {
			placeKnightSound.play();
			GameBoard.map[tempX][tempY] = 'S';
			sur.add(new Point(tempX,tempY));
		}
		else if(GameBoard.map[tempX][tempY] != 'I' && !bru){
			removeSound.play();
			GameBoard.map[tempX][tempY] = 'E';
			sur = getSur();
		}
		}
	}

	//Paints the board according to the theme
	@Override
	public void render(Graphics2D g) {

		if(THEME.equals( "hello there"))
			g.drawImage(Sprites.starwars, 0, 0 ,1700, 930, null);
		else if(THEME .equals( "lord of the rings"))
			g.drawImage(Sprites.LotrBack, 0, 0 ,1700, 930, null);
		else if(THEME .equals( "three little pigs"))
			g.drawImage(Sprites.PigBack, 0, 0 ,1700, 930, null);
		else if(THEME .equals( "scooby doo"))
			g.drawImage(Sprites.ScoobyBack, 0, 0 ,1700, 930, null);
		else
			g.drawImage(Sprites.ClassicBack, 0, 0 ,1700, 930, null);
		if(paused)
			return;

		Stroke oldStroke = g.getStroke();
		g.setStroke(new BasicStroke(6));
		g.setColor(Color.RED);
		//g.drawRect(x, y, GameBoard.BOARD_WIDTH, GameBoard.BOARD_HEIGHT);
		g.setStroke(oldStroke);
		g.setColor(WALL_FIELD_COLOR);
		g.fillRoundRect(x + WALL_THICKNESS + CELL_THICKNESS, y,
				GameBoard.BOARD_WIDTH - (2 * WALL_THICKNESS) - (2 * CELL_THICKNESS), GameBoard.BOARD_HEIGHT, ARC_WIDTH, ARC_HEIGHT);
		g.fillRoundRect(x, y + WALL_THICKNESS + CELL_THICKNESS, GameBoard.BOARD_WIDTH,
				GameBoard.BOARD_HEIGHT - (2 * WALL_THICKNESS) - (2 * CELL_THICKNESS), ARC_WIDTH, ARC_HEIGHT);
		g.setColor(CELL_FIELD_COLOR);
		for (int i = 0; i < 2  + boardSize; i++) {
			
			g.fillRoundRect(x + 2 * WALL_THICKNESS + CELL_THICKNESS + i * (WALL_THICKNESS + CELL_THICKNESS), y + WALL_THICKNESS, CELL_THICKNESS, CELL_THICKNESS,
					ARC_WIDTH, ARC_HEIGHT);
			g.fillRoundRect(x + 2 * WALL_THICKNESS + CELL_THICKNESS + i * (WALL_THICKNESS + CELL_THICKNESS), y + GameBoard.BOARD_HEIGHT - WALL_THICKNESS - CELL_THICKNESS, CELL_THICKNESS, CELL_THICKNESS,
					ARC_WIDTH, ARC_HEIGHT);
		}
		for (int i = 0; i < 4  + boardSize; i++) {
			g.fillRoundRect(x + WALL_THICKNESS + i * (WALL_THICKNESS + CELL_THICKNESS), y + 2 * WALL_THICKNESS + CELL_THICKNESS, CELL_THICKNESS, CELL_THICKNESS,
					ARC_WIDTH, ARC_HEIGHT);
			g.fillRoundRect(x + WALL_THICKNESS + i * (WALL_THICKNESS + CELL_THICKNESS), y + 3 * WALL_THICKNESS + 2 * CELL_THICKNESS, CELL_THICKNESS, CELL_THICKNESS,
					ARC_WIDTH, ARC_HEIGHT);
		}
		
		g.setColor(Color.GREEN);

		renderHelper(g,x,y);

		g.setFont(new Font("TimesRoman", Font.PLAIN,22));
		g.setColor(Color.MAGENTA);
		g.drawString("Press ESC to open the pause menu!", 15, 40);
		g.drawString("Press Enter to save your level!", 600, 40);
		int imageSize = CELL_THICKNESS-50;
		g.drawImage(Sprites.Wall1, 1800, 50, imageSize, imageSize, null);
		g.drawImage(Sprites.Wall2, 1802, 200, imageSize, imageSize, null);
		g.drawImage(Sprites.Wall3, 1800, 350, imageSize, 2*imageSize, null);
		g.drawImage(Sprites.Wall4, 1802, 600, imageSize, imageSize, null);
		g.drawImage(Sprites.Wall5, 1800, 750, imageSize, imageSize, null);
		g.drawImage(Sprites.Wall6, 1802, 900, imageSize, imageSize, null);

		g.setColor(Color.MAGENTA);

		if(unitControl == 0 && inPlay != null && inPlay.getShape() == 1 )
			g.setColor(Color.CYAN);
		g.drawOval(1798-imageSize/2,50+imageSize/4,imageSize/2,imageSize/2);
		g.drawString("1", 1750+imageSize/4-3, 50+imageSize/2+7);
		g.setColor(Color.MAGENTA);

		if(unitControl == 0 && inPlay != null && inPlay.getShape() == 2 )
			g.setColor(Color.CYAN);
		g.drawOval(1798-imageSize/2,200+imageSize/4,imageSize/2,imageSize/2);
		g.drawString("2", 1750+imageSize/4-3, 200+imageSize/2+7);
		g.setColor(Color.MAGENTA);


		if(unitControl == 0 && inPlay != null && inPlay.getShape() == 3 )
			g.setColor(Color.CYAN);
		g.drawOval(1798-imageSize/2,350+3*imageSize/4,imageSize/2,imageSize/2);
		g.drawString("3", 1750+imageSize/4-3, 350+imageSize+7);
		g.setColor(Color.MAGENTA);

		if(unitControl == 0 && inPlay != null && inPlay.getShape() == 4 )
			g.setColor(Color.CYAN);
		g.drawOval(1798-imageSize/2,600+imageSize/4,imageSize/2,imageSize/2);
		g.drawString("4", 1750+imageSize/4-3, 600+imageSize/2+7);
		g.setColor(Color.MAGENTA);

		if(unitControl == 0 && inPlay != null && inPlay.getShape() == 5 )
			g.setColor(Color.CYAN);
		g.drawOval(1798-imageSize/2,750+imageSize/4,imageSize/2,imageSize/2);
		g.drawString("5", 1750+imageSize/4-3, 750+imageSize/2+7);
		g.setColor(Color.MAGENTA);

		if(unitControl == 0 && inPlay != null && inPlay.getShape() == 6 )
			g.setColor(Color.CYAN);
		g.drawOval(1798-imageSize/2,900+imageSize/4,imageSize/2,imageSize/2);
		g.drawString("6", 1750+imageSize/4-3, 900+imageSize/2+7);
		g.setColor(Color.MAGENTA);


		g.drawImage(Sprites.BluKnightSprite, 100, 940, imageSize, imageSize, null);
		g.drawImage(Sprites.RedKnightSprite, 250, 940, imageSize, imageSize, null);
		g.drawImage(Sprites.TowerSprite,     400, 940, imageSize, imageSize, null);

		g.setColor(Color.BLUE);
		if(unitControl == 1  )
			g.setColor(Color.CYAN);
		g.drawOval(100+imageSize/4,940+imageSize,imageSize/2,imageSize/2);
		g.drawString("B", 100+imageSize/2-7, 940+imageSize+30);
		g.setColor(Color.RED);
		if(unitControl == 2  )
			g.setColor(Color.CYAN);
		g.drawOval(250+imageSize/4,940+imageSize,imageSize/2,imageSize/2);
		g.drawString("R", 250+imageSize/2-7, 940+imageSize+30);

		g.setColor(Color.GREEN);
		if(unitControl == 3  )
			g.setColor(Color.CYAN);
		g.drawOval(400+imageSize/4,940+imageSize,imageSize/2,imageSize/2);
		g.drawString("T", 400+imageSize/2-7, 940+imageSize+30);


		g.setStroke(oldStroke);

	}
	//Helps the render function
	protected static void renderHelper(Graphics2D g,int x,int y){
		for(int i = 0; i < GameBoard.map.length; i++) {
			for(int j = 0; j < GameBoard.map[i].length; j++) {
				int divisionX = i / 2;
				int divisionY = j / 2;
				int xx = x + (i - divisionX) * WALL_THICKNESS + divisionX * CELL_THICKNESS;
				int yy = y + (j - divisionY) * WALL_THICKNESS + divisionY * CELL_THICKNESS;
				if(GameBoard.map[i][j] == 'R' || GameBoard.map[i][j] == 'r') {


					if(THEME.equals( "hello there"))
						g.drawImage(Sprites.Kenobi, xx, yy ,CELL_THICKNESS, CELL_THICKNESS, null);
					else if(THEME.equals( "lord of the rings"))
						g.drawImage(Sprites.Gollum, xx, yy ,CELL_THICKNESS, CELL_THICKNESS, null);
					else if(THEME.equals("three little pigs"))
						g.drawImage(Sprites.Wolf, xx, yy ,CELL_THICKNESS, CELL_THICKNESS, null);
					else if(THEME.equals( "scooby doo"))
						g.drawImage(Sprites.Monster, xx, yy ,CELL_THICKNESS, CELL_THICKNESS, null);
					else
						g.drawImage(Sprites.RedKnightSprite, xx, yy ,CELL_THICKNESS, CELL_THICKNESS, null);
				} else if(GameBoard.map[i][j] == 'B' || GameBoard.map[i][j] == 'b') {

					if(THEME.equals( "hello there"))
						g.drawImage(Sprites.Grievous, xx, yy ,CELL_THICKNESS, CELL_THICKNESS, null);
					else if(THEME.equals( "lord of the rings"))
						g.drawImage(Sprites.Frodo, xx, yy ,CELL_THICKNESS, CELL_THICKNESS, null);
					else if(THEME.equals( "three little pigs"))
						g.drawImage(Sprites.Pig, xx, yy ,CELL_THICKNESS, CELL_THICKNESS, null);
					else if(THEME.equals("scooby doo"))
						g.drawImage(Sprites.Scooby, xx, yy ,CELL_THICKNESS, CELL_THICKNESS, null);
					else
						g.drawImage(Sprites.BluKnightSprite, xx, yy ,CELL_THICKNESS, CELL_THICKNESS, null);

				}else if(GameBoard.map[i][j] == 'T' || GameBoard.map[i][j] == 't') {
					g.drawImage(Sprites.TowerSprite, xx, yy ,CELL_THICKNESS, CELL_THICKNESS, null);

				}
				else if(GameBoard.map[i][j] == 'S' || GameBoard.map[i][j] == 's') {
					if(i%2 ==0)
						g.drawImage(Sprites.Lava, xx, yy ,WALL_THICKNESS, CELL_THICKNESS, null);
					else
						g.drawImage(Sprites.Lava, xx, yy ,CELL_THICKNESS, WALL_THICKNESS, null);

				}
			}
		}
	}




	//Checks victory and saves the level to the database if applicable
	private boolean savePossible()
	{
		boolean victory = true;
		boolean[] redOut = new boolean[redKnights.size()];// checks if reds reach outside
		boolean[] blueOut = new boolean[blueKnights.size()];// checks if blues reach outside
		boolean[] blueRed = new boolean[blueKnights.size()];// checks if blues reach reds
		boolean[] towOut = new boolean[towers.size()];// checks if towers reach outside
		boolean[] towRed = new boolean[towers.size()];//Checks if towers reach reds
		setPaths();

		//Reds
		for(int i =0; i<redKnights.size();i++){

			findPath(new Point(redKnights.get(i).x, redKnights.get(i).y));
			for(int j = 0; j < GameBoard.map.length;j++ ){
				if(paths[j][0] == 1)
					redOut[i] = true;
				else if(paths[j][paths[0].length-1] == 1)
					redOut[i] = true;
			}
			for(int j = 0; j < GameBoard.map[0].length;j++ ){
				if(paths[0][j] == 1)
					redOut[i] = true;
				else if(paths[paths.length-1][j] == 1)
					redOut[i] = true;
			}
			setPaths();
		}
		//Blues
		for(int i =0; i<blueKnights.size();i++){
			blueOut[i] = true;
		}

		for(int i =0; i<blueKnights.size();i++){
			findPath(new Point(blueKnights.get(i).x, blueKnights.get(i).y));
			for(int j = 0; j < GameBoard.map.length;j++ ){
				if(paths[j][0] == 1)
					blueOut[i] = false;
				else if(paths[j][paths[0].length-1] == 1)
					blueOut[i] = false;
			}
			for(int j = 0; j < GameBoard.map[0].length;j++ ){
				if(paths[0][j] == 1)
					blueOut[i] = false;
				else if(paths[paths.length-1][j] == 1)
					blueOut[i] = false;
			}
			boolean red1 = true;
			for(int j =0; j<redKnights.size();j++){
				if(paths[redKnights.get(j).x][redKnights.get(j).y] == 1 && red1)
					red1 = false;
			}
			blueRed[i] = red1;
			setPaths();
			
		}
		//Towers
		for(int i =0; i<towers.size();i++){
			towOut[i] = true;
		}

		for(int i =0; i<towers.size();i++){
			findPath(new Point(towers.get(i).x, towers.get(i).y));
			for(int j = 0; j < GameBoard.map.length;j++ ){
				if(paths[j][0] == 1)
					towOut[i] = false;
				else if(paths[j][paths[0].length-1] == 1)
					towOut[i] = false;
			}
			for(int j = 0; j < GameBoard.map[0].length;j++ ){
				if(paths[0][j] == 1)
					towOut[i] = false;
				else if(paths[paths.length-1][j] == 1)
					towOut[i] = false;
			}
			boolean red2 = true;
			for(int j =0; j<redKnights.size();j++){
				red2 = paths[redKnights.get(j).x][redKnights.get(j).y] != 1 && red2;
			}
			towRed[i] = red2;
			setPaths();
		}
		for(boolean i:towOut){

			if(!i)
				victory = false;

		}
		for(boolean i:towRed){
			if(!i)
				victory = false;

		}
		for(boolean i:redOut){

			if(!i)
				victory = false;

		}
		for(boolean i:blueOut){

			if(!i)
				victory = false;

		}
		for(boolean i:blueRed){

			if(!i)
				victory = false;

		}
		return victory;
	}


	@Override
	public Rectangle getBounds() {
		return null;
	}

	//pauses the game
	public void pause(){
		m.setVisible(true);
		paused = true;
		//bgm.stop();
		//victory(10);

	}
	//starts the background music
	public void startBGM(){
		bgm.play();
	}

	//stops the background music
	public void stopBGM() {
		bgm.stop();
	}

	// Marks the path array according the the start point reachable places are 1 after this
	private void findPath(Point start){

		if(start.x <0 || start.x == paths.length || start.y <0 || start.y == paths[0].length )
			return;
		paths[start.x][start.y] = 1;
		//count++;
		//System.out.println(count);


		//UP
		if( start.y+1 != paths[0].length && paths[start.x][start.y+1] == 0 )
			findPath(new Point(start.x,start.y+1));

		//DOWN
		if(start.y-1 != -1 &&paths[start.x][start.y-1] == 0 )
			findPath(new Point(start.x,start.y-1));

		//LEFT
		if( start.x-1 != -1 && paths[start.x-1][start.y] == 0 )
			findPath(new Point(start.x-1,start.y));

		//RIGHT
		if(start.x+1 != paths.length && paths[start.x+1][start.y] == 0 )
			findPath(new Point(start.x+1,start.y));
	}
	//Sets background music
	private void setBGM(){
		if(THEME.equals( "hello there"))
			bgm = Sprites.starwarsMusic;
		else if(THEME .equals( "lord of the rings"))
			bgm = Sprites.LotrMusic;
		else if(THEME .equals( "three little pigs"))
			bgm = Sprites.PigMusic;
		else if(THEME .equals( "scooby doo"))
			bgm = Sprites.ScoobyMusic;
		else
			bgm = Sprites.ClassicMusic;
		placeKnightSound = Sprites.placeKnightSound;
		placeKnightSound.setVolume(-10.f);
		removeSound = Sprites.removeSound;
		removeSound.setVolume(-10.f);
		int temp = Screen.db.getMusicSetting(Screen.selectedPlayer);

		bgm.setVolume((double)(-3)*(100-temp)/5);

	}
}
