package algorithms.features;

import game.GameState;
import game.IPlayer;

public interface IFeature {
    public double score(GameState state, IPlayer player);
}
