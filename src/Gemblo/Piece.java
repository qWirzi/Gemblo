package Gemblo;

/**
 * A simple Piece which consists of coordinates for each cell
 */
public class Piece {

    /**
     * Each piece has its coordinates as int[][], size (number of individual cells), and color it is assigned to
     */
    public int[][] coords;     // coordinates of all pieces
    public int size;           // how many cells a piece has
    public CellState color;    // assigned player to the piece

    /**
     * Getters and Setters
     */
    public int[][] getCoords() { return this.coords; }
    public CellState getCellState() {return this.color; }
    // Update color of a piece
    public Piece setColor(CellState color) {
        // create a new piece with the same coordinates but different color
        // Piece newPiece = new Piece(this.coords);
        this.color = color;
        return this;
    }


    /**
     * Constructor initializing a Piece with given int[][] coordinates
     */
    public Piece(int[][] coords) {
        this.coords = coords;
        this.size = coords.length;
    }

/*
    // Methode for creating new piece
    public void addCell(Cell c) {
        int nCells = this.coords.length;
        int[][] newCoords = new int[nCells+1][];
        System.arraycopy(this.coords, 0, newCoords, 0, nCells);
    }
*/

}
