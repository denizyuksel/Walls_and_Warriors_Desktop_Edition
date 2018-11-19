package entity;



import java.util.ArrayList;
import javafx.geometry.Point2D;

public class Wall extends GamePiece {

	// properties
	public enum Direction {
		UP, RIGHT, DOWN, LEFT
	}

	private ArrayList<Direction> wallDirections = new ArrayList<>();

	// constructors

	public Wall(int shape, Point2D position) {
		if (shape == 1) {
			wallTypeOne();
		} else if (shape == 2) {
			wallTypeTwo();
		} else if (shape == 3) {
			wallTypeThree();
		} else if (shape == 4) {
			wallTypeFour();
		} else if (shape == 5) {
			wallTypeTower();
		}

		super.setPosition(position);
		super.setMovable(false);
		super.setVisible(true);
	}

	// methods
	public ArrayList<Direction> getWallShape() {
		return wallDirections;
	}

	public void setShape(ArrayList<Direction> shape) {
		wallDirections = shape;
	}
	
	public ArrayList<Point2D> rotateClockwise() {
		for (int index = 0; index < wallDirections.size(); index++) {
			if (wallDirections.get(index) == Direction.UP) {
				wallDirections.set(index, Direction.RIGHT);
			} else if (wallDirections.get(index) == Direction.RIGHT) {
				wallDirections.set(index, Direction.DOWN);
			} else if (wallDirections.get(index) == Direction.DOWN) {
				wallDirections.set(index, Direction.LEFT);
			} else if (wallDirections.get(index) == Direction.LEFT) {
				wallDirections.set(index, Direction.UP);
			}
		}
		return calculateWallCoordinates();
	}

	public ArrayList<Point2D> rotateCounterClockwise() {
		for (int index = 0; index < wallDirections.size(); index++) {
			if (wallDirections.get(index) == Direction.UP) {
				wallDirections.set(index, Direction.LEFT);
			} else if (wallDirections.get(index) == Direction.LEFT) {
				wallDirections.set(index, Direction.DOWN);
			} else if (wallDirections.get(index) == Direction.DOWN) {
				wallDirections.set(index, Direction.RIGHT);
			} else if (wallDirections.get(index) == Direction.RIGHT) {
				wallDirections.set(index, Direction.UP);
			}
		}
		return calculateWallCoordinates();
	}

	private void wallTypeOne() {
		wallDirections.add(Direction.UP);
		wallDirections.add(Direction.RIGHT);
		wallDirections.add(Direction.DOWN);
	}

	private void wallTypeTwo() {
		wallDirections.add(Direction.DOWN);
		wallDirections.add(Direction.RIGHT);
		wallDirections.add(Direction.DOWN);
		wallDirections.add(Direction.RIGHT);
	}

	private void wallTypeThree() {
		wallDirections.add(Direction.RIGHT);
		wallDirections.add(Direction.UP);
		wallDirections.add(Direction.DOWN);
		wallDirections.add(Direction.RIGHT);
		wallDirections.add(Direction.UP);
	}

	private void wallTypeFour() {
		wallDirections.add(Direction.RIGHT);
		wallDirections.add(Direction.UP);
		wallDirections.add(Direction.RIGHT);
		wallDirections.add(Direction.UP);
		wallDirections.add(Direction.LEFT);
	}

	public void wallTypeTower() {
		wallDirections.add(Direction.RIGHT);
	}

	public ArrayList<Point2D> calculateWallCoordinates() {
		ArrayList<Point2D> coords = new ArrayList<>();
		coords.add(super.getPosition());

		Point2D p = coords.get(0); // coordinate of the start point of a wall.

		for (int i = 0; i < wallDirections.size(); i++) {
			Direction d = wallDirections.get(i);
			if (d == Direction.UP) {
				coords.add(new Point2D(p.getX(), p.getY() - 1));
				p = new Point2D(p.getX(), p.getY() - 2);
			} else if (d == Direction.RIGHT) {
				coords.add(new Point2D(p.getX() + 1, p.getY()));
				p = new Point2D(p.getX() + 2, p.getY());
			} else if (d == Direction.DOWN) {
				coords.add(new Point2D(p.getX(), p.getY() + 1)); // adds the wall segment's mid coordinate.
				p = new Point2D(p.getX(), p.getY() + 2); // jumps to the next edge.
			} else if (d == Direction.LEFT) {
				coords.add(new Point2D(p.getX() - 1, p.getY()));
				p = new Point2D(p.getX() - 2, p.getY());
			}
		}
		return coords;
	}

