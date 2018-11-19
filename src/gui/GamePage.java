package gui;

import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

/**
 * GamePage.java
 */
public class GamePage {

	// constants
	private static final int CELL_HEIGHT = 80;
	private static final int CELL_WIDTH = 80;
	private static final int EDGE_HEIGHT = 20;
	private static final int EDGE_WIDTH = 20;
	private static final int MARGIN_LEFT = 240;
	private static final int MARGIN_TOP = 240;

	// properties
	private Group layout;
	private Button backButton;
	private Button helpButton;
	private TextField timer;
	private Text[][] texts;

	private double orgSceneX, orgSceneY, orgTranslateX, orgTranslateY;
	private int counter = 0;
	// constructor

	public GamePage() {
		texts = new Text[11][9];
		backButton = new Button("Back");
		helpButton = new Button("Help");
		layout = new Group();
		timer = new TextField("00.00");

		setBackButtonProperties();
		setHelpButtonProperties();

		layout.getChildren().add(backButton);
		layout.getChildren().add(helpButton);

	}

	// methods

	private void setBackButtonProperties() {
		backButton.setLayoutX(50);
		backButton.setLayoutY(50);
		backButton.setScaleX(1.5);
		backButton.setScaleY(1.5);
	}

	private void setHelpButtonProperties() {
		helpButton.setLayoutX(GUIManager.SCENE_WIDTH - 100);
		helpButton.setLayoutY(50);
		helpButton.setScaleY(1.5);
		helpButton.setScaleX(1.5);
	}

	public Scene createScene() {
		return new Scene(layout, GUIManager.SCENE_WIDTH, GUIManager.SCENE_HEIGHT, Color.BEIGE);
	}

	public void updateBoard(String[][] boardState) {
		for (int i = 0; i < boardState.length; i++) {
			for (int j = 0; j < boardState[0].length; j++) {
				(texts[i][j]).setText(boardState[i][j]);				
				if(!texts[i][j].getText().equals("I") && !texts[i][j].getText().equals("E"))
					texts[i][j].setVisible(true);
			}
		}
	}

	public void drawBoard(String[][] boardState) {
		if (boardState != null) {
			int x = MARGIN_LEFT;
			int y;
			for (int i = 0; i < boardState.length; i++) {
				y = MARGIN_TOP;
				for (int j = 0; j < boardState[0].length; j++) {
					Text text = new Text(boardState[i][j]);
					texts[i][j] = text;
					layout.getChildren().add(text);
					Rectangle rectangle;
					if (j % 2 != 0) {
						//j odd, i odd
						if (i % 2 != 0) {
							rectangle = new Rectangle(x, y, CELL_WIDTH, CELL_HEIGHT);
							text.setLayoutX(x + (CELL_WIDTH / 2));
							text.setLayoutY(y + (CELL_HEIGHT / 2));
							if (text.getText().equals("I") || text.getText().equals("E"))
								text.setVisible(false);
						}
						//j odd, i even
						else {
							rectangle = new Rectangle(x, y, EDGE_WIDTH, CELL_HEIGHT);
							text.setLayoutX(x + (EDGE_WIDTH / 2));
							text.setLayoutY(y + (CELL_HEIGHT / 2));
							if (text.getText().equals("I") || text.getText().equals("E"))
								text.setVisible(false);
						}
						y += CELL_HEIGHT;

					} else {
						//j even, i odd
						if (i % 2 != 0) {
							rectangle = new Rectangle(x, y, CELL_WIDTH, EDGE_HEIGHT);
							text.setLayoutX(x + (CELL_WIDTH / 2));
							text.setLayoutY(y + (EDGE_HEIGHT / 2));
							if (text.getText().equals("I") || text.getText().equals("E"))
								text.setVisible(false);
						}
						//j even, i even
						else {
							rectangle = new Rectangle(x, y, EDGE_WIDTH, EDGE_HEIGHT);
							text.setLayoutX(x + (EDGE_WIDTH / 2));
							text.setLayoutY(y + (EDGE_HEIGHT / 2));
							if (text.getText().equals("I") || text.getText().equals("E"))
								text.setVisible(false);
						}
						y += EDGE_HEIGHT;
					}
					rectangle.setFill(Color.TRANSPARENT);
					rectangle.setStroke(Color.BLACK);
					rectangle.setArcHeight(7.0);
					rectangle.setArcWidth(7.0);
					if (boardState[i][j].charAt(0) != 'I') {
						layout.getChildren().add(rectangle);
					}

				}//inner loop end
				if (i % 2 == 0) {
					x += EDGE_WIDTH;
				} else {
					x += CELL_WIDTH;
				}
			}//outer loop end
		}
	}

