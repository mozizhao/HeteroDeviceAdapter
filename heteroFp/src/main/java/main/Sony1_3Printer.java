package main;

import entity.APSignal;
import entity.ReferencePoint;
import entity.TargetPoint;
import io.ReferencePointReader;
import io.TargetPointReader;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class Sony1_3Printer {
    public static void main(String[] args) throws Exception {
        String tpFilePath = "C:\\Users\\38284\\Desktop\\indoor navigation\\WiFi_Raw_Data\\WiFi_Raw_Data\\Sony Xperia Z2\\path2-3\\wifi.dat";

        String outputPath = "C:\\Users\\38284\\Desktop\\indoor navigation\\sony1-3simplified.txt";
        FileWriter writer = new FileWriter(outputPath);

        List<TargetPoint> tps = TargetPointReader.load(tpFilePath);

        tps.stream().forEach(tp -> {
            JSONArray result = new JSONArray();
            for (APSignal apSignal: tp.getApSignals()) {
                JSONObject item = new JSONObject();
                item.put(apSignal.getMacAddr(), String.valueOf(apSignal.getSignalStrength()));
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
