import org.apache.commons.cli.*;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Assignment 03
 * Authors:Christopher Kolberg, Maximilian Wilhelm
 */
public class Main {
    public static void main(String[] args) throws ParseException {
        System.out.println("GBI - Exercise Sheet 3");
        Options cliOptions = new Options();

        cliOptions.addOption(Option.builder().option("fp")
                .longOpt("filepath")
                .hasArg(true)
                .required(true)
                .desc("Path to the fasta file")
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
        CommandLineParser parser = new DefaultParser();
        CommandLine params = parser.parse(cliOptions, args);
        String fp;
        int matchScore = 0;
        int mismatchScore = 0;
        int gapPenalty = 0;
        ArrayList<Fasta> sequences = new ArrayList<>();
        try {
            fp = params.getOptionValue("fp");
            matchScore = Integer.parseInt(params.getOptionValue("masc"));
            mismatchScore = Integer.parseInt(params.getOptionValue("misc"));
            gapPenalty = Integer.parseInt(params.getOptionValue("gp"));
            sequences = FastaReader.readFasta(new File(fp));
        } catch (Exception e) {
            System.err.println("Error parsing arguments or reading file: " + e.getMessage());
        }

        // Call all functions from here and organise the output.

        // Catch scenarios in which not four entries were parsed.
        if (sequences.size() != 4) {
            System.err.println("Error: Expected four sequences in input file, but found " + sequences.size() +" sequences instead. Please" +
                    "provide a file with four sequences in order to limit the number of possible alignments and thus the runtime of the program.");
            System.exit(1);
        }

        NeedlemanWunsch aStarMax = null;
        NeedlemanWunsch aStarRest = null;
        int maxScore = Integer.MIN_VALUE;

        // Iterate over all possible combinations of sequences.
        for (int i = 0; i < sequences.size(); i++) {
            for (int j = 0; j < sequences.size(); j++) {
                // Skip the same sequence.
                if (i == j) {
                    continue;
                }
                // Create a new NeedlemanWunsch object for each combination of sequences.
                NeedlemanWunsch nw = new NeedlemanWunsch(matchScore, mismatchScore, gapPenalty, sequences.get(i).getSequence(), sequences.get(j).getSequence());
                nw.alignSequences();
                // Check if the score of the current alignment is higher than the current maximum score.
                if (nw.getAlignmentScore() > maxScore) {
                    aStarMax = nw;
                    maxScore = aStarMax.getAlignmentScore();
                    // Construct a new NeedlemanWunsch object for the remaining two sequences.
                    List<Integer> remainingSequences = new ArrayList<>(Arrays.asList(0,1,2,3));
                    remainingSequences.remove((Integer) i);
                    remainingSequences.remove((Integer) j);

                    aStarRest = new NeedlemanWunsch(matchScore, mismatchScore, gapPenalty,
                            sequences.get(remainingSequences.get(0)).getSequence(), sequences.get(remainingSequences.get(1)).getSequence());
                    aStarRest.alignSequences();
                }
            }
        }

        assert aStarMax != null;
        List<String> combinedSequences = CombineProfiles.combineAlignedSequences(aStarMax.getAlignedSequences(),
                aStarRest.getAlignedSequences(), matchScore, mismatchScore, gapPenalty);
        System.out.println("Multiple sequence alignment:");
        for (String s : combinedSequences) {
            System.out.println(s);
        }
    }
}
