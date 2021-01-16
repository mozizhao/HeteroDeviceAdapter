package main;

import entity.APSignal;
import entity.TargetPoint;
import io.TargetPointReader;
import org.apache.commons.math3.stat.regression.SimpleRegression;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.FastScatterPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.ApplicationFrame;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class Phase2MainEntry {
    public static void main(String[] args) throws Exception {
        String sonytpFilePath = "C:\\Users\\38284\\Desktop\\indoor navigation\\less is better\\less is better\\sony\\experience_offset_lessisbetter_L\\wifi.dat";
        List<TargetPoint> sonytps = TargetPointReader.load(sonytpFilePath);

        String samsungtpFilePath = "C:\\Users\\38284\\Desktop\\indoor navigation\\less is better\\less is better\\Samsung S8\\experience_offset_lessisbetter_L\\wifi.dat";
        List<TargetPoint> samsungtps = TargetPointReader.load(samsungtpFilePath);

        String huaweiFilePath = "C:\\Users\\38284\\Desktop\\indoor navigation\\less is better\\less is better\\Huawei nova 7i\\experience _offset_lessisbetter_P\\wifi.dat";
        List<TargetPoint> huaweitps = TargetPointReader.load(huaweiFilePath);




        List<TargetPoint> device1Tps = sonytps;
        List<TargetPoint> device2Tps = samsungtps;





        Set<String> device1TpMacs = new HashSet<>();
        for (TargetPoint tp: device1Tps) {
            tp.getApSignals().stream().forEach(apSignal -> device1TpMacs.add(apSignal.getMacAddr()));
        }
        Set<String> device2TpMacs = new HashSet<>();
        for (TargetPoint tp: device2Tps) {
            tp.getApSignals().stream().forEach(apSignal -> device2TpMacs.add(apSignal.getMacAddr()));
        }
        Set<String> commonSet = generateIntersactionSet(device1TpMacs, device2TpMacs);




        List<Double> device1maxList = new ArrayList<>();
        List<Double> device1avgList = new ArrayList<>();
        generateMaxAndAvgSignalList(commonSet, device1Tps, device1maxList, device1avgList);

        List<Double> device2maxList = new ArrayList<>();
        List<Double> device2avgList = new ArrayList<>();
        generateMaxAndAvgSignalList(commonSet, device2Tps, device2maxList, device2avgList);


        SimpleRegression regression1 = new SimpleRegression();
        SimpleRegression regression2 = new SimpleRegression();




//        System.out.println(device1avgList);
//        System.out.println(device2avgList);
//        System.out.println(device1maxList);
//        System.out.println(device2maxList);
//        System.exit(0);





        for (int i = 0; i < device1avgList.size(); i++) {
            regression1.addData(device1avgList.get(i), device2avgList.get(i));
        }

        for (int i = 0; i < device1maxList.size(); i++) {
            regression2.addData(device1maxList.get(i), device2maxList.get(i));
        }


        System.out.println("Avg: slope: " + regression1.getSlope() + ", intercept: " + regression1.getIntercept());
        System.out.println("Max: slope: " + regression2.getSlope() + ", intercept: " + regression2.getIntercept());







        XYSeries xyseries = new XYSeries("avg value");
        for (int i = 0; i < device1avgList.size(); i++) {
            xyseries.add(device1avgList.get(i), device2avgList.get(i));
        }
        XYSeriesCollection xyseriescollection = new XYSeriesCollection(); //再用XYSeriesCollection添加入XYSeries 对象
        xyseriescollection.addSeries(xyseries);

        JFreeChart chart = ChartFactory.createScatterPlot("xyPlot", "sony", "samsung", xyseriescollection, PlotOrientation.VERTICAL, true, false, false);
        try {
            ChartUtilities.saveChartAsPNG(new File("C:\\Users\\38284\\Desktop\\indoor navigation\\graph\\sony vs samsung avg.png"), chart, 500, 500);
        } catch (IOException e) {
            e.printStackTrace();
        }
//
//        XYSeries xyseries2 = new XYSeries("point");
//        for (int i = 0; i < device1avgList.size(); i++) {
//            xyseries.add(device1maxList.get(i), device2maxList.get(i));
//        }
//        XYSeriesCollection xyseriescollection2 = new XYSeriesCollection(); //再用XYSeriesCollection添加入XYSeries 对象
//        xyseriescollection2.addSeries(xyseries2);
//
//        JFreeChart chart2 = ChartFactory.createScatterPlot("xyPlot", "sony", "samsung", xyseriescollection2, PlotOrientation.VERTICAL, true, false, false);
//        try {
//            ChartUtilities.saveChartAsPNG(new File("C:\\Users\\38284\\Desktop\\indoor navigation\\graph\\sony vs samsung max.png"), chart2, 500, 500);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }


    private static Set<String> generateIntersactionSet(Set<String> set1, Set<String> set2) {
        Set<String> result = new HashSet<>();
        for (String s: set1) {
            if (set2.contains(s)) {
                result.add(s);
            }
        }
        return result;
    }

    private static void generateMaxAndAvgSignalList(Set<String> commonMacs, List<TargetPoint> tps, List<Double> maxList, List<Double> avgList) {
        for (String mac: commonMacs) {
            double max = Double.NEGATIVE_INFINITY;
            double sum = 0;
            int count = 0;
            for (TargetPoint tp: tps) {
                Optional<APSignal> currSignal = tp.getApSignals().stream().filter(apSignal->apSignal.getMacAddr().equals(mac)).findFirst();
                if (!currSignal.isPresent())
                    continue;
                double curr = currSignal.get().getSignalStrength();
                max = Math.max(max, curr);
                sum += curr;
                count++;
            }
            maxList.add(max);
            avgList.add(sum/count);
        }
    }
}
