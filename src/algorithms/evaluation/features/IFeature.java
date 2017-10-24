package algorithms.evaluation.features;

import game.GameState;
import game.IPlayer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public interface IFeature {
    public double score(GameState state, IPlayer player) throws FeatureException;

    public static Map<IFeature, Double> getFeatures(double defaultWeight){
        ArrayList<IFeature> features = new ArrayList<>();
        features.add(new ElectronFeature());
        features.add(new HexControlFeature());
        features.add(new NeutronDistanceFeature());
        features.add(new NeutronFeature());
        features.add(new PieceFeature());
        features.add(new PositronFeature());
        features.add(new PossibleMovesFeature());
        features.add(new PossibleCapturesFeature());

        HashMap<IFeature, Double> map = new HashMap<>();
        for (IFeature feature : features) {
            map.put(feature, defaultWeight);
        }
        return map;
    }
}