	// getter & setter methods

	public Group getLayout() {
		return layout;
	}

	public Button getBackButton() {
		return backButton;
	}

	public void setBackButton(Button backButton) {
		this.backButton = backButton;
	}

	public Button getHelpButton() {
		return helpButton;
	}

	public void setHelpButton(Button helpButton) {
		this.helpButton = helpButton;
	}

	public TextField getTimer() {
		return timer;
	}

	public void setTimer(TextField timer) {
		this.timer = timer;
	}

	public void drawWalls() {
		double x = 1.0 * MARGIN_LEFT;
		double y = EDGE_HEIGHT + CELL_HEIGHT;
		Polygon polygon = new Polygon();
		polygon.getPoints().addAll(x, y,
				x, y - EDGE_HEIGHT - CELL_HEIGHT,
				x + CELL_WIDTH + 2*EDGE_WIDTH, y - EDGE_HEIGHT - CELL_HEIGHT,
				x + 2*EDGE_WIDTH + CELL_WIDTH, y,
				x + CELL_WIDTH + EDGE_WIDTH, y,
				x + CELL_WIDTH + EDGE_WIDTH, y - CELL_HEIGHT,
				x + EDGE_WIDTH, y - CELL_HEIGHT,
				x + EDGE_WIDTH, y);
		polygon.setFill(Color.RED);
		polygon.setStroke(Color.RED);
		polygon.setOnMousePressed(pressHandler);
		polygon.setOnMouseDragged(dragHandler);
		polygon.setOnMouseReleased(releaseHandler);
		layout.getChildren().add(polygon);

		x += 2 * CELL_WIDTH;
		y = EDGE_HEIGHT;
		polygon = new Polygon();
		polygon.getPoints().addAll(x, y, x, y + CELL_HEIGHT + EDGE_HEIGHT, x + CELL_WIDTH + EDGE_WIDTH,
				y + EDGE_HEIGHT + CELL_HEIGHT, x + EDGE_WIDTH + CELL_WIDTH, y + 2 * EDGE_HEIGHT + 2 * CELL_HEIGHT,
				x + 3 * EDGE_WIDTH + 2 * CELL_WIDTH, y + 2 * EDGE_HEIGHT + 2 * CELL_HEIGHT,
				x + 3 * EDGE_WIDTH + 2 * CELL_WIDTH, y + EDGE_HEIGHT + CELL_HEIGHT, x + 2 * EDGE_WIDTH + 2 * CELL_WIDTH,
				y + EDGE_HEIGHT + CELL_HEIGHT, x + 2 * CELL_WIDTH + 2 * EDGE_WIDTH, y + 2 * CELL_HEIGHT + EDGE_HEIGHT,
				x + 2 * EDGE_WIDTH + CELL_WIDTH, y + EDGE_HEIGHT + 2 * CELL_HEIGHT, x + 2 * EDGE_WIDTH + CELL_WIDTH,
				y + CELL_HEIGHT, x + EDGE_WIDTH, y + CELL_HEIGHT, x + EDGE_WIDTH, y);
		polygon.setOnMousePressed(pressHandler);
		polygon.setOnMouseDragged(dragHandler);
		polygon.setOnMouseReleased(releaseHandler);
		polygon.setFill(Color.BLUE);
		polygon.setStroke(Color.RED);
		layout.getChildren().add(polygon);

		x += 3 * EDGE_WIDTH + 3 * CELL_WIDTH;
		y += EDGE_HEIGHT + CELL_HEIGHT;
		polygon = new Polygon();
		polygon.getPoints().addAll(x, y, x, y - CELL_HEIGHT - EDGE_HEIGHT, x + CELL_WIDTH + 2 * EDGE_WIDTH,
				y - EDGE_HEIGHT - CELL_HEIGHT, x + 2 * EDGE_WIDTH + CELL_WIDTH, y, x + 2 * EDGE_WIDTH + 2 * CELL_WIDTH,
				y, x + 2 * EDGE_WIDTH + 2 * CELL_WIDTH, y - EDGE_HEIGHT - CELL_HEIGHT,
				x + 3 * EDGE_WIDTH + 3 * CELL_WIDTH, y - EDGE_HEIGHT - CELL_HEIGHT, x + 3 * CELL_WIDTH + 3 * EDGE_WIDTH,
				y - CELL_HEIGHT, x + 3 * EDGE_WIDTH + 2 * CELL_WIDTH, y - CELL_HEIGHT,
				x + 3 * EDGE_WIDTH + 2 * CELL_WIDTH, y + EDGE_HEIGHT, x + EDGE_WIDTH + CELL_WIDTH, y + EDGE_HEIGHT,
				x + EDGE_WIDTH + CELL_WIDTH, y - CELL_HEIGHT, x + EDGE_WIDTH, y - CELL_HEIGHT, x + EDGE_WIDTH, y);
		polygon.setOnMousePressed(pressHandler);
		polygon.setOnMouseDragged(dragHandler);
		polygon.setOnMouseReleased(releaseHandler);
		polygon.setFill(Color.ORANGE);
		polygon.setStroke(Color.RED);
		layout.getChildren().add(polygon);

		x += 2 * EDGE_WIDTH + 3 * CELL_WIDTH;
		y += CELL_HEIGHT + EDGE_HEIGHT;
		polygon = new Polygon();
		polygon.getPoints().addAll(x, y, x + CELL_WIDTH + EDGE_WIDTH, y, x + CELL_WIDTH + EDGE_WIDTH,
				y - EDGE_HEIGHT - CELL_HEIGHT, x + 2 * EDGE_WIDTH + 2 * CELL_WIDTH, y - EDGE_HEIGHT - CELL_HEIGHT,
				x + 2 * EDGE_WIDTH + 2 * CELL_WIDTH, y - 2 * EDGE_HEIGHT - 2 * CELL_HEIGHT,
				x + EDGE_WIDTH + 2 * CELL_WIDTH, y - 2 * EDGE_HEIGHT - 2 * CELL_HEIGHT, x + EDGE_WIDTH + 2 * CELL_WIDTH,
				y - 2 * EDGE_HEIGHT - CELL_HEIGHT, x + CELL_WIDTH, y - 2 * EDGE_HEIGHT - CELL_HEIGHT, x + CELL_WIDTH,
				y - EDGE_HEIGHT, x + CELL_WIDTH, y - EDGE_HEIGHT, x, y - EDGE_HEIGHT);
		polygon.setOnMousePressed(pressHandler);
		polygon.setOnMouseDragged(dragHandler);
		polygon.setOnMouseReleased(releaseHandler);
		polygon.setFill(Color.VIOLET);
		polygon.setStroke(Color.RED);
		layout.getChildren().add(polygon);

	}// end method "drawWalls()"

