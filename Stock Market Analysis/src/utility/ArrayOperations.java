package utility;

import java.util.ArrayList;

/**
 * Created by M. Ding on 2016-10-30.
 * ArrayOperations class is used to do basic operations between
 * 2 or more arrays.
 * Last update: 2016-11-18
 */
public class ArrayOperations {

    /**
     * to subtract the corresponding elements of 2 arrays
     *
     * @param firstArray
     * @param secondArray
     * @return the subtraction of the elements of
     * firstArray and secondArray.
     * If the two input arrays don't have the same length, they
     * will be right aligned. And the shorter length will be
     * taken as the length.
     */
    public static double[] subtract2Arrays(double[] firstArray, double[] secondArray) {
        int resultLength;
        int delta = Math.abs(firstArray.length - secondArray.length);
        double[] subtraction;

        if (firstArray.length >= secondArray.length) {
            resultLength = secondArray.length;
            subtraction = new double[resultLength];
            for (int i = 0; i < resultLength; i++) {
                subtraction[i] = firstArray[i + delta] - secondArray[i];
            }
        } else {
            resultLength = firstArray.length;
            subtraction = new double[resultLength];
            for (int i = 0; i < resultLength; i++) {
                subtraction[i] = firstArray[i] - secondArray[i + delta];
            }
        }

        return subtraction;
    }

    /**
     * to calculate the average of a sliced array.
     *
     * @param entireArray
     * @param startIndex
     * @param endIndex
     * @return the average of all elements in the sliced array
     */
    public static double sliceAverage(double[] entireArray, int startIndex, int endIndex) {
        double tempSum = 0;
        for (int i = startIndex; i <= endIndex; i++) {
            tempSum += entireArray[i];
        }
        return (tempSum / (endIndex - startIndex + 1));
    }

    /**
     * to convert ArrayList<Integer> into an array.
     *
     * @param inputArrayList
     * @return the converted array.
     */
    public static int[] convertToIntArray(ArrayList<Integer> inputArrayList) {
        int[] result = new int[inputArrayList.size()];
        for (int i = 0; i < result.length; i++) {
            result[i] = inputArrayList.get(i).intValue();
        }
        return result;
    }
}
