package game;

import game.pieces.Electron;
import game.pieces.Neutron;
import game.pieces.Piece;
import game.pieces.Positron;
import util.QueryableList;
import util.Tuple;

import java.util.*;
import java.util.List;

public class GameState {

    private Set<Piece> pieces;

    private Game game;

    IPlayer currentPlayer;



    public GameState(List<Piece> pieces, Game game, IPlayer startingPlayer) {
        this.pieces = new HashSet<>(pieces);
        this.game = game;
        this.currentPlayer = startingPlayer;
    }


    // Copy constructor
    public GameState(GameState gameState) {
        this.pieces = new HashSet<>();
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

    public Set<Piece> getPieces() {
        return pieces;
    }

    private void setPieces(Set<Piece> pieces){
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

        return new GameState(pieces, game, player1);
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
        return new GameState(pieces, game, player1);
    }

    public void apply(Move move){
        move.apply(this);
    }

    public GameState getNewState(Move move) {
        GameState newState = new GameState(this);
        if(!moveIsLegal(move)) throw new IllegalArgumentException("Move is not legal in this state.");

        newState.apply(move);
        newState.currentPlayer = game.otherPlayer(currentPlayer);
        return newState;
    }

    private boolean moveIsLegal(Move move) {
        // TODO Check if move is legal
        // Piece must belong tot the current player
        Piece piece = move.getPiece();
        // Piece must be on the origin position
        Cell origin = move.getOrigin();
        // Destination may not be occupied unless this is a capture
        Cell destination = move.getDestination();
        return true;
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

    public List<CaptureMove> getCaptures(Move move) {

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

    private List<Piece> getClosestPieces(Cell start, GameState gameState, Collection<Piece> pieces) {
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
        boolean P1defeated = !hasAllPieces(game.player1);
        boolean P2Defeated = !hasAllPieces(game.player2);
        boolean neutronInF6 = neutronInMiddle();
        
        boolean b = P1defeated || P2Defeated || neutronInF6;
        return b;
    }

    public IPlayer getWinner(){
        IPlayer player1 = game.player1;
        IPlayer player2 = game.player2;
        if(this.hasAllPieces(player1) && !this.hasAllPieces(player2)) return player1;
        else if(!this.hasAllPieces(player1) && this.hasAllPieces(player2)) return player2;
        else if(this.neutronInMiddle()) return this.getNeutronInMiddle().getOwner();
        throw new IllegalStateException("End of game reached while both players have one of each piece.");
    }

    public boolean neutronInMiddle() {
        return getNeutronInMiddle() != null;
    }

    public Piece getNeutronInMiddle(){
        try {
            return new QueryableList<>(pieces)
                    .where(p->   p.getCell().getLabel().equals("F6")
                            && p.getClass() == Neutron.class)
                    .single();
        } catch (IllegalStateException ex){
            return null;
        }
    }

    @Override
    public int hashCode() {
        return pieces.hashCode();
    }

    public boolean hasAllPieces(IPlayer player){
        int electrons = 0;
        int neutrons = 0;
        int positrons = 0;
        for (Piece piece : getPlayerPieces(player)) {
            if(piece.getClass() == Electron.class) electrons++;
            if (piece.getClass() == Neutron.class) neutrons++;
            if (piece.getClass() == Positron.class) positrons++;
        }
        return electrons != 0 && neutrons != 0 && positrons != 0;
    }

    public IPlayer getCurrentPlayer() {
        return currentPlayer;
    }

    public List<Cell> getLoS(Piece piece) {
        ArrayList<Cell> cells = new ArrayList<>();
        Cell origin = piece.getCell();
        for (CellDirection direction : CellDirection.values()) {
            Cell currentCell = origin;
            boolean canContinue = true;
            while(canContinue){
                try{
                    currentCell = currentCell.getNeigbour(direction);
                    if(currentCell != null && !cellOccupied(currentCell)){
                        canContinue = false;
                    }
                    else{
                        cells.add(currentCell);
                    }
                }
                catch (NullPointerException e){
                    canContinue = false;
                }
                // stop if cell is occupied or if null
            }
        }
        return cells;
    }

    public Board getBoard() {
        return game.board;
    }

    public IPlayer getOpponent(IPlayer player) {
        return game.otherPlayer(player);
    }
}
