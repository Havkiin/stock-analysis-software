import utility.ArrayOperations;

import java.util.ArrayList;

/**
 * Created by M. Ding on 2016-10-30.
 * a class containing some indicators for buy/sell decision.
 * Last update: 2016-11-18
 */
public class Indicators {
    /**
     * if the curve of shorter term moving average goes across
     * the longer term moving from the bottom to the top, the
     * intersection would be a signal for buying.
     *
     * @param shorterTermMovingAverages
     * @param longerTermMovingAverages
     * @return the index of the points where you should buy.
     * index 0 of the return array is calculated from index 0
     * of longerTermMovingAverages
     */
    public static int[] buySignal(double[] shorterTermMovingAverages, double[] longerTermMovingAverages) {
        int subLength = longerTermMovingAverages.length;
        ArrayList resultArrayList = new ArrayList<Integer>();
        double[] subtraction = ArrayOperations.subtract2Arrays(shorterTermMovingAverages, longerTermMovingAverages);

        for (int i = 0; i < subLength - 1; i++) {
            if ((subtraction[i] * subtraction[i + 1] < 0) &&
                    (subtraction[i] < 0) &&
                    (subtraction[i + 1] > 0)) {
                resultArrayList.add(i);
            } else if ((i != 0) &&
                    (subtraction[i] == 0) &&
                    (subtraction[i + 1] > 0) &&
                    (subtraction[i - 1] < 0)) {
                resultArrayList.add(i);
            }
        }
        return ArrayOperations.convertToIntArray(resultArrayList);
    }

    /**
     * if the curve of shorter term moving average goes across
     * the longer term moving from the bottom to the top, the
     * intersection would be a signal for buying.
     *
     * @param shorterTermMovingAverages
     * @param longerTermMovingAverages
     * @return
     */
    public static int[] sellSignal(double[] shorterTermMovingAverages, double[] longerTermMovingAverages) {
        int subLength = longerTermMovingAverages.length;
        ArrayList resultArrayList = new ArrayList<Integer>();
        double[] subtraction = ArrayOperations.subtract2Arrays(shorterTermMovingAverages, longerTermMovingAverages);

        for (int i = 0; i < subLength - 1; i++) {
            if ((subtraction[i] * subtraction[i + 1] < 0) &&
                    (subtraction[i] > 0) &&
                    (subtraction[i + 1] < 0)) {
                resultArrayList.add(i);
            } else if ((i != 0) &&
                    (subtraction[i] == 0) &&
                    (subtraction[i + 1] < 0) &&
                    (subtraction[i - 1] > 0)) {
                resultArrayList.add(i);
            }
        }
        return ArrayOperations.convertToIntArray(resultArrayList);
    }

}
