package entrypoints;

import algorithms.AlphaBetaPlayer;
import algorithms.RandomPlayer;
import algorithms.evaluation.MCEvaluation;
import algorithms.evaluation.WeightedEvaluation;
import algorithms.evaluation.features.IFeature;
import game.*;

public class MCAlphaBeta {

    public static void main(String[] args) {
        Board board = new BoardGenerator(5).build();
        IPlayer r1 = new RandomPlayer(System.currentTimeMillis());
        IPlayer r2 = new AlphaBetaPlayer(new MCEvaluation(10), 2);
        Game game = new Game(board, r1, r2, true);
        GameResult result = game.play();
        System.out.println(result);
    }
}
