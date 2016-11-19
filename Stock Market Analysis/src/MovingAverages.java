import utility.ArrayOperations;

/**
 * Created by M. Ding on 2016-10-30.
 * to calculate different types of moving averages.
 * Last update: 2016-11-18
 */
public class MovingAverages {
    /**
     * The most common method: Simple Moving Average (SMA)
     *
     * @param closingPrices - an array of historical data of closing prices
     * @param periodInDays  - could be 20 days, 50 days, 100 days or 200 days
     * @return an array of simple moving average which
     * starts at the index of (period - 1)
     */
    public double[] simpleMovingAverage(double[] closingPrices, int periodInDays) {
        int dataPoints = closingPrices.length;
        int resultLength = dataPoints - (periodInDays - 1);
        double[] result = new double[resultLength];
        for (int i = 0; i < resultLength; i++) {
            result[i] = ArrayOperations.sliceAverage(closingPrices, i, i + periodInDays - 1);
        }
        return result;
    }

    // public static double[] linearWeightedAverage() {} // not required in Iteration #1

    // public static double[] exponentialMovingAverage () {} // not required in Iteration #1


}

