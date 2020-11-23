package main;

import entity.*;
import io.*;
import model.BasicCorrelationGenerator;
import model.HeteroTransformer;
import model.LinearRegressionModelGenerator;

import java.util.*;

public class Main {
    public static void main(String[] args) throws Exception {
        //String tpFilePath = args[1];
        String tpFilePath = "C:\\Users\\38284\\Desktop\\indoor navigation\\WiFi_Raw_Data\\WiFi_Raw_Data\\Sony Xperia Z2\\path1-3\\wifi.dat";
        //String rpFilePath = args[2];
        String rpFilePath = "C:\\Users\\38284\\Desktop\\indoor navigation\\fingerprint.txt";
        //Double threshold = Double.valueOf(args[3]);
        Double threshold = 0.95;

//        if (tpFilePath == null || tpFilePath.length() == 0
//                || rpFilePath == null || rpFilePath.length() == 0
//                || args[3] == null || args[3].length() == 0) {
//            System.err.println("input error! please input [target file path, reference file path, threshold value]");
//        }

        List<TargetPoint> tps = TargetPointReader.load(tpFilePath);
        List<ReferencePoint> rps = ReferencePointReader.load(rpFilePath);

        // use first tp to do the regression
        TargetPoint firstTp = tps.get(0);

        // get the slope and intercept of linear regression
        double[] vars = LinearRegressionModelGenerator
                .generateLinearRegressionParams(
                        BasicCorrelationGenerator.calculateCorrelationsGreaterThanThreshold(rps, firstTp, threshold));

        List<ReferencePoint> result = new ArrayList<>();
        for (int i = 0; i < tps.size(); i++) {
            TargetPoint tp = tps.get(i);
            ReferencePoint rp = HeteroTransformer.transformer(rps, tp, vars[0], vars[1]);
            result.add(rp);
        }

        result.stream().forEach(System.out::println);
    }
}
