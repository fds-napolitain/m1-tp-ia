package structureToBeCompleted;

import java.nio.file.AtomicMoveNotSupportedException;
import java.util.ArrayList;
import java.util.List;

class DemoFC1 {

	public static void main(String[] args) {
		KnowledgeBase ex1 = new KnowledgeBase("tp2ex1.txt");
		KnowledgeBase ex2 = new KnowledgeBase("tp2ex2.txt");
		ex1.forwardChainingBasic();
		System.out.println(ex1.toString());
		ex2.forwardChainingBasic();
		System.out.println(ex2.toString());
	}

}

class DemoFC2 {

	public static void main(String[] args) {
		KnowledgeBase ex1 = new KnowledgeBase("tp2ex1.txt");
		KnowledgeBase ex2 = new KnowledgeBase("tp2ex2.txt");
		ex1.forwardChainingOpt();
		System.out.println(ex1.toString2());
		ex2.forwardChainingOpt();
		System.out.println(ex2.toString2());
	}

}

class DemoBC {

	public static void main(String[] args) {
		KnowledgeBase ex2 = new KnowledgeBase("tp2ex2.txt");
		List<Atom> atoms = new ArrayList<>();
		Atom Q = new Atom("a12");
		System.out.println(ex2.backwardChainingOpt(Q, atoms, 0));
		Q = new Atom("a2");
		System.out.println(ex2.backwardChainingOpt(Q, atoms, 0));
		Q = new Atom("a4");
		System.out.println(ex2.backwardChainingOpt(Q, atoms, 0));
		Q = new Atom("a5");
		System.out.println(ex2.backwardChainingOpt(Q, atoms, 0));
	}

}

class DemoSP {

	public static void main(String[] args) {
		KnowledgeBase sempos1 = new KnowledgeBase("sempos1.txt");
		KnowledgeBase sempos2 = new KnowledgeBase("sempos2.txt");
		sempos1.forwardChainingOpt();
		System.out.println(sempos1.toString2());
		sempos2.forwardChainingOpt();
		System.out.println(sempos2.toString2());
	}

}