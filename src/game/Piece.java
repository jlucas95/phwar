package game;

public abstract class Piece {

    private Cell cell;

    private int charge;

    private Player owner;

    public Piece(int charge, Player owner) {
        this.charge = charge;
        this.owner = owner;
    }

    public int getCharge(){
        return charge;
    }

    public Cell getCell() {
        return cell;
    }

    public void setCell(Cell cell) {
        this.cell = cell;
    }

    public Player getOwner() {
        return owner;
    }
}
