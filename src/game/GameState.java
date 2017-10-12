package game;

import util.QueryableList;
import util.Tuple;

import java.util.*;

public class GameState {

    private List<Piece> pieces;

    private Game game;



    public GameState(List<Piece> pieces, Game game) {
        this.pieces = pieces;
        this.game = game;
    }


    // Copy constructor
    public GameState(GameState gameState) {
        this.pieces = new ArrayList<>();
        this.game = gameState.game;
        for (Piece piece : gameState.pieces) {
            this.pieces.add(piece.copy());
        }
        
    }

    public List<Piece> getPlayerPieces(IPlayer player){
        ArrayList<Piece> playerPieces = new ArrayList<>();
        for (Piece piece : pieces) {
            if(player.equals(piece.getOwner())){
                playerPieces.add(piece);
            }
        }
        return playerPieces;
    }

    private void setPieces(List<Piece> pieces){
        this.pieces = pieces;
    }

    public static GameState getTestState(IPlayer player1, IPlayer player2, Game game){
        Board board = game.board;
        ArrayList<Piece> pieces = new ArrayList<>();

        Electron electron = new Electron(player1);
        electron.setCell(board.getCell(-1, 1, 0));
        pieces.add(electron);

        Positron positron = new Positron(player1);
        positron.setCell(board.getCell(-1, -2, 3));
        pieces.add(positron);

        Neutron neutron = new Neutron(player1);
        neutron.setCell(board.getCell(0, 5, -5));
        pieces.add(neutron);

        Electron electron1 = new Electron(player2);
        electron1.setCell(board.getCell(-1, 4, -3));
        pieces.add(electron1);

        Electron electron2 = new Electron(player2);
        electron2.setCell(board.getCell(-4, 1, 3));
        pieces.add(electron2);

        Positron positron1 = new Positron(player2);
        positron1.setCell(board.getCell(4, -4, 0));
        pieces.add(positron1);

        Neutron neutron1 = new Neutron(player2);
        neutron1.setCell(board.getCell(0, -5, 5));
        pieces.add(neutron1);

        return new GameState(pieces, game);
    }

    public static GameState getStartState(IPlayer player1, IPlayer player2, Game game ) {
        Board board = game.board;
        int[][] electronPositions = new int[][] {{-2, 5, -3}, {0, 4, -4}, {0, 3, -3}, {2, 3, -5}};
        int[][] positronPositions = new int[][] {{-1, 5, -4}, {-1, 4, -3}, {1, 4, -5}, {1, 3, -4}};
        int[] neutronPosition = new int[] {0, 5, -5};

        ArrayList<Piece> pieces = new ArrayList<>();
        for (int[] electronPosition : electronPositions) {
            Electron electron = new Electron(player1);
            Cell cell = board.getCell(electronPosition[0], electronPosition[1], electronPosition[2]);
            electron.setCell(cell);
            pieces.add(electron);
        }
        for (int[] positronPosition : positronPositions) {
            Positron positron = new Positron(player1);
            Cell cell = board.getCell(positronPosition[0], positronPosition[1], positronPosition[2]);
            positron.setCell(cell);
            pieces.add(positron);
        }
        Neutron neutron = new Neutron(player1);
        neutron.setCell(board.getCell(neutronPosition[0], neutronPosition[1], neutronPosition[2]));
        pieces.add(neutron);

    // negating the coordinates makes them flip around the center cell
        ArrayList<Piece> newPieces = new ArrayList<>();
        for (Piece piece : pieces) {
            Piece p;
            if(piece.getClass()==Electron.class) p = new Electron(player2);
            else if(piece.getClass()== Positron.class) p = new Positron(player2);
            else if(piece.getClass()== Neutron.class) p = new Neutron(player2);
            else{throw new IllegalStateException("Class not found!");}
            Coordinate coordinate = new Coordinate(piece.getCell().getCoordinate());
            coordinate.negate();
            p.setCell(board.getCell(coordinate));
            newPieces.add(p);
        }
        pieces.addAll(newPieces);
        return new GameState(pieces, game);
    }

    public void apply(Move move){
        move.apply(this);
    }

    public GameState getNewState(Move move) {
        GameState newState = new GameState(this);
        newState.apply(move);
        return newState;
    }

    public List<Piece> getPieces() {
        return pieces;
    }

