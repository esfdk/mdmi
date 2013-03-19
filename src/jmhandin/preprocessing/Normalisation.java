package jmhandin.preprocessing;

public class Normalisation {
	/**
	 * Classifies the column specified into the format int-low, int-medium, int-high
	 * 
	 * @param dataSet The data set to use.
	 * @param columnToClassify The column in the data set to classify. Must be a string containing a integer between 1 and 10.
	 * @return The classified data set.
	 */
	public static String[][] classifyNumberInt(String[][] dataSet, int columnToClassify)
	{
		// TODO: Implement classifyNumnber
		return dataSet;
	}
	
	/**
	 * Classifies the column specified into the format first-low, first-medium, first-high
	 * 
	 * @param dataSet The data set to use.
	 * @param columnToClassify The column in the data set to classify. Must be a string containing a decimal value between 0 & 1.
	 * @return The classified data set.
	 */
	public static String[][] classifyNumberFirst(String[][] dataSet, int columnToClassify)
	{
		// TODO: Implement classifyNumnber		
		return dataSet;
	}
	
	/**
	 * Classifies the column specified into the format second-low, second-medium, second-high
	 * 
	 * @param dataSet The data set to use.
	 * @param columnToClassify The column in the data set to classify. Must be a string containing a decimal value between 0 & 1.
	 * @return The classified data set.
	 */
	public static String[][] classifyNumberSecond(String[][] dataSet, int columnToClassify)
	{
		// TODO: Implement classifyNumnber		
		return dataSet;
	}
}
