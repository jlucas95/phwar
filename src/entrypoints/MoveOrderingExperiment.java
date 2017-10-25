package entrypoints;

import algorithms.*;
import algorithms.evaluation.WeightedEvaluation;
import algorithms.evaluation.features.IFeature;
import algorithms.moveOrdering.CaptureMoveOrdering;
import game.*;
import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;
import org.omg.SendingContext.RunTime;
import util.Queryable;
import util.QueryableList;

import java.util.Properties;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.LinkedBlockingQueue;

public class MoveOrderingExperiment implements Runnable{


    //private static final long randomSeed = 0x41c97ca709cfc5e4L;
    private static final long randomSeed = System.currentTimeMillis();
    private static final int runsPerThread = 10;
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

    @Override
    public void run() {
        Board board = new BoardGenerator(5).build();
        for (int run = 0; run < runsPerThread; run++) {
            IPlayer r1 = new RandomPlayer(randomSeed);
            Algorithm r2 = new AlphaBetaPlayer(new WeightedEvaluation(IFeature.getFeatures(1.0)), 2, new CaptureMoveOrdering());

            Game game = new Game(board, r1, r2, false);
            GameResult result = game.play();
            queue.add( num + run + "," + r2.averageVisited() + "," + r2.averageEvaluated() + "," + (result.getWinner() == r2));
        }
    }
}
