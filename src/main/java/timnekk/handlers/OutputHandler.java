package timnekk.handlers;

import java.io.OutputStream;
import java.io.PrintWriter;

public class OutputHandler implements AutoCloseable {
    private final PrintWriter out;

    public OutputHandler(OutputStream outputStream) {
        this.out = new PrintWriter(outputStream);
    }

    public void printLine(String message) {
        out.println(message);
        out.flush();
    }

    @Override
    public void close() {
        out.close();
    }
}