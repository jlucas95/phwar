package algorithms.evaluation.features;

import game.GameState;
import game.IPlayer;
import game.pieces.Piece;
import util.Group;
import util.IGrouping;
import util.QueryableList;

/**
 * Created by Jan on 19-10-2017.
 */
public abstract class PieceTypeFeature implements IFeature{


    public double scoreType(GameState state, IPlayer player, Class type) {
        IGrouping<IPlayer, Piece> groups = new QueryableList<>(state.getPieces()).where(p -> p.getClass() == type).groupBy(Piece::getOwner);
        int own = 0;
        int opponent = 0;
        for (Group<IPlayer, Piece> group : groups) {
            if (group.getKey() == player) {
                own += group.size();
            }
            else{
                opponent += group.size();
            }
        }
        return own - opponent;
    }
}
