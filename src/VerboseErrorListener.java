import org.antlr.v4.runtime.BaseErrorListener;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.Recognizer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class VerboseErrorListener extends BaseErrorListener {
    private final String fileName;
    private final String phase;
    private final List<SyntaxError> errors = new ArrayList<>();

    public VerboseErrorListener(String fileName, String phase) {
        this.fileName = fileName;
        this.phase = phase;
    }

    @Override
    public void syntaxError(Recognizer<?, ?> recognizer, Object offendingSymbol,
                            int line, int charPositionInLine, String msg, RecognitionException e) {
        SyntaxError error = new SyntaxError(fileName, line, charPositionInLine, msg, phase);
        errors.add(error);
        System.err.println(error);
    }

    public boolean hasErrors() {
        return !errors.isEmpty();
    }

    public List<SyntaxError> getErrors() {
        return Collections.unmodifiableList(errors);
    }

    public void report(int line, int column, String message) {
        SyntaxError error = new SyntaxError(fileName, line, column, message, phase);
        errors.add(error);
        System.err.println(error);
    }

    public static final class SyntaxError {
        public final String file;
        public final int line;
        public final int column;
        public final String message;
        public final String phase;

        public SyntaxError(String file, int line, int column, String message, String phase) {
            this.file = file;
            this.line = line;
            this.column = column;
            this.message = message;
            this.phase = phase;
        }

        @Override
        public String toString() {
            return "[Syntax Error] file=" + file + ", line=" + line + ", column=" + column
                    + ", phase=" + phase + ", message=" + message;
        }
    }
}
