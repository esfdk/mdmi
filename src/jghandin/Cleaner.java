package jghandin;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * Used to clean a array of string array dataset
 * @author Jacob Grooss
 */
public class Cleaner
{	
	
	/**
	 * Finds the first int value in a string in the set and replaces the string with the int.
	 * 
	 * @param dataset The dataset
	 * @param columnToTrim The column to insert values into (0-indexed)
	 * @param defaultValue The value to insert if no int was found
	 * @return The updated dataset
	 */
	public static String[][] trimToFirstInt(String[][] dataset, int columnToTrim, int defaultValue)
	{
		// Iterates over all rows in the dataset
		for(int i = 0; i < dataset.length; i++)
		{
			// Scans the current row, looking for ints.
			Scanner scan = new Scanner(dataset[i][columnToTrim]);
			scan.useDelimiter("[^0-9]+");

			try
			{
				// If an int is found, set the value of the row/columnToTrim 
				// combination to the int 
				dataset[i][columnToTrim] = "" + scan.nextInt();
			}
			catch (NoSuchElementException e)
			{
				// Else set it to the defaultValue
				dataset[i][columnToTrim] = "" + defaultValue;
			}
			
			scan.close();
			
		}
		
		return dataset;
	}
	
	/**
	 * Checks all strings in a column for stringToFind. If it's found, the string is replaced
	 * by valueToFillIn.
	 * 
	 * @param dataset The dataset
	 * @param columnToTrim The column to insert values into (0-indexed)
	 * @param stringToFind The string to find
	 * @param valueToFillIn The value to fill in
	 * @return The updated dataset
	 */
	public static String[][] trimToString(String[][] dataset, int columnToTrim, String stringToFind, String valueToFillIn)
	{
		// Iterates over all rows in the dataset
		for(int i = 0; i < dataset.length; i++)
		{
			// If the row/columnToTrim combination contains the stringToFind value...
			if (dataset[i][columnToTrim].toLowerCase().contains(stringToFind.toLowerCase()))
			{
				// ... replace the cell's value with valueToFillIn
				dataset[i][columnToTrim] = valueToFillIn;
			}
		}
		
		return dataset;
	}
	
	/**
	 * Formates all date of birth to only show the year.
	 * 
	 * @param dataset The dataset
	 * @return The updated dataset
	 */
	private static String[][] formatDateOfBirth(String[][] dataset)
	{
		// The date of birth column is #2, so it's a static variable
		int dobColumn = 2;
		
		// Iterates over every row in the dataset
		for(int i = 0; i < dataset.length; i++)
		{
			// Gets the last two chars of the date string
			String dob = dataset[i][dobColumn];
			dob = dob.substring(dob.length() - 2, dob.length());
			
			try
			{
				// Parse dob to an int
				int birth = Integer.parseInt(dob);
				
				// If the value of birth is higher than 15...
				if(birth > 15)
				{
					// ... attach "19" to it to create the year
					dataset[i][dobColumn] = "19" + birth;
				}
				else
				{
					// .. else attach "20" to it to create the year
					dataset[i][dobColumn] = "20" + birth;
				}
			}
			// If it isn't possible to parse the string to an int...
			catch (NumberFormatException e)
			{
				int birth = 0;
				
				try 
				{
					// Parse the age to an int
					birth = Integer.parseInt(dataset[i][dobColumn - 1]);
				}
				catch (NumberFormatException ee)
				{
					// If it isn't possible to parse the age to an int, set birth to 0.
					birth = 0;
				}
				
				// Set the cell to the current year - age.
				int date = Calendar.getInstance().get(Calendar.YEAR) - birth;
				dataset[i][dobColumn] = "" + date;
			}
		}
		
		return dataset;
	}
	
	/**
	 * Formats the date of birth to show only the year and fills missing
	 * age values based on the date of birth.
	 *  
	 * @param dataset The dataset
	 * @return The updated dataset
	 */
	public static String[][] fixBirthAndAge(String[][] dataset)
	{
		// Calls formatDateOfBirth to ensure we have the right years.
		dataset = formatDateOfBirth(dataset);
		
		// The age is in column #1
		int ageColumn = 1;
		
		// Interate over all the rows in the dataset
		for(int i = 0; i < dataset.length; i++)
		{
			// If an age cell is empty...
			if(dataset[i][ageColumn].equals(" "))
			{
				// ... get the year from the dob cell next to and subtract it from the current year.
				// Normally I'd have to do a try/catch because of parseInt, but with this dataset,
				// I know that an exception won't be thrown.
				int dob = Integer.parseInt(dataset[i][ageColumn + 1]);
				dataset[i][ageColumn] = "" + (Calendar.getInstance().get(Calendar.YEAR) - dob);
			}
		}
		
		return dataset;
	}
	
	/**
	 * Fills up empty values in a specific column of a dataset
	 * 
	 * @param dataset The dataset
	 * @param columnToFill The column to insert values into (0-indexed)
	 * @param valueToFill The value to insert
	 * @return The updated dataset
	 */
	public static String[][] fillInValues(String[][] dataset, int columnToFill, String valueToFill)
	{
		// Iterates over all rows in the dataset
		for(int i = 0; i < dataset.length; i++)
		{
			// Is the cell is empty...
			if(dataset[i][columnToFill].equals(" "))
			{
				// ... insert valueToFill
				dataset[i][columnToFill] = valueToFill;
			}
		}
		return dataset;
	}
	
	/**
	 * Finds the median value of a column and replaces empty values in the column with the median value
	 * 
	 * @param dataset The dataset
	 * @param columnToFill The column to fill
	 * @return The updated dataset
	 */
	public static String[][] fillInMedian(String[][] dataset, int columnToFill)
	{
		// Used to keep track of the number of times a value appears in a column
		ArrayList<Pair<String, Integer>> timesSeen = new ArrayList<Pair<String, Integer>>();
		
		// Iterates over all rows in the dataset
		for(int i = 0; i < dataset.length; i++)
		{
			// Attempts to find the index of a pair
			int index = Pair.indexOfAPair(timesSeen, dataset[i][columnToFill].toLowerCase().trim());
			
			// If an index was found...
			if(index >= 0)
			{
				// ... update the pair to reflect that it's value was seen again
				Pair<String, Integer> oldPair = timesSeen.get(index);
				Pair<String, Integer> newPair = new Pair<String, Integer>(oldPair.left, oldPair.right + 1);
				timesSeen.set(index, newPair);
			}
			else
			{
				// ... else make a new pair.
				Pair<String, Integer> newPair = new Pair<String, Integer>(dataset[i][columnToFill].toLowerCase().trim(), 1);
				timesSeen.add(newPair);
			}
		}
		
		int a = 0;
		String valueToFill = "";
		
		// Iterates over all the pairs created
		for(Pair<String, Integer> p : timesSeen) 
		{
			// If the value is not empty and if it has been seen more times than a...
			if(p.right > a && !p.left.equals(" "))
			{
				// ... set valueToFill to the pair's left value and a to the pair's right value.
				valueToFill = p.left;
				a = p.right;
			}
		}
		
		// Calls fillInValues with the found median
		fillInValues(dataset, columnToFill, valueToFill);
		
		return dataset;
	}
}