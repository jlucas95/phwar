package game;

public class Positron extends Piece {
    public Positron(IPlayer owner) {
        super(1, owner);
    }

    public Positron(Positron p){
        super(p);
    }

    @Override
    public Positron copy() {
        return new Positron(this);
    }
}
