package algorithms.evaluation.features;

import game.pieces.Electron;
import game.GameState;
import game.IPlayer;

/**
 * Created by Jan on 19-10-2017.
 */
public class ElectronFeature extends PieceTypeFeature {

    public double score(GameState state, IPlayer player) {
        return super.scoreType(state, player, Electron.class);
    }
}
