package jmhandin;

import java.io.IOException;
import java.util.Arrays;

import jmhandin.other.CSVFileReader;
import jmhandin.preprocessing.*;

public class Main {

	/**
	 * 
	 * @param args
	 */
	public static void main(String args[]) {
		try {
			String[][] data = CSVFileReader.read("Files\\Lab2\\Data_Mining_Student_DataSet_Spring_2013.csv", false);
			
			data = WrongFormat.trimToFirstInt(data, 23);
			
			data = WrongFormat.transformString(data, 25, "Fibo", "Fibonacci");
			
			// Fill in global variables
			
			// Fill in with median
			data = MissingValues.fillInMedian(data, 22);
			for (String[] line : data) {
				System.out.println(Arrays.toString(line));
			}
		} catch (IOException e) {
			System.err.println(e.getLocalizedMessage());
		}
	}
	
	/**
	 * 
	 */
	public static void fillValues()
	{
		
	}
	
	/**
	 * 
	 */
	public static void correctFormat()
	{
		
	}

}
