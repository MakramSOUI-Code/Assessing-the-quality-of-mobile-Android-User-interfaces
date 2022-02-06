package Main;
import java.io.FileNotFoundException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Vector;

import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

import java.io.File;
import java.util.Random;

import Detection_Rule.Input;
import Detection_Rule.MetaModel;
import Detection_Rule.Rule;
import Detection_Rule.Source_Index;
 public class  RuleMain 
  { 
   public ArrayList<Rule> rules ;
   public ArrayList<Integer> ind1 =new ArrayList<Integer>(); 
   public ArrayList<Integer> ind2 =new ArrayList<Integer>();
   public ArrayList<Integer>  ind3 =new ArrayList<Integer>();
   ArrayList<RuleMain> individuals ;
   Detection_Rule.Rule Meilleur ;
   Vector<String> perfect_rules= new Vector<String>();
   double large_number = 1000;
 //ArrayList<String>perfect_rules ;

     int max_rule_size = 1000;
	private int crowding_distance;
	 
   public RuleMain()
	      {  
		    rules = new ArrayList<Rule>();
	        new ArrayList<MetaModel>();  
	      }

  public void create_rules(Input r, int min_rules_size,int max_rules_size)
    { 
    int rules_size;
    
    int  source_index0;
    int   source_index1;
    int   source_index2;
    int   source_index3;
    int  target_index;
    Random number_generator = new Random(); 
    rules_size = min_rules_size + (int)(Math.random() * ((max_rules_size - min_rules_size) + 1));
    //rules_size = number_generator.nextInt(max_rules_size);
    if (rules_size < min_rules_size) rules_size = min_rules_size ;
    
    //System.out.println("\n number of rules to create : "+rules_size);
    
    for (int i=0; i<rules_size;i++)
    {
    	  
          source_index0 = Source_Index.index_context(); 
          ind1.add(source_index0);
          source_index1 = Source_Index.index_ContxtVal(); 
          source_index2 = Source_Index.index_Metrics(); 
          ind2.add(source_index2);
          source_index3 = Source_Index.index_Operator(); 
          target_index = Source_Index.index_Problem();
          ind3.add(target_index);
        //System.out.println(source_index1);
          Rule temp = new Rule();
          temp.src =  Input.Context()[source_index0];
          temp.src0 = Input.ValuesOfContext()[source_index1];
          temp.src2 = Input.Metrics()[source_index2];
          temp.src3 = Input.Operator()[source_index3];
          temp.trg =  Input.Problem()[target_index];
    //    print_metamodel(temp.src,temp.src0,temp.src2,temp.trg);
        
        temp.print_rule();
        rules.add(temp);
        System.out.println(temp.rule_text);
        }
        
    }
 public void print_rules()
  {
      //System.out.println("**********solution******** ");
      for (int i=0; i<rules.size();i++)
      {
          System.out.println("\n"+(i+1)+" : "+rules.get(i).rule_text);
      }
  }
 public void evaluate_rules() throws FileNotFoundException, IOException{
	 int fit=0;
	 int k=1;
	
	 Workbook workbook = null;

		/* Récupération du classeur Excel (en lecture) */
		try {
			workbook = Workbook.getWorkbook(new File("C:\\Users\\makram souii\\Desktop\\Interview\\Trace2.xls"));
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
		  System.out.println("Context:"+Context);
		 
		  for(int j=0;j<5;j++){
		  jxl.Cell cell =  sheet.getCell(j, 0);
			String Cont =  cell.getContents();
			System.out.println(Cont);
			
          if((Context.equalsIgnoreCase(Cont))){
		 
			  String  Valeur =rules.get(i).src0 ;
			  System.out.println("la valeur:"+Valeur);
			  while (k<64){
				  jxl.Cell cell21 =  sheet.getCell(5, k);
				  String Pro1 =  cell21.getContents();
				  System.out.println(Pro1);
                	jxl.Cell Valeurs =  sheet.getCell(j, k);
                	String Val =  Valeurs.getContents();
                	System.out.println("val est:"+Val);
                	   if((Valeur.equalsIgnoreCase(Val)&&(Problem.equalsIgnoreCase(Pro1)))){
                		   System.out.println("la regle est :"+rules.get(i).rule_text );
                		
                		   Meilleur=rules.get(i);	
             
                	for(int l=0;l<i;l++){
                		String inter=Meilleur.rule_text;
                      perfect_rules.add(l, inter);;
                		  System.out.println("la meilleur regle est :"+perfect_rules.elementAt(l));} 
                		 fit++;
                		
                		}
                	   System.out.println("fitness ="+fit);
                	   k++; }
	}
  }}	 
	  System.out.println("*******************"+fit);
 }
 public void muatation(int src0 , int src2, int src4)
 {    Random number_generator = new Random();
      int mutated_rule_index = 0 ;
      
      System.out.println("La 1er liste"+ind1);
      System.out.println("La 2eme liste"+ind2);
      System.out.println("La 3eme liste"+ind3);
      
      mutated_rule_index = number_generator.nextInt(rules.size());    
      System.out.println(mutated_rule_index);
      Rule temp = new Rule();
      int source_index0 = Source_Index.index_context(); 
      //System.out.println(source_index0);
      int source_index1 = Source_Index.index_ContxtVal(); 
      int source_index2 = Source_Index.index_Metrics(); 
     
      int source_index3 = Source_Index.index_Operator(); 
      int source_index4 = Source_Index.index_Problem(); 
      temp.src =  Input.Context()[ind1.get(mutated_rule_index)];
      //temp.src = rules.get(mutated_rule_index).;
      temp.src0 = Input.ValuesOfContext()[source_index1];
      temp.src2 = Input.Metrics()[ind2.get(mutated_rule_index)];
      temp.src3 = Input.Operator()[source_index3];
      temp.trg =  Input.Problem()[ind3.get(mutated_rule_index)];
      temp.print_rule();
      rules.set(mutated_rule_index, temp);
      for(int i=0;i<rules.size();i++)
       {System.out.println("*******"+rules.get(i).rule_text);
        
       }
 } 
 
   
public static void main (String[] args) throws FileNotFoundException, IOException  
 {
        int min_rules_size = 10    ;//30;
        int max_rules_size = 20;        //100;	
        int population_size=12;
        RuleMain R = new RuleMain();
        Input input = new Input();
        int  src0 = Source_Index.index_context(); 
        int src2 = Source_Index.index_Metrics(); 
        int src4 = Source_Index.index_Problem();
        R.create_rules(input, min_rules_size, max_rules_size);
        R.evaluate_rules();
       
        R.muatation(src0,src2,src4);
  }     
}