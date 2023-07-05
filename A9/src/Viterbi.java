/**
 * Assignment 09
 * Authors: Christopher Kolberg, Maximilian Wilhelm
 * <p>
 * Code skeleton for viterbi implementation.
 */
public class Viterbi {

    // The viterbi matrix.
    private double[][] viterbiMatrix;
    // The traceback matrix.
    private int[][] tracebackMatrix;
    // The string of symbols to decode.
    private String symbols;
    // The traceback string containing the decoded states.
    private String traceback;

    /**
     * Runs the Viterbi decoding on a given string of symbols and hmm.
     *
     * @param symbols     The string of symbols to decode.
     * @param hmm         The {@link HMM} model to use.
     * @param isOptimized Whether to use the optimized version of the algorithm which prevents numerical underflow.
     * @return The string of decoded states
     */
    public String runViterbi(String symbols, HMM hmm, boolean isOptimized) {
        // Initialize the matrices. Dimensions are noStates x (symbols.length + 1). The +1 is for the initial state.
        tracebackMatrix = new int[hmm.getNoStates()][symbols.length()+1];
        viterbiMatrix = new double[hmm.getNoStates()][symbols.length()+1];
        this.symbols = symbols;
        // Do the actual viterbi algorithm in 4 steps.
        initialize(hmm);

        recursion(hmm, isOptimized);

        int terminationIndex = termination(hmm);

        traceback = traceback(hmm, terminationIndex);
        return traceback;
    }

    private void initialize(HMM hmm) {
        // Initialize the viterbi matrix with 1 for the initial state.
        viterbiMatrix[0][0] = 1;
        // Initialize the traceback matrix with -1 for the initial state.
        tracebackMatrix[0][0] = -1;
        for (int i = 1; i < hmm.getNoStates(); i++) {
            // Initialize the viterbi matrix with 0 for all other states.
            viterbiMatrix[i][0] = 0;
            tracebackMatrix[i][0] = -1;
        }
    }

    private void recursion(HMM hmm, boolean isOptimized) {
        // Iterate over all symbols.
        for (int i = 0; i < symbols.length(); i++) {
            // Iterate over all states.
            for (int j = 1; j < hmm.getNoStates(); j++) {
                // Find the maximum value in the previous column of the viterbi matrix.
                double max = Float.NEGATIVE_INFINITY;
                int maxIndex = 0;
                for (int k = 0; k < hmm.getNoStates(); k++) {
                    double value;
                    // Skip if the value is 0 or -inf to prevent false values in the optimized version.
                    if(viterbiMatrix[k][i] == 0 || viterbiMatrix[k][i] == Float.NEGATIVE_INFINITY) continue;

                    if (isOptimized) {
                        // Use log10 to prevent numerical underflow.
                        value = viterbiMatrix[k][i] +
                                Math.log10(hmm.getTransitionProbability(k, j)) +
                                Math.log10(hmm.getEmissionProbability(j, hmm.getSymbolIndex(symbols.charAt(i))));
                    } else {
                        // Use the normal version.
                        value = viterbiMatrix[k][i] *
                                hmm.getTransitionProbability(k, j) *
                                hmm.getEmissionProbability(j, hmm.getSymbolIndex(symbols.charAt(i)));
                    }
                    if (value > max) {
                        max = value;
                        maxIndex = k;
                    }
                }
                // Set the value in the viterbi matrix and the traceback matrix.
                viterbiMatrix[j][i+1] = max;
                tracebackMatrix[j][i+1] = maxIndex;
            }
        }

    }

    private int termination(HMM hmm) {
        // Find the maximum value in the last column of the viterbi matrix.
        double max = Float.NEGATIVE_INFINITY;
        int maxIndex = 0;
        for (int i = 1; i < hmm.getNoStates(); i++) {
            double value = viterbiMatrix[i][symbols.length()];
            if (value > max) {
                max = value;
                maxIndex = i;
            }
        }
        return maxIndex;
    }

    private String traceback(HMM hmm, int terminationIndex) {
        // Traceback the most likely sequence of states.
        StringBuilder result = new StringBuilder();
        int index = terminationIndex;
        result.append(hmm.getStateName(index));
        // Start at the end of the traceback matrix and follow the path back to the initial state.
        for (int i = symbols.length(); i > 1; i--) {
            index = tracebackMatrix[index][i];
            result.append(hmm.getStateName(index));
        }
        // Reverse the result string to get the correct order.
        return result.reverse().toString();
    }

    /**
     * Pretty console log of a input sequence and the decoded states.
     *
     * @return formated version of s
     */
    public String print() {
        StringBuilder prettyOutput = new StringBuilder();

        int index = 0;
        while (index < symbols.length()) {
            prettyOutput.append("Sequence: ");
            prettyOutput.append(symbols, index, Math.min(index + 60, symbols.length()));
            prettyOutput.append("\n");
            prettyOutput.append("Viterbi:  ");
            for(int i = index; i < Math.min(index + 60, symbols.length()); i++) {
                prettyOutput.append(Character.isUpperCase(traceback.charAt(i)) ? "+" : "-");
            }
            prettyOutput.append("\n");
            index += 60;
        }

        return prettyOutput.toString();
    }

}
