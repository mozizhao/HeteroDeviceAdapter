package entity;

import java.math.BigDecimal;
import java.util.List;

public class RpTpPair {
    private BigDecimal x;
    private BigDecimal y;
    private List<ApSignalStrengthPair> strengthPairs;
    private double correlation;

    public RpTpPair(BigDecimal x, BigDecimal y, List<ApSignalStrengthPair> powerPairs, double correlation) {
        this.x = x;
        this.y = y;
        this.strengthPairs = powerPairs;
        this.correlation = correlation;
    }

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

    public List<ApSignalStrengthPair> getStrengthPairs() {
        return strengthPairs;
    }

    public void setStrengthPairs(List<ApSignalStrengthPair> strengthPairs) {
        this.strengthPairs = strengthPairs;
    }

    public double getCorrelation() {
        return correlation;
    }

    public void setCorrelation(double correlation) {
        this.correlation = correlation;
    }

    @Override
    public String toString() {
        return "RpTpPair{" +
                "x=" + x +
                ", y=" + y +
                ", strengthPairs=" + strengthPairs +
                ", correlation=" + correlation +
                '}';
    }
}
