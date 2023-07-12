import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Assignment 04
 * Authors:Christopher Kolberg, Maximilian Wilhelm
 * <p>
 * Class to read in fasta files.
 */
public class FastaReader {
    public List<Fasta> readInFasta(String filepath) {
        List<Fasta> fastaEntries = new ArrayList<>();
        // read in the file from the given filepath
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(filepath))) {
            String line;
            String header = "";
            StringBuilder sequence = new StringBuilder();

            while ((line = bufferedReader.readLine()) != null) {
                // if the line is a header line; process it differently
                if (line.startsWith(">")) {
                    if (!header.equals("")) {
                        // if there is already a header, create a new Fasta object for the previous sequence
                        Fasta newFasta = new Fasta(header, sequence.toString());
                        fastaEntries.add(newFasta);
                        sequence = new StringBuilder();
                    }
                    // save the header line
                    header = line.substring(1);
                    continue;
                }
                // if the line is not a header line, append the line to the sequence
                sequence.append(line.replace(" ", ""));
            }
            // create a new Fasta object for the last sequence
            Fasta newFasta = new Fasta(header, sequence.toString());
            fastaEntries.add(newFasta);

        } catch (IOException e) {
            System.err.println("Error reading in fasta file");
            e.printStackTrace();
        }

        return fastaEntries;
    }
}
