/**
 * Created by derek on 11/19/17.
 */
import java.io.*;
import java.util.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.time.Instant;
import java.time.Duration;
import com.google.common.collect.HashBiMap;
import com.google.common.primitives.Ints;

public class Dispatch {
    public static void main(String[] args) {
        File folder = new File("./inputs");
        File[] listOfFiles = folder.listFiles(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                return !name.equals(".DS_Store");
            }
        });

        File outputDirectory = new File("./outputs");
        if (!outputDirectory.exists()) {
            outputDirectory.mkdir();
        }

        for (int i = 0; i < listOfFiles.length; i++) {
            System.out.println("Started " + listOfFiles[i].getName());
            Instant start = Instant.now();
            Problem p = Parser.parse("./inputs/" + listOfFiles[i].getName());
            //ChocoSolve.solve(p);
            AnnealingSolver tmp = new AnnealingSolver(p, 0.9999);
            tmp.solve();
            Instant end = Instant.now();
            System.out.println("--Finished " + listOfFiles[i].getName() + " in " + Duration.between(start, end));
        }
    }
}