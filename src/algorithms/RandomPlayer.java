package algorithms;

import game.*;

import java.util.List;
import java.util.Random;

public class RandomPlayer extends Player implements IPlayer {

    private Random random;
    @Override
    public Move getMove(GameState state) {
        List<Move> moves = state.getMoves(this);
        int index = random.nextInt(moves.size());
        return moves.get(index);
    }

    public RandomPlayer(long seed){
        random = new Random(seed);
    }

    public RandomPlayer(){
        random = new Random();
    }
}
