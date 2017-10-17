package entrypoints;

import algorithms.RandomPlayer;
import game.*;

import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Jan on 12-10-2017.
 */
public class ExpandEntry {

    public static void main(String[] args) {
        Board board = new BoardGenerator(5).build();
        IPlayer r1 = new RandomPlayer(1);
        IPlayer r2 = new RandomPlayer(2);
        Game game = new Game(board, r1, r2);
        GameState startState = GameState.getTestState(r1, r2, game);
        List<Move> moves = startState.getMoves(r2);

    }
}
