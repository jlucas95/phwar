package algorithms.features;

import game.GameState;
import game.IPlayer;
import game.Piece;
import util.*;

public class PieceFeature implements IFeature{

    @Override
    public double score(GameState state, IPlayer player) {
        // Group pieces by owner
        IGrouping<IPlayer, Piece> groups = new QueryableList<>(state.getPieces()).groupBy(Piece::getOwner);
        double playerPieces = 0;
        double opponentPieces = 0;
        for (Group<IPlayer, Piece> group : groups) {
            if (group.getKey() == player) {
                playerPieces += group.size();
            }
            else{
                opponentPieces += group.size();
            }
        }
        return playerPieces - opponentPieces;
    }
}
