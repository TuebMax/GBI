import java.util.ArrayList;
import java.util.List;

/**
 * Assignment 04
 * Authors: Christopher Kolberg, Maximilian Wilhelm
 * <p>
 * Class to align a set of sequences using progressive alignment.
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
     * @param guideStructure   {@link ProfileSet} used to chose sequence order for alignment.
     * @return {@link List} of {@link String} objects representing an MSA.
     */
    public List<String> alignSequences(List<String> sequencesToAlign, ProfileSet guideStructure) {

        // Create a set of profiles which is the parent of the given guide structure.
        ProfileSet guideStructureParent = new ProfileSet();
        guideStructureParent.add(guideStructure);

        // While there are still profiles to align, align them.
        while (!guideStructureParent.isEmpty()) {
            alignProfiles(guideStructureParent.iterator().next(), guideStructureParent);
        }
        // Return the aligned profile which is the first profile in the guide structure.
        return guideStructureParent.getProfile1();
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

    private void alignProfiles(ProfileSet child, ProfileSet parent){
        // If the child is not empty, first align the child.
        if(!child.isEmpty()){
            alignProfiles(child.iterator().next(), child);
            return;
        }
        // If the child is empty and thus contains no ProfileSets, it should two profiles.
        // Use the NeedlemanWunsch algorithm to align the two profiles. Which should be filled in profile1 and profile2!
        List<String> subAlignment1 = child.getProfile1();
        List<String> subAlignment2 = child.getProfile2();
        // Assert that the sub-alignments are not null.
        if(subAlignment1 == null || subAlignment2 == null){
            throw new UnsupportedOperationException("Cannot align profile set");
        }
        // Remove the child from the parent and add the new alignment to the parent.
        parent.remove(child);
        NeedlemanWunsch nw = new NeedlemanWunsch(this.scoringMatrix, this.gapPenalty, subAlignment1.get(0), subAlignment2.get(0));
        nw.alignSequences();
        // Adjust the profiles (add gaps) to the new alignment.
        adjustProfile(subAlignment1, nw.getAlignedSequences().get(0));
        adjustProfile(subAlignment2, nw.getAlignedSequences().get(1));
        List<String> newAlignment = new ArrayList<>();
        newAlignment.addAll(subAlignment1);
        newAlignment.addAll(subAlignment2);
        // Add the new alignment to the parent in the corresponding field for "finished" profiles.
        parent.setProfile(newAlignment);
    }

}
