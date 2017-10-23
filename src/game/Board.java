package game;

import java.util.ArrayList;
// TODO: Maybe make board static
public class Board {


    private final int size;

    private ArrayList<Cell> cells = new ArrayList<>();
    private Cell center;

    public Board(ArrayList<Cell> cells, int size) {
        this.cells = cells;
        this.size = size;
    }

    public Cell getCell(String label){
        for (Cell cell : cells) {
            if(cell.getLabel().equals(label)){
                return cell;
            }
        }
        throw new IllegalArgumentException("Cell with given label does not exist.");
    }

    public Cell getCenterCell(){
        if (center == null) {
            center = getCell(0,0,0);
        }
        return center;
    }

    public int getSize() {
        return size;
    }

    public Cell getCell(int x, int y, int z){
        return getCell(new Coordinate(x,y,z));
    }

    public Cell getCell(Coordinate coordinate){
        for (Cell cell : cells) {
            if(cell.getCoordinate().equals(coordinate)){
                return cell;
            }
        }
        throw new IllegalArgumentException("Cell with given coordinates does not exist.");
    }

    public ArrayList<Cell> getCells() {
        return cells;
    }
}
