package project;

/**
 * A class that represents a range between two values
 * 
 * @author Jacob Grooss
 */
public class Range 
{
	public float lowerEnd, higherEnd;

	/**
	 * Constructs a new range.
	 * 
	 * @param lowerEnd The lower-end.
	 * @param higherEnd The higher-end.
	 */
	public Range(float lowerEnd, float higherEnd)
	{
		this.lowerEnd = lowerEnd;
		this.higherEnd = higherEnd;
	}
	
	/**
	 * Checks if the value is in range of the range (inclusive on lower-end, exclusive on higher-end)
	 * 
	 * @param value The value to check
	 * @return True if the value is in range; False if not.
	 */
	public boolean inRange(int value)
	{		
		return (value < this.higherEnd) && (value >= this.lowerEnd);
	}
	
	/**
	 * Checks if the value is in range of the range (inclusive on lower-end, exclusive on higher-end)
	 * 
	 * @param value The value to check
	 * @return True if the value is in range; False if not.
	 */
	public boolean inRange(float value)
	{		
		return (value < this.higherEnd) && (value >= this.lowerEnd);
	}
}
