package game;



import game.UI.GamePanel;

import javax.swing.*;
import java.awt.*;


public class Game {

    private final boolean log = false;

    IPlayer player1;
    IPlayer player2;

    GamePanel panel;

    Board board;
    int turn;

    private final long showMilliseconds = 10_000;

    public GameState state;

    public Game(Board board, IPlayer player1, IPlayer player2, boolean UI){
        if(UI){
            initUI();
        }
        turn = 1;
        this.board = board;
        // Set up pieces
        state = GameState.getStartState(player1, player2, this);
        setPlayer1(player1);
        setPlayer2(player2);
    }

    public Game(Board board, IPlayer player1, IPlayer player2){
        this(board, player1, player2, false);
    }

    private void initUI() {
        JFrame frame = new JFrame();
        panel = new GamePanel(this);
        frame.add(panel);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Dimension dimension = new Dimension(600, 600);
        panel.setPreferredSize(dimension);
        frame.pack();
        frame.setVisible(true);
        panel.setVisible(true);
    }

    private void drawState(GameState state){
        // skip drawing if UI is not initialized
        if(panel == null) return;
        panel.setState(state);
        panel.repaint();
    }



    public void setPlayer1(IPlayer player1) {
        this.player1 = player1;
        player1.setGame(this);
    }

    public void setPlayer2(IPlayer player2) {
        this.player2 = player2;
        player2.setGame(this);
    }

    public GameResult play(){
        boolean done = false;
        IPlayer currentPlayer = player1;
        while (!done){
            drawState(state);
            if(log) System.out.println("playing turn " + turn);
            long start = System.nanoTime();
            Move move = currentPlayer.getMove(state);
            long duration = System.nanoTime() - start;
            if(log) System.out.println(currentPlayer + " took " + duration/1_000_000 + " ms for move: " + move);
             state = state.getNewState(move);

            // determine end of game.
            if(state.isEndgame()){done = true;}
            //switch players
            currentPlayer = otherPlayer(currentPlayer);
            turn++;
            drawMove(move);
            show(start);
        }
        return new GameResult(turn, state, determineWinner());
    }

    private void show(long start) {
        if (panel == null) return;
        long now = System.nanoTime();
        // convert from nano to milliseconds
        long millisPassed = (now - start)/1_000_000;

        long showTimeLeft = this.showMilliseconds - millisPassed;
        if(showTimeLeft > 0){
            try{
                Thread.sleep(showTimeLeft);
            }
            catch (InterruptedException e){
                e.printStackTrace();
            }
        }

    }

    private void drawMove(Move move) {
        if(panel == null) return;
        panel.setMove(move);
    }

    private IPlayer determineWinner() {
        if(state.hasAllPieces(player1) && !state.hasAllPieces(player2)) return player1;
        else if(!state.hasAllPieces(player1) && state.hasAllPieces(player2)) return player2;
        else if(state.neutronInMiddle()) return state.getNeutronInMiddle().getOwner();
        throw new IllegalStateException("End of game reached while both players have one of each piece.");
    }


    public IPlayer otherPlayer(IPlayer currentPlayer) {
        return currentPlayer == player1 ? player2 : player1;
    }


    public IPlayer getP1() {
        return player1;
    }
}
