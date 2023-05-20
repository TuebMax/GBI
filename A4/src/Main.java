import org.apache.commons.cli.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Assignment 04
 * Authors: Christopher Kolberg, Maximilian Wilhelm
 */
public class Main {
    public static void main(String[] args) throws ParseException {
        System.out.println("GBI - Exercise Sheet 4");
        Options cliOptions = new Options();

        cliOptions.addOption(Option.builder().option("sm")
                .longOpt("scoring-matrix")
                .hasArg(true)
                .required(true)
                .desc("Scoring matrix file")
                .build()
        );

        cliOptions.addOption(Option.builder().option("gp")
                .longOpt("gap-penalty")
                .hasArg(true)
                .required(true)
                .desc("Gap penalty for the alignment")
                .build()
        );

        cliOptions.addOption(Option.builder().option("sq")
                .longOpt("sequences")
                .hasArg(true)
                .required(true)
                .desc("File containing the sequences to align")
                .build()
        );
        CommandLineParser parser = new DefaultParser();
        CommandLine params = parser.parse(cliOptions, args);

        String scoringMatrixFilepath;
        int gapPenalty = 0;
        String sequencesFilepath;
        List<Fasta> sequences = null;
        ScoringMatrix scoringMatrix = null;
        // Parse the command line arguments.
        try {
            scoringMatrixFilepath = params.getOptionValue("sm");
            scoringMatrix = new ScoringMatrix(scoringMatrixFilepath);
            sequencesFilepath = params.getOptionValue("sq");
            gapPenalty = Integer.parseInt(params.getOptionValue("gp"));
            sequences = new FastaReader().readInFasta(sequencesFilepath);
        } catch (Exception e) {
            System.err.println("Error parsing arguments or reading file: " + e.getMessage());
            System.exit(-1);
        }





        // Call all functions from here and organise the output.
        /*
        - Read in the fasta entries to build an MSA from.
        - Read in the scoring matrix to use for the progressive alignment.
        - Build the data structure that is used to guide the alignment process, i.e., either manually (d) or by parsing a generalized guide
        tree structure (d').
        - Run the progressive alignment by supplying the input sequences, scoring matrix, gap penalty and guide data structure.
         */
        List<String> sequencesAsString = new ArrayList<>();
        for (Fasta fasta : sequences) {
            sequencesAsString.add(fasta.getSequence());
        }
        ProgressiveAlignment progressiveAlignment = new ProgressiveAlignment(scoringMatrix, gapPenalty);

        ProfileSet left1 = new ProfileSet();
        ProfileSet left2 = new ProfileSet();
        ProfileSet left3 = new ProfileSet();
        ProfileSet right2 = new ProfileSet();
        left3.setProfile(new ArrayList<>(List.of(sequencesAsString.get(0))));
        left3.setProfile(new ArrayList<>(List.of(sequencesAsString.get(1))));
        left2.setProfile(new ArrayList<>(List.of(sequencesAsString.get(2))));
        left2.add(left3);
        left1.add(left2);
        right2.setProfile(new ArrayList<>(List.of(sequencesAsString.get(3))));
        right2.setProfile(new ArrayList<>(List.of(sequencesAsString.get(4))));
        left1.add(right2);

        ProfileSet root = new ProfileSet();
        root.setProfile(new ArrayList<>(List.of(sequencesAsString.get(5))));
        root.add(left1);

        List<String> combinedSequences = progressiveAlignment.alignSequences(sequencesAsString, root);

        // Print the multiple sequence alignment.
        System.out.println("Multiple sequence alignment:");
        for (String s : combinedSequences) {
            System.out.println(s);
        }
    }
}
