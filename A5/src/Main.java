import org.apache.commons.cli.*;

/**
 * Assignment 05
 * Authors:Maximilian Wilhelm, Christopher Kolberg
 */
public class Main {
    public static void main(String[] args) throws ParseException {
        System.out.println("GBI - Exercise Sheet 5");
        // For command line interface parsing you can use the commons-cli package as follows (however, feel free to use any other approach):
        Options cliOptions = new Options();
        cliOptions.addOption(Option.builder().option("ori") // Short option name set to 'p1'.
                .longOpt("distOriginal") // Long option name set to 'parameter1'.
                .hasArg(true) // Parameter has an argument.
                .required(true) // Parameter is required.
                .desc("Distances original matrix") // Description of the parameter.
                .build() // Build the option.
        );
        cliOptions.addOption(Option.builder().option("t1")
                .longOpt("tree1")
                .hasArg(true)
                .required(true)
                .desc("Distance matrix derived from tree 1")
                .build()
        );
        cliOptions.addOption(Option.builder().option("t2")
                .longOpt("tree2")
                .hasArg(true)
                .required(true)
                .desc("Distance matrix derived from tree 2")
                .build()
        );
        CommandLineParser parser = new DefaultParser();
        CommandLine params = parser.parse(cliOptions, args);
        try {
            params.getOptionValue("ori");
            params.getOptionValue("t1");
            params.getOptionValue("t2");
        } catch (Exception e) {
            System.out.println("Missing parameters");
            System.exit(1);
        }

        // Read the distance matrices from the files specified in the command line arguments.
        String originalMatrixPath = params.getOptionValue("ori");
        String tree1MatrixPath = params.getOptionValue("t1");
        String tree2MatrixPath = params.getOptionValue("t2");

        DistanceMatrix originalMatrix = new DistanceMatrix(originalMatrixPath);
        DistanceMatrix tree1Matrix =  new DistanceMatrix(tree1MatrixPath);
        DistanceMatrix tree2Matrix =  new DistanceMatrix(tree2MatrixPath);

        // Compute the cophenetic correlation coefficient (CCC) for the two trees.
        double cccScore1 = CCC.computeCCC(originalMatrix, tree1Matrix);
        double cccScore2 = CCC.computeCCC(originalMatrix, tree2Matrix);

        // Print the CCC scores.
        System.out.println("CCC score for tree 1: " + cccScore1);
        System.out.println("CCC score for tree 2: " + cccScore2);
    }
}