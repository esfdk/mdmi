package jmhandin;

import java.io.IOException;
import java.util.*;

import jmhandin.algorithms.APriori;
import jmhandin.other.Attributes;
import jmhandin.other.CSVFileReader;
import jmhandin.preprocessing.MissingValues;
import jmhandin.preprocessing.Normalisation;
import jmhandin.preprocessing.WrongFormat;

public class Main {

	/**
	 * Main method for Jakob Melnyk's (jmel@itu.dk) assignment hand in.
	 * 
	 * @param args Ignored.
	 */
	public static void main(String args[]) {
		try 
		{
			String[][] data = CSVFileReader.read("Files\\jmhandin\\Data_Mining_Student_DataSet_Spring_2013.csv", false);
			for (String[] line : data) 
			{
				System.out.println(Arrays.toString(line));
			}
			
			fillValues(data);
			correctFormat(data);
			preprocess(data);
			
			for (String[] line : data) 
			{
				System.out.println(Arrays.toString(line));
			}
			
			List<List<String>> converted;
			converted = APriori.aprioriAlgorithm(data, 3);
			for(List<String> l : converted)
			{
				System.out.println(l);
			}
		} catch (IOException e) 
		{
			System.err.println(e.getLocalizedMessage());
		}
	}
	
	/**
	 * Fills in the missing values in the attribute columns used in the data mining.
	 * 
	 * @param dataSet The data set to preprocess.
	 * @return The updated data set.
	 */
	public static String[][] fillValues(String[][] dataSet)
	{
		// Fill in median
		MissingValues.fillInMedian(dataSet, Attributes.OS.ordinal()); // Fill in mean of Operating System
		MissingValues.fillInMedian(dataSet, Attributes.P_SKILL.ordinal()); // Fill in mean of programming skill
		MissingValues.fillInMedian(dataSet, Attributes.UNI_STUDY.ordinal()); // Fill in mean of years in higher education
		MissingValues.fillInMedian(dataSet, Attributes.ENGLISH.ordinal()); // Fill in mean of English Skills
		return dataSet;
	}
	
	/**
	 * Corrects the format of the values in the data set.
	 * 
	 * @param dataset The data set to preprocess.
	 * @return The updated data set.
	 */
	public static String[][] correctFormat(String[][] dataSet)
	{
		// Transform special character ½ to .5
		WrongFormat.transformSpecialCharacter(dataSet, Attributes.P_SKILL.ordinal());
		WrongFormat.transformSpecialCharacter(dataSet, Attributes.UNI_STUDY.ordinal());
		WrongFormat.transformSpecialCharacter(dataSet, Attributes.RAND_1_10.ordinal());
		WrongFormat.transformSpecialCharacter(dataSet, Attributes.RAND_0_1_A.ordinal());
		WrongFormat.transformSpecialCharacter(dataSet, Attributes.RAND_0_1_B.ordinal());
		
		// Trim to numbers
		WrongFormat.trimToFirstInt(dataSet, Attributes.RAND_1_10.ordinal());
		WrongFormat.trimToFirstInt(dataSet, Attributes.ENGLISH.ordinal());
		WrongFormat.trimToFirstDecimal(dataSet, Attributes.RAND_0_1_A.ordinal());
		WrongFormat.trimToFirstDecimal(dataSet, Attributes.RAND_0_1_B.ordinal());
		
		// Check colour values
		WrongFormat.checkColorColumn(dataSet, Attributes.COLOUR.ordinal());
		
		return dataSet;
	}

	/**
	 * Preprocesses the data set given.
	 * 
	 * @param dataset The data set to preprocess.
	 * @return The updated data set.
	 */
	public static String[][] preprocess(String[][] dataSet)
	{
		// Min-max normalise
		// age
		// programming skill
		// OS
		// years in higher
		// english
		
		// Normalises the numerical values of the random numbers to low, med, high
		Normalisation.classifyNumberInt(dataSet, Attributes.RAND_1_10.ordinal());
		Normalisation.classifyNumberFirst(dataSet, Attributes.RAND_0_1_A.ordinal());
		Normalisation.classifyNumberSecond(dataSet, Attributes.RAND_0_1_B.ordinal());
		
		return dataSet;
	}
}
