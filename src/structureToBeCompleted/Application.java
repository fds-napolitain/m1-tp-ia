package structureToBeCompleted;

import java.io.FileNotFoundException;

public class Application {

	public static void main(String[] args) throws FileNotFoundException {
		KnowledgeBase reunion = new KnowledgeBase("reunion.txt");
		reunion.forwardChainingBasic();
		System.out.println(reunion.toString());
	}

}
