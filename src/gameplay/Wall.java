package gameplay;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Stroke;
import java.util.ArrayList;

import engine.AudioPlayer;
import engine.Mouse;
import gui.Window;

import static engine.Keyboard.K;
import static engine.Keyboard.L;
import static gameplay.GameBoard.*;
import static main.Main.BoardXCoordinate;
import static main.Main.BoardYCoordinate;

public class Wall extends WallsAndWarriorsObject {

	/*public static Wall Wall1;
	public static Wall Wall2;
	public static Wall Wall3;
	public static Wall Wall4;
	public static Wall Wall5;
	public static Wall Wall6;*/
	protected static boolean blinking = false;
	protected boolean ClickedOnWall;



	private static Wall MovingWall = null;
	protected static boolean WallMoving = false;
	protected boolean placed;
	protected boolean onTheBoard;
	protected long placedTime;
	protected ArrayList<Point> gamePoints;
	private AudioPlayer placeWallSound;
	private AudioPlayer removeWallSound;
	private int shape;
	// Orientation 0 = default 1= 90 left rotate 2 = 180 3= 270
	private int orientation;
	private ArrayList<Point> points = new ArrayList<Point>();
	private ArrayList<WallOrNot> towersAndWalls = new ArrayList<WallOrNot>();
	private boolean cd = true;
	private Point lastMouse;
	private boolean oldMB1;
	protected Point originalPoint;
	private boolean PositionError;
	private long errorTimer;

