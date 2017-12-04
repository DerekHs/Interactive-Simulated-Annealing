import com.sun.rmi.rmid.ExecPermission;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by derek on 12/3/17.
 */
public class Printer extends Thread {
    InputStream out;
    Printer(InputStream out) {
        this.out = out;
    }

    @Override
    public void run() {
        while (true) {
            try {
                if (out.available() > 0) {
                    int tmp = out.read();
                    System.out.println("got: " + tmp);
                } else {
                    System.out.println("asdf");
                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
