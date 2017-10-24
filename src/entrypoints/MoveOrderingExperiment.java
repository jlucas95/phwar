package entrypoints;

import algorithms.*;
import algorithms.evaluation.WeightedEvaluation;
import algorithms.evaluation.features.IFeature;
import game.*;

import java.util.Properties;

public class MoveOrderingExperiment {


    private static final long randomSeed = 0x41c97ca709cfc5e4L;

    public static void main(String[] args) {
        Board board = new BoardGenerator(5).build();

        //TODO parallelize
        Properties properties = System.getProperties();
        for (int run = 0; run < 40; run++) {
            IPlayer r1 = new RandomPlayer(randomSeed);
            Algorithm r2 = new AlphaBetaPlayer<>(new WeightedEvaluation(IFeature.getFeatures(1.0)), 2);

            Game game = new Game(board, r1, r2, false);
            GameResult result = game.play();
            System.out.println(run + "," + r2.averageVisited() + "," + r2.averageEvaluated() + "," + (result.getWinner() == r2));
        }
        System.out.println("Done");
    }
}
