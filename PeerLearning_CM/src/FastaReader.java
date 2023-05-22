import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class FastaReader {
    BufferedReader bufferedReader;

    // Creates new FastaReader Object
    public FastaReader() {
    }

    // Reads file from file path
    public void setFilePath(String filePath) throws FileNotFoundException {
        this.bufferedReader = new BufferedReader(new FileReader(filePath));
    }

    // Return next Line of file
    public String getNextLine() throws NullPointerException, IOException {
        return bufferedReader.readLine();
    }
}
