import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Assignment 04
 * Authors:YOUR NAMES HERE
 * <p>
 * Do not forget to add a class documentation.
 */
public class ScoringMatrix {

    /*
    Add class implementation to store scoring matrices. A scoring matrix should be read from a .tsv file and be capable of returning the
    score stored for two characters. Catch cases in which a score is requested, but not stored for two characters. You may want to use
    Java's Map data structures for the implementation.
     */
    private final Map<Character, Map<Character, Integer>> scoringMat;

    public ScoringMatrix(String filepath) {
        // read in the scoring matrix from the given filepath
        scoringMat = new HashMap<>();
        readInScoringMatrix(filepath);
    }

    private void readInScoringMatrix(String filepath) {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(filepath))) {
            String line;
            String[] header = bufferedReader.readLine().split("\t");
            while ((line = bufferedReader.readLine()) != null) {
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
        if (scoringMat.containsKey(a) && scoringMat.get(a).containsKey(b)) {
            return scoringMat.get(a).get(b);
        }

        throw new IllegalArgumentException("No score stored for " + a + " and " + b);
    }
}
