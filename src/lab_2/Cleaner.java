package lab_2;

import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * Used to clean a array of string array dataset
 * @author Jakob Melnyk & Jacob Grooss
 */
public class Cleaner
{	
	
	/**
	 * Finds the first int value in a string in the set and replaces the string with the int.
	 * 
	 * @param dataSet The set to trim
	 * @param columnToTrim The column to insert values into (0-indexed)
	 * @return The updated dataset
	 */
	public static String[][] trimToFirstInt(String[][] dataSet, int columnToTrim)
	{
		for(int i = 0; i < dataSet.length; i++)
		{
			Scanner scan = new Scanner(dataSet[i][columnToTrim]).useDelimiter("[^0-9]+");

			try
			{
				dataSet[i][columnToTrim] = "" + scan.nextInt();
			}
			catch (NoSuchElementException e)
			{
				dataSet[i][columnToTrim] = "-1";
			}
			
		}
		return dataSet;
	}
	
	/**
	 * Checks all strings in a column for stringToFind. If it's found, the string is replaced
	 * by valueToFillIn.
	 * 
	 * @param dataSet The data set
	 * @param columnToTrim The column to insert values into (0-indexed)
	 * @param stringToFind The string to find
	 * @param valueToFillIn The value to fill in
	 * @return The updated dataset
	 */
	public static String[][] trimToString(String[][] dataSet, int columnToTrim, String stringToFind, String valueToFillIn)
	{
		for(int i = 0; i < dataSet.length; i++)
		{
			if (dataSet[i][columnToTrim].toLowerCase().contains(stringToFind.toLowerCase()))
			{
				dataSet[i][columnToTrim] = valueToFillIn;
			}
		}
		return dataSet;
	}
	
	/**
	 * Fills up empty values in a specific column of a dataset
	 * 
	 * @param dataSet The array of string arrays holding the data
	 * @param columnToFill The column to insert values into (0-indexed)
	 * @param valueToFill The value to insert
	 * @return The updated dataset
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
	 * @param dataSet Dataset to fill
	 * @param columnToFill 
	 * @return The updated dataset
	 */
	public static String[][] fillInMedian(String[][] dataSet, int columnToFill)
	{
		// Used to keep track of the number of times a value appears in a column
		ArrayList<Pair<String, Integer>> timesSeen = new ArrayList<Pair<String, Integer>>();
		
		for(int i = 0; i < dataSet.length; i++)
		{
			int index = Pair.indexOfAPair(timesSeen, dataSet[i][columnToFill].toLowerCase().trim());
			
			if(index >= 0)
			{
				Pair<String, Integer> oldPair = timesSeen.get(index);
				Pair<String, Integer> newPair = new Pair<String, Integer>(oldPair.left, oldPair.right + 1);
				timesSeen.set(index, newPair);
			}
			else
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
}