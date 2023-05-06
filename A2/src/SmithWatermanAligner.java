/**
 * Assignment 02
 * Authors:YOUR NAMES HERE
 */
/*
CLASS DOCUMENTATION HERE
NOTE: Feel free to change the code skeleton to a static implementation, types of properties, etc., if appropriate to you.
 */
public class SmithWatermanAligner {

    /**
     * Integer value used to score matches during DP recurrence.
     */
    private final int matchScore;

    /**
     * Integer value used to score mismatches during DP recurrence.
     */
    private final int mismatchScore;

    /**
     * Integer value used to penalize gaps during DP recurrence.
     */
    private final int gapPenalty;

    /**
     * First sequence to align.
     */
    private final String s1;

    /**
     * Second sequence to align.
     */
    private final String s2;

    // Find appropriate data structure(s) to store the alignment and traceback matrix information.

    public SmithWatermanAligner(int matchScore, int mismatchScore, int gapPenalty, String s1, String s2) {
        this.matchScore = matchScore;
        this.mismatchScore = mismatchScore;
        this.gapPenalty = gapPenalty;
        this.s1 = s1;
        this.s2 = s2;
    }

    /**
     * METHOD DOCUMENTATION HERE
     *
     * @return
     */
    public Integer alignSequences() {
        int optimalAlignmentScore = Integer.MIN_VALUE;
        // Implement the SW DP recurrence to fill out the alignment matrix.
        return optimalAlignmentScore;
    }

    /**
     * METHOD DOCUMENTATION HERE
     *
     * @return
     */
    public String traceback() {
        // Feel free to implement an own format/class to represent aligned sequences!
        StringBuilder alignmentString = new StringBuilder();
        // Go through the traceback matrix to reconstruct one or all optimal local alignments.
        alignmentString.append("...");
        return alignmentString.toString();
    }


}
