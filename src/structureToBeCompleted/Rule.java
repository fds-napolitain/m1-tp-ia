package structureToBeCompleted;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class Rule {
	private List<Atom> hypothesis;// l'hypothese : une liste d'atomes (H+)
	private List<Atom> hypothesisNegative; // atom négatif
	private Atom conclusion;// la conclusion : un atome

	/**
	 * Constructeur
	 * 
	 * @param strRule la regle, sous forme sous forme textuelle ; cette forme est
	 *                "atome1;atome2;...atomek", ou les (k-1) premiers atomes
	 *                forment l'hypothese, et le dernier forme la conclusion
	 */
	public Rule(String strRule) {
		hypothesis = new ArrayList<>();
		hypothesisNegative = new ArrayList<>();
		StringTokenizer st = new StringTokenizer(strRule, ";");
		while (st.hasMoreTokens()) {
			String s = st.nextToken(); // s represente un atome
			Atom a;
			if (s.startsWith("-")) { // négatif
				a = new Atom(s.substring(1));
				hypothesisNegative.add(a);
			} else {
				a = new Atom(s);
				hypothesis.add(a);// ajout de a a la liste des atomes de l'hypothese (pour l'instant, on ajoute
				// aussi celui de la conclusion)
			}

		}
		// on a mis tous les atomes crees en hypothese
		// il reste a tranferer le dernier en conclusion
		conclusion = hypothesis.get(hypothesis.size() - 1);
		hypothesis.remove(hypothesis.size() - 1);
	}

	/**
	 * accesseur a l'hypothese de la regle
	 * 
	 * @return l'hypothese de la regle
	 */
	public List<Atom> getHypothesis() {
		return hypothesis;
	}

	/**
	 * accesseur a l'hypothese de la regle
	 *
	 * @return l'hypothese de la regle
	 */
	public List<Atom> getHypothesisNegative() {
		return hypothesisNegative;
	}

	/**
	 * retourne la ieme atome de l'hypothese
	 * 
	 * @param i le rang de l'atome a retourner (debut a 0)
	 * @return le ieme atome de l'hypothese
	 */
	public Atom getAtomHyp(int i) {
		return hypothesis.get(i);
	}

	/**
	 * accesseur a la conclusion de la regle
	 * 
	 * @return l'atome conclusion de la regle
	 */
	public Atom getConclusion() {
		return conclusion;
	}

	/**
	 * retourne une description de la regle
	 * 
	 * @return la chaine decrivant la regle (suivant l'ecriture habituelle)
	 */
	@Override
	public String toString() {
		String s = "";
		for (int i = 0; i < hypothesis.size(); i++) {
			s += hypothesis.get(i);
			if (i < hypothesis.size() - 1)
				s += " ; ";
		}
		s += " --> ";
		s += conclusion;
		return s;
	}

}
