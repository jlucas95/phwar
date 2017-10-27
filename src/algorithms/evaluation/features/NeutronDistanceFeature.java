package algorithms.evaluation.features;

import game.*;
import game.pieces.Neutron;
import game.pieces.Piece;
import util.QueryableList;

public class NeutronDistanceFeature implements IFeature {

    @Override
    public double score(GameState state, IPlayer player) throws FeatureException {
        try{
            Cell center = state.getBoard().getCenterCell();
            Piece neutron = new QueryableList<>(state.getPlayerPieces(player)).where(piece -> piece.getClass() == Neutron.class).single();

            CellOffset cellOffset = new CellOffset(neutron.getCell(), center);
            return -1 * cellOffset.distance();
        }
        catch (IllegalStateException e){
            throw new FeatureException();
        }
    }
}
