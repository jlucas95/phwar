package algorithms;

import algorithms.features.IFeature;
import game.GameState;
import game.IPlayer;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class WeightedEvaluation implements EvaluationFunction, Serializable {

    private Map<IFeature, Double> features;

    public WeightedEvaluation(Map<IFeature, Double> features) {
        this.features = features;
    }

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
