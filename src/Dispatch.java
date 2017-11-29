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

public class Dispatch {
    public static void main(String[] args) {
        for (int i = 1; i < 10; i++) {
            Problem p = Parser.parse("inputs/phase2_inputs/inputs50/input50_" + Integer.toString(i)+ ".in");
            ChocoSolve.solve(p);
        }
    }
}