	// Event handler objects

	private EventHandler<MouseEvent> pressHandler = new EventHandler<MouseEvent>() {

		@Override
		public void handle(MouseEvent t) {
			orgSceneX = t.getSceneX();
			orgSceneY = t.getSceneY();
			orgTranslateX = ((Polygon) (t.getSource())).getTranslateX();
			orgTranslateY = ((Polygon) (t.getSource())).getTranslateY();

		}
	};

	private EventHandler<MouseEvent> dragHandler = new EventHandler<MouseEvent>() {

		@Override
		public void handle(MouseEvent t) {
			double offsetX = t.getSceneX() - orgSceneX;
			double offsetY = t.getSceneY() - orgSceneY;
			double newTranslateX = orgTranslateX + offsetX;
			double newTranslateY = orgTranslateY + offsetY;

			((Polygon) (t.getSource())).setTranslateX(newTranslateX);
			((Polygon) (t.getSource())).setTranslateY(newTranslateY);

		}
	};

	private EventHandler<MouseEvent> releaseHandler = t -> {
		if (isOnEdge((int)t.getSceneX(), (int)t.getSceneY())) {
			if (checkVictory((Polygon)t.getSource()))
			{
				counter++;
			}
			if(counter >= 4)
			{
				Alert winMessage = new Alert(Alert.AlertType.INFORMATION);
				winMessage.setTitle("Congratulations");
				winMessage.setHeaderText(null);
				winMessage.setContentText("You won!");
				winMessage.showAndWait().ifPresent(rs -> {
				    if (rs == ButtonType.OK) {
					        System.exit(1);
				    }
				});
			}

		} else {
			((Polygon) (t.getSource())).setTranslateX(0);
			((Polygon) (t.getSource())).setTranslateY(0);
		}
	};
	
