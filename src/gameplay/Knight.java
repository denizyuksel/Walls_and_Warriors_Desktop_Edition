package gameplay;

import java.awt.Graphics2D;
import java.awt.Rectangle;

public class Knight extends WallsAndWarriorsObject {

	private KnightColor color;

	public Knight(int x, int y, KnightColor color, boolean movable) {
		super(x, y);
		super.setMovable(movable);
		this.color = color;
	}

	public KnightColor getColor() {
		return color;
	}

	public void setKnightColor(KnightColor color) {
		this.color = color;
	}

	public enum KnightColor {
		
	}

	@Override
	public void tick() {}

	@Override
	public void render(Graphics2D g) {
		
	}

	@Override
	public Rectangle getBounds() {
		return null;
	}
}
