package algorithms.evaluation.features;

import game.GameState;
import game.IPlayer;
import game.pieces.Neutron;

/**
 * Created by Jan on 19-10-2017.
 */
public class NeutronFeature extends PieceTypeFeature{

    @Override
    public double score(GameState state, IPlayer player) {
        return super.scoreType(state, player, Neutron.class);
    }
}
