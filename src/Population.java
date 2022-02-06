/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import java.util.*;
import java.lang.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;




import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

/**
 *
 * @author MWM
 */
public class Population 
{ ArrayList<Rule> rules ;
    ArrayList<Individual> individuals ;
    Individual best_case;
    Individual worst_case;
    ReferencePoint ref ;
    Sigma s;
    int mutations_number;
    double weight ;
    
    int min_mutation_range;
    int max_mutation_range;
    
    int crossover_rate ;
    int mutation_rate ;
    
    int mutation_threshold ;
    
    double max_complexity  ;
    double min_complexity  ;
        
    double max_quality  ;
    double min_quality  ;
        
    double max_unprecision  ;
    double min_unprecision  ;
    
    double max_dominance_distance ;
    double min_dominance_distance ;
    
    double large_number = 1000;
    
    
    Population(ReferencePoint ref, Sigma s)
    {
        individuals = new ArrayList<Individual>();
        best_case = new Individual();
        worst_case = new Individual();
        this.ref = new ReferencePoint(ref);
        this.s = new Sigma(s);
        mutations_number = 0 ;
        weight = (double)1.0/3.0 ;
        crossover_rate = 5 ;
        mutation_rate = 3 ;
        min_mutation_range = -1;
        max_mutation_range = 1;

        
        max_complexity = 0 ;
        min_complexity = 0 ;
        
        max_quality = 0 ;
        min_quality = 0 ;
        
        max_unprecision = 0 ;
        min_unprecision = 0 ;
                
        max_dominance_distance =0;
        min_dominance_distance =0;
        
    }
    //   Mettre a jour le  current_value = 1 - current_generation*((1-user_value)/max_generations);
    public void update_sigma_value(int current_generation, int max_generations)
    {
        s.update_sigma(current_generation, max_generations);
    }
    
   // conversion de double to int
    public int double_to_int(double d)
{
    int result =0;
    
    while(Math.round(d) ==0) d = d*10 ;
    
    result = (int)d;
    
    return result ;
}
    
    public int update_mutation_threshold(ReferencePoint ref)
    {
        int temp = 0 ;
        temp = double_to_int(ref.complexity) + double_to_int(ref.quality) + double_to_int(ref.unprecision) ;
        temp = (int)temp/3 + 1 ;
        if (temp == 1) temp ++ ;
        
        temp = 1 ;
        
        return temp*10 ;
    }
    // creation d'ue population 
    public void create_poplulation(int population_size, Input r, int min_rules_size,int max_rules_size ) throws FileNotFoundException, IOException
    {
        
        for(int i=0;i<population_size;i++)
        {
            Individual temp = new Individual();
            temp.create_rules(r, min_rules_size, max_rules_size);
            individuals.add(temp);
        }
        best_case.perfect_rules(r);
        worst_case.worst_rules(r, max_rules_size);
    }
    //relancer une population 
    public void revive_poplulation(int population_size, Input r, int min_rules_size,int max_rules_size, int portion )
    {
        int new_individuals_number = Math.round((float)(population_size*portion)/100);
        
        for(int i=(population_size-1);i>=(population_size-new_individuals_number);i--)
        {
            Individual temp = new Individual();
            temp.create_rules(r, min_rules_size, max_rules_size);
            individuals.set(i,temp);
        }
    }
    
    public void evaluate_population(Input r) throws FileNotFoundException, IOException
    {
        for(int i=0;i<individuals.size();i++)
        {
            
            individuals.get(i).evaluate_rules();
        }
        this.min_max_current_generation_objective();
        this.mutation_threshold = update_mutation_threshold(ref) ;
        this.normalize_population();
        this.crowding_distance();
        this.dominance_distance();
       
        this.dominance_ranking(s);
        this.rank_sorting();
    }
    
    public void min_max_current_generation_objective()
    { 
        max_complexity = individuals.get(0).individual_NbPro ;
        min_complexity = individuals.get(0).individual_NbPro ;
        
      
        
      
        for(int i=1;i<individuals.size();i++)
        {
            if(individuals.get(i).individual_NbPro > max_complexity) max_complexity = individuals.get(i).individual_NbPro;
            if(individuals.get(i).individual_NbPro < min_complexity) min_complexity = individuals.get(i).individual_NbPro;
            
           
            
           
        }
    }
    
