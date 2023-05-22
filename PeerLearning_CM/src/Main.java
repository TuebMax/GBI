import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Map;

public class Main {
    public static void main(String[] args) {

        /**
         * There should not be a Stack Trace in the output console.
         * Catch every Exception and make it clear which Exception it is and why it got thrown.
         */


        // Creates a new FastaStorage Object
        FastaReader fastaReader = new FastaReader();
        // TODO (ExceptionHandling): Read in the "fasta.fasta" file using setFilePath of FastaReader
        // TODO (ExceptionHandling): Catch Exceptions of FastaReader
        // TODO ends here

        // Creates a new FastaStorage Object
        FastaStorage fastaStorage = new FastaStorage();
        // TODO (ExceptionHandling): Catch Exceptions of FastaReader
        //  Hint: Look at the FastaReader Class

        String line;
        FastaSequence fastaSequence = new FastaSequence("", "");
        StringBuilder stringBuilder = new StringBuilder();
        while ((line = fastaReader.getNextLine()) != null) {
            if (line.startsWith(">")) {
                if (!stringBuilder.isEmpty()) {
                    fastaSequence.setSequence(stringBuilder.toString());
                    // TODO (ExceptionHandling): Store the FastaSequence in List.
                    //  Hint: Look at the FastaStorage Methods.
                    stringBuilder = new StringBuilder();
                }
                fastaSequence = new FastaSequence(line.trim(), "");
            } else {
                stringBuilder.append(line.trim());
            }
        }


        fastaSequence.setSequence(stringBuilder.toString());

        // TODO (ExceptionHandling): Store the FastaSequence in List.
        //  Hint: Look at the FastaStorage Methods.
        // TODO ends here


        // TODO (ExceptionHandling): Iterate over SequenceList and count Sections using countSection Method in FastaSequence
        //  e.g.: "GTG", "CC", "AGATA"
        // TODO (ExceptionHandling): Catch Exceptions
        //  Hint: Look at the countSection Method in FastaSequence
        // TODO ends here

        for (FastaSequence sequence: fastaStorage.getFastaSequenceList()) {
            System.out.println(sequence.getSectionCount());
        }

        // TODO (Advanced): Using storeInFastaMap and/or insertSequenceAndSection count Sections, sore them in fastaSequenceSectionCount and print the whole Map
        // TODO (Advanced, ExceptionHandling): Catch Exceptions
        for (FastaSequence sequence: fastaStorage.getFastaSequenceList()) {
            for (Map.Entry<String, Integer> entry : sequence.getSectionCount().entrySet()) {
                // TODO (Advanced): These for loops are only an example. You can delete them or use them.
            }
        }

        // TODO (OPTIONAL): Print the Map in a readable/clear/graphical form. e.g.: Table, etc.

    }
}