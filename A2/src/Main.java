import org.apache.commons.cli.*;

import java.io.BufferedWriter;
import java.io.File;

/**
 * Assignment 02
 * Authors: Christopher Kolberg and Maximilian Wilhelm
 */
public class Main {
    public static void main(String[] args) {
        System.out.println("GBI - Exercise Sheet 2");
        Options cliOptions = new Options();

        cliOptions.addOption(Option.builder().option("fp1")
                .longOpt("filepath1")
                .hasArg(true)
                .required(true)
                .desc("Path to the first (query) fasta file")
                .build()
        );

        cliOptions.addOption(Option.builder().option("fp2")
                .longOpt("filepath2")
                .hasArg(true)
                .required(true)
                .desc("Path to the first (target) fasta file")
                .build()
        );

        cliOptions.addOption(Option.builder().option("masc")
                .longOpt("matchScore")
                .hasArg(true)
                .required(true)
                .desc("Match score for alignment")
                .build()
        );

        cliOptions.addOption(Option.builder().option("misc")
                .longOpt("mismatchScore")
                .hasArg(true)
                .required(true)
                .desc("Mismatch score for alignment")
                .build()
        );

        cliOptions.addOption(Option.builder().option("gp")
                .longOpt("gapPenalty")
                .hasArg(true)
                .required(true)
                .desc("Gap penalty for alignment")
                .build()
        );

        try (BufferedWriter bw = new BufferedWriter(new java.io.FileWriter("./resources/alignment.txt"))) {
            // Setting up the command line parser
            CommandLineParser parser = new DefaultParser();
            CommandLine params = parser.parse(cliOptions, args);

            // Extracting the parameters from the command line for the smith waterman aligner
            String filenameQuery = params.getOptionValue("fp1");
            String filenameTarget = params.getOptionValue("fp2");
            String querySequence = SimpleFastaReader.parseFastaFile(new File("./resources/" + filenameQuery)).get(0).getSequence();
            String targetSequence = SimpleFastaReader.parseFastaFile(new File("./resources/" + filenameTarget)).get(0).getSequence();
            int matchScore = Integer.parseInt(params.getOptionValue("masc"));
            int mismatchScore = Integer.parseInt(params.getOptionValue("misc"));
            int gapPenalty = Integer.parseInt(params.getOptionValue("gp"));
            SmithWatermanAligner smithWatermanAligner = new SmithWatermanAligner(matchScore, mismatchScore, gapPenalty, querySequence, targetSequence);
            int optimalScore = smithWatermanAligner.alignSequences();
            System.out.println("The optimal local alignment score for \"" + filenameQuery + "\" and \"" + filenameTarget + "\" is: " + optimalScore);
            // Writing the results to a file
            bw.write("The optimal local alignment score for \"" + filenameQuery + "\" and \"" + filenameTarget + "\" is: " + optimalScore);
            bw.newLine();
            bw.write("The optimal local alignment is: ");
            bw.newLine();
            bw.write(smithWatermanAligner.traceback());
            bw.newLine();
            bw.write("The local alignment starts at index " +
                    smithWatermanAligner.getStartCell()[0] +
                    " in sequence 1 and at index " +
                    smithWatermanAligner.getStartCell()[1] +
                    " in sequence 2.");
        } catch (Exception e) {
            System.err.println("Error parsing arguments: " + e.getMessage());
        }
    }
}
