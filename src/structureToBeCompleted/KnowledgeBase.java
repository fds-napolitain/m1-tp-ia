package structureToBeCompleted;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

public class KnowledgeBase {

	private FactBase bf; // base de faits initiale
	private RuleBase br; // base de règles
	private FactBase bfSat; // base de faits saturée - vide initialement

	public KnowledgeBase() {
		bf = new FactBase();
		br = new RuleBase();
		bfSat = new FactBase();

	}

	public KnowledgeBase(String fic) {
		this(); // initialisation des bases à vide
		BufferedReader lectureFichier = null;
		try {
			lectureFichier = new BufferedReader(new FileReader(new java.io.File(".").getCanonicalPath() + "/src/structureToBeCompleted/" + fic));
		} catch (FileNotFoundException e) {
			System.err.println("Fichier base de connaissances absent: " + fic);
			e.printStackTrace();
			return;
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			String s = lectureFichier.readLine();
			if (s != null) { // si non vide
				bf = new FactBase(s); // 1ere ligne : factbase
				s = lectureFichier.readLine();
				while (s != null && s.length() != 0) { // arret si fin de fichier ou ligne vide
					br.addRule(new Rule(s));
					s = lectureFichier.readLine();
				}
			}
			lectureFichier.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public FactBase getBf() {
		return bf;
	}

	public FactBase getBfSat() {
		return bfSat;
	}

	public RuleBase getBr() {
		return br;
	}

	/**
	 * Retourne une description de la base de connaissances
	 * 
	 * @return description de la base de connaissances
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "**********\nBase de connaissances: \n" + bf + br + bfSat + "\n**********";
	}


	public void forwardChainingBasic() {
		// algo basique de forward chaining

		bfSat = new FactBase(); // ré-initialisation de bfSat
		bfSat.addAtoms(bf.getAtoms()); // avec les atomes de bf
		boolean fin = false;
		boolean[] Appliquee = new boolean[br.size()];
		for (int i = 0; i < br.size(); i++) {
			Appliquee[i] = false;
		}
		while (!fin) {
			FactBase newFacts = new FactBase();
			for (int i = 0; i < br.size(); i++) {
				if (!Appliquee[i]) {
					Rule r = br.getRule(i);
					// test d'applicabilite de la regle i
					boolean applicable = true;
					List<Atom> hp = r.getHypothesis();
					for (int j = 0; applicable && j < hp.size(); j++)
						if (!bfSat.contains(r.getAtomHyp(j)))
							applicable = false;
					if (applicable) {
						Appliquee[i] = true;
						Atom c = r.getConclusion();
						if (!bfSat.contains(c) && !newFacts.contains(c))
							newFacts.addAtomWithoutCheck(c);
					}
				}
			}
			if (newFacts.isEmpty())
				fin = true;
			else
				bfSat.addAtoms(newFacts.getAtoms());
		}
	}

	public void forwardChainingOpt() {
		bfSat = bf.clone();
		FactBase aTraite = bf.clone();
		int[] compteur = new int[br.size()];
		for (int i = 0; i < br.size(); i++) {
			compteur[i] = br.getRule(i).getHypothesis().size();
		}
		while (!aTraite.isEmpty()) {
			Atom F = aTraite.remove(0);
			for (int i = 0; i < br.size(); i++) {
				Rule R = br.getRule(i);
				if (R.getHypothesis().contains(F)) {
					compteur[i]--;
					if (compteur[i] == 0) {
						if (!aTraite.contains(R.getConclusion())) {
							aTraite.addAtomWithoutCheck(R.getConclusion());
							bfSat.addAtomWithoutCheck(R.getConclusion());
						}
					}
				}
			}
		}
	}

	public boolean backwardChaining(Atom Q) {
		if (bf.contains(Q)) {
			return true;
		}
		for (int i = 0; i < br.size(); i++) {
			Rule R = br.getRule(i);

		}
	}

}
