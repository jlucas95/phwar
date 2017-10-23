package entrypoints;

import algorithms.ManualPlayer;
import algorithms.MiniMaxPlayer;
import algorithms.RandomPlayer;
import algorithms.WeightedEvaluation;
import algorithms.features.IFeature;
import game.*;

/**
 * Created by Jan on 13-10-2017.
 */
public class MiniMaxEntry {


    public static void main(String[] args) {
        Board board = new BoardGenerator(5).build();
        //IPlayer r1 = new RandomPlayer(System.currentTimeMillis());
        IPlayer r1 = new ManualPlayer();
        IPlayer r2 = new MiniMaxPlayer<>(new WeightedEvaluation(IFeature.getFeatures(1.0)), 2);
        Game game = new Game(board, r1, r2, true);
        GameResult result = game.play();
    }

}
