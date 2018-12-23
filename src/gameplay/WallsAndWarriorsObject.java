package gameplay;

import engine.GameObject;

abstract public class WallsAndWarriorsObject extends GameObject {

	 boolean movable;
	 boolean visible;

	public WallsAndWarriorsObject(int x, int y) {
		super(x, y);
	}

	public WallsAndWarriorsObject(int x, int y, int width, int height) {
		super(x, y);
	}

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
}