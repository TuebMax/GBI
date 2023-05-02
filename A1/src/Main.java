import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Assignment 01
 * Authors:Max Wilhelm & Christopher Kolberg
 */

public class Main {
    public static void main(String[] args) throws IOException {

        System.out.println("GBI - Exercise Sheet 1 \n" + "Max Wilhelm & Christopher Kolberg" );
        // Call all functions from here and organise the output
        FastaReader fastaReader = new FastaReader();
        List<Fasta> inputFastas = fastaReader.readInFasta(args[0]);
        for (int i = 0; i < inputFastas.size(); i++) {
            System.out.println("Length of sequence " + (i+1) + ": " + fastaReader.calculateSequenceLength(inputFastas.get(i)));
            System.out.println("Base frequency of sequence " + (i+1) + ":");
            fastaReader.calculateBaseFrequency(inputFastas.get(i)).forEach((ch, count) -> System.out.println(ch + "\t" + count));
        }
        System.out.println("Total number of sequences: " + inputFastas.size());


        String outputFilename = "ry_" + args[0];
        File ryOutputFile = new File(outputFilename);
        ryOutputFile.createNewFile();
        for (int i = 0; i < inputFastas.size(); i++) {
            String rySequence = inputFastas.get(i).getSequence();
            rySequence = rySequence.replace("A", "R");
            rySequence = rySequence.replace("G", "R");

            rySequence = rySequence.replace("C", "Y");
            rySequence = rySequence.replace("T", "Y");
            fastaReader.writeOutFasta(new Fasta(inputFastas.get(i).getHeader(), rySequence), outputFilename);
        }

        if(args[0].equals("sequences-edit.fasta")){
            List<Fasta> editFastas = fastaReader.readInFasta(args[0]);
            System.out.println("The edit distance between the sequences is: " + new EditDistance().computeEditDistance(editFastas.get(0), editFastas.get(1)));
        }
    }

}

