import java.util.List;

/**
 * Assignment 05
 * Authors:Maximilian Wilhelm, Christopher Kolberg
 * <p>
 * Class to compute the CCC score.
 */
public class CCC {
    // Changed the class by removing unused fields since method is now static

    public static double computeCCC(DistanceMatrix distanceMatrix1, DistanceMatrix distanceMatrix2) {
        // Calculate the CCC score of two distance matrices
        double averageMatrix1 = distanceMatrix1.getAverage();
        double averageMatrix2 = distanceMatrix2.getAverage();

        // Implement the given formula for the CCC score
        double numerator = 0;
        double denominator1 = 0;
        double denominator2 = 0;
        List<String> keys = distanceMatrix1.getKeys();
        for(int i = 0; i < keys.size(); i++) {
            for(int j = i + 1; j < keys.size(); j++) {
                numerator += (distanceMatrix1.getDistance(keys.get(i), keys.get(j)) - averageMatrix1) * (distanceMatrix2.getDistance(keys.get(i), keys.get(j)) - averageMatrix2);
                denominator1 += Math.pow(distanceMatrix1.getDistance(keys.get(i), keys.get(j)) - averageMatrix1, 2);
                denominator2 += Math.pow(distanceMatrix2.getDistance(keys.get(i), keys.get(j)) - averageMatrix2, 2);
            }
        }
        double denominator = denominator1 * denominator2;

        if (denominator != 0) {
            return (numerator / Math.sqrt(denominator));
        }
        throw new ArithmeticException("Denominator is 0");
    }
}
