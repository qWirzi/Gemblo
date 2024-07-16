package Gemblo;

/**
 * CellState is one of:
 * - one of the 6 game colours
 * - an empty cell
 * - a cell lying outside the board that is needed for internal representation only
 *                                                      (for non-rectangular boards)
 * - a highlighter which does not represent any player but marks relevant cells
 */
public enum CellState {

    // max. 6 players allowed, always listed above in enum
    RED,
    YELLOW,
    GREEN,
    BLUE,
    PURPLE,
    BLACK,

    // empty and invisible cells
    EMPTY,
    FORBIDDEN,

    // special state which can be overridden by players
    HIGHLIGHT
}
