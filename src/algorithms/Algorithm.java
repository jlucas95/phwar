package algorithms;

import game.GameState;
import game.Move;
import game.Player;
import util.QueryableList;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jan on 13-10-2017.
 */
public abstract class Algorithm extends Player{

    public boolean terminalNode(GameState s) {
        return s.isEndgame();
    }

    long nodesVisited = 0;
    List<Long> visited = new ArrayList<>();
    long nodesEvaluated = 0;
    List<Long> evaluated = new ArrayList<>();

    public final Move getMove(GameState state){
        nodesVisited = 0;
        nodesEvaluated = 0;
        Move m = run(state);
        visited.add(nodesVisited);
        evaluated.add(nodesEvaluated);
        return m;
    }

    public void clearStats(){
        visited.clear();
        evaluated.clear();
    }

    public double averageVisited(){
        return new QueryableList<>(visited).average(t -> t.doubleValue());
    }

    public double averageEvaluated(){
        return new QueryableList<>(evaluated).average(t-> t.doubleValue());
    }

    abstract Move run(GameState state);
}
