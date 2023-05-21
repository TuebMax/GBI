import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FastaStorage {
    // Define a List to store FastaSequences in
    private List<FastaSequence> fastaSequenceList;
    // Optional (1): Define a Map to store Maps of single FastaSequences
    private Map<FastaSequence, Map<String, Integer>> fastaSequenceSectionCount;

    // ...
    public FastaStorage() {
        this.fastaSequenceList = new ArrayList<>();
        // Optional (1)
        this.fastaSequenceSectionCount = new HashMap<>();
    }

    // Define Method to store FastaSequences
    public void storeInFastaList(FastaSequence sequence) {
        this.fastaSequenceList.add(sequence);
    }

    // Getter-Methods
    public List<FastaSequence> getFastaSequenceList() {
        return this.fastaSequenceList;
    }

    // Optional (1): Define Method to store multiple Maps (FastaSequence.sectionCount) in a single Map
    public void storeInFastaMap(FastaSequence sequence, Map<String, Integer> sectionCount) {

        this.fastaSequenceSectionCount.put(sequence, sectionCount);
    }

    // Optional (2): Define a Method to edit the Map within fastaSequenceSectionCount without using insertSequenceSection()
    public void insertSequenceAndSection(FastaSequence sequence, String section) {

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
