package algorithms;

import game.GameState;
import game.Move;
import game.Player;
import util.Tuple;

/**
 * Created by Jan on 13-10-2017.
 */
public class AlphaBetaPlayer<X extends EvaluationFunction> extends Algorithm{
    private X evalFunc;
    private int depth;

    public AlphaBetaPlayer(X evalFunc, int depth) {
        this.evalFunc = evalFunc;
        this.depth = depth;
    }

    @Override
    public Move getMove(GameState state) {
        Tuple<Integer, Move> integerMoveTuple = alphaBeta(state, depth, Integer.MIN_VALUE, Integer.MAX_VALUE, null);
        return integerMoveTuple.getSecond();
    }

    private Tuple<Integer, Move> alphaBeta(GameState s, int depth, int alpha, int beta, Move m) {
        if(depth == 0 || terminalNode(s)) return new Tuple<>(
                evalFunc.evaluate(s, this), m);
        Tuple<Integer, Move> scoreT = new Tuple<Integer, Move>(Integer.MIN_VALUE, null);

        for (Tuple<Move, GameState> successor : s.getSuccessors(s.getCurrentPlayer())) {
            GameState sPrime = successor.getSecond();
            Move move = successor.getFirst();

            Tuple<Integer, Move> result = alphaBeta(sPrime, depth - 1, -beta, -alpha, move);
            int value = -result.getFirst();

            if( value > scoreT.getFirst() ) scoreT = new Tuple<>(value, move);
            if( scoreT.getFirst() > alpha ) alpha = scoreT.getFirst();
            if( scoreT.getFirst() >= beta ) break;

        }
        return scoreT;
    }

    public String toString(){
        return "AlphaBeta player";
    }
}
