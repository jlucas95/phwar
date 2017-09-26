package game;

public class Game {

    Player player1;
    Player player2;

    Board board;
    int turn;

    public Game(Board board, Player p1, Player p2){
        player1 = p1;
        player2 = p2;
        turn = 0;
        this.board = board;
        // Set up pieces
    }

    public void play(){
        boolean done = false;
        Player currentPlayer = player1;
        while (!done){
            Move move = currentPlayer.getMove(board);
            board.apply(move);
            //checkCapture(move);

            // determine end of game.
            // if(endgameCheck()){done = true;}
            //switch players
            if (currentPlayer == player1) currentPlayer = player2;
            else currentPlayer = player1;
        }
    }
}
