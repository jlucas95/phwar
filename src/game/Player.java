package game;

/**
 * Created by Jan on 13-10-2017.
 */
public abstract class Player implements IPlayer {

    public Game game;
    @Override
    public void setGame(Game game) {
        this.game = game;
    }
}
