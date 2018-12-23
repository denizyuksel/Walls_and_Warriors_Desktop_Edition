package engine;

import main.Main;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;


public class Keyboard implements KeyListener {

	private static Keyboard INSTANCE = null;

	/* VK_PLUS IS 521, the array is upper bounded by VK_PLUS */
	private static boolean[] keys = new boolean[522];

	private Keyboard() {}

	public static Keyboard getInstance() {
		if(INSTANCE == null) {
			return INSTANCE = new Keyboard();
		}
		return INSTANCE;
	}
	//Gets the inputs and stores them in variables at every tick
	public synchronized static void tick() {
		ESCAPE = keys[KeyEvent.VK_ESCAPE];
		F1 = keys[KeyEvent.VK_F1];
		F2 = keys[KeyEvent.VK_F2];
		F3 = keys[KeyEvent.VK_F3];
		F4 = keys[KeyEvent.VK_F4];
		F5 = keys[KeyEvent.VK_F5];
		F6 = keys[KeyEvent.VK_F6];
		F7 = keys[KeyEvent.VK_F7];
		F8 = keys[KeyEvent.VK_F8];
		F9 = keys[KeyEvent.VK_F9];
		F10 = keys[KeyEvent.VK_F10];
		F11 = keys[KeyEvent.VK_F11];
		F12 = keys[KeyEvent.VK_F12];
		PRINT_SCREEN = keys[KeyEvent.VK_PRINTSCREEN];
		SCROLL_LOCK = keys[KeyEvent.VK_SCROLL_LOCK];
		PAUSE_BREAK = keys[KeyEvent.VK_PAUSE];
		TILDE = keys[KeyEvent.VK_DEAD_TILDE];
		ZERO = keys[KeyEvent.VK_0];
		ONE = keys[KeyEvent.VK_1];
		TWO = keys[KeyEvent.VK_2];
		THREE = keys[KeyEvent.VK_3];
		FOUR = keys[KeyEvent.VK_4];
		FIVE = keys[KeyEvent.VK_5];
		SIX = keys[KeyEvent.VK_6];
		SEVEN = keys[KeyEvent.VK_7];
		EIGHT = keys[KeyEvent.VK_8];
		NINE = keys[KeyEvent.VK_9];
		BACK_SPACE = keys[KeyEvent.VK_BACK_SPACE];
		INSERT = keys[KeyEvent.VK_INSERT];
		DELETE = keys[KeyEvent.VK_DELETE];
		HOME = keys[KeyEvent.VK_HOME];
		END = keys[KeyEvent.VK_END];
		PAGE_UP = keys[KeyEvent.VK_PAGE_UP];
		PAGE_DOWN = keys[KeyEvent.VK_PAGE_DOWN];
		A = keys[KeyEvent.VK_A];
		B = keys[KeyEvent.VK_B];
		C = keys[KeyEvent.VK_C];
		D = keys[KeyEvent.VK_D];
		E = keys[KeyEvent.VK_E];
		F = keys[KeyEvent.VK_F];
		G = keys[KeyEvent.VK_G];
		H = keys[KeyEvent.VK_H];
		I = keys[KeyEvent.VK_I];
		J = keys[KeyEvent.VK_J];
		K = keys[KeyEvent.VK_K];
		L = keys[KeyEvent.VK_L];
		M = keys[KeyEvent.VK_M];
		N = keys[KeyEvent.VK_N];
		O = keys[KeyEvent.VK_O];
		P = keys[KeyEvent.VK_P];
		Q = keys[KeyEvent.VK_Q];
		R = keys[KeyEvent.VK_R];
		S = keys[KeyEvent.VK_S];
		T = keys[KeyEvent.VK_T];
		U = keys[KeyEvent.VK_U];
		V = keys[KeyEvent.VK_V];
		W = keys[KeyEvent.VK_W];
		X = keys[KeyEvent.VK_X];
		Y = keys[KeyEvent.VK_Y];
		Z = keys[KeyEvent.VK_Z];
		TAB = keys[KeyEvent.VK_TAB];
		CAPS_LOCK = keys[KeyEvent.VK_CAPS_LOCK];
		SHIFT = keys[KeyEvent.VK_SHIFT];
		CTRL = keys[KeyEvent.VK_CONTROL];
		ALT = keys[KeyEvent.VK_ALT];
		NUM_1 = keys[KeyEvent.VK_NUMPAD1];
		NUM_2 = keys[KeyEvent.VK_NUMPAD2];
		NUM_3 = keys[KeyEvent.VK_NUMPAD3];
		NUM_4 = keys[KeyEvent.VK_NUMPAD4];
		NUM_5 = keys[KeyEvent.VK_NUMPAD5];
		NUM_6 = keys[KeyEvent.VK_NUMPAD6];
		NUM_7 = keys[KeyEvent.VK_NUMPAD7];
		NUM_8 = keys[KeyEvent.VK_NUMPAD8];
		NUM_9 = keys[KeyEvent.VK_NUMPAD9];
		NUM_0 = keys[KeyEvent.VK_NUMPAD0];
		DEL = keys[KeyEvent.VK_DELETE];
		ENTER = keys[KeyEvent.VK_ENTER];
		SPACE = keys[KeyEvent.VK_SPACE];
		SLASH = keys[KeyEvent.VK_SLASH];
		STAR = keys[KeyEvent.VK_MULTIPLY];
		DASH = keys[KeyEvent.VK_SUBTRACT];
		PLUS = keys[KeyEvent.VK_PLUS];
		KEY_UP = keys[KeyEvent.VK_UP];
		KEY_DOWN = keys[KeyEvent.VK_DOWN];
		KEY_LEFT = keys[KeyEvent.VK_LEFT];
		KEY_RIGHT = keys[KeyEvent.VK_RIGHT];
	}

	@Override
	public void keyPressed(KeyEvent e) {
		keys[e.getKeyCode()] = true;

	}

	@Override
	public void keyReleased(KeyEvent e) {
		keys[e.getKeyCode()] = false;
	}

	@Override
	public void keyTyped(KeyEvent e) {}

	public static boolean ESCAPE, F1, F2, F3, F4, F5, F6, F7, F8, F9, F10, F11, F12, PRINT_SCREEN, SCROLL_LOCK, PAUSE_BREAK, TILDE, ZERO, ONE, TWO, THREE, FOUR, FIVE, SIX, SEVEN,
			EIGHT,
			NINE, BACK_SPACE, INSERT, DELETE, HOME, END, PAGE_UP, PAGE_DOWN, A, B, C, D, E, F, G, H, I, J, K, L,
			M, N, O, P, Q, R, S, T, U, V, W, X, Y, Z, TAB, CAPS_LOCK, SHIFT, CTRL, ALT, NUM_1, NUM_2, NUM_3, NUM_4, NUM_5, NUM_6, NUM_7, NUM_8, NUM_9, NUM_0, DEL, ENTER, SPACE,
			SLASH,
			STAR, DASH,
			PLUS, KEY_UP, KEY_DOWN, KEY_RIGHT, KEY_LEFT;
}
