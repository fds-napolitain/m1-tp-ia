package csp_etud;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.io.BufferedReader;
import java.sql.SQLOutput;

public class ConstraintExp extends Constraint {

	private String expression;
	private static ScriptEngine engine = new ScriptEngineManager().getEngineByName("JavaScript");

	private static void enginePut(ScriptEngine e, String var, Object value) {
		try {
			e.put(var, Integer.parseInt(value.toString()));
		} catch (NumberFormatException nfe) {
			try {
				e.put(var, Float.parseFloat(value.toString()));
			} catch (NumberFormatException nfe2) {
				if (value.toString() == "false" || value.toString() == "true") {
					e.put(var, Boolean.parseBoolean(value.toString()));
				} else {
					e.put(var, value);
				}
			}
		}
	}

	public ConstraintExp(BufferedReader in) throws Exception {
		super(in);
		this.expression = in.readLine();
	}

	public boolean violation(Assignment a) {
		try {
			if (a.getVars().containsAll(this.varList)) {
				for (String var : a.getVars()) {
					enginePut(engine, var, a.get(var));
				}
				return !((boolean) engine.eval(this.expression));
			}
		} catch (ScriptException e) {
			System.err.println("probleme: " + e.getMessage());
		}
		return false;
	}

	public String toString() {
		return "\n\t Exp " + super.toString();
	}

}
