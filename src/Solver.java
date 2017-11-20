/**
 * Created by derek on 11/19/17.
 */
import java.io.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;

public class Solver {
    public static void main(String[] args) {
        Problem p = new Problem("phase2_inputs/inputs20/input20_0.in");
    }
}

class Problem {
    /* reads in a file, and creates a system to store
    a mapping from wizard to age rank
    also reads in constraints, and turns them into formulations
    */
    String[] ordering;
    Constraints constraints;

    Problem(String fileName) {
        String line = null;
        try {
            FileReader reader = new FileReader(fileName);
            BufferedReader bufferedReader = new BufferedReader(reader);
            if ((line = bufferedReader.readLine()) != null) {
                ordering = new String[Integer.parseInt(line)];
            }
            while((line = bufferedReader.readLine()) != null) {
                System.out.println(line);
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

class Constraints {
    ArrayList<String[]> constraints;
    Constraints(int num_constraints) {
        constraints = new ArrayList<>(num_constraints);
    }
    void addConstraint (String[] wizardNameTrio) {
        constraints.add(wizardNameTrio);
    }

    boolean verifyConsistency (String[] ordering) {
        for (int i = 0; i < constraints.size(); i++) {

        }
    }
}