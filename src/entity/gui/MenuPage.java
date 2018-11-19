package gui;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;

/**
 * MenuPage.java
 */

public class MenuPage {

    //properties
    private Group layout;
    private Button newGameButton;
    private Button quitButton;

    //constructor
    public MenuPage() {
        layout = new Group();
        newGameButton = new Button("New Game");
        quitButton = new Button("Quit");

        newGameButton.setScaleX(2);
        newGameButton.setScaleY(2);
        quitButton.setScaleY(2);
        quitButton.setScaleX(2);

        newGameButton.setLayoutX(GUIManager.SCENE_WIDTH / 2 - 50);
        newGameButton.setLayoutY(GUIManager.SCENE_HEIGHT / 2 - 50);
        quitButton.setLayoutX(GUIManager.SCENE_WIDTH / 2 - 25);
        quitButton.setLayoutY(GUIManager.SCENE_HEIGHT / 2 + 50);

        layout.getChildren().add(newGameButton);
        layout.getChildren().add(quitButton);
    }

    //methods

    public Group getLayout() {
        return layout;
    }

    public Button getNewGameButton() {
        return newGameButton;
    }

    public void setNewGameButton(Button newGameButton) {
        this.newGameButton = newGameButton;
    }

    public Button getQuitButton() {
        return quitButton;
    }

    public void setQuitButton(Button quitButton) {
        this.quitButton = quitButton;
    }

    public Scene createScene() {
        return new Scene(layout, GUIManager.SCENE_WIDTH, GUIManager.SCENE_HEIGHT, Color.BEIGE);
    }
}
