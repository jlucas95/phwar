package algorithms;

import algorithms.evaluation.IEvaluationFunction;
import game.GameState;
import game.Move;
import util.Tuple;

import static algorithms.MiniMaxType.MAX;
import static algorithms.MiniMaxType.MIN;

/**
 * Created by Jan on 13-10-2017.
 */
public class MiniMaxPlayer extends Algorithm {

    private IEvaluationFunction evalFunc;

    private int searchDept;

    public MiniMaxPlayer(IEvaluationFunction evalFunction, int searchDept){
        this.evalFunc = evalFunction;
        this.searchDept = searchDept;
    }

    @Override
    Move run(GameState state) {
        return miniMax(state, searchDept, MAX, null).getSecond();
    }

    private Tuple<Integer, Move> miniMax(GameState s, int depth, MiniMaxType type, Move m){
        nodesVisited++;
        if(depth == 0 || terminalNode(s)) {
            nodesEvaluated++;
            return new Tuple<>(evalFunc.evaluate(s, this), m);
        }

        Tuple<Integer, Move> score;
        if(type == MAX){
            score = new Tuple<>(Integer.MIN_VALUE, null);
            for(Tuple<Move, GameState> successor : s.getSuccessors(this)){
                Move move = successor.getFirst();
                GameState sPrime = successor.getSecond();
                Tuple<Integer, Move> value = miniMax(sPrime, depth - 1, MIN, move);

                Integer newScore = value.getFirst();
                if(newScore > score.getFirst()) score = new Tuple<>(newScore, move);
            }
        }
        else{
            score = new Tuple<>(Integer.MAX_VALUE, null);
            for(Tuple<Move, GameState> successor : s.getSuccessors(game.otherPlayer(this))){
                Move move = successor.getFirst();
                GameState sPrime = successor.getSecond();
                Tuple<Integer, Move> value = miniMax(sPrime, depth - 1, MAX, move);
                Integer newScore = value.getFirst();
                if( newScore < score.getFirst()) score = new Tuple<>(newScore, move);
            }
        }
        return score;
    }

    @Override
    public String toString() {
        return "MiniMaxPlayer";
    }
}
