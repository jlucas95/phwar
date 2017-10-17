package algorithms;

import game.GameState;
import game.IPlayer;
import game.Piece;
import util.MultiMap;
import util.Queryable;
import util.QueryableEntry;
import util.QueryableList;

/**
 * Created by Jan on 13-10-2017.
 *
 * Simple evaluation by dividing number of own pieces by number of opponent pieces.
 * Score is then multiplied by ten.
 */
public class PieceEvaluationFunction implements EvaluationFunction {

    private IPlayer player;

    @Override
    public void setPlayer(IPlayer player){
        this.player = player;
    }

    @Override
    public int evaluate(GameState state) {
        if(player == null) throw new IllegalStateException("no player assigned to evaluation function");
        MultiMap<IPlayer, Piece> groups = new QueryableList<>(state.getPieces()).groupBy(Piece::getOwner);
        double playerPieces = 0;
        double opponentPieces = 0;
        for (QueryableEntry<IPlayer, Piece> group : groups) {
            int size = group.getValues().size();
            if (group.getKey() == player) {
                playerPieces += size;
            }
            else{
                opponentPieces += size;
            }
        }
        return (int) Math.round((playerPieces / opponentPieces) * 10.0);
    }
}
