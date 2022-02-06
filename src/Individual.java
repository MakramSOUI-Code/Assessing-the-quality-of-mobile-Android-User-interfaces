/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author MWM
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Vector;


import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

public class Individual 
{Rule Meilleur ;
public String src;
public  String src0;
  public String src2;
  public String src3;
  public String trg;
  
Vector<String> perfect_rule= new Vector<String>();
    ArrayList<Rule> rules ;
    ArrayList<Rule> perfect_rules;
    ArrayList<Integer> correct_rules;
   
    
    int tables_number;
    int columns_number;
    int primary_keys_number;
    int foreign_keys_number;
    int composition_keys_number;
    
    double correct_rules_ratio ;
    double metamodel_constraints_ratio ;
    

    double individual_NbPro ;
  
    double crowding_distance ;
    double dominance_distance ;
    
    int rank ;
    int dominance_rank ;
    
    ArrayList NbPro_dominated ;
   
    ArrayList dominance_dominated ;
    
    double total_tables;
    double not_empty_table;
    double att_in_table;
    double pk_in_table;
    double total_fkeys;
    double fk_is_pk;    
    public ArrayList<Integer> ind1 =new ArrayList<Integer>(); 
    public ArrayList<Integer> ind2 =new ArrayList<Integer>();
    public ArrayList<Integer>  ind3 =new ArrayList<Integer>();
    Individual()
    {
        rules = new ArrayList<Rule>();
        perfect_rules = new ArrayList<Rule>();
        correct_rules = new ArrayList<Integer>();
       ;
        
        tables_number =0;
        columns_number =0;
        primary_keys_number =0;
        foreign_keys_number =0;
        composition_keys_number =0;
        
        correct_rules_ratio =0;
        metamodel_constraints_ratio  =0;
    
    
        individual_NbPro  =0;
     
    
        crowding_distance =0;
        dominance_distance  =0;
    
        rank  =0;
        dominance_rank  =0;
        
        NbPro_dominated = new ArrayList() ;
        int rules_size;
        dominance_dominated = new ArrayList() ;
        trg=Input.Problem()[0];
        total_tables  =0;
        not_empty_table  =0;
        att_in_table  =0;
        pk_in_table  =0;
        total_fkeys  =0;
        fk_is_pk  =0; 
        
    }
    Individual(Individual i)
    {
        rules = new ArrayList<Rule>(i.rules);
        perfect_rules = new ArrayList<Rule>(i.perfect_rules);
        correct_rules = new ArrayList<Integer>(i.correct_rules);
    
      
        tables_number =i.tables_number;
        columns_number =i.columns_number;
        primary_keys_number =i.primary_keys_number;
        foreign_keys_number =i.foreign_keys_number;
        composition_keys_number =i.composition_keys_number;
        
        correct_rules_ratio =i.correct_rules_ratio;
        metamodel_constraints_ratio  =i.metamodel_constraints_ratio;
    
       
        individual_NbPro  =i.individual_NbPro;

    
        crowding_distance =i.crowding_distance;
        dominance_distance  =i.dominance_distance;
     
        rank  =i.rank;
        dominance_rank  =i.dominance_rank;
        
        NbPro_dominated = new ArrayList(i.NbPro_dominated) ;
       
        dominance_dominated = new ArrayList(i.dominance_dominated) ;
    
        total_tables  =i.total_tables;
        not_empty_table  =i.not_empty_table;
        att_in_table  =i.att_in_table;
        pk_in_table  =i.pk_in_table;
        total_fkeys  =i.total_fkeys;
        fk_is_pk  =i.fk_is_pk; 
    }
    
