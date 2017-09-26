package game;

public abstract class Piece {

    int charge;

    public Piece(int charge) {
        this.charge = charge;
    }

    public int getCharge(){
        return charge;
    }
}