	protected Wall(int shape, int x, int y) {	
		super(x, y);
		oldMB1 = false;
		ClickedOnWall = false;
		onTheBoard = false;
		placedTime = -1;
		this.shape = shape;
		errorTimer = -1;
		PositionError = false;
		orientation =0;
		placeWallSound = new AudioPlayer("/sound_effects/wall_sound.wav");
		placeWallSound.volumeUp();
		removeWallSound = new AudioPlayer("/sound_effects/wall_sound.wav");
		removeWallSound.volumeUp();
		Point startPoint = new Point(x, y);
		if (shape == 1) {
			points.add(startPoint);
			towersAndWalls.add(WallOrNot.WALL);
			Point point2 = new Point(x, y + CELL_THICKNESS + WALL_THICKNESS);
			points.add(point2);
			towersAndWalls.add(WallOrNot.WALL);
			Point point3 = new Point(x + CELL_THICKNESS + WALL_THICKNESS,
					y + CELL_THICKNESS + WALL_THICKNESS);
			points.add(point3);
			towersAndWalls.add(WallOrNot.TOWER);
			Point point4 = new Point(x + CELL_THICKNESS + WALL_THICKNESS, y);
			points.add(point4);
			towersAndWalls.add(WallOrNot.WALL);
		} else if (shape == 2) {
			points.add(startPoint);
			towersAndWalls.add(WallOrNot.WALL);
			Point point2 = new Point(x, y + CELL_THICKNESS + WALL_THICKNESS);
			points.add(point2);
			towersAndWalls.add(WallOrNot.TOWER);
			Point point3 = new Point(x + CELL_THICKNESS + WALL_THICKNESS,
					y + CELL_THICKNESS + WALL_THICKNESS);
			points.add(point3);
			towersAndWalls.add(WallOrNot.WALL);
			Point point4 = new Point(x + CELL_THICKNESS + WALL_THICKNESS,
					y + 2 * CELL_THICKNESS + 2 * WALL_THICKNESS);
			points.add(point4);
			towersAndWalls.add(WallOrNot.TOWER);
			Point point5 = new Point(x + 2 * CELL_THICKNESS + 2 * WALL_THICKNESS,
					y + 2 * CELL_THICKNESS + 2 * WALL_THICKNESS);
			points.add(point5);
			towersAndWalls.add(WallOrNot.WALL);
		} else if (shape == 3) {
			points.add(startPoint);
			towersAndWalls.add(WallOrNot.WALL);
			Point point2 = new Point(x, y + CELL_THICKNESS + WALL_THICKNESS);
			points.add(point2);
			towersAndWalls.add(WallOrNot.WALL);
			Point point3 = new Point(x + CELL_THICKNESS + WALL_THICKNESS,
					y + CELL_THICKNESS + WALL_THICKNESS);
			points.add(point3);
			towersAndWalls.add(WallOrNot.WALL);
			Point point4 = new Point(x + CELL_THICKNESS + WALL_THICKNESS,
					y + 2 * CELL_THICKNESS + 2 * WALL_THICKNESS);
			points.add(point4);
			towersAndWalls.add(WallOrNot.WALL);
			Point point5 = new Point(x, y + 2 * CELL_THICKNESS + 2 * WALL_THICKNESS);
			points.add(point5);
			towersAndWalls.add(WallOrNot.WALL);
			Point point6 = new Point(x, y + 3 * CELL_THICKNESS + 3 * WALL_THICKNESS);
			points.add(point6);
			towersAndWalls.add(WallOrNot.WALL);
			Point point7 = new Point(x + CELL_THICKNESS + WALL_THICKNESS,
					y + 3 * CELL_THICKNESS + 3 * WALL_THICKNESS);
			points.add(point7);
			towersAndWalls.add(WallOrNot.WALL);
		} else if (shape == 4) {
			points.add(startPoint);
			towersAndWalls.add(WallOrNot.WALL);
			Point point2 = new Point(x, y + CELL_THICKNESS + WALL_THICKNESS);
			points.add(point2);
			towersAndWalls.add(WallOrNot.WALL);
			Point point3 = new Point(x + CELL_THICKNESS + WALL_THICKNESS,
					y + CELL_THICKNESS + WALL_THICKNESS);
			points.add(point3);
			towersAndWalls.add(WallOrNot.WALL);
			Point point4 = new Point(x + CELL_THICKNESS + WALL_THICKNESS,
					y + 2 * CELL_THICKNESS + 2 * WALL_THICKNESS);
			points.add(point4);
			towersAndWalls.add(WallOrNot.TOWER);
			Point point5 = new Point(x + 2 * CELL_THICKNESS + 2 * WALL_THICKNESS,
					y + 2 * CELL_THICKNESS + 2 * WALL_THICKNESS);
			points.add(point5);
			towersAndWalls.add(WallOrNot.WALL);
			Point point6 = new Point(x + 2 * CELL_THICKNESS + 2 * WALL_THICKNESS,
					y + CELL_THICKNESS + WALL_THICKNESS);
			points.add(point6);
			towersAndWalls.add(WallOrNot.WALL);
		}
		else if (shape == 5) {
			points.add(startPoint);
			towersAndWalls.add(WallOrNot.WALL);
			Point point2 = new Point(x, y + CELL_THICKNESS + WALL_THICKNESS);
			points.add(point2);
			towersAndWalls.add(WallOrNot.WALL);
			Point point3 = new Point(x,
					y + 2 * CELL_THICKNESS + 2 * WALL_THICKNESS);
			points.add(point3);
			towersAndWalls.add(WallOrNot.TOWER);
			Point point4 = new Point(x + CELL_THICKNESS + WALL_THICKNESS, y + 2 * CELL_THICKNESS + 2 * WALL_THICKNESS );
			points.add(point4);
			towersAndWalls.add(WallOrNot.WALL);
		}
		else if (shape == 6) {
			points.add(startPoint);
			towersAndWalls.add(WallOrNot.WALL);
			Point point2 = new Point(x, y + CELL_THICKNESS + WALL_THICKNESS);
			points.add(point2);
			towersAndWalls.add(WallOrNot.TOWER);
			Point point3 = new Point(x + CELL_THICKNESS + WALL_THICKNESS, y + CELL_THICKNESS + WALL_THICKNESS);
			points.add(point3);
			towersAndWalls.add(WallOrNot.WALL);
			Point point4 = new Point(x + 2 * CELL_THICKNESS + 2 * WALL_THICKNESS, y + CELL_THICKNESS + WALL_THICKNESS);
			points.add(point4);
			towersAndWalls.add(WallOrNot.TOWER);
			Point point5 = new Point(x + 2 * CELL_THICKNESS + 2 * WALL_THICKNESS, y + 2 * CELL_THICKNESS + 2 * WALL_THICKNESS);
			points.add(point5);
			towersAndWalls.add(WallOrNot.WALL);
		}


		LevelCreator.allWalls.add(this);

	}

