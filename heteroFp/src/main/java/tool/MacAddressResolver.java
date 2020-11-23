package tool;

import entity.ReferencePoint;
import entity.TargetPoint;
import io.ReferencePointReader;
import io.TargetPointReader;

import java.util.*;
import java.util.stream.Collectors;

public class MacAddressResolver {
    /**
     * transform any types of mac address to a standardized format like "D46E0E4CF370"
     * @param macAddress
     * @return
     */
    public static String standardizeMacAddress(String macAddress) {
        if (macAddress == null || macAddress.length() == 0) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < macAddress.length(); i++) {
            char c = macAddress.charAt(i);
            if ((c <= 'Z' && c >= 'A') || (c <= '9' && c >= '0')) {
                sb.append(c);
            } else if (c <= 'z' && c >= 'a') {
                sb.append(Character.toUpperCase(c));
            }
        }
        return sb.toString();
    }

    public static void main(String[] args) throws Exception {
        List<ReferencePoint> rps = ReferencePointReader.load("C:\\Users\\38284\\Desktop\\indoor navigation\\fingerprint.txt");
        List<TargetPoint> tps = TargetPointReader.load("C:\\Users\\38284\\Desktop\\indoor navigation\\WiFi_Raw_Data\\WiFi_Raw_Data\\Sony Xperia Z2\\path2-2\\wifi.dat");

        TargetPoint tp = tps.get(0);

        List<String> tpMacAddrs = tp.getApSignals().stream().map(o -> o.getMacAddr()).collect(Collectors.toList());

        Map<ReferencePoint, List<String>> result = new HashMap<>();
        for (ReferencePoint rp: rps) {
            List<String> rpMacAddrs = rp.getApSignals().stream().map(o -> o.getMacAddr()).collect(Collectors.toList());

            List<String> commonAddrs = rpMacAddrs.stream().filter(s -> tpMacAddrs.contains(s)).collect(Collectors.toList());
            if (commonAddrs != null && commonAddrs.size() > 0)
                result.put(rp, commonAddrs);
        }

        System.out.println(tp);
        System.out.println(result.entrySet().stream().max(Comparator.comparingInt(o -> o.getValue().size())).get());
    }
}
