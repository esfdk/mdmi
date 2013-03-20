package jmhandin.algorithms;

import java.util.*;

public class APriori {
	
	private static void aprioriAlgorithm(String[][] dataSet, int min_support)
	{
		HashMap<Integer, List<List<String>>> frequentItemSets= new HashMap<Integer, List<List<String>>>();
		
		List<List<String>> convertedDataSet = convertDataSet(dataSet);
		
		// Step (1)
		frequentItemSets.put(1, findFrequentOneItemsets(convertedDataSet));
		
		// Step (2)
		for(int k = 2; frequentItemSets.get(k - 1).size() != 0; k++)
		{
			// Step (3)
			List<List<String>> candidateSet = aprioriGen(frequentItemSets.get(k-1)); 
			
			HashMap<List<String>, Integer> candidateTimesSeen = new HashMap<List<String>, Integer>();
			
			// Step (4)
			for(int i = 0; i < convertedDataSet.size(); i++)
			{
				// Step (5)
				List<List<String>> candidateSubsets = subset(candidateSet, convertedDataSet.get(0));
				
				// Step (6)
				for(List<String> candidateSubset : candidateSubsets)
				{
					// Step (7)
					if(candidateTimesSeen.containsKey(candidateSubset))
					{
						candidateTimesSeen.put(candidateSubset, candidateTimesSeen.get(candidateSubset) + 1);						
					}
					else
					{
						candidateTimesSeen.put(candidateSubset, 0);
					}
				}
			}
			
			// Step (9)
			for()
			{
				frequentItemSets.put(k, );
			}
		}
	}
	
	private static List<List<String>> aprioriGen(List<List<String>> frequentItemSet)
	{
		return frequentItemSet;
	}
	
	private static List<List<String>> findFrequentOneItemsets(List<List<String>> dataSet) 
	{
		
		return new ArrayList<List<String>>();
	}
	
	private static boolean hasInfrequentSubset()
	{
		return true;
	}
	
	private static List<List<String>> subset(List<List<String>> candidateSet, List<String> tuple)
	{
		return candidateSet;
	}
	
	private static List<List<String>> convertDataSet(String[][] dataSet)
	{
		return new ArrayList<List<String>>();
	}
}
