package jmhandin.preprocessing;

public class Normalisation {
	/**
	 * Classifies the column specified into the format int-low, int-med, int-high
	 * 
	 * @param dataSet The data set to use.
	 * @param columnToClassify The column in the data set to classify. Must be a string containing a integer between 1 and 10.
	 * @return The classified data set.
	 */
	public static String[][] classifyNumberInt(String[][] dataSet, int columnToClassify)
	{
		for(int i = 0; i < dataSet.length; i++)
		{
			if(dataSet[i][columnToClassify].equals("null")) dataSet[i][columnToClassify] = "int-null";
			else if(Integer.parseInt(dataSet[i][columnToClassify]) <= 3) // 1-3
			{
				dataSet[i][columnToClassify] = "int-low"; // Insert value
			}
			else if(Integer.parseInt(dataSet[i][columnToClassify]) <= 7) // 4-7
			{
				dataSet[i][columnToClassify] = "int-med"; // Insert value
			}
			else if(Integer.parseInt(dataSet[i][columnToClassify]) <= 10) // 8-10
			{
				dataSet[i][columnToClassify] = "int-high"; // Insert value
			}
			else
			{
				dataSet[i][columnToClassify] = "int-null";
			}
		}	
		return dataSet;
	}
	
	/**
	 * Classifies the column specified into the format first-low, first-med, first-high
	 * 
	 * @param dataSet The data set to use.
	 * @param columnToClassify The column in the data set to classify. Must be a string containing a decimal value between 0 & 1.
	 * @return The classified data set.
	 */
	public static String[][] classifyNumberFirst(String[][] dataSet, int columnToClassify)
	{
		for(int i = 0; i < dataSet.length; i++)
		{
			if(dataSet[i][columnToClassify].equals("null")) dataSet[i][columnToClassify] = "1st-null";
			else if(Double.parseDouble(dataSet[i][columnToClassify]) <= 1.0/3) // Between 0.0 and 1/3
			{
				dataSet[i][columnToClassify] = "first-low"; // Insert value
			}
			else if(Double.parseDouble(dataSet[i][columnToClassify]) <= (1.0/3) * 2) // Between 0.0 and 1/3
			{
				dataSet[i][columnToClassify] = "first-med"; // Insert value
			}
			else if(Double.parseDouble(dataSet[i][columnToClassify]) <= 1.0)
			{
				dataSet[i][columnToClassify] = "first-high"; // Insert value
			}
			else
			{
				dataSet[i][columnToClassify] = "1st-null";
			}
		}	
		return dataSet;
	}
	
	/**
	 * Classifies the column specified into the format second-low, second-med, second-high
	 * 
	 * @param dataSet The data set to use.
	 * @param columnToClassify The column in the data set to classify. Must be a string containing a decimal value between 0 & 1.
	 * @return The classified data set.
	 */
	public static String[][] classifyNumberSecond(String[][] dataSet, int columnToClassify)
	{
		for(int i = 0; i < dataSet.length; i++)
		{
			if(dataSet[i][columnToClassify].equals("null")) dataSet[i][columnToClassify] = "2nd-null";
			else if(Double.parseDouble(dataSet[i][columnToClassify]) <= 1.0/3) // Between 0.0 and 1/3
			{
				dataSet[i][columnToClassify] = "second-low"; // Insert value
			}
			else if(Double.parseDouble(dataSet[i][columnToClassify]) <= (1.0/3) * 2) // Between 0.0 and 1/3
			{
				dataSet[i][columnToClassify] = "second-med"; // Insert value
			}
			else if(Double.parseDouble(dataSet[i][columnToClassify]) <= 1.0)
			{
				dataSet[i][columnToClassify] = "second-high"; // Insert value
			}
			else
			{
				dataSet[i][columnToClassify] = "2nd-null";
			}
		}	
		return dataSet;
	}
}
