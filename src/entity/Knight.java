package entity;

import javafx.geometry.Point2D;

public class Knight extends GamePiece {

	// properties

	public enum KnightColor {
		RED, BLUE
	}

	private KnightColor color;

	// constructors
	public Knight(Point2D position, KnightColor color, boolean movable, boolean visible) {
		this.color = color;
		super.setPosition(position);
		super.setMovable(movable);
		super.setVisible(visible);
		super.setPosition(position);
	}

	// methods
	public KnightColor getColor() {
		return color;
	}

	public void setKnightColor(KnightColor color) {
		this.color = color;
	}
}
