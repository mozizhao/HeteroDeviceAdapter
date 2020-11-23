package model;

import entity.*;
import io.ReferencePointReader;
import io.TargetPointReader;
import org.apache.commons.math3.stat.correlation.PearsonsCorrelation;
import tool.IntersectionFinder;

import java.util.*;
import java.util.stream.Collectors;

public class BasicCorrelationGenerator {
    /**
     * calculate the correlation from target signal to a list of reference point signal
     * and pick those which value is greater than threshold.
     * @param rps list of reference point
     * @param tp target point
     * @param threshold
     * @return
     */
    public static List<RpTpPair> calculateCorrelationsGreaterThanThreshold(List<ReferencePoint> rps, TargetPoint tp, double threshold) {
        List<RpTpPair> result = new ArrayList<>();
        // iterate all the reference point
        for (ReferencePoint rp: rps) {
            // calculate correlation for those mac belongs to both reference point and target point,
            // so generate mac intersection set here.
            List<String> intersectionMacList = IntersectionFinder.generateIntersectionMacAddrs(rp, tp);
            List<ApSignalStrengthPair> pairs = generatePowerPair(intersectionMacList, rp, tp);

            // for calculating correlations, only considering the pairs which size is greater than 2
            if (pairs.size() <= 2) {
                continue;
            }

            Double correlation = calculateCorrelation(
                    pairs.stream().map(ApSignalStrengthPair::getRpPower).collect(Collectors.toList()),
                    pairs.stream().map(ApSignalStrengthPair::getTpPower).collect(Collectors.toList())
            );
            if (Double.isNaN(correlation) || correlation < threshold) {
                continue;
            }
            RpTpPair rpTpPair = new RpTpPair(rp.getX(), rp.getY(), pairs, correlation);
            result.add(rpTpPair);
        }

        return result;
    }

    /**
     * calculate correlation value between reference point signals and target point signals
     * @param rpValList
     * @param tpValList
     * @return
     */
    public static Double calculateCorrelation(List<Double> rpValList, List<Double> tpValList) {
        return new PearsonsCorrelation().correlation(
                    convertDoubleListIntoArr(rpValList),
                    convertDoubleListIntoArr(tpValList));
    }

    private static List<ApSignalStrengthPair> generatePowerPair(List<String> macAddrs, ReferencePoint rp, TargetPoint tp) {
        List<ApSignalStrengthPair> result = new ArrayList<>();
        // for each of the common addr contained by rp and tp, assemble them together
        for (String macAddr: macAddrs) {
            Optional<APSignal> correspondingRpAp = rp.getApSignals().stream()
                    .filter(ap -> ap.getMacAddr().equals(macAddr)).findFirst();
            Optional<APSignal> correspondingTpAp = tp.getApSignals().stream()
                    .filter(ap -> ap.getMacAddr().equals(macAddr)).findFirst();

            ApSignalStrengthPair pair = new ApSignalStrengthPair(macAddr, correspondingRpAp.get().getSignalStrength(), correspondingTpAp.get().getSignalStrength());
            result.add(pair);
        }
        return result;
    }

    private static double[] convertDoubleListIntoArr(List<Double> params) {
        double[] result = new double[params.size()];
        for (int i = 0; i < params.size(); i++) {
            result[i] = params.get(i);
        }
        return result;
    }

    public static void main(String[] args) throws Exception {
        List<ReferencePoint> rps = ReferencePointReader.load("C:\\Users\\38284\\Desktop\\indoor navigation\\fingerprint.txt");
        TargetPoint tp = TargetPointReader.load("C:\\Users\\38284\\Desktop\\indoor navigation\\WiFi_Raw_Data\\WiFi_Raw_Data\\Sony Xperia Z2\\path2-3\\wifi.dat").get(0);
        List<RpTpPair> result = calculateCorrelationsGreaterThanThreshold(rps, tp, 0.98);

//        CorrelationReport.write(result, "C:\\Users\\38284\\Desktop\\indoor navigation\\WiFi_Raw_Data\\WiFi_Raw_Data\\Sony Xperia Z2\\path2-3\\raw_report.txt");
        System.out.println(result);
    }
}
