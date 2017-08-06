import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import static java.util.stream.Collectors.toList;

public class GameApp extends Application implements Restartable
{

    public static final String TITLE = "Minesweeper";
    public static final int WIDTH = 800;
    public static final int HEIGHT = 600;
    public static final int ADD_TO_HEIGHT = 20;

    private Mineseeper mineseeper;
    private Pane cellsPane;

    public static void main(String[] args)
    {
        launch(args);
    }

    public void start(Stage primaryStage) throws Exception
    {
        VBox vBox = new VBox();
        primaryStage.setTitle(TITLE);

        HBox topMenu = new HBox();
        Button restartButton = new Button("Restart");
        restartButton.setOnMouseClicked(e -> onRestartAction());
        restartButton.setMinHeight(ADD_TO_HEIGHT);
        topMenu.getChildren().add(restartButton);

        Timer timer = new Timer();
        topMenu.getChildren().add(timer);

        cellsPane = new Pane();
        vBox.getChildren().addAll(Arrays.asList(topMenu, cellsPane));


        Scene myScene = new Scene(vBox, WIDTH, HEIGHT + ADD_TO_HEIGHT);
        primaryStage.setScene(myScene);
        primaryStage.show();
        restart();
    }

    private void onRestartAction()
    {
        restart();
    }

    public void restart()
    {
        mineseeper = new Mineseeper(this);
        cellsPane.getChildren().clear();
        List<Cell> cellList = Arrays
                .stream(mineseeper.getCells())
                .flatMap(Arrays::stream)
                .collect(toList());
        cellsPane.getChildren().addAll(cellList);

    }

    @Override
    public void winGame()
    {
        ObservableList<Node> children = cellsPane.getChildren();

        for (Node child : children) {
            Cell cell = (Cell) child;
            String hex = getRandomHexColor();
            cell.setStyle("-fx-background-color: " + hex);
        }

    }

    private String getRandomHexColor()
    {
        Random random = new Random();
        java.awt.Color color1 = new java.awt.Color(random.nextFloat(), random.nextFloat(), random.nextFloat());
        return String.format("#%02x%02x%02x", color1.getRed(), color1.getBlue(), color1.getGreen());
    }


}
