package game;

import java.util.ArrayList;
import java.util.List;

public class GameState {

    private List<Piece> pieces;

    public List<Piece> getPlayerPieces(Player player){
        ArrayList<Piece> playerPieces = new ArrayList<>();
        for (Piece piece : pieces) {
            if(player.equals(piece.getOwner())){
                playerPieces.add(piece);
            }
        }
        return playerPieces;
    }

    private void setPieces(List<Piece> pieces){
        this.pieces = pieces;
    }

    public static GameState getStartState(Player player1, Player player2) {
        GameState startState = new GameState();
        int[][] electronPositions = new int[][] {{}};

        // negating the coordinates makes them flip around the center cell
    }
}
