package jghandin;

import java.util.ArrayList;

/**
 * A class that holds an implementation of the k-means algorithm.
 * 
 * @author Jacob Grooss (jcgr@itu.dk)
 */
public class kMeans
{
	private int amountOfDataPoints;
	private int amountofClusters;
	private ArrayList<kMeansDataPoint> dataPoints;
	private ArrayList<kMeansCluster> clusters;
	
	public kMeans(String[][] dataset, int amountOfClusters)
	{
		this.amountOfDataPoints = dataset.length;
		this.amountofClusters = amountOfClusters;
		this.dataPoints = new ArrayList<kMeansDataPoint>();
		this.clusters = new ArrayList<kMeansCluster>();
		
		// Adds the data to the dataPoints list.
		for(int i = 0; i < this.amountOfDataPoints; i++)
		{
			int age = Integer.parseInt(dataset[i][1]);
			int dob = Integer.parseInt(dataset[i][2]);
			int progSkill = Integer.parseInt(dataset[i][3]);
			int uniStudy = Integer.parseInt(dataset[i][4]);
			kMeansDataPoint dp = new kMeansDataPoint(i, age, dob, progSkill, uniStudy);
			dataPoints.add(dp);
		}
		
		this.makeInitialClusters();
		this.initialClustering();
		this.finishClustering();
	}
	
	public ArrayList<kMeansCluster> getClusters()
	{
		return this.clusters;
	}
	
	/**
	 * Makes the initial clusters by making each hold a centroid that holds
	 * values spread over the dataset.
	 */
	private void makeInitialClusters()
	{
		ArrayList<kMeansCentroid> centroids = new ArrayList<kMeansCentroid>();
		
		// Initializing the centroids with default values.
		for (int i = 0; i < this.amountofClusters; i++)
		{
			centroids.add(new kMeansCentroid(i));
		}
		
		// Arrays holding the lowest and highest values for age, dob, progSkill and uniStudy
		// 0 = age, 1 = dob, 2 = progSkill, 3 = uniStudy
		int[] lowest = new int[4];
		int[] highest = new int[4];
		
		// Setting default values, so the comparisons will work properly.
		for (int i = 0; i < lowest.length; i++)
		{
			lowest[i] = 10000000;
			highest[i] = 0;
		}
		
		// Finds the highest and lowest value for each of age, dob, progSkill and uniStudy
		for (kMeansDataPoint dp : dataPoints)
		{
			if (dp.getAge() < lowest[0]) lowest[0] = dp.getAge();
			if (dp.getAge() > highest[0]) highest[0] = dp.getAge();
			if (dp.getDob() < lowest[1]) lowest[1] = dp.getDob();
			if (dp.getDob() > highest[1]) highest[1] = dp.getDob();
			if (dp.getProgSkill() < lowest[2]) lowest[2] = dp.getProgSkill();
			if (dp.getProgSkill() > highest[2]) highest[2] = dp.getProgSkill();
			if (dp.getUniStudy() < lowest[3]) lowest[3] = dp.getUniStudy();
			if (dp.getUniStudy() > highest[3]) highest[3] = dp.getUniStudy();
		}
		
		// Changes the value of each centroid to split the data between
		// them with the same interval.
		for (kMeansCentroid kmC : centroids)
		{
			kmC.setAge(this.getValueWithInterval(lowest, highest, 0, kmC.getId()));
			kmC.setDob(this.getValueWithInterval(lowest, highest, 1, kmC.getId()));
			kmC.setProgSkill(this.getValueWithInterval(lowest, highest, 2, kmC.getId()));
			kmC.setUniStudy(this.getValueWithInterval(lowest, highest, 3, kmC.getId()));
			
			this.clusters.add(new kMeansCluster(kmC));
		}
	}
	
