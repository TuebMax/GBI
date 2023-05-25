import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Assignment 04
 * Authors: Christopher Kolberg, Maximilian Wilhelm
 * <p>
 * Class to read in and store scoring matrices.
 */
public class ScoringMatrix {

    private final Map<Character, Map<Character, Integer>> scoringMat;

    public ScoringMatrix(String filepath) {
        // read in the scoring matrix from the given filepath
        scoringMat = new HashMap<>();
        readInScoringMatrix(filepath);
    }

    private void readInScoringMatrix(String filepath) {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(filepath))) {
            String line;
            // Split at tabs since the scoring matrix is tab separated
            String[] header = bufferedReader.readLine().split("\t");
            while ((line = bufferedReader.readLine()) != null) {
                // Split at tabs since the scoring matrix is tab separated
                String[] lineSplit = line.split("\t");
                Map<Character, Integer> row = new HashMap<>();
                for (int i = 1; i < lineSplit.length; i++) {
                    row.put(header[i].charAt(0), Integer.parseInt(lineSplit[i]));
                }
                scoringMat.put(lineSplit[0].charAt(0), row);
            }
        } catch (IOException e) {
            System.err.println("Error reading in scoring matrix file");
            System.exit(-1);
        }
    }

    public int getScore(char a, char b) {
        // Return the score for the given characters otherwise throw an exception
        if (scoringMat.containsKey(a) && scoringMat.get(a).containsKey(b)) {
            return scoringMat.get(a).get(b);
        }
        throw new IllegalArgumentException("No score stored for " + a + " and " + b);
    }
}
