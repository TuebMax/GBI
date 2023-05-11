import org.apache.commons.cli.*;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;

/**
 * Assignment 02
 * Authors: Christopher Kolberg and Maximilian Wilhelm
 */
public class Main {

    private static int matchScore;
    private static int mismatchScore;
    private static int gapPenalty;

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

        try {
            // Setting up the command line parser
            CommandLineParser parser = new DefaultParser();
            CommandLine params = parser.parse(cliOptions, args);

            // Extracting the parameters from the command line for the smith waterman aligner
            String filenameQuery = params.getOptionValue("fp1");
            String filenameTarget = params.getOptionValue("fp2");
            String querySequence = SimpleFastaReader.parseFastaFile(new File(filenameQuery)).get(0).getSequence();
            String targetSequence = SimpleFastaReader.parseFastaFile(new File(filenameTarget)).get(0).getSequence();
            matchScore = Integer.parseInt(params.getOptionValue("masc"));
            mismatchScore = Integer.parseInt(params.getOptionValue("misc"));
            gapPenalty = Integer.parseInt(params.getOptionValue("gp"));
            // Creating the smith waterman aligner with the given parameters
            SmithWatermanAligner smithWatermanAligner = new SmithWatermanAligner(matchScore, mismatchScore, gapPenalty, querySequence, targetSequence);
            int optimalScore = smithWatermanAligner.alignSequences();
            System.out.println("The optimal local alignment score for \"" + filenameQuery + "\" and \"" + filenameTarget + "\" is: " + optimalScore);
            // Writing the results to a file
            writeResultsToFile(filenameQuery, filenameTarget, optimalScore, smithWatermanAligner);
        } catch (Exception e) {
            System.err.println("Error parsing arguments: " + e.getMessage());
        }
    }

    /**
     * Writes the results of the smith waterman algorithm to a file
     *
     * @param filenameQuery  the filename of the query sequence
     * @param filenameTarget the filename of the target sequence
     * @param optimalScore   the optimal score of the alignment
     * @param smithWatermanAligner the smith waterman aligner
     */
    private static void writeResultsToFile(String filenameQuery, String filenameTarget, int optimalScore, SmithWatermanAligner smithWatermanAligner) {
        try (BufferedWriter bw = new BufferedWriter(new java.io.FileWriter("alignment.txt"))) {
            bw.write("The optimal local alignment score for \"" + filenameQuery + "\" and \"" + filenameTarget + "\" was calculated \n" +
                    "using the Smith-Waterman algorithm with the following parameters: \n");
            bw.write("----------------------------------------------\n");
            bw.write("Match score: " + matchScore + "\n");
            bw.write("Mismatch score: " + mismatchScore + "\n");
            bw.write("Gap penalty: " + gapPenalty + "\n");
            bw.write("----------------------------------------------\n");
            String traceback = smithWatermanAligner.traceback();

            bw.write("The local alignment starts at index " +
                    (smithWatermanAligner.getStartCell()[0]+1) +
                    " in sequence 1 and at index " +
                    (smithWatermanAligner.getStartCell()[1]+1) +
                    " in sequence 2. \n");
            bw.write("The score of the local alignment is: " + optimalScore + "\n");
            bw.write("----------------------------------------------\n");
            String seq1Loc = traceback.split("\n")[0];
            String seq2Loc = traceback.split("\n")[1];
            // Count the number of matches, mismatches and gaps
            int gapCount = 0;
            int matchCount = 0;
            int mismatchCount = 0;
            for (int i = 0; i < seq1Loc.length(); i++) {
                if (seq1Loc.charAt(i) == '-' || seq2Loc.charAt(i) == '-') {
                    gapCount++;
                } else if (seq1Loc.charAt(i) == seq2Loc.charAt(i)) {
                    matchCount++;
                } else {
                    mismatchCount++;
                }
            }
            bw.write("The local alignment contains " + matchCount + " matches, " + mismatchCount + " mismatches and " + gapCount + " gaps. \n");

            bw.write("----------------------------------------------\n");
            bw.write("The position lines show the index in the original sequences. \n");
            bw.write("The symbol line shows the alignment of the two sequences. \n");
            bw.write("The sequence lines show the sequences with gaps inserted. \n");
            bw.write("| indicates a match, : indicates a mismatch and a space indicates a gap. \n");
            bw.write("----------------------------------------------\n");

            // Write out the alignment to the file
            writeOutAlignment(smithWatermanAligner, bw, traceback);
            bw.newLine();
            bw.write("----------------------------------------------\n");
        } catch (Exception e) {
            System.err.println("Error writing results to file: " + e.getMessage());
        }
    }

    private static void writeOutAlignment(SmithWatermanAligner smithWatermanAligner, BufferedWriter bw, String traceback) throws IOException {
        String seq1Local = traceback.split("\n")[0];
        int seq1Position = smithWatermanAligner.getStartCell()[0];
        String seq2Local = traceback.split("\n")[1];
        int seq2Position = smithWatermanAligner.getStartCell()[1];

        int i = 0;
        StringBuilder position1Line = new StringBuilder("Position 1:  ");
        StringBuilder seq1Line = new StringBuilder("Sequence 1:");
        StringBuilder symbolLine = new StringBuilder("Symbol:    ");
        StringBuilder seq2Line = new StringBuilder("Sequence 2:");
        StringBuilder position2Line = new StringBuilder("Position 2:  ");
        // Iterate through the alignment and extracting the corresponding position/sequence characters/symbol for matching etc.
        while (true) {
            i = i + 1;
            if (seq1Local.length() < i) {
                bw.write(position1Line + "\n");
                bw.write(seq1Line + "\n");
                bw.write(symbolLine + "\n");
                bw.write(seq2Line + "\n");
                bw.write(position2Line + "\n");
                break;
            }
            if (seq1Local.charAt(i - 1) != '-') {
                seq1Position++;
            }
            if (seq2Local.charAt(i - 1) != '-') {
                seq2Position++;
            }
            // Append the characters to the corresponding lines
            // The format function is used to make sure the lines are aligned
            // Every character is followed by 5 spaces
            position1Line.append(String.format("% 4d", seq1Position)).append("  ");
            seq1Line.append("     ").append(seq1Local.charAt(i - 1));
            if (seq1Local.charAt(i - 1) == seq2Local.charAt(i - 1)) {
                symbolLine.append("     |");
            } else if (seq1Local.charAt(i - 1) == '-' || seq2Local.charAt(i - 1) == '-') {
                symbolLine.append("      ");
            } else {
                symbolLine.append("     :");
            }
            seq2Line.append("     ").append(seq2Local.charAt(i - 1));
            position2Line.append(String.format("% 4d", seq2Position)).append("  ");
        }
    }
}
