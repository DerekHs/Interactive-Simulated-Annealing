/**
 * Created by derek on 11/19/17.
 */
import java.io.*;
import java.util.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;

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
    BiMap<String, Integer> assignments;
    ArrayList<String[]> constraints = new ArrayList<>();
    Constraints c;

    Problem(String fileName) {
        String line = null;
        try {
            FileReader reader = new FileReader(fileName);
            BufferedReader bufferedReader = new BufferedReader(reader);
            if ((line = bufferedReader.readLine()) != null) {
                System.out.println("number of wizards:" + line);
            }
            if((line = bufferedReader.readLine()) != null) {
                System.out.println("num constraints: " + line);
                c = new Constraints(Integer.parseInt(line.split(" ")[0]));
            }
            while ((line = bufferedReader.readLine()) != null) {
                System.out.println(line);
                c.addConstraint(line.split(" "));
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

    String solve() {
        
        return "";
    }
}

class Constraints {
    ArrayList<String[]> constraints;
    Constraints(int num_constraints) {
        constraints = new ArrayList<>(num_constraints);
    }
    void addConstraint(String[] wizardNameTrio) {
        constraints.add(wizardNameTrio);
    }

//    boolean verifyConsistency (String[] ordering) {
//        for (int i = 0; i < constraints.size(); i++) {
//
//        }
//    }
}