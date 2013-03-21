package jmhandin.preprocessing;

import java.awt.Color;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
			// Look for a decimal number
			Pattern my_pattern = Pattern.compile("\\d*[,.]?\\d*");
			Matcher m = my_pattern.matcher(dataSet[i][columnToTrim]);
			if(!dataSet[i][columnToTrim].matches("\\d*[,.]?\\d*"))
			{
				dataSet[i][columnToTrim] = "null";
				continue;
			}
			if(m.find())
			{
				// Use locale that uses either comma or period as decimal character
				NumberFormat nf = NumberFormat.getInstance(Locale.GERMAN);
				double number = 0;
				try 
				{
					// Parse number as double
					number = nf.parse(dataSet[i][columnToTrim].substring(m.start(), m.end())).doubleValue();
				} catch (ParseException e1) 
				{
					e1.printStackTrace();
				}
				
				dataSet[i][columnToTrim] = "" + number;
			}
			else
			{
				// If regex was not found in string, use null
				dataSet[i][columnToTrim] = "null";
			}
			
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
			{ 
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
	
	/**
	 * Checks if the items in the columns are colours. If they are not, value is changed to "null". If they are, the value will be trimmed and put to lower case.
	 * 
	 * @param dataSet The data set to process.
	 * @param column The column to process
	 * @return The updated data set.
	 */
	public static String[][] checkColorColumn(String[][] dataSet, int column)
	{
		for(int i = 0; i < dataSet.length; i++)
		{
			// If value is a colour, trim it to lower case
			if(isColor(dataSet[i][column].toLowerCase().trim()))
			{
				dataSet[i][column] = dataSet[i][column].toLowerCase().trim(); 
			}
			// Else put in "null" as value
			else 
			{
				dataSet[i][column] = "null";
			}
		}
		
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
	
	
}