package game;

import java.util.ArrayList;
import java.util.List;

public interface IPlayer {

    public void setGame(Game game);

    public Move getMove(GameState state);

}
