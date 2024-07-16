package Gemblo;

import java.util.ArrayList;
import java.util.List;


/**
 * Class for the internal representation of the board
 * Checks if a new piece can be added to the board, i.e. implements the logic of the Gemblo game
 * Has a method for printing the board in the console
 */
public class BoardInternal {

    /** Instance variables */
    public int width;            // how wide the board is vertically
    public int nRows;            // how many rows the rectangular part of the board has
    public Cell[][] boardMatrix; // matrix values represent individual cells
    public int nPlayers;         // value from 1 to 6 foreseen

    private final String sstep; // parameter used for printing the board
    private final String step;  // complementary parameter

    public List<Cell[][]> boardHistory;
    private int currentIter = 0;

    ArrayList<ArrayList<Piece>> piecesOnBoard = new ArrayList<>(); // save all pieces used by each player

    /** Getters for the key variables */
    public int getWidth() { return this.width; }
    public int getNRows() { return this.nRows; }


    /** Needed for creating rounded board*/
    private int calculateNumberOfCells(int row, int width) {
        int halfWidth = (width + 1) / 2;
        if (row < halfWidth) {
            return halfWidth + row;
        } else {
            return 3 * halfWidth - row - 2;
        }
    }

    /**
     * Main constructor for the board defined through a 2-dim matrix
     */
    public BoardInternal(final String type,
                         final int nRows, final int width, final int spacing, final int nPlayers) {
        // update all class variables
        this.width = width;
        //this.nRows = nRows;
        this.sstep = " ".repeat(spacing);
        this.step = " ".repeat(spacing * 2 - 1);
        this.nPlayers = nPlayers;

        //this.boardMatrix = new Cell[nRows][width]; // the matrix with nRows rows representing the board
        this.boardHistory = new ArrayList<>();

        for (int i = 0; i < nPlayers; i++) {
            ArrayList<Piece> piecesOfPlayer = new ArrayList<>();
            piecesOnBoard.add(piecesOfPlayer);
        }



        // Construct different type of boards depending on the parameter
        switch (type) {
            // Constructing a squared board for 2 or 4 players
            case "sq" -> {
                this.nRows = nRows;
                this.boardMatrix = new Cell[nRows][width];

                for (int i = 0; i < nRows; i++) {
                    for (int j = 0; j < width - 1; j++) {
                        Cell k = new Cell(CellState.EMPTY, i, j);
                        this.boardMatrix[i][j] = k;
                    }
                    this.boardMatrix[i][width - 1] = new Cell((i % 2 == 0) ? CellState.EMPTY : CellState.FORBIDDEN,
                            i, width - 1);
                }
            }

            // Constructing a board with same rows and columns which looks round
            // used for small boards as icons on the side -- not fully functional now
            case "r" -> {
                this.nRows = width;
                this.boardMatrix = new Cell[this.nRows][width];

                for (int row = 0; row < this.nRows - (width + 1) % 2; row++) {
                    int numberOfCells = calculateNumberOfCells(row, width);
                    int offset = (width - numberOfCells) / 2 + (row + 1) % 2;

                    for (int col = 0; col < width; col++) {
                        if (col < offset || col >= offset + numberOfCells) {
                            this.boardMatrix[row][col] = new Cell(CellState.FORBIDDEN, row, col);
                        } else {
                            this.boardMatrix[row][col] = new Cell(CellState.EMPTY, row, col);
                        }
                    }
                }
            }

            // Constructing one concrete 5*5 round board
            case "round" -> {
                this.nRows = 5;
                this.width = 5;
                this.boardMatrix = new Cell[this.nRows][width];

                this.boardMatrix[0][0] = new Cell(CellState.FORBIDDEN, 0, 0);
                this.boardMatrix[0][1] = new Cell(CellState.EMPTY, 0, 0);
                this.boardMatrix[0][2] = new Cell(CellState.EMPTY, 0, 0);
                this.boardMatrix[0][3] = new Cell(CellState.EMPTY, 0, 0);
                this.boardMatrix[0][4] = new Cell(CellState.FORBIDDEN, 0, 0);
                this.boardMatrix[1][0] = new Cell(CellState.EMPTY, 0, 0);
                this.boardMatrix[1][1] = new Cell(CellState.EMPTY, 0, 0);
                this.boardMatrix[1][2] = new Cell(CellState.EMPTY, 0, 0);
                this.boardMatrix[1][3] = new Cell(CellState.EMPTY, 0, 0);
                this.boardMatrix[1][4] = new Cell(CellState.FORBIDDEN, 0, 0);
                this.boardMatrix[2][0] = new Cell(CellState.EMPTY, 0, 0);
                this.boardMatrix[2][1] = new Cell(CellState.EMPTY, 0, 0);
                this.boardMatrix[2][2] = new Cell(CellState.EMPTY, 0, 0);
                this.boardMatrix[2][3] = new Cell(CellState.EMPTY, 0, 0);
                this.boardMatrix[2][4] = new Cell(CellState.EMPTY, 0, 0);
                this.boardMatrix[3][0] = new Cell(CellState.EMPTY, 0, 0);
                this.boardMatrix[3][1] = new Cell(CellState.EMPTY, 0, 0);
                this.boardMatrix[3][2] = new Cell(CellState.EMPTY, 0, 0);
                this.boardMatrix[3][3] = new Cell(CellState.EMPTY, 0, 0);
                this.boardMatrix[3][4] = new Cell(CellState.FORBIDDEN, 0, 0);
                this.boardMatrix[4][0] = new Cell(CellState.FORBIDDEN, 0, 0);
                this.boardMatrix[4][1] = new Cell(CellState.EMPTY, 0, 0);
                this.boardMatrix[4][2] = new Cell(CellState.EMPTY, 0, 0);
                this.boardMatrix[4][3] = new Cell(CellState.EMPTY, 0, 0);
                this.boardMatrix[4][4] = new Cell(CellState.FORBIDDEN, 0, 0);
            }


            // Constructing the board for 3 or 6 players where the corners are blended out - marked as forbidden cells
            // not implemented
            default -> {

                System.out.println("Entering else");
                int smallestRow = (width + 1) / 2;
                int steppingRow = 1;

                int trimmedRows = width - smallestRow;          // how many rows are not full-width (one side)

                this.nRows = nRows + trimmedRows * 2;
                this.boardMatrix = new Cell[this.nRows][width]; // update total number of rows

                System.out.println(nRows + " " + trimmedRows);

                for (int i = 0; i < this.nRows; i++) {

                    int currentDraws = Math.min(width, smallestRow + steppingRow * i);
                    System.out.println(currentDraws);
                    if (i > this.nRows - trimmedRows - 1) {
                        currentDraws = (this.nRows - 1 - i) * steppingRow + 1;
                    }
                    int currentSpace = (width - currentDraws) / 2;

                    for (int j = 0; j < currentSpace; j++) {
                        Cell k = new Cell(CellState.FORBIDDEN, i, j);
                        this.boardMatrix[i][j] = k;
                    }
                    for (int j = currentSpace; j < currentSpace + currentDraws; j++) {
                        Cell k = new Cell(CellState.EMPTY, i, j);
                        this.boardMatrix[i][j] = k;

                        Cell lastCellInRow = this.boardMatrix[i][width - 1];
                        if (lastCellInRow != null && lastCellInRow.state != CellState.FORBIDDEN) {
                            lastCellInRow = new Cell((i % 2 == 0) ? CellState.EMPTY : CellState.FORBIDDEN,
                                    i, width - 1);
                            this.boardMatrix[i][j] = k;
                        }
                    }
                    for (int j = currentDraws + currentDraws; j < width; j++) {
                        Cell k = new Cell(CellState.FORBIDDEN, i, j);
                        this.boardMatrix[i][j] = k;
                    }
                }

            }
        }

        // Here board history is initialized
        this.boardHistory.add(this.boardMatrix);

    }

