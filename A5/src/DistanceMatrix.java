import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Assignment 05
 * Authors:Maximilian Wilhelm, Christopher Kolberg
 * <p>
 * Class to read in and store scoring matrices.
 */
public class DistanceMatrix {

    private final Map<String, Map<String, Double>> distanceMat;

    public DistanceMatrix(String filepath) {
        // read in the scoring matrix from the given filepath
        distanceMat = new HashMap<>();
        readInDistanceMatrix(filepath);
    }

    private void readInDistanceMatrix(String filepath) {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(filepath))) {
            String line;
            // Split at tabs since the scoring matrix is tab separated
            String[] header = bufferedReader.readLine().split("\t");
            while ((line = bufferedReader.readLine()) != null) {
                // Split at tabs since the scoring matrix is tab separated
                String[] lineSplit = line.split("\t");
                Map<String, Double> row = new HashMap<>();
                for (int i = 1; i < lineSplit.length; i++) {
                    row.put(header[i], Double.parseDouble(lineSplit[i]));
                }
                distanceMat.put(lineSplit[0], row);
            }
        } catch (IOException e) {
            System.err.println("Error reading in distance matrix file");
            System.exit(-1);
        }
    }

    public List<String> getKeys() {
        return new ArrayList<>(distanceMat.keySet());
    }

    public double getAverage(){
        // Calculate the average score of the scoring matrix; only consider the upper triangle of the matrix
        double average = 0;
        int counter = 0;
        for (int i = 0; i < distanceMat.size(); i++) {
            for (int j = i + 1; j < distanceMat.size(); j++) {
                average += distanceMat.get(getKeys().get(i)).get(getKeys().get(j));
                counter++;
            }
        }
        assert counter != 0;
        return average / counter;
    }

    public double getDistance(String a, String b) {
        // Return the score for the given characters otherwise throw an exception
        if (distanceMat.containsKey(a) && distanceMat.get(a).containsKey(b)) {
            return distanceMat.get(a).get(b);
        }
        throw new IllegalArgumentException("No score stored for " + a + " and " + b);
    }

}
