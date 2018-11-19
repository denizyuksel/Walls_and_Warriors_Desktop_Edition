package entity;

import java.util.*;

public abstract class GameEngine {
	boolean gameState;
	GameBoard levelBoard;
	ArrayList<Wall> availableWalls;

	public GameEngine(char[][] initialBoardState, int x, int y, ArrayList<Wall> availableWalls) {
		levelBoard = new GameBoard(initialBoardState, x, y);
		this.availableWalls = new ArrayList<Wall>();
		for (int i = 0; i < availableWalls.size(); i++) {
			this.availableWalls.add(availableWalls.get(i));
		}
		gameState = false;
	}

	public GameBoard getLevelBoard() {
		return levelBoard;
	}

	public void setLevelBoard(GameBoard newLevelBoard) {
		levelBoard = newLevelBoard;
	}

	public ArrayList<Wall> getavaibleWalls() {
		return availableWalls;
	}

	public void setavaibleWalls(ArrayList<Wall> walls) {
		availableWalls = walls;
	}

	public void levelComplete() {
		gameState = true;
	}

	public boolean isLevelComplete() {
		return gameState;
	}
}
