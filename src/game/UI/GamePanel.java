package game.UI;

import game.*;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Jan on 23-10-2017.
 */
public class GamePanel extends JPanel{

    private final Game game;
    private Move move;
    public void setState(GameState state) {
        this.state = state;
    }


    private GameState state;

    final private Color fillColor = Color.LIGHT_GRAY;
    final private Color outLineColor = Color.BLACK;

    final private Color P1CircleColor = Color.BLACK;
    final private Color P1SignColor = Color.WHITE;

    final private Color P2CircleColor = Color.WHITE;
    final private Color P2SignColor = Color.BLACK;

    final private Color moveColor = Color.YELLOW;
    final private float moveLineWidth = 3;
    final private Color captureColor = Color.RED;

    @Override
    public void paintComponent(Graphics graphics){
        super.paintComponent(graphics);
        Dimension panelSize = this.getSize();
        Point center = new Point((int)panelSize.getWidth()/2, (int)panelSize.getHeight()/2);

        int w = (int) Math.round(  panelSize.getWidth() / ((state.getBoard().getSize() * 2) + 1));
        int h = (int) Math.round(panelSize.getHeight()) / ((state.getBoard().getSize() * 2) + 1);

        Cell centerCell = state.getBoard().getCenterCell();
        for (Cell cell : state.getBoard().getCells()) {

            Point cellCenter = cellCenterLocation(cell, w, h, center);

            Hexagon hexagon = new Hexagon(cellCenter, w, h, cell.getLabel());
            drawHexagon(graphics, hexagon);
        }
        for (Piece piece : state.getPieces()) {
            int pieceSize = (int) Math.round(h * 0.6);
            Point cellCenter = cellCenterLocation(piece.getCell(), w, h, center);
            // draw circle
            Color circleColor;
            Color signColor;
            if(piece.getOwner() == game.getP1()) {
                circleColor = this.P1CircleColor;
                signColor = P1SignColor;
            }
            else {
                circleColor = this.P2CircleColor;
                signColor = P2SignColor;
            }
            graphics.setColor(circleColor);
            graphics.fillOval(cellCenter.x - pieceSize/2, cellCenter.y - pieceSize/2, pieceSize, pieceSize);
            graphics.setColor(this.outLineColor);
            graphics.drawOval(cellCenter.x - pieceSize/2, cellCenter.y - pieceSize/2, pieceSize, pieceSize);
            // draw sign
            if(piece.getClass() != Neutron.class){
                Polygon sign;
                if(piece.getClass() == Electron.class) sign = new Minus(cellCenter, w, h);
                else sign = new Plus(cellCenter, w, h);
                graphics.setColor(signColor);
                graphics.fillPolygon(sign);
                graphics.setColor(this.outLineColor);
                graphics.drawPolygon(sign);
            }
            // draw move
            Stroke oldStroke = ((Graphics2D) graphics).getStroke();
            Cell origin = move.getOrigin();
            Cell destination;
            if (move.getClass()==CaptureMove.class)
                destination = ((CaptureMove)move).getMoveDestination();
            else
                destination = move.getDestination();

            Point originPoint = cellCenterLocation(origin, w, h, center);
            Point destinationPoint = cellCenterLocation(destination, w, h, center);
            ((Graphics2D) graphics).setStroke(new BasicStroke(moveLineWidth));
            graphics.setColor(moveColor);
            graphics.drawLine(originPoint.x, originPoint.y, destinationPoint.x, destinationPoint.y);
            if (move.getClass() == CaptureMove.class) {
                CaptureMove captureMove = (CaptureMove) move;
                // Draw the capturing
                // might have to make field out of this in CaptureMove
                Cell cell = captureMove.getCapturingPiece().getCell();
                Cell cell1 = captureMove.getCapturedPiece().getCell();
                Point captureOrigin = cellCenterLocation(cell, w, h, center);
                Point captureDestination = cellCenterLocation(cell1, w, h, center);

                graphics.setColor(this.captureColor);
                graphics.drawLine(captureOrigin.x, captureOrigin.y, captureDestination.x, captureDestination.y);
            }
            ((Graphics2D) graphics).setStroke(oldStroke);



        }

    }

    private Point cellCenterLocation(Cell cell, int w, int h, Point center){
        int xOffset = (int) Math.round((cell.getX()) * (0.75*w));
        int yOffset = ((cell.getY() - cell.getZ()) * -1) * (h/2);
        return new Point(center.x + xOffset, center.y + yOffset);
    }

    private void drawHexagon(Graphics graphics, Hexagon hexagon) {
        graphics.setColor(fillColor);
        graphics.fillPolygon(hexagon);
        graphics.setColor(outLineColor);
        graphics.drawPolygon(hexagon);
        int labelWidth = graphics.getFontMetrics().stringWidth(hexagon.label);
        int labelX = hexagon.getCenter().x - Math.round(labelWidth/2);
        graphics.drawString(hexagon.label, labelX, hexagon.getBounds().y + 12);
    }

    public GamePanel(Game game) {
        this.game = game;
    }

    public void setMove(Move move) {
        this.move = move;
    }
}
