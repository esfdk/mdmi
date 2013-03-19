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
		String[][] data = null;
		
		try 
		{
			// Reads the dataset
			data = CSVFileReader.read("Files\\Lab2\\Data_Mining_Student_DataSet_Spring_2013.csv", false);
			
			cleanDataset(data);
			kMeans kM = new kMeans(data, 3);

			ArrayList<kMeansCluster> clusters = kM.getClusters();
			for (kMeansCluster kmC : clusters)
			{
				System.out.println(kmC.toString());
			}
		} 
		catch (IOException e) 
		{
			System.err.println(e.getLocalizedMessage());
		}
	}
	
	/**
	 * Calls the necessary methods to clean the dataset
	 * 
	 * @param dataset The dataset
	 * @return The cleaned dataset
	 */
	private static String[][] cleanDataset(String[][] dataset)
	{
		// Formats the age and date of birth
		dataset = Cleaner.fixBirthAndAge(dataset);
		
		 // Trims the prog_skill column
		dataset = Cleaner.trimToFirstInt(dataset, 03, 0);
		// Trims the yrs_of_uni_study column
		dataset = Cleaner.trimToFirstInt(dataset, 04, 0); 
		// Trims the solar_system_planets column
		dataset = Cleaner.trimToFirstInt(dataset, 23, 0); 

		// Makes sure that "Fibonacci" is spelled correctly if it was written in the cell.
		dataset = Cleaner.trimToString(dataset, 25, "Fibo", "Fibonacci"); 
		
		// Fill in seq_name column with "Don't know"
		dataset = Cleaner.fillInValues(dataset, 25, "Don't know"); 
		
		// Fill in therb_fortt_glag with the median
		dataset = Cleaner.fillInMedian(dataset, 22);
		
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
		
		return dataset;
	}
}
