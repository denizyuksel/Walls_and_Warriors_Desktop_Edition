package entity;

public class GameTimer {
	long currentTime;
	long startTime;
	boolean running;

	public GameTimer() {
		currentTime = 0;
		startTime = 0;
		running = false;

	}

	public String GetTime() {
		return "" + (currentTime - startTime);
	}

	public void reset() {
		running = false;
		startTime = 0;
		currentTime = 0;
	}

	public void start() {
		startTime = System.nanoTime();
		running = true;
	}

	public void stop() {
		running = false;
	}

	public void refresh() {
		if (running) 
			currentTime = System.nanoTime();
	}
}
