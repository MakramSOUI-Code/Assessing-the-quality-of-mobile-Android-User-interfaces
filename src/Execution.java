
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


import java.util.*;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.*;

/**
 *
 * @author MWM
 */
public class Execution 
{
    
    static int population_size ;
    static int min_rules_size;
    static int max_rules_size ;
    static int generations ;
    static int offsprings_percentage ;
    static int new_individuals_percentage ;
    //ArrayList<MetaModel> output;
    static ReferencePoint ref;
    static Sigma s;
    
    Execution()
    {
        population_size = 20 ;
        generations = 20 ;
        ref = new ReferencePoint(0.5,0.3,0.6);
        s = new Sigma(0.35);
        
        offsprings_percentage = 50 ;
        new_individuals_percentage = 10 ;
        min_rules_size = 20 ;        
        max_rules_size = 50 ;
       // output = new ArrayList<MetaModel>();
        
        
    }
    
    public static void main(String[] args) throws FileNotFoundException, IOException
    {
        Execution e = new Execution();
        Input input = new Input();
        Population p = new Population(ref,s);
        p.create_poplulation(population_size, input, min_rules_size, max_rules_size);
        p.export_population(0); 
        for(int i=0;i<generations;i++)
        {
            p.update_sigma_value(i, generations);         
            p.print_population(i);
            p.selection_tournament(offsprings_percentage,population_size, input, min_rules_size, max_rules_size,new_individuals_percentage);
            //p.print_population();
            //p.export_population(i+1);
        }
        
        //p.msm(input, min_rules_size, max_rules_size);
        
    }
}
