package algorithms.moveOrdering;

import game.CaptureMove;
import game.GameState;
import game.Move;
import util.Tuple;


// Moves capture moves to the front of the list
public class CaptureMoveOrdering implements IMoveOrdering {

    @Override
    public int compare(Tuple<Move, GameState> t1, Tuple<Move, GameState> t2) {
        Move move1 = t1.getFirst();
        Move move2 = t2.getFirst();
        boolean move1Capture = move1.getClass() == CaptureMove.class;
        boolean move2Capture = move2.getClass() == CaptureMove.class;

        if(move1Capture){
            if (move2Capture){
                // if both moves are captures -> check if move 1 has a bigger capture chain
                int move1Captures = captureChainSize((CaptureMove) move1);
                int move2Captures = captureChainSize((CaptureMove) move2);

                return Integer.compare(move2Captures, move1Captures);
            }
            // move 1 is a captureMove and move 2 not
            return -1;
        }
        // Move 1 is not a capture
        else{
            // if move2 is a capture
            if(move2Capture) return 1;
            else return 0;
        }
    }

    private int captureChainSize(CaptureMove capture){
        int count = 1;
        if(capture.getFollowCapture() != null){
            count += captureChainSize(capture.getFollowCapture());
        }
        return count;

    }

}
