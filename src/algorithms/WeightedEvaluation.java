package algorithms;

import algorithms.features.IFeature;
import game.GameState;
import game.IPlayer;

import java.util.HashMap;
import java.util.Map.Entry;

public class WeightedEvaluation implements EvaluationFunction {

    private HashMap<IFeature, Double> features;


    @Override
    public int evaluate(GameState state, IPlayer player) {
        double score = 0;
        for (Entry<IFeature, Double> featureDoubleEntry : features.entrySet()) {
            IFeature feature = featureDoubleEntry.getKey();
            Double weight = featureDoubleEntry.getValue();
            score += weight * feature.score(state, player);
        }
        return (int) Math.round(score);
    }
}
