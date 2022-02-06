package measureMetrics;


import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import parser.bean.ClassBean;


public class Dimension {

	
	public static int getComponent ( String d) throws IOException{
	
		
		        int Nb_Component = 0;
		     String regex1 = "\\bbounds\\b[=][\"][\\[][0][\\,][0][\\]][\\[](((?:\\d*\\.)?\\d*)*)";
			String regex = "\\bbounds\\b[=][\"][\\[]";
			Pattern pattern = Pattern.compile(regex);
			Pattern pattern1 = Pattern.compile(regex1);
			Matcher matcher = pattern.matcher((CharSequence) d);
			Matcher matcher1 = pattern1.matcher((CharSequence) d);
			while (matcher.find()){
				Nb_Component++;
			}
			while (matcher1.find()){
				Nb_Component=Nb_Component-1 ;	
			}
		//  System.out.println( "Le nombre de composant est:"+Nb_Component);
		return Nb_Component;}
	
	public static ArrayList getX ( String d) throws IOException{
		
		
        String m =null;
        ArrayList<String> m2=new ArrayList<String>();
      String[] m1 = null;
      String m3 = "\\bbounds\\b[=][\"][\\[][0][\\,][0]";  
	String regex = "\\bbounds\\b[=][\"][\\[](((?:\\d*\\.)?\\d*)*)";
	Pattern pattern = Pattern.compile(regex);
	//Pattern pattern1 = Pattern.compile(regex1);
	Matcher matcher = pattern.matcher( d);
	//Matcher matcher1 = pattern1.matcher( d);
	
		while (matcher.find()){
			
		m = matcher.group();
		
		if (m!=m3 ){
		 m1= m.split("\\bbounds\\b[=][\"][\\[]");
		 for(int i=0;i<d.length();i++){
				for(int j=i+1 ;j<m1.length;j++){
			//	System.out.println( "Le X de compsant est:" + m1[j]);
				m2.add(m1[j]);}	
			}
			}
		}
return( m2);}
public static ArrayList<String> getY ( String d) throws IOException{
		
		
        String m =null;
        String[] m1 = null;
        String regex1 = "\\bbounds\\b[=][\"][\\[][0][\\,][0]"; 
        ArrayList<String> m2=new ArrayList<String>();
	String regex = "\\bbounds\\b[=][\"][\\[](((?:\\d*\\.)?\\d*)*)[\\,](((?:\\d*\\.)?\\d*)*)";
	Pattern pattern = Pattern.compile(regex);
	//Pattern pattern1 = Pattern.compile(regex1);
	Matcher matcher = pattern.matcher( d);
	//Matcher matcher1 = pattern1.matcher( d);
		while (matcher.find()){
		
		m = matcher.group();
		if (m != regex1){
		 m1= m.split("\\bbounds\\b[=][\"][\\[](((?:\\d*\\.)?\\d*)*)[\\,]");
		 for(int i=0;i<d.length();i++){
				for(int j=i+1 ;j<m1.length;j++){
				//System.out.println( "Le Y de compsant est:" + m1[j]);
				m2.add(m1[j]);}	
			}
			}}
return( m2);}
public static ArrayList<String> getWidth ( String d) throws IOException{
	
	
    String m =null;
    String[] m1 = null;
    ArrayList<String> m2=new ArrayList<String>();
String regex = "\\bbounds\\b[=][\"][\\[](((?:\\d*\\.)?\\d*)*)[\\,](((?:\\d*\\.)?\\d*)*)[\\]][\\[](((?:\\d*\\.)?\\d*)*)";
Pattern pattern = Pattern.compile(regex);
Matcher matcher = pattern.matcher( d);

while (matcher.find()){
	m = matcher.group();
	 m1= m.split("\\bbounds\\b[=][\"][\\[](((?:\\d*\\.)?\\d*)*)[\\,](((?:\\d*\\.)?\\d*)*)[\\]][\\[]");
	
			for(int j=1 ;j<m1.length;j++){
			//System.out.println( "Le width de compsant est:" + m1[j]);
			m2.add(m1[j]);}	
		
		}


return (m2);}
public static ArrayList<String> getHeight (String d) throws IOException{
	
	
    String m =null;
    String[] m1 = null;
    ArrayList<String> m2=new ArrayList<String>();
String regex = "\\bbounds\\b[=][\"][\\[](((?:\\d*\\.)?\\d*)*)[\\,](((?:\\d*\\.)?\\d*)*)[\\]][\\[](((?:\\d*\\.)?\\d*)*)[\\,](((?:\\d*\\.)?\\d*)*)";
Pattern pattern = Pattern.compile(regex);
Matcher matcher = pattern.matcher( d);

	while (matcher.find()){
	
	m = matcher.group();
	 m1= m.split("\\bbounds\\b[=][\"][\\[](((?:\\d*\\.)?\\d*)*)[\\,](((?:\\d*\\.)?\\d*)*)[\\]][\\[](((?:\\d*\\.)?\\d*)*)[\\,]");
	
			for(int j=1 ;j<m1.length;j++){
		//	System.out.println( "Le Height de compsant est:" + m1[j]);
			m2.add(m1[j]);}}



return (m2);}
public static  ArrayList<String> getWidthInterface ( String d) throws IOException{
	
    String m =null;
    String[] m1 = null;
    ArrayList<String> m2=new ArrayList<String>();
  int nb= Dimension.getComponent(d);
//String regex = "\\bsetSize\\b[(](((?:\\d*\\.)?\\d*)*)";
String regex1 = "\\bbounds\\b[=][\"][\\[][0][\\,][0][\\]][\\[](((?:\\d*\\.)?\\d*)*)";

Pattern pattern1 = Pattern.compile(regex1);
Matcher matcher1 = pattern1.matcher( d);
//Pattern pattern = Pattern.compile(regex);
//Matcher matcher = pattern.matcher( d);

		while (matcher1.find()){
	m = matcher1.group();
	
	 m1= m.split("\\bbounds\\b[=][\"][\\[][0][\\,][0][\\]][\\[]");
	 for(int i=0;i<d.length();i++){
			for(int j=i+1 ;j<m1.length;j++){
			//System.out.println( "Le Width de l'interface de compsant est:" + m1[j]);
			m2.add(m1[j]);}	}}
		
		
return (m2);}
public static ArrayList<String> getHeightInterface ( String d) throws IOException{
	
	
    String m =null;
    String[] m1 = null;
    
    ArrayList<String> m2=new ArrayList<String>();
    int nb= Dimension.getComponent(d);
//String regex = "\\bsetSize\\b[(](((?:\\d*\\.)?\\d*)*[,])(\\s)*(((?:\\d*\\.)?\\d*)*)";
String regex1 = "\\bbounds\\b[=][\"][\\[][0][\\,][0][\\]][\\[](((?:\\d*\\.)?\\d*)*)[\\,](((?:\\d*\\.)?\\d*)*)";
Pattern pattern1 = Pattern.compile(regex1);
//Pattern pattern = Pattern.compile(regex);
Matcher matcher1 = pattern1.matcher( d);
//Matcher matcher = pattern.matcher( d);

 while (matcher1.find()){
	m = matcher1.group();
	
	 m1= m.split("\\bbounds\\b[=][\"][\\[][0][\\,][0][\\]][\\[](((?:\\d*\\.)?\\d*)*)[\\,]");
	 for(int i=0;i<d.length();i++){
			for(int j=i+1 ;j<m1.length;j++){
			//System.out.println( "Le height de l'interface compsant est:" + m1[j]);
			m2.add(m1[j]);}	}}
if(m2 != null) {
	 for(int i=0;i<d.length();i++){

		for(int j=i+1 ;j<nb;j++){
		m2.add("0");
	}}}
return (m2);}
public static String getLocation ( String d) throws IOException{
    String m = null ;
    ArrayList<String> m2=new ArrayList<String>();
    ArrayList<String> m1=new ArrayList<String>();
    ArrayList<String> m3=new ArrayList<String>();
    ArrayList<String> m4=new ArrayList<String>();
    ArrayList<String> m5=new ArrayList<String>();
    m3= Dimension.getHeightInterface(d);
    m4=Dimension.getWidthInterface(d);
    m1=Dimension.getX(d);
    m2=Dimension.getY(d);
    int c=Dimension.getComponent(d);
   for(int i=0;i<c;i++){
    	int X= Integer.parseInt(m1.get(i));
    	int Y=Integer.parseInt(m2.get(i));
    	int w=Integer.parseInt(m4.get(0));
    	int H=Integer.parseInt(m3.get(0));
if((X<(w/2)) && (Y>(H/2))){
	//System.out.println("Upper left");
	m="UL";}
if((X<(w/2)) && (Y<(H/2))){
	//System.out.println("lower left");
	m="LL";}
else
if((X>(w/2)) && (Y>(H/2))){
	//System.out.println("upper right");
	m="UR";}
else
if((X>(w/2)) && (Y<(H/2))){
	//System.out.println("Lower right");
	m="LR";
	}	
}
    
   // System.out.println(m);
   // System.out.println("************");
return m;}
public static int getAlignementVertical ( String d) throws IOException{	
    int Nb_Vetrical = 0  ;
    ArrayList<String> m2=new ArrayList<String>();
    ArrayList<String> m1=new ArrayList<String>();
    m2=Dimension.getWidth(d);
    m1=Dimension.getX(d);
    int c=Dimension.getComponent(d);
    for(int i=0;i<c;i++){
    	 for(int j=i+1;j<c;j++){
    	int X1= Integer.parseInt(m1.get(i));
    	int X=Integer.parseInt(m1.get(j));
    	int W1=Integer.parseInt(m2.get(i));
    	int W2=Integer.parseInt(m2.get(j));
   if((X1==X) && (W1==W2)){
	Nb_Vetrical++;}
}}		
return Nb_Vetrical;}

public static int getAlignementHorizontal ( String d) throws IOException{
    int Nb_Horizontal = 0  ;
    ArrayList<String> m2=new ArrayList<String>();
    ArrayList<String> m1=new ArrayList<String>();
    m2=Dimension.getHeight(d);
    m1=Dimension.getY(d);
    int c=Dimension.getComponent(d);
    for(int i=0;i<c;i++){
    	 for(int j=i+1;j<c;j++){
    	int Y1= Integer.parseInt(m1.get(i));
    	int Y=Integer.parseInt(m1.get(j));
    	int H1=Integer.parseInt(m2.get(i));
    	int H2=Integer.parseInt(m2.get(j));
    if((Y1==Y) && (H1==H2)){
	Nb_Horizontal++;}
}}
    return Nb_Horizontal;}
public static int getSapace ( String d) throws IOException{
    int N_space = 0;
    int N_Sp1=0;
    int N_Sp2=0;
     ArrayList<String> L1=new  ArrayList<String>();
	 ArrayList<String> L2=new  ArrayList<String>();
	 ArrayList<String> L3=new  ArrayList<String>();
	 ArrayList<String> L4=new  ArrayList<String>();
	 ArrayList<Integer>L5=new  ArrayList<Integer>();
	 ArrayList<Integer> L6=new  ArrayList<Integer>();
	 L1=Dimension.getHeight(d);
	 L2=Dimension.getY(d);				 
	 L3=Dimension.getWidth(d);
	 L4=Dimension.getX(d);
	 int c=Dimension.getComponent(d);
	for(int i=0;i<=c;i++){
		for(int j=1;i<=c-1;i++){
	     int Y1=Integer.parseInt(L1.get(i));
 	     int Y=Integer.parseInt(L1.get(j));
 	     int H=Integer.parseInt(L2.get(i));
 	     int X1=Integer.parseInt(L1.get(i));
 	     int X=Integer.parseInt(L1.get(j));
 	     int W=Integer.parseInt(L2.get(i));
 	     if ( (X1<X && (Y1+W)>Y) ||  X1==X || (X1<X && (Y+W)>Y1 ) )
 			   {	   
	              int Sp1 = Math.abs(X-(X1+H));
	              L5.add(Sp1);
	              
	              }
		 }}
	for(int i=0;i<=c;i++){
		for(int j=1;i<=c-1;i++){
	     int Y1=Integer.parseInt(L1.get(i));
 	     int Y=Integer.parseInt(L1.get(j));
 	     int H=Integer.parseInt(L2.get(i));
 	     int X1=Integer.parseInt(L1.get(i));
 	     int X=Integer.parseInt(L1.get(j));
 	     int W=Integer.parseInt(L2.get(i));
 	     if((Y1<Y && (X1+H)>X)|| Y1==Y ||(Y<Y1 && (X+H)>X1) )
		   {	   
 	    	   int Sp2 = Math.abs(Y-(Y1+W)) ;
 	    	   //System.out.println("problem"+Sp2);
			     L6.add(Sp2);
			   }
		 }}
	 for(int h=0;h<L5.size();h++)
	      {for(int g=1;h<L5.size();h++)
	          { if(L5.get(h).compareTo(L5.get(g))!=0)
	          { N_Sp1++;
	           //System.out.println("N_Sp1="+N_Sp1);
	          }	  
	          }}
	 for(int h=0;h<L6.size();h++)
         {for(int g=1;h<L6.size();h++)
               { if(L6.get(h).compareTo(L6.get(g))!=0)
               { N_Sp2++;}
                         // System.out.println("N_Sp2="+N_Sp2);
         }}
	 N_space = N_Sp1 + N_Sp2;
	 //System.out.println("the nbr is "+N_space);
	    
    return N_space ;}

	public static String readFile(String nomeFile) throws IOException {
		InputStream is = null;
		InputStreamReader isr = null;

		StringBuffer sb = new StringBuffer();
		char[] buf = new char[1024];
		int len;

		try {
			is = new FileInputStream(nomeFile);
			isr = new InputStreamReader(is);

			while ((len = isr.read(buf)) > 0)
				sb.append(buf, 0, len);

			return sb.toString();
		} finally {
			if (isr != null)
				isr.close();
		}}
	
}
