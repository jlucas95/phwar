package entrypoints;

import algorithms.*;
import game.*;

/**
 * Created by Jan on 13-10-2017.
 */
public class AlphaBetaEntry {


    public static void main(String[] args) {
        Board board = new BoardGenerator(5).build();
        IPlayer r1 = new RandomPlayer(System.currentTimeMillis());
        IPlayer r2 = new AlphaBetaPlayer<>(new WeightedEvaluation(), 2);
        Game game = new Game(board, r1, r2);
        GameResult result = game.play();

    }
}
