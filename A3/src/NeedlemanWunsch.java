import java.util.ArrayList;

/**
 * Assignment 03
 * Authors:YOUR NAMES HERE
 * <p>
 * Template class for adapted Needleman-Wunsch alignment. Do not forget to comment your code ;)
 * You may want to define an appropriate data structure to store aligned sequences and their score.
 */
public class NeedlemanWunsch {

    private final int matchScore;

    private final int mismatchScore;

    private final int gapPenalty;

    private final char[] sequence1;

    private final char[] sequence2;

    // Define appropriate data structures to store DP/traceback matrix information.

    public NeedlemanWunsch(int matchScore, int mismatchScore, int gapPenalty, String sequence1, String sequence2) {
        this.matchScore = matchScore;
        this.mismatchScore = mismatchScore;
        this.gapPenalty = gapPenalty;
        this.sequence1 = sequence1.toCharArray();
        this.sequence2 = sequence2.toCharArray();
    }

    public void alignSequences() {
        // Fill Score- and Traceback matrix here.
    }

    public int getAlignmentScore() {
        // Return the optimal global alignment score.
        return Integer.MIN_VALUE;
    }

    public ArrayList<String> getAlignedSequences() {
        // Run traceback to obtain aligned sequences.
        return new ArrayList<>();
    }


}
