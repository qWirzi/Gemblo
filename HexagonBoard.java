package Gemblo;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

/**
 * The main GUI prat of the project, showing the internal matrix as colorful hexagons
 * and showing the panel with ever-updating available pieces
 */
public class HexagonBoard extends JPanel {
    private BoardInternal board;
    private final int hexSize;
    public IconBoards[] iconBoards;
    private final int smallHexSize;
    private int selectedRow = -1; // selected row by mouse click
    private int selectedCol = -1; // selected column by mouse click
    private ArrayList<Hexagon> allIconCorners = new ArrayList<>();
    public int[] angles;
/*
    private ArrayList<int> anglesIcons = new ;
*/

    /**
     *
     * @param board -- internal matrix representing board state
     * @param hexSize -- how big should the main board hexagons be (e.g. 25 pixels)
     * @param iconBoards -- the IconBoard object representing small boards with available pieces
     * @param smallHexSize -- how big should the small hexagons be (e.g. 8 pixels)
     */
    public HexagonBoard(BoardInternal board, int hexSize, IconBoards[] iconBoards, int smallHexSize) {
        this.board = board;
        this.hexSize = hexSize;
        this.iconBoards = iconBoards;
        this.smallHexSize = smallHexSize;
        this.angles = new int[iconBoards[0].iconsMatrix.size()];

        // Set the layout to null to place components manually
        setLayout(null);

        // Create and add buttons
        JButton undoButton = new JButton("Undo");
        JButton finishButton = new JButton("Finish");
        JButton confirmButton = new JButton("Confirm move");
        undoButton.setBounds(800, 10, 120, 30);   // Set position and size of the button1
        finishButton.setBounds(640, 10, 120, 30); // Set position and size of the button2
        confirmButton.setBounds(710, 500, 120, 30); // Set position and size of the button3
        add(undoButton);
        add(finishButton);
        add(confirmButton);
        confirmButton.setEnabled(false); // not fully implemented yet
        undoButton.setEnabled(false); // not fully implemented yet

        // Add buttons for undoing and finishing the game, and for confirming the move
        undoButton.addActionListener(e -> {
            GameInterface.undoLastMove();
        });

        finishButton.addActionListener(e -> {
            GameInterface.finishGame();
        });

        confirmButton.addActionListener(e -> {
            GameInterface.confirmMove();
        });

        // Add MouseListener to detect clicks on IconBoards
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                handleClickOnBoard(e.getX(), e.getY());
                handleClickOnIconBoard(e.getX(), e.getY());
            }
        });
    }

    public void updateBoard(BoardInternal board, IconBoards iconBoards) {
        // this.angles = angles; implement private internal changes later
        this.board = board;
        this.iconBoards[getCurrentPlayer()] = iconBoards;
        repaint();
    }

    public void updateBoard(BoardInternal board, IconBoards[] iconBoards) {
        // this.angles = angles; implement private internal changes later
        this.board = board;
        for (int i = 0; i < iconBoards.length; i++) {
            this.iconBoards[i] = iconBoards[i];
        }
        repaint();
    }

    private void handleClickOnBoard(int mouseX, int mouseY) {
        // Calculate the clicked cell's coordinates (row and col)
        int row = (int) ((mouseY - (hexSize * Math.sqrt(3))) / (hexSize * 1.5)) + 1;
        int col = (int) ((mouseX - (hexSize * Math.sqrt(3))) / (hexSize * Math.sqrt(3))) + 1;
        if (row % 2 == 1) {
            col = (int) ((mouseX - (hexSize * Math.sqrt(3) / 2)) / (hexSize * Math.sqrt(3)));
        }

        // Check if the coordinates are within the board bounds
        if (row >= 0 && row < board.getNRows() && col >= 0 && col < board.getWidth()) {
            // Save the selected coordinates
            selectedRow = row;
            selectedCol = col;
            GameInterface.highlight(row, col);
            updateBoard(board, iconBoards); // Update the board's visual representation
            repaint();
        }
    }

    /**
     * Handle click on IconBoard
     */
    private void handleClickOnIconBoard(int mouseX, int mouseY) {
        // Calculate the bounds for each IconBoard and check if the click is within any of these bounds
        for (int i = 0; i < iconBoards[0].iconsMatrix.size(); i++) {
            double xOffset = 620 + (i % 3) * smallHexSize * Math.sqrt(3) * 7;
            double yOffset = 60 + (int) (i / 3) * smallHexSize * 9;
            double iconWidth = smallHexSize * Math.sqrt(3) * 7;
            double iconHeight = smallHexSize * 9;

            // Check if the click is within the bounds of the current IconBoard
            if (mouseX >= xOffset && mouseX <= xOffset + iconWidth &&
                    mouseY >= yOffset && mouseY <= yOffset + iconHeight) {
                IconBoards currentBoard = iconBoards[GameInterface.currentIter % GameInterface.nPlayers];
                Piece currentPiece = currentBoard.iconsMatrix.get(i).assignedPiece;
                GameInterface.turnIcon(currentPiece);

                angles[i]++;

                // Update the Hexagon instances with the new angle
                allIconCorners.get(i).setAngle(angles[i]);

                updateBoard(board, iconBoards); // Update the board's visual representation
                repaint(); // Update the board's visual representation

                // later for selecting piece by clickign -- check if a cell has been selected
                /* if (selectedRow != -1 && selectedCol != -1) { // Ensure a cell has been selected
                    System.out.println("Clicked on IconBoard " + (i + 1));
                    GameInterface.placeByMouse(iconBoards.iconsMatrix.get(i).assignedPiece, selectedRow, selectedCol);
                    selectedRow = -1; // Reset selection for the next move
                    selectedCol = -1;
                    updateBoard(board, iconBoards); // Update the board's visual representation
                    repaint(); // Update the board's visual representation
                    break; // Exit the loop once the clicked IconBoard is found and handled
                }*/
            }
        }
    }

    /**
     * Set how the different cell states should be displayed
     */
    private Color getColorFromCellState(CellState state) {
        return switch (state) {
            case EMPTY -> Color.WHITE;
            case FORBIDDEN -> Color.BLACK;
            case RED -> Color.RED;
            case YELLOW -> Color.ORANGE;
            case GREEN -> Color.GREEN;
            case BLUE -> Color.BLUE;
            case PURPLE -> Color.MAGENTA;
            case BLACK -> Color.BLACK;
            case HIGHLIGHT -> Color.PINK;
            default -> Color.GRAY;
        };
    }

    /**
     * Methode for painting a general board
     */
    public void paintBoard(Graphics2D g2, int hexSize, BoardInternal board, double startX, double startY,
                           boolean drawText, int index, int angle) {
        int nRows = board.getNRows();
        int longestRow = board.getWidth();

        double xOffset = hexSize * Math.sqrt(3); // because k = side * sqrt(3) / 2, we need 2*k
        double yOffset = hexSize * (Math.sqrt(3) - 0.3);

        for (int i = 0; i < nRows; i++) {
            for (int j = 0; j < longestRow; j++) {
                Cell cell = board.boardMatrix[i][j];
                if (cell != null && cell.state != CellState.FORBIDDEN) {
                    double x = startX + j * xOffset + (i % 2) * (xOffset / 2);
                    double y = startY + i * yOffset;
                    Color color = getColorFromCellState(cell.state);
                    Hexagon hex = new Hexagon(x, y, hexSize, color, i + 1, j + 1, angle);
                    hex.draw(g2);

                    if (drawText) {
                        hex.drawText(g2);
                    } else {
                        if (i == 2 && j == 2) {
                            hex.drawDot(g2);
                        }
                    }

                }
            }
        }

        if (!drawText) {
            Hexagon hexUpLeft = new Hexagon(startX, startY, hexSize, Color.LIGHT_GRAY, 1, 1, angle);
            hexUpLeft.draw(g2);
            hexUpLeft.drawIndex(g2, Integer.toString(index));
            Hexagon hexUpRight = new Hexagon(startX + 4 * xOffset, startY, hexSize, Color.LIGHT_GRAY, 5, 5, angle);
            allIconCorners.add(hexUpRight);
            hexUpRight.drawAngle(g2);
        }

    }



    @Override
    /**
     * Paint the big main board and the board icons displaying available pieces
     */
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        paintBoard(g2, hexSize, board, hexSize * Math.sqrt(3), hexSize * Math.sqrt(3),
                true, -1, -1);

        for (int i = 0; i < iconBoards[getCurrentPlayer()].iconsMatrix.size(); i++) {
            double xOffset = 620 + (i % 3) * smallHexSize * Math.sqrt(3) * 7;
            double yOffset = 60 + (int) (i / 3) * smallHexSize * 9;
            // double xOffset = 620 + smallHexSize * Math.sqrt(3) * 3.5 * (2 * (i % 3) + (i / 3) % 2); another way
            // double yOffset = 60 + (int) (i / 3) * smallHexSize * 6;
            paintBoard(g2, smallHexSize, iconBoards[getCurrentPlayer()].iconsMatrix.get(i), xOffset, yOffset,
                    false, i+1, angles[i] % 6);
        }
    }

    public int getCurrentPlayer() {
        return GameInterface.currentIter % GameInterface.nPlayers;
    }

    public int getCurrentPlayerPlusOne() {
        return (GameInterface.currentIter + 1) % GameInterface.nPlayers;
    }
}
