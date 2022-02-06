package Detection_Rule;

import java.util.ArrayList;

import Main.RuleMain;

public class Solution {
	    ArrayList<RuleMain> sol =new ArrayList<RuleMain>(); 
	    Solution()
	    {}
	    Solution(Solution S)
	    {}
	    
	public ArrayList<RuleMain> create_solution (int min_sol_size,int max_sol_size)
	{
		int sol_size;
		 sol_size = min_sol_size + (int)(Math.random() * ((max_sol_size - min_sol_size) + 1));
		 if (sol_size < min_sol_size) 
			 {sol_size = min_sol_size ;}
		   
	        //Solution Sol = new Solution();
	        
		 for (int i=0; i<sol_size;i++)
		    { 
			 int min_rules_size = 10    ;//30;
		     int max_rules_size = 20;        //100;	
		     //int population_size= 12;
		     Input input = new Input();
			RuleMain R = new RuleMain ();
			R.create_rules(input, min_rules_size, max_rules_size);
			sol.add(R);
		    }
		return sol ;
		
	}
	  public void print_solution()
	    { 
	        for(int i=0;i<sol.size();i++)
	        {
	            System.out.println("\n--- Solution number "+(i+1)+"---");
	            sol.get(i).print_rules();
	        }
	    }
	 public static void main(String[] args) 
	    {
	    	Solution S = new Solution ();
	    	int min_sol_size=5;
			int max_sol_size=12;
			S.create_solution(min_sol_size, max_sol_size);
			S.print_solution();
	    }
}
