package model;

import entity.*;
import org.apache.commons.math3.stat.regression.SimpleRegression;
import tool.IntersectionFinder;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class LinearRegressionModelGenerator {
    public static double[] generateLinearRegressionParams(TargetPoint tp, List<ReferencePoint> rps) {

        SimpleRegression regression = new SimpleRegression(true);
        for (ReferencePoint rp: rps) {
            List<String> commonAddrList = IntersectionFinder.generateIntersectionMacAddrs(tp.getApSignals(), rp.getApSignals());

            for (String mac: commonAddrList) {
                APSignal tpap = tp.getApSignals().stream().filter(o -> o.getMacAddr().equals(mac)).findFirst().get();
                APSignal rpap = rp.getApSignals().stream().filter(o -> o.getMacAddr().equals(mac)).findFirst().get();
                regression.addData(tpap.getSignalStrength(), rpap.getSignalStrength());
            }
        }
        return new double[]{regression.getSlope(), regression.getIntercept()};
    }
}
