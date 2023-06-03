package GroupCoding;

import java.util.ArrayList;
import java.util.HashMap;
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
        this.fastaSequenceList = new ArrayList<>();
        this.fastaSequenceSectionCount = new HashMap<>();
    }

    // Stores FastaSequences in List
    public void storeInFastaList(FastaSequence sequence) {
        // TODO (Datastructures): Add Sequence to List
        this.fastaSequenceList.add(sequence);
    }

    // Getter-Methods
    public List<FastaSequence> getFastaSequenceList() {
        return this.fastaSequenceList;
    }

    // Stores multiple Maps (GroupCoding.FastaSequence.sectionCount) in a single Map
    public void storeInFastaMap(FastaSequence sequence, Map<String, Integer> sectionCount) {
        // TODO (DataStructures): Map the given Map to the given Sequence
        this.fastaSequenceSectionCount.put(sequence, sectionCount);
    }

    // Optional: Define a Method to edit the Map within fastaSequenceSectionCount without using insertSequenceSection()
    public void insertSequenceAndSection(FastaSequence sequence, String section) {
        // TODO (Advanced): Count the given Section in the given Sequence using Methods from GroupCoding.FastaSequence and Map them in fastaSequenceSectionCount
        boolean sequenceContainsKey = this.fastaSequenceSectionCount.containsKey(sequence);
        Map<String, Integer> getMapByKey = this.fastaSequenceSectionCount.get(sequence);
        Integer getCount = getMapByKey.get(section);
        Map<String, Integer> tempMap = new HashMap<>();

        if (sequenceContainsKey) {
            if (getMapByKey.containsKey(section)) {
                tempMap.put(section, (getCount+1));
                this.fastaSequenceSectionCount.put(sequence, tempMap);
            } else {
                tempMap.put(section, 1);
                this.fastaSequenceSectionCount.put(sequence, tempMap);
            }
        } else {
            tempMap.put(section, 1);
            this.fastaSequenceSectionCount.put(sequence, tempMap);
        }
    }
}
