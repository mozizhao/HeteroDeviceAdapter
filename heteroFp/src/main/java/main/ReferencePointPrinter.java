package main;

import entity.APSignal;
import entity.ReferencePoint;
import io.ReferencePointReader;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.FileWriter;
import java.util.List;

public class ReferencePointPrinter {
    public static void main(String[] args) throws Exception {
        String rpFilePath = "C:\\Users\\38284\\Desktop\\indoor navigation\\fingerprint.txt";
        List<ReferencePoint> rps = ReferencePointReader.load(rpFilePath);

        String outputPath = "C:\\Users\\38284\\Desktop\\indoor navigation\\reference_point_simplified.txt";
        FileWriter writer = new FileWriter(outputPath);

        rps.stream().forEach(rp -> {
            JSONArray result = new JSONArray();
            for (APSignal apSignal: rp.getApSignals()) {
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
