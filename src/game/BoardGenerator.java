package game;

import java.util.ArrayList;

public class BoardGenerator {

    private ArrayList<Cell> cells = new ArrayList<>();

    private int depth;
    public BoardGenerator(int depth) {
        this.depth = depth;
    }

    public Board build(){
        // TODO: optimize
        for (int x = -depth; x <= depth; x++) {
            for (int y = -depth; y <= depth; y++) {
                for (int z = -depth; z <= depth; z++) {
                    if(x+y+z == 0){
                        Cell cell = new Cell(x, y, z);
                        cells.add(cell);
                    }
                }
            }
        }
        for (Cell cell : cells) {
            String label = getLabel(cell, depth);
            cell.setLabel(label);
            for (Cell cell2 : cells) {
                if(cell.isNeighbour(cell2)){
                    CellOffset offset = new CellOffset(cell, cell2);
                    CellDirection direction = offset.getDirection();
                    cell.setNeighbour(cell2, direction);
                }
            }
        }


        return new Board(cells, depth);
    }


    private static String getLabel(Cell cell, int depth) {
        StringBuilder b = new StringBuilder();
        //determine column

        char[] columnNames =new char[]{ 'A', 'B', 'C', 'D', 'E',
                                        'F',
                                        'G', 'H', 'I', 'J', 'K'};
        int i = cell.getX()+ depth;
        b.append(columnNames[i]);

        // determine row
        double num = (cell.getX() + 1) / 2.0;
        int rowStart = (int)Math.ceil(num) + depth;

        int labelNum = cell.getZ() + rowStart;
        b.append(labelNum);

        return b.toString();
    }

    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        BoardGenerator boardGenerator = new BoardGenerator(5);
        Board build = boardGenerator.build();
        long end = System.currentTimeMillis();
        System.out.println(end-start);

        Cell cell1 = new Cell(0, 0, 0);
        Cell cell2 = new Cell(-1,0,1);
        Cell cell3 = new Cell(-1,-1,2);
        System.out.println(getLabel(new Cell(-5, 3, 2), 5));
    }
}
