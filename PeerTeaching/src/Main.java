import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Map;

public class Main {
    public static void main(String[] args) {

        /**
         * There should not be a Stack Trace in the output console.
         * Catch every Exception and make it clear which Exception it is and why it got thrown.
         */


        // Set the file path
        FastaReader fastaReader = new FastaReader();
        try {
            fastaReader.setFilePath("fasta.fasta");
        } catch (FileNotFoundException e) {
            System.out.println("FilePath is invalid!: " + e.getMessage());
        }

        // Read the file
        // Create Sequences
        // And store them in FastaStorage.fastaSequenceList
        // Catch Exceptions
        FastaStorage fastaStorage = new FastaStorage();
        try {
            String line;
            FastaSequence fastaSequence = new FastaSequence("", "");
            StringBuilder stringBuilder = new StringBuilder();
            while ((line = fastaReader.getNextLine()) != null) {
                if (line.startsWith(">")) {
                    if (!stringBuilder.isEmpty()) {
                        fastaSequence.setSequence(stringBuilder.toString());
                        fastaStorage.storeInFastaList(fastaSequence);
                        stringBuilder = new StringBuilder();
                    }
                    fastaSequence = new FastaSequence(line.trim(), "");
                } else {
                    stringBuilder.append(line.trim());
                }
            }


            fastaSequence.setSequence(stringBuilder.toString());
            fastaStorage.storeInFastaList(fastaSequence);

        } catch (IOException e) {
            System.out.println("Something went wrong: " + e.getMessage());
        } catch (NullPointerException e) {
            System.out.println("There is nothing more to read: " + e.getMessage());
        }


        // For every FastaSequence in the fastaSequenceList count following SequenceSections:
        // "GTG", "CC", "AGATA"
        // Catch Exceptions
        try {

            for (FastaSequence sequence: fastaStorage.getFastaSequenceList()) {
                sequence.countSection("GTG");
                //sequence.countSection("GCT");
                sequence.countSection("CC");
                sequence.countSection("AGATA");
                System.out.println(sequence.getSectionCount());
            }

        } catch (IllegalArgumentException e) {
            System.out.println("The SequenceSection is invalid!: " + e.getMessage());
        }

        // Now fill the fastaStorage Map
        for (FastaSequence sequence: fastaStorage.getFastaSequenceList()) {

            for (Map.Entry<String, Integer> entry : sequence.getSectionCount().entrySet()) {
                // fastaStorage.storeInFastaMap(sequence, );
            }
        }

        // Optional: Fancy Outprint

    }
}