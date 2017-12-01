import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.HashSet;
import java.util.Iterator;
import java.util.concurrent.ThreadLocalRandom;
import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;

/**
 * Created by derek on 11/30/17.
 */
public class AnnealingSolver {
    static void solve(Problem p, int temperature) {
        HashSet<String> wizardSet = p.wizardSet;
        ArrayList<Constraint> constraints = p.constraints;

        // initializes domains
        ArrayList<Integer> remaining = new ArrayList<Integer>();
        for (int i = 0; i < wizardSet.size(); i++) {
            remaining.add(i);
        }

        // create initial random assignment
        HashBiMap<String, Integer> ordering = HashBiMap.create();
        Iterator<String> i = wizardSet.iterator();
        while (i.hasNext()) {
            String wizard = i.next();
            int randomNum = ThreadLocalRandom.current().nextInt(0, remaining.size());
            ordering.put(wizard, remaining.get(randomNum));
            remaining.remove(randomNum);
        }

        while (temperature > 0) {
            System.out.println(getScore(ordering, constraints));
            int before = getScore(ordering, constraints);
            int change = ThreadLocalRandom.current().nextInt(0, wizardSet.size() - 1);
            String first = ordering.inverse().get(change);
            String second = ordering.inverse().get(change + 1);
            ordering.forcePut(first, change + 1);
            ordering.forcePut(second, change);
            int after = getScore(ordering, constraints);
//            if (after > before) {
//                break;
//            }
//            else {
//
//            }
            temperature--;
        }

    }

    static int getScore(Map<String, Integer> assignment, ArrayList<Constraint> constraints) {
        int retVal = 0;
        for (Constraint c : constraints) {
            String first = c.wizards[0];
            String second = c.wizards[1];
            String third = c.wizards[2];
            if (assignment.get(third) > assignment.get(first) && assignment.get(third) > assignment.get(second)) {
                retVal++;
            }
            if (assignment.get(third) < assignment.get(first) && assignment.get(third) < assignment.get(second)) {
                retVal++;
            }
        }
        return retVal;
    }
}