    public void normalize_population()
    {
        /*
         *  a + (x-A)*(b-a)/(B-A) where:
            A minimum of dataset
            B maximum of dataset
            a is from where you would like normalised data set to start
            b is where you would like normalised data set to end
            x is the value you are trying to normalise
         * 
         * so in case of [0..1] then a = 0 and b = 1
         */
        
        for(int i=0;i<individuals.size();i++)
        {
            individuals.get(i).individual_NbPro = (double)(individuals.get(i).individual_NbPro - min_complexity) / (max_complexity - min_complexity) ;
           
            
        }
    }
    
    int r_dominates(Individual x, Individual y, double dmin, double dmax, Sigma s)
    {
        int result = 0 ;
        
        if(x.rank < y.rank)
        {
            result = 1 ;
        }
        else if(x.rank > y.rank)
        {
            result = -1 ;
        }
        else 
        {
            result = t_dominates(x,y,dmin,dmax,s);
        }
        
        return result ;
    }
    
    int t_dominates(Individual x, Individual y, double dmin, double dmax, Sigma s)
    {
        int result = 0 ;
        double dist_between_individuals = 0 ;
                
        dist_between_individuals = ((double)(x.dominance_distance - y.dominance_distance)/(double)(dmax-dmin));
        
        if( dist_between_individuals < -s.current_value)
        {
            result = 1 ;
        }
        else if( dist_between_individuals > s.current_value)
        {
            result = -1 ;
        }
        else
        {
            result = 0 ;
        }
        
        return result ;
    }
    
    public void dominance_distance()
    {
        //System.out.println(" Min / Max Complexity : "+min_complexity+" / "+max_complexity);
        //System.out.println(" min / Max Dissimilarity : "+min_quality+" / "+max_quality);
        //System.out.println(" Min / Max unprecision : "+min_unprecision+" / "+max_unprecision);
        for(int i=0;i<individuals.size();i++)
        {
            individuals.get(i).dominance_distance = 0;
        }
        // determining min/max - complexity - quality - unprecision
        for(int i=0;i<individuals.size();i++)
        {
            double temporary_sum = 0;
            temporary_sum += weight*(Math.pow((individuals.get(i).individual_NbPro - ref.complexity)/(max_complexity-min_complexity), 2));
          
            individuals.get(i).dominance_distance = Math.sqrt(temporary_sum);
            /**
            if (i==0)
            {
                temporary_sum = 0;
                double dist_complexity = max_complexity-min_complexity;
                double dist_quality = max_quality-min_quality;
                double dist_unprecision = max_unprecision-min_unprecision;
       
                temporary_sum += weight*(Math.pow((individuals.get(i).individual_complexity - ref.complexity)/(dist_complexity), 2.0));
                
                System.out.println(" indiv_comp - ref.comp :"+(individuals.get(i).individual_complexity - ref.complexity)+" Dist complexity :"+dist_complexity);
                System.out.println(" division :"+(individuals.get(i).individual_complexity - ref.complexity)/(dist_complexity));
                System.out.println(" power per 2 :"+Math.pow(((individuals.get(i).individual_complexity - ref.complexity)/dist_complexity), 2));
                System.out.println(" mult per weight :"+(weight*Math.pow(((individuals.get(i).individual_complexity - ref.complexity)/dist_complexity), 2)));
                System.out.println(" temp_sum :"+temporary_sum);
                
                temporary_sum += weight*(Math.pow((individuals.get(i).individual_quality - ref.quality)/(dist_quality), 2.0));
                
                System.out.println(" indiv_qual - ref.qual :"+(individuals.get(i).individual_quality - ref.quality)+" Dist quality :"+dist_quality);
                System.out.println(" division :"+(individuals.get(i).individual_quality - ref.quality)/(dist_quality));
                System.out.println(" power per 2 :"+Math.pow(((individuals.get(i).individual_quality - ref.quality)/dist_quality), 2));
                System.out.println(" mult per weight :"+(weight*Math.pow(((individuals.get(i).individual_quality - ref.quality)/dist_quality), 2)));
                System.out.println(" temp_sum :"+temporary_sum);
                
                temporary_sum += weight*(Math.pow((individuals.get(i).individual_unprecision - ref.unprecision)/(dist_unprecision), 2.0));
                
                System.out.println(" indiv_unpr - ref.unpr :"+(individuals.get(i).individual_unprecision - ref.unprecision)+" Dist unprecision :"+dist_unprecision);
                System.out.println(" division :"+(individuals.get(i).individual_unprecision - ref.unprecision)/(dist_unprecision));
                System.out.println(" power per 2 :"+Math.pow(((individuals.get(i).individual_unprecision - ref.unprecision)/dist_unprecision), 2));
                System.out.println(" mult per weight :"+(weight*Math.pow(((individuals.get(i).individual_unprecision - ref.unprecision)/dist_unprecision), 2)));
                System.out.println(" temp_sum :"+temporary_sum);
                
                System.out.println(" sqrt(temp_sum) :"+Math.sqrt(temporary_sum));
                
                individuals.get(i).dominance_distance = Math.sqrt(temporary_sum);
            }
            * */
        }  
    }
    
