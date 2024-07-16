# Gemblo
A first try (tbu) of simulating the Gemblo board game in Java
(this is not a proper README - a full version comes out one day later)

****************************************************************************************
1. Declaration of properties and adhering the fair-code codex
****************************************************************************************
(blended out)
Virzhiniia Frejlak, 14.07.2024



****************************************************************************************
2. What the game is about - in a nutshell
****************************************************************************************
Representation of a board game called Gemblo (with yet no internet analog). The game is played on a hexagonal board
which makes us the main challenge when trying to represent the board and print it out

Possible to place pieces on the board according to the rules. There is a fixed number of pieces for each player,
and multiple options are important when making a move (where to place, what to place, what is the rotation angle).
After setting the number of the players internally, the colors of the player on turn are updated automatically.
It is possible to play the game for 1-6 players by typing the move data each time in the console.


****************************************************************************************
3. To the structure of the code
****************************************************************************************
GameInterface is the main class that starts and runs the game. BoardInternal is initialized and used as the ground
matrix for the game.

GameInterface is supported by the graphical component HexagonBoard, which is a JPanel subclass and is responsible for
 drawing the main board, buttons, and available pieces. It also handles mouse events in theory, but I did not fully
 success with the implementation so the buttons are disabled, and it is recommended to play the game in the console.

 Hexagon is then a class that represents a single hexagon on the board. It is used for drawing the individual hexagons.

 AllPieces has all standard pieces of a Gemblo game and defined center at them.

 Piece is a simple Piece class
 Exteneded piece class is RealPiece which has complete coordinates and an option to rotate the piece

 BoardInternal is the big hurdle of the project and is dealing with internal representations of the board, checking
 if placing a piece is allowed: not out of board, not already used, not already occupied, there is a bridge, doesn't
 touch pieces of its color etc. It also has a method to print out the board in the console.

 ... to complete the list...

****************************************************************************************
4. Current functionalities
****************************************************************************************
- Start the game
(setting the number of players, board type is parameters is possible internally for simplicity of the application)
(board sizes can be very different, board type too -- all not easy tasks. There is an internal representation of
"forbidden" cells that are not printed out)
- In a console, write the coordinates of a piece, the piece type, and the angle to rotate it
- There are reappearing messages to support the game flow
- If a piece was placed incorrectly, an error is shown (comments are also error-appropriate) but the game is
not interrupted
- The current board state is printed out as a matrix in console but also has a GUI which is shown as a 800x1000 window
- Both the console and the GUI representations are updated after each move
- Piece's availability is checked and updated
- Still available pieced are shown for each color on the right of the GUI
- the coordinates of each Cell are also shown in GUI
- Clicking button Finish will end the game and show the winner
- many many more internal functions

****************************************************************************************
5. Missing functionalities and further ideas
****************************************************************************************
- MouseListener is not fully implemented and disabled in this version. Ideally, pieces can be selected by clicking
- There is one additional rule for placing pieces in the original Gemblo game which is not implemented here
(i.e. bridge should be of a special type -- not as crucially important for the project)
- Some rotations are still weird -- a bug that appeared a few hours ago and I didn't manage to fix it yet
- still many more small stuff that can be implemented -- some are in comments. Ah, and more comments could be good:)

