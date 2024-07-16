package Gemblo;

import java.util.Arrays;

/**
 * Subclass of Piece with refined coordinates and a possibility to rotate the piece around the center
 * Has a primary usage for the game
 */
public class RealPiece extends Piece {

    public Piece origPiece;
    public int[][] ringTouch;
    public int[][] ring2;
    public int[][] ringBridge;

    // maybe one day it makes sense to implement more rings
    public int[][] ring3;
    public int[][] ring4;
    public int[][] ring3B1;
    public int[][] ring3B2;
    public int[][] ring4B1;
    public int[][] ring4B2;
    public int[][] ring4B3;

    public int[][] realCoords; // MAKE THIS TO NEW
    public CellState color;

    /**
     * Sets the real coordinates of the piece
     */
    public void setRealCoords(int r, int c) {
        for (int i = 0; i < size; i++) {
            int sup = (r % 2 == 1 && ((this.coords[i][0] + 2) % 2 == 1)) ? 1 : 0;
            this.realCoords[i] = new int[]{this.coords[i][0] + r, this.coords[i][1] + c + sup};
        }         // int sup2 = (row % 2 == 0) ? 0 : 1;
    }

    /**
     * Constructor initializing a Piece with given int[][] coordinates
     * and getting rings that are needed for rotating later
     */
    public RealPiece(Piece p, int r, int c) {
        super(p.coords);
        this.origPiece = p;
        this.color = p.color;
        this.realCoords = new int[size][];
        setRealCoords(r, c);
        setRings();
    }

    /**
     * Constructor initializing a Piece with given int[][] coordinates
     * and getting rings that are needed for rotating later -- now rotating right away
     */
    public RealPiece(Piece p, int r, int c, int angle) {
        super(p.coords);
        this.origPiece = p;
        this.color = p.color;
        this.realCoords = new int[size][];
        setRealCoords(r, c);
        setRings();
        rotate(angle);
    }

    /**
     * Give a ring which is horizontally aligned with the center
     * and has a distance of d from it
     * @param d distance from the center
     * @return int[][] coordinates of a ring for a given distance
     */
    private int[][] cleanRing(int d) {
        int r0 = this.realCoords[0][0];
        int c0 = this.realCoords[0][1];
        int ds = -1 - (d-1) / 2;
        int sup2 = (r0 % 2 == 1 && d % 2 == 1) ? 1 : 0;

        int[][] ring = new int[][]{
                    {r0, c0 - d},
                    {r0 - d, c0 + ds + sup2},
                    {r0 - d, c0 + ds + d + sup2},
                    {r0, c0 + d},
                    {r0 + d, c0 + ds + d + sup2},
                    {r0 + d, c0 + ds + sup2}};
        return ring;
    }



    /**
     * Sets the rings around the turning point
     */
    private void setRings() {
        int r0 = this.realCoords[0][0];
        int c0 = this.realCoords[0][1];

        int sup2 = (r0 % 2 == 0) ? 0 : 1;

        this.ringBridge = new int[][]{
                {r0 - 1, c0 - 2 + sup2},
                {r0 - 2, c0},
                {r0 - 1, c0 + 1 + sup2},
                {r0 + 1, c0 + 1 + sup2},
                {r0 + 2, c0},
                {r0 + 1, c0 - 2 + sup2}};
        this.ringTouch = this.cleanRing(1);
        this.ring2 = this.cleanRing(2);

    }


    /**
     * Rotates the pieces and thus updates the full coordinates
     * @param angle rotation angle, significantly number from 0 to 5
     * @return RealPiece with updated coordinates
     */
    public RealPiece rotate(int angle) {
        int[][][] rings = {ringTouch, ringBridge, ring2};

        for (int i = 1; i < size; i++) {
            for (int[][] ring : rings) {
                for (int j = 0; j < 6; j++) {
                    if (Arrays.equals(realCoords[i], ring[j])) {
                        realCoords[i] = ring[(j + angle) % 6];
                        break;
                    }
                }
            }
        }
        return this;
    }


}

