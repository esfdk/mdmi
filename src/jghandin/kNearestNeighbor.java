package jghandin;

import java.util.ArrayList;

/**
 * 
 * 
 * @author Jacob
 */
public class kNearestNeighbor
{
	/**
	 * Compares the nearest people and "predicts" if the person wants more of the given column
	 * 
	 * @param dataset The dataset
	 * @param nearest The list of nearest persons
	 * @param person The person to check for
	 * @param column The column to go through
	 * @param valueToCheckFor The value to check for
	 * @return True if he might want more; False otherwise
	 */
	public boolean wantMore(String[][] dataset, 
							ArrayList<Integer> nearest, 
							int person, 
							int column, 
							String valueToCheckFor)
	{
		boolean wantMore = false;
		int more = 0;
		
		for (Integer i : nearest)
		{
			if (dataset[i][column].equals(valueToCheckFor)) more++;
		}
		
		wantMore = more > (nearest.size() / 2);
		
		return wantMore;
	}
	
	/**
	 * Finds the k rows that are closest to the person
	 * 
	 * @param trainingData
	 * @param trainingRows
	 * @param person
	 * @param k
	 * @return
	 */
	private ArrayList<Integer> getKNearest(String[][] trainingData, ArrayList<Integer> trainingRows, int person, int k)
	{
		@SuppressWarnings("unchecked")
		Pair<Integer, Double>[] nearest = new Pair[k];
		
		// Initialize the pairs
		for (int i = 0; i < k; i++)
		{
			double distance = 10000000d;
			nearest[i] = new Pair<Integer, Double>(i, distance);
		}
		
		int trainingRowsSize = trainingRows.size();
		
		for (int i = 0; i < trainingRowsSize; i++)
		{
			// No point in calculating the distance from the row to itself
			if (i == person) continue;
			
			int index = trainingRows.get(i);
			double distance = this.eDistance(trainingData[person], trainingData[index]);
			
			int furthestRow = 0;
			
			// Find the row that's furthest from rowFrom of the nearest ones
			for(int j = 0; j < k; j++)
			{
				if(nearest[j].right > nearest[furthestRow].right)
				{
					furthestRow = j;
				}
			}
			
			// Replace the furthest nearest row if this row is closer
			if(nearest[furthestRow].right > distance)
			{
				nearest[furthestRow] = new Pair<Integer, Double>(person, distance);
			}
		}
		
		ArrayList<Integer> nearestRows = new ArrayList<Integer>();
		
		// Add the indicies of the closest row to the list
		for(Pair<Integer, Double> p : nearest)
		{
			nearestRows.add(p.left);
		}
		
		return nearestRows;
	}
	
	public void printResultsFor(String[][] dataset, int person, int k)
	{
		ArrayList<Integer> trainingRows = new ArrayList<Integer>();
		ArrayList<Integer> nearest = this.getKNearest(dataset, trainingRows, person, k);

		for (int i = 0; i < dataset.length; i++)
		{
			if (i == person) continue;
			trainingRows.add(i);
		}
		
		boolean winterTired = this.wantMore(dataset, nearest, person, EColumns.winter_tired.ordinal(), "Yes");
		System.out.println("Winter tired?");
		System.out.println(dataset[person][0] + " is predicted to be tired of winter: " + winterTired);
		System.out.println("Is " + dataset[person][0] + " tired of winter? " + dataset[person][EColumns.winter_tired.ordinal()]);
		System.out.println();
		
		boolean moreMountains = this.wantMore(dataset, nearest, person, EColumns.more_dk_mnts.ordinal(), "Yes");
		System.out.println("More mountains?");
		System.out.println(dataset[person][0] + " is predicted to want more mountains: " + moreMountains);
		System.out.println("Does " + dataset[person][0] + " want more mountains? " + dataset[person][EColumns.more_dk_mnts.ordinal()]);
		System.out.println();
	}
	
	/**
	 * Finds the Euclidean distance between two rows
	 * 
	 * @param from The first row
	 * @param to The second row
	 * @return The Euclidean distance
	 */
	private double eDistance(String[] from, String[] to)
	{
		// age, prog_skill and yrs_of_uni_study
		int[] columns = new int[] { 1, 3, 4 };
		double result = 0d;
		
		for (int i = 0; i < columns.length; i++)
		{
			result += Math.pow((DataTransformation.convertToInt(from[i]) - DataTransformation.convertToInt(to[i])), 2);
		}

		result = Math.sqrt(result);
		
		return result;
	}
}