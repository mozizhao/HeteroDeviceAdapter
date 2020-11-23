package entity;

public class ValuePair<T> {
    private T x;
    private T y;

    public ValuePair(T x, T y) {
        this.x = x;
        this.y = y;
    }

    public T getX() {
        return x;
    }

    public void setX(T x) {
        this.x = x;
    }

    public T getY() {
        return y;
    }

    public void setY(T y) {
        this.y = y;
    }

    @Override
    public String toString() {
        return "ValuePair{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