    public void create_rules(Input r, int min_rules_size,int max_rules_size)
    { 
        int rules_size;
        int source_index;
        int source_index0;
        int source_index1;
     int   source_index2;
        int target_index;
        Random number_generator = new Random(); 
        rules_size = min_rules_size + (int)(Math.random() * ((max_rules_size - min_rules_size) + 1));
        //rules_size = number_generator.nextInt(max_rules_size);
        if (rules_size < min_rules_size) rules_size = min_rules_size ;
        
        //System.out.println("\n number of rules to create : "+rules_size);
        
        for (int i=0; i<rules_size;i++)
        {
            source_index = number_generator.nextInt(Input.Context().length);
            ind1.add(source_index);
            source_index0= number_generator.nextInt(Input.ValuesOfContext().length);
            source_index1 = number_generator.nextInt(Input.Metrics().length);
            ind2.add(source_index1);
            source_index2 = number_generator.nextInt(Input.Operator().length);
            target_index = number_generator.nextInt(Input.Problem().length);
            ind3.add(target_index);
           // System.out.println(source_index1);
            Rule temp = new Rule();
            temp.src =  Input.Context()[source_index];
            temp.src0 = Input.ValuesOfContext()[source_index0];
            temp.src2 = Input.Metrics()[source_index1];
            temp.src3 = Input.Operator()[source_index2];
            temp.trg =  Input.Problem()[target_index];
        //    print_metamodel(temp.src,temp.src0,temp.src2,temp.trg);
           
            temp.print_rule();
            rules.add(temp);
            System.out.println(temp.rule_text); } }
    void print_rules()
    {
        System.out.println("\n Tree decomposition into leaves : ");
        for (int i=0; i<rules.size();i++)
        {
            System.out.println("\n leaf number "+(i+1)+" : "+rules.get(i).rule_text);
        }
    }
    
 
/*    void execute_rules(Input r)
    {
        this.output = new ArrayList<MetaModel>();
        ArrayList<MetaModel> output = new ArrayList<MetaModel>() ;
        MetaModel temp = new MetaModel();
             
        for (int i=0; i<r.model.size();i++)
        {
            if(r.model.get(i).prefixes.get(0).getText().equals("Class"))
            {
                //System.out.print("BINGO CLASS!!!");
                    for (int j=0; j<rules.size();j++)
                    {
                        boolean found = false ;
                        if(rules.get(j).src.prefixes.get(0).getText().equals("Class"))
                        {
                            found = true ;
                            temp = new MetaModel();
                            temp.prefixes = rules.get(j).trg.prefixes ;
                            
                            if (rules.get(j).trg.suffixes.size()==1)
                            {
                                temp.suffixes = r.model.get(i).suffixes;
                            }
                            else
                            {
                                String predicate = new String("class_null");
                                Element extra = new Element("suffix");
                                extra.addContent(new Text(predicate));
                                temp.suffixes.add(extra);
                                for (int k=1; k<rules.get(j).trg.suffixes.size();k++)
                                {
                                    predicate = new String("null");
                                    extra = new Element("suffix");
                                    extra.addContent(new Text(predicate));
                                    temp.suffixes.add(extra);
                                    
                                }
                            }
                        }
                        if (found) 
                        {
                            temp.print_metamodel();
                            output.add(temp);
                        }
                    }
            }
            if(r.model.get(i).prefixes.get(0).getText().equals("Attribute"))
            {
                
                    for (int j=0; j<rules.size();j++)
                    {
                        boolean found = false ;
                        if(rules.get(j).src.prefixes.get(0).getText().equals("Attribute"))
                        {
                            found = true ;
                            temp = new MetaModel();
                            temp.prefixes = rules.get(j).trg.prefixes ;
                            if (r.model.get(i).suffixes.size() == rules.get(j).trg.suffixes.size())
                            {
                                temp.suffixes.add(r.model.get(i).suffixes.get(0));
                                temp.suffixes.add(r.model.get(i).suffixes.get(1));
                                temp.suffixes.add(rules.get(j).trg.suffixes.get((rules.get(j).trg.suffixes.size()-1)));
                            }
                            else 
                            {
                                String predicate = new String("attribute_null");
                                Element extra = new Element("suffix");
                                extra.addContent(new Text(predicate));
                                temp.suffixes.add(extra);
                                for (int k=1; k<rules.get(j).trg.suffixes.size();k++)
                                {
                                    predicate = new String("null");
                                    extra = new Element("suffix");
                                    extra.addContent(new Text(predicate));
                                    temp.suffixes.add(extra);
                                    
                                }
                            }      
                        }
                        if (found) 
                        {
                            temp.print_metamodel();
                            output.add(temp);
                        }
                    }
            }
            else if(r.model.get(i).prefixes.get(0).getText().equals("Association"))
            {
                    for (int j=0; j<rules.size();j++)
                    {
                        boolean found = false ;
                        if(rules.get(j).src.prefixes.get(0).getText().equals("Association"))
                        {
                            found = true ;
                            temp = new MetaModel();
                            temp.prefixes = rules.get(j).trg.prefixes ;
                            
                            if (rules.get(j).trg.suffixes.size() == 3)
                            {
                                temp.suffixes.add(r.model.get(i).suffixes.get(6));
                                temp.suffixes.add(r.model.get(i).suffixes.get(7));
                                temp.suffixes.add(rules.get(j).trg.suffixes.get((rules.get(j).trg.suffixes.size()-1)));
                            }
                            else if (rules.get(j).trg.suffixes.size() == 4)
                            {
                                temp.suffixes.add(r.model.get(i).suffixes.get(6));
                                temp.suffixes.add(r.model.get(i).suffixes.get(7));
                                temp.suffixes.add(r.model.get(i).suffixes.get(5));
                                temp.suffixes.add(rules.get(j).trg.suffixes.get((rules.get(j).trg.suffixes.size()-1)));
                            }
                            else
                            {
                                String predicate = new String("association_null");
                                Element extra = new Element("suffix");
                                extra.addContent(new Text(predicate));
                                temp.suffixes.add(extra);
                                for (int k=1; k<rules.get(j).trg.suffixes.size();k++)
                                {
                                    predicate = new String("null");
                                    extra = new Element("suffix");
                                    extra.addContent(new Text(predicate));
                                    temp.suffixes.add(extra);
                                    
                                }
                            }
                        }
                        if (found) 
                        {
                            temp.print_metamodel();
                            output.add(temp);
                        }
                    }
            }
            else if(r.model.get(i).prefixes.get(0).getText().equals("Generalization"))
            {
                    for (int j=0; j<rules.size();j++)
                    {
                        boolean found = false ;
                        if(rules.get(j).src.prefixes.get(0).getText().equals("Generalization"))
                        {
                            found = true ;
                            temp = new MetaModel();
                            temp.prefixes = rules.get(j).trg.prefixes ;
                            
                            if (rules.get(j).trg.suffixes.size() == 3)
                            {
                                temp.suffixes.add(r.model.get(i).suffixes.get(1));
                                temp.suffixes.add(r.model.get(i).suffixes.get(2));
                                temp.suffixes.add(rules.get(j).trg.suffixes.get((rules.get(j).trg.suffixes.size()-1)));
                            }
                            else
                            {
                                String predicate = new String("generalization_null");
                                Element extra = new Element("suffix");
                                extra.addContent(new Text(predicate));
                                temp.suffixes.add(extra);
                                for (int k=1; k<rules.get(j).trg.suffixes.size();k++)
                                {
                                    predicate = new String("null");
                                    extra = new Element("suffix");
                                    extra.addContent(new Text(predicate));
                                    temp.suffixes.add(extra);
                                    
                                }
                            }
                            
                        }
                        if (found) 
                        {
                            temp.print_metamodel();
                            output.add(temp);
                        }
                    }
            }
        }*//*
        System.out.println("\n output.size :"+output.size());
        if (output.size() > 0)
        {
            int l = 0 ;
            while (l<output.size())
            {
                System.out.println("\n execution :"+output.get(l).metamodel_text);
                l++;
            }
        }*/
        //r.writeXML(output);
      /*  this.output = new ArrayList<MetaModel>();
        this.output = output;
    }*/
    
