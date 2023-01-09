import javax.swing.JLabel;
import javax.swing.ImageIcon;
import java.awt.Color;
import java.awt.Component;
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

	private Color tileCol;
	private String piece;
	private String player;
	private int row;
	private int col;

	private static final Color BORDERCOLOR = new Color(0xffd700); // border color for all squares
	private static final String REDPAWNIMGPATH = "images/rp.png";
	private static final String LAVPAWNIMGPATH = "images/lp.png";
	private static final String LAVQUEENIMGPATH = "images/lq.png";
	private static final String REDQUEENIMGPATH = "images/rq.png";
	
	
	/**
	 * Constructs a Square object
	 *
	 * @param tileCol color of the square
	 * @param piece the piece on this tile or "none" if empty 
	 * @param pieceCol color of the piece on this square
	 * @param player 1 for player 1 and 2 for player 2
	 * @param the row of this square on the board
	 * @param the column of this square on the board
	 */
	public Square(Color tileCol, String piece, String player, int row, int col){
		super();
	
		this.tileCol = tileCol;	
		this.piece = piece;
		this.player = player;
		this.row = row;
		this.col = col;

		this.addMouseListener(this);
		this.setBorder(BorderFactory.createLineBorder(BORDERCOLOR, 1));

		this.setHorizontalAlignment(JLabel.CENTER);
		this.setBackground(tileCol);
		this.setOpaque(true);

		// set the image of the piece according to its arguments
		if(piece.equals("pawn") && player.equals("player1")){
			this.setIcon(new ImageIcon(REDPAWNIMGPATH));
		} else if(piece.equals("pawn") && player.equals("player2")){
			this.setIcon(new ImageIcon(LAVPAWNIMGPATH));	
		} else if(piece.equals("queen") && player.equals("player1")){
			this.setIcon(new ImageIcon(REDQUEENIMGPATH));
		} else if(piece.equals("queen") && player.equals("player2")){
			this.setIcon(new ImageIcon(LAVQUEENIMGPATH));	
		}

	}

	/**
	 * Get the color of the tile
	 *
	 * @return Color of this tile
	 */
	public Color getTileCol(){
		return this.tileCol;	
	}

	/**
	 * Get the piece on this tile if one exists
	 *
	 * @return "pawn", "queen" or "none"
	 */
	public String getPiece(){
		return this.piece;	
	}

	/**
	 * Get the player who owns the piece on this tile if one exists 
	 *
	 * @return "player1", "player2", "none"
	 */
	public String getPlayer(){
		return this.player;	
	}

	/**
	 * Get the coordinates of this piece on the checkers board
	 *
	 * @return row and column of piece on board
	 */
	public int[] getCoords(){
		int[] coords = new int[2];
		coords[0] = this.row;
		coords[1] = this.col;
		return coords;	
	}


	/**
	 * Remove the piece on this tile
	 */
	public void removePiece(){
		this.piece = "none";
		this.setIcon(null);
	}

	/**
	 * Place piece on this tile
	 */
	public void placePiece(String piece, String player){
		this.piece = piece;
		this.player = player;
		if(player.equals("player1") && piece.equals("pawn")){
			this.setIcon(new ImageIcon(REDPAWNIMGPATH));
		} else if(player.equals("player1") && piece.equals("queen")){	
			this.setIcon(new ImageIcon(REDQUEENIMGPATH));
		} else if(player.equals("player2") && piece.equals("pawn")){	
			this.setIcon(new ImageIcon(LAVPAWNIMGPATH));
		} else if(player.equals("player2") && piece.equals("queen")){	
			this.setIcon(new ImageIcon(LAVQUEENIMGPATH));
		}
	}



	public void mouseClicked(MouseEvent e){}

	public void mousePressed(MouseEvent e){
		GameRunner.processMousePressed(this);
	}

	public void mouseReleased(MouseEvent e){}

	public void mouseEntered(MouseEvent e){}

	public void mouseExited(MouseEvent e){}
}
