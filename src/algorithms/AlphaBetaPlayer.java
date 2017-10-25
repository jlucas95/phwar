package algorithms;

import algorithms.evaluation.EvaluationFunction;
import algorithms.moveOrdering.IMoveOrdering;
import game.GameState;
import game.IPlayer;
import game.Move;
import util.Tuple;

import java.util.List;

/**
 * Created by Jan on 13-10-2017.
 */
public class AlphaBetaPlayer extends Algorithm{
    private EvaluationFunction evalFunc;
    private int depth;
    private IMoveOrdering order;

    public AlphaBetaPlayer(EvaluationFunction evalFunc, int depth) {
        this.evalFunc = evalFunc;
        this.depth = depth;
    }

    public AlphaBetaPlayer(EvaluationFunction evalFunc, int depth, IMoveOrdering order) {
        this.evalFunc = evalFunc;
        this.depth = depth;
        this.order = order;
    }

    @Override
    Move run(GameState state) {
        Tuple<Integer, Move> integerMoveTuple = alphaBeta(state, depth, Integer.MIN_VALUE, Integer.MAX_VALUE, null);
        return integerMoveTuple.getSecond();
    }

    private Tuple<Integer, Move> alphaBeta(GameState s, int depth, int alpha, int beta, Move m) {
        nodesVisited++;
        if(depth == 0 || terminalNode(s)) {
            nodesEvaluated++;
            return new Tuple<>(
                    getSign(s.getCurrentPlayer()) * evalFunc.evaluate(s, this), m);

        }
        Tuple<Integer, Move> scoreT = new Tuple<Integer, Move>(Integer.MIN_VALUE, null);

        List<Tuple<Move, GameState>> successors = s.getSuccessors(s.getCurrentPlayer());
        sort(successors);
        for (Tuple<Move, GameState> successor : successors) {
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

    private void sort(List<Tuple<Move, GameState>> successors) {
        if(this.order == null) return;

        successors.sort(order);
    }

    private Integer getSign(IPlayer currentPlayer) {
        if (currentPlayer==this) return 1;
        else return -1;
    }

    public String toString(){
        return "AlphaBeta player";
    }
}