 /*   void evaluate_rules(InputReader metrics)
    {
        individual_quality = 0 ;
        individual_complexity = 0 ;
        tables_number = 0;
        columns_number = 0;
        primary_keys_number = 0;
        correct_rules = new ArrayList<Integer>();
        this.evaluation_empty_table();
        this.evaluation_primary_foreign_keys();
        /*
        if (classes_number != 0) number_attributes_per_class = (int)(attributes_number/classes_number);
        else number_attributes_per_class = 0 ;
        if (classes_number != 0) number_associations_per_class = (int)(associations_number/classes_number);
        else number_attributes_per_class = 0 ;
        
        individual_quality = Math.abs(classes_number - metrcis.classes_lower_bound);
        individual_quality += Math.abs(attributes_number - metrcis.attributes_lower_bound);
        individual_quality += Math.abs(associations_number - metrcis.associations_lower_bound);
        individual_quality += Math.abs(generalization_number - metrcis.generalization_lower_bound);
        individual_quality += Math.abs(number_attributes_per_class - metrcis.average_attributes_per_class);
        individual_quality += Math.abs(number_associations_per_class - metrcis.average_associations_per_class);
        
        individual_quality = (float) individual_quality/6;
        
        System.out.print("\n individual_quality : "+individual_quality);
        * 
        * */
        
