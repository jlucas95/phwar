package entrypoints;

import algorithms.MiniMaxPlayer;
import algorithms.PieceEvaluationFunction;
import algorithms.RandomPlayer;
import game.*;

/**
 * Created by Jan on 13-10-2017.
 */
public class MiniMaxEntry {


    public static void main(String[] args) {
        Board board = new BoardGenerator(5).build();
        IPlayer r1 = new RandomPlayer(System.currentTimeMillis());
        IPlayer r2 = new MiniMaxPlayer<PieceEvaluationFunction>(new PieceEvaluationFunction(), 2);
        Game game = new Game(board, r1, r2);
        GameResult result = game.play();
    }

}
