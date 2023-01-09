import java.util.ArrayList;
import java.awt.Color;

public class GameLogic{

	private static CheckersBoard cb;	// Checkers board of this game session
	public Square previouslyClicked;	// The Square that was previously clicked
	public int currPlayer;			// The player whose turn it is. 1 for player1 and 2 for player2
	
	public static final int ROWS = 8;	// Total number of rows on this board
	public static final int COLS = 8;	// Total number of columns on this board

	/**
	 * Constructs a GameLogic object
	 *
	 * @param the checkers board that will be played with
	 */	
	public GameLogic(CheckersBoard cb){
		this.currPlayer = 1;
		this.cb = cb;	
	}

	/**
	 * Return a Square object at a specified row and column
	 *
	 * @param i row of desired Square
	 * @param j col of desired Square
	 * @return the desired Square
	 */
	private static Square getSquare(int i, int j){
		return cb.getSquares()[i][j];	
	}

	/**
	 * Highlight a square by changing its background color
	 *
	 * @param the square that will be highlighted
	 */
	public void highlight(Square s){
		s.setBackground(Color.green);
		cb.getHighlighted().add(s);
	}

	/**
	 * Unhighlight everything that is already highlighted by changing its color to black
	 * and removing it from the ArrayList
	 */
	public void clearHighlighted(){
		ArrayList<Square> h = cb.getHighlighted();
		for(int i = h.size() - 1; i >= 0; i--){
			h.remove(i).setBackground(Color.black);	
		}	
	}


	/**
	 * List the moves available to this Square
	 *
	 * @param Square of interest
	 * @return List of all possible squares this Square can jump to
	 */
	public ArrayList<Square> getAvailableMoves(Square tile){
		ArrayList<Square> potentialMoves = new ArrayList<Square>();	
		int[] coords =  tile.getCoords();
		int row = coords[0];
		int col = coords[1];

		if(tile.getPiece().equals("none")){
			return potentialMoves;	
		}	

		if(tile.getPlayer().equals("player1") && this.currPlayer == 1){ // piece belongs to player 1
			if(tile.getPiece().equals("pawn")){ // piece is a pawn 
				
				// square at top left is open
				if(row - 1 >= 0 && col - 1 >= 0 && getSquare(row - 1, col - 1).getPiece().equals("none")){ 
					potentialMoves.add(getSquare(row - 1, col - 1));	
				} else{
				
				}
				
				// square at top right is open
				if(row - 1 >= 0 && col + 1 < COLS && getSquare(row - 1, col + 1).getPiece().equals("none")){ 
					potentialMoves.add(getSquare(row - 1, col + 1));	
				} else{
				
				}
			}

		
		} else if(tile.getPlayer().equals("player2") && this.currPlayer == 2){ // piece belongs to player 2
			if(tile.getPiece().equals("pawn")){ // piece is not a queen
				
				// square at bottom left is open
				if(row + 1 < ROWS && col - 1 >= 0 && getSquare(row + 1, col - 1).getPiece().equals("none")){
					potentialMoves.add(getSquare(row + 1, col - 1));
				} else{
				
				}

				// square at bottom right is open
				if(row + 1 < ROWS && col + 1 < COLS && getSquare(row + 1, col + 1).getPiece().equals("none")){
					potentialMoves.add(getSquare(row + 1, col + 1));
				} else{
				
				}
			}
		}
		return potentialMoves;
	}


	/**
	 * Move a piece on a square to a different square
	 *
	 * @param curr the Square where the piece is currently on
	 * @param dest the destination Square where the piece should be moved to
	 */
	public void simpleMove(Square curr, Square dest){

		dest.placePiece(curr.getPiece(), curr.getPlayer());
		curr.removePiece();	
		this.currPlayer = this.currPlayer == 1 ? 2 : 1; // change player
	}


	/**
	 * Get the Square that is diagonally up and to the left. 
	 *
	 * @param tile the Square whose diagonal we want
	 * @return the Square to the upper left or null if out of bounds
	 */
	private static Square getUpperLeft(Square tile){
		int[] coords = tile.getCoords();
		int row = coords[0];
		int col = coords[1];
		return (row - 1 >= 0 && col - 1 >= 0) ? getSquare(row - 1, col - 1) : null;
	}


	/**
	 * Get the Square that is diagonally up and to the left. 
	 *
	 * @param tile the Square whose diagonal we want
	 * @return the Square to the upper left or null if out of bounds
	 */
	private static Square getUpperRight(Square tile){
		int[] coords = tile.getCoords();
		int row = coords[0];
		int col = coords[1];
		return (row - 1 >= 0 && col + 1 < COLS) ? getSquare(row - 1, col + 1) : null;
	}