	/**
	 * Does the initial clustering.
	 */
	private void initialClustering()
	{
		// Iterate over all data points
		for (kMeansDataPoint dp : this.dataPoints)
		{
			int closestClusterIndex = -1;
			double eDistance = 10000000d;

			// Iterate over all clusters
			for (kMeansCluster cluster : this.clusters)
			{
				kMeansCentroid centroid = cluster.getCentroid();
				double tempDist = this.eDistance(dp, centroid);
				
				// If the distance between the data point and the cluster is less than
				// the current distance...
				if (tempDist < eDistance)
				{
					// ... save the id of the cluster and the distance
					closestClusterIndex = centroid.getId();
					eDistance = tempDist;
				}
			}

			// Add the data point to the closest cluster
			this.clusters.get(closestClusterIndex).AddDataPoint(dp);
		}
	}
	
	/**
	 * Finishes clustering by moving data points until they are at the
	 * "correct" cluster.
	 */
	private void finishClustering()
	{
		boolean isRunning = true;
		int iterations = 0;
		
		// While it's still running and hasn't exceeded a certain amount of loops...
		while (isRunning && iterations < this.amountofClusters * 100)
		{
			isRunning = false;
			
			// Iterate over all data points
			for (kMeansDataPoint kmDP : this.dataPoints)
			{
				boolean switchCluster = false;
				int newClusterIndex = -1;
				double currentDist = 0;
				
				// Get the Euclidean distance from the data point to it's current cluster
				currentDist = this.eDistance(kmDP, this.clusters.get(kmDP.getClusterId()).getCentroid());
				
				// Iterate over all clusters
				for (kMeansCluster kmClust : this.clusters)
				{
					// Get the Euclidean distance between the data point and the cluster
					double newDist = this.eDistance(kmDP, kmClust.getCentroid());
					
					// If the newDist is less than the currentDist...
					if (newDist < currentDist)
					{
						// ... save the id of the cluster and the distance and notify that the
						// data point needs to be moved
						switchCluster = true;
						newClusterIndex = kmClust.getId();
						currentDist = newDist;
					}
				}
				
				// If the data point is to be moved...
				if (switchCluster)
				{
					// ... remove it from it's current cluster and add it to the
					// closer cluster
					this.clusters.get(kmDP.getClusterId()).RemoveDataPoint(kmDP);
					this.clusters.get(newClusterIndex).AddDataPoint(kmDP);
					
					// Tell the loop to run again as changes were made
					isRunning = true;
				}
			}
			
			// Ensures that an endless loop won't happen
			iterations++;
		}
	}
	
	/**
	 * Finds the Euclidean distance between a data point and a centroid
	 * 
	 * @param dp The data point
	 * @param c The centroid
	 * @return The Euclidean distance
	 */
	private double eDistance(kMeansDataPoint dp, kMeansCentroid c)
	{
		double result;
		result = Math.pow((dp.getAge() - c.getAge()), 2) + Math.pow((dp.getDob() - c.getDob()), 2) +
				Math.pow((dp.getProgSkill() - c.getProgSkill()), 2) + Math.pow((dp.getUniStudy() - c.getUniStudy()), 2);
		result = Math.sqrt(result);
		
		return result;
	}

	/**
	 * Gets the value for a cetroid for a certain index in lowest/highest
	 * 
	 * @param lowest The array holding the lowest values
	 * @param highest The array holding the highest values
	 * @param index The value that tells what value in the arrays to access.
	 * 				0 = age
	 * 				1 = dob
	 * 				2 = progSkill
	 * 				3 = uniStudy
	 * @param centroidNumber The number of the centroid
	 * @return A value
	 */
	private double getValueWithInterval(int[] lowest, int[] highest, int index, int centroidNumber)
	{
		double interval = (double)(highest[index] - lowest[index]) / (this.amountofClusters - 1);
		double result = lowest[index] + (interval * centroidNumber);
		
		// Manipulating both ends a bit, so there are no extreme centroids in either end
		if (centroidNumber == 0) result += (interval / 2);
		if (centroidNumber == this.amountofClusters - 1) result -= (interval / 2);
		
		return result;
	}
}
