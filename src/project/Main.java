package project;

import java.io.IOException;

public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
	    try
	    {
	        String[][] balanceOfPaymentsRawData = CSVFileReader.read("../Files/Project/DataSets/Balance_of_payments_annual.csv", false);
    		String[][] unemploymentRawData = CSVFileReader.read("../Files/Project/DataSets/Unemployment_rate_annual.csv", false);

    		String[][] balanceOfPaymentsFlagged = getEntriesWithFlags(balanceOfPaymentsRawData, 7);
    		String[][] unemploymentFlagged = getEntriesWithFlags(unemploymentRawData, 6);

    		int[] matchColumns = {0, 1};
    		int[] balanceOfPaymentsColumns = {6};
    		int[] unemplomentColumns = {5};


    		String[][] data = DataSetHelpers.combineDataSets(unemploymentRawData, unemplomentColumns, matchColumns, balanceOfPaymentsRawData, balanceOfPaymentsColumns, matchColumns);

    		//data = replace(data, ":", "?");

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
		for(String[] dataLine : data)
		{
			for(String dataItem : dataLine)
			{
			    if (dataItem == null) System.out.println("lol");
				if(dataItem.equals(stringToReplace))
				{
					dataItem = stringToInsert;
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