    /**
     * Parse CellState to a number corresponding to players
     */
    public String StringState(CellState state) {
        switch (state) {
            case FORBIDDEN -> {return " " + step;}
            case EMPTY ->     {return "0" + this.step;}
            case HIGHLIGHT -> {return "*" + step;}
            default -> {return state.ordinal() + 1 + step; } // for max. 6 players, RED as 1 etc.
        }
    }

    /**
     * Prints out the int[][] Board line by line (console board)
     */
     public void printBoard() {
         for (int i = 0; i < this.nRows; i++) {
             System.out.print((i % 2 == 1) ? sstep : "");
             for (int j = 0; j < this.width; j++) {
                 String stringState = StringState(this.boardMatrix[i][j].state); // print out
                 System.out.print(stringState);
             }
             System.out.println();
         }
         System.out.println();
     }

    /**
     * Checks is the piece would be placed outside the board
     * THE IMPLEMENTATION NEEDS TO BE ADJUSTED FOR 3/6 PLAYERS BOARD
     */
    private boolean checkWithinBoardNoOverlap(RealPiece piece) {
        for (int[] coords : piece.realCoords) {
            int row = coords[0];
            int col = coords[1];
            if (row < 0 || row >= nRows || col < 0 || col >= width) {
                System.out.println("!!! Exceeded board limits?");
                return false;
            }
            try {
                if (this.boardMatrix[row][col].state != CellState.EMPTY &&
                    this.boardMatrix[row][col].state != CellState.HIGHLIGHT) { // check overlapping with any color
                    System.out.println("!!! Overlapped?");
                    return false;
                }
            } catch (IndexOutOfBoundsException ignored) {;}
            // more cases needed for 3/6 players board
        }
        return true;
    }



