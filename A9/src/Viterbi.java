/**
 * Assignment 09
 * Authors: YOUR NAMES HERE
 * <p>
 * Code skeleton for viterbi implementation.
 */
public class Viterbi {

    // The viterbi matrix.
    private double[][] viterbiMatrix;
    // The traceback matrix.
    private int[][] tracebackMatrix;
    private String symbols;
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
        tracebackMatrix = new int[hmm.getNoStates()][symbols.length()+1];
        viterbiMatrix = new double[hmm.getNoStates()][symbols.length()+1];
        this.symbols = symbols;
        initialize(hmm);
        recursion(hmm, isOptimized);
        int terminationIndex = termination(hmm);

        traceback = traceback(hmm, terminationIndex);
        return traceback;
    }

    private void initialize(HMM hmm) {
        viterbiMatrix[0][0] = 1;
        tracebackMatrix[0][0] = -1;
        for (int i = 1; i < hmm.getNoStates(); i++) {
            viterbiMatrix[i][0] = 0;
            tracebackMatrix[i][0] = -1;
        }
    }

    private void recursion(HMM hmm, boolean isOptimized) {
        for (int i = 0; i < symbols.length(); i++) {
            for (int j = 1; j < hmm.getNoStates(); j++) {
                double max = Float.NEGATIVE_INFINITY;
                int maxIndex = 0;
                for (int k = 0; k < hmm.getNoStates(); k++) {
                    double value;
                    if(viterbiMatrix[k][i] == 0 || viterbiMatrix[k][i] == Float.NEGATIVE_INFINITY) continue;

                    if (isOptimized) {
                        value = viterbiMatrix[k][i] + Math.log10(hmm.getTransitionProbability(k, j)) + Math.log10(hmm.getEmissionProbability(j, hmm.getSymbolIndex(symbols.charAt(i))));
                    } else {
                        value = viterbiMatrix[k][i] * hmm.getTransitionProbability(k, j) * hmm.getEmissionProbability(j, hmm.getSymbolIndex(symbols.charAt(i)));
                    }
                    if (value > max) {
                        max = value;
                        maxIndex = k;
                    }
                }
                viterbiMatrix[j][i+1] = max;
                tracebackMatrix[j][i+1] = maxIndex;
            }
        }

    }

    private int termination(HMM hmm) {
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
        StringBuilder result = new StringBuilder();
        int index = terminationIndex;
        result.append(hmm.getStateName(index));
        for (int i = symbols.length(); i > 1; i--) {
            index = tracebackMatrix[index][i];
            result.append(hmm.getStateName(index));
        }
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
