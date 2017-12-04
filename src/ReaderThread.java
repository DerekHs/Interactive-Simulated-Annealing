/**
 * Created by derek on 12/3/17.
 */
import java.io.*;

public class ReaderThread extends Thread {
    OutputStream out;

    ReaderThread (OutputStream out) {
        this.out = out;
    }

    @Override
    public void run() {
        InputStreamReader sc = new InputStreamReader(System.in);
        while (true) {
            try {
                int tmp = sc.read();
                out.write(tmp);
                out.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}