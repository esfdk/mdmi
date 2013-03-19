package jmhandin;

import java.io.IOException;
import java.util.Arrays;

import jmhandin.other.CSVFileReader;
import jmhandin.preprocessing.Normalisation;
import jmhandin.preprocessing.WrongFormat;

public class Main {

	/**
	 * Main method for Jakob Melnyk's (jmel@itu.dk) assignment hand in.
	 * 
	 * 
	 * @param args Ignored.
	 */
	public static void main(String args[]) {
		try 
		{
			String[][] data = CSVFileReader.read("Files\\jmhandin\\Data_Mining_Student_DataSet_Spring_2013.csv", false);
			
			/*
			// Print the data set
			for (String[] line : data) 
			{
				System.out.println(Arrays.toString(line));
			}
			*/
			fillValues(data);
			correctFormat(data);
			
			// Print cleaned data set
			for (String[] line : data) 
			{
				System.out.println(Arrays.toString(line));
			}
			/*
			preprocess(data);
			
			// Print the completely pre-processed data set
			for (String[] line : data) 
			{
				System.out.println(Arrays.toString(line));
			}
			*/
			
		} catch (IOException e) 
		{
			System.err.println(e.getLocalizedMessage());
		}
	}
	
	/**
	 * Fills in the missing 
	 * 
	 * @param dataSet The data set to pre-process.
	 * @return
	 */
	public static String[][] fillValues(String[][] dataSet)
	{
		
		return dataSet;
	}
	
	/**
	 * Corrects the format of the values in the  
	 * 
	 * @param dataset The data set to pre-process.
	 * @return
	 */
	public static String[][] correctFormat(String[][] dataSet)
	{
		// Transform special character ½ to .5
		WrongFormat.transformSpecialCharacter(dataSet, 3);
		WrongFormat.transformSpecialCharacter(dataSet, 4);
		WrongFormat.transformSpecialCharacter(dataSet, 11);
		WrongFormat.transformSpecialCharacter(dataSet, 12);
		WrongFormat.transformSpecialCharacter(dataSet, 13);
		
		// 
		
		
		return dataSet;
	}

	/**
	 * 
	 * 
	 * @param dataset The data set to pre-process.
	 * @return 
	 */
	public static String[][] preprocess(String[][] dataSet)
	{
		// Normalises the numerical values of the random numbers to low, med, high
		Normalisation.classifyNumberInt(dataSet, 11);
		Normalisation.classifyNumberInt(dataSet, 12);
		Normalisation.classifyNumberInt(dataSet, 13);
		
		return dataSet;
	}
}
