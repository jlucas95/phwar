package algorithms.evaluation.features;

import game.Cell;
import game.GameState;
import game.IPlayer;
import game.pieces.Piece;
import util.*;

import java.util.HashSet;
import java.util.Set;

/**
 * Scores a state based on the hexagons currently in line of sight for this player
 */
public class HexControlFeature implements IFeature {

    @Override
    public double score(GameState state, IPlayer player) {
        IGrouping<IPlayer, Piece> groups = new QueryableList<>(state.getPieces()).groupBy(Piece::getOwner);
        HashSet<Cell> playerCells = new HashSet<>();
        HashSet<Cell> opponentCells = new HashSet<>();

        for (Group<IPlayer, Piece> group : groups) {
            Set<Cell> destination;
            if(group.getKey() == player) destination = playerCells;
            else destination = opponentCells;

            group.forEach(p -> destination.addAll(state.getLoS(p)));
        }
        return playerCells.size() - opponentCells.size();
    }
}
