package main;

import entity.ReferencePoint;
import entity.TargetPoint;
import io.ReferencePointReader;
import io.TargetPointReader;
import model.CorrelationRpFilter;
import tool.IntersectionFinder;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        try {
            Double threshold = Double.valueOf(args[0]);
            String rpFilePath = args[1];

            List<TargetPoint> tps = new ArrayList<>();
            for (int i = 2; i < args.length; i++) {
                tps.addAll(TargetPointReader.load(args[i]));
            }
            List<ReferencePoint> rps = ReferencePointReader.load(rpFilePath);

            double diffSum = 0.0;
            int l = 0;

            for (TargetPoint tp : tps) {
                List<ReferencePoint> filteredRps = CorrelationRpFilter.filterRpHavingCorrelationGreaterThanThreshold(rps, tp, threshold);

                if (filteredRps == null) {
                    continue;
                }

                for (ReferencePoint rp: filteredRps) {
                    List<String> intersectionMacList = IntersectionFinder.generateIntersectionMacAddrs(rp, tp);
                    for (String addr: intersectionMacList) {
                        Double tpSig = tp.getApSignals().stream()
                                .filter(o -> o.getMacAddr().equals(addr)).findFirst().get().getSignalStrength();
                        Double rpSig = rp.getApSignals().stream()
                                .filter(o -> o.getMacAddr().equals(addr)).findFirst().get().getSignalStrength();

                        diffSum += (rpSig - tpSig);
                        l++;
                    }
                }
            }
            System.out.println(diffSum/l);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
