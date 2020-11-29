package structureToBeCompleted;

import java.io.FileNotFoundException;

public class Application {

	public static void main(String[] args) throws FileNotFoundException {
		KnowledgeBase fcb = new KnowledgeBase("reunion.txt");
		fcb.forwardChainingBasic();
		System.out.println(fcb.toString());
		KnowledgeBase fco = new KnowledgeBase("reunion.txt");
		fco.forwardChainingOpt();
		System.out.println(fco.toString());
	}

}
