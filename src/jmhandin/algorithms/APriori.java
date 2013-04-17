package jmhandin.algorithms;

import java.util.*;

import jmhandin.other.Attributes;

/** 
 * A Priori algorithm for frequent patterns in the 3 random numbers and favourite colour. 
 *  
 * @author Jakob Melnyk, jmel@itu.dk
 */
public class APriori {
	
	/**
	 * A Priori to find frequent patterns in random numbers and colour.
	 * 
	 * @param dataSet The data set to find frequent patterns in. 
	 * @param min_support The minimum support of the pattern.
	 * @return The frequent patterns of k sizes - key is 1...4
	 */
	public static HashMap<Integer, List<List<String>>> aprioriAlgorithm(String[][] dataSet, int min_support)
	{
		HashMap<Integer, List<List<String>>> frequentItemSets = new HashMap<Integer, List<List<String>>>();
		
		List<List<String>> convertedDataSet = convertDataSet(dataSet);
		
		// Step (1)
		frequentItemSets.put(1, findFrequentOneItemsets(convertedDataSet, min_support));
		
		
		// Step (2) - as long as there are k-1 frequent sets, attempt to find a k size set.
		for(int k = 2; (frequentItemSets.get(k - 1).size() != 0); k++)
		{
			// Step (3) - generate set of possibly frequent candidates
			List<List<String>> candidateSet = aprioriGen(frequentItemSets.get(k-1)); 
			
			HashMap<List<String>, Integer> candidateTimesSeen = new HashMap<List<String>, Integer>();
			
			// Step (4)
			for(int i = 0; i < convertedDataSet.size(); i++)
			{
				// Step (5) - generate candidate subsets
				List<List<String>> candidateSubsets = subset(candidateSet, convertedDataSet.get(i));
				
				// Step (6) - run through all candidate subsets
				for(List<String> candidateSubset : candidateSubsets)
				{
					// Step (7) - count number of times a candidate has been seen
					if(candidateTimesSeen.containsKey(candidateSubset))
					{
						candidateTimesSeen.put(candidateSubset, candidateTimesSeen.get(candidateSubset) + 1);						
					}
					else
					{
						candidateTimesSeen.put(candidateSubset, 1);
					}
				}
			}
			
			List<List<String>> kFrequentSet = new ArrayList<List<String>>();
			
			// Step (9) - every candidate set that has been seen more times than the minimum support is added to kfrequent set
			for(List<String> candidate : candidateTimesSeen.keySet())
			{
				if(candidateTimesSeen.get(candidate) >= min_support)
				{
					kFrequentSet.add(candidate);
				}
			}
			
			frequentItemSets.put(k, kFrequentSet);
		}
		
		return frequentItemSets;
	}
	
	/**
	 * Generates a list of supersets from the frequent item sets that contain no infrequent subsets
	 * 
	 * @param frequentItemSet All the item sets in this set must be of the same size.
	 * @return The list of generated sets that contain no infrequent subsets
	 */
	private static List<List<String>> aprioriGen(List<List<String>> frequentItemSet)
	{
		List<List<String>> cK = new ArrayList<List<String>>();
		// Step (1) - every l1 set
		for(List<String> lone : frequentItemSet)
		{
			// Step (2) - every l2 set
			for(List<String> ltwo : frequentItemSet)
			{
				List<String> c = new ArrayList<String>(); 
				
				// Step (3a)
				boolean shouldJoin = true;
				
				// If size of frequent item set is 1, then join as long as items are not equal
				if(lone.size() == 1 && lone.get(0) != ltwo.get(0))
				{
					c.add(lone.get(0));
					c.add(ltwo.get(0));
				}
				// If not, check if all items are equal except last item in set
				else
				{
					for(int i = 0; i < ltwo.size() - 1; i ++)
					{
						if(!lone.get(i).equals(ltwo.get(i)))
						{
							shouldJoin = false; break;
						}
					}
					
					// Step (3b)
					// Join sets if they should be joined
					int size = lone.size();
					if(shouldJoin && !(lone.get(size - 1).equals(ltwo.get(size - 1))))
					{
						// Step (4)
						for(int j = 0; j < size; j++)
						{
							c.add(lone.get(j));
						}
						
						c.add(ltwo.get(size-1));
					}
				}
				
				// If set is previously seen in the candidate sets, then it is duplicate and should not be copied.
				boolean cIsDuplicate = false;
				for(List<String> set : cK)
				{
					// Step (5)
					if(areSetsEqual(c, set))
					{
						cIsDuplicate = true;
						break;
					}
				}
				
				// Step (6) omitted
				// Step (7) - if not a duplicate, does not have infrequent subsets and is not of size 0 then add to candidate sets
				if(!cIsDuplicate && !hasInfrequentSubset(c, frequentItemSet) && c.size() != 0)
				{
					
					cK.add(c);
				}
			}
		}
		
		// Step (9)
		return cK;
	}
	
