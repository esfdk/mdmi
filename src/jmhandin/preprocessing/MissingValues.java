package jmhandin.preprocessing;

import java.awt.Color;
import java.util.ArrayList;

import lab_2.Pair;

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
		// Used to keep track of the number of times a value appears in a column
		ArrayList<Pair<String, Integer>> timesSeen = new ArrayList<Pair<String, Integer>>();
		
		for(int i = 0; i < dataSet.length; i++)
		{
			// Finds the index of the pair containing the value in the column. 
			int index = Pair.indexOfAPair(timesSeen, dataSet[i][columnToFill].toLowerCase().trim());
			
			if(index >= 0) // If the value has been seen before, increase the number of times it has been seen. 
			{
				Pair<String, Integer> oldPair = timesSeen.get(index);
				Pair<String, Integer> newPair = new Pair<String, Integer>(oldPair.left, oldPair.right + 1);
				timesSeen.set(index, newPair);
			}
			else // Add the value to list of items seen.
			{
				Pair<String, Integer> newPair = new Pair<String, Integer>(dataSet[i][columnToFill].toLowerCase().trim(), 1);
				timesSeen.add(newPair);
			}
		}
		
		int a = 0;
		String valueToFill = "";
		
		for(Pair<String, Integer> p : timesSeen) // Iterates over the list to find the value that appeared most often.  
		{
			if(p.right > a && !p.left.equals(" ")) // Checks if value is empty and if it appeared the most times.
			{
				valueToFill = p.left;
				a = p.right;
			}
		}
		
		fillInValues(dataSet, columnToFill, valueToFill);
		return dataSet;
	}
	
	/**
	 * Checks if a string is a colour.
	 * 
	 * @param colorAsString The string to be checked for a colour.
	 * @return True if string contains a colour recognised by java else false.
	 */
	private static boolean isColor(String colorAsString)
	{
		if (colorAsString == null) // If string is null, it is not a colour.
		{
			return false;
		}
		try 
		{
			Color.class.getField(colorAsString); // Get colour from value using reflection
			return true;
		} catch (Exception ce) 
		{
			return false;  // If it is not a colour
		}
	}
	
	public static String[][] checkColorColumn(String[][] dataSet, int column)
	{
		// TODO: Implement checkColorColumn
		
		return dataSet;
	}
}