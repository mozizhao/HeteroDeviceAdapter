package io;

import entity.APSignal;
import entity.TargetPoint;
import org.json.JSONArray;
import org.json.JSONObject;
import tool.MacAddressResolver;

import java.io.*;
import java.util.*;

public class TargetPointReader {
    public static List<TargetPoint> load(String fileName) throws Exception {
        File file = new File(fileName);
        if (!file.exists() || !file.isFile()) {
            throw new RuntimeException("file not exist.");
        }

        FileInputStream inputStream = new FileInputStream(fileName);
        Scanner scanner = new Scanner(inputStream);

        List<TargetPoint> result = new ArrayList<>();
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            JSONObject json = new JSONObject(line);
            JSONArray dataArr = json.getJSONArray("data");

            TargetPoint targetPoint = new TargetPoint();
            for (int i = 0; i < dataArr.length(); i++) {
                JSONObject item = dataArr.getJSONObject(i);
                APSignal apSignal = new APSignal();
                apSignal.setMacAddr(MacAddressResolver.standardizeMacAddress(item.getString("bssid")));
                apSignal.setSignalStrength(item.getDouble("rssi"));
                targetPoint.addApSignals(apSignal);
            }
            result.add(targetPoint);
        }
        return result;
    }

    public static void main(String[] args) throws Exception {
        System.out.println(load("C:\\Users\\38284\\Desktop\\indoor navigation\\WiFi_Raw_Data\\WiFi_Raw_Data\\Redmi 4A\\path 2-3\\wifi.dat"));
    }
}
