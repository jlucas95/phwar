package game;

/**
 * Created by Jan on 12-10-2017.
 */
public class Coordinate {
    private int x;
    private int y;

    @Override
    public String toString() {
        return "Coordinate{" +
                "x=" + x +
                ", y=" + y +
                ", z=" + z +
                '}';
    }

    private int z;

    public Coordinate(int x, int y, int z) {
        this.setX(x);
        this.setY(y);
        this.setZ(z);
    }

    public Coordinate(Coordinate c ){
        this.setX(c.getX());
        this.setY(c.getY());
        this.setZ(c.getZ());
    }

    public void negate(){
        this.setX(this.getX() * -1);
        this.setY(this.getY() * -1);
        this.setZ(this.getZ() * -1);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Coordinate that = (Coordinate) o;

        return getX() == that.getX() && getY() == that.getY() && getZ() == that.getZ();
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getZ() {
        return z;
    }

    public void setZ(int z) {
        this.z = z;
    }
}
