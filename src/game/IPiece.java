package game;

/**
 * Created by Jan on 12-10-2017.
 */
public interface IPiece {


    public int getCharge();

    public Cell getCell();

    public void setCell(Cell cell);

    public IPlayer getOwner();
}
