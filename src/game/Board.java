package game;

import algorithms.RandomPlayer;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.ArrayList;
import java.util.List;

public class Board {

    ArrayList<Cell> cells = new ArrayList<>();

    public Board(ArrayList<Cell> cells) {
        this.cells = cells;
    }

    public void apply(Move move) {

    }

    public List<Move> getMoves(RandomPlayer randomPlayer) {
        List<Piece> pieces = randomPlayer.getPieces();
        ArrayList<Move> moves = new ArrayList<>();
        for (Piece piece : pieces) {
            List<Move> pieceMoves = getMoves(piece);
            moves.addAll(pieceMoves);
        }
        return moves;
    }

    private List<Move> getMoves(Piece piece) {
        // do stuff
    }
}
