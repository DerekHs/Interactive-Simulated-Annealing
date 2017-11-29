import java.util.ArrayList;
import java.util.HashSet;


/**
 * Created by derek on 11/25/17.
 */
public class Problem {
    HashSet<String> wizardSet;
    ArrayList<Constraint> constraints;
    String fileName;
    public Problem(HashSet<String> wizardSet, ArrayList<Constraint> constraints, String fileName) {
        this.wizardSet = wizardSet;
        this.constraints = constraints;
        this.fileName = fileName;
    }
}