	// createWall methods create the wall at the position of their top left corner.
	protected static Wall createWall1(int x, int y) {
		Wall w = new Wall(1, x, y);
		w.setVisible(true);
		w.originalPoint = new Point(x,y);
		return w;
	}

	protected static Wall createWall2(int x, int y) {
		Wall w = new Wall(2, x, y);
		w.setVisible(true);
        w.originalPoint = new Point(x,y);
		return w;
	}

	protected static Wall createWall3(int x, int y) {
		Wall w = new Wall(3, x, y);
		w.setVisible(true);
        w.originalPoint = new Point(x,y);
		return w;
	}

	protected static Wall createWall4(int x, int y) {
		Wall w = new Wall(4, x, y);
		w.setVisible(true);
        w.originalPoint = new Point(x,y);
		return w;
	}
	
	protected static Wall createWall5(int x, int y) {
		Wall w = new Wall(5, x, y);
		w.setVisible(true);
        w.originalPoint = new Point(x,y);
		return w;
	}
	
	protected static Wall createWall6(int x, int y) {
		Wall w = new Wall(6, x, y);

		w.setVisible(true);
        w.originalPoint = w.getTopLeft();
		//w.rotateLeft90Deg(false);
		return w;
	}

	//Rotates the Wall
	public void rotateRight90Deg() {
		Point grabPoint = Wall.findWallMidPoint(this);
		for (int i = 0; i < points.size(); i++) {
			Point temp = points.get(i);
			temp = Wall.rotateRightHelper(temp, grabPoint);
			points.set(i, temp);
		}
		orientation--;
		if(orientation == -1)
			orientation =3;
		Point newPoint = getTopLeft();
		setPoint(newPoint);

	}
	//Rotates the Wall
	public void rotateLeft90Deg(boolean forced) {

		Point grabPoint = Wall.findWallMidPoint(this);
		//Point grabPoint = new Point((int)Mouse.MOUSE_X,(int)Mouse.MOUSE_Y);

		for (int i = 0; i < this.points.size(); i++) {
			Point temp = this.points.get(i);
			temp = Wall.rotateLeftHelper(temp, grabPoint);
			this.points.set(i, temp);
		}
		this.orientation++;
		if(this.orientation == 4)
			this.orientation =0;

		Point newPoint = this.getTopLeft();
		this.setPoint(newPoint);

		// If rotate is problematic reverse it
		for(int i = 0; i < this.getPoints().size();i++){
			if((points.get(i).x < 0 || points.get(i).x > Window.WIDTH || points.get(i).y < 0 ||  points.get(i).y > Window.HEIGHT) && !forced) {
				rotateRight90Deg();
				return;
			}
		}
		//If mouse is outside after the rotation move the Wall on the mouse cursor
		if (!getBounds().contains(Mouse.MOUSE_X, Mouse.MOUSE_Y)) {
			//Point newMid = Wall.findWallMidPoint(this);
			moveWall((int)Mouse.MOUSE_X-getBounds().width/2,(int)Mouse.MOUSE_Y-getBounds().height/2);
			//System.out.println((int)Mouse.MOUSE_X-newMid.x);

		}







	}
	@Override
	public void setVisible(boolean visible){
		super.setVisible(visible);
	}


	private static Point rotateLeftHelper(Point coord, Point pin) {
		int x1 = (int) pin.getX();
		int y1 = (int) pin.getY();

		int x2 = (int) coord.getX();
		int y2 = (int) coord.getY();

		int xTemp = x2 - x1;
		int yTemp = y2 - y1;

		int swap = xTemp;
		xTemp = yTemp;
		yTemp = swap;
		xTemp = (-1) * xTemp;

		int xResult = x1 + xTemp;
		int yResult = y1 + yTemp;
		return new Point(xResult, yResult);
	}
	private static Point rotateRightHelper(Point coord, Point pin) {
		int x1 = (int) pin.getX();
		int y1 = (int) pin.getY();

		int x2 = (int) coord.getX();
		int y2 = (int) coord.getY();

		int xTemp = x2 - x1;
		int yTemp = y2 - y1;

		int swap = xTemp;
		xTemp = yTemp;
		yTemp = swap;
		yTemp = (-1) * yTemp;

		int xResult = x1 + xTemp;
		int yResult = y1 + yTemp;
		Point resultant = new Point(xResult, yResult);
		return resultant;
	}
	//Finds the center point
	public static Point findWallMidPoint(Wall w) {
		int x = 0;
		int y = 0;
		for (int i = 0; i < w.points.size(); i++) {
			x = x + (int) (w.points.get(i).getX());
			y = y + (int) (w.points.get(i).getY());
		}
		int avgX = x / w.points.size();
		int avgY = y / w.points.size();
		Point midPoint = new Point(avgX, avgY);
		return midPoint;
	}

