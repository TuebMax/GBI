import org.apache.commons.cli.*;

import org.biojava.nbio.genome.parsers.gff.FeatureList;
import org.biojava.nbio.genome.parsers.gff.GFF3Reader;

/**
 * Assignment 10
 * Authors: Christopher Kolberg & Maximilian Wilhelm
 */
public class Main {
    public static void main(String[] args) throws Exception {
        System.out.println("GBI - Exercise Sheet 10");
        Options cliOptions = new Options();
        cliOptions.addOption(
                Option.builder().option("gt").longOpt("groundtruth")
                        .hasArg(true)
                        .required(true)
                        .desc("Ground truth GFF")
                        .build()
        );
        cliOptions.addOption(
                Option.builder().option("gff1").longOpt("gff1")
                        .hasArg(true)
                        .required(true)
                        .desc("GFF output from first method")
                        .build()
        );
        cliOptions.addOption(
                Option.builder().option("gff2").longOpt("gff2")
                        .hasArg(true)
                        .required(true)
                        .desc("GFF output from second method")
                        .build()
        );
        cliOptions.addOption(Option.builder().option("seq").longOpt("sequence")
                .hasArg(true)
                .required(false)
                .desc("Fasta file containing the genome sequence")
                .build()
        );


        CommandLineParser parser = new DefaultParser(); // Init. parser object.
        CommandLine params = parser.parse(cliOptions, args); // Parse built cli options from args.

        FeatureList groundtruth = GFF3Reader.read(params.getOptionValue("gt"));
        FeatureList gff1 = GFFReader.read(params.getOptionValue("gff1"));
        FeatureList gff2 = GFFReader.read(params.getOptionValue("gff2"));

        // Length of the sequence (default: 3397754 (given in the exercise sheet))
        int seqLength = 3397754;
        try{
            Fasta fasta = new FastaReader().readInFasta(params.getOptionValue("seq")).get(0);
            seqLength = fasta.getSequence().length();
        }catch (Exception e){
            System.out.println("No sequence file provided, using default length of 3397754");
        }

        System.out.println("Comparison of genome annotation predictions:");

        String[] methods = {"GeneMark", "Prokka"}; //replace with the methods you picked
        FeatureList[] gffs = {gff1, gff2};

        for(int i = 0; i<2; i++) {
            CompareGFFs current_comp = new CompareGFFs(groundtruth, gffs[i], seqLength);
            System.out.println(String.format("Results for %s:", methods[i]));
            System.out.println(String.format("Sensitivity: %f", current_comp.computeSensitivity()));
            System.out.println(String.format("Specificity: %f", current_comp.computeSpecificity()));
            System.out.println(String.format("Accuracy: %f \n", current_comp.computeAccuracy()));
        }
    }
}
