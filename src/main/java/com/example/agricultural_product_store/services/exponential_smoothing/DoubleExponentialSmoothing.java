package com.example.agricultural_product_store.services.exponential_smoothing;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DoubleExponentialSmoothing {
    public static List<Double> forecast(List<Double> y, double alpha,
                                        double gamma, int m) {
        double a0 = calculateInitialLevel(y);
        double b0 = calculateInitialTrend(y);

        List<Double> forecast = calculateHolt(y, a0, b0, alpha, gamma, 0.1, m);

        return forecast;
    }

    private static List<Double> calculateHolt(List<Double> y, double a0, double b0,
                                              double alpha, double gamma, double phi, int m) {

        List<Double> St = new ArrayList<Double>(y.size());
        while (St.size() < y.size()) St.add(0.0);

        List<Double> Bt = new ArrayList<Double>(y.size());
        while (Bt.size() < y.size()) Bt.add(0.0);

        List<Double> Ft = new ArrayList<Double>(y.size() + m);
        while (Ft.size() < y.size() + m) Ft.add(0.0);

        List<Double> MD = new ArrayList<Double>(y.size());
        while (MD.size() < y.size()) MD.add(0.0);

        List<Double> MAD = new ArrayList<Double>(y.size());
        while (MAD.size() < y.size()) MAD.add(0.0);

        // Initialize base values
        St.add(0, a0);
        Bt.add(0, b0);
        MD.add(0, 0.0);
        MAD.add(0, 0.0);
        Ft.set(1, a0);

        for (int i = 1; i < y.size(); i++) {
            // Calculate overall smoothing
            St.set(i, alpha * y.get(i) + (1.0 - alpha) * (St.get(i - 1) + Bt.get(i - 1)));
            // Calculate trend smoothing
            Bt.set(i, gamma * (St.get(i) - St.get(i - 1)) + (1 - gamma) * Bt.get(i - 1));

            Ft.set(i + 1, (St.get(i) + Bt.get(i)));

        }
        // Calculate forecast
        for (int i = 1; i < m; i++) {
            Ft.set(y.size() + i, (St.get(y.size() - 1) + (i + 1) * Bt.get(y.size() - 1)));
        }
        return Ft;
    }

    private static double calculateInitialLevel(List<Double> y) {
        return y.get(0);
    }

    private static double calculateInitialTrend(List<Double> y) {
        if(y.size() == 1) {
            return y.get(0);
        }
        return (y.get(y.size() - 1) - y.get(0))/(y.size() - 1);
    }

}