    public void dominance_ranking(Sigma s)
    {
        for(int i=0;i<individuals.size();i++)
        {
            individuals.get(0).dominance_rank = 0 ;
        }
        
        int current_dominance_rank = 1 ;
        boolean dominated = false;
        boolean dominance_sorting_done = false;
        double dist_between_individuals = 0 ;
        
        this.max_dominance_distance = individuals.get(0).dominance_distance ;
        this.min_dominance_distance = individuals.get(0).dominance_distance ;
        //double[][] matrix = new double[individuals.size()][individuals.size()];
       
        for(int i=1;i<individuals.size();i++)
        {
            if(individuals.get(i).dominance_distance > max_dominance_distance) max_dominance_distance = individuals.get(i).dominance_distance;
            if(individuals.get(i).dominance_distance < min_dominance_distance) min_dominance_distance = individuals.get(i).dominance_distance;
        }
        System.out.println("\n max_dominance_distance : "+max_dominance_distance+" min_dominance_distance : "+min_dominance_distance+" Sigma : "+s.current_value);
        
        ArrayList<Individual> copies = new ArrayList() ;
        for(int i=0;i<individuals.size();i++)
        {
            copies.add(new Individual(individuals.get(i)));
            copies.get(i).dominance_rank = 0 ;
            //individuals.remove(i);
        }
        int me = 0 ;
        while(individuals.size() !=0)
        {
            individuals.remove(me);
        }
        //System.out.print("\n copies size :"+copies.size());
        do
        {
            for(int i=0;i<copies.size();i++)
            {
                dominated = false;
                
                if(copies.get(i).dominance_rank == 0)
                {
                    for(int j=0;j<copies.size();j++)
                    {
                        
                        if(copies.get(j).dominance_rank == 0)
                        {
                            if(r_dominates(copies.get(i),copies.get(j),min_dominance_distance,max_dominance_distance,s) == -1)
                            {
                                dominated = true;
                                //System.out.print("\n elt "+i+" dominÃ© par "+j);
                                //individuals.get(i).dominance_dominated.add(j);
                            }
                        }
                    }
                    if (!dominated)
                    {
                        //System.out.print("\n elt "+i+" non dominÃ©");
                        copies.get(i).dominance_rank = current_dominance_rank ;
                        individuals.add(new Individual(copies.get(i)));
                    }
                }
            }
            dominance_sorting_done = true;
            for (int i=0;i<copies.size();i++)
            {
                if(copies.get(i).dominance_rank == 0) 
                {
                    dominance_sorting_done = false;
                    //System.out.print("\n not yet");
                }
            }
            current_dominance_rank++;
        }
        while(!dominance_sorting_done);      
    }
    
  /*  public void non_dominated_sorting()
    {
        boolean sorting_done = false;
        int current_rank = 1 ;
        // initialize ranks
        for (int i=0;i<individuals.size();i++)
        {
            individuals.get(i).rank = 0 ;
            individuals.get(i).dominance_rank = 0 ;
            individuals.get(i).NbPro_dominated = new ArrayList();
            
            individuals.get(i).dominance_dominated = new ArrayList();
        }
       
        do
        {
            for (int i=0;i<individuals.size();i++)
            {
                if(individuals.get(i).rank == 0) // not yet sorted
                {
                    //System.out.print("\n element "+i+" not yet sorted");
                    for (int j=0;j<individuals.size();j++)
                    {
                        if(individuals.get(j).rank == 0)
                        {
                        
                            if (individuals.get(i).rules.size()>individuals.get(j).rules.size())
                            {
                                individuals.get(i).NbPro_dominated.add(j);
                            }
                          
                        }
                  }
                    boolean non_dominated = true;
                   
                                
                            }
                        }
        
                  
                   
        
            current_rank++;
            sorting_done = true;
            for (int i=0;i<individuals.size();i++)
            {
                individuals.get(i).NbPro_dominated = new ArrayList();
              
                if(individuals.get(i).rank == 0) sorting_done = false;}
               
        }
       
    }*/
    
