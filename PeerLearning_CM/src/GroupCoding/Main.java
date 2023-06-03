package GroupCoding;

import java.io.FileNotFoundException;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {

        /*
         * TODO:
         *  There should not be a Stack Trace in the output console.
         *  Catch every Exception and make it clear which Exception it is and why it got thrown.
         */


        // Creates a new GroupCoding.FastaStorage Object
        FastaReader fastaReader = new FastaReader();
        // TODO (ExceptionHandling): Read in the "fasta.fasta" file using setFilePath of GroupCoding.FastaReader
        // TODO (ExceptionHandling): Catch Exceptions to GroupCoding.FastaReader
        // TODO ends here
        try {
            fastaReader.setFilePath("fasta.fasta");
        } catch (FileNotFoundException e) {
            System.out.println("FilePath is invalid!: " + e.getMessage());
        }

        // Creates a new GroupCoding.FastaStorage Object
        FastaStorage fastaStorage = new FastaStorage();
        // TODO (ExceptionHandling): Catch Exceptions of GroupCoding.FastaReader
        //  Hint: Look at the GroupCoding.FastaReader Class
        try {
            String line;
            FastaSequence fastaSequence = new FastaSequence("", "");
            StringBuilder stringBuilder = new StringBuilder();
            while ((line = fastaReader.getNextLine()) != null) {
                if (line.startsWith(">")) {
                    if (!stringBuilder.isEmpty()) {
                        fastaSequence.setSequence(stringBuilder.toString());
                        // TODO (ExceptionHandling): Store the GroupCoding.FastaSequence in List.
                        //  Hint: Look at the GroupCoding.FastaStorage Methods.
                        fastaStorage.storeInFastaList(fastaSequence);
                        stringBuilder = new StringBuilder();
                    }
                    fastaSequence = new FastaSequence(line.trim(), "");
                } else {
                    stringBuilder.append(line.trim());
                }
            }


            fastaSequence.setSequence(stringBuilder.toString());

            // TODO (ExceptionHandling): Store the GroupCoding.FastaSequence in List.
            //  Hint: Look at the GroupCoding.FastaStorage Methods.
            // TODO ends here

            fastaStorage.storeInFastaList(fastaSequence);
        } catch (IOException e) {
            System.out.println("Something went wrong: " + e.getMessage());
        } catch (NullPointerException e) {
            System.out.println("There is nothing more to read: " + e.getMessage());
        }

        // TODO (ExceptionHandling): Iterate over SequenceList and count Sections using countSection Method in GroupCoding.FastaSequence
        //  e.g.: "GTG", "CC", "AGATA"
        // TODO (ExceptionHandling): Catch Exceptions
        //  Hint: Look at the countSection Method in GroupCoding.FastaSequence
        // TODO ends here

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

        /*
            // TODO (Advanced): Using storeInFastaMap and/or insertSequenceAndSection count Sections, sore them in fastaSequenceSectionCount and print the whole Map
            // TODO (Advanced, ExceptionHandling): Catch Exceptions
            for (FastaSequence sequence: fastaStorage.getFastaSequenceList()) {
                for (Map.Entry<String, Integer> entry : sequence.getSectionCount().entrySet()) {
                    // TODO (Advanced): These for loops are only an example. You can delete them or use them.
                }
            }

         */

        // TODO (OPTIONAL): Print the Map in a readable/clear/graphical form. e.g.: Table, etc.

    }
}