    /**
     * Checks that the piece wouldn't be adjacent to earlier pieces of the same color
     */
    private boolean checkNoTouch(RealPiece piece) {
        for (int[] coords : piece.realCoords) {

            int row = coords[0];
            int col = coords[1];
            int sup2 = (row % 2 == 0) ? 0 : 1;

            CellState color = piece.color;

            int[][] neighbors = {
                    {row, col - 1}, {row, col + 1},                      // neighbors in the same row
                    {row - 1, col + sup2}, {row + 1, col + sup2},        // depends on if the row is even/uneven
                    {row - 1, col - 1 + sup2}, {row + 1, col - 1 + sup2} // neighbors one row above/below
            };

            for (int[] neighbor : neighbors) {
                try {
                    if (this.boardMatrix[neighbor[0]][neighbor[1]].state == color) {
                        return false;
                    }
                } catch (IndexOutOfBoundsException e) {
                    // Ignore out of bounds, they can't be touching
                }
            }
        }
        return true;
    }

    /**
     * Checks if there is a valid bridge between the new piece and the old ones of the same color
     */
    private boolean checkBridge(RealPiece piece) {
        for (int[] coords: piece.realCoords) {
            int row = coords[0];
            int col = coords[1];
            int sup2 = (row % 2 == 0) ? 0 : 1;


            CellState color = piece.color;

            int[][] bridges = {
                {row - 2, col}, {row + 2, col},                      // bridged straight up/down
                {row - 1, col - 2 + sup2}, {row + 1, col - 2 + sup2}, // bridges diagonally
                {row - 1, col + 1 + sup2}, {row + 1, col + 1 + sup2}, // depends on if the row is even/uneven
            };

            for (int[] bridge : bridges) {
                try {
                    if (this.boardMatrix[bridge[0]][bridge[1]].state == color) {
                        return true;
                    }
                } catch (IndexOutOfBoundsException e) {
                    // Ignore out of bounds, any bridge will suffice
                }
            }
        }
        return false;
    }

