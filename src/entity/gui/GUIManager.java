package gui;
/*
 * GUIManager.java
 * Manages the two existing pages, a level page and a main menu page.
 * Subject to change in the second iteration.
 */

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

public class GUIManager extends Application {

	// constants
	static final int SCENE_WIDTH = 1200;
	static final int SCENE_HEIGHT = 800;

	// properties

	private Scene gameScene;
	private Scene menuScene;
	private Scene currentScene;
	private MenuPage mainMenu;
	private GamePage levelPage;
	private String[][] boardState;

	@Override
	public void start(Stage primaryStage) {
		// set up the two pages

		mainMenu = new MenuPage();
		levelPage = new GamePage();
		setMenuPageListeners(primaryStage);
		setLevelPageListeners(primaryStage);
		gameScene = levelPage.createScene();
		menuScene = mainMenu.createScene();

		currentScene = menuScene;

		//initialize to empty board
		boardState = new String[][]{{"I", "I", "E", "E", "E", "E", "E", "I", "I"},
				{"I", "I", "E", "E", "E", "E", "E", "I", "I"},
				{"E", "E", "E", "E", "E", "E", "E", "E", "E"},
				{"E", "E", "E", "E", "E", "E", "E", "E", "E"},
				{"E", "E", "E", "E", "E", "E", "E", "E", "E"},
				{"E", "E", "E", "E", "E", "E", "E", "E", "E"},
				{"E", "E", "E", "E", "E", "E", "E", "E", "E"},
				{"E", "E", "E", "E", "E", "E", "E", "E", "E"},
				{"E", "E", "E", "E", "E", "E", "E", "E", "E"},
				{"I", "I", "E", "E", "E", "E", "E", "I", "I"},
				{"I", "I", "E", "E", "E", "E", "E", "I", "I"}};

		drawBoard();
		levelPage.drawWalls();
		primaryStage.setResizable(false);
		primaryStage.setScene(currentScene);
		primaryStage.setTitle("Walls and Warriors");
		primaryStage.show();
	}

	private void setMenuPageListeners(Stage primaryStage) {
		// pressing the new game button changes the scene to level page
		mainMenu.getNewGameButton().setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				primaryStage.setScene(gameScene);
				currentScene = gameScene;

				//set new board state TEST.
				String[][] arr = new String[][]{{ "I", "I", "E", "E", "E", "E", "E", "I", "I" },
												{ "I", "I", "E", "E", "E", "B", "E", "I", "I" },
												{ "E", "E", "E", "E", "E", "E", "E", "E", "E" },
												{ "E", "R", "E", "E", "E", "E", "E", "E", "E" },
						 						{ "E", "E", "E", "E", "E", "E", "E", "E", "E" },
												{ "E", "E", "E", "T", "T", "T", "E", "E", "E" },
												{ "E", "E", "E", "E", "E", "E", "E", "E", "E" },
												{ "E", "B", "E", "E", "E", "E", "E", "R", "E" },
												{ "E", "E", "E", "E", "E", "E", "E", "E", "E" },
												{ "I", "I", "E", "E", "E", "R", "E", "I", "I" },
												{ "I", "I", "E", "E", "E", "E", "E", "I", "I" } };

				setBoardState(arr);
				levelPage.updateBoard(boardState);
			}
		});

		mainMenu.getQuitButton().setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				System.exit(1);
			}
		});
	}

	private void setLevelPageListeners(Stage primaryStage) {
		levelPage.getBackButton().setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				primaryStage.setScene(menuScene);
				currentScene = menuScene;
			}
		});
		levelPage.getHelpButton().setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				Alert alert = new Alert(Alert.AlertType.INFORMATION, "Place the walls on top inside edges in between cells.\n" +
						"Try to save blue soldiers from the reds by surrounding them with walls.", ButtonType.OK);
				alert.showAndWait();
			}
		});
	}

	// setter & getter methods

	public void setScene(Scene newScene) {
		currentScene = newScene;
	}

	public MenuPage getMainMenu() {
		return mainMenu;
	}

	public GamePage getLevelPage() {
		return levelPage;
	}

	public void drawBoard() {
		levelPage.drawBoard(boardState);
	}

	// methods

	public void setBoardState(String[][] boardState) {
		this.boardState = boardState;
	}

	public String[][] getBoardState() {
		return boardState;
	}

}