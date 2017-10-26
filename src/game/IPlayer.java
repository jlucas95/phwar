package game;

public interface IPlayer {

    void setGame(Game game);

    Game getGame();

    Move getMove(GameState state);

}
