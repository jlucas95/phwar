package algorithms.evaluation;

import algorithms.evaluation.features.FeatureException;
import algorithms.evaluation.features.IFeature;
import game.GameState;
import game.IPlayer;

import java.io.Serializable;
import java.util.Map;
import java.util.Map.Entry;

public class WeightedEvaluation implements IEvaluationFunction, Serializable {

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
            double featureScore;
            try {
                featureScore = feature.score(state, player);
            }
            catch(FeatureException e){
                featureScore = 0;
            }
            score += weight *featureScore;
        }
        return (int) Math.round(score);
    }
}
