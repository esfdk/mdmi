package jmhandin.algorithms.itemsets;

/**
 * Set of two items
 * 
 * @author Jakob Melnyk
 */
public class TwoItemSet {

	private String itemOne;
	private String itemTwo;
	
	public TwoItemSet()
	{
		
	}
	
	public TwoItemSet(String itemOne, String itemTwo)
	{
		this.itemOne = itemOne;
		this.itemTwo = itemTwo;
	}

	public String getItemOne() {
		return itemOne;
	}

	public String getItemTwo() {
		return itemTwo;
	}

	@Override
	public String toString() {
		return "TwoItemSet [itemOne=" + itemOne + ", itemTwo=" + itemTwo + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((itemOne == null) ? 0 : itemOne.hashCode());
		result = prime * result + ((itemTwo == null) ? 0 : itemTwo.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TwoItemSet other = (TwoItemSet) obj;
		if (itemOne == null) {
			if (other.itemOne != null)
				return false;
		} else if (!itemOne.equals(other.itemOne))
			return false;
		if (itemTwo == null) {
			if (other.itemTwo != null)
				return false;
		} else if (!itemTwo.equals(other.itemTwo))
			return false;
		return true;
	}
	
	
}