	public boolean isOnEdge(int x, int y) {
        if (x < MARGIN_LEFT || y < MARGIN_TOP)
            return false;
        int k = MARGIN_LEFT;
        for (int i = 0; i < 11; i++) {
            int l = MARGIN_TOP;
            for (int j = 0; j < 9; j++) {
                if (j % 2 != 0) {
                    //j odd, i odd
                    if (i % 2 != 0) {
                        if ((k < x  && x < k + CELL_WIDTH) && (l < y && y < l + CELL_HEIGHT))
                            return false;
                    }
                    //j odd, i even
                    else {
                        if ((k < x && x < k + EDGE_WIDTH) && (l < y && y < l + CELL_HEIGHT)) {
							return true;
						}
                    }
                    l += CELL_HEIGHT;

                } else {
                    //j even, i odd
                    if (i % 2 != 0) {
                        if((k < x && x < k + CELL_WIDTH) && (l < y && y < l + EDGE_HEIGHT)) {
							return true;
						}
                    }
                    //j even, i even
                    else {
                        if ((k < x && x < k + EDGE_WIDTH) && (l < y && y < l + EDGE_HEIGHT)) {
							return true;
						}
                    }
                    l += EDGE_HEIGHT;
                }

                }//inner loop end
            if (i % 2 == 0) {
                k += EDGE_WIDTH;
            } else {
                k += CELL_WIDTH;
            }
        }//outer loop end
        return false;
    }
	
	public boolean checkVictory(Polygon p){
		
		if((p.getTranslateX() > 298.0 && p.getTranslateX() < 308.0 && p.getTranslateY() > 238.0 && p.getTranslateY() < 248.0)
				|| (p.getTranslateX() < -156.0 && p.getTranslateX() > -166.0 && p.getTranslateY() > 438.0 && p.getTranslateY() < 448.0)
				|| (p.getTranslateX() < -458.0 && p.getTranslateX() > -468.0 && p.getTranslateY() > 317.0 && p.getTranslateY() < 327.0)
				|| (p.getTranslateX() < -517.0 && p.getTranslateX() > -527.0 && p.getTranslateY() > 336.0 && p.getTranslateY() < 346.0))
		{
			return true;
		}
		return false;
		
	}
}

