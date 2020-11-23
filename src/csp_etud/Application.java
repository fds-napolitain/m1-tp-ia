package csp_etud;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.PrintWriter;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Application {
    
    public static void main(String[] args) throws Exception {
        String name = "couleur";
        String fileName = new java.io.File(".").getCanonicalPath() + "/src/csp_etud/" + name + ".txt";
        Network n = new Network(new BufferedReader(new FileReader(fileName)));
        CSP csp = new CSP(n);
        Assignment solution = csp.searchSolution();
        List<Assignment> solutions = csp.searchAllSolutions();
        System.out.println(solution);
        System.out.println(solutions);
    }
    
}
