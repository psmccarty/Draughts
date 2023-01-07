import javax.swing.JLabel;
import javax.swing.ImageIcon;
import java.awt.Color;
import javax.swing.border.Border;
import javax.swing.BorderFactory;
import java.util.ArrayList;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;


/**
 * Square objects represent a tile on the checkers book
 *
 * @author Patrick McCarty
 */
public class Square extends JLabel implements MouseListener{

	private boolean hasPiece;
	private boolean isQueen;
	private int player;
	public int row;
	public int col;

	/**
	 * Constructs a Square object
	 *
	 * @param tileCol color of the square
	 * @param hasPiece true if there is a piece on this tile
	 * @param pieceCol color of the piece on this square
	 * @param isQueen true is the piece on this tile is a queen
	 * @param player 1 for player 1 and 2 for player 2
	 * @param the row of this square on the board
	 * @param the column of this square on the board
	 */
	public Square(Color tileCol, boolean hasPiece, Color pieceCol, boolean isQueen, int player, int row, int col){
		super();
		
		this.hasPiece = hasPiece;
		this.isQueen = isQueen;
		this.player = player;
		this.row = row;
		this.col = col;

		this.addMouseListener(this);
		this.setBorder(BorderFactory.createLineBorder(new Color(0xffd700), 1));
		if(hasPiece && player == 1){
			this.setIcon(new ImageIcon("improvedRedPiece.png"));
		}
		if(hasPiece && player == 2){
			this.setIcon(new ImageIcon("improvedBlackPiece.png"));
		}
		this.setHorizontalAlignment(JLabel.CENTER);
		this.setBackground(tileCol);
		this.setOpaque(true);
	}

	public boolean getHasPiece(){
		return this.hasPiece;	
	}

	public boolean getIsQueen(){
		return this.isQueen;	
	}

	/**
	 * List the moves available to this Square
	 */
	public ArrayList<Square> moves(CheckersBoard cb){
		if(this.hasPiece == false){
			return null;	
		}	

		ArrayList<Square> potentialMoves = new ArrayList<Square>();	

		if(this.player == 1){ // piece belongs to player 1
			if(!this.isQueen){ // piece is not a queen
				if(!cb.getSquare(this.row - 1, this.col - 1).hasPiece){ // square at top left is open
					potentialMoves.add(cb.getSquare(this.row - 1, this.col - 1));	
				} else{
				
				}
				if(!cb.getSquare(this.row - 1, this.col + 1).hasPiece){ // square at top right is open
					potentialMoves.add(cb.getSquare(this.row - 1, this.col + 1));	
				} else{
				
				}
			}

		
		} else{ // piece belongs to player 2
			if(!this.isQueen){ // piece is not a queen
				if(!cb.getSquare(this.row + 1, this.col - 1).hasPiece){ // square at bottom left is open
					potentialMoves.add(cb.getSquare(this.row + 1, this.col - 1));
				} else{
				
				}
				if(!cb.getSquare(this.row + 1, this.col + 1).hasPiece){ // square at bottom right is open
					potentialMoves.add(cb.getSquare(this.row + 1, this.col + 1));
				} else{
				
				}
			}
		}

		return potentialMoves.size() == 0 ? null : potentialMoves;

	}


	public void mouseClicked(MouseEvent e){

	}

	public void mousePressed(MouseEvent e){
		System.out.printf("Square(%d, %d) pressed\n", this.row, this.col);	
	}


	public void mouseReleased(MouseEvent e){
	
	}


	public void mouseEntered(MouseEvent e){
	
	}

	public void mouseExited(MouseEvent e){
	
	}




}


