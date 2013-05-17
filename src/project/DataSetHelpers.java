package project;

import java.lang.IllegalArgumentException;
import java.util.ArrayList;
import java.util.List;

public class DataSetHelpers
{
    /**
     * Private constructor for static class
     */
    private DataSetHelpers() {}
    
    /**
     * Combine 2 datasets.
     *
     * @param dataSet1 The first dataset.
     * @param columns1 The column indexes to copy from first dataset.
     * @param matchColumns1 The columns to match on (indexes in first dataset).
     * @param dataSet2 The second dataset.
     * @param columns2 The column indexes to copy from second dataset.
     * @param matchColumns2 The columns to match on (indexes in first dataset).
     * @return Combined dataset.
     */
    public static String[][] combineDataSets(String[][] dataSet1, int[] columns1, int[] matchColumns1, String[][] dataSet2, int[] columns2, int[] matchColumns2)
    {
        // Check if programmer is retarded.
        if (matchColumns1.length != matchColumns2.length)
        {
            throw new IllegalArgumentException();
        }
        
        // Calculate amount of columns in new dataset.
        int amountOfColumns = matchColumns1.length + columns1.length + columns2.length;
        
        // Make (initially empty) 2D array for our new dataset.
        String[][] returnData = new String[dataSet1.length][amountOfColumns];
        
        // Fill in data from dataset1.
        for (int i = 0; i < dataSet1.length; i++)
        {
            int j = 0;
            
            // Fill in from matchColumns1
            for (int matchCol : matchColumns1)
            {
                returnData[i][j] = dataSet1[i][matchCol];
                
                j++;
            }
            
            // Fill in from columns1
            for (int col : columns1)
            {
                returnData[i][j] = dataSet1[i][col];
                
                j++;
            }
        }
        
        // Match data from dataset2.
        for (String[] dataLine : dataSet2)
        {
            for (String[] returnDataLine : returnData)
            {
                boolean matched = true;
                int a = 0;
                
                for (int matchCol : matchColumns2)
                {
                    if (!returnDataLine[a].equals(dataLine[matchCol]))
                    {
                        matched = false;
                    }
                    a++;
                }
                
                if(matched)
                {
                    int c = matchColumns1.length + columns1.length;
                    for(int column : columns2)
                    {
                        returnDataLine[c] = dataLine[column];
                        c++;
                    }
                }
            }
        }
        
        // Return new dataset.
        return returnData;
    }
    
    /**
     * Discretize all values in the given column with the intervals provided.
     * 
     * @param dataset Dataset to work on.
     * @param column The column to discretize.
     * @param rangeBins The ranges to discretize by.
     * @return The updated dataset.
     */
    public static String[][] discretizeColumn(String[][] dataset, List<Range> ranges, int column)
    {
    	// Iterate over all lines in dataset
        for (String[] dataLine : dataset)
        {
        	// Is it's a question mark, skip the line
        	if (dataLine[column].replace("\"", "").equals("?"))
        	{
        		continue;
        	}        	
        	
        	// Get the value and see if it fits in any of the given ranges
            float value = Float.parseFloat(dataLine[column].replace("\"", ""));
            for (Range range : ranges)
            {
                if (range.inRange(value))
                {
                    dataLine[column] = range.lowerEnd + "-" + range.higherEnd;
                }
            }
        }
    	
    	return dataset;
    }
    
    /**
     * Discretize all values in the given column with the intervals provided,
     * assuming they are from the given year.
     * 
     * @param dataset Dataset to work on.
     * @param ranges The ranges to discretize by.
     * @param column The column to discretize.
     * @param year The year to discretize for.
     * @param yearColumn The column where the year is
     * @return The updated dataset.
     */
    public static String[][] discretizeColumnYear(String[][] dataset, List<Range> ranges, int column, int year, int yearColumn)
    {
    	// Iterate over all lines in dataset
        for (String[] dataLine : dataset)
        {
        	// If it's a question mark or not the correct year, skip the line
        	if (dataLine[column].replace("\"", "").equals("?")
        		|| Integer.parseInt(dataLine[yearColumn].replace("\"", "")) != year)
        	{
        		continue;
        	}

        	// Get the value and see if it fits in any of the given ranges
            float value = Float.parseFloat(dataLine[column].replace("\"", ""));
            for (Range range : ranges)
            {
                if (range.inRange(value))
                {
                    dataLine[column] = range.lowerEnd + "-" + range.higherEnd;
                }
            }
        }
    	
    	return dataset;
    }
    
