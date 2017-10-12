package game;

import util.QueryableList;

public class Move {
    public Move(Piece piece, Cell origin, Cell destination) {
        this.piece = piece;
        this.origin = origin;
        this.destination = destination;
    }

    private Piece piece;
    private Cell destination;
    private Cell origin;

    public Move(Move move) {
        this.piece = move.piece;
        this.origin = move.origin;
        this.destination = move.destination;
    }

    public Piece getPiece() {
        return piece;
    }

    public void setPiece(Piece piece) {
        this.piece = piece;
    }

    public Cell getDestination() {
        return destination;
    }

    public void setDestination(Cell destination) {
        this.destination = destination;
    }

    public Cell getOrigin() {
        return origin;
    }

    public void setOrigin(Cell origin) {
        this.origin = origin;
    }

    public void apply(GameState gameState) {
        // Move the piece
        QueryableList<Piece> pieces = new QueryableList<>(gameState.getPieces());
        Piece currentStatePiece = pieces.where(p -> piece.equals(p)).first();
        currentStatePiece.setCell(destination);
    }

    @Override
    public String toString() {
        return origin.getLabel() + "-->" + destination.getLabel();
    }
}
