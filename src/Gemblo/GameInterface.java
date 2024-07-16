package Gemblo;

import javax.swing.*;
import java.util.Scanner;

/**
 * The main class for the Gemblo game.
 * Responsible for the interaciton with the user and the game logic.
 */
public class GameInterface {

    static int nPlayers = 2;              // set the number of players
    public static int currentIter = 0;   // keep track of whose turn it is

    // For a decent game for 2 players choose Board("sq", 12, 13, 2, 2) or similar
    // Type of boards: "sq" (for 2/4 players, rectangular), "r" (general round board),
    //                 "round" (for one specific small board), "3-6" (not implemented)
    static BoardInternal board = new BoardInternal("sq", 14, 15, 2, nPlayers);
    static IconBoards[] allIconBoards = new IconBoards[nPlayers];

    static Scanner in = new Scanner(System.in);
    static boolean GAME_FINISHED = false;      // status of the game

    private static HexagonBoard hexagonBoard;  // the main action board with buttons etc.


    /**
     * Asks for coordinates, piece type and rotation angle before those are entered correctly;
     * then places the pieces and updates the board and other relevant game variables
     */
    public static void move() {
        System.out.println("Enter your move (separated by \",\"): x, y, piece.ordinal, rotation degree: ");

        boolean piecePlaced = false;
        while (!piecePlaced) {
            try {
                String input = in.nextLine();
                String[] s = input.split(",");
                int[] dat = new int[]{
                    Integer.parseInt(s[0]),
                    Integer.parseInt(s[1]),
                    Integer.parseInt(s[2]),
                    Integer.parseInt(s[3])
                };

                // try placing the piece with parameters inputted
                Piece newPiece = AllPieces.getPiece(dat[2]-1).setColor(CellState.values()[currentIter % nPlayers]);
                RealPiece piece = new RealPiece(newPiece, dat[0]-1, dat[1]-1).rotate(dat[3]);
                if (board.place(newPiece, dat[0], dat[1], dat[3])) {
                     piecePlaced = true;
                     allIconBoards[currentIter % nPlayers].iconsMatrix.get(dat[2]-1).clearBoard(true);
                }

            } catch (Exception e) {
                System.out.println("Something went wrong! Enter your move carefully :)");
            }
        }
        // board.highlightNeighbors(); something wrong here -- only for experimenting
        hexagonBoard.iconBoards[currentIter % nPlayers].setStandard();
        board.printBoard();
        currentIter++;
        hexagonBoard.angles = new int[hexagonBoard.angles.length]; // reset the angles for the next player
        hexagonBoard.updateBoard(board, allIconBoards[currentIter % nPlayers]); // Update the graphical board
    }

    public static void undoLastMove() {
        board.stepBack();
        System.out.println("Undoing the last move");
    }

    public static void confirmMove() {
        // not implemented
        System.out.println("Move confirmed");
    }

    public static void finishGame() {
        GAME_FINISHED = true;
        System.out.println("Alright! Let's get to the score");
        System.out.println("...");
        System.out.println("...");
        System.out.println("The winner is Player Nr. " + board.StringState(board.determineWinner()));
        System.out.println("That was it, have a nice day!");
    }

    // disabled for now
    public static void placeByMouse(Piece piece, int r, int c) {
        System.out.println(piece.getCellState());
        board.place(piece, r, c, 0);
        board.printBoard();
        currentIter++;
        hexagonBoard.updateBoard(board, allIconBoards[currentIter % nPlayers]); // Update the graphical board
    }

    public static void turnIcon(Piece piece) {

        int pieceOrdinal = AllPieces.findPieceIndexByCoords(piece);
        SmallBoard relSmallBoard = allIconBoards[currentIter % nPlayers].iconsMatrix.get(pieceOrdinal);

        if (relSmallBoard.isDisplayed) {
            int newAngle = relSmallBoard.currentAngle += 1;

            relSmallBoard.clearBoard(true);
            relSmallBoard.place(piece, 2, 2, newAngle);
        }
    }

    public static void highlight(int r, int c) {
        board.clearHighlight();
        Piece onePiece = AllPieces.a.getFirst().setColor(CellState.HIGHLIGHT);
        board.place(onePiece, r, c, 0);
        board.printBoard();
        hexagonBoard.updateBoard(board, allIconBoards[currentIter % nPlayers]); // Update the graphical board

    }


    public static void main(String[] args) {
        // create as many IconBoards as there are players
        for (int i = 0; i < nPlayers; i++) {
            allIconBoards[i] = new IconBoards(i+1);
        }

        JFrame frame = new JFrame("Gemblo");
        hexagonBoard = new HexagonBoard(board, 21, allIconBoards, 8); // hex size of 25 is stadard
        frame.add(hexagonBoard);
        frame.setSize(1000, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

         System.out.println("Welcome to the Gemblo game! Let's start with a small board :)");
         board.printBoard();

         // Ask for move before game is finished
         while (!GAME_FINISHED) {
             System.out.println(STR."It is your turn, Player \{currentIter % nPlayers + 1}!. Place your piece");
             move();
         }


/*
         while (!GAME_FINISHED) {
             System.out.println("What's next?");
             System.out.println("Place a piece (0), see available pieces (1), set a move back (2), finish the game " +
                     "and determine the winner (3)");

             int input = -1;
             while (input < 0 || input > 3) {
                 try {
                     input = in.nextInt();
                 } catch (Exception e) {
                     System.out.println("Enter a number from 0 to 3");
                     in.next();
                     break;
                 }
             }

             switch (input) {
                 case 0:
                     move();
                     break;
                 case 1:
                     showPieces();
                     break;
                 case 2:
                     // not implemented
                     System.out.println("not yet");
                     break;
                 case 3:
                     GAME_FINISHED = true;
                     break;
                 default:
                     // should not happen
                     break;
             }
         }*/


    }
}
