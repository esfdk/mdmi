package jmhandin.algorithms;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import jmhandin.other.Attributes;
import jmhandin.other.Pair;

public class KNN {

	public static String guessProgrammingLanguage(String[] tuple, String[][] trainingData, int k)
	{
		// Get nearest languages 
		List<String[]> kNearestLanguages = getKNearest(tuple, trainingData, k);
		
		HashMap<String[], Integer> favourite = new HashMap<String[], Integer>();
		
		// Go through the nearest languages and count the number of times they appear
		for(String[] sa : kNearestLanguages)
		{
			if(favourite.containsKey(sa)) favourite.put(sa, favourite.get(sa) + 1);
			else favourite.put(sa, 1);
		}
		
		// Find the programming language with most appearances.
		String programmingLanguage = "";
		int timesSeen = 0;
		for(String[] sa : favourite.keySet())
		{
			if(favourite.get(sa) > timesSeen)
			{
				timesSeen = favourite.get(sa);
				programmingLanguage = sa[Attributes.P_LANG.ordinal()];
			}
		}
		
		return programmingLanguage;
	}
	
	private static List<String[]> getKNearest(String[] tuple, String[][] trainingData, int k)
	{	
		Pair<String[], Double>[] nearestLanguages = new Pair[k];
		
		for(int i = 0; i < k; i++)
		{
			String[] b = trainingData[i];
			double d = tupleDistance(tuple, b);
			nearestLanguages[i] = new Pair<String[], Double>(b, d);
		}
		
		for(int i = k; i < trainingData.length; i++)
		{
			String[] b = trainingData[i];
			double d = tupleDistance(tuple, b);
			
			int furthestTuple = 0;
			
			for(int j = 0; j < k; j++)
			{
				if(nearestLanguages[j].y > nearestLanguages[furthestTuple].y){
					furthestTuple = j;
				}
			}
			
			if(nearestLanguages[furthestTuple].y > d)
			{
				nearestLanguages[furthestTuple] = new Pair<String[], Double>(b, d);
			}
		}
		
		List<String[]> nl = new ArrayList<String[]>();
		
		for(Pair<String[], Double> p : nearestLanguages)
		{
			nl.add(p.x);
		}
		
		return nl;
	}
	
	/**
	 * Takes the Euclidean distance of a and b age, uni_study, p_skill and english values and then adds 1.0 to it if favourite OS is not the same.
	 * 
	 * @param a Tuple a
	 * @param b Tuple b
	 * @return Distance between two elements.
	 */
	private static double tupleDistance(String[] a, String[] b)
	{
		double distance = 0;
		
		distance +=  Math.pow(Double.parseDouble(a[Attributes.AGE.ordinal()]) - Double.parseDouble(b[Attributes.AGE.ordinal()]), 2);
		distance +=  Math.pow(Double.parseDouble(a[Attributes.UNI_STUDY.ordinal()]) - Double.parseDouble(b[Attributes.UNI_STUDY.ordinal()]), 2);
		distance +=  Math.pow(Double.parseDouble(a[Attributes.P_SKILL.ordinal()]) - Double.parseDouble(b[Attributes.P_SKILL.ordinal()]), 2);
		distance +=  Math.pow(Double.parseDouble(a[Attributes.ENGLISH.ordinal()]) - Double.parseDouble(b[Attributes.ENGLISH.ordinal()]), 2);
		
		distance = Math.sqrt(distance);
		
		if(!a[Attributes.OS.ordinal()].equals(b[Attributes.OS.ordinal()]))
		{
			distance += 1.0;
		}
		
		return distance;
	}
}
