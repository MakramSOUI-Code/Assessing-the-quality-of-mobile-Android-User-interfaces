package measureMetrics;

import java.awt.GraphicsConfiguration;
import java.awt.Rectangle;
import java.awt.Window;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import bean.AnalyzedClass;
import Detection_Rule.MetaModel;

public class CMetrics2 {
	public static double Density ( String d) throws IOException{
		
		if ( Dimension.getHeightInterface(d) != null) { 
			 if (Dimension.getWidthInterface(d) != null) {
		
		  int HI = Integer.parseInt( Dimension.getHeightInterface(d).get(0));
	int WI=Integer.parseInt(Dimension.getWidthInterface(d).get(0)) ;
	double AreaInterface= HI*WI;
		int somme=0;
		ArrayList<String> WC=new ArrayList<String>();
		WC=Dimension.getWidth(d) ;
		ArrayList<String> HC=new ArrayList<String>();
		HC=Dimension.getHeight(d) ;
		java.awt.Dimension tailleEcran = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
		int hauteur = (int)tailleEcran.getHeight();
		int largeur = (int)tailleEcran.getWidth();
		//System.out.println(hauteur);
		//System.out.println(largeur);
	    double  a_screen = hauteur*largeur ;
	    for(int i=0; i<WC.size();i++)
	    {
	    int WCs=Integer.parseInt(WC.get(i)) ;
	    int HCs=Integer.parseInt(HC.get(i)) ;
	   int CArea= WCs*HCs;
	   somme= somme+CArea;
	    }
	    double DM = (double)0.5 * Math.abs((somme/AreaInterface)+(AreaInterface/a_screen));
	    return ((double)((int)( DM*100))/100);}}
		   
		else{
		return 0.0; }
		return 0;

 }
public static double Unity ( String d) throws IOException{
	 ArrayList<Integer> List = new ArrayList<Integer>();
	 int n = Dimension.getComponent(d);
		if ( Dimension.getHeightInterface(d) != null) { 
			 if (Dimension.getWidthInterface(d) != null) {
		  int HI = Integer.parseInt( Dimension.getHeightInterface(d).get(0));
	int WI=Integer.parseInt(Dimension.getWidthInterface(d).get(0)) ;
	double AreaInterface= HI*WI;
	java.awt.Dimension tailleEcran = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
	int hauteur = (int)tailleEcran.getHeight();
	int largeur = (int)tailleEcran.getWidth();
	//System.out.println(hauteur);
	//System.out.println(largeur);
    double  a_screen = hauteur*largeur ;
    
		int somme=0;
		int N_Size=0;
		ArrayList<String> WC=new ArrayList<String>();
		WC=Dimension.getWidth(d) ;
		ArrayList<String> HC=new ArrayList<String>();
		HC=Dimension.getHeight(d) ;
	    for(int i=0; i<WC.size();i++)
	    {
	    int WCs=Integer.parseInt(WC.get(i)) ;
	    int HCs=Integer.parseInt(HC.get(i)) ;
	   int CArea= WCs*HCs;
	   List.add(CArea);
	   somme= somme+CArea;
	    }
	    
		  
		for(int h=0;h<List.size();h++)
    	{for(int g=1;h<List.size();h++)
		   { if(List.get(h).compareTo(List.get(g))!=0)
		   { N_Size++;
		   System.out.println(N_Size);}	  }} 
	    double UM_Space = (Math.abs(N_Size-1))/n ;
	    double UM_Form =(Math.abs(a_screen)+somme)/(2*AreaInterface) ;
	    System.out.println("UM"+UM_Space);
	    System.out.println("UM_form = "+UM_Form);
	    double UM =  1- ((UM_Space+UM_Form)/2);
	    return ((double)((int)( UM*100))/100);}}
		   
		else{
		return 0.0; }
		return 0;

 }

public static double Regularity ( String d) throws IOException{
	 ArrayList<Integer> List = new ArrayList<Integer>();
		if ( Dimension.getHeightInterface(d) != null) { 
			 if (Dimension.getWidthInterface(d) != null) {
				   //******
				 double RM=0;
				 int Nbr_Sp = Dimension.getSapace(d);
				 int  nv= Dimension.getAlignementVertical(d);
				 int  nh= Dimension.getAlignementHorizontal(d);
				 int n = Dimension.getComponent(d);
				 double  RM_Alignment=0;
				 double  RM_Spacing=0;
				 if(n==1)
				 { RM_Alignment = 1;
				   RM_Spacing =1;}
				 else
				 { RM_Alignment = (double) 1-((nv-nh)/(2*n));
				 RM_Spacing=(double) 1-((Nbr_Sp-1)/(2*(n-1)));       }
				 RM =1- (double)((nv+nh+Nbr_Sp)/(3*n));
				 
	        return ((double)((int)( RM*100))/100);}}
		   
		else{
		return 0.0; }
		
		return 0;

}
public static double sorting ( String d) throws IOException{
	double SQM=0;
	int c=Dimension.getComponent(d);
	 ArrayList<Integer> L1=new ArrayList<Integer>();
	    //L1= Dimension.getX(d);
	    ArrayList<String> m1=new ArrayList<String>();
	    ArrayList<String> m2=new ArrayList<String>();
	    ArrayList<String> m3=new ArrayList<String>();
	    ArrayList<String> m4=new ArrayList<String>();
	    ArrayList<String> m5=new ArrayList<String>();
	    ArrayList<String> m6=new ArrayList<String>();
	    ArrayList<Integer> L=new ArrayList<Integer>();
	    m1=Dimension.getX(d);
	    m2=Dimension.getY(d);
	    m3=Dimension.getWidth(d);
	    m4=Dimension.getHeight(d);
	    m5= Dimension.getHeightInterface(d);
	    m6=Dimension.getWidthInterface(d);
	    //System.out.println("la liste:"+m6);
	    int somme =0;
	    int W_UL =0;
	    int W_UR =0;
	    int W_LL =0;
	    int W_LR =0;
	    int V_UL =0;
	    int V_UR =0;
	    int V_LL =0;
	    int V_LR =0;
   for(int i=0;i<c;i++){ 	
	    int X= Integer.parseInt(m1.get(i));
   	    int Y=Integer.parseInt(m2.get(i));
    	int W= Integer.parseInt(m3.get(i));
    	int H=Integer.parseInt(m4.get(i)); 
       	int HI= Integer.parseInt(m5.get(0));
    	int WI= Integer.parseInt(m6.get(0));
	if((X<(WI/2)) && (Y<(HI/2)))
	{   somme+= H*W;
		 W_UL = 4* somme ;		
	}
	else
		if((X>(WI/2)) && (Y<(HI/2)))
		{   somme+= H*W;
			 W_UR = 3* somme ;		
		}
		else
			if((X<(WI/2)) && (Y>(HI/2)))
			{   somme+= H*W;
				 W_LL = 2* somme ;	
				 
			}
			else
				if((X>(WI/2)) && (Y>(HI/2)))
				{   somme+= H*W;
					 W_LR = 1* somme ;	
					
				}
	   //  System.out.println(W_UL+"******"+W_UR+"*********"+W_LL+"******"+W_LR);
	    
		
		}
    L1.add(W_UL);
    L1.add(W_UR);
    L1.add(W_LL);
    L1.add(W_LR);
     System.out.println(L1);
    Integer max =0;
    Integer min =0;
     for(int i=0; i<L1.size();i++)
    	 
      {if (L1.get(0) > L1.get(i)  )
      { V_UL =4;
      max = L1.get(0); } 
     else
    	  if(L1.get(1) >L1.get(i))
    	  { V_UR =4;
    	  max = L1.get(1);}  
    else
    	  if(L1.get(2)>L1.get(i))
    	  { V_LL =4;
    	  max = L1.get(2);}
    else
        	  if(L1.get(3)>L1.get(i))
        	  { V_LR =4;
        	  max = L1.get(3);}
    	  
         if (L1.get(0) <= L1.get(i) )
         { V_UL =1;
           min = L1.get(0);} 
        else
       	 if(L1.get(1)<=L1.get(i))
       	  { V_UR =1;
       	    min = L1.get(1);}
       	 else
       	  if(L1.get(2)<=L1.get(i))
       	  { V_LL =1;
        	min = L1.get(2);}
       	  else
           	  if(L1.get(3)<=L1.get(i))
           	  { V_LR =1;
            	min = L1.get(3);}}
        for(int i=0;i<L1.size();i++)
           	  { 
           		  if(L1.get(0)!=max &&L1.get(0)!=min && L1.get(0)< L1.get(i))
           	  { V_UL = 2;}
           	  
           		if(L1.get(1)!=max &&L1.get(1)!=min && L1.get(1) < L1.get(i))
           		{ V_UR = 2;}
           		else 
               		if(L1.get(2)!=max &&L1.get(2)!=min && L1.get(2)< L1.get(i))
                		   {V_LL = 2;}
               		//else 
                   		if(L1.get(3)!=max &&L1.get(3)!=min && L1.get(3)< L1.get(i))
                   		{ V_LL = 2;}
                   		else
           	    if(L1.get(0)!=max &&L1.get(0)!=min && L1.get(0)>= L1.get(i) )
         	           { V_UL = 3;}
         	   else 
         		if(L1.get(1)!=max &&L1.get(1)!=min && L1.get(1)>= L1.get(i))
         		         { V_UR = 3;}
         		else 
                if(L1.get(2)!=max &&L1.get(2)!=min && L1.get(2)>= L1.get(i))
              		   {V_LL = 3;}
             	else 
                 		if(L1.get(3)!=max &&L1.get(3)!=min && L1.get(3)>= L1.get(i))
                 		{ V_LL = 3;}

           		  }
       		  System.out.println( V_UL +"/////"+ V_UR+"/////"+ V_LL+"////"+V_LR); 
     int som = Math.abs(4 - V_UL)+Math.abs(3-V_UR)+Math.abs(2-V_LL)+Math.abs(1-V_LR);
     //System.out.println("ttttt="+som);
    SQM =  1- (double) som/8 ;
	return ((double)((int)( SQM*100))/100);
	
}
public static double composition ( String d) throws IOException{
	double GM=0;
    int gi=0;
    //String regex = "\\bnew\\(\\s\\)JPanel\\b\\([(]\\)\\([)]\\)\\([;]\\)";
    String regex = "\\bJPanel\\b(\\s)[a-zA-Z0-9_]*\\b(\\s)([=])(\\s)\\bnew\\b(\\s)\\bJPanel\\b([(])([)])([;])";
    String regex2 = "[a-zA-Z0-9_]*";
    
    ArrayList<String> L=new ArrayList<String>();
    ArrayList<String> L1=new ArrayList<String>();
    String tab [];
    Pattern pattern = Pattern.compile(regex);
    Matcher matcher = pattern.matcher((CharSequence) d);
	while (matcher.find()){
	gi++;
	L.add(matcher.group());
	   //System.out.println("Le nobre de groupe:"+gi);
	}
	// JPanel
	for(int i=0;i<L.size();i++)
	{ 
		tab = L.get(i).split("\\s");
		L1.add(tab[1]);	
	}
	//nomrbre des conposant existe dans chaque groupe 
	int nbr=0;
	String regex3 ="([.])\\badd\\b";
	for (int i=0;i<L1.size();i++)
	{ Pattern pat2 = Pattern.compile("\\b"+L1.get(i)+"\\b([.])\\badd\\b");
    Matcher mat2 = pat2.matcher((CharSequence) d);
    while (mat2.find()){
    	nbr++;
      System.out.println("le nombre de composants de  groupe" +i+":"+ nbr++ );
    }
    }
	 //System.out.println(L1);
    GM = (double) nbr/Dimension.getComponent(d);
	//System.out.println("Le nobre de groupe:"+gi);
	return ((double)((int)( GM*100))/100);
	}
public static double repartition ( String d) throws Exception{
	int NbUl = 0;
	int NbUr = 0;
	int NbLl = 0;
	int NbLr = 0;
	int c=Dimension.getComponent(d);
System.out.println(c);
for(int i=0;i<c;i++){
 if (Dimension.getLocation(d)=="UL"){ NbUl++;}
 if (Dimension.getLocation(d)=="UR"){NbUr++;}
 if (Dimension.getLocation(d)=="LL"){NbLl++;}
 if (Dimension.getLocation(d)=="LR"){NbLr++;}	
}
System.out.println( Factorielle(c));
int X= Factorielle(c);
int Y=Factorielle(NbUl);
int Z=Factorielle(NbUr);
int T=Factorielle(NbLl);
int Q=Factorielle(NbLr);
int som=Y+Z+T+Q;
//System.out.println("la somme est:"+som);
double W=(double)(X)/(som);
double Wmax= (double)( (Factorielle(c))/(Math.pow((Factorielle(c/4)), 4)));
double HM=(double)(W/Wmax);
	return HM ;
	
}
public static int Factorielle(int nb) throws Exception {
    
    if (nb == 0) {
        return 1;
    } else {
        return nb * Factorielle(Math.abs(nb - 1));
    }
}

public static double Complexity (String d ) throws IOException{
	int Nvap= Dimension.getAlignementVertical(d);
	//System.out.println("Nvap="+Nvap);
	int Nhap= Dimension.getAlignementHorizontal(d);
	//System.out.println("Nhap="+Nhap);
	int n= Dimension.getComponent(d);
	int som= Nvap+Nhap;
	double SMM=(double)(som)/(2*n);
	return ((double)((int)( SMM*100))/100);
}


	}