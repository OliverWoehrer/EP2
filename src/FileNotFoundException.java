import java.io.IOException;

public class FileNotFoundException extends IOException {

    /**
     * Throws an exception error without any error message
     */
    public FileNotFoundException() {
        super();
    }

    /**
     * Throws an exception error with the given error message
     * @param invalidFile Name of the invalid file
     */
    public FileNotFoundException(String invalidFile) {
        super("file "+invalidFile+" not found.");
    }
}
