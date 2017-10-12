package game;

public class Game {

    IPlayer player1;
    IPlayer player2;

    Board board;
    int turn;

    public GameState state;

    public Game(Board board, IPlayer player1, IPlayer player2){
        turn = 0;
        this.board = board;
        // Set up pieces
        state = GameState.getStartState(player1, player2, this);
        setPlayer1(player1);
        setPlayer2(player2);
    }

    public void setPlayer1(IPlayer player1) {
        this.player1 = player1;
        player1.setGame(this);
    }

    public void setPlayer2(IPlayer player2) {
        this.player2 = player2;
        player2.setGame(this);
    }

    public void play(){
        boolean done = false;
        IPlayer currentPlayer = player1;
        while (!done){
            Move move = currentPlayer.getMove(state);
            state = state.getNewState(move);

            // determine end of game.
            if(state.isEndgame()){done = true;}
            //switch players
            currentPlayer = otherPlayer(currentPlayer);
        }
    }


    public IPlayer otherPlayer(IPlayer currentPlayer) {
        return currentPlayer == player1 ? player2 : player1;
    }




}
