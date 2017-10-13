package game;

/**
 * Created by Jan on 12-10-2017.
 */
public interface IPiece {


    int getCharge();

    Cell getCell();

    void setCell(Cell cell);

    IPlayer getOwner();
}
