package algorithms;

import game.GameState;
import game.Move;
import game.Player;
import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;
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

    List<Integer> times = new ArrayList<>();



    public final Move getMove(GameState state){
        nodesVisited = 0;
        nodesEvaluated = 0;
        long start = System.currentTimeMillis();
        Move m = run(state);
        int elapsed = (int)(System.currentTimeMillis() - start);
        times.add(elapsed);
        visited.add(nodesVisited);
        evaluated.add(nodesEvaluated);
        return m;
    }

    public void clearStats(){
        visited.clear();
        evaluated.clear();
        times.clear();
    }

    public double averageVisited(){
        return new QueryableList<>(visited).average(t -> t.doubleValue());
    }

    public double averageEvaluated(){
        return new QueryableList<>(evaluated).average(t-> t.doubleValue());
    }
    public double averageTimeElapsed() {return new QueryableList<>(times).average(t-> t.doubleValue());}

    abstract Move run(GameState state);
}
