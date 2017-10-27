package entrypoints;

import algorithms.*;
import algorithms.evaluation.CaptureMovesFirstStrategy;
import algorithms.evaluation.MCEvaluation;
import algorithms.evaluation.WeightedEvaluation;
import algorithms.evaluation.features.IFeature;
import algorithms.moveOrdering.CaptureMoveOrdering;
import game.*;
import util.QueryableList;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

public class MoveOrderingExperiment implements Runnable{


    //private static final long randomSeed = 0x41c97ca709cfc5e4L;
    private static final long randomSeed = System.currentTimeMillis();
    private static final int runsPerThread = 10;
    private static final LinkedBlockingQueue<String> queue = new LinkedBlockingQueue<>();
    private final Supplier<IPlayer> playerSupplier;
    private final Supplier<Algorithm> algorithmSupplier;

    private Integer num;


    private MoveOrderingExperiment(int num, Experiment ex) {
        playerSupplier = ex.getP1();
        algorithmSupplier = ex.getP2();
        this.num = num * 10;
    }

    public static void main(String[] args) {
        System.out.println("STARTING");
        System.out.println(LocalDateTime.now());
        // get core count
        int processors = Runtime.getRuntime().availableProcessors();
        System.out.println("Found " + processors + " processors");
        System.out.println(
        );
        System.out.println();
        Supplier<IPlayer> randomSupplier = () -> new RandomPlayer(randomSeed);
        List<Experiment> experiments = new ArrayList<>();
        // Add experiments
//        experiments.add(new Experiment("Experiment 1 random vs defaultWeightedEval",
//                randomSupplier,
//                ()-> new AlphaBetaPlayer(new WeightedEvaluation(IFeature.getFeatures(1.0)), 2)));
//
//        experiments.add(new Experiment("Experiment 2 move-ordering",
//                randomSupplier,
//                () -> new AlphaBetaPlayer(new WeightedEvaluation(IFeature.getFeatures(1.0)), 2, new CaptureMoveOrdering())));
//
//        experiments.add(new Experiment("Experiment 6 Depth-one search",
//                randomSupplier,
//                () -> new DepthOneSearch(100)));
//
//        experiments.add(new Experiment("Experiment 3 monte-carlo and move ordering",
//                randomSupplier,
//                () -> new AlphaBetaPlayer(new MCEvaluation(10), 2, new CaptureMoveOrdering()) ));
//
//        experiments.add(new Experiment("Experiment 4 monte carlo with playStrategy and move ordering",
//                randomSupplier,
//                () -> new AlphaBetaPlayer(new MCEvaluation(10, new CaptureMovesFirstStrategy()), 2, new CaptureMoveOrdering())));

        experiments.add(new Experiment("Alpha beta with move ordering and depth 1",
                randomSupplier,
                ()-> new AlphaBetaPlayer(new WeightedEvaluation(IFeature.getFeatures(1.0)), 1, new CaptureMoveOrdering())));

        experiments.add(new Experiment("Alpha beta with move ordering and depth 3",
                randomSupplier,
                ()-> new AlphaBetaPlayer(new WeightedEvaluation(IFeature.getFeatures(1.0)), 3, new CaptureMoveOrdering())));

        experiments.add(new Experiment("Alpha beta with move ordering and depth 4",
                randomSupplier,
                ()-> new AlphaBetaPlayer(new WeightedEvaluation(IFeature.getFeatures(1.0)), 4, new CaptureMoveOrdering())));
        //Experiment 5 multi-threaded monte-carlo
        //experiments.add(new Experiment());

        // run experiments
        for (Experiment experiment : experiments) {
            runExperiment(experiment, processors);
        }
        System.out.println();
        System.out.println();
        System.out.println("DONE");
        System.out.println(LocalDateTime.now());

    }

    static class Experiment{

        private final String name;
        private final Supplier<IPlayer> p1;

        private String getName() {
            return name;
        }

        private Supplier<IPlayer> getP1() {
            return p1;
        }

        private Supplier<Algorithm> getP2() {
            return p2;
        }

        private final Supplier<Algorithm> p2;

        Experiment(String name, Supplier<IPlayer> P1, Supplier<Algorithm> P2) {
            this.name = name;
            p1 = P1;
            p2 = P2;
        }
    }

   private static void runExperiment(Experiment ex, int processors){
        // print experiment name
        System.out.println(ex.getName());
        // make thread per core
        QueryableList<Thread> threads = new QueryableList<>();
        for (int i = 0; i < processors; i++) {
            threads.add(new Thread(new MoveOrderingExperiment(i, ex)));
        }
        threads.forEach(Thread::start);

        while(!threads.all(t-> !t.isAlive()) || !queue.isEmpty()){
            try{

                String poll = queue.poll(10, TimeUnit.SECONDS);
                if(poll != null){
                    System.out.println(poll);
                }
            }
            catch (InterruptedException e){
                e.printStackTrace();
            }
        }
        System.out.println("======= Done ========");
        System.out.println();
    }


    @Override
    public void run() {
        Board board = new BoardGenerator(5).build();
        for (int run = 0; run < runsPerThread; run++) {
            IPlayer r1 = playerSupplier.get();
            Algorithm r2 = algorithmSupplier.get();

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
