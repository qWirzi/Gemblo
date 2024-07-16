package Gemblo;

/**
 * Class representing the small 5*5 board with a piece assigned to it
 * Used for Icons representing available pieces shown on the right of the screen
 * There are as many small boards as pieces available in the game (18 standard pieces)
 */
public class SmallBoard extends BoardInternal{

    // Most important characteristic of the small board: the piece assigned to it (displayed or not)
    public Piece assignedPiece;
    public int currentAngle;
    public boolean isDisplayed = true;

    /**
     * Main constructor for the small 5*5 board defined through a 2-dim matrix
     */
    public SmallBoard() {
        super("round", 5, 5, 3, 1); // fixed size for the small board
    }

    @Override
    /**
     * Placing a piece should be less sophisticated here than in general (on a big board)
     */
    public boolean place(Piece p, int r, int c, int angle) {

        this.currentAngle = angle;
        RealPiece piece = new RealPiece(p, 2, 2).rotate(angle);

        for (int[] coords : piece.realCoords) {
            this.boardMatrix[coords[0]][coords[1]].setState(piece.color);
        }

        assignedPiece = p; // the piece is now assigned to the small board
        isDisplayed = true;
        return true; // the piece was successfully placed
    }

    /**
     * Hide not display the piece on the small board
     */
    public void clearBoard(boolean permanent) {
        for (int i = 0; i < this.nRows; i++) {
            for (int j = 0; j < this.width; j++) {
                if (this.boardMatrix[i][j].state != CellState.FORBIDDEN) {
                    this.boardMatrix[i][j] = new Cell(CellState.EMPTY, i, j);
                }
            }
        }
        if (permanent) {
            this.isDisplayed = false;
        }
    }

    public void setStandard() {
        clearBoard(false);
        if (isDisplayed) {
            place(assignedPiece, 2, 2, 0);
        } else {
            clearBoard(true);
        }
    }

}
