package engine;

import java.awt.Graphics2D;

public interface EnginePartWindow {

	int getWidth();

	int getHeight();

	default void concatTickPipeline() {};

	default void postRender(Graphics2D g) {};
}
