package entity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class ReferencePoint {
    private BigDecimal x;
    private BigDecimal y;
    private List<APSignal> apSignals = new ArrayList<APSignal>();

    public BigDecimal getX() {
        return x;
    }

    public void setX(BigDecimal x) {
        this.x = x;
    }

    public BigDecimal getY() {
        return y;
    }

    public void setY(BigDecimal y) {
        this.y = y;
    }

    public List<APSignal> getApSignals() {
        return apSignals;
    }

    public void addApSignal(APSignal apSignal) {
        apSignals.add(apSignal);
    }

    @Override
    public String toString() {
        return "ReferencePoint{" +
                "x=" + x +
                ", y=" + y +
                ", apSignals=" + apSignals +
                '}';
    }
}
