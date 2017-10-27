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

public class MoveOrderingExperiment {


    //private static final long randomSeed = 0x41c97ca709cfc5e4L;
    public static final long randomSeed = System.currentTimeMillis();


    private MoveOrderingExperiment(int num, Experiment ex) {

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

//        experiments.add(new Experiment("Alpha beta with move ordering and depth 3",
//                randomSupplier,
//                ()-> new AlphaBetaPlayer(new WeightedEvaluation(IFeature.getFeatures(1.0)), 3, new CaptureMoveOrdering())));
//
//        experiments.add(new Experiment("Alpha beta with move ordering and depth 4",
//                randomSupplier,
//                ()-> new AlphaBetaPlayer(new WeightedEvaluation(IFeature.getFeatures(1.0)), 4, new CaptureMoveOrdering())));
        //Experiment 5 multi-threaded monte-carlo
        //experiments.add(new Experiment());

        // run experiments
        for (Experiment experiment : experiments) {
            experiment.runExperiment(80);
        }
        System.out.println();
        System.out.println();
        System.out.println("DONE");
        System.out.println(LocalDateTime.now());

    }




}
