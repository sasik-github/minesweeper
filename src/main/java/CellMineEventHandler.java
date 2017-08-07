import javafx.event.EventHandler;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

public class CellMineEventHandler implements EventHandler<MouseEvent>
{
    private Minesweeper minesweeper;

    private int defusedMinesCount = 0;

    public CellMineEventHandler(Minesweeper minesweeper)
    {
        this.minesweeper = minesweeper;
    }

    public void handle(MouseEvent event)
    {
        Cell cell = (Cell) event.getSource();

        if (event.getButton() == MouseButton.PRIMARY) {
            onPrimaryButton(cell);
        } else {
            onSecondaryButton(cell);
        }

        if (isAllBombDefused()) {
            System.out.println("You win");
            minesweeper.winGame();
        }


    }

    private void onSecondaryButton(Cell cell)
    {
        defusedMinesCount += cell.defuse();
    }

    private void onPrimaryButton(Cell cell)
    {
        cell.open();
        if (cell.hasMine() && !cell.isDefused()) {
            minesweeper.gameOver();
        }
    }

    private boolean isAllBombDefused()
    {
        return minesweeper.getMines() == defusedMinesCount;
    }

}
