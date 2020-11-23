package main;

import org.apache.commons.math3.stat.correlation.PearsonsCorrelation;

public class CorrelationTest {
    public static void main(String[] args) {
//        double[] vector1 = new double[]{-60.0, -53.0, -81.0, -78.0, -79.0};
//        double[] vector2 = new double[]{-65.57142857142857, -60.75, -74.71428571428571, -76.66666666666667, -74.42857142857143};
        double[] vector1 = new double[]{-1, -2, -3};
        double[] vector2 = new double[]{-1.1, -2.1, -3.1};

        System.out.println(new PearsonsCorrelation().correlation(vector1, vector2));
    }
}