	/**
	 * Finds the set of frequent one items.
	 * 
	 * @param dataSet The data set to look through.
	 * @param min_support The minimum number of instances that must be present for the item to be frequent.
	 * @return The list of frequent one item sets
	 */
	private static List<List<String>> findFrequentOneItemsets(List<List<String>> dataSet, int min_support) 
	{
		HashMap<String, Integer> timesSeen = new HashMap<String, Integer>();
		
		for(List<String> tuple : dataSet)
		{
			/* 
			 * Go through the 4 attributes: random number 1-10, random number 0-1, random number 0-1-again and colour
			 * Count the number of times they have been seen.
			 */
			
			// Random_number_1_10
			if(timesSeen.containsKey(tuple.get(0)))
			{
				timesSeen.put(tuple.get(0), timesSeen.get(tuple.get(0)) + 1); 
			}
			else
			{
				timesSeen.put(tuple.get(0), 1);
			}
			// Random_number_0_1A
			if(timesSeen.containsKey(tuple.get(1)))
			{
				timesSeen.put(tuple.get(1), timesSeen.get(tuple.get(1)) + 1); 
			}
			else
			{
				timesSeen.put(tuple.get(1), 1);
			}
			// Random_number_0_1B
			if(timesSeen.containsKey(tuple.get(2)))
			{
				timesSeen.put(tuple.get(2), timesSeen.get(tuple.get(2)) + 1); 
			}
			else
			{
				timesSeen.put(tuple.get(2), 1);
			}
			// Colour
			if(timesSeen.containsKey(tuple.get(3)))
			{
				timesSeen.put(tuple.get(3), timesSeen.get(tuple.get(3)) + 1); 
			}
			else
			{
				timesSeen.put(tuple.get(3), 1);
			}
			
		}
		
		// Every item seen more than the minimum support is added to the one item sets.
		List<List<String>> oneItemSets = new ArrayList<List<String>>();
		for(String item : timesSeen.keySet())
		{
			if(timesSeen.get(item) >= min_support) 
			{
				ArrayList<String> al = new ArrayList<String>();
				al.add(item);
				oneItemSets.add(al);
			}
		}
		
		return oneItemSets;
	}

	/**
	 * Checks if a a set has a subset that is not contained in the frequent k-1 size subsets
	 * 
	 * @param kCandidateSet The set to check.
	 * @param frequentKMinusOneSet The frequent k-1 subsets
	 * @return True if there is a infrequent subset, else false.
	 */
	private static boolean hasInfrequentSubset(List<String> kCandidateSet, List<List<String>> frequentKMinusOneSet)
	{
		// Step (1) - Go through all k-1 subsets of the kCandidateSet
		for(List<String> s : kMinusOneSets(kCandidateSet))
		{
			// Step (2) - If a set is not in set of frequent k-1 sets, then the candidate set has an infrequent subset.
			if(!isFrequentSet(s, frequentKMinusOneSet))
			{
				// Step (3)
				return true;
			}
		}
		// Step (4)
		return false;
	}
	
	/**
	 * Finds the list of sets in the candidate set that are in the tuple.
	 * 
	 * @param candidateSet The set of item sets to compare to the tuple.
	 * @param tuple The tuple from the database
	 * @return The list of sets that are in the tuple
	 */
	private static List<List<String>> subset(List<List<String>> candidateSet, List<String> tuple)
	{
		List<List<String>> subset = new ArrayList<List<String>>();
		
		for(List<String> ct : candidateSet)
		{
			// If every item of the candidate is contained in the tuple, then it is a subset
			boolean isSubset = true;
			for(String c : ct)
			{
				if(!tuple.contains(c))
				{
					isSubset = false; 
					break;
				}
			}
			
			if(isSubset)
			{
				subset.add(ct);
			}
		}
		
		return subset;
	}
	
