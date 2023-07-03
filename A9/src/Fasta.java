/**
 * Simple container class to store fasta format entries.
 * You do not have to change this code.
 *
 * @param header   The header of the fasta entry.
 * @param sequence The sequence of the fasta entry.
 */
@SuppressWarnings("unused")
public record Fasta(String header, String sequence) {

    /**
     * Constructor of {@link Fasta} container instances.
     *
     * @param header   {@link String}; The header to store.
     * @param sequence {@link String}; The sequence to store.
     */
    public Fasta {
    }

    /**
     * Get the stored sequence.
     *
     * @return {@link String}; The stored sequence.
     */
    @Override
    public String sequence() {
        return this.sequence;
    }

    /**
     * Get the stored header.
     *
     * @return {@link String}; The stored header.
     */
    @Override
    public String header() {
        return this.header;
    }
}