package algorithms;

import game.*;
import util.QueryableList;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Scanner;

/**
 * Created by Jan on 19-10-2017.
 */
public class ManualPlayer implements IPlayer {

    Game game;
    private final Scanner reader;

    @Override
    public void setGame(Game game) {
        this.game = game;
    }

    public ManualPlayer() {

        reader = new Scanner(System.in);
        reader.useDelimiter("\n");
    }

    @Override
    public Move getMove(GameState state) {

        boolean goodInput = false;
        Move returnMove = null;
        while (!goodInput){
            String[] cells = askCells("Enter move command");

            String origin = cells[0].toUpperCase();
            String destination = cells[1].toUpperCase();
            try{
                Piece piece = new QueryableList<>(state.getPieces()).where(p -> p.getCell().getLabel().equals(origin) && p.getOwner() == this).single();
                Board board = state.getBoard();
                Cell originCell = board.getCell(origin);
                Cell destinationCell = board.getCell(destination);
                Move move = new Move(piece, originCell, destinationCell);
                // TODO check move validity...
                returnMove = askCapture(move, state);
                goodInput = true;
            }
            catch (IllegalStateException e){
                System.out.println("No owned piece on that cell");
            }
            catch (IllegalArgumentException e){
                System.out.println(e.getMessage());
            }
        }
        return returnMove;
    }


    private Move askCapture(Move move, GameState state){
        List<CaptureMove> captures = state.getCaptures(move);
        if(captures.size() != 0){
            boolean goodInput = false;
            while(!goodInput){
                String[] strings = askCells("Enter capture move");
                String origin  = strings[0];
                String destination = strings[1];
                try{
                    CaptureMove single = new QueryableList<>(captures).where(c -> c.getOrigin().getLabel().equals(origin) && c.getDestination().getLabel().equals(destination)).single();
                    GameState gameState = new GameState(state);
                    gameState.apply(single);
                    return askCapture(single, gameState);
                }
                catch (IllegalStateException e){
                    System.out.println("Not a valid capture move");
                }
            }


        }
        return move;
    }

    private String[] askCells(String message){
        boolean correct = false;
        String[] cells = null;
        while (!correct){
            System.out.println(message);
            String next = reader.next();
            cells = next.split("x");
            if (cells.length != 2) System.out.println("Invalid move command");
            else{
                correct = true;
            }
        }
        return cells;
    }
}
