package engine;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferStrategy;
import java.lang.reflect.InvocationTargetException;
import java.util.MissingResourceException;

import javax.swing.SwingUtilities;

import gui.Window;

public class Engine extends Canvas implements Runnable {

	private static final long serialVersionUID = -2105482237764442641L;

	public static int FPS = 100;

	private static int numberOfEnginesRunning = 0;

	private EnginePartWindow gameWindow;

	private boolean isRunning = false;
	private Thread gameThread;

	//Constructor
	public Engine() {

		this.addKeyListener(Keyboard.getInstance());
		this.addMouseListener(Mouse.getInstance());
		this.addMouseMotionListener(Mouse.getInstance());
		numberOfEnginesRunning++;
		if(numberOfEnginesRunning > 1) {
			throw new MissingResourceException("Only one instance of Engine is allowed", "engine.Engine", "numberOfEnginesRunning > 1");
		}
	}
	public void setWindow(EnginePartWindow window){
		this.gameWindow = window;
	}
	//starts the engine
	public void start() {
		if(!isRunning) {
			isRunning = true;
			gameThread = new Thread(this);
			gameThread.start();
		} else {
			throw new IllegalStateException("Engine already running, cannot start again!");
		}
	}
	//Called after stopping and creating another engine
	public void destroy(){
		numberOfEnginesRunning--;
		Handler.getInstance().clear();
	}
	//Stops the engine
	public void stop() {
		if(isRunning) {
			isRunning = false;
			try {
				gameThread.join();
			} catch(InterruptedException ex) {
				ex.printStackTrace();
			}
		}
	}
	//runs according to the FPS of the game controls tick intervals
	@Override
	public void run() {
		this.requestFocus();
		long lastTime = System.nanoTime();
		long thisTime = System.nanoTime();
		double nanoseconds = 1000000000 / FPS;
		double deltaTime = 0;
		while(isRunning) {
			thisTime = System.nanoTime();
			deltaTime += (thisTime - lastTime) / nanoseconds;
			lastTime = thisTime;
			while(deltaTime >= 1) {
				tick();
				deltaTime--;
			}
			render();
		}
		stop();
	}
	//ticks other engine objects
	private void tick() {
		Keyboard.tick();
		Mouse.tick();
		Handler.getInstance().tick();

		gameWindow.concatTickPipeline();
	}
	//Renders the instances of the gameObjects
	private void render() {

		BufferStrategy bs = super.getBufferStrategy();
		if(bs == null) {
			super.createBufferStrategy(3);
			return;
		}
		Graphics2D g = (Graphics2D) bs.getDrawGraphics();

		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,  RenderingHints.VALUE_ANTIALIAS_ON);
		g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR);
		g.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);

		g.clearRect(0, 0, gameWindow.getWidth(), gameWindow.getHeight());
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, gameWindow.getWidth(), gameWindow.getHeight());

		/* RENDERING STARTS HERE */
		/* | */
		/* | */
		/* | */Handler.getInstance().render(g);
		/* | */
		/* V */
		/* RENDERING ENDS HERE */

		gameWindow.postRender(g);

		bs.show();
		g.dispose();
	}

}
