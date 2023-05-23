package ErrorSearch;

/**
 * There are 3 "major" errors and 3 "minor" wrong coding conventions (don't affect functionality)
 * to be found in the Sequence and Main classes.
 */


public class Main {
    public static void main(String[] args) {
        Sequence sequence1 = new Sequence("sequence1.tsv");
        System.out.println(sequence1);
        // get the char at position 4 and print it
        System.out.println("Char at position " + 3+1 + " of sequence 1: " + sequence1.getChar(3));
    }
}
