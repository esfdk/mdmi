package jghandin;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Split the people into clusters based on DoB, Year, Uni study and Prog Skill. 
 * Which groups scores better on the three last questions?
 * Can one predict what a given group will answer, based on the clusters?
 * 
 * @author Jacob
 */
public class Main
{
	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		kMeans km = new kMeans();
		String[][] dataset = null;
		kNearestNeighbor knn = new kNearestNeighbor();
				
		dataset = kMeansLoadAndCleanDataset();
		km.clusterData(dataset, 3);
		km.printCorrectAnswers(dataset, km.getClusters());

		dataset = loadAndCleanDatasetKNN();
		dataset = discrentizeDatasetKNN(dataset);
		knn.printResultsFor(dataset, 1, 5);

		dataset = loadAndCleanDatasetKNN();
		dataset = normalizeDatasetKNN(dataset);
		knn.printResultsFor(dataset, 1, 5);
	}
	
	/**
	 * Normalizes the dataset for the kNN algorithm
	 * 
	 * @param dataset The dataset
	 * @return The updated dataset
	 */
	private static String[][] normalizeDatasetKNN(String[][] dataset)
	{
		dataset = DataTransformation.normalizeColumn(dataset, EColumns.age.ordinal(), 20, 0);
		dataset = DataTransformation.normalizeColumn(dataset, EColumns.prog_skill.ordinal(), 20, 0);
		dataset = DataTransformation.normalizeColumn(dataset, EColumns.yrs_of_uni_study.ordinal(), 20, 0);
		
		return dataset;
	}
	
	/**
	 * Discrentizes the dataset for the kNN algorithm
	 * 
	 * @param dataset The dataset
	 * @return The updated dataset
	 */
	private static String[][] discrentizeDatasetKNN(String[][] dataset)
	{
		ArrayList<Pair<Integer, Integer>> intervals = new ArrayList<Pair<Integer, Integer>>();

		// Discretizes the values for age
		intervals.add(new Pair<Integer, Integer>(0, 19));
		intervals.add(new Pair<Integer, Integer>(20, 24));
		intervals.add(new Pair<Integer, Integer>(25, 29));
		intervals.add(new Pair<Integer, Integer>(30, 100));
		dataset = DataTransformation.discretizeColumn(dataset, intervals, EColumns.age.ordinal());

		// Discretizes the values for prog_skill
		intervals = new ArrayList<Pair<Integer,Integer>>();
		intervals.add(new Pair<Integer, Integer>(0, 3));
		intervals.add(new Pair<Integer, Integer>(4, 7));
		intervals.add(new Pair<Integer, Integer>(8, 10));
		dataset = DataTransformation.discretizeColumn(dataset, intervals, EColumns.prog_skill.ordinal());

		// Discretizes the values for prog_skill
		intervals = new ArrayList<Pair<Integer,Integer>>();
		intervals.add(new Pair<Integer, Integer>(0, 3));
		intervals.add(new Pair<Integer, Integer>(4, 7));
		intervals.add(new Pair<Integer, Integer>(8, 12));
		dataset = DataTransformation.discretizeColumn(dataset, intervals, EColumns.yrs_of_uni_study.ordinal());		
		
		return dataset;
	}
	
	/**
	 * Loads the data set and does basic cleaning to prepare it for the kNN algorithm.
	 * 
	 * @return The updated dataset
	 */
	private static String[][] loadAndCleanDatasetKNN()
	{
		String[][] dataset = null;
		
		try
		{
			dataset = CSVFileReader.read("src\\jghandin\\Data_Mining_Student_DataSet_Spring_2013.csv", false);
			
			// Formats the age and date of birth
			dataset = DataTransformation.formatDateOfBirth(dataset);
			
			 // Trims the prog_skill column
			dataset = DataTransformation.trimToFirstInt(dataset, EColumns.prog_skill.ordinal(), 0);
			// Trims the yrs_of_uni_study column
			dataset = DataTransformation.trimToFirstInt(dataset, EColumns.yrs_of_uni_study.ordinal(), 0); 
			// Trims the solar_system_planets column
			dataset = DataTransformation.trimToFirstInt(dataset, EColumns.solar_system_planets.ordinal(), 0); 

			// Makes sure that stuff is spelled the same way
			dataset = DataTransformation.trimToString(dataset, EColumns.seq_name.ordinal(), "Fibo", "Fibonacci");
			dataset = DataTransformation.trimToString(dataset, EColumns.more_dk_mnts.ordinal(), "Yes", "Yes");
			dataset = DataTransformation.trimToString(dataset, EColumns.more_dk_mnts.ordinal(), "No", "No");
			dataset = DataTransformation.trimToString(dataset, EColumns.winter_tired.ordinal(), "Yes", "Yes");
			dataset = DataTransformation.trimToString(dataset, EColumns.winter_tired.ordinal(), "No", "No");
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		
		return dataset;
	}
	
	/**
	 * Loads and cleans the dataset for the kMeans algorithm
	 * 
	 * @param dataset The dataset
	 * @return The cleaned dataset
	 */
	private static String[][] kMeansLoadAndCleanDataset()
	{
		String[][] dataset = null;
		try
		{
			dataset = CSVFileReader.read("src\\jghandin\\Data_Mining_Student_DataSet_Spring_2013.csv", false);
			
			// Formats the date of birth
			dataset = DataTransformation.formatDateOfBirth(dataset);
			
			 // Trims the prog_skill column
			dataset = DataTransformation.trimToFirstInt(dataset, EColumns.prog_skill.ordinal(), 0);
			// Trims the yrs_of_uni_study column
			dataset = DataTransformation.trimToFirstInt(dataset, EColumns.yrs_of_uni_study.ordinal(), 0); 
			// Trims the solar_system_planets column
			dataset = DataTransformation.trimToFirstInt(dataset, EColumns.solar_system_planets.ordinal(), 0); 

			// Makes sure that "Fibonacci" is spelled correctly if it was written in the cell.
			dataset = DataTransformation.trimToString(dataset, EColumns.seq_name.ordinal(), "Fibo", "Fibonacci"); 
			
			// Normalizes the values for age, DOB, prog_skill and yrs_of_uni_study
			DataTransformation.normalizeColumn(dataset, EColumns.age.ordinal(), 20, 0);
			DataTransformation.normalizeColumn(dataset, EColumns.DOB.ordinal(), 20, 0);
			DataTransformation.normalizeColumn(dataset, EColumns.prog_skill.ordinal(), 20, 0);
			DataTransformation.normalizeColumn(dataset, EColumns.yrs_of_uni_study.ordinal(), 20, 0);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		
		return dataset;
	}
	
	/**
	 * Prints the dataset in a decent format
	 * 
	 * @param dataset The dataset
	 */
	@SuppressWarnings("unused")
	private static void printDataset(String[][] dataset)
	{
		// Prints the dataset to the console
		for (String[] line : dataset) 
		{
			System.out.print("[");
			for (String word : line)
			{
				System.out.print(word + "; ");
			}
			System.out.println("]");
		}
	}
}