    public List<Move> getMoves(IPlayer player) {
        List<Piece> pieces = getPlayerPieces(player);
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
                if (destination != null && !cellOccupied(destination)) {
                    //check if piece is already on cell.
                    Move move = new Move(piece, start, destination);
                    origin = destination;
                    List<CaptureMove> captures = getCaptures(move);
                    if(captures.size() == 0){
                        moves.add(move);
                    }
                    else{
                        moves.addAll(captures);
                    }

                }
                else{ canContinue = false; }
                if (destination != null && destination.getLabel().equals("F6")){ canContinue = false;}
            }
        }
        return moves;
    }

    private boolean cellOccupied(Cell destination) {
       return new QueryableList<>(pieces).select(p -> p.getCell())
               .where(c-> c.equals(destination))
               .size() != 0;
    }

    private List<CaptureMove> getCaptures(Move move) {

        ArrayList<CaptureMove> captureMoves = new ArrayList<>();
        GameState newState = getNewState(move);
        IPlayer currentPlayer = move.getPiece().getOwner();
        IPlayer opponent = game.otherPlayer(currentPlayer);
        Cell origin = move.getDestination();
        // Closest opponent pieces that are in line of sight
        List<Piece> closestPieces = getClosestPieces(origin, newState, newState.getPieces());
        closestPieces.removeIf(p -> p.getOwner() == currentPlayer);

        for (Piece piece : closestPieces) {

            QueryableList<Piece> closest = new QueryableList<>(getClosestPieces(piece.getCell(), newState, newState.getPieces()));
            QueryableList<Piece> playerPieces = closest.where(p -> p.getOwner() == currentPlayer);
            //at least 2 of currentplayers pieces
            if(playerPieces.size() >= 2){
                int sum = 0;
                for (Piece closestPiece : closest) {
                    sum += closestPiece.getCharge();
                }
                // total charge == 0
                if(sum == 0){
                    for (Piece playerPiece : playerPieces){
                        // Create new captureMove
                        CaptureMove captureMove = new CaptureMove(move, piece, playerPiece);
                        // See if we can capture as a result of this capture.
                        GameState afterCaptureState = new GameState(newState);
                        List<CaptureMove> captures = afterCaptureState.getCaptures(captureMove);
                        // If we can, add these captures.
                        if(captures.size() > 0){
                            for (CaptureMove followCapture : captures) {
                                CaptureMove capture = new CaptureMove(captureMove);
                                capture.followCapture = followCapture;
                                captureMoves.add(capture);
                            }
                        } // Otherwise we just use this one
                        else {captureMoves.add(captureMove);}
                    }
                }
            }

        }
        return captureMoves;
    }

    private List<Piece> getClosestPieces(Cell start, GameState gameState, List<Piece> pieces) {
        HashMap<CellDirection, Tuple<Piece, Integer>> closestPiece = new HashMap<>();
        for (CellDirection direction : CellDirection.values()) closestPiece.put(direction, null);

        // for every direction -> find closest opponent piece.
        // loop over pieces
        for (Piece piece : pieces) {
            // check if in line of sight
            CellOffset offset = new CellOffset(start, piece.getCell());
            if(offset.LOS()){
                // get direction and distance
                CellDirection direction = offset.getDirection();
                Tuple<Piece, Integer> tuple = closestPiece.get(direction);
                int distance = offset.distance();
                if(tuple == null || distance < tuple.getSecond()){
                    closestPiece.put(direction, new Tuple<>(piece, distance));
                }
            }
        }
        Collection<Tuple<Piece, Integer>> values = closestPiece.values();
        values.removeIf(Objects::isNull);
        ArrayList<Piece> closestPieces = new ArrayList<>();
        values.forEach(tuple -> closestPieces.add(tuple.getFirst()));
        return closestPieces;
    }

    public boolean removePiece(Piece piece) {
        return pieces.remove(piece);
    }

    public List<Tuple<Move, GameState>> getSuccessors(IPlayer player) {
        ArrayList<Tuple<Move, GameState>> successors = new ArrayList<>();

        List<Move> moves = getMoves(player);
        for (Move move : moves) {
            successors.add(new Tuple<>(move, getNewState(move)));
        }
        return successors;
    }

    public boolean isEndgame() {
        return !hasAllPieces(game.player1) || !hasAllPieces(game.player2);
    }

    private boolean hasAllPieces(IPlayer player){
        int electrons = 0;
        int neutrons = 0;
        int positrons = 0;
        for (Piece piece : getPlayerPieces(player)) {
            if(piece.getClass() == Electron.class) electrons++;
            if (piece.getClass() == Neutron.class) neutrons++;
            if (piece.getClass() == Positron.class) positrons++;
        }
        return electrons == 0 || neutrons == 0 || positrons == 0;
    }
}
