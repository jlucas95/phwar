package game;

public class Game {

    IPlayer player1;
    IPlayer player2;

    Board board;
    int turn;

    public GameState state;

    public Game(Board board, IPlayer player1, IPlayer player2){
        turn = 1;
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

    public GameResult play(){
        boolean done = false;
        IPlayer currentPlayer = player1;
        while (!done){
            System.out.println("playing turn " + turn);
            long start = System.nanoTime();
            Move move = currentPlayer.getMove(state);
            long duration = System.nanoTime() - start;
            System.out.println(currentPlayer + " took " + duration/1_000_000 + " ms for move: " + move);
             state = state.getNewState(move);

            // determine end of game.
            if(state.isEndgame()){done = true;}
            //switch players
            currentPlayer = otherPlayer(currentPlayer);
            turn++;
        }
        return new GameResult(turn, state, determineWinner());
    }

    private IPlayer determineWinner() {
        if(state.hasAllPieces(player1) && !state.hasAllPieces(player2)) return player1;
        else if(!state.hasAllPieces(player1) && state.hasAllPieces(player2)) return player2;
        throw new IllegalStateException("End of game reached while both players have one of each piece.");
    }


    public IPlayer otherPlayer(IPlayer currentPlayer) {
        return currentPlayer == player1 ? player2 : player1;
    }




}
