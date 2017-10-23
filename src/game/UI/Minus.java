package game.UI;

import java.awt.*;

/**
 * Created by Jan on 24-10-2017.
 */
public class Minus extends Polygon {
    public Minus(Point center, int w, int h){
        int xOffset = (int) Math.round(w * 0.35/2);
        int yOffset = (int) Math.round(h * 0.15/2);

        // left upper corner
        this.addPoint(center.x - xOffset, center.y + yOffset);
        // right upper corner
        this.addPoint(center.x + xOffset, center.y + yOffset);
        // right lower corner
        this.addPoint(center.x + xOffset, center.y - yOffset);
        // left lower corner
        this.addPoint(center.x - xOffset, center.y - yOffset);

    }
}
