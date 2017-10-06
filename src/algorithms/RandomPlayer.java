package algorithms;

import game.Board;
import game.Move;
import game.Piece;
import game.Player;

import java.util.List;
import java.util.Random;

public class RandomPlayer extends Player {

    Random random;
    @Override
    public Move getMove(Board board) {
        List<Move> moves = game.getMoves(this);
        int index = random.nextInt(moves.size());
        return moves.get(index);
    }

    RandomPlayer(List<Piece> pieces, long seed){
        super(pieces);
        random = new Random(seed);
    }
}
