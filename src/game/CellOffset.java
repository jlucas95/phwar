package game;

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

    public CellDirection getDirection(){
        if(componentDifference() != 1) throw new IllegalStateException("Direction of offset can not be determined");
        CellDirection[] dirs = new CellDirection[2];
        // get the component that is different.
        if(deltaX == 0) {
            dirs[0] = CellDirection.NORTH;
            dirs[1] = CellDirection.SOUTH;
        }
        else if(deltaY == 0){
            dirs[0] = Cell.
        }
        // determine the direction

    }
}
