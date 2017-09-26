package game;

import java.util.HashMap;
import java.util.Map;

public class Cell {
    private HashMap<CellDirection, Cell> neighbours = new HashMap<CellDirection, Cell>();
    private int x;
    private int y;
    private int z;

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getZ() {
        return z;
    }

    private String label = "";

    public Cell(int x, int y, int z) {
        if(x+y+z != 0){
            throw new IllegalArgumentException("Coordinate components do not sum to one.");
        }
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public boolean isNeighbour(Cell cell){
        // Cells are neighbours if the sum of differences is smaller or equal to 2
        int xdiff = Math.abs(this.x - cell.x);
        int ydiff = Math.abs(this.y - cell.y);
        int zdiff = Math.abs(this.z - cell.z);
        return xdiff + ydiff + zdiff <=2;

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Cell cell = (Cell) o;

        if (x != cell.x) return false;
        if (y != cell.y) return false;
        return z == cell.z;
    }

    @Override
    public int hashCode() {
        int result = x;
        result = 31 * result + y;
        result = 31 * result + z;
        return result;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }
}
