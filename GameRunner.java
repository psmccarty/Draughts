import java.util.ArrayList;


public class GameRunner{

	public static GameLogic gl;

	public GameRunner(GameLogic gl){
		this.gl = gl;	
	}

	/**
	 * Process a click on a given tile
	 *
	 * @param Square that was clicked on
	 */
	public static void processMousePressed(Square tile){

		if(gl.previouslyClicked != null && gl.getAvailableMoves(gl.previouslyClicked).contains(tile)){
			//TODO: change this to also eliminate jumped pieces
			gl.simpleMove(gl.previouslyClicked, tile);	
		}


		int[] coords = tile.getCoords();
		System.out.printf("Square(%d, %d) pressed\n", coords[0], coords[1]);	

		gl.clearHighlighted();
		ArrayList<Square> moves = new ArrayList<Square>();
		if(gl.currPlayer == 1 && tile.getPlayer().equals("player1")){
			moves = gl.getRedPawnMoves(tile);	
		} else if(gl.currPlayer == 2 && tile.getPlayer().equals("player2")){
			moves = gl.getLavPawnMoves(tile);	
		}
		for(int i = 0; i < moves.size(); i++){
			gl.highlight(moves.get(i));
		}





		gl.previouslyClicked = tile;
	}
}
