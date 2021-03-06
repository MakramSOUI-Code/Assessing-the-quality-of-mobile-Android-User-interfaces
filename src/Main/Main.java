package Main;



import java.io.BufferedInputStream;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;













import measureMetrics.CMetrics;
import measureMetrics.Dimension;
import parser.bean.ClassBean;
import bean.AnalyzedClass;



public class Main {

	
	private static String experimentationPath = "C:\\Users\\makram souii\\Desktop\\Interview\\data";
	private static String outputFile = experimentationPath + "/Data"  + ".csv";
	
	@SuppressWarnings("unused")
	public static void main(String[] args) throws Exception {
		File output = new File(outputFile);
		output.createNewFile();
		PrintWriter pw = new PrintWriter(output);
	pw.println("Nom_Interface; X;Y;Width;Height;WInterface;HInterface;Nb; Density;Location;Unity;Regularity;Sequence;Grouping;Simplicity");
		
		File errorLog = new File(experimentationPath + "/errorLog.txt");
		errorLog.createNewFile();
		PrintWriter pwError = new PrintWriter(errorLog);
		
		
		File sourceCodeFolder = new File(experimentationPath + "/" + "sourceCode/" );

		String[] classesFile = sourceCodeFolder.list();
		int j=0;
		ArrayList<String> m3=new ArrayList<String>();
		ArrayList<String> m4=new ArrayList<String>();
		 String m1=null;
		String m2=null;
		AnalyzedClass analyzed = new AnalyzedClass();
		AnalyzedClass tmp = new AnalyzedClass();
	
		for (int i=0; i<classesFile.length; i++){
			System.out.println(classesFile[i] );
			       BufferedInputStream in = new BufferedInputStream(new FileInputStream(sourceCodeFolder.getAbsolutePath() + "/" + classesFile[i]));
			       StringWriter out = new StringWriter();
			       int b;
			       while ((b=in.read()) != -1)
			           out.write(b);
			       out.flush();
			       out.close();
			       in.close();
			      String Contenu= out.toString();
			tmp.Nom_Interface =classesFile[i] ;
			tmp.Nombre_Composant=  Dimension.getComponent( Contenu);
			tmp.WidthInterface= (String) Dimension.getWidthInterface( Contenu).get(0);
			tmp.HeightInterface=(String) Dimension.getHeightInterface( Contenu).get(0);
			tmp.DM=CMetrics.Density(Contenu);
			tmp.UM=CMetrics.Unity(Contenu);
			tmp.RM=CMetrics.Regularity(Contenu);
			tmp.SQM=CMetrics.sorting(Contenu);
			tmp.GM=CMetrics.composition(Contenu);
			//tmp.HM=CMetrics.Homogeneity(Contenu);
			tmp.SMM=CMetrics. Simplicity(Contenu);
			tmp.L=Dimension.getLocation(Contenu);
			System.out.println("Density: "+tmp.DM);
			System.out.println("Unity: "+tmp.UM);
			System.out.println("Regularity: "+tmp.RM);
			System.out.println("sorting: "+tmp.SQM);
			System.out.println("composition: "+tmp.GM);
			//System.out.println("Homogeneity: "+tmp.HM);
			System.out.println("Simplicity: "+tmp.SMM);
			j=0;
			while(j<tmp.Nombre_Composant){
				
		    tmp.X=(String) Dimension.getX( Contenu).get(j);
			tmp.Y=(String)Dimension.getY( Contenu).get(j);
			tmp.Width=(String)Dimension.getWidth(Contenu).get(j);
			tmp.Height=(String)Dimension.getHeight(Contenu).get(j);
			pw.println(tmp.Nom_Interface+";"+ tmp.X + ";"+ tmp.Y+ ";"+tmp.Width +";"+ tmp.Height+";"+tmp.WidthInterface+";"+tmp.HeightInterface+";"+tmp.Nombre_Composant+";"+tmp.DM+";"+tmp.L+";"+tmp.UM+";"+tmp.RM+";"+tmp.SQM+";"+tmp.GM+";"+tmp.SMM );
			j++;
			}
			}
pwError.close();
pw.close();
	}			
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
	/*private static ClassBean getClassBeanByClassName(String className, Vector<ClassBean> system){
		ClassBean result = null;
		className += ";";	
		for(ClassBean c: system){		
				result = c;
				break;
			}
		return result;
	}*/
}
