
package gameplay;


import java.awt.*;
import java.util.ArrayList;

import Menus.PauseMenu;
import Menus.VictoryScreen;
import engine.*;
import engine.Timer;
import gui.Screen;
import util.Sprites;


import javax.swing.*;

import static gameplay.Wall.*;



public class GameBoard extends GameObject {

	public static final int WALL_THICKNESS = 35;
	public static final int CELL_THICKNESS = 4 * WALL_THICKNESS;
	private int boardSize;
	public static boolean paused;

	protected static int BOARD_WIDTH;
	protected static int BOARD_HEIGHT;
	public static String THEME;

	protected static final int ARC_WIDTH = 32;
	protected static final int ARC_HEIGHT = 32;
	//static int count = 0;

	protected static final Color WALL_FIELD_COLOR = new Color(86, 86, 86);
	protected static final Color CELL_FIELD_COLOR = new Color(155, 155, 155);

	public static char[][] map;
	protected static ArrayList<Point> sur;

	protected ArrayList<Point> redKnights = new ArrayList<>();
	protected ArrayList<Point> blueKnights= new ArrayList<>();
	protected ArrayList<Point> towers= new ArrayList<>();
	private boolean oldMB1;
	private boolean oldestMB1;
	private int[][] paths;
	private Timer timer;
	PauseMenu m;
	VictoryScreen v;
	private boolean removeMode;
	private AudioPlayer placeKnightSound;
	private AudioPlayer removeSound;
	private AudioPlayer bgm;
	private AudioPlayer victorySound;


