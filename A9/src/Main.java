import org.apache.commons.cli.*;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;

/**
 * Assignment 09
 * Authors: Christopher Kolberg, Maximilian Wilhelm
 */
public class Main {
    public static void main(String[] args) throws Exception {
        System.out.println("GBI - Exercise Sheet 9");
        Options cliOptions = new Options();
        cliOptions.addOption(
                Option.builder().option("hmm").longOpt("markovModel")
                        .hasArg(true)
                        .required(true)
                        .desc("File to parse hidden markov model definition from.")
                        .build()
        );
        cliOptions.addOption(
                Option.builder().option("seqs").longOpt("sequences")
                        .hasArg(true)
                        .required(true)
                        .desc("Fasta format file to read sequences to be decoded with the Viterbi algorithm.")
                        .build()
        );
        CommandLineParser parser = new DefaultParser(); // Init. parser object.
        CommandLine params = parser.parse(cliOptions, args); // Parse built cli options from args.
        File hmmFile = new File(params.getOptionValue("hmm"));
        File sequencesFile = new File(params.getOptionValue("seqs"));
        ArrayList<Fasta> sequencesToDecode = FastaReader.readFasta(sequencesFile);
        HMM hmm = HMM.read(new FileReader(hmmFile));
        Viterbi viterbi = new Viterbi();
        // Run Viterbi algorithm for each sequence in the input file. Write results to output file.
        try(BufferedWriter writer = new BufferedWriter(new FileWriter("output.txt"))){
            for (Fasta fasta : sequencesToDecode) {
                viterbi.runViterbi(fasta.sequence(), hmm, true);
                System.out.println(viterbi.print());
                writer.write(fasta.header());
                writer.newLine();
                writer.write(viterbi.print());
                writer.newLine();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

