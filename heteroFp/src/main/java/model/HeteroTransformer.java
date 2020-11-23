package model;

import entity.APSignal;
import entity.ReferencePoint;
import entity.TargetPoint;
import tool.ApSignalDiff;

import java.util.*;
import java.util.stream.Collectors;

public class HeteroTransformer {
    public static ReferencePoint transformer(List<ReferencePoint> rps, TargetPoint tp, double slope, double intercept) {
        List<APSignal> tpaps = tp.getApSignals().stream().map(o -> new APSignal(o.getMacAddr(), o.getSignalStrength()*slope+intercept)).collect(Collectors.toList());
        ReferencePoint resultRp = findClosestRpByTpAps(tpaps, rps);

        return resultRp;
    }

    private static ReferencePoint findClosestRpByTpAps(List<APSignal> tpaps, List<ReferencePoint> rps) {
        ReferencePoint result = null;
        double minDistance = Double.MAX_VALUE;
        for (ReferencePoint rp: rps) {
            double distance = ApSignalDiff.calculate(tpaps, rp.getApSignals());
            if (distance < minDistance) {
                minDistance = distance;
                result = rp;
            }
        }
        return result;
    }
}
