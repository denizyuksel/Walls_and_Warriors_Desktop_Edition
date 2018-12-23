package engine;

import java.awt.Graphics2D;
import java.awt.Rectangle;

public abstract class GameObject {

	protected int x = 0;
	protected int y = 0;
	protected int width = 0;
	protected int height = 0;
	protected float xSpeed = 0;
	protected float ySpeed = 0;

	public GameObject(int x, int y) {
		this(x, y, 0, 0);
	}

	public GameObject(int x, int y, int width, int height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		Handler.getInstance().add(this);
	}

	public abstract void tick();

	public abstract void render(Graphics2D g);

	public abstract Rectangle getBounds();

	public float getX() {
		return x;
	}

	public float getY() {
		return y;
	}
}