    /**
     * Normalizes a column in a dataset.
     * 
     * @param dataset The dataset to normalize.
     * @param countryColumn The column where country is located.
     * @param yearColumn The column where year is located.
     * @param columnToNormalize The column to normalize.
     * @param min The minimum value for normalization.
     * @param max The maximum value for normalization.
     * @return The normalized dataset.
     */
    public static String[][] normalizeDataset(
    		String[][] dataset, int countryColumn, int yearColumn, int columnToNormalize, float min, float max)
    {
    	ArrayList<String> foundCountries = new ArrayList<String>();
    	int startYear = 5000;
    	int endYear = 0;
    	int amountOfCountries = 0;
    	
    	// Finds the amount of countries and the lowest and highest year
    	for (String[] dataLine : dataset)
    	{
    		int year = Integer.parseInt(dataLine[yearColumn].replace("\"", ""));
    		if (year < startYear) startYear = year;
    		if (year > endYear) endYear = year;
    		
    		if (!foundCountries.contains(dataLine[countryColumn].replace("\"", "")))
    		{
    			amountOfCountries++;
    			foundCountries.add(dataLine[countryColumn].replace("\"", ""));
    		}
    	}

    	// Iterates over all years
    	for (int i = startYear; i <= endYear; i++)
    	{
    		// [0] = year, [1] = country, [2] = value
    		String[][] smallDataset = new String[amountOfCountries][3];
    		int k = 0;
    		
    		// Loads the relavant data into smallDataset
        	for (String[] dataLine : dataset)
        	{        		
        		if (Integer.parseInt(dataLine[yearColumn].replace("\"", "")) == i)
        		{
        			String val = "?";
        			if (!dataLine[columnToNormalize].contains("?"))
        			{
                        val = dataLine[columnToNormalize].replace("\"", "");    
        			}
        			
                    smallDataset[k][0] = "" + Integer.parseInt(dataLine[yearColumn].replace("\"", ""));
                    smallDataset[k][1] = dataLine[countryColumn].replace("\"", "");
                    smallDataset[k][2] = val;
                    
                    k++;
        		}
        	}
        	
        	smallDataset = normalizeSmallDataset(smallDataset, min, max);        	
        	dataset = returnToLargeDataset(dataset, smallDataset, columnToNormalize);
    	}
    	
    	return dataset;
    }

    /**
     * Min-max normalization of data.
     * All values in specified column must be able to be parsed to integers.
     * 
     * @param data Dataset to work on.
     * @param column Which column to normalize.
     * @param min Minimum value to use in new dataset (e.g. 0).
     * @param max Maximum value to use in new dataset (e.g. 1).
     * @return Updated dataset.
     */
    private static String[][] normalizeSmallDataset(String[][] dataset, float min, float max)
    {
        float minVal = Float.MAX_VALUE, maxVal = Float.MIN_VALUE;
    	
        // Find lowest and highest value.
        for (String[] dataLine : dataset)
        {
        	if (dataLine[2]. contains("?"))
        	{
        		continue;
        	}
        	
        	float val = Float.parseFloat(dataLine[2]);
            if (val < minVal) minVal = val;
            if (val > maxVal) maxVal = val;
        }
        
        // Change all values (in the column) to be within min and max.
        for (String[] dataLine : dataset)
        {
        	if (dataLine[2]. contains("?"))
        	{
        		continue;
        	}
        	
        	// Split up, because otherwise it does not return the correct result.
            float val = Float.parseFloat(dataLine[2]);
            float tempVal = (val - minVal);
            tempVal = tempVal / (maxVal - minVal);
            tempVal = tempVal * ( max - min );
            tempVal = tempVal + min; 

            dataLine[2] = "" + tempVal;
        }

    	return dataset;
    }

    /**
     * Moves the values from smallDataset to the correct places in dataset.
     * 
     * @param dataset The dataset to move values to.
     * @param smallDataset The dataset to move values from.
     * @param columnToChange The column that is being changed.
     * @return The changed dataset.
     */
    private static String[][] returnToLargeDataset(String[][] dataset, String[][] smallDataset, int columnToChange)
    {
    	int yearColumn = 0;
    	int countryColumn = 1;
    	int valueColumn = 2;
    	
    	// Iterates over all rows in smallDataset
    	for (String[] smallDataLine : smallDataset)
    	{
    		String year = smallDataLine[yearColumn].replace("\"", "");
    		String country = smallDataLine[countryColumn].replace("\"", "");
    		
    		// Finds the respective row in dataset and changes the data
    		// in columnToChange to the value from smallDataset.
    		for (String[] dataLine : dataset)
    		{
        		String year2 = dataLine[yearColumn].replace("\"", "");
        		String country2 = dataLine[countryColumn].replace("\"", "");
        		
    			if (year2.equals(year)
    					&& country2.equals(country))
    			{
    				dataLine[columnToChange] = smallDataLine[valueColumn];
    			}
    		}
    	}
    	
    	return dataset;
    }
}