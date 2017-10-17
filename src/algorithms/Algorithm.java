package algorithms;

import game.GameState;
import game.Player;

/**
 * Created by Jan on 13-10-2017.
 */
public abstract class Algorithm extends Player{

    public boolean terminalNode(GameState s) {
        return s.isEndgame();
    }
}
