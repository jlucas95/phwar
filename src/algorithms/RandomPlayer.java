package algorithms;

import game.Board;
import game.Move;
import game.Player;

public class RandomPlayer implements Player {
    @Override
    public Move getMove(Board board) {
        board.getMoves(this);
        return null;
    }
}
