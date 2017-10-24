package algorithms.evaluation.moveOrdering;

import game.Move;

import java.io.Serializable;
import java.util.Comparator;

public interface IMoveOrdering extends Comparator<Move>, Serializable {
}
