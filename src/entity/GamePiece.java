package entity;

import javafx.geometry.Point2D;

abstract public class GamePiece {

	// properties
	private boolean movable;
	private boolean visible;
	private Point2D position;

	// methods

	public boolean getMovable() {
		return movable;
	}

	public void setMovable(boolean movable) {
		this.movable = movable;
	}

	public boolean getVisible() {
		return visible;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}

	public Point2D getPosition() {
		return position;
	}

	public void setPosition(Point2D position) {
		this.position = position;
	}

}
