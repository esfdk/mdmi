package project;

/**
 * A class that represents a range between two values
 * 
 * @author Jacob Grooss
 */
public class Range 
{
	private float lowerEnd, higherEnd;

	public Range(float lowerEnd, float higherEnd)
	{
		this.lowerEnd = lowerEnd;
		this.higherEnd = higherEnd;
	}
	
	public float getLowerEnd()
	{
		return this.lowerEnd;
	}
	
	public float getHigherEnd()
	{
		return this.higherEnd;
	}
	
	/**
	 * Checks if the value is in range of the range
	 * 
	 * @param value The value to check
	 * @return True if the value is in range; False if not.
	 */
	public boolean inRange(int value)
	{		
		return (value <= this.higherEnd) && (value >= this.lowerEnd);
	}
	
	/**
	 * Checks if the value is in range of the range
	 * 
	 * @param value The value to check
	 * @return True if the value is in range; False if not.
	 */
	public boolean inRange(float value)
	{		
		return (value <= this.higherEnd) && (value >= this.lowerEnd);
	}
}
