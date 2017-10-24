package algorithms.evaluation.features;

import game.CaptureMove;
import game.GameState;
import game.IPlayer;
import util.QueryableList;

public class PossibleCapturesFeature implements IFeature {
    @Override
    public double score(GameState state, IPlayer player) {
        return new QueryableList<>(state.getMoves(player))
                .where(m->m.getClass()== CaptureMove.class)
                .size();
    }
}