import org.apache.commons.cli.*;

import java.io.IOException;

/**
 * Assignment 04
 * Authors:YOUR NAMES HERE
 */
public class Main {
    public static void main(String[] args) throws IOException, ParseException {
        System.out.println("GBI - Exercise Sheet 4");
        // For command line interface parsing you can use the commons-cli package as follows (however, feel free to use any other approach):
        Options cliOptions = new Options();
        cliOptions.addOption(Option.builder().option("p1") // Short option name set to 'p1'.
                .longOpt("parameter1") // Long option name set to 'parameter1'.
                .hasArg(true) // Parameter has an argument.
                .required(true) // Parameter is required.
                .desc("CLI parameter example") // Description of the parameter.
                .build() // Build the option.
        );
        /*
        The built option can be passed via cli either with -p1 SOME_VALUE or --parameter1 SOME_VALUE
        You can add the parameter via the Run Configuration > Program Arguments field.
         */
        CommandLineParser parser = new DefaultParser(); // Init. parser object.
        CommandLine params = parser.parse(cliOptions, args); // Parse built cli options from args.
        // The option value can be retrieved as follows; By default a String type is returned. Ensure to cast to correct type.
        String p1 = params.getOptionValue("p1");
        // If the option was not set (and is not required), null is returned.
        if (p1 == null) {
            p1 = "SOME_VALUE"; // This way you can ensure to set a default value.
        }
        // Call all functions from here and organise the output.
        /*
        - Read in the fasta entries to build an MSA from.
        - Read in the scoring matrix to use for the progressive alignment.
        - Build the data structure that is used to guide the alignment process, i.e., either manually (d) or by parsing a generalized guide
        tree structure (d').
        - Run the progressive alignment by supplying the input sequences, scoring matrix, gap penalty and guide data structure.
         */
    }
}