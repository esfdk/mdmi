package lab_3;

import java.util.ArrayList;

/**
 * A pair of values.
 * 
 * @author Jakob Melnyk 
 * @reference http://stackoverflow.com/questions/2670982/using-tuples-in-java
 *
 * @param <LeftType> Type of left value
 * @param <RightType> Type of right value
 */
public class Pair<LeftType, RightType> { 
	public final LeftType left; 
	public final RightType right; 
	
	/**
	 * A pair.
	 * 
	 * @param left The left value.
	 * @param right The right value.
	 */
	public Pair(LeftType left, RightType right) { 
		this.left = left; 
		this.right = right; 
	}
	
	/**
	 * Finds the index of a value if it exists.
	 * 
	 * @param al The array list containing the pairs to iterate.
	 * @param s The string to look for in the left side of the pairs.
	 * @return The index of the value matching the string, -1 if it is not in the list.  
	 */
	public static int indexOfAPair(ArrayList<Pair<String, Integer>> al, String s)
	{
		for(Pair<String, Integer> p : al)
		{
			if(p.left.equals(s))
			{
				return al.indexOf(p);
			}
		}
		return -1;
	}
} 