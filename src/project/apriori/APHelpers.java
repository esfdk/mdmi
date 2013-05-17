package project.apriori;

import java.util.ArrayList;
import java.util.List;

public class APHelpers {

	/**
	 * If there are an equal amount of items in both sets and every string in a is contained in b, the sets are equal.
	 * 
	 * @param a First list.
	 * @param b Second list.
	 * @return True if sets are equal, false if not.
	 */
	protected static boolean areSetsEqual(List<String> a, List<String> b)
	{
		// If size is not equal, sets cannot be equal.
		if(a.size() != b.size()) return false; 
		
		// If every element of a is contained in b, then sets are equal.
		boolean equal = true;
		for(String s : a)
		{
			if(!b.contains(s)) 
			{
				equal = false; 
				break;
			}
		}
		return equal;
	}
	
	public static List<List<String>> convertDataSet(String[][] dataSet, int[] values)
	{
		 List<List<String>> convertedDataSet = new ArrayList<List<String>>();
		 
		 /* For every tuple in the data set, 
		  * create a new tuple containing only the values for the 4 attributes:
		  * Random_1_10, Random_0_1_A, Random_0_1_B & Colour
		  */
		 for(String[] tuple : dataSet)
		 {
			 List<String> newTuple = new ArrayList<String>();
			 for(int i = 0; i < values.length; i++)
			 {
				 newTuple.add(tuple[values[i]]); 
			 }
			 
			 convertedDataSet.add(newTuple);
		 }
		
		 return convertedDataSet;	
	}
}
