package algorithms;

import game.GameState;

/**
 * Created by Jan on 13-10-2017.
 */
interface EvaluationFunction {

    public int evaluate(GameState state);
}