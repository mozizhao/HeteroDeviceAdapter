package main;

import entity.APSignal;
import entity.ReferencePoint;
import entity.TargetPoint;
import io.ReferencePointReader;
import io.TargetPointReader;
import model.BasicCorrelationGenerator;
import model.LinearRegressionModelGenerator;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.FileWriter;
import java.util.List;

public class Sony1_3AdaptedPrinter {
    public static void main(String[] args) throws Exception {
        String rpFilePath = "C:\\Users\\38284\\Desktop\\indoor navigation\\fingerprint.txt";
        String tpFilePath = "C:\\Users\\38284\\Desktop\\indoor navigation\\WiFi_Raw_Data\\WiFi_Raw_Data\\Sony Xperia Z2\\path2-3\\wifi.dat";
        String outputPath = "C:\\Users\\38284\\Desktop\\indoor navigation\\sony1-3simplified_adapted.txt";
        FileWriter writer = new FileWriter(outputPath);
        List<ReferencePoint> rps = ReferencePointReader.load(rpFilePath);
        List<TargetPoint> tps = TargetPointReader.load(tpFilePath);

        TargetPoint firstTp = tps.get(0);
        double[] vars = LinearRegressionModelGenerator
                .generateLinearRegressionParams(
                        BasicCorrelationGenerator.calculateCorrelationsGreaterThanThreshold(rps, firstTp, 0.95));

        tps.stream().forEach(tp -> {
            JSONArray result = new JSONArray();
            for (APSignal apSignal: tp.getApSignals()) {
                JSONObject item = new JSONObject();
                item.put(apSignal.getMacAddr(), String.valueOf(apSignal.getSignalStrength()*vars[0]+vars[1]));
                result.put(item);
            }
            try {
                result.write(writer);
                writer.write("\n");
                writer.flush();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        writer.close();
    }
}
