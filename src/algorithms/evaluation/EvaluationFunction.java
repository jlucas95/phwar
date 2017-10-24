package algorithms.evaluation;

import game.GameState;
import game.IPlayer;

/**
 * Created by Jan on 13-10-2017.
 */
public interface EvaluationFunction {
    public int evaluate(GameState state, IPlayer player);
}
