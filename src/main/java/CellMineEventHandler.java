import javafx.event.EventHandler;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

public class CellMineEventHandler implements EventHandler<MouseEvent>
{
    private Mineseeper mineseeper;

    private int defusedMinesCount = 0;

    public CellMineEventHandler(Mineseeper mineseeper)
    {
        this.mineseeper = mineseeper;
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
            mineseeper.winGame();
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
            mineseeper.gameOver();
        }
    }

    private boolean isAllBombDefused()
    {
        return mineseeper.getMines() == defusedMinesCount;
    }

}
