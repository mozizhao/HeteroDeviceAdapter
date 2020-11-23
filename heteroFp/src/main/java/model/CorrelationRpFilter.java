package model;

import entity.*;
import io.ReferencePointReader;
import io.TargetPointReader;
import org.apache.commons.math3.stat.correlation.PearsonsCorrelation;
import tool.IntersectionFinder;

import java.util.*;

public class CorrelationRpFilter {
    /**
     * calculate the correlation from target signal to a list of reference point signal
     * and pick those which value is greater than threshold.
     * @param rps list of reference point
     * @param tp target point
     * @param threshold
     * @return
     */
    public static List<ReferencePoint> filterRpHavingCorrelationGreaterThanThreshold(List<ReferencePoint> rps, TargetPoint tp, double threshold) {
        List<ReferencePoint> result = new ArrayList<>();
        // iterate all the reference point
        for (ReferencePoint rp: rps) {
            // calculate correlation for those mac belongs to both reference point and target point,
            // so generate mac intersection set here.
            List<String> intersectionMacList = IntersectionFinder.generateIntersectionMacAddrs(rp, tp);

            List<Double> tpapvals = new ArrayList<>();
            List<Double> rpapvals = new ArrayList<>();

            if (intersectionMacList.size() > 2) {
                for (String mac: intersectionMacList) {
                    APSignal tpap = tp.getApSignals().stream().filter(o -> o.getMacAddr().equals(mac)).findFirst().get();
                    APSignal rpap = rp.getApSignals().stream().filter(o -> o.getMacAddr().equals(mac)).findFirst().get();

                    tpapvals.add(tpap.getSignalStrength());
                    rpapvals.add(rpap.getSignalStrength());
                }

                double[] tpapvector = new double[tpapvals.size()];
                double[] rpapvector = new double[rpapvals.size()];
                for (int i = 0; i < tpapvals.size(); i++) {
                    tpapvector[i] = tpapvals.get(i);
                    rpapvector[i] = rpapvals.get(i);
                }

                Double correlation = new PearsonsCorrelation().correlation(tpapvector, rpapvector);
                if (!correlation.isNaN() && correlation > threshold) {
                    result.add(rp);
                }
            }
        }
        return result;
    }

    public static void main(String[] args) throws Exception {
        List<ReferencePoint> rps = ReferencePointReader.load("C:\\Users\\38284\\Desktop\\indoor navigation\\fingerprint.txt");
        TargetPoint tp = TargetPointReader.load("C:\\Users\\38284\\Desktop\\indoor navigation\\WiFi_Raw_Data\\WiFi_Raw_Data\\Sony Xperia Z2\\path2-3\\wifi.dat").get(0);
        List<ReferencePoint> result = filterRpHavingCorrelationGreaterThanThreshold(rps, tp, 0.98);

//        CorrelationReport.write(result, "C:\\Users\\38284\\Desktop\\indoor navigation\\WiFi_Raw_Data\\WiFi_Raw_Data\\Sony Xperia Z2\\path2-3\\raw_report.txt");
        System.out.println(result);
    }
}
