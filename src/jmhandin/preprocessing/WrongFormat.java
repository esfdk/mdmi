package jmhandin.preprocessing;

import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * 
 * @author Jakob Melnyk jmel@itu.dk (in collaboration with Jacob Grooss jcgr@itu.dk)
 */
public class WrongFormat {

	/**
	 * Finds the first integer value in a string in the set and replaces the string with a string containing only the integer.
	 * 
	 * @param dataSet The set to trim
	 * @param columnToTrim The column to insert values into (0-indexed)
	 * @return The updated data set
	*/
	public static String[][] trimToFirstInt(String[][] dataSet, int columnToTrim)
	{
		for(int i = 0; i < dataSet.length; i++)
		{
			Scanner scan = new Scanner(dataSet[i][columnToTrim]);
			scan.useDelimiter("[^0-9]+");

			try
			{
				dataSet[i][columnToTrim] = "" + scan.nextInt();
			}
			catch (NoSuchElementException e)
			{
				dataSet[i][columnToTrim] = "null";
			}
			
			scan.close();
		}
		return dataSet;
	}
	
	/**
	 * Finds the first decimal value in a string in the set and replaces the string with a string only containing the decimal value.
	 * 
	 * @param dataSet The set to trim
	 * @param columnToTrim The column to insert values into (0-indexed)
	 * @return The updated data set
	*/
	public static String[][] trimToFirstDecimal(String[][] dataSet, int columnToTrim)
	{
		for(int i = 0; i < dataSet.length; i++)
		{
			Scanner scan = new Scanner(dataSet[i][columnToTrim]);
			scan.useDelimiter("[^0-9]+");

			try
			{
				dataSet[i][columnToTrim] = "" + scan.nextDouble();
			}
			catch (NoSuchElementException e)
			{
				dataSet[i][columnToTrim] = "null";
			}
			
			scan.close();
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
	public static String[][] transformString(String[][] dataSet, int columnToTrim, String stringToFind, String valueToFillIn)
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
}