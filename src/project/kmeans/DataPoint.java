package project.kmeans;

import java.util.List;
import project.enums.DataColumn;

/**
 * Data point in a k-means cluster.
 * 
 * @author Niklas Hansen
 */
public class DataPoint
{
    private String[] dataLine;
    private double[] attributeValues;
    
    /**
     * Constructor for DataPoint class.
     * 
     * @param dataLine (Untampered) data to be stored in data point.
     * @param attributeIndexes Indexes of attributes to cluster by.
     */
    public DataPoint(String[] dataLine, int[] attributeIndexes)
    {
        this.dataLine = dataLine;
        this.attributeValues = new double[attributeIndexes.length];
        
        for (int i = 0; i < this.attributeValues.length; i++)
        {
        	if(!this.dataLine[attributeIndexes[i]].equals("?"))
        	{
        		this.attributeValues[i] = Double.parseDouble(this.dataLine[attributeIndexes[i]]);
        	}
        }
    }
    
    /**
     * Constructor for DataPoint class.
     * 
     * @param dataLine (Untampered) data to be stored in data point.
     * @param attribute Attributes to cluster by.
     */
    public DataPoint(String[] dataLine, List<DataColumn> attributes)
    {
        this.dataLine = dataLine;
        
        int[] attributeIndexes = new int[attributes.size()];
        for (int i = 0; i < attributeIndexes.length; i++)
        {
            attributeIndexes[i] = attributes.get(i).ordinal();
        }
        
        this.attributeValues = new double[attributeIndexes.length];
        
        for (int i = 0; i < this.attributeValues.length; i++)
        {
            this.attributeValues[i] = Double.parseDouble(this.dataLine[attributeIndexes[i]]);
        }
    }
    
    /**
     * Returns attribute value in specified column.
     * 
     * @param column The attribute to get.
     * @return The attribute value.
     */
    public double getAttribute(int attributeIndex)
    {
        return this.attributeValues[attributeIndex];
    }
    
    /**
     * Returns attribute value from original data line.
     * 
     * @param attributeIndex The attribute to get.
     * @return The attribute value.
     */
    public String getOriginalAttribute(int attributeIndex)
    {
        return this.dataLine[attributeIndex];
    }
}
