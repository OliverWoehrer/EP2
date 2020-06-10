import java.io.IOException;

public class FileFormatException extends Throwable {

    /**
     * Throws an exception error without an error message
     */
    public FileFormatException(){
        super("Invalid data format!");
    }

    /**
     * Throws an error exception error with the given error message
     * @param filePath Given path of the invalid file
     * @param invalidLine Line where the error was found
     */
    public FileFormatException(String filePath, String invalidLine, int lineNumber) {
        super("invalid data in \""+filePath+"\" at line "+lineNumber+" -> "+invalidLine);
    }

}
