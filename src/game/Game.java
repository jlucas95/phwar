package game;

import java.util.ArrayList;
import java.util.List;

public class Game {

    Player player1;
    Player player2;

    Board board;
    int turn;

    GameState state;

    public Game(Board board){
        turn = 0;
        this.board = board;
        // Set up pieces
        setUpPieces();
    }

    private void setUpPieces(){
        state = GameState.getStartState(player1, player2);

    }

    public void setPlayer1(Player player1) {
        this.player1 = player1;
        player1.setGame(this);
    }

    public void setPlayer2(Player player2) {
        this.player2 = player2;
        player2.setGame(this);
    }

    public void play(){
        boolean done = false;
        Player currentPlayer = player1;
        while (!done){
            Move move = currentPlayer.getMove(board);
            state = Game.apply(move);

            // determine end of game.
            if(endgameCheck(otherPlayer(currentPlayer))){done = true;}
            //switch players
            currentPlayer = otherPlayer(currentPlayer);
        }
    }

    private static GameState apply(Move move) {
        return null;
    }

    private Player otherPlayer(Player currentPlayer) {
        return currentPlayer == player1 ? player2 : player1;
    }

    private boolean endgameCheck(Player player) {
        // game ends if any player does not have all types of pieces left.
        int electrons = 0;
        int neutrons = 0;
        int positrons = 0;
        for (Piece piece : player.getPieces()) {
            if(piece.getClass() == Electron.class) electrons++;
            if (piece.getClass() == Neutron.class) neutrons++;
            if (piece.getClass() == Positron.class) positrons++;
        }
        return electrons == 0 || neutrons == 0 || positrons == 0;
    }

    private void checkCapture(Move move) {

    }


    public List<Move> getMoves(Player randomPlayer) {
        List<Piece> pieces = randomPlayer.getPieces();
        ArrayList<Move> moves = new ArrayList<>();
        for (Piece piece : pieces) {
            List<Move> pieceMoves = getMoves(piece);
            moves.addAll(pieceMoves);
        }
        return moves;
    }

    private List<Move> getMoves(Piece piece) {
        ArrayList<Move> moves = new ArrayList<>();
        Cell start = piece.getCell();
        for (CellDirection direction : CellDirection.values()) {
            Cell origin = start;
            boolean canContinue = true;
            while(canContinue) {
                Cell destination = origin.getNeigbour(direction);
                if (destination != null || !destination.getLabel().equals("F6")) {
                    // TODO: Check for capture situation
                    Move move = new Move(piece, origin, destination);
                    moves.add(move);
                    origin = destination;
                }
                else{ canContinue = false; }
            }
        }
        return moves;
    }

}
