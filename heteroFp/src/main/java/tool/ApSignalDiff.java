package tool;

import entity.APSignal;

import java.util.List;

public class ApSignalDiff {
    // euclidean distance
    public static double calculate(List<APSignal> aps1, List<APSignal> aps2) {
        double result = 0;
        List<String> commonAddrs = IntersectionFinder.generateIntersectionMacAddrs(aps1, aps2);

        for (String addr: commonAddrs) {
            double ap1value = aps1.stream().filter(o -> addr.equals(o.getMacAddr())).findFirst().get().getSignalStrength();
            double ap2value = aps2.stream().filter(o -> addr.equals(o.getMacAddr())).findFirst().get().getSignalStrength();
            result += Math.pow((ap1value-ap2value), 2);
        }
        return Math.pow(result, 0.5)/commonAddrs.size();
    }
}
