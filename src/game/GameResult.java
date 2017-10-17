package game;

/**
 * Created by Jan on 13-10-2017.
 */
public class GameResult {
    final int turns;
    final GameState endState;
    final IPlayer player;

    public GameResult(int turns, GameState endState, IPlayer player) {
        this.turns = turns;
        this.endState = endState;
        this.player = player;
    }

    public int getTurns() {
        return turns;
    }

    public GameState getEndState() {
        return endState;
    }

    public IPlayer getPlayer() {
        return player;
    }
}
