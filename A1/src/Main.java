import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Assignment 01
 * Authors: Christopher Kolberg, Maximilian Wilhelm
 */

public class Main {
    public static void main(String[] args) throws IOException {

        System.out.println("GBI - Exercise Sheet 1 \n" + "Christopher Kolberg & Maximilian Wilhelm" );
        // Call all functions from here and organise the output
        FastaReader fastaReader = new FastaReader();

        // read in the fasta objects from the command line arguments
        List<Fasta> inputFastas = fastaReader.readInFasta(args[0]);
        for (int i = 0; i < inputFastas.size(); i++) {
            // print out length of the sequence for each fasta object
            System.out.println("Length of sequence " + (i+1) + ": " + fastaReader.calculateSequenceLength(inputFastas.get(i)));
            // print out base frequency of the sequence for each fasta object
            System.out.println("Base frequency of sequence " + (i+1) + ":");
            fastaReader.calculateBaseFrequency(inputFastas.get(i)).forEach((ch, count) -> System.out.println(ch + "\t" + count));
        }
        // print out the total number of sequences
        System.out.println("Total number of sequences: " + inputFastas.size());

        // output the RY sequences of the input fasta objects
        outputAsRYFile(args[0], fastaReader, inputFastas);

        // if the input file is a fasta file with the name "sequences-edit.fasta" the edit distance between the sequences is calculated and printed
        if(args[0].equals("sequences-edit.fasta")){
            List<Fasta> editFastas = fastaReader.readInFasta(args[0]);
            System.out.println("The edit distance between the sequences is: " + new EditDistance().computeEditDistance(editFastas.get(0), editFastas.get(1)));
        }
    }

    /**
     * Creates a new fasta file with the RY sequence of the given (perhaps multiple) input fasta objects.
     *
     */
    private static void outputAsRYFile(String filename, FastaReader fastaReader, List<Fasta> inputFastas) throws IOException {
        String outputFilename = "ry_" + filename;
        // Create new output file
        File ryOutputFile = new File(outputFilename);
        ryOutputFile.createNewFile();
        // iterate over the fasta list; replacing A, G with R and C, T with Y and saving the newly created fasta object
        for (int i = 0; i < inputFastas.size(); i++) {
            String rySequence = inputFastas.get(i).getSequence();

            rySequence = rySequence.replace("A", "R");
            rySequence = rySequence.replace("G", "R");
            rySequence = rySequence.replace("C", "Y");
            rySequence = rySequence.replace("T", "Y");

            fastaReader.writeOutFasta(new Fasta(inputFastas.get(i).getHeader(), rySequence), outputFilename);
        }
    }

}