	public ArrayList<Point2D> calculatePointWiseWallCoordinates() {
		ArrayList<Point2D> coords = new ArrayList<>();
		coords.add(super.getPosition());

		Point2D p = coords.get(0); // coordinate of the start point of a wall.

		for (int i = 0; i < wallDirections.size(); i++) {
			Direction d = wallDirections.get(i);
			if (d == Direction.UP) {
				coords.add(new Point2D(p.getX(), p.getY() - 1));
				coords.add(new Point2D(p.getX(), p.getY() - 2));
				p = new Point2D(p.getX(), p.getY() - 2);
			} else if (d == Direction.RIGHT) {
				coords.add(new Point2D(p.getX() + 1, p.getY()));
				coords.add(new Point2D(p.getX() + 2, p.getY()));
				p = new Point2D(p.getX() + 2, p.getY());
			} else if (d == Direction.DOWN) {
				coords.add(new Point2D(p.getX(), p.getY() + 1)); // adds the wall segment's mid coordinate.
				coords.add(new Point2D(p.getX(), p.getY() + 2));
				p = new Point2D(p.getX(), p.getY() + 2); // jumps to the next edge.
			} else if (d == Direction.LEFT) {
				coords.add(new Point2D(p.getX() - 1, p.getY()));
				coords.add(new Point2D(p.getX() - 2, p.getY()));
				p = new Point2D(p.getX() - 2, p.getY());
			}
		}
		return coords;
	}

	public ArrayList<Point2D> calculateFutureWallCoordinates(int x, int y) {
		ArrayList<Point2D> coords = new ArrayList<>();
		coords.add(new Point2D(x, y));

		Point2D p = coords.get(0); // coordinate of the start point of a wall.

		for (int i = 0; i < wallDirections.size(); i++) {
			Direction d = wallDirections.get(i);
			if (d == Direction.UP) {
				coords.add(new Point2D(p.getX(), p.getY() - 1));
				p = new Point2D(p.getX(), p.getY() - 2);
			} else if (d == Direction.RIGHT) {
				coords.add(new Point2D(p.getX() + 1, p.getY()));
				p = new Point2D(p.getX() + 2, p.getY());
			} else if (d == Direction.DOWN) {
				coords.add(new Point2D(p.getX(), p.getY() + 1)); // adds the wall segment's mid coordinate.
				p = new Point2D(p.getX(), p.getY() + 2); // jumps to the next edge.
			} else if (d == Direction.LEFT) {
				coords.add(new Point2D(p.getX() - 1, p.getY()));
				p = new Point2D(p.getX() - 2, p.getY());
			}
		}
		return coords;
	}

	public boolean passingFromThisPoint(Point2D point) {
		ArrayList<Point2D> coords = new ArrayList<>();
		coords = calculatePointWiseWallCoordinates();
		for (int index = 0; index < coords.size(); index++) {
			if (point.getX() == coords.get(index).getX() && point.getY() == coords.get(index).getY())
				return true;
		}
		return false;
	}

	public Point2D findWallCheckpoint() {
		ArrayList<Point2D> points = new ArrayList<>();
		points = calculatePointWiseWallCoordinates();

		int minX = (int) points.get(0).getX();
		int minY = (int) points.get(0).getY();
		Point2D checkpoint;
		for (int index = 0; index < points.size(); index++) {
			if (minX >= (int) points.get(index).getX())
				minX = (int) points.get(index).getX();
			if (minY >= (int) points.get(index).getY())
				minY = (int) points.get(index).getY();
		}
		checkpoint = new Point2D( minX, minY);
		Point2D startPoint = new Point2D((int) checkpoint.getX(),(int) checkpoint.getY());
		while( passingFromThisPoint( startPoint) == false) {
			startPoint = new Point2D( (int) startPoint.getX() + 1, (int) startPoint.getY());
		}
		
		return startPoint;

	}
	
	public void moveWall( Point2D location) {
		super.setPosition(location);
	}
}
