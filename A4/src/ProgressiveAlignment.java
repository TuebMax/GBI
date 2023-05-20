import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
    private final ScoringMatrix scoringMatrix;

    /**
     * Positive {@link Integer} used for scoring of gaps for global pairwise sequence alignment.
     */
    private final int gapPenalty;

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
     * Method to align a set of sequences using progressive alignment. (Changed method signature to not be static)
     *
     * @param sequencesToAlign {@link ArrayList} of {@link String} objects representing sequences to align.
     * @param    {@link Object} used to chose sequence order for alignment.
     * @return {@link ArrayList} of {@link String} objects representing an MSA.
     */
    public List<String> alignSequences(List<String> sequencesToAlign) {
        /*
        Implement the pseudocode from Chapter 5 (p.68) here. You will have to implement a data structure that is used to select the order in
        which sequences are to be aligned (for this change the Type of the guideStructure parameter from Object to your implementation.
         */
        Set<List<String>> alignments = new HashSet<>();

        for (String sequence: sequencesToAlign) {
            alignments.add(new ArrayList<>(List.of(sequence)));
        }

        while (alignments.size() > 1) {
            List<String> subAlignment1 = alignments.iterator().next();
            alignments.remove(subAlignment1);
            List<String> subAlignment2 = alignments.iterator().next();
            alignments.remove(subAlignment2);

            NeedlemanWunsch nw = new NeedlemanWunsch(this.scoringMatrix, this.gapPenalty, subAlignment1.get(0), subAlignment2.get(0));
            nw.alignSequences();
            adjustProfile(subAlignment1, nw.getAlignedSequences().get(0));
            adjustProfile(subAlignment2, nw.getAlignedSequences().get(1));
            List<String> newAlignment = new ArrayList<>();
            newAlignment.addAll(subAlignment1);
            newAlignment.addAll(subAlignment2);
            alignments.add(newAlignment);
        }

        return new ArrayList<>(alignments.iterator().next());
    }

    private void adjustProfile(List<String> profile, String alignedSequence) {
        // Insert the new gaps from the aligned sequence and add them into every sequence in the profile.
        for(int i = 0; i < alignedSequence.length(); i++){
            if(alignedSequence.charAt(i) == '-' ){
                if(i >= profile.get(0).length()){
                    profile.replaceAll(s -> s + "-");
                }
                if(profile.get(0).charAt(i) != '-'){
                    for(int j = 0; j < profile.size(); j++){
                        profile.set(j, profile.get(j).substring(0, i) + "-" + profile.get(j).substring(i));
                    }
                }
            }
        }
    }

}
