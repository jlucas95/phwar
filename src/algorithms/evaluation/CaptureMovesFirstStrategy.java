package algorithms.evaluation;

import algorithms.moveOrdering.CaptureMoveFirstOrdering;
import algorithms.moveOrdering.CaptureMoveOrdering;
import game.Move;

import java.util.List;

public class CaptureMovesFirstStrategy implements IPlayStrategy{
    @Override
    public Move pickMove(List<Move> moves) {
        moves.sort(new CaptureMoveFirstOrdering());
        return moves.get(0);
    }
}
