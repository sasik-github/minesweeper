import javafx.scene.control.Button;

import java.util.ArrayList;
import java.util.List;

public class Cell extends Button
{

    private static final int HEIGHT = GameApp.HEIGHT / Minesweeper.SIZE;
    private static final int WIDTH = GameApp.WIDTH / Minesweeper.SIZE;

    private int x;
    private int y;
    private final boolean hasMine;
    private boolean isDefused = false;
    private boolean isOpen = false;
    private int neighborsMineCount = 0;
    private List<Cell> neighborsCells = new ArrayList<>();

    public Cell(int x, int y, final boolean hasMine, CellMineEventHandler eventHandler)
    {
        this.x = x;
        this.y = y;
        this.hasMine = hasMine;

        setHeight(HEIGHT);
        setMinHeight(HEIGHT);
        setWidth(WIDTH);
        setMinWidth(WIDTH);

        setTranslateX(x * Cell.WIDTH);
        setTranslateY(y * Cell.HEIGHT);

        setOnMouseClicked(eventHandler);

    }

    public boolean hasMine()
    {
        return hasMine;
    }

    /**
     *
     * @return count of opened cells
     */
    private int showNeighbors()
    {
        if (!isOpen) {
            return 0;
        }

        if (neighborsMineCount > 0) {
            setText(String.valueOf(neighborsMineCount));
        }

        int openedCount = 0;
        setDisabled(true);
        if (neighborsMineCount == 0) {
            for (Cell neighborCell: neighborsCells) {
                openedCount += neighborCell.open();
            }
        }

        return openedCount;
    }

    public void setNeighborsCells(List<Cell> neighborsCells)
    {
        this.neighborsCells = neighborsCells;

        for (Cell neighborCell : neighborsCells) {
            if (neighborCell.hasMine()) {
                incrementNeighborsMineCount();
            }
        }

        showNeighbors();
    }

    private void incrementNeighborsMineCount()
    {
        neighborsMineCount++;
    }

    private void explode()
    {
        setStyle("-fx-background-color: red");
    }

    /**
     *
     * @return count of opened cells (+ neighbors that was recursively opened )
     */
    public int open()
    {
        if (isOpen || isDefused) {
            return 0;
        }

        isOpen = true;
        if (hasMine()) {
            explode();
            return 0;
        }

        return showNeighbors() + 1;
    }

    public int getX()
    {
        return x;
    }

    public int getY()
    {
        return y;
    }

    public int defuse()
    {
        if (isOpen) {
            return 0;
        }

        isDefused = !isDefused;
        String cellText = "";
        if (isDefused) {
            cellText = "X";
        }

        setText(cellText);

        if (hasMine()) {
            return isDefused ? 1 : -1;
        }

        return 0;
    }

    public boolean isDefused()
    {
        return isDefused;
    }
}
