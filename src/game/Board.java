package game;

import java.util.ArrayList;
// TODO: Maybe make board static
public class Board {


    private ArrayList<Cell> cells = new ArrayList<>();

    public Board(ArrayList<Cell> cells) {
        this.cells = cells;
    }

    public Cell getCell(String label){
        for (Cell cell : cells) {
            if(cell.getLabel().equals(label)){
                return cell;
            }
        }
        throw new IllegalArgumentException("Cell with given label does not exist.");
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

}
