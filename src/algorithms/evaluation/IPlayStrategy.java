package algorithms.evaluation;

import game.Move;

import java.util.List;

/**
 * Created by Jan on 26-10-2017.
 */
public interface IPlayStrategy {

    public Move pickMove(List<Move> moves);
}
