package structureToBeCompleted;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class Application {

	public static void main(String[] args) throws FileNotFoundException {
		KnowledgeBase fcb = new KnowledgeBase("reunion.txt");
		fcb.forwardChainingBasic();
		System.out.println(fcb.toString());

		KnowledgeBase fco = new KnowledgeBase("reunion.txt");
		fco.forwardChainingOpt();
		System.out.println(fco.toString());

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
	}

}
