package Detection_Rule;

/**
 * 
 */


import java.util.Vector;

/**
 * @author User
 *
 */
public class Input {

	/**
	 * @param args
	 * @return 
	 * @return 
	 */
	public static  String[] Context() {
		String [] Context= { "Age","Motivation","Education Level","User Experience","Interest"};
	
		
		return Context;

	}
	public static  String[] ValuesOfContext() {
		String [] Values= { "L","M","H"};
	
	
		return Values;

	}
	public static  String[] Metrics() {
		String [] Metrics= { "Regularity","Grouping","Sequence","Simplicity","Unity","Density","Symmetry","Homogeneity"};
	
		
		return Metrics;

	}
	public static  String[] Operator() {
		String [] Operator= { "<","<=",">",">="};
	
		
		return Operator;

	}

	public static  String[] Problem() {
		String [] Problem= { "Ineffective appearance of widgets","Overloaded MUI","Complicated MUI","Difficult navigation","Incorrect data presentation"};
	
		
		return Problem;

	}
	

}
