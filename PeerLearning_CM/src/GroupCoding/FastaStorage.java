package GroupCoding;

import java.util.List;
import java.util.Map;

public class FastaStorage {
    // Stores multiple Sequences
    private List<FastaSequence> fastaSequenceList;
    // Optional: Stores every Sequence and the Section with count
    private Map<FastaSequence, Map<String, Integer>> fastaSequenceSectionCount;

    // Creates new GroupCoding.FastaStorage Object
    public FastaStorage() {
        // TODO (DataStructures, Advanced): Initialize Attributes with the corresponding Objects
    }

    // Stores FastaSequences in List
    public void storeInFastaList(FastaSequence sequence) {
        // TODO (Datastructures): Add Sequence to List
    }

    // Getter-Methods
    public List<FastaSequence> getFastaSequenceList() {
        return this.fastaSequenceList;
    }

    // Stores multiple Maps (GroupCoding.FastaSequence.sectionCount) in a single Map
    public void storeInFastaMap(FastaSequence sequence, Map<String, Integer> sectionCount) {
        // TODO (DataStructures): Map the given Map to the given Sequence
    }

    // Optional: Define a Method to edit the Map within fastaSequenceSectionCount without using insertSequenceSection()
    public void insertSequenceAndSection(FastaSequence sequence, String section) {
        // TODO (Advanced): Count the given Section in the given Sequence using Methods from GroupCoding.FastaSequence and Map them in fastaSequenceSectionCount
    }
}