        // tables number :
     /*   for (int i=0; i<output.size();i++)
                {
                    if(output.get(i).prefixes.get(0).getText().equals("Table"))
                    {
                        tables_number++;
                    }
                }
        // columns number :
        for (int i=0; i<output.size();i++)
                {
                    if(output.get(i).prefixes.get(0).getText().equals("Column"))
                    {
                        columns_number++;
                    }
                }
        // primary keys number :
        for (int i=0; i<output.size();i++)
                {
                    if(output.get(i).suffixes.size()>1)
                    {
                        if(output.get(i).suffixes.get(output.get(i).suffixes.size()-1).getText().equals("pk"))
                        {
                            primary_keys_number++;
                        }
                    }  
                }
        int max1 ;
        if(tables_number>(metrics.tables_lower_bound + (tables_number*this.rules.size()))) max1 = tables_number ; else max1 = metrics.tables_lower_bound + this.rules.size() ;
        int max2 ;
        if(columns_number>(metrics.columns_lower_bound + (columns_number*this.rules.size()))) max2 = columns_number ; else max2 = metrics.columns_lower_bound + this.rules.size() ;
        int max3 ;
        if(primary_keys_number>(metrics.primary_keys_lower_bound + (primary_keys_number*this.rules.size()))) max3 = primary_keys_number ; else max3 = metrics.primary_keys_lower_bound + this.rules.size() ;
        
        individual_quality = Math.abs(metrics.tables_lower_bound );
        individual_quality += Math.abs(metrics.columns_lower_bound );
        individual_quality +=Math.abs(metrics.primary_keys_lower_bound );
        individual_quality /= (double) (tables_number+columns_number+primary_keys_number) ;
        //System.out.print("\n number of detected tables : "+tables_number);
        //System.out.print("\n number of detected columns : "+columns_number);
        //System.out.print("\n number of expected tables : "+metrcis.tables_lower_bound);
        //System.out.print("\n number of expected columns : "+metrcis.columns_lower_bound);
        //System.out.print("\n Individual Quality : "+individual_quality);
        
        this.complexity_calc();
        
        // Checking rules unprecision :
        
         for (int i=0; i<rules.size();i++)
         {
            //System.out.println("\n checking correct rules number at iteration : "+(i+1)+" is "+correct_rules.size());
            if((rules.get(i).src.prefixes.get(0).getText().equals("Class"))&&rules.get(i).trg.prefixes.get(0).getText().equals("Table"))
            {
                correct_rules.add(i);
                //System.out.println("\n class - table working");
            }
            else if((rules.get(i).src.prefixes.get(0).getText().equals("Attribute"))&&(rules.get(i).src.suffixes.get(2).getText().equals("unique")))
            {
                if((rules.get(i).trg.prefixes.get(0).getText().equals("Column"))&&(rules.get(i).trg.suffixes.get(2).getText().equals("pk")))
                {
                    correct_rules.add(i);
                    //System.out.println("\n att unique working");
                }

            }
            else if((rules.get(i).src.prefixes.get(0).getText().equals("Attribute"))&&(rules.get(i).src.suffixes.get(2).getText().equals("notunique")))
            {
                if((rules.get(i).trg.prefixes.get(0).getText().equals("Column"))&&(rules.get(i).trg.suffixes.get(2).getText().equals("_")))
                {
                    correct_rules.add(i);
                    //System.out.println("\n att notunique working");
                }
            }
            else if((rules.get(i).src.prefixes.get(0).getText().equals("Association"))&&(rules.get(i).src.suffixes.get(1).getText().equals("1")))
            {
                if((rules.get(i).trg.prefixes.get(0).getText().equals("Column"))&&(rules.get(i).trg.suffixes.get(2).getText().equals("fk")))
                {
                    correct_rules.add(i);
                    //System.out.println("\n ass fk working");
                }

            }
            else if((rules.get(i).src.prefixes.get(0).getText().equals("Association"))&&(rules.get(i).src.suffixes.get(1).getText().equals("n")))
            {
                if((rules.get(i).trg.prefixes.get(0).getText().equals("Column"))&&(rules.get(i).trg.suffixes.size()==4))
                {
                    correct_rules.add(i);
                    //System.out.println("\n ass pfk working");
                }
            }
            else if((rules.get(i).src.prefixes.get(0).getText().equals("Generalization")))
            {
                if((rules.get(i).trg.prefixes.get(0).getText().equals("Column"))&&(rules.get(i).trg.suffixes.get(2).getText().equals("pfk")))
                {
                    correct_rules.add(i);
                    //System.out.println("\n generalization working");
                }
            }
         }
         this.correct_rules_ratio = (double)correct_rules.size()/rules.size();
         
         // calculating constraints
         
         double temp1 = 0 ;
         double temp2 = 0 ;
         
         if (this.total_tables != 0)
         {
             temp1 = (double)(this.not_empty_table/this.total_tables);
         }
         if (this.total_fkeys != 0)
         {
             temp2 = (double)(this.fk_is_pk/this.total_fkeys);
         }
         
         //System.out.println("\n not_empty_table/this.total_tables : "+temp1);
         //System.out.println("\n this.fk_is_pk/this.total_fkeys : "+temp2);
         
         this.metamodel_constraints_ratio = (double) (temp1 + temp2 + 1.0 + 1.0) / 4.0 ;
         
         // unprecision :
         
         individual_unprecision = 1 - (0.75*metamodel_constraints_ratio + correct_rules_ratio*0.25) ;
    }
    */
    
