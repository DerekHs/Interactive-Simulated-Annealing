import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.stream.IntStream;

/**
 * Created by derek on 11/25/17.
 */
public class Parser {
    public static Problem parse(String fileName) {
        HashSet<String> wizardSet = new HashSet<String>(); // set of all wizards maybe not necessary?
        ArrayList<Constraint> constraints = new ArrayList<Constraint>(); // stores all constraints
        String line = null;
        int numWizards;
        try {
            FileReader reader = new FileReader(fileName);
            BufferedReader bufferedReader = new BufferedReader(reader);
            if ((line = bufferedReader.readLine()) != null) {
                numWizards = Integer.parseInt(line.trim());
            }
            if ((line = bufferedReader.readLine()) != null) {
                //System.out.println("num constraints: " + line);
            }
            while ((line = bufferedReader.readLine()) != null) {
                //System.out.println(line);
                String[] wizardNames = line.split("\\s+");
                constraints.add(new Constraint(wizardNames));
                for (String wizard : wizardNames) {
                    wizardSet.add(wizard);
                }
            }
            bufferedReader.close();
        } catch (FileNotFoundException ex) {
            System.out.println("unable to read file " + fileName);
        } catch (IOException ex) {
            System.out.println("error");
        }
        return new Problem(wizardSet, constraints, fileName);
    }
}
