package model;

import entity.APSignal;
import entity.ReferencePoint;
import entity.TargetPoint;
import tool.IntersectionFinder;

import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

public class HeteroTransformer {
    public static ReferencePoint transformer(List<ReferencePoint> rps, TargetPoint tp, double slope, double intercept) {
        ReferencePoint result = null;

        Double minDistance = Double.MAX_VALUE;
        for (ReferencePoint rp: rps) {
            List<String> intersectMacAddrs = IntersectionFinder.generateIntersectionMacAddrs(rp, tp);

            if (intersectMacAddrs.size() > 2) {
                List<Double> tpapVals = new ArrayList<>();
                List<Double> rpapVals = new ArrayList<>();
                for (String mac: intersectMacAddrs) {
                    APSignal tpAp = tp.getApSignals().stream().filter(o -> o.getMacAddr().equals(mac)).findFirst().get();
                    APSignal rpAp = rp.getApSignals().stream().filter(o -> o.getMacAddr().equals(mac)).findFirst().get();

                    Double amendedTpApVal = tpAp.getSignalStrength() * slope + intercept;
                    Double rpApVal = rpAp.getSignalStrength();

                    tpapVals.add(amendedTpApVal);
                    rpapVals.add(rpApVal);
                }

                Double distance = CosineSimilarity.calc(tpapVals, rpapVals);
                if (distance < minDistance) {
                    result = rp;
                    minDistance = distance;
                }
            }
        };
        return result;
    }

    public static void main(String[] args) {

    }
}
