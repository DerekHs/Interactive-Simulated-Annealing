/**
 * Created by derek on 11/19/17.
 */
import java.io.*;

public class Solver {
    public static void main(String[] args) {
        Assignment ass = new Assignment("phase2_inputs/inputs20/input20_0.in");
    }
}

class Assignment {
    /* reads in a file, and creates a system to store
    a mapping from wizard to age rank
    */
    Assignment(String fileName) {
        String line = null;
        try {
            FileReader reader = new FileReader(fileName);
            BufferedReader bufferedReader = new BufferedReader(reader);
            while((line = bufferedReader.readLine()) != null) {
                System.out.println(line);
            }
            bufferedReader.close();
        }
        catch (FileNotFoundException ex) {
            System.out.println("unable to read file " + fileName);
        }
        catch(IOException ex) {
            System.out.println(
                    "Error reading file '"
                            + fileName + "'");
            // Or we could just do this:
            // ex.printStackTrace();
        }
    }
}