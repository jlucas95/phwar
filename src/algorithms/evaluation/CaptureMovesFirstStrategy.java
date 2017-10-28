package algorithms.evaluation;

import algorithms.moveOrdering.CaptureMoveFirstOrdering;
import algorithms.moveOrdering.CaptureMoveOrdering;
import game.CaptureMove;
import game.Move;

import java.util.List;
import java.util.Random;

public class CaptureMovesFirstStrategy implements IPlayStrategy{
    Random random = new Random();

    @Override
    public Move pickMove(List<Move> moves) {
        moves.sort(new CaptureMoveFirstOrdering());
        Move move = moves.get(0);
        if(move.getClass() == CaptureMove.class){
            return move;
        }
        else return moves.get(random.nextInt(moves.size()));

    }
}
