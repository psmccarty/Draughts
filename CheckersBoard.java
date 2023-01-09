import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Graphics;
import java.awt.Dimension;
import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.ImageIcon;

import java.util.ArrayList;


/**
 * Draws a checkers board
 *
 * @author Patrick McCarty
 *
 */
public class CheckersBoard extends JFrame{

	private Square[][] squares;						// Contains the tiles of the game board
	private ArrayList<Square> highlighted;					// List of currently highlighted squares
	private static final String ICONPATH = "images/checkersIcon.png";	// Path to the icon of the app

	public CheckersBoard(){
		this.squares = new Square[GameLogic.ROWS][GameLogic.COLS];
		this.highlighted = new ArrayList<Square>();

		this.setTitle("Checkers");
		ImageIcon image = new ImageIcon(ICONPATH);
		this.setIconImage(image.getImage());
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(true);

		JPanel panel = new JPanel();
		panel.setLayout(new GridBagLayout());

		GridBagConstraints gbc = new GridBagConstraints();

		this.setMinimumSize(new Dimension(990, 990));

		for(int i = 0; i < GameLogic.ROWS; i++){
			for(int j = 0; j < GameLogic.COLS; j++){
				if(i % 2 == j % 2){ // red squares, no pieces can enter
					squares[i][j] = new Square(Color.red, "none", "none", i, j);
				} else{ // black squares, pieces can reside here
					if(i <= 2){ // squares contain pawns of player 2
						squares[i][j] = new Square(Color.black, "pawn", "player2", i, j);	
					} else if(i >= 5){ // squares contain pawns of player 1
						squares[i][j] = new Square(Color.black, "pawn", "player1", i, j);	
					} else{ // squares are initially empty
						squares[i][j] = new Square(Color.black, "none", "none", i, j);	
					}
				}
				squares[i][j].setPreferredSize(new Dimension(90, 90));
				gbc.gridx = j;
				gbc.gridy = i;
				panel.add(squares[i][j], gbc);
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
	public Square[][] getSquares(){
		return this.squares;	
	}

	/**
	 * Get squares that are currently highlighted on this board
	 *
	 * @return squares currently highlighted on this board
	 */
	public ArrayList<Square> getHighlighted(){
		return this.highlighted;	
	}

		

}