	public int getShape() {
		return shape;
	}
	public boolean getPlaced() {return placed;}
	public void setPlaced(boolean placed){ this.placed = placed;}

	//Ticks with certain intervals
	@Override
	public void tick() {

		if(GameBoard.paused)
			return;


		if (getBounds().contains(Mouse.MOUSE_X, Mouse.MOUSE_Y)) {



			//mouseReleased

		    if(Mouse.MB1 == false && oldMB1 == true) {
		    	ClickedOnWall = false;

                if(onTheBoard)

                    snap(gamePoints);

                if(this.onTheBoard == false) {

                	this.reset();
                }
				//If wall is not positioned properly snap it to place or send it back to its original place


                if((wallValid() == false && this.getPlaced() == false)) {
                    if (errorTimer == -1){
                        errorTimer = System.nanoTime();
                        PositionError = true;
                        blinking = true;

                    }



                }
                if(this.onTheBoard) {
					this.placed = true;
					if(this.wallValid()) {
						for (int i = 0; i < gamePoints.size(); i++) {
							map[gamePoints.get(i).x][gamePoints.get(i).y] = 'W';
							if (i != gamePoints.size() - 1) {
								map[((gamePoints.get(i).x - gamePoints.get(i + 1).x) / 2) + gamePoints.get(i + 1).x][((gamePoints.get(i).y - gamePoints.get(i + 1).y) / 2) + gamePoints.get(i + 1).y] = 'W';
								System.out.println("Duvar");
							}
						}
						placeWallSound.play();

					}
				}
			}
            //Mouse Clicked
            if(Mouse.MB1 && !oldMB1){
		    	ClickedOnWall = true;
			}




            //Mouse Pressed

		    if (Mouse.MB1 && ClickedOnWall) {

				//Drag the wall with the mouse
				if (MovingWall == null || this == MovingWall) {
					MovingWall = this;
				}
				if (this == MovingWall) {
					WallMoving = true;
					int dx = (int) (Mouse.MOUSE_X - lastMouse.x);
					int dy = (int) (Mouse.MOUSE_Y - lastMouse.y);
					Rectangle bounds = getBounds();
					if (x + dx > 0 && y + dy > 0 && x + bounds.getWidth() + dx < Window.WIDTH
							&& y + bounds.getHeight() + dy < Window.HEIGHT) {
						for (Point p : points) {
							p.translate(dx, dy);
						}
						x += dx;
						y += dy;
						gamePoints = this.getGamePoints();
						placed = false;
						if( !oldMB1) {


							for (int i = 0; i < gamePoints.size(); i++) {
								map[gamePoints.get(i).x][gamePoints.get(i).y] = 'E';
								if(i != gamePoints.size()-1){
										map[((gamePoints.get(i).x-gamePoints.get(i+1).x)/2)+gamePoints.get(i+1).x][((gamePoints.get(i).y-gamePoints.get(i+1).y)/2)+gamePoints.get(i+1).y] = 'E';
								}
							}
							removeWallSound.play();
						}
					}

				}
			}
			if(!Mouse.MB1) {
				MovingWall = null;
				WallMoving = false;

			}
			if (Mouse.MB3 && cd&& Mouse.MB1 ) {
				if (MovingWall == null || this == MovingWall) {
					MovingWall = this;
				}
				//Rotate the mouse and wait so that the wall doesn't constantly spin
				if(this.placed != true && this == MovingWall)
		            rotateLeft90Deg(false);
				cd = false;
				new Thread(() -> {
					try {
						Thread.sleep(300);
					} catch (InterruptedException ex) {
						Thread.currentThread().interrupt();
						ex.printStackTrace();
					}
					cd = true;
				}).start();
			}
		}
		//Drag the wall with the mouse
		else if(Mouse.MB1 && oldMB1 && MovingWall == this){
			int dx = (int) (Mouse.MOUSE_X - lastMouse.x);
			int dy = (int) (Mouse.MOUSE_Y - lastMouse.y);
			Rectangle bounds = getBounds();
			if (x + dx > 0 && y + dy > 0 && x + bounds.getWidth() + dx < Window.WIDTH
					&& y + bounds.getHeight() + dy < Window.HEIGHT) {
				for (Point p : points) {
					p.translate(dx, dy);
				}
				x += dx;
				y += dy;
				gamePoints = this.getGamePoints();
				placed = false;
				if( !oldMB1) {


					for (int i = 0; i < gamePoints.size(); i++) {
						map[gamePoints.get(i).x][gamePoints.get(i).y] = 'E';
						if(i != gamePoints.size()-1){
							map[((gamePoints.get(i).x-gamePoints.get(i+1).x)/2)+gamePoints.get(i+1).x][((gamePoints.get(i).y-gamePoints.get(i+1).y)/2)+gamePoints.get(i+1).y] = 'E';
						}
					}
					removeWallSound.play();;
				}
			}





		}
		//Red Blinking control

        if((System.nanoTime()-errorTimer)/(1000000000) > 0.3 && PositionError == true){
            PositionError = false;
            errorTimer = -1;
            blinking = false;
            reset();
        }
		lastMouse = new Point((int) Mouse.MOUSE_X, (int) Mouse.MOUSE_Y);
        oldMB1 = Mouse.MB1;
	}

