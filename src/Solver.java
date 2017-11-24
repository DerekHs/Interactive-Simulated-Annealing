/**
 * Created by derek on 11/19/17.
 */
import java.io.*;
import java.util.HashSet;
import java.util.HashMap;
import net.sf.javailp.*;

public class Solver {
    public static void main(String[] args) {
        Parser p = new Parser("phase2_inputs/inputs20/input20_0.in");
    }
}

class Parser {
    int numWizards;

    Parser(String fileName) {
        String line = null;
        try {
            FileReader reader = new FileReader(fileName);
            BufferedReader bufferedReader = new BufferedReader(reader);
            if ((line = bufferedReader.readLine()) != null) {
                //System.out.println("number of wizards:" + line);
                numWizards = Integer.parseInt(line.trim());
            }
            if((line = bufferedReader.readLine()) != null) {
                //System.out.println("num constraints: " + line);
            }
            Problem problem = new Problem();
            Linear linear = new Linear();
            while ((line = bufferedReader.readLine()) != null) {
                //System.out.println(line);
                String[] wizardNames = line.split("\\s+");

            }
            bufferedReader.close();
        }
        catch (FileNotFoundException ex) {
            System.out.println("unable to read file " + fileName);
        }
        catch(IOException ex) {
            System.out.println("error");
        }
    }
}