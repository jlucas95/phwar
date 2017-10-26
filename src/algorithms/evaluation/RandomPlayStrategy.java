package algorithms.evaluation;

import game.Move;

import java.util.Collection;
import java.util.List;
import java.util.Random;

/**
 * Created by Jan on 26-10-2017.
 */

public class RandomPlayStrategy implements IPlayStrategy {

    private final Random random;

    public RandomPlayStrategy() {
        random = new Random();
    }

    public RandomPlayStrategy(long seed) {
        this.random = new Random(seed);
    }

    @Override
    public Move pickMove(List<Move> moves) {
        int index = random.nextInt(moves.size());
        return moves.get(index);
    }
}
