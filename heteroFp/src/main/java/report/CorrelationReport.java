package report;

import entity.RpTpPair;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

public class CorrelationReport {
    public static void write(final Map<Double, RpTpPair> sitePowerPairMap, String path) throws Exception {
        FileWriter writer = new FileWriter(path);
        sitePowerPairMap.entrySet().stream().forEach(entry -> {
            try {
                writer.write(entry.getKey() + " = " + entry.getValue());
                writer.write("\n");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        writer.close();
    }
}
