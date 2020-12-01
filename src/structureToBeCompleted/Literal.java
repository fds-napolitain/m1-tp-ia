package structureToBeCompleted;

public class Literal extends Atom {

	public Signe signe;

	public Literal(String s) {
		super(s);
		this.signe = Signe.NEGATIF;
	}

	public Literal(String s, Signe signe) {
		super(s);
		this.signe = signe;
	}

	/*@Override
	public boolean equals(Object b) {
		if (!(b instanceof Literal))
			return false;
		return this.symbol.equals(((Literal) b).symbol) &&
				this.signe == ((Literal) b).signe;
	}*/
}
