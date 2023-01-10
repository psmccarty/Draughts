import java.util.ArrayList;
import java.util.HashMap;


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

		if(gl.movesList != null && gl.movesList.containsKey(tile)){
			//TODO: change this to also eliminate jumped pieces
			gl.simpleMove(gl.previouslyClicked, tile);
			ArrayList<Square> kills = gl.movesList.get(tile);
			for(int i = 0; i < kills.size(); i++){
				kills.get(i).removePiece();	
			}
			gl.checkAndPromoteToQueen(tile);
		}


		int[] coords = tile.getCoords();
		System.out.printf("Square(%d, %d) pressed\n", coords[0], coords[1]);	

		gl.clearHighlighted();
		ArrayList<Square> moves = new ArrayList<Square>();
		HashMap<Square, ArrayList<Square>> ml = new HashMap<Square, ArrayList<Square>>();
		if(gl.currPlayer == 1 && tile.getPlayer().equals("player1")){
			ml = gl.getRedPawnMoves(tile);	
		} else if(gl.currPlayer == 2 && tile.getPlayer().equals("player2")){
			ml = gl.getLavPawnMoves(tile);	
		}
		Square[] sqs = ml.keySet().toArray(new Square[0]);
		for(int i = 0; i < sqs.length; i++){
			gl.highlight(sqs[i]);
		}





		gl.previouslyClicked = tile;
		gl.movesList = ml;
	}
}
