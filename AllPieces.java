/**
 * Later: Option to create new pieces for the game for users
 */

package Gemblo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class AllPieces {

    /**
    All standard 18 pieces of the game
     0,0 should necessarily be the first coordinate - but there can be negative numbers here
     The first coordinate pair marks the rotation center
     */
    static Piece p1 = new Piece(new int[][] {{0, 0}});
    static Piece p2 = new Piece(new int[][] {{0, 0}, {0, 1}});
    static Piece p3fat = new Piece(new int[][] {{0, 0}, {0, 1}, {1, 0}});
    static Piece p3curve = new Piece(new int[][] {{0, 0}, {0, -1}, {1, 0}});
    static Piece p3line = new Piece(new int[][] {{0, 0}, {0, -1}, {0, 1}});
    static Piece p4wind = new Piece(new int[][] {{0, 0}, {0, 1}, {-1, -1}, {1, -1}});
    static Piece p4line = new Piece(new int[][] {{0, 0}, {0, -1}, {0, 1}, {0, 2}});
    static Piece p4romb = new Piece(new int[][] {{0, 0}, {0, 1}, {1, 0}, {1, 1}});
    static Piece p4zig = new Piece(new int[][] {{0, 0}, {0, -1}, {1, 0}, {1, 1}});
    static Piece p4hug = new Piece(new int[][] {{0, 0}, {0, -1}, {1, 0}, {2, 0}});
    static Piece p5line = new Piece(new int[][] {{0, 0}, {0, -1}, {0, -2}, {0, 1}, {0, 2}});
    static Piece p5vi = new Piece(new int[][] {{0, 0}, {0, 1}, {0, 2}, {1, 0}, {2, 1}});
    static Piece p5zig = new Piece(new int[][] {{0, 0}, {-1, -1}, {-1, -2}, {0, 1}, {1, 1}});
    static Piece p5strange = new Piece(new int[][] {{0, 0}, {0, 1}, {-1, -1}, {-1, -2}, {1, -1}});
    static Piece p5wing = new Piece(new int[][] {{0, 0}, {0, -1}, {-1, -2}, {0, 1}, {1, 1}});
    static Piece p5brokenLine = new Piece(new int[][] {{0, 0}, {0, -1}, {0, -2}, {1, 0}, {1, 1}});
    static Piece p5flower = new Piece(new int[][] {{0, 0}, {-1, -1}, {0, 1}, {0, 2}, {1, -1}});
    static Piece p5bumerang = new Piece(new int[][] {{0, 0}, {0, -1}, {0, -2}, {1, 0}, {2, 1}});
    // static Piece p6line = new Piece(new int[][] {{0, 0}, {1, 0}, {2, 1}, {3, 1}, {4, 2}, {5, 2}});

    // All pieces in a list for easier access
    public static ArrayList<Piece> a = new ArrayList<>();
    static {
        Collections.addAll(a, p1, p2, p3fat, p3curve, p3line,
                p4wind, p4line, p4romb, p4zig, p4hug,
                p5line, p5vi, p5zig, p5strange, p5wing, p5brokenLine, p5flower,p5bumerang);
    }

    // Get a piece at a specific index for valid indexes
    public static Piece getPiece(int index) {
        if (index >= 0 && index < a.size()) {
            return a.get(index);
        } else {
            throw new IndexOutOfBoundsException("Invalid index: " + index);
        }
    }

    public static int findPieceIndexByCoords(Piece targetPiece) {
    for (int i = 0; i < AllPieces.a.size(); i++) {
        Piece currentPiece = AllPieces.a.get(i);
        if (Arrays.deepEquals(currentPiece.coords, targetPiece.coords)) {
            return i;
        }
    }
    return 8; // Piece not found
}

    // there are some pieces that would be better copied when flipped -- to implement one day
    // cause there are 98 distinct quasi-pieces in total!

}
