package algorithms;

import game.GameState;
import game.IPlayer;

/**
 * Created by Jan on 13-10-2017.
 */
interface EvaluationFunction {
    public void setPlayer(IPlayer player);
    public int evaluate(GameState state);
}