    void perfect_rules(Input input)
    {
        
        int rules_size = 6 ;
        for (int i=0; i<rules_size; i++)
        {
            Rule temp = new Rule();
            temp.src =  Input.Context()[4];
            temp.src0 = Input.ValuesOfContext()[2];
            temp.src2 = Input.Metrics()[i];
            temp.src3 = Input.Operator()[1];
            temp.trg =  Input.Problem()[i];
            temp.print_rule();
            
            perfect_rules.add(temp);
        }
        this.individual_NbPro =16;
    
        this.rules = this.perfect_rules ;
      
     
        try {
			this.evaluate_rules();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        //this.print_individual_performance();
    }
    void worst_rules(Input input, int max_rules_size) throws FileNotFoundException, IOException
    {
        
        for (int i=0; i<max_rules_size;i++)
        {
            Rule temp = new Rule();
            temp.src =  Input.Context()[4];
            temp.src0 = Input.ValuesOfContext()[2];
            temp.src2 = Input.Metrics()[6];
            temp.src3 = Input.Operator()[1];
            temp.trg =  Input.Problem()[5];
            //temp.print_rule(); 
            rules.add(temp);
        }
        this.individual_NbPro = 0;
      
       
        this.evaluate_rules();
        //this.print_individual_performance();
    }
    
    void apply_perf_rules()
    {
        ArrayList<Integer> indexes = new ArrayList<Integer>();
        
        for (int i=0;i<this.rules.size();i++)
        {
            indexes.add(i);
        }
    }
    
    void cross_over()
    {
        int first_rule_index = 0 ;
        int second_rule_index = 0 ;
        Random number_generator = new Random();
        do
        {
            first_rule_index = number_generator.nextInt(rules.size());
            second_rule_index = number_generator.nextInt(rules.size());
            
        }while(first_rule_index==second_rule_index);
        
        Rule temp = new Rule();
        temp = rules.get(first_rule_index);
        rules.get(first_rule_index).trg = rules.get(second_rule_index).trg;
        rules.get(second_rule_index).trg = temp.trg;
    }
    
  /*  void mutation()
    {
        Random number_generator = new Random();
        int mutated_rule_index = number_generator.nextInt(rules.size());
        rules.remove(mutated_rule_index);
    }
    
    void mutation_modify(InputReader r)
    {
        Random number_generator = new Random();
        int mutated_rule_index = 0 ;
        //System.out.println("starting mutation2 in individual where correct_rules.size :"+correct_rules.size()+"rules.size :"+rules.size());
        //do
        //{
            mutated_rule_index = number_generator.nextInt(rules.size());
            //if(correct_rules.isEmpty()) break;
        //}
        //while((!correct_rules.contains(mutated_rule_index)&&(correct_rules.size()<rules.size())));
        //System.out.println("finishing mutation2");
        
        int source_index = number_generator.nextInt(r.source.size());
        int target_index = number_generator.nextInt(r.target.size());
            
        Rule temp = new Rule();
        temp.src = r.source.get(source_index);
        temp.trg = r.target.get(target_index);
            
        rules.set(mutated_rule_index, temp);
    }
    */
  /*  void mutation_add(InputReader r)
    {
        Random number_generator = new Random();
        //int mutated_rule_index = 0 ;
        //System.out.println("starting mutation2 in individual where correct_rules.size :"+correct_rules.size()+"rules.size :"+rules.size());
        //do
        //{
            //mutated_rule_index = number_generator.nextInt(rules.size());
            //if(correct_rules.isEmpty()) break;
        //}
        //while((!correct_rules.contains(mutated_rule_index)&&(correct_rules.size()<rules.size())));
        //System.out.println("finishing mutation2");
        
        int source_index = number_generator.nextInt(r.source.size());
        int target_index = number_generator.nextInt(r.target.size());
            
        Rule temp = new Rule();
        temp.src = r.source.get(source_index);
        temp.trg = r.target.get(target_index);
            
        rules.add(temp);
    }*/
    
    
    void print_individual_performance()
    {
        System.out.println("\n --- Printing individual overall performance ---");
        System.out.println("\n Nombre of Problem : "+this.individual_NbPro);
      
        System.out.println("\n not empty tables : "+this.not_empty_table);
        System.out.println("\n total tables : "+this.total_tables);
        System.out.println("\n fk is pk : "+this.fk_is_pk);
        System.out.println("\n total fk : "+this.total_fkeys);
        System.out.println("\n metamodel constraints ratio : "+this.metamodel_constraints_ratio);
        
        System.out.println("\n correct nodes / all nodes : "+correct_rules.size()+" / "+rules.size());
        System.out.print("\n correct rules numbers : ");
        for (int i=0; i<correct_rules.size();i++)
         {
             System.out.print((correct_rules.get(i)+1)+" ");
         }
    }
    
  
 /*   void evaluation_empty_table()
    {
        boolean table_not_empty = false ;
        String table_name = new String();
        this.not_empty_table = 0 ;
        this.total_tables = 0 ;
        
        for(int i=0; i<this.output.size();i++)
        {
            table_not_empty = false ;
            if(this.output.get(i).prefixes.get(0).getText().equals("Table"))
            {
                total_tables++;
                table_name = new String(this.output.get(i).suffixes.get(0).getText());
                //System.out.print("\n table number "+i+" : "+table_name);
                for(int j=0; j<this.output.size();j++)
                {
                    if(this.output.get(j).prefixes.get(0).getText().equals("Column"))
                    {
                        if(this.output.get(j).suffixes.get(1).getText().equals(table_name))
                        {
                            table_not_empty = true ;
                            //System.out.print("\n table "+table_name+" not empty !");
                        }   
                    }
                }
            }
            if(table_not_empty)
            {
                not_empty_table++;
            }
        }
    }
    
    void evaluation_primary_foreign_keys()
    {
        boolean fk_is_pk = false ;
        this.fk_is_pk = 0;
        this.total_fkeys = 0;
        String attribute_name = new String();
        
        for(int i=0; i<this.output.size();i++)
        {
            fk_is_pk = false ;
            if(this.output.get(i).prefixes.get(0).getText().equals("Column"))
            {
                if((this.output.get(i).suffixes.get(this.output.get(i).suffixes.size()-1).getText().equals("fk"))||(this.output.get(i).suffixes.get(this.output.get(i).suffixes.size()-1).getText().equals("pfk")))
                {
                    total_fkeys++;
                    attribute_name = new String(this.output.get(i).suffixes.get(0).getText());
                    //System.out.print("\n foreign key "+i+" : "+attribute_name);
                    for(int j=0; j<this.output.size();j++)
                    {
                        if(this.output.get(j).prefixes.get(0).getText().equals("Column"))
                        {
                            if((this.output.get(j).suffixes.get(0).getText().equals(attribute_name))&&(this.output.get(j).suffixes.get(this.output.get(j).suffixes.size()-1).getText().equals("pk")))
                            {
                                fk_is_pk = true ;
                                //System.out.print("\n attribute "+attribute_name+" is both foreign and primary key !");
                            }   
                        }
                    }
                }
                
                
            }
            if(fk_is_pk)
            {
                this.fk_is_pk++;
            }
        }
    }
    */
    void evaluate_rules() throws FileNotFoundException, IOException{
   	 int fit=0;
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
   	  for (int i=0; i<rules.size();i++){
   		  String  Context =rules.get(i).src ;
   		  String Problem=rules.get(i).trg ;
   		//  System.out.println("Context:"+Context);
   		 
   		  for(int j=0;j<5;j++){
   		  jxl.Cell cell =  sheet.getCell(j, 0);
   			String Cont =  cell.getContents();
   			//System.out.println(Cont);
   			
             if((Context.equalsIgnoreCase(Cont))){
   		 
   			  String  Valeur =rules.get(i).src0 ;
   			//  System.out.println("la valeur:"+Valeur);
   			  while (k<64){
   				  jxl.Cell cell21 =  sheet.getCell(5, k);
   				  String Pro1 =  cell21.getContents();
   				//  System.out.println(Pro1);
                   	jxl.Cell Valeurs =  sheet.getCell(j, k);
                   	String Val =  Valeurs.getContents();
                  // 	System.out.println("val est:"+Val);
                   	   if((Valeur.equalsIgnoreCase(Val)&&(Problem.equalsIgnoreCase(Pro1)))){
                   		 //  System.out.println("la regle est :"+rules.get(i).rule_text );
                   		
                   		   Meilleur=rules.get(i);	
                
                   	for(int l=0;l<i;l++){
                   		String inter=Meilleur.rule_text;
                         perfect_rule.add(l, inter);;
                   		//  System.out.println("la meilleur regle est :"+perfect_rule.elementAt(l));
                         } 
                   		 fit++;
                   		}
                   	   k++; }
   	}
     }}	 
   	  System.out.println(fit);
    }
    public void mutation(int src0 , int src2, int src4)
    {    Random number_generator = new Random();
         int mutated_rule_index = 0 ;
         
        System.out.println("La 1er liste"+ind1);
        System.out.println("La 2eme liste"+ind2);
         System.out.println("La 3eme liste"+ind3);
         
         mutated_rule_index = number_generator.nextInt(rules.size());    
       //  System.out.println(mutated_rule_index);
         Rule temp = new Rule();
       int source_index1 = number_generator.nextInt(Input.ValuesOfContext().length);
   		 int source_index3=  number_generator.nextInt(Input.Operator().length);
         temp.src =  Input.Context()[ind1.get(mutated_rule_index)];
         //temp.src = rules.get(mutated_rule_index).;
         temp.src0 = Input.ValuesOfContext()[source_index1];
         temp.src2 = Input.Metrics()[ind2.get(mutated_rule_index)];
         temp.src3 = Input.Operator()[source_index3];
         temp.trg =  Input.Problem()[ind3.get(mutated_rule_index)];
         temp.print_rule();
         rules.set(mutated_rule_index, temp);
         for(int i=0;i<rules.size();i++)
          {//System.out.println("*******"+rules.get(i).rule_text);
           
          }
    } 
    public static void main(String[] args) throws FileNotFoundException, IOException
    {
        int min_rules_size = 7;
        int max_rules_size = 10;
        Individual i = new Individual();
        Individual best_case = new Individual();
        Individual worst_case = new Individual();
        Input input = new Input();
        
       

        i.create_rules(input, min_rules_size, max_rules_size);
        //i.print_rules();
       
        i.evaluate_rules();

        best_case.perfect_rules(input);
        worst_case.worst_rules(input, max_rules_size);
        
        //normalization
       
        //i.individual_unprecision = (double)(i.individual_unprecision - best_case.individual_unprecision)/(worst_case.individual_unprecision - best_case.individual_unprecision);
        
               
       
        best_case.print_individual_performance();
        worst_case.print_individual_performance();
        i.print_individual_performance();
        
    }
     
    
}
