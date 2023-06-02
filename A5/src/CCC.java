/**
 * Assignment 05
 * Authors:Maximilian Wilhelm, Christopher Kolberg
 * <p>
 * Class to compute the CCC score.
 */
public class CCC {

    /**
     * First {@link DistanceMatrix} object to be used for CCC computation.
     */
    private DistanceMatrix distanceMatrix1;

    /**
     * Second {@link DistanceMatrix} object to be used for CCC computation.
     */
    private DistanceMatrix distanceMatrix2;

    /*
     Add the implementation for the CCC computation here. The method should be able to consume two distance matrices and return the CCC score.
     Feel free to implement the method as a static method.
     */

    public static double computeCCC(DistanceMatrix distanceMatrix1, DistanceMatrix distanceMatrix2) {
        double averageMatrix1 = distanceMatrix1.getAverage();
        double averageMatrix2 = distanceMatrix2.getAverage();

        double numerator = 0;
        double denominator1 = 0;
        double denominator2 = 0;
        for (String key1 : distanceMatrix1.getKeys()) {
            for (String key2 : distanceMatrix1.getKeys()) {
                numerator += (distanceMatrix1.getDistance(key1, key2) - averageMatrix1) * (distanceMatrix2.getDistance(key1, key2) - averageMatrix2);
                denominator1 += Math.pow(distanceMatrix1.getDistance(key1, key2) - averageMatrix1, 2);
                denominator2 += Math.pow(distanceMatrix2.getDistance(key1, key2) - averageMatrix2, 2);
            }
        }
        double denominator = denominator1 * denominator2;

        if (denominator != 0) {
            return (numerator / Math.sqrt(denominator));
        }
        throw new ArithmeticException("Denominator is 0");
    }
}
