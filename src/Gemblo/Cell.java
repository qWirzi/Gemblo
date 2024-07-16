package Gemblo;

/**
 * A class representing a single cell on the board
 */
public class Cell {

    // A Cell has a state (one of the 6 colors, EMPTY, FORBIDDEN, or HIGHLIGHT)
    // A Cell has markers (reference) to its place, i.e. x and y coordinates
    public CellState state;
    public int r;
    public int p;

    // constructor without specifying the coordinates
    public Cell() {
        this.state = CellState.EMPTY;
        this.r = 0;
        this.p = 0;
    }

    // Constructor for specifying both the CellState and the coordinates
    public Cell(CellState state, int r, int p) {
        this.r = r;
        this.p = p;
        this.state = state;
    }

    // Getter and Setter
    public CellState getState() { return this.state;}
    public CellState setState(CellState newState) { return this.state = newState;}

}
