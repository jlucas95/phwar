package algorithms;

import game.GameState;
import game.IPlayer;
import game.Player;

/**
 * Created by Jan on 13-10-2017.
 */
interface EvaluationFunction {
    public int evaluate(GameState state, IPlayer player);
}
