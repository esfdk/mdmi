package jghandin;

import java.util.ArrayList;

/**
 * A class that represents a cluster.
 * 
 * @author Jacob Grooss (jcgr@itu.dk)
 */
public class kMeansCluster
{
	private ArrayList<kMeansDataPoint> clusterPoints;

	private kMeansCentroid centroid;
	
	/**
	 * A cluster with a centroid.
	 * 
	 * @param centroid The centroid of the cluster
	 */
	public kMeansCluster(kMeansCentroid centroid)
	{
		this.clusterPoints = new ArrayList<kMeansDataPoint>();
		this.centroid = centroid;
	}
	
	/**
	 * Recalculates the centroid of the cluster
	 */
	private void RecalculateCentroid()
	{
		// If there are no data points in the cluster...
		if (this.clusterPoints.size() <= 0)
		{
			// ... don't bother calculating anything
			return;
		}
		
		double meanAge = 0, meanProgSkill = 0, meanUniStudy = 0;
		
		// Finds the total value of all data points in the cluster for each category
		for (kMeansDataPoint dp : this.clusterPoints)
		{
			meanAge += dp.getAge();
			meanProgSkill += dp.getProgSkill();
			meanUniStudy += dp.getUniStudy();
		}
		
		// Sets the value of the centroid to the new values
		this.centroid.setAge(meanAge / this.clusterPoints.size());
		this.centroid.setProgSkill(meanProgSkill / this.clusterPoints.size());
		this.centroid.setUniStudy(meanUniStudy / this.clusterPoints.size());
	}

	/**
	 * Adds a data point to the cluster, followed by a recalculation
	 * of the centroid.
	 * 
	 * @param dp The data point to add
	 */
	public void AddDataPoint(kMeansDataPoint dp)
	{
		dp.setClusterId(this.centroid.getId());
		this.clusterPoints.add(dp);
		this.RecalculateCentroid();
	}

	/**
	 * Removes a data point from the cluster, followed by a recalculation
	 * of the centroid.
	 * 
	 * @param dp The data point to remove
	 */
	public void RemoveDataPoint(kMeansDataPoint dp)
	{
		this.clusterPoints.remove(dp);
		dp.setClusterId(-1);
		this.RecalculateCentroid();
	}
	
	/**
	 * @return the clusterPoints
	 */
	public ArrayList<kMeansDataPoint> getClusterPoints()
	{
		return clusterPoints;
	}
	
	/**
	 * @return the centroid
	 */
	public kMeansCentroid getCentroid()
	{
		return centroid;
	}
	
	/**
	 * @return the Id
	 */
	public int getId()
	{
		return centroid.getId();
	}

	/**
	 * Auto-generated toString
	 * 
	 * @return
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString()
	{
		return "kMeansCluster [clusterPoints=" + clusterPoints + ", centroid="
				+ centroid + "]";
	}
}