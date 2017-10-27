package algorithms.evaluation;

import game.Game;
import game.GameState;
import game.IPlayer;
import game.Move;
import util.QueryableList;

import java.util.List;

/**
 * Created by Jan on 24-10-2017.
 */
public class MCEvaluation implements IEvaluationFunction {

    private final int N;
    private final int win = 10;
    private final int loss = -10;

    private Game game;
    private IPlayer evaluatingPlayer;
    private IPlayStrategy strategy;

    public MCEvaluation(int n, IPlayStrategy strategy) {
        N = n;
        this.strategy = strategy;
    }

    public MCEvaluation(int n) {
        this(n, new RandomPlayStrategy());
    }

    @Override
    public int evaluate(GameState state, IPlayer player) {
        return MC(state, player);
    }

    public int MC(GameState state, IPlayer player){
        this.game = player.getGame();
        evaluatingPlayer = player;

        QueryableList<Integer> scores = new QueryableList<>();
        for (int i = 0; i < N; i++) {
            scores.add(play_and_score(new GameState(state), state.getCurrentPlayer()));
        }
        return (int) Math.round(scores.average(Integer::doubleValue));
    }

    public int play_and_score(GameState state, IPlayer player) {
        if (state.isEndgame()) return determineScore(state);
        state = doRandomMove(state, player);
        return play_and_score(state, game.otherPlayer(player));
    }

    private GameState doRandomMove(GameState state , IPlayer player){
        List<Move> moves = state.getMoves(player);
        Move move = this.strategy.pickMove(moves);
        state.apply(move);
        return state;
    }

    private int determineScore(GameState state)
    {
        if(state.getWinner() == evaluatingPlayer) return win;
        else  return loss;
    }
}