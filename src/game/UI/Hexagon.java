package game.UI;

import java.awt.*;

/**
 * Created by Jan on 23-10-2017.
 */
public class Hexagon extends Polygon {

    private final Point center;

    public Point getCenter() {
        return center;
    }

    String label;


    public Hexagon(Point center, double w, double h, String label){

        this.center = center;
        this.label = label;
        // left point
        this.addPoint( center.x - (int) w/2, center.y);
        // left upper corner
        this.addPoint(center.x - (int) w/4, center.y + (int) h/2);
        // right upper corner
        this.addPoint(center.x + (int) w/4, center.y + (int) h/2);
        // right point
        this.addPoint(center.x + (int) w/2, center.y);
        // right lower corner
        this.addPoint(center.x + (int) w/4, center.y - (int) h/2);
        // left lower corner
        this.addPoint(center.x - (int) w/4, center.y - (int) h/2);
    }

}
