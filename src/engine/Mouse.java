package engine;

import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;

import javax.swing.event.MouseInputAdapter;

import util.Maths;

import static gameplay.Wall.*;

public class Mouse extends MouseInputAdapter {

	private static Mouse INSTANCE = null;

	private static boolean[] buttons = new boolean[4];
	private static double mouseX = 0;
	private static double mouseY = 0;
	private static double mouseZ = 4;

	private Mouse() {}

	public static Mouse getInstance() {
		if(INSTANCE == null) {
			return INSTANCE = new Mouse();
		}
		return INSTANCE;
	}
	//Gets the inputs and stores them in variables at every tick
	public synchronized static void tick() {
		MB1 = buttons[MouseEvent.BUTTON1];
		MB2 = buttons[MouseEvent.BUTTON2];
		MB3 = buttons[MouseEvent.BUTTON3];
		MOUSE_X = mouseX;
		MOUSE_Y = mouseY;
		MBWHEEL = mouseZ;
	}

	@Override
	public void mousePressed(MouseEvent e) {
		buttons[e.getButton()] = true;
		mouseX = e.getX();
		mouseY = e.getY();
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		buttons[e.getButton()] = false;
		mouseX = e.getX();
		mouseY = e.getY();

	}

	@Override
	public void mouseMoved(MouseEvent e) {
		mouseX = e.getX();
		mouseY = e.getY();
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		mouseX = e.getX();
		mouseY = e.getY();
	}

	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		mouseZ -= e.getPreciseWheelRotation() * 0.5;
		mouseZ = Maths.clamp(mouseZ, 0.5, 10); 
	}

	public static boolean MB1, MB2, MB3;
	public static double MOUSE_X = mouseX;
	public static double MOUSE_Y = mouseY;
	public static double MBWHEEL = mouseZ;
}
