package algorithms.moveOrdering;

import game.GameState;
import game.Move;
import util.Tuple;

import java.io.Serializable;
import java.util.Comparator;

public interface IMoveOrdering extends Comparator<Tuple<Move, GameState>>, Serializable {
}
