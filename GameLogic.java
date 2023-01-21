import java.util.ArrayList;
import java.awt.Color;
import java.util.HashMap;

public class GameLogic{

	private static CheckersBoard cb;	// Checkers board of this game session
	public Square previouslyClicked = null;	// The Square that was previously clicked
	public HashMap<Square, ArrayList<Square>> movesList = null; 
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
	 * Move a piece on a square to a different square
	 *
	 * @param curr the Square where the piece is currently on
	 * @param dest the destination Square where the piece should be moved to
	 */
	public void simpleMove(Square curr, Square dest){

		replacePlayerPiece(curr, dest);	
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
	 * Get the Square that is diagonally up and to the right. 
	 *
	 * @param tile the Square whose diagonal we want
	 * @return the Square to the upper right or null if out of bounds
	 */
	private static Square getUpperRight(Square tile){
		int[] coords = tile.getCoords();
		int row = coords[0];
		int col = coords[1];
		return (row - 1 >= 0 && col + 1 < COLS) ? getSquare(row - 1, col + 1) : null;
	}


	/**
	 * Get the Square that is diagonally down and to the left. 
	 *
	 * @param tile the Square whose diagonal we want
	 * @return the Square to the lower left or null if out of bounds
	 */
	private static Square getLowerLeft(Square tile){
		int[] coords = tile.getCoords();
		int row = coords[0];
		int col = coords[1];
		return (row + 1 < ROWS && col - 1 >= 0) ? getSquare(row + 1, col - 1) : null;
	}


	/**
	 * Get the Square that is diagonally down and to the right. 
	 *
	 * @param tile the Square whose diagonal we want
	 * @return the Square to the lower right or null if out of bounds
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
	 * @param a HashMap where keys are final destination for moves and values are all
	 * 	  the kills genereated by that move
	 */
	public static HashMap<Square, ArrayList<Square>> getRedPawnMoves(Square tile){

		//TODO: broken. Will return the key with squares that it shouldnt

		HashMap<Square, ArrayList<Square>> map = new HashMap<Square, ArrayList<Square>>();

		ArrayList<Square> queue = new ArrayList<Square>();
		ArrayList<ArrayList<Square>> kills = new ArrayList<ArrayList<Square>>();
		ArrayList<Character> origins = new ArrayList<Character>();
		ArrayList<Boolean> enemyJumped = new ArrayList<Boolean>();

		queue.add(getUpperLeft(tile));
		kills.add(new ArrayList<Square>());
		origins.add('L');
		enemyJumped.add(false);

		queue.add(getUpperRight(tile));
		kills.add(new ArrayList<Square>());
		origins.add('R');
		enemyJumped.add(false);

		while(queue.size() > 0){
			Square curr = queue.remove(0);
			ArrayList<Square> kill = kills.remove(0);
			char origin = origins.remove(0);
			boolean jumped = enemyJumped.remove(0);
			if(curr == null){
				continue;	
			}
			if(curr.getPiece().equals("none")){ // square is empty. valid move
				ArrayList<Square> neoKill = new ArrayList<Square>(); // make deep copy
				neoKill.addAll(kill);
				map.putIfAbsent(curr, neoKill);
				if(jumped){ // reached this square by jumping over an enemy
					if(getUpperLeft(curr) != null && !getUpperLeft(curr).getPiece().equals("none")){
						queue.add(getUpperLeft(curr));
						origins.add('L');
						enemyJumped.add(false);

						ArrayList<Square> deepCopyLeft = new ArrayList<Square>(); // make deep copy
						deepCopyLeft.addAll(neoKill);
						kills.add(deepCopyLeft); //probably have to change this to neoKill
					}
					
					if(getUpperRight(curr) != null && !getUpperRight(curr).getPiece().equals("none")){
						queue.add(getUpperRight(curr));
						origins.add('R');
						enemyJumped.add(false);

						ArrayList<Square> deepCopyRight = new ArrayList<Square>(); // make deep copy
						deepCopyRight.addAll(neoKill);
						kills.add(deepCopyRight); //probably have to change this to neoKill
					}
				}
			} else if(!curr.getPlayer().equals(tile.getPlayer()) && !jumped){ // square has enemy. could potentially kill enemy // change

				ArrayList<Square> neoKill = new ArrayList<Square>(); // make deep copy
				neoKill.addAll(kill);
				neoKill.add(curr);
				kills.add(neoKill);

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
		return map;
	}


	/**
	 * Gets the available moves of a lavander pawn
	 *
	 * @param tile tile where the lavander pawn resides
	 * @param a HashMap where keys are final destination for moves and values are all
	 * 	  the kills genereated by that move
	 */
	public static HashMap<Square, ArrayList<Square>> getLavPawnMoves(Square tile){

		HashMap<Square, ArrayList<Square>> map = new HashMap<Square, ArrayList<Square>>();

		ArrayList<Square> queue = new ArrayList<Square>();
		ArrayList<ArrayList<Square>> kills = new ArrayList<ArrayList<Square>>();
		ArrayList<Character> origins = new ArrayList<Character>();
		ArrayList<Boolean> enemyJumped = new ArrayList<Boolean>();

		queue.add(getLowerLeft(tile));
		kills.add(new ArrayList<Square>());
		origins.add('L');
		enemyJumped.add(false);

		queue.add(getLowerRight(tile));
		kills.add(new ArrayList<Square>());
		origins.add('R');
		enemyJumped.add(false);

		while(queue.size() > 0){
			Square curr = queue.remove(0);
			ArrayList<Square> kill = kills.remove(0);
			char origin = origins.remove(0);
			boolean jumped = enemyJumped.remove(0);
			if(curr == null){
				continue;	
			}
			if(curr.getPiece().equals("none")){ // square is empty. valid move
				ArrayList<Square> neoKill = new ArrayList<Square>();
				neoKill.addAll(kill);
				map.putIfAbsent(curr, neoKill);
				if(jumped){ // reached this square by jumping over an enemy
					if(getLowerLeft(curr) != null && !getLowerLeft(curr).getPiece().equals("none")){
						queue.add(getLowerLeft(curr));
						origins.add('L');
						enemyJumped.add(false);

						ArrayList<Square> deepCopyLeft = new ArrayList<Square>(); // make deep copy
						deepCopyLeft.addAll(neoKill);
						kills.add(deepCopyLeft); //probably have to change this to neoKill
					}
					
					if(getLowerRight(curr) != null && !getLowerRight(curr).getPiece().equals("none")){
						queue.add(getLowerRight(curr));
						origins.add('R');
						enemyJumped.add(false);

						ArrayList<Square> deepCopyRight = new ArrayList<Square>(); // make deep copy
						deepCopyRight.addAll(neoKill);
						kills.add(deepCopyRight); //probably have to change this to neoKill
					}
				}
			} else if(!curr.getPlayer().equals(tile.getPlayer()) && !jumped){ // square has enemy. could potentially kill enemy // change

				ArrayList<Square> neoKill = new ArrayList<Square>(); // make deep copy
				neoKill.addAll(kill);
				neoKill.add(curr);
				kills.add(neoKill);

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
		//System.out.println("List of (move, kill) pairs");
		//for(int i = 0; i < moves.size(); i++){
		//	System.out.printf("(%d, %d) = ", moves.get(i).getCoords()[0], moves.get(i).getCoords()[1]);
		//	for(int j = 0; j < map.get(moves.get(i)).size(); j++){
		//		System.out.printf("(%d, %d) ", map.get(moves.get(i)).get(j).getCoords()[0], map.get(moves.get(i)).get(j).getCoords()[1]);
		//	}
		//	System.out.println();
		//	map.put(moves.get(i), killsOnMove.get(i));	
		//}
		return map;
	}


	/**
	 * Promote a pawn to a queen. If pawn cannot be promoted do nothing
	 *
	 * @param the pawn of interest
	 */
	public void checkAndPromoteToQueen(Square tile){
		if(!tile.getPiece().equals("pawn")){ // only pawn can become queens
			return;	
		}

		if(tile.getPlayer().equals("player1") && tile.getCoords()[0] == 0){
			tile.promoteToQueen();	
		} else if(tile.getPlayer().equals("player2") && tile.getCoords()[0] == ROWS - 1){
			tile.promoteToQueen();	
		} 
	}


	/**
	 * Merge the contents of m1 into m2
	 *
	 * @param m1 move map where moves will be moved from
	 * @param m2 move map where where entries will be moved into
	 * @return a hashmap with thr merged contents of the two arguments	
	 */
	public static HashMap<Square, ArrayList<Square>> mergeTwoMoveMaps(HashMap<Square, ArrayList<Square>> m1, HashMap<Square, ArrayList<Square>> m2){

		Square[] keys1 = m1.keySet().toArray(new Square[0]); // this might throw some kind of null pointer exception
		for(int i = 0; i < keys1.length; i++){
			m2.putIfAbsent(keys1[i], m1.get(keys1[i]));	
		}	
		return m2;	
	}



	/**
	 * Gets the available moves of a red pawn
	 *
	 * @param tile tile where the red pawn resides
	 * @param a HashMap where keys are final destination for moves and values are all
	 * 	  the kills genereated by that move
	 */
	public static HashMap<Square, ArrayList<Square>> getQueenMoves(Square tile){
		HashMap<Square, ArrayList<Square>> map1 = getRedPawnMoves(tile);
		HashMap<Square, ArrayList<Square>> map2 = getLavPawnMoves(tile);
		HashMap<Square, ArrayList<Square>> mergedMap = mergeTwoMoveMaps(map1, map2);
		return mergedMap;

		
	}


	/**
	 * Determine whether a gives player still has any available moves
	 *
	 * @param pieces ArrayList containing the squares controled by the given player
	 * @return true if the player still has available moves. false otherwise.
	 */
	public static boolean playerHasMoves(String p){
		ArrayList<Square> pieces = cb.getPlayerPieces(p);
		if(pieces == null || pieces.size() == 0){ // player has no pieces remaining
			return false;	
		}
		
		for(int i = 0; i < pieces.size(); i++){
			Square tile = pieces.get(i);
			HashMap<Square, ArrayList<Square>> map = null;
			if(tile.getPiece().equals("pawn")){
				if(tile.getPlayer().equals("player1")){ // red pawn
					map = getRedPawnMoves(tile);	
				} else{ // lavander pawn
					map = getLavPawnMoves(tile);	
				}	
			} else if(tile.getPiece().equals("queen")){
				map = getQueenMoves(tile);	
			} else{
				System.out.println("Problem in playerHasMoves()");
			}
			if(map != null && map.size() > 0){
				return true;	
			}
		}
		return false;
	}


	/**
	 * Remove a given piece from the board and from the list of player pieces
	 *
	 * @param tile The square where the piece must be removed from
	 */
	public static void eliminate(Square tile){
		ArrayList<Square> l1 = cb.getPlayerPieces(tile.getPlayer());
		System.out.println(l1.toString());
		l1.remove(tile);
		System.out.println(l1.toString());
		tile.removePiece();
	}


	/**
	 * A piece has moved from one square to another. Remove the old square from the list
	 * of player pieces and add the new square
	 *
	 * @param former Square that currently has the piece that has moved
	 * @param latter Square that the piece will be moved to
	 */
	public static void replacePlayerPiece(Square former, Square latter){
		ArrayList<Square> piec = cb.getPlayerPieces(former.getPlayer()); 
		piec.remove(former);
		piec.add(latter);
	}	


	/**
	 * Display a message to the screen that a player has won
	 */
	public static void displayWin(String message){
		cb.interruptMessage(message);
	}

}
