package project;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import project.apriori.*;
import project.enums.DataColumn;
import project.kmeans.KMeans;
import project.kmeans.Cluster;
import project.kmeans.DataPoint;

public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
	    try
	    {
	    	System.out.println("UE");
    		String[][] unemploymentRawData = CSVFileReader.read("../Files/Project/DataSets/Unemployment_rate_annual.csv", false);
	    	System.out.println("Balance");
	        String[][] balanceOfPaymentsRawData = CSVFileReader.read("../Files/Project/DataSets/Balance_of_payments_annual.csv", false);
    		System.out.println("GDP");
    		String[][] GDPRawData = CSVFileReader.read("../Files/Project/DataSets/Euro_per_inhabitant.csv", false);
    		System.out.println("Pop");
    		String[][] populationRawData = CSVFileReader.read("../Files/Project/DataSets/Population_Annual.csv", false);
    		
    		String[][] balanceOfPaymentsFlagged = getEntriesWithFlags(balanceOfPaymentsRawData, 7);
    		String[][] unemploymentFlagged = getEntriesWithFlags(unemploymentRawData, 6);
    		String[][] GDPFlagged = getEntriesWithFlags(GDPRawData, 5);
    		String[][] populationFlagged = getEntriesWithFlags(populationRawData, 5);

    		CsvWriter.writeDataToFile(balanceOfPaymentsFlagged, "../Files/Project/DataSets/Flagged/balance_flagged.csv");
    		CsvWriter.writeDataToFile(unemploymentFlagged, "../Files/Project/DataSets/Flagged/unemployment_flagged.csv");
    		CsvWriter.writeDataToFile(GDPFlagged, "../Files/Project/DataSets/Flagged/GDP_flagged.csv");
    		CsvWriter.writeDataToFile(populationFlagged, "../Files/Project/DataSets/Flagged/population_flagged.csv");
    		
    		int[] matchColumns = {0, 1};
    		int[] balanceOfPaymentsColumns = {6};
    		int[] unemplomentColumns = {5};
    		int[] GDPColumns = {4};
    		int[] populationColumns = {4};
    		
    		String[][] data = DataSetHelpers.combineDataSets(unemploymentRawData, unemplomentColumns, matchColumns, balanceOfPaymentsRawData, balanceOfPaymentsColumns, matchColumns);
    		int[] columnsToKeep1 = {2, 3};
    		data = DataSetHelpers.combineDataSets(data, columnsToKeep1, matchColumns, GDPRawData, GDPColumns, matchColumns);
    		int[] columnsToKeep2 = {2, 3, 4};
    		data = DataSetHelpers.combineDataSets(data, columnsToKeep2, matchColumns, populationRawData, populationColumns, matchColumns); 
			
    		data = removeSpaces(data);
    		data = replace(data, "\":\"", "\"?\"");
    		
    		WekaWriter.writeDataToFile(data, "KMeansReadyDataSet");

    		// Normalizes data
    		data = DataSetHelpers.normalizeDataset(data, 1, 0, DataColumn.UnemploymentRate.ordinal(), 0, 10);
    		data = DataSetHelpers.normalizeDataset(data, 1, 0, DataColumn.BalanceOfPayments.ordinal(), 0, 10);
    		data = DataSetHelpers.normalizeDataset(data, 1, 0, DataColumn.GdpPerInhabitant.ordinal(), 0, 10);
    		data = DataSetHelpers.normalizeDataset(data, 1, 0, DataColumn.Population.ordinal(), 0, 10);
    		
    		// Discretizes data
    		ArrayList<Range> ranges = new ArrayList<Range>();
    		for(float fl = 0.0f; fl <= 9.5f ; fl += 0.5f)
    		{
    			ranges.add(new Range(fl, fl + 0.5f));	
    		}
    		
    		ranges.add(new Range(10.0f, 10.0f));
    		
    		for(int i = 1983; i <= 2012; i++)
    		{
    			data = DataSetHelpers.discretizeColumnYear(data, ranges, DataColumn.UnemploymentRate.ordinal(), i, 0);
    			data = DataSetHelpers.discretizeColumnYear(data, ranges, DataColumn.BalanceOfPayments.ordinal(), i, 0);
    			data = DataSetHelpers.discretizeColumnYear(data, ranges, DataColumn.GdpPerInhabitant.ordinal(), i, 0);
    			data = DataSetHelpers.discretizeColumnYear(data, ranges, DataColumn.Population.ordinal(), i, 0);
    		}
    		
    		WekaWriter.writeDataToFile(data, "apReadyDataSet");
	    }
		catch (IOException e)
		{
		    System.out.println(e.getMessage());
		}
	}
	
	private static String[][] removeSpaces(String[][] data)
	{
		for(int i = 0; i < data.length; i++)
		{
			for(int j = 0; j < data[i].length; j++)
			{
				data[i][j] = data[i][j].replaceAll(" ", "");
			}
		}
		
		return data;
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
	
	private static String[][] getEntriesWithFlags(String[][] data, int flagColumn)
	{
		String[][] temp = new String[data.length][3];
		for(int i = 0; i < data.length; i++)
		{
			temp[i][0] = data[i][0];
			temp[i][1] = data[i][1];
			temp[i][2] = data[i][flagColumn];
		}
		
		List<Integer> flagList = new ArrayList<Integer>();
		for(int i = 0; i < temp.length; i++)
		{
			if(!temp[i][2].equals("\"\""))
			{
				flagList.add(i);
			}
			
		}
		
		int flSize = flagList.size();
		
		String[][] rarray = new String[flSize][3]; 
		
		for(int i = 0; i < flSize; i++)
		{
			rarray[i][0] = temp[flagList.get(i)][0];
			rarray[i][1] = temp[flagList.get(i)][1];
			rarray[i][2] = temp[flagList.get(i)][2];
		}
		
		return rarray;
	}
	
<<<<<<< HEAD
	
=======
	public static void printClusters(KMeans kmeans)
	{
	    List<Cluster> clusters = kmeans.GetClusters();
	    
	    int i = 0;
	    for (Cluster c : clusters)
	    {
	        System.out.println("Cluster #" + i);
	        
	        double[] centroid = c.GetCentroid();
	        /*for (double val : centroid)
	        {
	            System.out.println(val);
	        }*/
	        /*System.out.println("Unemployment rate: " + centroid[0]);
	        System.out.println("Balance of payments: " + centroid[1]);
	        System.out.println("GDP Per Inhabitant: " + centroid[2]);*/
	        System.out.println("Population: " + centroid[0]);
	        
	        /*System.out.println("\nDatapoints:");
	        List<DataPoint> dataPoints = c.GetDataPoints();
	        for (DataPoint dp : dataPoints)
	        {
	            System.out.println(dp.getOriginalAttribute(DataColumn.Country.ordinal()) + ", " + dp.getOriginalAttribute(DataColumn.Year.ordinal()));
	        }*/
	        
	        System.out.println("-------------------------------------------\n");
	        
	        i++;
	    }
	}
>>>>>>> 01f8882c7a869c2f904bf24479fab58d99ace578
}
