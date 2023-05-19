import java.util.ArrayList;

/**
 * Assignment 04
 * Authors:YOUR NAMES HERE
 * <p>
 * Do not forget to add a class documentation.
 */
public class ProgressiveAlignment {

    /**
     * {@link ScoringMatrix} object used for scoring for global pairwise sequence alignment.
     */
    private ScoringMatrix scoringMatrix;

    /**
     * Positive {@link Integer} used for scoring of gaps for global pairwise sequence alignment.
     */
    private int gapPenalty;

    /**
     * Constructor object for {@link ProgressiveAlignment} class.
     *
     * @param scoringMatrix {@link ScoringMatrix} to use for internal alignment steps.
     * @param gapPenalty    {@link Integer} to use for internal alignment steps.
     */
    public ProgressiveAlignment(ScoringMatrix scoringMatrix, int gapPenalty) {
        this.scoringMatrix = scoringMatrix;
        this.gapPenalty = gapPenalty;
    }

    /**
     * Implement progressive alignment here.
     *
     * @param sequencesToAlign {@link ArrayList} of {@link String} objects representing sequences to align.
     * @param guideStructure   {@link Object} used to chose sequence order for alignment.
     * @return {@link ArrayList} of {@link String} objects representing a MSA.
     */
    public static ArrayList<String> alignSequences(ArrayList<String> sequencesToAlign, Object guideStructure) {
        /*
        Implement the pseudo code from Chapter 5 (p.68) here. You will have to implement a data structure that is used to select the order in
        which sequences are to be aligned (for this change the Type of the guideStructure parameter from Object to your implementation.
         */
    }

}