    void rank_sorting()
    {
        int min_index = 0 ;
        int max_index = 0 ;
        int max_dominance_rank = 0 ;
        
        for(int i=0;i<(individuals.size()-1);i++)
        {
            min_index = i ;
            for(int j=i+1;j<individuals.size();j++)
            {
                if (individuals.get(min_index).rank>individuals.get(j).rank) min_index = j ;
            }
            if (min_index != i)
            {
                Individual temp = new Individual();
                temp = individuals.get(i);
                individuals.set(i, individuals.get(min_index));
                individuals.set(min_index, temp);
            }
        }
        
        min_index = 0 ;
        for(int i=0;i<(individuals.size()-1);i++)
        {
            min_index = i ;
            for(int j=i+1;j<individuals.size();j++)
            {
                if (individuals.get(min_index).dominance_rank>individuals.get(j).dominance_rank) min_index = j ;
            }
            if (min_index != i)
            {
                Individual temp = new Individual();
                temp = individuals.get(i);
                individuals.set(i, individuals.get(min_index));
                individuals.set(min_index, temp);
            }
        }
        for(int i=0;i<individuals.size();i++)
        {
            if(max_dominance_rank<individuals.get(i).dominance_rank)
            {
                max_dominance_rank = individuals.get(i).dominance_rank ;
            }
        }
        //for(int i=1;i<=max_dominance_rank;i++)
        //{
            max_index = 0 ;
            for(int j=0;j<(individuals.size()-1);j++)
            {
                max_index = j ;
                for(int k=j+1;k<individuals.size();k++)
                {
                    if ((individuals.get(max_index).crowding_distance < individuals.get(k).crowding_distance)&&(individuals.get(max_index).dominance_rank == individuals.get(k).dominance_rank)) max_index = k ;
                }
                if (max_index != j)
                {
                    Individual temp = new Individual();
                    temp = individuals.get(j);
                    individuals.set(j, individuals.get(max_index));
                    individuals.set(max_index, temp);
                }
            }
        //}
    }
        
    void Nombre_Problem()
    {
    	 int Nb_Pro=0;
    	 int k=1;
    	
    	 Workbook workbook = null;

    		/* Récupération du classeur Excel (en lecture) */
    		try {
    			workbook = Workbook.getWorkbook(new File("Trace/Trace.xls"));
    		} catch (BiffException e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		} catch (IOException e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		}
    		
    		/* Un fichier excel est composé de plusieurs feuilles, on y accède de la manière suivante*/
    		Sheet sheet =  workbook.getSheet(0);
    		 for (int i=0; i<individuals.size();i++){
    			  String Problem=individuals.get(i).trg;
    			//  System.out.println("la problem est :"+ Problem);
    		 while (k<64){
				  jxl.Cell cell21 =  sheet.getCell(5, k);
				  String Pro1 =  cell21.getContents();
				 // System.out.println(Pro1);
				  if(Problem.equalsIgnoreCase(Pro1)){
					  Nb_Pro++;}
				  k++;}
    		 }
    		// System.out.println(Nb_Pro);
    }
    
    void quality_sorting()
    {
        int min_index = 0 ;
        
        for(int i=0;i<(individuals.size()-1);i++)
        {
            min_index = i ;
            for(int j=i+1;j<individuals.size();j++)
            {
             //   if (individuals.get(min_index).individual_quality > individuals.get(j).individual_quality) min_index = j ;
            }
         if (min_index != i)
            {
                Individual temp = new Individual();
                temp = individuals.get(i);
                individuals.set(i, individuals.get(min_index));
                individuals.set(min_index, temp);
            }
        }
    }
    
