import java.util.HashMap;
import java.util.Map;

public class FastaSequence {

    // Stores Sequence Header
    private String header;
    // Stores Sequence String
    private String sequence;
    // Stores Section Count of this Object
    private Map<String, Integer> sectionCount;

    // Creates a FastaSequence Object
    public FastaSequence(String header, String sequence){
        // TODO (DataStructures): Initialize Attributes with the corresponding Objects
    }

    // Getter-Methods
    public String getSequence(){
        return this.sequence;
    }

    public String getHeader(){
        return this.header;
    }

    public Map<String, Integer> getSectionCount() {
        return this.sectionCount;
    }

    // Setter-Methods
    public void setHeader(String header) {
        this.header = header;
    }
    public void setSequence(String sequence) {
        this.sequence = sequence;
    }

    // Define Method to insert Sequences Section into HashMap
    public void insertSequenceSection(String section){
        // TODO (DataStructures): Insert Section into Map. If already exists -> +1
    }

    // Define Method: Search for SequenceSections and count them. Store in Map
    public void countSection(String section) throws IllegalArgumentException {

        // TODO (Advanced): What does it do?
        if (!section.matches("[ACGT]+")) {
            throw new IllegalArgumentException();
        }

        int index = 0;
        while (index != -1) {
            index = this.sequence.indexOf(section, index);
            if (index != -1)
            {
                // TODO (DataStructures): Insert Section into Map
                index += section.length();
            }
            else {
                break;
            }
        }
    }
}