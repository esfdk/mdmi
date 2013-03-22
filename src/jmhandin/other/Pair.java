package jmhandin.other;

import java.util.ArrayList;

/**
 * A pair of values.
 * 
 * @author Jakob Melnyk 
 * @reference http://stackoverflow.com/questions/2670982/using-tuples-in-java
 *
 * @param <A> Type of left value
 * @param <B> Type of right value
 */
public class Pair<A, B> { 
	public final A x; 
	public final B y; 
	
	/**
	 * A pair.
	 * 
	 * @param left The left value.
	 * @param right The right value.
	 */
	public Pair(A left, B right) { 
		this.x = left; 
		this.y = right; 
	}
} 