package model;

import entity.*;
import org.apache.commons.math3.stat.regression.SimpleRegression;

import java.util.List;

public class LinearRegressionModelGenerator {
    public static double[] generateLinearRegressionParams(List<RpTpPair> powerPairs) {
        SimpleRegression regression = new SimpleRegression(true);
        powerPairs.stream().forEach(pair -> pair.getStrengthPairs().stream().forEach(apPowerPair -> regression.addData(apPowerPair.getTpPower(), apPowerPair.getRpPower())));
        return new double[]{regression.getSlope(), regression.getIntercept()};
    }

    public static void main(String[] args) {
        SimpleRegression regression = new SimpleRegression(true);
        regression.addData(2, 3);
        regression.addData(3, 4);
        regression.addData(4, 5);
        System.out.println(regression.getSlope());
        System.out.println(regression.getIntercept());
    }
}
