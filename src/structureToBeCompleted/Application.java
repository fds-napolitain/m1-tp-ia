package structureToBeCompleted;

import java.io.FileNotFoundException;
import java.util.*;

public class Application {

	public static void main(String[] args) throws FileNotFoundException {
		// étape 2
		KnowledgeBase reunion = new KnowledgeBase("reunion.txt");
		reunion.forwardChainingBasic();
		System.out.println(reunion.toString());

		// étape 3
		reunion.forwardChainingOpt();
		System.out.println(reunion.toString2());

		// étape 4
		KnowledgeBase bc = new KnowledgeBase();
		bc.getBf().addAtomWithoutCheck(new Atom("A"));
		bc.getBf().addAtomWithoutCheck(new Atom("B"));
		bc.getBr().addRule(new Rule("P;Q"));
		bc.getBr().addRule(new Rule("L;M;P"));
		bc.getBr().addRule(new Rule("B;L;M"));
		bc.getBr().addRule(new Rule("A;P;L"));
		bc.getBr().addRule(new Rule("A;B;L"));
		Atom Q = new Atom("Q");
		List<Atom> Lb = new ArrayList<>();
		System.out.println(bc.getBf().toString());
		System.out.println(bc.getBr().toString());
		System.out.println(bc.backwardChaining(Q, Lb, 0));

		// étape 5
		System.out.println(bc.getBf().toString());
		System.out.println(bc.getBr().toString());
		System.out.println(bc.backwardChainingOpt(Q, Lb, 0));

		// étape 6
		KnowledgeBase app = new KnowledgeBase("reunion.txt");
		System.out.println(app.getBf().toString());
		System.out.println(app.getBr().toString());
		app.forwardChainingOpt();
		System.out.println("Base de Faits saturés (" + app.HashBF.size() + "):");
		for (Atom atom : app.HashBF) {
			System.out.printf(atom.toString() + " ; ");
		}
		System.out.println();
		Atom X;
		List<Atom> L;
		Scanner scanner = new Scanner(System.in);
		while (true) {
			X = new Atom(scanner.nextLine());
			L = new ArrayList<>();
			System.out.println(app.HashBF.contains(X));
			System.out.println(app.backwardChainingOpt(X, L, 0));
		}
	}

}
