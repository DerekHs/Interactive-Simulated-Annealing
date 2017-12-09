import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.lang.Math;
import java.util.Random;
import java.nio.*;
import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.Selector;
import java.nio.channels.SelectionKey;
import java.nio.channels.SelectableChannel;
import java.nio.channels.ReadableByteChannel;
import java.util.Iterator;

/**
 * Created by derek on 11/30/17.
 */


public class AnnealingSolver {
    Problem p;
    HashSet<String> wizardSet;
    ArrayList<Constraint> constraints;
    double alpha;
    ArrayList<String> curOrdering;
    ArrayList<String> newOrdering;
    double temperature = 1.0;

    public AnnealingSolver(Problem p, double alpha) {
        this.p = p;
        this.wizardSet = p.wizardSet;
        this.constraints = p.constraints;
        this.alpha = alpha;
    }

    public static void solve(Problem p, double alpha) {
        AnnealingSolver temp = new AnnealingSolver(p, alpha);
        try {
            temp.solve();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean solve() throws IOException{
        // IO stuff
        PipedOutputStream source = new PipedOutputStream();
        Thread listener = new ReaderThread(source);
        listener.start();


        PipedInputStream consume = new PipedInputStream(source);
        curOrdering = getRandomOrdering();
        Random r = new Random();
        int counter = 0;
        while (true) {
            if (counter % 500 == 0) {
                if (consume.available() > 0) {
                    int command = consume.read();
                    switch (command) {
                        case 'q':
                            temperature += 0.3;
                            System.out.println("new temperature: " + temperature);
                            break;
                        case 'a':
                            temperature *= 0.5;
                            System.out.println("new temperature: " + temperature);
                            break;
                        case 't':
                            System.out.println("current temperature: " + temperature);
                            break;
                        case 'r':
                            curOrdering = getRandomOrdering();
                            break;
                        case 's':
                            curOrdering = reRoll(curOrdering);
                            break;
                        case 'w':
                            curOrdering = swap(curOrdering);
                            break;
                        case 'p':
                            writePartial(curOrdering, p);
                            return false;
                        default:
                            break;
                    }
                    continue;
                }
            }
            int before = getScore(curOrdering, constraints);
            if (before == constraints.size()) {
                writeToFile(curOrdering, p);
                return true;
            }
            double goodness = before/(double)constraints.size();
            if (counter%501 == 0) {
                System.out.println(before);
            }
            if (goodness > 0.90 && counter % 1000000 == 0) {
                temperature += 0.2;
            }
            else if (goodness > 0.85 && counter % 10000 == 0) {
                newOrdering = capOff(curOrdering);
                temperature = 0.3;
            }
            else if (goodness < 0.5 && counter % 201 == 0) {
                System.out.println("reroll");
                newOrdering = getRandomOrdering();
            }
            else {
                newOrdering = getNeighbor(curOrdering);
            }
            int after = getScore(newOrdering, constraints);
            if (acceptanceProbability(before, after, temperature) > r.nextDouble()) {
                curOrdering = newOrdering;
            }
            temperature = temperature*alpha;
            counter ++;
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

    static void writePartial (ArrayList<String> ordering, Problem p) {
        try {
            String newpath = p.fileName.replace(".in", ".out");
            newpath = newpath.replace("./inputs", "./outputs");
            FileWriter fileWriter = new FileWriter(newpath);
            PrintWriter printWriter = new PrintWriter(fileWriter);
            for (String wizard: ordering) {
                printWriter.print(wizard + " ");
            }
            printWriter.close();
            System.out.println("Partial: " + p.fileName);
        }
        catch (IOException ex) {
            System.out.println("could not write file out");
        }
    }

    static double acceptanceProbability (int before, int after, double temperature) {
        return Math.exp((after - before)/temperature);
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
    public ArrayList<String> reRoll(ArrayList<String> prev) {
        ArrayList<String> next = new ArrayList<String>();
        for (int i = 0; i < prev.size(); i++) {
            next.add(prev.get(i));
        }
        ArrayList<String> getOffenders = getOffenders(prev);
        HashMap<String, Integer> tmp = new HashMap<String, Integer>();
        for (int i = 0; i < getOffenders.size()/8; i++) {
            String removed = getOffenders.get(i);
            next.remove(removed);
            int randomNum = ThreadLocalRandom.current().nextInt(0, prev.size() - 1);
            next.add(randomNum, removed);
        }
        return next;
    }

    public ArrayList<String> getOffenders (ArrayList<String> ordering) {
        ArrayList<String> offenders = new ArrayList<>();
        HashMap<String, Integer> tmp = new HashMap<String, Integer>();
        for (int i = 0; i < ordering.size(); i++) {
            tmp.put(ordering.get(i), i);
        }
        for (Constraint c : constraints) {
            String first = c.wizards[0];
            String second = c.wizards[1];
            String third = c.wizards[2];
            if (tmp.get(third) > tmp.get(first) && tmp.get(third) > tmp.get(second)) {
                continue;
            }
            if (tmp.get(third) < tmp.get(first) && tmp.get(third) < tmp.get(second)) {
                continue;
            }
            offenders.add(first);
            offenders.add(second);
            offenders.add(third);
        }
        return offenders;
    }

    public ArrayList<String> swap(ArrayList<String> prev) {
        ArrayList<String> next = new ArrayList<>();
        for (int i = 0; i < prev.size(); i++) {
            next.add(prev.get(i));
        }
        for (int i = 0; i < 13; i++) {
            int randomNum1 = ThreadLocalRandom.current().nextInt(0, prev.size() - 1);
            int randomNum2 = ThreadLocalRandom.current().nextInt(0, prev.size() - 1);
            Collections.swap(next, randomNum1, randomNum2);
        }
        return next;
    }
}