package project;

import java.lang.IllegalArgumentException;

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
}