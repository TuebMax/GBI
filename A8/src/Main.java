import org.apache.commons.cli.*;

import java.io.File;
import java.io.FileReader;
import java.nio.file.Files;
import java.util.Arrays;

/**
 * Assignment 08
 * Authors:YOUR NAMES HERE
 * You do not need to change the code here!
 */
public class Main {
    public static void main(String[] args) throws Exception {
        System.out.println("GBI - Exercise Sheet 8");
        // For command line interface parsing you can use the commons-cli package as follows (however, feel free to use any other approach):
        Options cliOptions = new Options();
        cliOptions.addOption(
                Option.builder().option("mm").longOpt("markovModel")
                        .hasArg(true)
                        .required(true)
                        .desc("Files to parse markov model definitions from.") // Description of the parameter.
                        .build() // Build the option.
        );
        cliOptions.addOption(
                Option.builder().option("s").longOpt("simulate")
                        .hasArg(false)
                        .required(false)
                        .desc("(Optional) Flag to set in order to simulate a sequence with the parsed model.")
                        .build()
        );
        cliOptions.addOption(Option.builder().option("mc").longOpt("markovChain")
                .hasArg(true)
                .required(false)
                .desc("(Optional) File to parse markov chain from to determine log probability.") // Description of the parameter.
                .build() // Build the option.
        );
        cliOptions.addOption(Option.builder().option("mc").longOpt("markovChain").hasArg(true).required(false).desc("(Optional) File to parse markov chain from to determine log probability.") // Description of the parameter.
                .build() // Build the option.
        );
        CommandLineParser parser = new DefaultParser(); // Init. parser object.
        CommandLine params = parser.parse(cliOptions, args); // Parse built cli options from args.
        File markovModelFile = new File(params.getOptionValue("mm"));
        File markovChainFile = null;
        if (params.hasOption("mc")) {
            markovChainFile = new File(params.getOptionValue("mc"));
        }
        MarkovModel markovModel = new MarkovModel();
        markovModel.readModel(new FileReader(markovModelFile));
        System.out.println("Parsed markov model:");
        System.out.println(markovModel); // Print read model to console.
        if (params.hasOption("s")) {
            String markovChain = markovModel.simulate();
            System.out.println("Simulated markov chain:");
            System.out.println(markovChain);
            System.out.println("Simulated markov chain log probability:");
            System.out.println(markovModel.getLogProbability(markovChain));
        }
        if (markovChainFile != null) {
            String markovChain = Files.readAllLines(markovChainFile.toPath()).get(0).strip();
            System.out.println("Loaded markov chain:");
            System.out.println(markovChain);
            System.out.println("Loaded markov chain log probability:");
            System.out.println(markovModel.getLogProbability(markovChain));
        }

    }
}
