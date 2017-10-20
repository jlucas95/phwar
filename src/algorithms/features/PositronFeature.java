package algorithms.features;

import game.GameState;
import game.IPlayer;
import game.Positron;

/**
 * Created by Jan on 19-10-2017.
 */
public class PositronFeature extends PieceTypeFeature{
    @Override
    public double score(GameState state, IPlayer player) {
        return super.scoreType(state, player, Positron.class);
    }
}