	//Renders the walls
	@Override
	public void render(Graphics2D g) {
		if(paused)
			return;

		if(visible == true) {
			Stroke oldStroke = g.getStroke();
			if (PositionError == true && System.nanoTime() % 1000000000 < 100000000) {
				g.setColor(Color.RED);
				g.setStroke(new BasicStroke(15));
			} else {
				g.setColor(Color.WHITE);
				g.setStroke(new BasicStroke(10));
			}
			for (int i = 0; i < points.size() - 1; i++) {
				Point current = points.get(i);
				Point next = points.get(i + 1);
				g.drawLine(current.x, current.y, next.x, next.y);
				if (towersAndWalls.get(i) == WallOrNot.TOWER) {
					g.fillOval(current.x - 15, current.y - 15, 30, 30);
				}
			}

			if ( !Mouse.MB1 && (MovingWall == this || MovingWall ==null) && !this.placed  &&getBounds().contains(Mouse.MOUSE_X, Mouse.MOUSE_Y )) {
				g.setStroke(new BasicStroke(3));
				g.setColor(Color.CYAN);
				int temp = getBounds().height;
				int temp2 = getBounds().width;

				g.drawLine(x-10,y-10,x+temp2+20,y-10);
				g.drawLine(x-10,y-10,x-10,y+temp+20);
				g.drawLine(x+temp2+20,y-10,x+temp2+20,y+temp+20);
				g.drawLine(x-10,y+temp+20,x+temp2+20,y+temp+20);


			}
			g.setStroke(oldStroke);

		}
	}
	//get the rectangle that contains the wall
	@Override
	public Rectangle getBounds() {
		int lowestX = Integer.MAX_VALUE;
		int lowestY = Integer.MAX_VALUE;
		int highestX = Integer.MIN_VALUE;
		int highestY = Integer.MIN_VALUE;
		for (Point p : points) {
			if (p.x < lowestX) {
				lowestX = p.x;
			}
			if (p.x > highestX) {
				highestX = p.x;
			}
			if (p.y < lowestY) {
				lowestY = p.y;
			}
			if (p.y > highestY) {
				highestY = p.y;
			}
		}
		return new Rectangle(lowestX, lowestY, highestX - lowestX, highestY - lowestY);
	}
	//Walls topLeft point is its location
	public Point getTopLeft(){
		int lowestY = Integer.MAX_VALUE;
		int lowestX = Integer.MAX_VALUE;
		for (Point p : points) {
			if (p.x < lowestX) {
				lowestX = p.x;
			}
			if (p.y < lowestY) {
				lowestY = p.y;
			}
		}
		return new Point(lowestX,lowestY);
	}

