import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.ImageIcon;

import java.util.ArrayList;

/**
 * Draws a checkers board
 *
 * @author Patrick McCarty
 *
 */
public class CheckersBoard extends JFrame {

    // Contains the tiles of the game board
    private Square[][] squares;

    // List of currently highlighted squares
    private ArrayList<Square> highlighted;

    // List of squares where there are pieces belonging to player1
    private ArrayList<Square> p1Pieces;
    
    // List of squares where there are pieces belonging to player2
    private ArrayList<Square> p2Pieces;
    
    // Path to the icon of the app
    private static final String ICONPATH = "images/checkersIcon.png"; 
    
    // Pane where all the GUI elements reside
    private JLayeredPane pane;

    private static final String TROPHYPATH = "images/trophy.png";

    /**
     * Creates a checkersboard and initializes all the squares on it so it is
     * ready to be played on
     */
    public CheckersBoard() {
        this.squares = new Square[GameLogic.ROWS][GameLogic.COLS];
        this.highlighted = new ArrayList<Square>();
        this.p1Pieces = new ArrayList<Square>();
        this.p2Pieces = new ArrayList<Square>();

        this.setTitle("Draughts");
        ImageIcon image = new ImageIcon(ICONPATH);
        this.setIconImage(image.getImage());
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(true);

        // JPanel panel = new JPanel();
        JLayeredPane panel = new JLayeredPane();
        this.pane = panel;
        panel.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();

        this.setMinimumSize(new Dimension(990, 990));

        for (int i = 0; i < GameLogic.ROWS; i++) {
            for (int j = 0; j < GameLogic.COLS; j++) {
                if (i % 2 == j % 2) { // red squares, no pieces can enter
                    squares[i][j] = new Square(Color.red, "none", "none", i, j);
                } else { // black squares, pieces can reside here
                    if (i <= 2) { // squares contain pawns of player 2
                        squares[i][j] = new Square(Color.black, "pawn", "player2", i, j);
                        this.p2Pieces.add(squares[i][j]);
                    } else if (i >= 5) { // squares contain pawns of player 1
                        squares[i][j] = new Square(Color.black, "pawn", "player1", i, j);
                        this.p1Pieces.add(squares[i][j]);
                    } else { // squares are initially empty
                        squares[i][j] = new Square(Color.black, "none", "none", i, j);
                    }
                }
                squares[i][j].setPreferredSize(new Dimension(90, 90));
                gbc.gridx = j;
                gbc.gridy = i;
                panel.add(squares[i][j], gbc, 0);
            }
        }
        this.add(panel);
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.setVisible(true);
    }

    /**
     * Get all squares that are in this board
     *
     * @return squares of this board
     */
    public Square[][] getSquares() {
        return this.squares;
    }

    /**
     * Get squares that are currently highlighted on this board
     *
     * @return squares currently highlighted on this board
     */
    public ArrayList<Square> getHighlighted() {
        return this.highlighted;
    }

    /**
     * Get the list of squares where a given player has pieces
     *
     * @param player the player whose squares we want to get
     *               return the list of squares where the player has pieces
     */
    public ArrayList<Square> getPlayerPieces(String player) {
        return player.equals("player1") ? this.p1Pieces : this.p2Pieces;
    }

    /**
     * Display a message to the screen and have a win page
     *
     * @param message message to be displayed to the screen
     */
    public void interruptMessage(String message) {

        JLabel lab = new JLabel(message);
        lab.setHorizontalAlignment(JLabel.CENTER);
        lab.setIcon(new ImageIcon(TROPHYPATH));
        lab.setFont(new Font("Arial", Font.PLAIN, 40));
        lab.setBackground(new Color(0x966F33));
        lab.setHorizontalTextPosition(JLabel.CENTER);
        lab.setVerticalTextPosition(JLabel.TOP);
        lab.setOpaque(true);
        lab.setPreferredSize(new Dimension(500, 500));
        this.remove(pane);
        this.add(lab);
    }
}
