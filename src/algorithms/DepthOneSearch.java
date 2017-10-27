package algorithms;

import algorithms.evaluation.IPlayStrategy;
import algorithms.evaluation.RandomPlayStrategy;
import game.GameState;
import game.IPlayer;
import game.Move;
import util.QueryableList;

import java.util.List;

/**
 * Created by Jan on 26-10-2017.
 */
public class DepthOneSearch extends Algorithm {

    private int N;

    private IPlayStrategy strategy;
    public DepthOneSearch(int n, IPlayStrategy strategy) {
        N = n;
        this.strategy = strategy;
    }

    public DepthOneSearch(int n) {
        this(n, new RandomPlayStrategy());
    }

    public int getN() {
        return N;
    }


    @Override
    Move run(GameState state) {
        List<Move> moves = state.getMoves(this);
        double max = Double.MIN_VALUE;
        Move chosen = null;
        for (Move move : moves) {
            double score = scoreMove(state.getNewState(move));
            if(score > max){
                chosen = move;
                max = score;
            }
        }
        return chosen;

    }

    double scoreMove(GameState state){
        QueryableList<Integer> scores = new QueryableList<>();
        for (int i = 0; i < N; i++) {
            // We always make the first move
            scores.add(play_and_score(state, state.getOpponent(this)));
        }
        return scores.average(Integer::doubleValue);
    }

    public int play_and_score(GameState state, IPlayer player) {
        if (state.isEndgame()) return determineScore(state);
        List<Move> moves = state.getMoves(player);
        Move move = this.strategy.pickMove(moves);
        return play_and_score(state.getNewState(move), game.otherPlayer(player));
    }

    private int determineScore(GameState state)
    {
        if(state.getWinner() == this) {
            return 10;}
        else  return -10;
    }

}
