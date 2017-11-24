/**
 * Created by derek on 11/22/17.
 */
import net.sf.javailp.Solver;
import net.sf.javailp.SolverFactory;
import net.sf.javailp.SolverFactoryLpSolve;
import net.sf.javailp.Problem;
import net.sf.javailp.Linear;
import net.sf.javailp.OptType;
import net.sf.javailp.*;

public class Test {
    public static void main(String[] args) {
        SolverFactory factory = new SolverFactoryLpSolve(); // use lp_solve
        Problem problem = new Problem();

        Linear linear = new Linear();
        linear.add(143, "x");
        linear.add(60, "y");

        problem.setObjective(linear, OptType.MAX);

        linear = new Linear();
        linear.add(120, "x");
        linear.add(210, "y");

        problem.add(linear, "<=", 15000);

        linear = new Linear();
        linear.add(110, "x");
        linear.add(30, "y");

        problem.add(linear, "<=", 4000);

        linear = new Linear();
        linear.add(1, "x");
        linear.add(1, "y");

        problem.add(linear, "<=", 75);

        problem.setVarType("x", Integer.class);
        problem.setVarType("y", Integer.class);

        Solver solver = factory.get(); // you should use this solver only once for one problem
        Result result = solver.solve(problem);

        System.out.println(result);

/**
 * Extend the problem with x <= 16 and solve it again
 */
        problem.setVarUpperBound("x", 16);

        solver = factory.get();
        result = solver.solve(problem);

        System.out.println(result);
    }
}
