/**
 * Created by derek on 11/19/17.
 */
import java.io.*;
import java.util.*;
import com.google.common.collect.HashBiMap;


import com.google.common.base.Strings;
import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;

public class Solver {
    public static void main(String[] args) {
        Problem p = new Problem("phase2_inputs/inputs20/input20_0.in");
    }
}

class Problem {
    /* reads in a file, and stores the problem
    */
    ArrayList<Constraint> constraints;
    HashMap<String, boolean[]> domains;
    HashBiMap<String, Integer> assignments;

    Problem(String fileName) {
        int numWizards;
        domains = new HashMap<String, boolean[]>();
        assignments = HashBiMap.create();
        String line = null;
        try {
            FileReader reader = new FileReader(fileName);
            BufferedReader bufferedReader = new BufferedReader(reader);
            if ((line = bufferedReader.readLine()) != null) {
                //System.out.println("number of wizards:" + line);
                numWizards = Integer.parseInt(line.trim());
            }
            if((line = bufferedReader.readLine()) != null) {
                System.out.println("num constraints: " + line);
            }
            constraints = new ArrayList<Constraint>();
            while ((line = bufferedReader.readLine()) != null) {
                //System.out.println(line);
                String[] names = line.split("\\s+");
                constraints.add(new Constraint(names[0], names[1], names[2]));
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

class Constraint {
    String wizard1;
    String wizard2;
    String wizard3;
    Constraint(String wizard1, String wizard2, String wizard3) {
        this.wizard1 = wizard1;
        this.wizard2 = wizard2;
        this.wizard3 = wizard3;
    }
}