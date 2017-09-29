package game;

import java.util.ArrayList;
import java.util.List;

public abstract class Player {
    abstract Move getMove(Board board);
    public List<Piece> getPieces(){
        return pieces;
    }

    private List<Piece> pieces;

    void setPieces(List<Piece> pieces){
        this.pieces = pieces;
    }

    public Player(List<Piece> pieces){
        setPieces(pieces);
    }
}
