package game;

/**
 * Created by Jan on 13-10-2017.
 */
public class GameResult {
    final int turns;
    final GameState endState;
    final IPlayer winner;

    public GameResult(int turns, GameState endState, IPlayer winner) {
        this.turns = turns;
        this.endState = endState;
        this.winner = winner;
    }

    public int getTurns() {
        return turns;
    }

    public GameState getEndState() {
        return endState;
    }

    public IPlayer getWinner() {
        return winner;
    }

    @Override
    public String toString() {
        return "Player " + winner + " wins in turn " + turns;
     }
}
