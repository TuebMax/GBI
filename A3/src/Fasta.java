/**
 * Class to store fasta format entries.
 * You don't need to change this class
 */
public class Fasta {

    /**
     * The header of the fasta entry to store.
     */
    private final String header;
    /**
     * The sequence of the fasta entry to store.
     */
    private final String sequence;

    /**
     * Constructor of the {@link Fasta} class.
     *
     * @param header   {@link String} to store as header.
     * @param sequence {@link String} to store as sequence.
     */
    public Fasta(String header, String sequence) {
        this.header = header;
        this.sequence = sequence;
    }

    /**
     * Access the sequence stored in this object.
     *
     * @return {@link String} repr. the sequence of this fasta entry.
     */
    public String getSequence() {
        return this.sequence;
    }

    /**
     * Access the header stored in this object.
     *
     * @return {@link String} repr. the header of this fasta entry.
     */
    public String getHeader() {
        return this.header;
    }
}
