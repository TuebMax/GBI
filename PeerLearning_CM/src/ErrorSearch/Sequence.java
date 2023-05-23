package ErrorSearch;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

public class Sequence {
    // Maps each position in the sequence to the corresponding character
    private final HashMap<Integer, Character> sequence;

    public Sequence(String filepath) {
        readInSequence(filepath);
        this.sequence = new HashMap<>();
    }

    private void readInSequence(String filepath) {
        // read in the sequence from the given filepath
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(filepath))) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] lineSplit = line.split("\t");
                for (int i = 0; i < lineSplit.length; i++) {
                    this.sequence.put(i, lineSplit[i].charAt(0));
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading in scoring matrix file");
        }
    }

    public char getChar(int pos) {
        if (sequence.containsKey(pos)) {
            return sequence.get(pos);
        }else if(!sequence.containsKey(pos)){
            throw new IllegalArgumentException("No character stored at position " + pos);
        }
        return ' ';
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 1; i < sequence.size(); i++) {
            sb.append(sequence.get(i));
        }
        return sb.toString();
    }
}
