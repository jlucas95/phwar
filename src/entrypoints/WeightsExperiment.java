package entrypoints;

import algorithms.AlphaBetaPlayer;
import algorithms.RandomPlayer;
import algorithms.evaluation.WeightedEvaluation;
import algorithms.evaluation.features.IFeature;
import game.IPlayer;
import util.QueryableList;

import java.util.*;
import java.util.function.Supplier;

public class WeightsExperiment {

    private static int gamesPerFeatureSet = 10;
    private static int depth = 2;

    public static void main(String[] args) {
        Map<IFeature, Double> features = IFeature.getFeatures(0.0);
        double stepSize = 0.1;
        List<IFeature> entries = new ArrayList<>(features.keySet());
        List<FeatureSet> featureSets = createFeatureSets(entries);
        Supplier<IPlayer> randomSupplier = ()->new RandomPlayer(MoveOrderingExperiment.randomSeed);


        for (FeatureSet featureSet : featureSets) {
            Map<IFeature, Double> featureWeights = createMap(featureSet);
            // play games
            FeatureExperiment experiment = new FeatureExperiment(
                    featureSet.toString(),
                    randomSupplier,
                    () -> new AlphaBetaPlayer(new WeightedEvaluation(featureWeights), depth)
            );
            experiment.runExperiment(gamesPerFeatureSet);
        }
    }

    private static Map<IFeature, Double> createMap(FeatureSet featureSet) {
        Map<IFeature, Double> featuresWeights = IFeature.getFeatures(0.0);
        for (IFeature feature : featureSet) {
            featuresWeights.put(feature, 1.0);
        }
        return featuresWeights;
    }

    private static List<FeatureSet> createFeatureSets(List<IFeature> entries){
        List<FeatureSet> sets = new ArrayList<>();
        // allow empty features sets
        sets.add(new FeatureSet());
        for (IFeature feature : entries) {
//               for every featureSet
            List<FeatureSet> newSets = new ArrayList<>();
            for (FeatureSet set : sets) {
                //Add new FeatureSet with feature
                FeatureSet inclusiveSet = new FeatureSet(set);
                inclusiveSet.add(feature);
                newSets.add(inclusiveSet);
                //Add new FeatureSet without feature
//                FeatureSet exclusiveSet = new FeatureSet(set);
//                sets.add(exclusiveSet);
            }
            sets.addAll(newSets);
        }
        return sets;
    }

    static class FeatureSet extends ArrayList<IFeature>{
        public FeatureSet(Collection<? extends IFeature> c) {
            super(c);
        }

        public FeatureSet() {
            super();
        }

        @Override
        public String toString() {
            StringJoiner stringJoiner = new StringJoiner(".", "{", "}");
            for (IFeature iFeature : this) {
                stringJoiner.add(iFeature.getString());
            }
            return stringJoiner.toString();
        }
    }

}
