package jmhandin.preprocessing;

import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * Contains static methods for correcting the format of values in a data set.
 * 
 * @author Jakob Melnyk, jmel@itu.dk (in collaboration with Jacob Grooss, jcgr@itu.dk)
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
			scan.useDelimiter("[^0-9]+[,.]");

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
	 * Transforms special characters into values readable by the algorithms.
	 * 
	 * @param dataSet The set to trim
	 * @param columnToTransform The column to transform values in (0-indexed)
	 * @return The updated data set
	 */
	public static String[][] transformSpecialCharacter(String[][] dataSet, int columnToTransform)
	{
		for(int i = 0; i < dataSet.length; i++)
		{
			int index;
			
			if ((index = dataSet[i][columnToTransform].indexOf("½")) != -1)
			{ System.out.println(index);
				if (index == 0)
				{
					dataSet[i][columnToTransform] = "0.5";
				}
				else
				{
					dataSet[i][columnToTransform] = dataSet[i][columnToTransform].toLowerCase().substring(0, index) + ".5";
				}
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
	 * @return The updated data set
	 */
	public static String[][] transformString(String[][] dataSet, int columnToTransform, String stringToFind, String valueToFillIn)
	{
		for(int i = 0; i < dataSet.length; i++)
		{
			if (dataSet[i][columnToTransform].toLowerCase().contains(stringToFind.toLowerCase()))
			{
				dataSet[i][columnToTransform] = valueToFillIn;
			}
		}
		return dataSet;
	}
}