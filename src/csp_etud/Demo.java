package csp_etud;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class Demo {

	public static void main(String[] args) throws Exception {
		ArrayList<String> reseaux = new ArrayList<>();

		reseaux.add("10reines_exp.txt");

		for (String reseau : reseaux) {
			String fileName = new java.io.File(".").getCanonicalPath() + "/src/csp_etud/" + reseau;
			Network n = new Network(new BufferedReader(new FileReader(fileName)));
			CSP csp = new CSP(n);
			System.out.println("\nNom du réseau: " + reseau);
			Assignment solution = csp.searchSolution();
			System.out.println("searchSol:");
			System.out.println("\tPremière solution trouvée: " + solution);
			System.out.println("\tNombre de noeuds explorés: " + csp.cptr);
			List<Assignment> solutions = csp.searchAllSolutions();
			System.out.println("searchAllSol:");
			System.out.println("\tNombre de solutions trouvées: " + solutions.size());
			System.out.println("\tNombre de noeuds explorés: " + csp.cptr);
		}
	}

}
