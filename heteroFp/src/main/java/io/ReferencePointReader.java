package io;

import entity.APSignal;
import entity.ReferencePoint;
import tool.MacAddressResolver;

import java.io.File;
import java.io.FileInputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ReferencePointReader {
    public static List<ReferencePoint> load(String fileName) throws Exception{
        File file = new File(fileName);
        if (!file.exists() || !file.isFile()) {
            throw new RuntimeException("file not exist.");
        }

        FileInputStream inputStream = new FileInputStream(fileName);
        Scanner scanner = new Scanner(inputStream);

        List<ReferencePoint> rps = new ArrayList<ReferencePoint>();
        while (scanner.hasNextLine()) {
            ReferencePoint rp = new ReferencePoint();

            String line = scanner.nextLine();
            String[] contentsInLine = line.split("\\s+");

            String[] axis = contentsInLine[0].split(",");
            rp.setX(new BigDecimal(axis[0]));
            rp.setY(new BigDecimal(axis[1]));

            for (int i = 1; i < contentsInLine.length; i++) {
                String[] apContent = contentsInLine[i].split(",");
                APSignal apSignal = new APSignal();
                apSignal.setMacAddr(MacAddressResolver.standardizeMacAddress(apContent[0]));
                apSignal.setSignalStrength(Double.valueOf(apContent[1]));

                rp.addApSignal(apSignal);
            }
            rps.add(rp);
        }
        return rps;
    }

    public static void main(String[] args) throws Exception {
        System.out.println(load("C:\\Users\\38284\\Desktop\\indoor navigation\\fingerprint.txt").get(0));
    }
}
