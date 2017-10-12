package game;

public class Move {
    public Move(Piece piece, Cell origin, Cell destination) {
        this.piece = piece;
        this.origin = origin;
        this.destination = destination;
    }

    private Piece piece;
    private Cell destination;
    private Cell origin;
}
