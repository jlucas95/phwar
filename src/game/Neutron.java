package game;

public class Neutron extends Piece {
    public Neutron(IPlayer owner) {
        super(0, owner);
    }

    public Neutron(Neutron n){
        super(n);
    }

    @Override
    public Neutron copy() {
        return new Neutron(this);
    }
}
