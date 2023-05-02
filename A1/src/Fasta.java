/**
 * Class Fasta
 * use to create a Fasta object
 * you don't need to change this class
 */
public class Fasta {
    private String header;
    private String sequence;

    public Fasta(String header, String sequence){
        this.header = header;
        this.sequence = sequence;
    }

    public String getSequence(){
        return this.sequence;
    }

    public String getHeader(){
        return this.header;
    }
}