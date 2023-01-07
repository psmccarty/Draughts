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


/**
 * Draws a checkers board
 *
 * @author Patrick McCarty
 *
 */
public class CheckersBoard extends JFrame{

	public Square[][] squares;

	private final int ROWS = 8;
	private final int COLS = 8;

	public CheckersBoard(){
		this.squares = new Square[ROWS][COLS];

		this.setTitle("Checkers");
		ImageIcon image = new ImageIcon("checkersIcon.png");
		this.setIconImage(image.getImage());
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(true);

		JPanel panel = new JPanel();
		panel.setLayout(new GridBagLayout());

		GridBagConstraints gbc = new GridBagConstraints();

		this.setMinimumSize(new Dimension(990, 990));


		for(int i = 0; i < ROWS; i++){
			for(int j = 0; j < COLS; j++){
				if(i % 2 == j % 2){
					squares[i][j] = new Square(Color.red, false, null, false, 0, i, j);
				} else{
					if(i <= 2){
						squares[i][j] = new Square(Color.black, true, Color.red, false, 2, i, j);	
					} else if(i >= 5){
						squares[i][j] = new Square(Color.black, true, Color.black, false, 1, i, j);	
					} else{
						squares[i][j] = new Square(Color.black, false, null, false, 0, i, j);	
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
	 * Return a Square object at a specified row and column
	 *
	 * @param i row of desired Square
	 * @param j col of desired Square
	 * @return the desired Square
	 */
	public Square getSquare(int i, int j){
		return this.squares[i][j];	
	}

	

}