	//Basic Constructor
	public GameBoard(int x, int y, char[][] newMap,int size,String theme) {
		super(x, y);
		boardSize = size;
		Screen.getInstance().MenuPlayer.setVolume(-80F);
		THEME = theme;
		timer = new Timer();
		timer.start();
		paused = false;
        m = new PauseMenu();
		v = new VictoryScreen(0);
		v.setVisible(false);
		m.setVisible(false);
		removeMode = false;
		setBGM();
		bgm.play();
		removeSound = new AudioPlayer("/sound_effects/maledeath2.wav");
		//bgm.setVolume(-15F);
		bgm.loop(100);
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
		map = newMap;
		oldMB1 = false;
		oldestMB1 = false;
		for (int i = 0; i < map.length; i++) {
			for (int j = 0; j < map[i].length; j++) {
				if(map[i][j] == 'R' || map[i][j] == 'r' )
					redKnights.add(new Point(i,j));
				else if(map[i][j] == 'B')
					blueKnights.add(new Point(i,j));
				else if(map[i][j] == 'T' || map[i][j] == 'S')
					towers.add(new Point(i,j));
			}
		}
		sur = getSur();
		paths = new int[map.length][map[1].length];
		setPaths();
		Wall.generateWalls();
	}
	//Constructor for custom sandbox games
	public GameBoard(int x, int y, char[][] newMap,int size, int[] numOfWalls,String theme) {
		super(x, y);
		boardSize = size;
		Screen.getInstance().MenuPlayer.setVolume(-80F);
		THEME = theme;
		timer = new Timer();
		timer.start();
		paused = false;
		m = new PauseMenu();
		v = new VictoryScreen(0);
		v.setVisible(false);
		m.setVisible(false);
		removeMode = false;
		removeSound = new AudioPlayer("/sound_effects/maledeath2.wav");
		setBGM();
		bgm.play();
		//bgm.setVolume(-15F);
		bgm.loop(100);
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
		map = newMap;
		oldMB1 = false;
		oldestMB1 = false;
		for (int i = 0; i < map.length; i++) {
			for (int j = 0; j < map[i].length; j++) {
				if(map[i][j] == 'R' || map[i][j] == 'r' )
					redKnights.add(new Point(i,j));
				else if(map[i][j] == 'B')
					blueKnights.add(new Point(i,j));
				else if(map[i][j] == 'T' || map[i][j] == 'S')
					towers.add(new Point(i,j));
			}
		}
		sur = getSur();
		paths = new int[map.length][map[1].length];
		setPaths();
		for(int i = 0; i < 6; i++)
		{
			for(int j = 0; j < numOfWalls[i];j++ ) {
				if(i ==0) //first wall
				{
					createWall1(1300, 130);
				}
				if(i ==1) //second wall
				{
					createWall2(1530, 130);
				}
				if(i ==2) //third wall
				{
					createWall3(1300, 500);
				}
				if(i ==3) //fourth wall
				{
					createWall4(1530, 600);
				}
				if(i ==4) //fifth wall
				{
					createWall5(970, 700);
				}
				if(i ==5) //sixth wall
				{
					createWall6(70, 700);
				}
			}
		}
	}
	//Basic constructor
	public GameBoard(int x, int y, int width, int height, char[][] newMap,int size) {
		super(x, y, width, height);
		boardSize = size;
		Screen.getInstance().MenuPlayer.setVolume(-80F);
		timer = new Timer();
		timer.start();
		paused = false;
		m = new PauseMenu();
		v = new VictoryScreen(0);
		v.setVisible(false);
		m.setVisible(false);
		removeMode = false;
		removeSound = new AudioPlayer("/sound_effects/maledeath2.wav");
		setBGM();
		bgm.play();
		//bgm.setVolume(-15F);
		bgm.loop(100);
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
		map = newMap;
		oldMB1 = false;
		oldestMB1 = false;
		for (int i = 0; i < map.length; i++) {
			for (int j = 0; j < map[i].length; j++) {
				if(map[i][j] == 'R' || map[i][j] == 'r' )
					redKnights.add(new Point(i,j));
				else if(map[i][j] == 'B')
					blueKnights.add(new Point(i,j));
				else if(map[i][j] == 'T' || map[i][j] == 'S')
					towers.add(new Point(i,j));
			}
		}
		sur = getSur();
		paths = new int[map.length][map[1].length];
		setPaths();
		Wall.generateWalls();
	}
	//Helper method that marks the paths array with obstacles = -1 or available paths =0
	private  void setPaths(){
		for (int i = 0; i < paths.length; i++) {
			for (int j = 0; j < paths[0].length; j++) {
				//System.out.println("İ : " + i + ", J: " + j);
				if(map[i][j] == 'W' || map[i][j] == 'w') {
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
		//Marks the map with S(Moats) in case they are overwritten
		for(Point sur: sur)
			if(sur != null && map[(int)sur.getX()][(int)sur.getY()] != 'S')
				map[(int)sur.getX()][(int)sur.getY()] = 'S';



		if(Keyboard.ESCAPE){

			pause();

		}
		bgm.play();

		if(paused){
			return;
		}
		if(Keyboard.Q && !removeMode){
			removeMode = true;
		}
		else if(Keyboard.E && removeMode){
			removeMode = false;
		}

		//This performs 2 ticks after the Mouse is released

		if (Mouse.MB1 == false && oldMB1 == false && oldestMB1 == true){
			//Removing a Knight logic
			if(removeMode && Screen.db.getDiamonds(Screen.selectedPlayer) >0){
				if(removeKnight()) {
                    removeMode = false;
                    Screen.db.setDiamonds(Screen.selectedPlayer, Screen.db.getDiamonds(Screen.selectedPlayer) - 1);
					removeSound.play();
					JOptionPane p = new JOptionPane();
					JOptionPane.showMessageDialog(m.w, "Succesfully Removed a knight","Remove Knight",
							JOptionPane.PLAIN_MESSAGE);
					m.w.setVisible(false);
					GameBoard.paused = false;
				}

			}
			//Victory!!!
			boolean victory = true;
			boolean[] redOut = new boolean[redKnights.size()]; // checks if reds reach outside
			boolean[] blueOut = new boolean[blueKnights.size()]; // checks if blues reach outside
			boolean[] blueRed = new boolean[blueKnights.size()]; // checks if blues reach reds
			boolean[] towOut = new boolean[towers.size()]; // checks if towers reach outside
			boolean[] towRed = new boolean[towers.size()]; //Checks if towers reach reds
			setPaths();

			//Reds
			for(int i =0; i<redKnights.size();i++){

				findPath(new Point(redKnights.get(i).x, redKnights.get(i).y));
				for(int j = 0; j < map.length;j++ ){
					if(paths[j][0] == 1)
						redOut[i] = true;
					else if(paths[j][paths[0].length-1] == 1)
						redOut[i] = true;
				}
				for(int j = 0; j < map[0].length;j++ ){
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
				for(int j = 0; j < map.length;j++ ){
					if(paths[j][0] == 1)
						blueOut[i] = false;
					else if(paths[j][paths[0].length-1] == 1)
						blueOut[i] = false;
				}
				for(int j = 0; j < map[0].length;j++ ){
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
				for(int j = 0; j < map.length;j++ ){
					if(paths[j][0] == 1)
						towOut[i] = false;
					else if(paths[j][paths[0].length-1] == 1)
						towOut[i] = false;
				}
				for(int j = 0; j < map[0].length;j++ ){
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
			//All booleans must be true in order to reach victory
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
			if(victory){
				int coins =500-(int)(timer.getElapsedTime());
				if(coins < 50)
					coins = 50;
				paused = true;
				victory(coins);

			}



		}

		oldestMB1 = oldMB1;
		oldMB1 = Mouse.MB1;
	}

	//Paints the board according to the theme
	@SuppressWarnings("Duplicates")
	@Override
	public void render(Graphics2D g) {



		if(THEME.equals( "hello there"))
			g.drawImage(Sprites.starwars, 0, 0 ,1920, 1080, null);
		else if(THEME .equals( "lord of the rings"))
			g.drawImage(Sprites.LotrBack, 0, 0 ,1920, 1080, null);
		else if(THEME .equals( "three little pigs"))
			g.drawImage(Sprites.PigBack, 0, 0 ,1920, 1080, null);
		else if(THEME .equals( "scooby doo"))
			g.drawImage(Sprites.ScoobyBack, 0, 0 ,1920, 1080, null);
		else
			g.drawImage(Sprites.ClassicBack, 0, 0 ,1920, 1080, null);


		if(paused)
			return;

        g.setFont(new Font("TimesRoman", Font.PLAIN,22));
		g.setColor(Color.MAGENTA);
        g.drawString("Press ESC to open the pause menu!", 45, 40);
        g.setColor(Color.BLACK);
        g.fillRect(500,10,100,50);
        g.drawImage(Sprites.Diamond,500,17,40,40,null);
        g.setColor(Color.WHITE);
        g.drawString("x  " + Screen.db.getDiamonds(Screen.selectedPlayer),550,40);



		Stroke oldStroke = g.getStroke();
		g.setStroke(new BasicStroke(6));
		g.setColor(Color.RED);

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
		LevelCreator.renderHelper(g,x,y);
		

		g.setStroke(oldStroke);

	}



	// Sur array is composed of points that represent S in the map
	protected static ArrayList<Point> getSur(){
		ArrayList<Point> result = new ArrayList<>();
		for(int i = 0; i < map.length; i++) {
			for (int j = 0; j < map[i].length; j++) {
				if (map[i][j] == 'S'){
					 result.add(new Point(i,j));
				}
			}
		}
		return result;

	}



	@Override
	public Rectangle getBounds() {
		return null;
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

	public void pause(){
	    //m = new PauseMenu();
		m.setVisible(true);
		paused = true;
        //bgm.stop();
		//victory(10);

	}
	//Calls victory operations
	public void victory(int coins){
	    v = new VictoryScreen(coins);
		v.setVisible(true);
		bgm.stop();
		victorySound.play();
        Screen.db.updateUnlockedLevel(Screen.selectedPlayer);
        int currentCoins = Screen.db.getCoins(Screen.selectedPlayer);
        Screen.db.setCoins(Screen.selectedPlayer,(currentCoins+coins));
	}
	//removes a knight from the board
	public boolean removeKnight(){
		int x = (int) Mouse.MOUSE_X-this.x;
		int y = (int) Mouse.MOUSE_Y-this.y;

		if(x < (this.x+GameBoard.BOARD_WIDTH) && y < (this.y+GameBoard.BOARD_HEIGHT) && x> this.x && y > this.y ) {
			int tempX = (int) x / (WALL_THICKNESS + CELL_THICKNESS);
			if (x - (tempX * (WALL_THICKNESS + CELL_THICKNESS)) - WALL_THICKNESS > 0)
				tempX = (tempX * 2) + 1;
			else
				tempX = tempX * 2;

			int tempY = (int) y / (WALL_THICKNESS + CELL_THICKNESS);
			if (y - (tempY * (WALL_THICKNESS + CELL_THICKNESS)) - WALL_THICKNESS > 0)
				tempY = (tempY * 2) + 1;
			else
				tempY = tempY * 2;

				if (GameBoard.map[tempX][tempY] == 'R') {
					GameBoard.map[tempX][tempY] = 'E';
					redKnights.remove(new Point(tempX,tempY));
					return true;

				}
				else if (GameBoard.map[tempX][tempY] == 'B') {
					GameBoard.map[tempX][tempY] = 'E';
					blueKnights.remove(new Point(tempX,tempY));
                    return true;
				}

		}
        return false;

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
		victorySound = Sprites.victorySound;
		int temp = Screen.db.getMusicSetting(Screen.selectedPlayer);


		bgm.setVolume((double)(-3)*(100-temp)/5);

	}
	//Starts the background music
	public void startBGM(){
	    bgm.play();
    }
    //Stops the background music
    public void stopBGM() {
        bgm.stop();
    }

	/*static protected final char[][] map = {{ 'I', 'I', 'E', 'E', 'E', 'E', 'E', 'I', 'I' },
			{ 'I', 'I', 'E', 'E', 'E', 'E', 'E', 'I', 'I' },
			{ 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E' },
			{ 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E' },
			{ 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E' },
			{ 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E' },
			{ 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E' },
			{ 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E' },
			{ 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E' },
			{ 'I', 'I', 'E', 'E', 'E', 'E', 'E', 'I', 'I' },
			{ 'I', 'I', 'E', 'E', 'E', 'E', 'E', 'I', 'I' }};
*/

	
	 

}