	@Override
	public String toString() {
		String s = "";
		System.out.println("Points:");
		for (Point p : points) {
			s += "X: " + p.x + " Y: " + p.y + "\n";
		}
		return s;
	}

	protected enum WallOrNot {
		TOWER, WALL
	}

	public static void generateWalls() {
		createWall1(1300, 130);
		createWall2(1530, 130);
		createWall3(1300, 500);
		createWall4(1530, 600);
	}


	public ArrayList<Point> getPoints() {
		return points;
	}
	protected void setPoint(Point p){
	    this.x = p.x;
	    this.y = p.y;
    }
    protected void moveWall(int X , int Y){
        int dx = (int) ( X- this.x);
        int dy = (int) (Y - this.y);
        for (Point p : points) {
			p.translate(dx, dy);
		}
		x += dx;
		y += dy;
		gamePoints = getGamePoints();

    }

	//Checks if the current wall intersects with anything
    protected boolean wallValid(){

    	for(Wall myWall: LevelCreator.allWalls)
        if(myWall != null && !(this.equals(myWall))){
            if(WallIntersects(this,myWall))
                return false;
     

        }
		for(Point sur: sur) {
			if (gamePoints != null && sur != null) {
				for (int i = 0; i < gamePoints.size() - 1; i++) {
					if ((gamePoints.get(i).getX() == sur.getX() + 1 && gamePoints.get(i).getY() == sur.getY()) && (gamePoints.get(i + 1).getX() == sur.getX() - 1 && gamePoints.get(i + 1).getY() == sur.getY()))
						return false;
					else if ((gamePoints.get(i).getX() == sur.getX() - 1 && gamePoints.get(i).getY() == sur.getY()) && (gamePoints.get(i + 1).getX() == sur.getX() + 1 && gamePoints.get(i + 1).getY() == sur.getY()))
						return false;
					else if ((gamePoints.get(i).getY() == sur.getY() + 1 && gamePoints.get(i).getX() == sur.getX()) && (gamePoints.get(i + 1).getY() == sur.getY() - 1 && gamePoints.get(i + 1).getX() == sur.getX()))
						return false;
					else if ((gamePoints.get(i).getY() == sur.getY() - 1 && gamePoints.get(i).getX() == sur.getX()) && (gamePoints.get(i + 1).getY() == sur.getY() + 1 && gamePoints.get(i + 1).getX() == sur.getX()))
						return false;
				}
			}
		}
        return true;
    }

    //Check if the two walls intersect
    private boolean WallIntersects(Wall wall1, Wall wall2){
        if(wall1.getOnTheBoard() == false || wall2.getOnTheBoard() == false ){
            return false;
        }

        ArrayList<Point> points1 = wall1.getGamePoints();
        ArrayList<Point> points2 = wall2.getGamePoints();

        for(int i =0; i< points1.size(); i++) {
			for (int j = 0; j < points2.size() ; j++) {
				if (points1.get(i).equals(points2.get(j)) && (wall1.getTowersAndWalls().get(i) == WallOrNot.TOWER || wall2.getTowersAndWalls().get(j) == WallOrNot.TOWER)) {
					return true;
				}
			}
		}
		for(int i =0; i< points1.size()-1; i++){
			for(int j =0; j < points2.size()-1; j++){

                if ( (points1.get(i).equals(points2.get(j)) && (points1.get(i+1).equals(points2.get(j+1))) ) || (points1.get(i).equals(points2.get(j+1)) && (points1.get(i+1).equals(points2.get(j)))))
                    return true;

            }
        }

        return false;

    }




    public ArrayList<WallOrNot> getTowersAndWalls() {
        return towersAndWalls;
    }


