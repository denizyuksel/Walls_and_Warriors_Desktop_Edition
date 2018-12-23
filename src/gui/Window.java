package gui;

import java.awt.Graphics2D;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

import engine.Engine;
import engine.EnginePartWindow;

public class Window extends JFrame implements EnginePartWindow {
	
	public static final int WIDTH = 1920;
	public static final int HEIGHT = 1080;

	public static final int INNER_WIDTH = 1264;
	public static final int INNER_HEIGHT = 681;
	
	private static final long serialVersionUID = 5839375177464673897L;
	
	public Window(Engine engine) {
		super("Walls & Warriors");
		super.setBounds(0, 0, WIDTH, HEIGHT);
		super.setUndecorated(true);
		super.setResizable(false);
		super.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		super.add(engine);
		super.setVisible(true);
	}
	
	@Override
	public void concatTickPipeline() {}

	@Override
	public void postRender(Graphics2D g) {}
}
