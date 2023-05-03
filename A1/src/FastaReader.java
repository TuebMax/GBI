/**
 * Assignment 01
 * Authors: Christopher Kolberg, Maximilian Wilhelm
 */

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FastaReader {
    /**
     * 2.1: Method to read in a fasta file. It generates a new fasta objects for each entry.
     * This function is also needed for Exercise 3, so be sure to start on time with it.
     */
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

    /**
     * 2.2: Returns the sequence length
     */
    public int calculateSequenceLength(Fasta fasta) {
        // Calculate length of the Fasta object
        return fasta.getSequence().length();
    }

    /**
     * 2.3 Calculates frequency for each base
     */
    public Map<Character, Integer> calculateBaseFrequency(Fasta fasta) {
        Map<Character, Integer> baseFrequencies = new HashMap<>();
        String sequence = fasta.getSequence();

        // iterate through the sequence and put the frequency of each base in the map
        sequence.chars().forEach(intChar -> {
            char ch = (char) intChar;
            if (baseFrequencies.containsKey(ch)) {
                // if the base is already in the map, increment the value by 1
                baseFrequencies.put(ch, baseFrequencies.get(ch) + 1);
            } else {
                // if the base is not in the map, add it
                baseFrequencies.put(ch, 1);
            }
        });

        return baseFrequencies;
    }

    /**
     * 2.4  Writes a Fasta object to a given file path. Must adhere to fasta format conventions
     */
    public void writeOutFasta(Fasta fasta, String filepath) {
        // create new Writer for writing to the given filepath; it appends to the end of the file
        try(BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(filepath, true))) {
            // write the header
            bufferedWriter.write(">" + fasta.getHeader());
            bufferedWriter.newLine();
            // write the sequence with 70 characters per line
            String sequence = fasta.getSequence();
            for(int i = 0; i <= sequence.length() - 70; i+=70) {
                bufferedWriter.write(sequence.substring(i, i + 70));
                bufferedWriter.newLine();
            }
            bufferedWriter.write(sequence.substring(sequence.length() - sequence.length() % 70));
            bufferedWriter.newLine();
        }catch (IOException e) {
            System.err.println("Error writing out fasta file");
            e.printStackTrace();
        }
    }
}
