package Gemblo;

import java.util.ArrayList;

/**
 * Class representing all small boards with available pieces together -- created anew for each color
 * There are 18 small boards in a standard Gemblo game, which are then shown in the GUI
 */
public class IconBoards {

    public ArrayList<SmallBoard> iconsMatrix;
    private final int NrPlayer;

    public IconBoards(int NrPlayer) {
        // Make sure that iconsMatrix is specific to the current IconBoards object.
        // Currently, it is shared among all IconBoards objects.

        this.iconsMatrix = new ArrayList<>();
        this.NrPlayer = NrPlayer;

        for (int i = 0; i < AllPieces.a.size(); i++) {
            SmallBoard oneSmallBoard = new SmallBoard();
            Piece piece = new Piece(AllPieces.getPiece(i).coords).setColor(CellState.values()[NrPlayer-1]);
            oneSmallBoard.place(piece,1, 1, 0);
            iconsMatrix.add(oneSmallBoard);
        }

    }

    public void setStandard() {
        for (SmallBoard sBoard : iconsMatrix) {
            sBoard.setStandard();
        }
    }

    //
    // another update method is used currently
    //
    public void update(ArrayList<ArrayList<RealPiece>> placed) {

        if (placed == null) {
            return;
        }

        for (SmallBoard sBoard : iconsMatrix) {
            if (placed.get(NrPlayer).contains(sBoard.assignedPiece)) {
                sBoard.clearBoard(true);
            }
        }
    }

}
