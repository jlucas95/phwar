package game;

import algorithms.RandomPlayer;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.ArrayList;
import java.util.List;

public class Board {


    ArrayList<Cell> cells = new ArrayList<>();

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
        for (Cell cell : cells) {
            if (cell.getX() == x && cell.getY() == y && cell.getZ() == z) {
                return cell;
            }
        }
        throw new IllegalArgumentException("Cell with given coordinates does not exist.");
    }

}
