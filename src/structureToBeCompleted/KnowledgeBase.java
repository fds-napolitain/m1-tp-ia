package structureToBeCompleted;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class KnowledgeBase {

	private FactBase bf; // base de faits initiale
	private RuleBase br; // base de règles
	private FactBase bfSat; // base de faits saturée - vide initialement
	public HashSet<Atom> HashBF;
	public HashSet<Atom> prouve;
	public HashSet<Atom> echec;

	public KnowledgeBase() {
		bf = new FactBase();
		br = new RuleBase();
		bfSat = new FactBase();
		HashBF = new HashSet<>();
		prouve = new HashSet<>();
		echec = new HashSet<>();
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

	/**
	 * Retourne une description de la base de connaissances
	 *
	 * @return description de la base de connaissances
	 * @see java.lang.Object#toString()
	 */
	public String toString2() {
		return "**********\nBase de connaissances: \n" + bf + br + HashBF + "\n**********";
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
		LinkedList<Atom> aTraiter = new LinkedList<>();
		HashBF = new HashSet<>();
		HashMap<Rule,Integer> cptr = new HashMap<>();
		HashMap<Atom,List<Rule>> atome2regle = new HashMap<>();

		for (int i = 0; i < br.size(); i++) {
			cptr.put(br.getRule(i), br.getRule(i).getHypothesis().size());
			for (Atom a : br.getRule(i).getHypothesis()) {
				List<Rule> listeRegles = atome2regle.get(a);
				if (listeRegles == null) {
					listeRegles = new ArrayList<>();
					atome2regle.put(a, listeRegles);
				}
				listeRegles.add(br.getRule(i));
			}
		}

		for (Atom F : bf.getAtoms()) {
			aTraiter.addLast(F);
			HashBF.add(F);
		}

		while (!aTraiter.isEmpty()) {
			Atom f = aTraiter.removeFirst();
			List<Rule> LRf = atome2regle.get(f);
			if (LRf != null) {
				for (Rule r : LRf) {
					int val = cptr.get(r)-1;
					cptr.put(r, val);
					if (val == 0) {
						Atom c = r.getConclusion();
						if (!HashBF.contains(c)) {
							aTraiter.addLast(c);
							HashBF.add(c);
						}
					}
				}
			}
		}
	}

	public boolean backwardChaining(Atom Q, List<Atom> Lb, int r) {
		if (r > 0) {
			System.out.println("-".repeat(r) + " " + Q);
		} else {
			System.out.println(Q);
		}
		if (bf.getAtoms().contains(Q)) {
			return true;
		}
		for (int k = 0; k < br.size(); k++) {
			Rule R = br.getRule(k);
			if (R.getConclusion().equals(Q)) {
				List<Atom> H = R.getHypothesis();
				if (Collections.disjoint(H, Lb)) {
					int i = 0;
					int n = H.size();
					if (!Lb.contains(Q)) {
						Lb.add(Q);
					}
					while (i < n && backwardChaining(H.get(i), Lb, r+1)) {
						i++;
					}
					if (i >= n) {
						Lb.remove(Q);
						System.out.println(Q + " prouvé");
						return true;
					}
				}
			}
		}
		System.out.println(Q + " échec");
		return false;
	}

	public boolean backwardChainingOpt(Atom Q, List<Atom> Lb, int r) {
		if (r > 0) {
			System.out.println("-".repeat(r) + " " + Q);
		} else {
			System.out.println(Q);
		}
		if (bf.getAtoms().contains(Q) || prouve.contains(Q)) {
			System.out.println(Q + " deja prouvé");
			return true;
		}
		if (echec.contains(Q)) {
			System.out.println(Q + " deja échec");
			return false;
		}
		for (int k = 0; k < br.size(); k++) {
			Rule R = br.getRule(k);
			if (R.getConclusion().equals(Q)) {
				List<Atom> H = R.getHypothesis();
				if (Collections.disjoint(H, Lb)) {
					int i = 0;
					int n = H.size();
					if (!Lb.contains(Q)) {
						Lb.add(Q);
					}
					while (i < n && backwardChainingOpt(H.get(i), Lb, r+1)) {
						i++;
					}
					if (i >= n) {
						Lb.remove(Q);
						prouve.add(Q);
						System.out.println(Q + " prouvé");
						return true;
					}
				}
			}
		}
		echec.add(Q);
		System.out.println(Q + " échec");
		return false;
	}

}
