package algorithms.moveOrdering;

import game.CaptureMove;
import game.Move;


// Moves capture moves to the front of the list
public class CaptureMoveOrdering implements IMoveOrdering {

    @Override
    public int compare(Move move1, Move move2) {
        // if move 1 is capture move

        // if move 2 is capture move

        // if both are capture moves
        if(move1.getClass() == CaptureMove.class && move2.getClass() == CaptureMove.class){
            
        }
    }
}
