import java.util.HashMap;
import java.util.Map;

public class FastaSequence {

    // Define header and sequence
    private String header;
    private String sequence;

    // ...
    private Map<String, Integer> sectionCount;

    // Create Fasta Sequence Object
    public FastaSequence(String header, String sequence){
        // Assign Parameters to Attributes
        this.header = header;
        this.sequence = sequence;
        this.sectionCount = new HashMap<>();
    }

    // Define Getter-Methods for Attributes
    public String getSequence(){
        return this.sequence;
    }

    public String getHeader(){
        return this.header;
    }

    public Map<String, Integer> getSectionCount() {
        return this.sectionCount;
    }

    // Define Setter-Methods for Attributes
    public void setHeader(String header) {
        this.header = header;
    }
    public void setSequence(String sequence) {
        this.sequence = sequence;
    }

    // Define Method for counting Sequence Length
    public int countSequenceLength() {
        return this.sequence.length();
    }

    // Define Method to insert Sequences Section into HashMap
    public void insertSequenceSection(String section){

        if (this.sectionCount.containsKey(section)) {
            this.sectionCount.put(section, (this.sectionCount.get(section)+1));
        } else {
            this.sectionCount.put(section, 1);
        }
    }

    // Define Method: Search for SequenceSections and count them. Store in Map
    public void countSection(String section) throws IllegalArgumentException {

        // What does it do?
        if (!section.matches("[ACGT]+")) {
            throw new IllegalArgumentException();
        }

        int index = 0;
        while (index != -1) {
            index = this.sequence.indexOf(section, index);
            if (index != -1)
            {
                insertSequenceSection(section);
                index += section.length();
            }
            else {
                break;
            }
        }
    }
}