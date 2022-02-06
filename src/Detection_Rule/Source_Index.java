package Detection_Rule;

import java.util.Random;

public class Source_Index {
	static Random number_generator = new Random(); 
	public static  int index_context()
	{
		int source_index0 =  number_generator.nextInt(Input.Context().length);
		return source_index0 ;
	}
	public static int index_ContxtVal()
	{
		 int source_index1=  number_generator.nextInt(Input.ValuesOfContext().length);
		return source_index1 ;
	}
	public static int index_Metrics()
	{
		 int source_index2=  number_generator.nextInt(Input.Metrics().length);
		return source_index2 ;
	}
	public static int index_Operator()
	{
		 int source_index3=  number_generator.nextInt(Input.Operator().length);
		return source_index3 ;
	}
	public static int index_Problem()
	{
		 int target_index=  number_generator.nextInt(Input.Problem().length);
		return target_index ;
	}
}