    //converts the coordinates of wall points from display coordinates to gameBoard coordinates
    protected ArrayList<Point> getGamePoints(){
        ArrayList<Point> newPoints = new ArrayList<>();
        onTheBoard = true;
        for(int i = 0; i < points.size();i++){
            Point newPoint = new Point(points.get(i));
            newPoint.translate(-1*BoardXCoordinate,-1*BoardYCoordinate);
            if(newPoint.getX()> BOARD_WIDTH || newPoint.getY() > BOARD_HEIGHT  || newPoint.getY() < 0 || newPoint.getX() < 0){
				onTheBoard = false;
				return new ArrayList<>();
            }
            else{
				int tempX = (int)newPoint.getX() / (WALL_THICKNESS+CELL_THICKNESS);
				if(newPoint.getX()-(tempX*(WALL_THICKNESS+CELL_THICKNESS))-WALL_THICKNESS > 0)
                    tempX = (tempX*2)+1;
				else
				    tempX = tempX*2;

				int tempY = (int)newPoint.getY() / (WALL_THICKNESS+CELL_THICKNESS);
				if(newPoint.getY()-(tempY*(WALL_THICKNESS+CELL_THICKNESS))-WALL_THICKNESS > 0)
					tempY = (tempY*2)+1;
				else
				    tempY = tempY*2;
				newPoints.add(new Point(tempX,tempY));
			}
		//sets the gamePoints array to include the game coordinates of the wall
		//Also check if the wall is on the board or not
        }
        for(int i = 0; i < newPoints.size();i++){
            Point temp = newPoints.get(i);
            if(   (temp.getX() <2 && temp.getY() < 2)
               || (temp.getX() >map.length - 3 && temp.getY() > 6)
               || (temp.getX() < 2 && temp.getY() >6)
               || (temp.getX() >map.length - 3 && temp.getY() < 2) ){
                onTheBoard = false;
                return new ArrayList<>();
            }
        }
        return newPoints;
    }
    //Puts the wall back at its original creation point with original orientation
    public void reset(){
		while(orientation !=0) {
			rotateLeft90Deg(true);
		}

		this.moveWall(this.originalPoint.x,this.originalPoint.y);
		this.x = originalPoint.x;
		this.y = originalPoint.y;
		gamePoints = getGamePoints();
		this.placed = false;



	}

	public long getPlacedTime() {
		return placedTime;
	}

	public void setPlacedTime(long placedTime) {
		this.placedTime = placedTime;
	}
	public boolean getOnTheBoard(){
    	return onTheBoard;
	}
	public void setOnTheBoard(boolean onTheBoard){
		 this.onTheBoard = onTheBoard;
	}
	// Snap the Wall in Place

	//If walls is not positioned exactly right move it a bit to fit perfectly
	private void snap(ArrayList<Point> points) {

            for (int i = 0; i < points.size(); i++) {
                Point temp = points.get(i);
                if (temp.getX() % 2 != 0) {
                    for (int j = 0; j < points.size(); j++) {
                        points.get(j).translate(-1, 0);
                    }

                }
                if (temp.getY() % 2 != 0) {
                    for (int j = 0; j < points.size(); j++) {
                        points.get(j).translate(0, -1);
                    }

                }
            }
        for (int j = 0; j < points.size(); j++) {
            this.points.set(j, new Point((int) points.get(j).getX() / 2 * (CELL_THICKNESS + WALL_THICKNESS) + BoardXCoordinate + (WALL_THICKNESS / 2),
                    (int) points.get(j).getY() / 2 * (CELL_THICKNESS + WALL_THICKNESS) + BoardYCoordinate + (WALL_THICKNESS / 2)));
        }
        Point newPoint = getTopLeft();
		x = newPoint.x;
		y = newPoint.y;


    }
    //Disable this Wall until the garbage collector collects it
    public void disable(){
		this.visible = false;
		this.moveWall(-1000,-1000);
		gamePoints = this.getGamePoints();
		for (int i = 0; i < gamePoints.size(); i++) {
			map[gamePoints.get(i).x][gamePoints.get(i).y] = 'E';
			if(i != gamePoints.size()-1){
				map[((gamePoints.get(i).x-gamePoints.get(i+1).x)/2)+gamePoints.get(i+1).x][((gamePoints.get(i).y-gamePoints.get(i+1).y)/2)+gamePoints.get(i+1).y] = 'E';
			}
		}
		this.placed = false;
	}
	//Enables the wall back in to the game
	public void enable(){
    	this.visible = true;
    	this.reset();
		this.getGamePoints();
	}
}
