import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;
/**
 * Created by derek on 11/30/17.
 */
public class AnnealingSolver {
    static void solve(Problem p) {
        HashSet<String> wizardSet = p.wizardSet;
        ArrayList<Constraint> constraints = p.constraints;

        HashMap<String, Integer> orderings = new HashMap<String, Integer>();
        ArrayList<Integer> remaining = new ArrayList<Integer>();
        for (int i =0; i<wizardSet.size(); i++) {
            remaining.add(i);
        }

        Iterator<String> i = wizardSet.iterator();
        while (i.hasNext()) {
            String wizard = i.next();
            int randomNum = ThreadLocalRandom.current().nextInt(0, remaining.size());
            orderings.put(wizard, remaining.get(randomNum));
            remaining.remove(randomNum);
        }
        System.out.println(getScore(orderings, constraints));

    }
    static int getScore(HashMap<String, Integer> assignment, ArrayList<Constraint> constraints) {
        Iterator<Constraint> i = constraints.iterator();
        int retVal = 0;
        while (i.hasNext()) {
            Constraint c = i.next();
            String first = c.wizards[0];
            String second = c.wizards[1];
            String third = c.wizards[2];
            if (assignment.get(third) > assignment.get(first) && assignment.get(third) > assignment.get(second)) {
                retVal++;
            }
            if (assignment.get(third) < assignment.get(first) && assignment.get(third) < assignment.get(second)) {
                retVal++;
            }
            i.remove();
        }
        return retVal;
    }
}
