import org.chocosolver.solver.Model;

import java.awt.*;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.lang.Math;
import java.util.Random;

/**
 * Created by derek on 11/30/17.
 */
public class AnnealingSolver {
    Problem p;
    HashSet<String> wizardSet;
    ArrayList<Constraint> constraints;
    double alpha;
    public AnnealingSolver(Problem p, double alpha) {
        this.p = p;
        this.wizardSet = p.wizardSet;
        this.constraints = p.constraints;
        this.alpha = alpha;
    }

    public void solve() {
        ArrayList<String> curOrdering;
        ArrayList<String> newOrdering;
        double temperature = 1.0;
        curOrdering = getRandomOrdering();

        Random r = new Random();
        boolean flag = true;
        while (true) {
            int before = getScore(curOrdering, constraints);
            //System.out.println(before);
            if (before == constraints.size()) {
                writeToFile(curOrdering, p);
                return;
            }
            if (before >= 0.95*constraints.size() && flag) {
                System.out.println("viable");
                flag = false;
            }
            newOrdering = getNeighbor(curOrdering);
            int after = getScore(newOrdering, constraints);
            if (acceptanceProbability(before, after, temperature) > r.nextDouble()) {
                curOrdering = newOrdering;
            }
            temperature = temperature*alpha;
        }

    }

    public int getScore(ArrayList<String> assignment, ArrayList<Constraint> constraints) {
        int retVal = 0;
        HashMap<String, Integer> tmp = new HashMap<String, Integer>();
        for (int i = 0; i < assignment.size(); i++) {
            tmp.put(assignment.get(i), i);
        }
        for (Constraint c : constraints) {
            String first = c.wizards[0];
            String second = c.wizards[1];
            String third = c.wizards[2];
            if (tmp.get(third) > tmp.get(first) && tmp.get(third) > tmp.get(second)) {
                retVal++;
            }
            if (tmp.get(third) < tmp.get(first) && tmp.get(third) < tmp.get(second)) {
                retVal++;
            }
        }
        return retVal;
    }

    static void writeToFile (ArrayList<String> ordering, Problem p) {
        try {
            String newpath = p.fileName.replace("in", "out");
            FileWriter fileWriter = new FileWriter(newpath);
            PrintWriter printWriter = new PrintWriter(fileWriter);
            for (String wizard: ordering) {
                printWriter.print(wizard + " ");
            }
            printWriter.close();
            System.out.println("Finished " + p.fileName);
        }
        catch (IOException ex) {
            System.out.println("could not write file out");
        }
    }

    static double acceptanceProbability (int before, int after, double temperature) {
        return java.lang.Math.exp((after - before)/temperature);
    }

    public ArrayList<String> getRandomOrdering() {
        ArrayList<String> assignment = new ArrayList<String>();
        for (String wizard: wizardSet) {
            assignment.add(wizard);
        }
        Collections.shuffle(assignment);
        return assignment;
    }

    public ArrayList<String> getNeighbor(ArrayList<String> prev) {
        ArrayList<String> next = new ArrayList<>();
        for (int i = 0; i < prev.size(); i++) {
            next.add(prev.get(i));
        }
        for (int i = 0; i < 1; i++) {
            int randomNum1 = ThreadLocalRandom.current().nextInt(0, prev.size() - 1);
            int randomNum2 = ThreadLocalRandom.current().nextInt(0, prev.size() - 1);
            String toPut = next.remove(randomNum1);
            next.add(randomNum2, toPut);
        }
        return next;
    }

    public ArrayList<String> capOff(ArrayList<String> prev) {
        ArrayList<String> next = new ArrayList<String>();
        for (int i = 0; i < prev.size(); i++) {
            next.add(prev.get(i));
        }
        HashMap<String, Integer> tmp = new HashMap<String, Integer>();
        for (int i = 0; i < prev.size(); i++) {
            tmp.put(prev.get(i), i);
        }
        for (Constraint c : constraints) {
            String first = c.wizards[0];
            String second = c.wizards[1];
            String third = c.wizards[2];
            if (tmp.get(third) < tmp.get(first) && tmp.get(third) > tmp.get(second)) {
                Collections.swap(next, 0, tmp.get(third));
                return next;
            }
            if (tmp.get(third) > tmp.get(first) && tmp.get(third) < tmp.get(second)) {
                Collections.swap(next, prev.size()-1, tmp.get(third));
                return next;
            }

        }
        return next;
    }
}