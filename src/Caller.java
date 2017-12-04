import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;

/**
 * Created by derek on 12/3/17.
 */
public class Caller {
    public static void main(String[] args) {
        PipedOutputStream source = new PipedOutputStream();
        Thread listener = new ReaderThread(source);
        Thread printer;
        PipedInputStream consume;
        try {
            consume = new PipedInputStream(source);
            printer = new Printer(consume);
            printer.start();
            listener.start();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
