package jmhandin.preprocessing;

import java.util.HashMap;

/**
 * Contains static methods for filling in missing values in different ways.
 * 
 * @author Jakob Melnyk, jmel@itu.dk (in collaboration with Jacob Grooss, jcgr@itu.dk)
 */
public class MissingValues {
	
	/**
	 * Fills up empty values in a specific column of a data set
	 * 
	 * @param dataSet The array of string arrays holding the data
	 * @param columnToFill The column to insert values into (0-indexed)
	 * @param valueToFill The value to insert
	 * @return The updated data set
	 */
	public static String[][] fillInValues(String[][] dataSet, int columnToFill, String valueToFill)
	{
		for(int i = 0; i < dataSet.length; i++)
		{
			if(dataSet[i][columnToFill].equals(" ")) // If value is empty
			{
				dataSet[i][columnToFill] = valueToFill; // Insert value
			}
		}
		return dataSet;
	}
	
	/**
	 * Finds the median value of a column and replaces empty values in the column with the median value
	 * 
	 * @param dataSet Data set to fill
	 * @param columnToFill 
	 * @return The updated data set
	 */
	public static String[][] fillInMedian(String[][] dataSet, int columnToFill)
	{
		// Hash map to keep track of the number of times an item has been seen
		HashMap<String, Integer> timesSeen = new HashMap<String, Integer>(); 
		
		// Iterate over the column in the set to see the number of times an item is seen
		for(int i = 0; i < dataSet.length; i++)
		{
			// Lower case and trim to make sure the "same" values are considered the same
			String key = dataSet[i][columnToFill].toLowerCase().trim();
			
			// Do not fill in missing with empty value
			if(key.equals(" ") || key.equals("")) continue;
			
			// If key has already been seen, add 1 to the value of the key otherwise put it into the map
			if(timesSeen.containsKey(key))
			{
				timesSeen.put(key, timesSeen.get(key) + 1);
			}
			else
			{
				timesSeen.put(key, 1);
			}
		}
		
		int mostSeen = 0;
		String valueToFill = "";
		
		// Iterate over the keys in the map 
		for(String currentKey : timesSeen.keySet())
		{
			// If the key is seen more time than the previously most seen, update mostSeen and valueToFill
			if(timesSeen.get(currentKey) > mostSeen)
			{
				valueToFill = currentKey;
				mostSeen = timesSeen.get(currentKey);
			}
		}
				
		// Fills in the newly found median
		fillInValues(dataSet, columnToFill, valueToFill);
		return dataSet;
	}
}