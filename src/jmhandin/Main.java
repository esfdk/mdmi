package jmhandin;

import java.io.IOException;
import java.util.Arrays;

import jmhandin.other.CSVFileReader;

public class Main {

	/**
	 * 
	 * @param args
	 */
	public static void main(String args[]) {
		try 
		{
			String[][] data = CSVFileReader.read("Files\\jmhandin\\Data_Mining_Student_DataSet_Spring_2013.csv", false);
			
			// Print the dataset
			for (String[] line : data) 
			{
				System.out.println(Arrays.toString(line));
			}
			
			fillValues(data);
			correctFormat(data);
			
			// Print cleaned data set
			for (String[] line : data) 
			{
				System.out.println(Arrays.toString(line));
			}
			
			preprocess(data);
			
			// Print the completely pre-processed data set
			for (String[] line : data) 
			{
				System.out.println(Arrays.toString(line));
			}
			
			
		} catch (IOException e) 
		{
			System.err.println(e.getLocalizedMessage());
		}
	}
	
	/**
	 * 
	 * @param dataset The data set to pre-process.
	 * @return
	 */
	public static String[][] fillValues(String[][] dataset)
	{
		
		return dataset;
	}
	
	/**
	 * 
	 * @param dataset The data set to pre-process.
	 * @return
	 */
	public static String[][] correctFormat(String[][] dataset)
	{
		
		return dataset;
	}

	/**
	 * 
	 * @param dataset The data set to pre-process.
	 * @return
	 */
	public static String[][] preprocess(String[][] dataset)
	{
		
		return dataset;
	}
}
