/**
 * Assignment 01
 * Authors: Christopher Kolberg, Maximilian Wilhelm
 */

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
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

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(filepath))) {
            String line;
            String header = "";
            StringBuilder sequence = new StringBuilder();

            while ((line = bufferedReader.readLine()) != null) {
                if (line.startsWith(">")) {
                    if (!header.equals("")) {
                        Fasta newFasta = new Fasta(header, sequence.toString());
                        fastaEntries.add(newFasta);
                        sequence = new StringBuilder();
                    }
                    header = line.substring(1);
                    continue;
                }
                sequence.append(line.replace(" ", ""));
            }
            Fasta newFasta = new Fasta(header, sequence.toString());
            fastaEntries.add(newFasta);

        } catch (Exception e) {
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

        sequence.chars().forEach(intChar -> {
            char ch = (char) intChar;
            if (baseFrequencies.containsKey(ch)) {
                baseFrequencies.put(ch, baseFrequencies.get(ch) + 1);
            } else {
                baseFrequencies.put(ch, 1);
            }
        });

        return baseFrequencies;
    }

    /**
     * 2.4  Writes a Fasta object to a given file path. Must adhere to fasta format conventions
     */
    public void writeOutFasta(Fasta fasta, String filepath) {
        try(BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(filepath, true))) {
            bufferedWriter.write(">" + fasta.getHeader());
            bufferedWriter.newLine();
            String sequence = fasta.getSequence();
            for(int i = 0; i <= sequence.length() - 70; i+=70) {
                bufferedWriter.write(sequence.substring(i, i + 70));
                bufferedWriter.newLine();
            }
            bufferedWriter.write(sequence.substring(sequence.length() - sequence.length() % 70));
            bufferedWriter.newLine();
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
}
