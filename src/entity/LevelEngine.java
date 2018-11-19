package entity;

import java.util.ArrayList;

public class LevelEngine extends GameEngine {

	boolean gameState;
	int currentLevel;
	GameTimer gameTimer;

	public LevelEngine(char[][] initialState, int dimensionX, int dimensionY, ArrayList<Wall> availableWalls) {
		super(initialState, dimensionX, dimensionY, availableWalls);

		currentLevel = 0;
		gameTimer = new GameTimer();
	}

	public boolean getGameState() {
		return gameState;
	}

	public void setGameState(boolean gameState) {
		this.gameState = gameState;
	}

	public int getCurrentLevel() {
		return currentLevel;
	}

	public void setCurrentLevel(int currentLevel) {
		this.currentLevel = currentLevel;
	}

	public boolean checkVictory() {
		boolean victory = false;
		for (int i = 0; i < levelBoard.getPlacedKnights().size(); i++) {
			if (levelBoard.getPlacedKnights().get(i).getColor().equals(Knight.KnightColor.BLUE)) {
				levelBoard.clearMarks();
				victory = checkVictoryHelper((int) levelBoard.getPlacedKnights().get(i).getPosition().getX(),
						(int) levelBoard.getPlacedKnights().get(i).getPosition().getY());
				if (victory == false)
					return false;
			}
		}
		return true;

	}

	public boolean checkVictoryHelper(int x, int y) {
		levelBoard.setMark(x, y);

		if (levelBoard.chechkMark(x + 1, y)) {
			if (levelBoard.getContent(x + 1, y) == 'R')
				return false;
			else if ((x + 1 <= levelBoard.getDimensionX() && x + 1 >= 0)
					&& (levelBoard.getContent(x + 1, y) != 'W' || levelBoard.getContent(x + 1, y) != 'T')) {
				checkVictoryHelper(x + 1, y);
			}
		}

		if (levelBoard.chechkMark(x - 1, y)) {
			if (levelBoard.getContent(x - 1, y) == 'R')
				return false;
			else if ((x - 1 <= levelBoard.getDimensionX() && x - 1 >= 0)
					&& (levelBoard.getContent(x - 1, y) != 'W' || levelBoard.getContent(x - 1, y) != 'T')) {
				checkVictoryHelper(x - 1, y);
			}
		}
		// not duplicate
		if (levelBoard.chechkMark(x, y + 1)) {
			if (levelBoard.getContent(x, y + 1) == 'R')
				return false;
			else if ((y + 1 <= levelBoard.getDimensionX() && y + 1 >= 0)
					&& (levelBoard.getContent(x, y + 1) != 'W' || levelBoard.getContent(x, y + 1) != 'T')) {
				checkVictoryHelper(x, y + 1);
			}
		}

		if (levelBoard.chechkMark(x, y - 1)) {
			if (levelBoard.getContent(x, y - 1) == 'R')
				return false;
			else if ((y - 1 <= levelBoard.getDimensionX() && y - 1 >= 0)
					&& (levelBoard.getContent(x, y - 1) != 'W' || levelBoard.getContent(x, y - 1) != 'T')) {
				checkVictoryHelper(x, y - 1);
			}
		}
		return true;

	}

	public boolean removeWall(int x, int y) {
		Wall removed = levelBoard.findAndRemoveWall(x, y);
		if (removed.getPosition().getY() != -10) {
			availableWalls.add(removed);
			return true;
		} else
			return false;
	}

	public boolean placeWall(Wall wall, int x, int y) {
		if (levelBoard.checkIfValid(wall, x, y)) {

			availableWalls.remove(wall);
			levelBoard.addWall(wall, x, y);
			return true;
		} else
			return false;
	}

}
