package algorithms.features;

import game.*;
import util.QueryableList;

public class NeutronDistanceFeature implements IFeature {

    @Override
    public double score(GameState state, IPlayer player) {
        Cell center = state.getBoard().getCenterCell();
        Piece neutron = new QueryableList<>(state.getPlayerPieces(player)).where(piece -> piece.getClass() == Neutron.class).single();

        CellOffset cellOffset = new CellOffset(neutron.getCell(), center);
        return cellOffset.distance();


    }
}
