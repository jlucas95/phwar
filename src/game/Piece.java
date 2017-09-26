package game;

public abstract class Piece {

    private int charge;

    public Piece(int charge) {
        this.charge = charge;
    }

    public int getCharge(){
        return charge;
    }
}