    /**
     * Placing a piece on the given coordinates or returning false for failing to do so
     * To place a piece correctly:
     * 1. Coordinates should not be out of bound, and they should refer to EMPTY cells only
     * 2. There should be a bridge between the old and new cells
     * 3. New piece should not touch old ones of the same color
     */
    public boolean place(Piece p, int r, int c, int angle) {

        RealPiece piece = new RealPiece(p, r-1, c-1, angle);

        if (!checkWithinBoardNoOverlap(piece)) {
            System.out.println("Piece cannot be placed out of the board or overlap with existing pieces!");
            return false;
        }

        // following rules apply only after the very first moves were played
        if (currentIter >= nPlayers) {
            if (piecesOnBoard.get(currentIter % nPlayers).contains(piece.origPiece)) {
                System.out.println("You've already used this piece");
                return false;
            }
            if (!checkNoTouch(piece)) {
                System.out.println("Pieces of the same color should not touch!");
                return false;
            }

            if (!checkBridge(piece)) {
                System.out.println("Each new piece should have a bridge to at least one of the same color!");
                return false;
            }
        }


        try {
            for (int[] coords : piece.realCoords) {
                this.boardMatrix[coords[0]][coords[1]].setState(piece.color);
            }
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Be careful when placing a piece! Probably out of bounds?");
            return false;
        }

        // update lists of pieces already used
        this.piecesOnBoard.get(currentIter % nPlayers).add(piece.origPiece);
        currentIter++;

        // update board
        this.boardHistory.add(this.boardMatrix);;
        return true; // the piece was successfully placed
    }


    /**
     * Counts how many cells of each color are present on the board
     */
    public int[] countScore() {
        return null;
    }

    /**
     * Outputs the winner based on the number of cells of each player
     */
    public CellState determineWinner() {
        int[] freq = new int[CellState.values().length];
        int pl = 0;
        for (ArrayList<Piece> piecesOfPlayer: piecesOnBoard) { // here each player's pieces are represented
            for (Piece piece: piecesOfPlayer) {
                freq[pl] += piece.size; // get the size of each peace
            }
            pl++;
            // count how many pieces of each player are on the board
        }

        int maxIndex = 0;
        int maxValue = freq[0];
        boolean isJigo = false;
        for (int i = 0; i < nPlayers; i++) {
            if (freq[i] > maxValue) {
                maxValue = freq[i];
                maxIndex = i;
            } else if (freq[i] == maxValue) {
                {
                    isJigo = true;
                }
            }
        }

        if (isJigo) {
            System.out.println("That is a Jigo!");
            return CellState.EMPTY;
        }
        return CellState.values()[maxIndex];

    }


    /**
     * Set the board to the previous state
     * UNDERIMPlEMENTED
     */
    public BoardInternal stepBack() {

        if (boardHistory.size() <= 1) {
            System.out.println(boardHistory.size());
            System.out.println("Nothing to undo");
            return this;
        }

        boardHistory.removeLast(); // remove last board state
        boardMatrix = boardHistory.getLast(); // revert board matrix to the previous state
        currentIter--;

        // Revert piecesOnBoard and piecesByPlayers
        int playerIndex = currentIter % nPlayers;
        if (!piecesOnBoard.get(playerIndex).isEmpty()) {
            // Remove the last piece placed by the current player
            Piece lastPiece = piecesOnBoard.get(playerIndex).removeLast();
            // Find the index of the last piece in AllPieces
            int pieceIndex = AllPieces.a.indexOf(lastPiece);
        }

        System.out.println("Successfully undone");
        return this;
    }

    public void clearHighlight() {
        for (int i = 0; i < this.nRows; i++) {
            for (int j = 0; j < this.width; j++) {
                if (this.boardMatrix[i][j].state == CellState.HIGHLIGHT) {
                    this.boardMatrix[i][j].setState(CellState.EMPTY);
                }
            }
        }
    }

    // Highlighting rings (while experimenting -- not for the game)
    /*public void highlightNeighbors() {
        for (RealPiece realpiece : piecesOnBoard) {
            int[][] neighbors = realpiece.ring2;
            for (int[] coords: neighbors) {
                int r = coords[0];
                int c = coords[1];
                if (r < this.nRows && c < this.width) {
                    Cell interested = this.boardMatrix[r][c];
                    if (interested.state.ordinal() > nPlayers) {
                        interested.setState(CellState.HIGHLIGHT);
                    }
                }
            }
        }
    }*/
}
