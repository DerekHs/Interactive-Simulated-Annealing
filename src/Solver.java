/**
 * Created by derek on 11/19/17.
 */
import java.io.*;
import java.util.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.stream.IntStream;
import com.google.common.collect.HashBiMap;
import com.google.common.primitives.Ints;

public class Solver {
    public static void main(String[] args) {
        Problem p = new Problem("phase2_inputs/inputs20/input20_0.in");
    }
}

class Problem {
    HashSet<String> wizardSet; // set of all wizards maybe not necessary?
    HashMap<String, HashSet<Integer>> domains; // stores the domains of each wizard
    HashBiMap<String, Integer> assignments; // stores current assignments of each wizard
    ArrayList<Constraint> constraints; // stores all constraints
    int numWizards;

    Problem(String fileName) {
        wizardSet = new HashSet<>();
        domains = new HashMap<String, HashSet<Integer>>();
        assignments = HashBiMap.create();
        constraints = new ArrayList<Constraint>();
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
            while ((line = bufferedReader.readLine()) != null) {
                //System.out.println(line);
                String[] wizardNames = line.split("\\s+");
                constraints.add(new Constraint(wizardNames[0], wizardNames[1], wizardNames[2]));
                for (String wizard: wizardNames) {
                    int[] possiblePositions = IntStream.range(0, numWizards).toArray();
                    HashSet<Integer> positionsSet = new HashSet<Integer>(Ints.asList(possiblePositions));
                    domains.putIfAbsent(wizard, positionsSet);
                }
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