    void unprecision_sorting()
    {
        int min_index = 0 ;
        
        for(int i=0;i<(individuals.size()-1);i++)
        {
            min_index = i ;
            for(int j=i+1;j<individuals.size();j++)
            {
               // if (individuals.get(min_index).individual_unprecision > individuals.get(j).individual_unprecision) min_index = j ;
            }
            if (min_index != i)
            {
                Individual temp = new Individual();
                temp = individuals.get(i);
                individuals.set(i, individuals.get(min_index));
                individuals.set(min_index, temp);
            }
        }
    }
    
    void crowding_distance()
    {
        for(int i=0;i<(individuals.size());i++)
        {
            this.individuals.get(i).crowding_distance = 0 ;
        }
        // first objective :
        this.Nombre_Problem();
        this.individuals.get(0).crowding_distance = large_number;
        this.individuals.get(individuals.size()-1).crowding_distance = large_number;
        for(int i=1;i<(individuals.size()-1);i++)
        {
            this.individuals.get(i).crowding_distance += ( this.individuals.get(i+1).individual_NbPro - this.individuals.get(i-1).individual_NbPro) / (max_complexity - min_complexity) ;
        }
        // second objective :
        this.quality_sorting();
        this.individuals.get(0).crowding_distance = large_number;
        this.individuals.get(individuals.size()-1).crowding_distance = large_number;
        for(int i=1;i<(individuals.size()-1);i++)
        {
         //   this.individuals.get(i).crowding_distance += ( this.individuals.get(i+1).individual_quality - this.individuals.get(i-1).individual_quality) / (max_quality - min_quality) ;
        }
        // third objective :
        this.unprecision_sorting();
        this.individuals.get(0).crowding_distance = large_number;
        this.individuals.get(individuals.size()-1).crowding_distance = large_number;
        for(int i=1;i<(individuals.size()-1);i++)
        {
         //   this.individuals.get(i).crowding_distance += ( this.individuals.get(i+1).individual_unprecision - this.individuals.get(i-1).individual_unprecision) / (max_unprecision - min_unprecision) ;
        }
    }
    
    void print_population(int current_generation)
    {
        System.out.print("\n Generation number "+current_generation+" Sigma : "+s.current_value+" with threshold : "+mutation_threshold);
        for(int i=0;i<individuals.size();i++)
        {
            //System.out.print("\n Individual "+(i+1)+" r-dominance rank : "+individuals.get(i).dominance_rank+" rules : "+individuals.get(i).rules.size()+"("+individuals.get(i).correct_rules.size()+") r-distance : "+individuals.get(i).dominance_distance+" complexity : "+individuals.get(i).individual_complexity +" quality : "+individuals.get(i).individual_quality+" unprecision : "+individuals.get(i).individual_unprecision +" hash : "+individuals.get(i).hashCode());
            System.out.print("\n Individual "+(i+1)+" rank : "+individuals.get(i).rank+" r-dominance rank : "+individuals.get(i).dominance_rank+" rules : "+individuals.get(i).rules.size()+"("+individuals.get(i).correct_rules.size()+") r-distance : "+individuals.get(i).dominance_distance+" Nombre of problem : "+individuals.get(i).individual_NbPro);}
    }
    
    void export_population(int population_index)
    {
        String file_name = new String("./output/population_");
        file_name = file_name.concat(Integer.toString(population_index));
        file_name = file_name.concat(".csv");
        try
	{
	    FileWriter writer = new FileWriter(file_name);
 
	    writer.append("Rank");
	    writer.append(',');
            writer.append("Complexity");
	    writer.append(',');
	    writer.append("Dissimilarity");
            writer.append(',');
	    writer.append("Unprecision");
	    writer.append('\n');
            
            
            for(int i=0;i<individuals.size();i++)
            {
                writer.append(Integer.toString(individuals.get(i).rank));
                writer.append(',');
                writer.append(Double.toString(individuals.get(i).individual_NbPro));
              
                writer.append('\n');
            }
 
	    //generate whatever data you want
 
	    writer.flush();
	    writer.close();
	}
	catch(IOException e)
	{
	     e.printStackTrace();
	} 
    }
    
