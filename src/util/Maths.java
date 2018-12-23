package util;

import java.awt.Point;

public final class Maths {

	private Maths() {}

	public static double clamp(double value, double min, double max) {
		return Math.max(min, Math.min(max, value));
	}

	public static double lerp(double first, double second, double amt) {
		return first * (1 - amt) + second * amt;
	}

	public static double dot(Point first, Point second) {
		return first.x * second.x + first.y * second.y;
	}
}
