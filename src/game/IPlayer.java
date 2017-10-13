package game;

public interface IPlayer {

    void setGame(Game game);

    Move getMove(GameState state);

}
