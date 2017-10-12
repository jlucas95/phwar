package algorithms;

import game.GameState;
import game.Move;
import game.Player;
import util.Tuple;

import static algorithms.MiniMaxType.MAX;
import static algorithms.MiniMaxType.MIN;

/**
 * Created by Jan on 13-10-2017.
 */
public class MiniMaxPlayer <X extends EvaluationFunction> extends Player {

    private X evalFunc;
    private int searchDept;

    public MiniMaxPlayer(X evalFunction, int searchDept){
        this.evalFunc = evalFunction;
        this.searchDept = searchDept;
    }

    @Override
    public Move getMove(GameState state) {
        return miniMax(state, searchDept, MAX, null).getSecond();
    }

    private Tuple<Integer, Move> miniMax(GameState s, int depth, MiniMaxType type, Move m){
        if(depth == 0 || terminalNode(s)) return new Tuple<>(evalFunc.evaluate(s), m);
        Tuple<Integer, Move> score;
        if(type == MAX){
            score = new Tuple<>(Integer.MIN_VALUE, null);
            for(Tuple<Move, GameState> sPrime : s.getSuccessors(this)){
                Tuple<Integer, Move> value = miniMax(sPrime.getSecond(), depth - 1, MIN, sPrime.getFirst());
                if(value.getFirst() > score.getFirst()) score = value;
            }
        }
        else{
            score = new Tuple<>(Integer.MAX_VALUE, null);
            for(Tuple<Move, GameState> sPrime : s.getSuccessors(game.otherPlayer(this))){
                Tuple<Integer, Move> value = miniMax(sPrime.getSecond(), depth - 1, MAX, sPrime.getFirst());
                if( value.getFirst() < score.getFirst()) score = value;
            }
        }
        return score;
    }

    private boolean terminalNode(GameState s) {
        return s.isEndgame();
    }
}
