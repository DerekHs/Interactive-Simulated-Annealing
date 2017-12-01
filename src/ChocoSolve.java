/**
 * Created by derek on 11/28/17.
 */

import org.chocosolver.solver.Model;
import org.chocosolver.solver.Solver;
import org.chocosolver.solver.variables.SetVar;
import org.chocosolver.solver.variables.IntVar;
import org.chocosolver.solver.search.loop.move.MoveLNS;
import org.chocosolver.solver.search.loop.lns.neighbors.INeighbor;
import org.chocosolver.solver.search.loop.lns.INeighborFactory;
import org.chocosolver.solver.search.limits.FailCounter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Collections;
import java.util.Comparator;
import java.io.PrintWriter;
import java.io.FileWriter;


public class ChocoSolve {
    static void solve(Problem p) {
        HashMap<String, IntVar> wizardMappings = new HashMap<String, IntVar>();
        Model model = new Model("Choco Solver Hello World");
        HashSet<String> wizards = p.wizardSet;
        for (String wizard : wizards) {
            int[] domains = new int[wizards.size()];
            for (int i = 0; i < wizards.size(); i++) {
                domains[i] = i;
            }
            wizardMappings.put(wizard, model.intVar(wizard, domains));
        }
        ArrayList<Constraint> constraints = p.constraints;
        for (Constraint c: constraints) {
            IntVar first = wizardMappings.get(c.wizards[0]);
            IntVar second = wizardMappings.get(c.wizards[1]);
            IntVar third = wizardMappings.get(c.wizards[2]);
            model.or(model.and(model.arithm(third, ">", first), model.arithm(third, ">", second)),
                    model.and(model.arithm(third, "<", first), model.arithm(third, "<", second)
            )).post();
        }
        model.allDifferent(wizardMappings.values().toArray(new IntVar[(wizardMappings.values().size())])).post();
        Solver s = model.getSolver();
        INeighbor blackBox = INeighborFactory.blackBox(new IntVar[]{model.intVar("dummy", new int[]{wizards.size()})});
        //INeighbor explanationBased = INeighborFactory.explanationBased(new IntVar[]{model.intVar("dummy", new int[]{wizards.size()})});
        //INeighbor propagationGuided = INeighborFactory.propagationGuided(new IntVar[]{model.intVar("dummy", new int[]{wizards.size()})});
        //INeighbor reversedPropagationGuided = INeighborFactory.reversedPropagationGuided(new IntVar[]{model.intVar("dummy", new int[]{wizards.size()})});
        //INeighbor random = INeighborFactory.random(new IntVar[]{model.intVar("dummy", new int[]{wizards.size()})});
        s.setLNS(blackBox, new FailCounter(s, 50));
        if (s.solve()) {
            ArrayList<IntVar> solution = new ArrayList<IntVar>();
            for (IntVar wizard : wizardMappings.values()) {
                solution.add(wizard);
            }
            Collections.sort(solution, new Comparator<IntVar>() {
                public int compare(IntVar one, IntVar other) {
                    return one.getValue() - other.getValue();
                }
            });
            try {
                String newpath = p.fileName.replace("in", "out");
                FileWriter fileWriter = new FileWriter(newpath);
                PrintWriter printWriter = new PrintWriter(fileWriter);
                for (IntVar wizard: solution) {
                    printWriter.print(wizard.getName() + " ");
                }
                printWriter.close();
                System.out.println("Finished " + p.fileName);
            }
            catch (IOException ex) {
                System.out.println("could not write file out");
            }
        }
        System.out.println("could not solve" + p.fileName);
    }
}