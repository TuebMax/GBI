import java.io.FileNotFoundException;
import java.io.IOException;

public class ExceptionHandler {
    FastaReader fastaReader;
    public ExceptionHandler(FastaReader fastaReader) {
        this.fastaReader = fastaReader;
    }

    public void setFilePath(String filePath) {
        try {
            fastaReader.setFilePath(filePath);
        } catch (FileNotFoundException e) {
            System.out.println("FilePath is invalid: " + e.getMessage());
            throw new RuntimeException();
        }
    }

    public String getNextLine() {
        try {
            return fastaReader.getNextLine();
        } catch (IOException e) {
            System.out.println("Something went wrong: " + e.getMessage());
            throw new RuntimeException();
        } catch (NullPointerException e) {
            System.out.println("There is no line to read: " + e.getMessage());
            throw new RuntimeException();
        }
    }
}
