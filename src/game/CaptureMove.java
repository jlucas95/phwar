package game;

import util.QueryableList;

/**
 * Created by Jan on 2-10-2017.
 */
public class CaptureMove extends Move {
    Piece capturedPiece;

    Piece capturingPiece;
    CaptureMove followCapture = null;

    boolean isFollowCapture;

    public CaptureMove(Move move, Piece piece, Piece playerPiece) {
        super(move);
        isFollowCapture = move.getClass() == CaptureMove.class;
        this.capturedPiece = piece;
        this.capturingPiece = playerPiece;
    }


    // copy constructor
    public CaptureMove(CaptureMove m){
        super(m);
        this.capturedPiece = m.capturedPiece;
        this.capturingPiece = m.capturingPiece;
        this.followCapture = m.followCapture;
    }

    @Override
    public void apply(GameState gameState){
        // Moves the piece
        if(!isFollowCapture) super.apply(gameState);
        // remove the captured piece
        QueryableList<Piece> pieces = new QueryableList<>(gameState.getPieces());
        Piece piece = pieces.where(p -> capturedPiece.equals(p)).first();
        gameState.removePiece(piece);
        // Move the capturing piece
        Move move = new Move(capturingPiece, capturingPiece.getCell(), capturedPiece.getCell());
        gameState.apply(move);
    }
    @Override
    public Cell getDestination(){
        return capturedPiece.getCell();
    }
    public Cell getMoveDestination(){
        return super.getDestination();
    }

    @Override
    public String toString(){
        String s = capturingPiece.getCell().getLabel() + "-->" + capturedPiece.getCell().getLabel();
        if(!isFollowCapture) s = super.toString() + " || " + s;
        if(followCapture != null) s += " || " + followCapture.toString();
        return s;
    }

    public Piece getCapturedPiece() {
        return capturedPiece;
    }

    public Piece getCapturingPiece() {
        return capturingPiece;
    }

    public CaptureMove getFollowCapture() {
        return followCapture;
    }
}
