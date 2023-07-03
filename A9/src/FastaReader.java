import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Simple class to read fasta format files.
 * You do not have to change this code.
 */
@SuppressWarnings("unused")
public class FastaReader {

    /**
     * Read a .fasta file and return its entries as a {@link ArrayList} of {@link Fasta} records.
     *
     * @param file The {@link File} to parse data from.
     * @return {@link ArrayList} of {@link Fasta} records.
     * @throws IOException If the file can not be accessed.
     */
    public static ArrayList<Fasta> readFasta(File file) throws IOException {
        ArrayList<Fasta> entries = new ArrayList<>();
        StringBuilder currentHeader = new StringBuilder();
        StringBuilder currentSequence = new StringBuilder();
        BufferedReader br = new BufferedReader(new FileReader(file));
        for (String line = br.readLine(); line != null; line = br.readLine()) {
            line = line.trim().replace("\r\n", "").replace("\n", "");
            if (line.startsWith(">")) {
                addEntry(currentHeader, currentSequence, entries);
                currentHeader.append(line);
            } else {
                currentSequence.append(line);
            }
        }
        addEntry(currentHeader, currentSequence, entries);
        br.close();
        return entries;
    }

    private static void addEntry(StringBuilder headerBuilder, StringBuilder sequenceBuilder, ArrayList<Fasta> container) {
        if (headerBuilder.length() > 0 && sequenceBuilder.length() > 0) {
            container.add(new Fasta(headerBuilder.toString(), sequenceBuilder.toString()));
            headerBuilder.setLength(0);
            sequenceBuilder.setLength(0);
        }

    }
}