	/** 
	 * Removes the elements not to be considered by the A Priori algorithm and 
	 * creates a new data set containing only the random numbers and the favourite colour.
	 * 
	 * @param dataSet The original data set filled from the "Data Mining, Spring 2013:Data Collection Questionnaire"
	 * @return A converted data set containing only the values used to find a frequent pattern in random numbers and colour
	 */
	private static List<List<String>> convertDataSet(String[][] dataSet)
	{
		 List<List<String>> convertedDataSet = new ArrayList<List<String>>();
		 
		 /* For every tuple in the data set, 
		  * create a new tuple containing only the values for the 4 attributes:
		  * Random_1_10, Random_0_1_A, Random_0_1_B & Colour
		  */
		 for(String[] tuple : dataSet)
		 {
			 List<String> newTuple = new ArrayList<String>();
			 newTuple.add(tuple[Attributes.RAND_1_10.ordinal()]);
			 newTuple.add(tuple[Attributes.RAND_0_1_A.ordinal()]);
			 newTuple.add(tuple[Attributes.RAND_0_1_B.ordinal()]);
			 newTuple.add(tuple[Attributes.COLOUR.ordinal()]);
			 convertedDataSet.add(newTuple);
		 }
		
		 return convertedDataSet;	
	}

	/**
	 * Finds the set of k-1 item sets.
	 * 
	 * @param kSet The k-set of items to get k-1 subsets from.
	 * @return The list of k-1 sets
	 */
	private static List<List<String>> kMinusOneSets(List<String> kSet)
	{
		List<List<String>> kMinusOneSets = new ArrayList<List<String>>();
		
		if(kSet.size() == 4)
		{
			kMinusOneSets.add(kSet.subList(0, 3)); // Three-set of item 1, item 2 and item 3
			kMinusOneSets.add(kSet.subList(1, 4)); // Three-set of item 2, item 3 and item 4
			List<String> oneTwoFour = new ArrayList<String>();
			oneTwoFour.add(kSet.get(0));
			oneTwoFour.add(kSet.get(1));
			oneTwoFour.add(kSet.get(3));
			kMinusOneSets.add(oneTwoFour); // Three-set of item 1, item 2 and item 4
			
			List<String> oneThreeFour = new ArrayList<String>();
			oneThreeFour.add(kSet.get(0));
			oneThreeFour.add(kSet.get(2));
			oneThreeFour.add(kSet.get(3));
			kMinusOneSets.add(oneThreeFour); // Three-set of item 1, item 2 and item 3
		}
		else if(kSet.size() == 3)
		{
			kMinusOneSets.add(kSet.subList(0, 2)); // Two-set of item 1 and item 2
			kMinusOneSets.add(kSet.subList(1, 3)); // Two-set of item 2 and item 3
			
			List<String> oneThree = new ArrayList<String>();
			oneThree.add(kSet.get(0));
			oneThree.add(kSet.get(2));
			kMinusOneSets.add(oneThree); // Two-set of item 1 and item 3
		}
		else if(kSet.size() == 2)
		{
			kMinusOneSets.add(kSet.subList(0, 1)); // Item 1
			kMinusOneSets.add(kSet.subList(1, 2)); // Item 2
		}
				
		return kMinusOneSets;
	}

	/**
	 * Checks if a candidate is contained in the list given.
	 * 
	 * @param candidate The list to look for
	 * @param frequentItemSets The list of sets to look through.
	 * @return True if contained, otherwise false.
	 */
	private static boolean isFrequentSet(List<String> candidate, List<List<String>> frequentItemSets)
	{
		for(List<String> frequentItemSet : frequentItemSets)
		{
			// If set is equal to one frequent item set, then it is frequent.
			if(areSetsEqual(candidate, frequentItemSet))
			{
				return true;
			}
		}
		
		return false;
	}
	
	/**
	 * If there are an equal amount of items in both sets and every string in a is contained in b, the sets are equal.
	 * 
	 * @param a First list.
	 * @param b Second list.
	 * @return True if sets are equal, false if not.
	 */
	private static boolean areSetsEqual(List<String> a, List<String> b)
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
}
