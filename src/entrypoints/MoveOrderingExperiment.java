package entrypoints;

import algorithms.*;
import algorithms.evaluation.CaptureMovesFirstStrategy;
import algorithms.evaluation.MCEvaluation;
import algorithms.evaluation.MTMCEvaluation;
import algorithms.evaluation.WeightedEvaluation;
import algorithms.evaluation.features.IFeature;
import algorithms.moveOrdering.CaptureMoveOrdering;
import game.*;
import util.QueryableList;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
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
        experiments.add(new Experiment("Experiment 6 Depth-one search",
                randomSupplier,
                () -> new DepthOneSearch(100)));
//
//        experiments.add(new Experiment("Experiment 3 monte-carlo and move ordering",
//                randomSupplier,
//                () -> new AlphaBetaPlayer(new MCEvaluation(10), 2, new CaptureMoveOrdering()) ));
//
//        experiments.add(new Experiment("Experiment 4 monte carlo with playStrategy and move ordering",
//                randomSupplier,
//                () -> new AlphaBetaPlayer(new MCEvaluation(10, new CaptureMovesFirstStrategy()), 2, new CaptureMoveOrdering())));

//        experiments.add(new Experiment("Alpha beta with move ordering and depth 1",
//                randomSupplier,
//                ()-> new AlphaBetaPlayer(new WeightedEvaluation(IFeature.getFeatures(1.0)), 1, new CaptureMoveOrdering())));

//        experiments.add(new Experiment("Alpha beta with move ordering and depth 3",
//                randomSupplier,
//                ()-> new AlphaBetaPlayer(new WeightedEvaluation(IFeature.getFeatures(1.0)), 3, new CaptureMoveOrdering())));
//
//        experiments.add(new Experiment("Alpha beta with move ordering and depth 4",
//                randomSupplier,
//                ()-> new AlphaBetaPlayer(new WeightedEvaluation(IFeature.getFeatures(1.0)), 4, new CaptureMoveOrdering())));

//        experiments.add(new Experiment("Experiment 5 multi-threaded monte-carlo",
//                randomSupplier,
//                () -> new AlphaBetaPlayer(new MTMCEvaluation(10, new CaptureMovesFirstStrategy()), 2, new CaptureMoveOrdering())));

        File file = new File(String.format("data-%s.txt", LocalDateTime.now().toEpochSecond(ZoneOffset.UTC)));
        PrintStream p;
        try{
            file.createNewFile();
            p = new PrintStream(file);
            System.setOut(p);
        }
        catch (IOException e){
            e.printStackTrace();
            return;
        }
        // run experiments
        WeightsExperiment.main(null);
//        for (Experiment experiment : experiments) {
//            if(experiment.getName().equals("Experiment 5 multi-threaded monte-carlo")){
//                experiment.runExperiment(40, 1);
//            }
//            else{
//                experiment.runExperiment(40);
//            }
//            System.out.flush();
//        }
        System.out.println();
        System.out.println();
        System.out.println("DONE");
        System.out.println(LocalDateTime.now());
        System.out.flush();
        p.close();
    }




}
