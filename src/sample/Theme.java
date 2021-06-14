package sample;

import colorconverters.JzAzBz;
import org.apache.commons.math3.stat.regression.SimpleRegression;

import java.util.ArrayList;
import java.util.Arrays;

public class Theme {
    int[][] rgbs;
    double[][] jabs;
    double[][] jchs;

    public Theme(ArrayList<int[]> rgb_s) {
        int l = rgb_s.size();
        rgbs = new int[l][3];
        jabs = new double[l][3];
        jchs = new double[l][3];
        for (int i = 0; i < l; i++) {
            rgbs[i] = rgb_s.get(i);
            jabs[i] = JzAzBz.rgbToJab(rgbs[i]);
            jchs[i] = JzAzBz.rgbToJch(rgbs[i]);
        }
    }

    public Theme(Theme _theme){
        int l = _theme.rgbs.length;
        rgbs = new int[l][3];
        jabs = new double[l][3];
        jchs = new double[l][3];
        for (int i = 0; i < l; i++) {
            rgbs[i] = _theme.rgbs[i];
            jabs[i] = JzAzBz.rgbToJab(rgbs[i]);
            jchs[i] = JzAzBz.rgbToJch(rgbs[i]);
        }
    }
    public int[][] generate_C_L() {
        SimpleRegression regression = new SimpleRegression();
        double[][] data = new double[5][2];
        for (int i = 0; i < jchs.length; i++) {
            data[i][0] = jchs[i][0];
            data[i][1] = jchs[i][1];
        }
        regression.addData(data);
        //y=ax+b
        double a = regression.getSlope();
        double b = regression.getIntercept();

        //new data
        double[][] newdata = new double[5][3];
        int[][] newRgb = new int[5][3];

        for (int i = 0; i < jchs.length; i++) {
            newdata[i][0] = data[i][0];
            newdata[i][1] = a * data[i][0] + b;
            newdata[i][2] = jchs[i][2];
            newRgb[i] = JzAzBz.jchToRgb(newdata[i]);
        }
        return newRgb;
    }

    public int[][] generate_H_L() {
        SimpleRegression regression = new SimpleRegression();
        double[][] data = new double[5][2];
        for (int i = 0; i < jchs.length; i++) {
            data[i][0] = jchs[i][0];
            data[i][1] = jchs[i][2];
        }
        regression.addData(data);
        //y=ax+b
        double a = regression.getSlope();
        double b = regression.getIntercept();

        //new data
        double[][] newdata = new double[5][3];
        int[][] newRgb = new int[5][3];

        for (int i = 0; i < jchs.length; i++) {
            newdata[i][0] = data[i][0];
            newdata[i][2] = a * data[i][0] + b;
            newdata[i][1] = jchs[i][1];
            newRgb[i] = JzAzBz.jchToRgb(newdata[i]);
        }
        return newRgb;
    }

    public int[][] generate_L_C() {
        SimpleRegression regression = new SimpleRegression();
        double[][] data = new double[5][2];
        for (int i = 0; i < jchs.length; i++) {
            data[i][0] = jchs[i][1];
            data[i][1] = jchs[i][0];
        }
        regression.addData(data);
        //y=ax+b
        double a = regression.getSlope();
        double b = regression.getIntercept();

        //new data
        double[][] newdata = new double[5][3];
        int[][] newRgb = new int[5][3];

        for (int i = 0; i < jchs.length; i++) {
            newdata[i][1] = data[i][0];
            newdata[i][0] = a * data[i][0] + b;
            newdata[i][2] = jchs[i][2];
            newRgb[i] = JzAzBz.jchToRgb(newdata[i]);
        }
        return newRgb;
    }

    public int[][] generate_L_H() {
        SimpleRegression regression = new SimpleRegression();
        double[][] data = new double[5][2];
        for (int i = 0; i < jchs.length; i++) {
            data[i][0] = jchs[i][2];
            data[i][1] = jchs[i][0];
        }
        regression.addData(data);
        //y=ax+b
        double a = regression.getSlope();
        double b = regression.getIntercept();

        //new data
        double[][] newdata = new double[5][3];
        int[][] newRgb = new int[5][3];

        for (int i = 0; i < jchs.length; i++) {
            newdata[i][2] = data[i][0];
            newdata[i][0] = a * data[i][0] + b;
            newdata[i][1] = jchs[i][1];
            newRgb[i] = JzAzBz.jchToRgb(newdata[i]);
        }
        return newRgb;
    }

    public int[][] generate_C_H() {
        SimpleRegression regression = new SimpleRegression();
        double[][] data = new double[5][2];
        for (int i = 0; i < jchs.length; i++) {
            data[i][0] = jchs[i][2];
            data[i][1] = jchs[i][1];
        }
        regression.addData(data);
        //y=ax+b
        double a = regression.getSlope();
        double b = regression.getIntercept();

        //new data
        double[][] newdata = new double[5][3];
        int[][] newRgb = new int[5][3];

        for (int i = 0; i < jchs.length; i++) {
            newdata[i][2] = data[i][0];
            newdata[i][1] = a * data[i][0] + b;
            newdata[i][0] = jchs[i][0];
            newRgb[i] = JzAzBz.jchToRgb(newdata[i]);
        }
        return newRgb;
    }

    public int[][] generate_H_C() {
        SimpleRegression regression = new SimpleRegression();
        double[][] data = new double[5][2];
        for (int i = 0; i < jchs.length; i++) {
            data[i][0] = jchs[i][1];
            data[i][1] = jchs[i][2];
        }
        regression.addData(data);
        //y=ax+b
        double a = regression.getSlope();
        double b = regression.getIntercept();

        //new data
        double[][] newdata = new double[5][3];
        int[][] newRgb = new int[5][3];

        for (int i = 0; i < jchs.length; i++) {
            newdata[i][1] = data[i][0];
            newdata[i][2] = a * data[i][0] + b;
            newdata[i][0] = jchs[i][0];
            newRgb[i] = JzAzBz.jchToRgb(newdata[i]);
        }
        return newRgb;
    }

    public String toString() {
        String sRgbs = "rgbs : \n";
        for (int i = 0; i < this.rgbs.length; i++) {
            sRgbs += Arrays.toString(this.rgbs[i]) + "\n";
        }

        String sJabs = "jabs : \n";
        for (int i = 0; i < this.jabs.length; i++) {
            sJabs += Arrays.toString(this.jabs[i]) + "\n";
        }
        String sJchs = "jchs : \n";
        for (int i = 0; i < this.jchs.length; i++) {
            sJchs += Arrays.toString(this.jchs[i]) + "\n";
        }

        return sRgbs + "\n" + sJabs + "\n" + sJchs;
    }

    public void sortOn(char p) {//p must be in {l,c,h}
        int param;
        if (p == 'l') param = 0;
        else if (p == 'c') param = 1;
        else if (p == 'h') param = 2;
        int n = jchs.length;
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (jchs[j][p] > jchs[j + 1][p]) {
                    double[] tempJchs = jchs[j];
                    jchs[j] = jchs[j + 1];
                    jchs[j + 1] = tempJchs;

                    int[] temprgbs = rgbs[j];
                    rgbs[j] = rgbs[j + 1];
                    rgbs[j + 1] = temprgbs;

                    double[] tempjabs = jabs[j];
                    jabs[j] = jabs[j + 1];
                    jabs[j + 1] = tempjabs;
                }
            }
        }
    }
}


