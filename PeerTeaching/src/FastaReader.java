import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class FastaReader {
    BufferedReader bufferedReader;
    public FastaReader() {
    }

    // Define Setter-method for filePath
    public void setFilePath(String filePath) throws FileNotFoundException {
        this.bufferedReader = new BufferedReader(new FileReader(filePath));
    }

    // Define Getter-Method for next Line of Fasta File
    public String getNextLine() throws NullPointerException, IOException {
        return bufferedReader.readLine();
    }
}
