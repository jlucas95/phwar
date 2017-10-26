package entrypoints;

import algorithms.*;
import algorithms.evaluation.CaptureMovesFirstStrategy;
import algorithms.evaluation.MCEvaluation;
import algorithms.evaluation.WeightedEvaluation;
import algorithms.evaluation.features.IFeature;
import algorithms.moveOrdering.CaptureMoveOrdering;
import game.*;
import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;
import org.omg.SendingContext.RunTime;
import util.Queryable;
import util.QueryableList;

import java.util.Properties;
import java.util.StringJoiner;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.LinkedBlockingQueue;

public class MoveOrderingExperiment implements Runnable{


    //private static final long randomSeed = 0x41c97ca709cfc5e4L;
    private static final long randomSeed = System.currentTimeMillis();
    private static final int runsPerThread = 20;
    private static final LinkedBlockingQueue<String> queue = new LinkedBlockingQueue<>();

    private Integer num;

    public MoveOrderingExperiment(int num) {
        this.num = num * 10;
    }

    public static void main(String[] args) {

        // get core count
        int processors = Runtime.getRuntime().availableProcessors();
        System.out.println("Found " + processors + " processors");

        // make thread per core
        QueryableList<Thread> threads = new QueryableList<>();
        for (int i = 0; i < processors; i++) {
            threads.add(new Thread(new MoveOrderingExperiment(i)));
        }
        threads.forEach(t-> t.start());

        while(!threads.all(t-> !t.isAlive()) || !queue.isEmpty()){
            try{
                System.out.println(queue.take());
            }
            catch (InterruptedException e){
                e.printStackTrace();
            }
        }
        System.out.println("Done");

    }

//    String name = "Experiment 1 random vs defaultWeightedEval";
//    IPlayer r1 = new RandomPlayer(randomSeed);
//    Algorithm r2 = new AlphaBetaPlayer(new WeightedEvaluation(IFeature.getFeatures(1.0)), 2);

//    String name = "Experiment 2 move-ordering";
//    IPlayer r1 = new RandomPlayer(randomSeed);
//    Algorithm r2 = new AlphaBetaPlayer(new WeightedEvaluation(IFeature.getFeatures(1.0)), 2, new CaptureMoveOrdering());

//    String name = "Experiment 3 monte-carlo and move ordering";
//    IPlayer r1 = new RandomPlayer(randomSeed);
//    Algorithm r2 = new AlphaBetaPlayer(new MCEvaluation(10), 2, new CaptureMoveOrdering());

//    String name = "Experiment 4 monte carlo with playStrategy and move ordering";
//    IPlayer r1 = new RandomPlayer(randomSeed);
//    Algorithm r2 = new AlphaBetaPlayer(new MCEvaluation(10, new CaptureMovesFirstStrategy()), 2, new CaptureMoveOrdering());

    //Experiment 5 multi-threaded monte-carlo

    String name = "Experiment 6 Depth-one search";
    IPlayer r1 = new RandomPlayer(randomSeed);
    Algorithm r2 = new DepthOneSearch(100);


    @Override
    public void run() {
        Board board = new BoardGenerator(5).build();
        for (int run = 0; run < runsPerThread; run++) {


            Game game = new Game(board, r1, r2, false);
            GameResult result = game.play();
            StringJoiner joiner = new StringJoiner(",");

            joiner.add(String.valueOf(num+run));
            joiner.add(String.valueOf(r2.averageVisited()));
            joiner.add(String.valueOf(r2.averageEvaluated()));
            joiner.add(String.valueOf((result.getWinner() == r2)));
            joiner.add(String.valueOf(r2.averageTimeElapsed()));
            joiner.add(String.valueOf(result.getTurns()));

            queue.add(joiner.toString());


//            queue.add(
//                    num + run + "," +
//                    r2.averageVisited() + "," +
//                    r2.averageEvaluated() + "," +
//                    (result.getWinner() == r2) + "," +
//                    r2.averageTimeElapsed() + "," +
//                    result.getTurns());
        }
    }
}
