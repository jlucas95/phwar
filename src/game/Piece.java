package game;

public abstract class Piece implements IPiece{

    private Cell cell;

    private int charge;

    private IPlayer owner;

    private int ID;

    static int currentID = 0;

    public Piece(int charge, IPlayer owner) {
        this.charge = charge;
        this.owner = owner;
        this.ID = currentID++;
    }

    public Piece(Piece piece){
        this.cell = piece.cell;
        this.charge = piece.charge;
        this.owner = piece.owner;
        this.ID = piece.ID;
    }

    public int getCharge(){
        return charge;
    }

    @Override
    public String toString() {
        return "Piece{" +
                "cell=" + cell +
                ", charge=" + charge +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Piece piece = (Piece) o;

        if (charge != piece.charge) return false;
        if (ID != piece.ID) return false;
        return cell.equals(piece.cell);
    }

    @Override
    public int hashCode() {
        int result = cell.hashCode();
        result = 31 * result + charge;
        result = 31 * result + ID;
        return result;
    }

    public Cell getCell() {
        return cell;
    }

    public void setCell(Cell cell) {
        this.cell = cell;
    }

    public IPlayer getOwner() {
        return owner;
    }

    public abstract Piece copy();

    public boolean equals(Piece p){
        return this.ID == p.ID;
    }



}