	/**
	 * Get the Square that is diagonally up and to the left. 
	 *
	 * @param tile the Square whose diagonal we want
	 * @return the Square to the upper left or null if out of bounds
	 */
	private static Square getLowerLeft(Square tile){
		int[] coords = tile.getCoords();
		int row = coords[0];
		int col = coords[1];
		return (row + 1 < ROWS && col - 1 >= 0) ? getSquare(row + 1, col - 1) : null;
	}


	/**
	 * Get the Square that is diagonally up and to the left. 
	 *
	 * @param tile the Square whose diagonal we want
	 * @return the Square to the upper left or null if out of bounds
	 */
	private static Square getLowerRight(Square tile){
		int[] coords = tile.getCoords();
		int row = coords[0];
		int col = coords[1];
		return (row + 1 < ROWS && col + 1 < COLS) ? getSquare(row + 1, col + 1) : null;
	}

	
	/**
	 * Gets the available moves of a red pawn
	 *
	 * @param tile tile where the red pawn resides
	 */
	public ArrayList<Square> getRedPawnMoves(Square tile){
		int[] coords = tile.getCoords();
		int row = coords[0];
		int col = coords[1];
		ArrayList<Square> queue = new ArrayList<Square>();
		ArrayList<Character> origins = new ArrayList<Character>();
		ArrayList<Boolean> enemyJumped = new ArrayList<Boolean>();
		ArrayList<Square> moves = new ArrayList<Square>();
		
		queue.add(getUpperLeft(tile));
		origins.add('L');
		enemyJumped.add(false);

		queue.add(getUpperRight(tile));
		origins.add('R');
		enemyJumped.add(false);

		while(queue.size() > 0){
			Square curr = queue.remove(0);
			char origin = origins.remove(0);
			boolean jumped = enemyJumped.remove(0);
			if(curr == null){
				continue;	
			}
			if(curr.getPiece().equals("none")){ // square is empty. valid move
				moves.add(curr);	
				if(jumped){ // reached this square by jumping over an enemy
					queue.add(getUpperLeft(curr));
					origins.add('L');
					enemyJumped.add(false);

					queue.add(getUpperRight(curr));
					origins.add('R');
					enemyJumped.add(false);
				}
			} else if(curr.getPlayer().equals("player2") && !jumped){ // square has enemy. could potentially kill enemy
				if(origin == 'L'){
					queue.add(getUpperLeft(curr));
					origins.add('L');	
					enemyJumped.add(true);
				} else if(origin == 'R'){
					queue.add(getUpperRight(curr));
					origins.add('R');	
					enemyJumped.add(true);
				}	
			}
		}
		return moves;
	}


	/**
	 * Gets the available moves of a red pawn
	 *
	 * @param tile tile where the red pawn resides
	 */
	public ArrayList<Square> getLavPawnMoves(Square tile){
		int[] coords = tile.getCoords();
		int row = coords[0];
		int col = coords[1];
		ArrayList<Square> queue = new ArrayList<Square>();
		ArrayList<Character> origins = new ArrayList<Character>();
		ArrayList<Boolean> enemyJumped = new ArrayList<Boolean>();
		ArrayList<Square> moves = new ArrayList<Square>();
		
		queue.add(getLowerLeft(tile));
		origins.add('L');
		enemyJumped.add(false);

		queue.add(getLowerRight(tile));
		origins.add('R');
		enemyJumped.add(false);

		while(queue.size() > 0){
			Square curr = queue.remove(0);
			char origin = origins.remove(0);
			boolean jumped = enemyJumped.remove(0);
			if(curr == null){
				continue;	
			}
			if(curr.getPiece().equals("none")){ // square is empty. valid move
				moves.add(curr);	
				if(jumped){ // reached this square by jumping over an enemy
					queue.add(getLowerLeft(curr));
					origins.add('L');
					enemyJumped.add(false);

					queue.add(getLowerRight(curr));
					origins.add('R');
					enemyJumped.add(false);
				}
			} else if(curr.getPlayer().equals("player1") && !jumped){ // square has enemy. could potentially kill enemy
				if(origin == 'L'){
					queue.add(getLowerLeft(curr));
					origins.add('L');	
					enemyJumped.add(true);
				} else if(origin == 'R'){
					queue.add(getLowerRight(curr));
					origins.add('R');	
					enemyJumped.add(true);
				}	
			}
		}
		return moves;
	}


}
