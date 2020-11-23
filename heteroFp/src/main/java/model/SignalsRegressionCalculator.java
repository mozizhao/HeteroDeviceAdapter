package model;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class SignalsRegressionCalculator {
    public static List<Double> calc(List<Double> originalStrengths, double slope, double intercept) {
        return originalStrengths.stream().map(d -> slope*d+intercept).collect(Collectors.toList());
    }
}
