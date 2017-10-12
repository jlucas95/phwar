package game;

import java.util.HashMap;

public class Cell implements Cloneable {
    private HashMap<CellDirection, Cell> neighbours = new HashMap<CellDirection, Cell>();
    private Coordinate coordinate;
    private String label = "";


    public int getX() {
        return coordinate.getX();
    }

    public int getY() {
        return coordinate.getY();
    }

    public int getZ() {
        return coordinate.getZ();
    }

    public Cell(int x, int y, int z) {
        if(x+y+z != 0){
            throw new IllegalArgumentException("Coordinate components do not sum to one.");
        }
        for (CellDirection dir : CellDirection.values()) neighbours.put(dir, null);
        this.coordinate = new Coordinate(x,y,z);
    }

    public Cell setNeighbour(Cell c, CellDirection direction){ return neighbours.put(direction, c); }

    public Cell getNeigbour(CellDirection direction) { return neighbours.get(direction); }

    public boolean isNeighbour(Cell cell){
        // Return false for itself
        if(this.equals(cell))return false;

        // Cells are neighbours if the sum of differences is smaller or equal to 2
        int xdiff = Math.abs(this.coordinate.getX() - cell.getX());
        int ydiff = Math.abs(this.coordinate.getY() - cell.getY());
        int zdiff = Math.abs(this.coordinate.getZ() - cell.getZ());
        return xdiff + ydiff + zdiff <=2;

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Cell cell = (Cell) o;

        if (getX() != cell.getX()) return false;
        if (getY() != cell.getY()) return false;
        return getZ() == cell.getZ();
    }

    @Override
    public int hashCode() {
        int result = getX();
        result = 31 * result + getY();
        result = 31 * result + getZ();
        return result;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {

        if(label != null) this.label = label;
        else throw new NullPointerException("Label may not be null");
    }

    public void setX(int x) {this.coordinate.setX(x);}

    public void setY(int y) {this.coordinate.setY(y);}

    public void setZ(int z) {this.coordinate.setZ(z);}

    @Override
    public Cell clone(){
        try{
            Object clone = super.clone();
            if(clone.getClass() != this.getClass()) throw new IllegalStateException("cloning resulted in an object that " +
                    "does not belong to this class");
            Cell cell = (Cell) clone;
            cell.setX(getX());
            cell.setY(getY());
            cell.setZ(getZ());
            return cell;

        }
        catch (CloneNotSupportedException e){
            return new Cell(getX(),getY(),getZ());

        }

    }

    public Coordinate getCoordinate() {
        return coordinate;
    }


    @Override
    public String toString() {
        return "Cell{" +
                "coordinate=" + coordinate +
                ", label='" + label + '\'' +
                '}';
    }
}
