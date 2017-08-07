import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Minesweeper
{
    public static final int SIZE = 5;

    private static final int CELLS_PER_MINE = 6;

    private int mines = 0;

    private Random random = new Random();

    private Cell[][] cells;

    private CellMineEventHandler cellMineEventHandler;

    private long startGameMillis = 0;
    private Restartable restartableApplication;

    public Minesweeper(Restartable restartableApplication)
    {
        this.restartableApplication = restartableApplication;
        init();
        System.out.println("total mines: " + mines);
    }

    private void init()
    {
        cellMineEventHandler = new CellMineEventHandler(this);
        startGameMillis = System.currentTimeMillis();
        initCells();
    }

    public void gameOver()
    {
        int duration = (int) ((System.currentTimeMillis() - startGameMillis) / 1000);
        System.out.println("Game duration: " + duration);
        restartableApplication.restart();
    }

    public void winGame()
    {
        int duration = (int) ((System.currentTimeMillis() - startGameMillis) / 1000);
        System.out.println("Game duration: " + duration);
        restartableApplication.winGame();
    }

    private void initCells()
    {
        cells = new Cell[SIZE][SIZE];
        for(int x = 0; x < SIZE; x++) {
            for(int y = 0; y < SIZE; y++) {
                boolean hasMine = random.nextInt(CELLS_PER_MINE) == 0;
                mines += hasMine ? 1 : 0;
                Cell cell = new Cell(x, y, hasMine, cellMineEventHandler);
                cells[x][y] = cell;
            }
        }

        initNeighbors();

    }

    private void initNeighbors()
    {
        for(int x = 0; x < SIZE; x++) {
            for(int y = 0; y < SIZE; y++) {
                Cell currentCell = cells[x][y];
                currentCell.setNeighborsCells(getNeighbors(currentCell));
            }
        }
    }

    private List<Cell> getNeighbors(Cell cell)
    {
        int x = cell.getX();
        int y = cell.getY();
        int leftXBorder = x - 1 < 0 ? x : x - 1;
        int rigthXBorder = x + 1 >= SIZE ? x : x + 1;

        int topYBorder = y - 1 < 0 ? y : y - 1;
        int bottomYBorder = y + 1 >= SIZE ? y : y + 1;

        List<Cell> neighbors = new ArrayList<>();
        for (int dx =  leftXBorder; dx < rigthXBorder + 1; dx++) {
            for(int dy = topYBorder; dy < bottomYBorder + 1; dy++) {
                if (!(x == dx && y == dy)) {
                    neighbors.add(cells[dx][dy]);
                }
            }
        }

        return neighbors;
    }

    public Cell[][] getCells()
    {
        return cells;
    }

    public int getMines()
    {
        return mines;
    }
}
