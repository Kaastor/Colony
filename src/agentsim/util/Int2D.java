package agentsim.util;


public class Int2D {
    private int x;
    private int y;

    public Int2D() {
        this.x = 0;
        this.y = 0;
    }

    public Int2D(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Int2D(MutableInt2D p) {
        this.x = p.getX();
        this.y = p.getY();
    }

    public int getX() { return x; }

    public int getY() { return y; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Int2D position = (Int2D) o;

        return x == position.x && y == position.y;
    }

    @Override
    public int hashCode() {
        int result = x;
        result = 31 * result + y;
        return result;
    }

    @Override
    public String toString() {
        return "Int2D{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
