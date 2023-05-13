import java.util.ArrayList;
import java.util.List;

/**
 * Assignment 03
 * Authors:Christopher Kolberg, Maximilian Wilhelm
 * <p>
 * Template class for combining sequence profiles. The idea is that profile1 and profile2 both store a list of already aligned sequences.
 * The static method combineAlignedSequences should pick one sequence and align them, insert new gaps into the resp. profiles and returns
 * all new sequences in a single profile. Feel free to implement other datastructures than ArrayLists to store aligned sequences.
 */
public class CombineProfiles {
    private CombineProfiles() {
    }

    // Changed method signature! Since this method is supposed to align as stated above, the method need the parameters matchScore, mismatchScore and gapPenalty.
    public static List<String> combineAlignedSequences(List<String> profile1, List<String> profile2, int matchScore, int mismatchScore, int gapPenalty) {
        String sequence1 = profile1.get(0);
        String sequence2 = profile2.get(0);
        NeedlemanWunsch nw1 = new NeedlemanWunsch(matchScore, mismatchScore,gapPenalty, sequence1, sequence2);
        nw1.alignSequences();
        System.out.println("Aligned the first sequence from both profiles");
        System.out.println("Alignment score: " + nw1.getAlignmentScore());
        System.out.println("Alignment:");
        for(String s : nw1.getAlignedSequences()){
            System.out.println(s);
        }
        System.out.println("-------------------------");


        String alignedSequence1 = nw1.getAlignedSequences().get(0);
        String alignedSequence2 = nw1.getAlignedSequences().get(1);
        adjustProfile(profile1, alignedSequence1);
        adjustProfile(profile2, alignedSequence2);


        List<String> newProfile = new ArrayList<>();
        newProfile.addAll(profile1);
        newProfile.addAll(profile2);
        return newProfile;
    }

    private static void adjustProfile(List<String> profile, String alignedSequence) {
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
