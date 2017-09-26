package game;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.ArrayList;

public class Board {

    ArrayList<Cell> cells = new ArrayList<>();

    public Board(ArrayList<Cell> cells) {
        this.cells = cells;
    }

    public void apply(Move move) {

    }
}
