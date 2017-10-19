package algorithms.features;

import game.*;
import util.Queryable;
import util.QueryableList;

import java.util.List;

public class PossibleMovesFeature implements IFeature{
    @Override
    public double score(GameState state, IPlayer player) {

        Queryable<Move> captures = new QueryableList<>(state.getMoves(player)).where(m -> m.getClass() == CaptureMove.class);
        Queryable<Move> opponentCaptures = new QueryableList<>(state.getMoves(state.getOpponent(player))).where(m -> m.getClass() == CaptureMove.class);
        return captures.size() - opponentCaptures.size();
    }
}
