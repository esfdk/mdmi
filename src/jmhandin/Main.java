package jmhandin;

import java.io.IOException;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import jmhandin.algorithms.APriori;
import jmhandin.algorithms.KNN;
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
	public static void main(String args[]) 
	{
		try 
		{
			String[][] data = CSVFileReader.read("Files\\jmhandin\\Data_Mining_Student_DataSet_Spring_2013.csv", false);
					
			fillValues(data);
			correctFormat(data);
			preprocess(data);
			
			// KNN for training 12, 18, 24, 30 and with k = 4
			for(int i = 12; i < 31; i += 6)
			{
				KNNPrint(data, i, 4);
			}
			System.out.println();
			
			// KNN for training 12, 18, 24, 30 and with k = 6
			for(int i = 12; i < 31; i += 6)
			{
				KNNPrint(data, i, 6);
			}
			System.out.println();
			
			// KNN for training 12, 18, 24, 30 and with k = 8
			for(int i = 12; i < 31; i += 6)
			{
				KNNPrint(data, i, 8);
			}
			System.out.println();
			
			// Run A Priori algorithm with 2 as minimum support
			APrioriPrint(data, 4, 2);
			// Run A Priori algorithm with 3 as minimum support
			APrioriPrint(data, 4, 3);
			// Run A Priori algorithm with 4 as minimum support
			APrioriPrint(data, 4, 4);
			
		} catch (IOException e) 
		{
			System.err.println(e.getLocalizedMessage());
		}
	}
	
	private static void KNNPrint(String[][] dataSet, int trainingSize, int k)
	{
		if(!(dataSet.length >= trainingSize)) return;
		
		String[][] trainingSet = new String[trainingSize][Attributes.SEQUENCE_NAME.ordinal()];
		for(int i = 0; i < trainingSize; i++)
		{
			trainingSet[i] = dataSet[i];
		}
		
		String[][] testSet = new String[dataSet.length - trainingSize][Attributes.SEQUENCE_NAME.ordinal()];
		for(int i = trainingSize; i < dataSet.length; i++)
		{
			testSet[i - trainingSize] = dataSet[i];
		}
		
		int correctPredictions = 0;
		int numberOfPredictions = 0;
		for(String[] testTuple : testSet)
		{
			String prediction = KNN.guessProgrammingLanguage(testTuple, trainingSet, k);
			
			numberOfPredictions++;
			
			if(testTuple[Attributes.P_LANG.ordinal()].equals(prediction))
			{
				correctPredictions++;
			}
		}
		
		System.out.println("KNN,training size=" + trainingSize + " and k=" + k + " result: " + numberOfPredictions + "predictions, " + correctPredictions + " correct!");
		
	}
	
	/**
	 * Prints result of APriori algorithm
	 * @param dataSet The data set to run the algorithm on - should be cleaned before the call.
	 * @param size Size of the frequent pattern
	 * @param support Minimum support of the frequent pattern.
	 */
	private static void APrioriPrint(String[][] dataSet, int size, int support)
	{
		HashMap<Integer, List<List<String>>> APresult;
		
		APresult = APriori.aprioriAlgorithm(dataSet, support);
		System.out.println("\nFrequent patterns of size " + size + " with support " + support + ":");
		for(List<String> l : APresult.get(size))
		{
			System.out.println(l);
		}
	}
	
	/**
	 * Fills in the missing values in the attribute columns used in the data mining.
	 * 
	 * @param dataSet The data set to preprocess.
	 * @return The updated data set.
	 */
	private static String[][] fillValues(String[][] dataSet)
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
	private static String[][] correctFormat(String[][] dataSet)
	{
		// Transform special character ½ to .5
		WrongFormat.transformSpecialCharacter(dataSet, Attributes.P_SKILL.ordinal());
		WrongFormat.transformSpecialCharacter(dataSet, Attributes.UNI_STUDY.ordinal());
		WrongFormat.transformSpecialCharacter(dataSet, Attributes.RAND_1_10.ordinal());
		WrongFormat.transformSpecialCharacter(dataSet, Attributes.RAND_0_1_A.ordinal());
		WrongFormat.transformSpecialCharacter(dataSet, Attributes.RAND_0_1_B.ordinal());
		
		// Trim to numbers
		WrongFormat.trimToFirstInt(dataSet, Attributes.RAND_1_10.ordinal());
		WrongFormat.trimToFirstDecimal(dataSet, Attributes.AGE.ordinal());
		WrongFormat.trimToFirstDecimal(dataSet, Attributes.P_SKILL.ordinal());
		WrongFormat.trimToFirstDecimal(dataSet, Attributes.UNI_STUDY.ordinal());
		WrongFormat.trimToFirstDecimal(dataSet, Attributes.ENGLISH.ordinal());
		WrongFormat.trimToFirstDecimal(dataSet, Attributes.RAND_0_1_A.ordinal());
		WrongFormat.trimToFirstDecimal(dataSet, Attributes.RAND_0_1_B.ordinal());
		
		// Check colour values
		WrongFormat.checkColorColumn(dataSet, Attributes.COLOUR.ordinal()); 
		
		// Reduce programming languages to only 1 language, comma-seperated
		WrongFormat.trimToOneLanguage(dataSet, Attributes.P_LANG.ordinal());
		
		return dataSet;
	}

	/**
	 * Preprocesses the data set given.
	 * 
	 * @param dataset The data set to preprocess.
	 * @return The updated data set.
	 */
	private static String[][] preprocess(String[][] dataSet)
	{
		// Min-max normalise
		Normalisation.minMax(dataSet, Attributes.AGE.ordinal(), 0.0, 1.0);
		Normalisation.minMax(dataSet, Attributes.P_SKILL.ordinal(), 0.0, 1.0);
		Normalisation.minMax(dataSet, Attributes.UNI_STUDY.ordinal(), 0.0, 1.0);
		Normalisation.minMax(dataSet, Attributes.ENGLISH.ordinal(), 0.0, 1.0);
		
		// Normalises the numerical values of the random numbers to low, med, high
		Normalisation.classifyNumberInt(dataSet, Attributes.RAND_1_10.ordinal());
		Normalisation.classifyNumberFirst(dataSet, Attributes.RAND_0_1_A.ordinal());
		Normalisation.classifyNumberSecond(dataSet, Attributes.RAND_0_1_B.ordinal());

		// Normalise operating system to "Windows" "Mac" "*NIX" or "OS-null"
		Normalisation.classifyOS(dataSet, Attributes.OS.ordinal());
		
		return dataSet;
	}
}