    void selection_tournament(int offsprings_percentage, int population_size, Input r, int min_rules_size,int max_rules_size, int portion) throws FileNotFoundException, IOException
    {
        int offsprings_number = Math.round((float)(population_size*offsprings_percentage)/100);
        
        ArrayList<Individual> parents = new ArrayList<Individual>();
        ArrayList<Individual> offsprings = new ArrayList<Individual>();
        ArrayList<Individual> new_population = new ArrayList<Individual>();
        Individual temp ;
        
        Input input = new Input();
       
        Random number_generator = new Random();
        
        int first_individual_index = 0;
        int second_individual_index = 0;
        int mutations = 0 ;
        int mutation_index = 0 ;
        int max_population_size = this.individuals.size();
        
        ArrayList chosen_individuals = new ArrayList();
        
        int tournament_bound = (int)offsprings_number;
        
        for(int i=0;i<tournament_bound;i++)
        {
            number_generator = new Random();
            do
            {
                first_individual_index = number_generator.nextInt((individuals.size()));
                second_individual_index = number_generator.nextInt((individuals.size()));
            }
            while((first_individual_index==second_individual_index)||(first_individual_index<0)||(second_individual_index<0));
            
            if(individuals.get(first_individual_index).rank < individuals.get(second_individual_index).rank)
            {
                chosen_individuals.add(first_individual_index);
                //System.out.println(first_individual_index+" chosen over "+second_individual_index+" in rank");
            }
            else if(individuals.get(first_individual_index).rank > individuals.get(second_individual_index).rank)
            {
                chosen_individuals.add(second_individual_index);
                //System.out.println(second_individual_index+" chosen over "+first_individual_index+" in rank");
            }
            else
            {
                if(individuals.get(first_individual_index).crowding_distance < individuals.get(second_individual_index).crowding_distance)
                {
                    chosen_individuals.add(first_individual_index);
                    //System.out.println(first_individual_index+" chosen over "+second_individual_index+" in r-distance");
                }
                else if (individuals.get(first_individual_index).crowding_distance > individuals.get(second_individual_index).crowding_distance)
                {
                    chosen_individuals.add(second_individual_index);
                    //System.out.println(second_individual_index+" chosen over "+first_individual_index+" in r-distance");
                }
                else
                {
                    chosen_individuals.add(second_individual_index);
                    //System.out.println(second_individual_index+" chosen by randomness");
                }
            }
        }
        for(int i=0;i<chosen_individuals.size();i++)
        {
            int chosen = (int)chosen_individuals.get(i);
            parents.add(new Individual(individuals.get(chosen)));
        }
        //crossover
        for(int i=0;i<parents.size()-1;i+=2)
        {
            int first_rule_index = 0 ;
            int second_rule_index = 0 ;

            first_rule_index = number_generator.nextInt(parents.get(i).rules.size());
            second_rule_index = number_generator.nextInt(parents.get(i+1).rules.size());                        
            
            Rule temp_rule = new Rule();
            temp_rule = parents.get(i).rules.get(first_rule_index);
            parents.get(i).rules.get(first_rule_index).trg = parents.get(i+1).rules.get(second_rule_index).trg;
            parents.get(i+1).rules.get(second_rule_index).trg = temp_rule.trg;
        }
        // mutation phase Marouane idea      
         number_generator = new Random();
         int mutation_kind =0 ;
         
         for(int i=0;i<parents.size();i++)
         {
             mutation_kind = min_mutation_range + (int)(Math.random() * ((max_mutation_range - min_mutation_range) + 1));
             if(mutation_kind<0)
             {
                 //if(parents.get(i).rules.size()> mutation_threshold)
                 //{
                     if(parents.get(i).rules.size()> 1)
                     {
                        parents.get(i).mutation(first_individual_index, second_individual_index, mutation_threshold);
                        //System.out.println("\n removing elements");
                     }
                     
                 //}
                 
             }
           /*  else if(mutation_kind == 0)
             {
                 parents.get(i).mutation_modify(r);
                 //System.out.println("\n editing elements");
             }*/
            /* else
             {
                 if (parents.get(i).rules.size()<max_rules_size)
                 {
                     
                     parents.get(i).mutation_add(r);
                     //System.out.println("\n adding elements");
                 }
                 
             }*/
             
         }
         
         for(int i1=0;i1<parents.size();i1++)
         {
             this.individuals.add(new Individual(parents.get(i1)));
         }
         this.evaluate_population(r);
         
         while(individuals.size()>max_population_size)
         {
             individuals.remove(individuals.size()-1);
         }
         
         
        System.out.println("end of generation");
    }
    
}
