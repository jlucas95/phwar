package algorithms;

import game.Board;
import game.Move;
import game.IPlayer;

import java.util.List;
import java.util.Random;

public class RandomPlayer extends IPlayer {

    Random random;
    @Override
    public Move getMove(Board board) {
        List<Move> moves = game.state.getMoves(this);
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
