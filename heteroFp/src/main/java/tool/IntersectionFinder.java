package tool;

import entity.APSignal;
import entity.ReferencePoint;
import entity.TargetPoint;

import java.util.List;
import java.util.stream.Collectors;

public class IntersectionFinder {

    public static List<String> generateIntersectionMacAddrs(ReferencePoint rp, TargetPoint ts) {
        List<String> rpMacAddrs = rp.getApSignals().stream().map(o -> o.getMacAddr()).collect(Collectors.toList());
        List<String> tsMacAddrs = ts.getApSignals().stream().map(o -> o.getMacAddr()).collect(Collectors.toList());

        return rpMacAddrs.stream().filter(s -> tsMacAddrs.contains(s)).collect(Collectors.toList());
    }

    public static List<String> generateIntersectionMacAddrs(List<APSignal> aps1, List<APSignal> aps2) {
        List<String> rps1MacAddrs = aps1.stream().map(o -> o.getMacAddr()).collect(Collectors.toList());
        List<String> rps2MacAddrs = aps2.stream().map(o -> o.getMacAddr()).collect(Collectors.toList());

        return rps1MacAddrs.stream().filter(s -> rps2MacAddrs.contains(s)).collect(Collectors.toList());
    }
}
