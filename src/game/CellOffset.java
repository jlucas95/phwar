package game;

import java.util.function.DoubleConsumer;
import java.util.function.IntFunction;

/**
 * Represents the difference between 2 cells
 */
public class CellOffset {

    private int deltaX;
    private int deltaY;
    private int deltaZ;

    public CellOffset(int x, int y, int z) {
        this.deltaX = x;
        this.deltaY = y;
        this.deltaZ = z;
    }

    public CellOffset(Cell cell1, Cell cell2){
        this.deltaX = cell1.getX() - cell2.getX();
        this.deltaY = cell1.getY() - cell2.getY();
        this.deltaZ = cell1.getZ() - cell2.getZ();
    }

    public int distance(){
        return (Math.abs(deltaX) + Math.abs(deltaY) + Math.abs(deltaZ)) / 2;
    }

    public Cell apply(Cell c){
        Cell clone = c.clone();
        clone.setX(clone.getX() + deltaX);
        clone.setY(clone.getY() + deltaY);
        clone.setZ(clone.getZ() + deltaZ);
        return clone;
    }

    public int abssum(){
        return Math.abs(deltaX) + Math.abs(deltaY) + Math.abs(deltaZ);
    }

    public int componentDifference(){
        int sum = 0;
        if(deltaX != 0) sum++;
        if(deltaY != 0) sum++;
        if(deltaZ != 0) sum++;
        return sum;
    }

    //TODO: Maybe do this with relative angles instead of this ugliness

    /**
     * Gives the CellDirection for the offset as seen from cell1.
     * @return Direction of the offset.
     */
    public CellDirection getDirection(){
        if(componentDifference() != 2) throw new IllegalStateException("Direction of offset can not be determined");

        CellDirection direction = null;
        // get the unchanged component and determine the direction.
        if(deltaX == 0) {
            // north or south
            if(deltaY < 0) direction = CellDirection.NORTH;
            if(deltaY > 0) direction = CellDirection.SOUTH;
        }
        else if(deltaY == 0){
            if(deltaX < 0) direction = CellDirection.NORTHEAST;
            if(deltaX > 0) direction = CellDirection.SOUTHWEST;
        }
        else if(deltaZ == 0){
            if(deltaX < 0) direction = CellDirection.SOUTHEAST;
            if(deltaX > 0) direction = CellDirection.NORTHWEST;
        }
        if (direction != null) {
            return direction;
        }
        throw new IllegalStateException("Direction not found");
    }

    public boolean LOS() {
        return componentDifference() == 2;
    }
}
