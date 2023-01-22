import java.util.ArrayList;
import java.util.HashMap;

public class GameRunner {

    public static GameLogic gl;

    public GameRunner(GameLogic g) {
        gl = g;
    }

    /**
     * Process a click on a given tile
     *
     * @param Square that was clicked on
     */
    public static void processMousePressed(Square tile) {

        if (gl.movesList != null && gl.movesList.containsKey(tile)) {
            gl.simpleMove(gl.previouslyClicked, tile);
            ArrayList<Square> kills = gl.movesList.get(tile);
            for (int i = 0; i < kills.size(); i++) {
                GameLogic.eliminate(kills.get(i));
            }
            gl.checkAndPromoteToQueen(tile);
            if (!GameLogic.playerHasMoves("player1")) {
                GameLogic.displayWin("Player 2 Wins!");
            } else if (!GameLogic.playerHasMoves("player2")) {
                GameLogic.displayWin("Player 1 Wins!");
            }
        }

        gl.clearHighlighted();
        HashMap<Square, ArrayList<Square>> ml = new HashMap<Square, ArrayList<Square>>();
        if (gl.currPlayer == 1 && tile.getPlayer().equals("player1")
                && tile.getPiece().equals("pawn")) {
            ml = GameLogic.getRedPawnMoves(tile);
        } else if (gl.currPlayer == 2 && tile.getPlayer().equals("player2")
                && tile.getPiece().equals("pawn")) {
            ml = GameLogic.getLavPawnMoves(tile);
        } else if (gl.currPlayer == 1 && tile.getPlayer().equals("player1")
                && tile.getPiece().equals("queen")) {
            ml = GameLogic.getQueenMoves(tile);
        } else if (gl.currPlayer == 2 && tile.getPlayer().equals("player2")
                && tile.getPiece().equals("queen")) {
            ml = GameLogic.getQueenMoves(tile);
        }
        Square[] sqs = ml.keySet().toArray(new Square[0]);
        for (int i = 0; i < sqs.length; i++) {
            gl.highlight(sqs[i]);
        }

        gl.previouslyClicked = tile;
        gl.movesList = ml;
    }
}
