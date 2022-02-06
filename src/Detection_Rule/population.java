package Detection_Rule;

import java.util.ArrayList;

import Main.RuleMain;

public class population {
	ArrayList<RuleMain> solutions ;
	int population_size= 10 ;
    population()
         {}
    population (population P)
    {}
    public void create_poplulation()
    {
        this.solutions = new ArrayList<RuleMain>();
        int min_rules_size = 10    ;//30;
        int max_rules_size = 20;        //100;	
        Input input = new Input();
        for(int i=0;i<population_size;i++)
        {
            RuleMain temp = new RuleMain();
            temp.create_rules(input, min_rules_size, max_rules_size);
            //temp.print_rules();
            solutions.add(temp);
         
        }
        // System.out.println("la sol est : "+solutions);
       
    }
    public void print_popluation()
    { 
        for(int i=0;i<solutions.size();i++)
        {
            System.out.println("\n--- Solution number "+(i+1)+"---");
            solutions.get(i).print_rules();
        }
    }
    
    public static void main(String[] args) 
    {
    	population p = new population ();
    	p.create_poplulation();
    	p.print_popluation();
    }
}