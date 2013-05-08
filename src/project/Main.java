package project;

import java.io.IOException;

public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
	    try
	    {
	    	System.out.println("Balance");
	        String[][] balanceOfPaymentsRawData = CSVFileReader.read("Files/Project/DataSets/Balance_of_payments_annual.csv", false);
	        System.out.println("UE");
    		String[][] unemploymentRawData = CSVFileReader.read("Files/Project/DataSets/Unemployment_rate_annual.csv", false);
    		System.out.println("GDP");
    		String[][] GDPRawData = CSVFileReader.read("Files/Project/DataSets/Euro_per_inhabitant.csv", false);
    		System.out.println("Pop");
    		String[][] populationRawData = CSVFileReader.read("Files/Project/DataSets/Population_Annual.csv", false);
    		

    		String[][] balanceOfPaymentsFlagged = getEntriesWithFlags(balanceOfPaymentsRawData, 7);
    		String[][] unemploymentFlagged = getEntriesWithFlags(unemploymentRawData, 6);
    		String[][] populationFlagged = getEntriesWithFlags(populationRawData, 5);

    		int[] matchColumns = {0, 1};
    		int[] balanceOfPaymentsColumns = {6};
    		int[] unemplomentColumns = {5};
    		int[] GDPColumns = {4};
    		int[] populationColumns = {4};


    		String[][] data = DataSetHelpers.combineDataSets(unemploymentRawData, unemplomentColumns, matchColumns, balanceOfPaymentsRawData, balanceOfPaymentsColumns, matchColumns);
    		int[] columnsToKeep = {2, 3};
    		data = DataSetHelpers.combineDataSets(data, columnsToKeep, matchColumns, GDPRawData, GDPColumns, matchColumns);
    		columnsToKeep = new int[] {2, 3, 4};
    		data = DataSetHelpers.combineDataSets(data, columnsToKeep, matchColumns, populationRawData, populationColumns, matchColumns); 
			
    		data = replace(data, "\":\"", "\"?\"");

    		CsvWriter.writeDataToFile(data, "datafile.csv");
	    }
		catch (IOException e)
		{
		    System.out.println(e.getMessage());
		}
	}
	
	/**
	 * 
	 * 
	 * @param data
	 * @param stringToReplace
	 * @param stringToInsert The string to insert
	 * @return The updated data set
	 */
	private static String[][] replace(String[][] data, String stringToReplace, String stringToInsert)
	{
		for(int i = 0; i < data.length; i++)
		{
			for(int j = 0; j < data[i].length; j++)
			{
				if(data[i][j].equals(stringToReplace))
				{
					data[i][j] = stringToInsert;
				}
			}
		}
		
		return data;
	}
	
	private static String[][] replaceCommaWithDot(String[][] data)
	{
		for(String[] dataLine : data)
		{
			for(String dataItem : dataLine)
			{
				if(dataItem.contains(","))
				{
					dataItem = dataItem.replace(",", ".");
				}
			}
		}
		
		return data;
	}
	
	private static String[][] getEntriesWithFlags(String[][] data, int flagColumn)
	{
		
		
		return data;
	}

}
