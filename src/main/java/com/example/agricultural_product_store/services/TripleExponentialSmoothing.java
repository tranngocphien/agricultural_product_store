package com.example.agricultural_product_store.services;

import java.util.ArrayList;
import java.util.List;

public class TripleExponentialSmoothing {
    public static List<Double> forecast(List<Double> y, double alpha, double beta,
                                        double gamma, int period, int m) {

        validateArguments(y, alpha, beta, gamma, period, m);

        int seasons = y.size() / period;
        double a0 = calculateInitialLevel(y);
        double b0 = calculateInitialTrend(y, period);
        List<Double> initialSeasonalIndices = calculateSeasonalIndices(y, period,
                seasons);

        List<Double> forecast = calculateHoltWinters(y, a0, b0, alpha, beta, gamma, 0.1,
                initialSeasonalIndices, period, m);

        return forecast;
    }

    private static void validateArguments(List<Double> y, double alpha, double beta,
                                          double gamma, int period, int m) {
        if (y == null) {
            throw new IllegalArgumentException("Value of y should be not null");
        }

        if (m <= 0) {
            throw new IllegalArgumentException("Value of m must be greater than 0.");
        }

        if (m > period) {
            throw new IllegalArgumentException("Value of m must be <= period.");
        }

        if ((alpha < 0.0) || (alpha > 1.0)) {
            throw new IllegalArgumentException("Value of Alpha should satisfy 0.0 <= alpha <= 1.0");
        }

        if ((beta < 0.0) || (beta > 1.0)) {
            throw new IllegalArgumentException("Value of Beta should satisfy 0.0 <= beta <= 1.0");
        }

        if ((gamma < 0.0) || (gamma > 1.0)) {
            throw new IllegalArgumentException("Value of Gamma should satisfy 0.0 <= gamma <= 1.0");
        }
    }

    private static List<Double> calculateHoltWinters(List<Double> y, double a0, double b0,
                                                     double alpha, double beta, double gamma, double phi,
                                                     List<Double> initialSeasonalIndices, int period, int m) {

        List<Double> St = new ArrayList<Double>(y.size());
        while (St.size() < y.size()) St.add(0.0);

        List<Double> Bt = new ArrayList<Double>(y.size());
        while (Bt.size() < y.size()) Bt.add(0.0);

        List<Double> It = new ArrayList<Double>(y.size());
        while (It.size() < y.size()) It.add(0.0);

        List<Double> Ft = new ArrayList<Double>(y.size() + m);
        while (Ft.size() < y.size() + m) Ft.add(0.0);


        List<Double> MD = new ArrayList<Double>(y.size() + m);
        while (MD.size() < y.size() + m) MD.add(0.0);


        List<Double> MAD = new ArrayList<Double>(y.size() + m);
        while (MAD.size() < y.size() + m) MAD.add(0.0);

        // Initialize base values
        St.add(1, a0);
        Bt.add(1, b0);
        MD.add(1, 0.0);
        MAD.add(1, 0.0);

        for (int i = 0; i < period; i++) {
            It.set(i, initialSeasonalIndices.get(i));
        }

        // Start calculations
        for (int i = 2; i < y.size(); i++) {
            // Adaptive update alpha
            double error = y.get(i) - St.get(i - 1) - Bt.get(i - 1) - It.get(i%period);
            MD.set(i, phi*error + (1-phi)*MD.get(i-1));
            MAD.set(i, phi*Math.abs(error) + (1-phi)*MAD.get(i-1));
            if(MAD.get(i) == 0) {
                alpha = 1;
            } else {
                alpha = Math.abs(MD.get(i)/MAD.get(i));
            }

            // Calculate overall smoothing
            if ((i - period) >= 0) {
                St.set(i, alpha * y.get(i) / It.get(i - period) + (1.0 - alpha)
                        * (St.get(i - 1) + Bt.get(i - 1)));
            } else {
                St.set(i, alpha * y.get(i) + (1.0 - alpha) * (St.get(i - 1) + Bt.get(i - 1)));
            }

            // Calculate trend smoothing
            Bt.set(i, gamma * (St.get(i) - St.get(i - 1)) + (1 - gamma) * Bt.get(i - 1));

            // Calculate seasonal smoothing
            if ((i - period) >= 0) {
                It.set(i, beta * y.get(i) / St.get(i) + (1.0 - beta) * It.get(i - period));
            }

            // Calculate forecast
            if (((i + m) >= period)) {
                Ft.set(i + m, (St.get(i) + m * Bt.get(i)) * It.get(i - period + m));
            }
        }

        return Ft;
    }

    private static double calculateInitialLevel(List<Double> y) {
        return y.get(0);
    }

    private static double calculateInitialTrend(List<Double> y, int period) {

        double sum = 0;

        for (int i = 0; i < period; i++) {
            sum += (y.get(period + i) - y.get(i));
        }

        return sum / (period * period);
    }

    private static List<Double> calculateSeasonalIndices(List<Double> y, int period,
                                                         int seasons) {

        double[] seasonalAverage = new double[seasons];
        double[] seasonalIndices = new double[period];

        double[] averagedObservations = new double[y.size()];

        for (int i = 0; i < seasons; i++) {
            for (int j = 0; j < period; j++) {
                seasonalAverage[i] += y.get((i * period) + j);
            }
            seasonalAverage[i] /= period;
        }

        for (int i = 0; i < seasons; i++) {
            for (int j = 0; j < period; j++) {
                averagedObservations[(i * period) + j] = y.get((i * period) + j)
                        / seasonalAverage[i];
            }
        }

        for (int i = 0; i < period; i++) {
            for (int j = 0; j < seasons; j++) {
                seasonalIndices[i] += averagedObservations[(j * period) + i];
            }
            seasonalIndices[i] /= seasons;
        }

        ArrayList<Double> list = new ArrayList<Double>(seasonalIndices.length);
        for (double d : seasonalIndices) list.add(d);
        return list;
    }

}
