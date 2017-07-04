package agentsim.util;


public class MutableInt2D {

    private int x;
    private int y;

    public MutableInt2D() {
        this.x = 0;
        this.y = 0;
    }

    public MutableInt2D(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public MutableInt2D(Int2D p) {
        this.x = p.getX();
        this.y = p.getY();
    }

    public int getX() { return x; }

    public int getY() { return y; }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setTo(int x, int y){
        this.x = x;
        this.y = y;
    }

    public void setTo(Int2D p){
        this.x = p.getX();
        this.y = p.getY();
    }

    public void setTo(MutableInt2D p){
        this.x = p.getX();
        this.y = p.getY();
    }

    public void moveByVector(int x, int y){
        this.x += x;
        this.y += y;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MutableInt2D that = (MutableInt2D) o;

        return x == that.x && y == that.y;
    }

    @Override
    public int hashCode() {
        int result = x;
        result = 31 * result + y;
        return result;
    }

    @Override
    public String toString() {
        return "MutableInt2D{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
