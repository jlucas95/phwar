package game.UI;

import java.awt.*;

/**
 * Created by Jan on 24-10-2017.
 */
public class Plus extends Polygon {

    public Plus(Point center, int w, int h){
        double upperRatio = 0.2;
        double LowerRatio = 0.075;

        int upperXOffset = (int) Math.round(w * upperRatio);
        int lowerXOffset = (int) Math.round(w * LowerRatio);

        int upperYOffset = (int) Math.round(h * upperRatio);
        int lowerYOffset = (int) Math.round(h * LowerRatio);

        // left side
        this.addPoint(center.x - upperXOffset, center.y - lowerYOffset);
        this.addPoint(center.x - upperXOffset, center.y + lowerYOffset);
        // upper left
        this.addPoint(center.x - lowerXOffset, center.y + lowerYOffset);
        // top side
        this.addPoint(center.x - lowerXOffset, center.y + upperYOffset);
        this.addPoint(center.x + lowerXOffset, center.y + upperYOffset);
        // upper right
        this.addPoint(center.x + lowerXOffset, center.y + lowerYOffset);
        // right side
        this.addPoint(center.x + upperXOffset, center.y + lowerYOffset);
        this.addPoint(center.x + upperXOffset, center.y - lowerYOffset);
        // lower right
        this.addPoint(center.x + lowerXOffset, center.y - lowerYOffset);
        // bottom side
        this.addPoint(center.x + lowerXOffset, center.y - upperYOffset);
        this.addPoint(center.x - lowerXOffset, center.y - upperYOffset);
        // lower left
        this.addPoint(center.x - lowerXOffset, center.y - lowerYOffset);
    }
}
