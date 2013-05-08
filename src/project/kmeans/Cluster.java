package project.kmeans;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Cluster for k-means algorithm.
 * 
 * @author Niklas Hansen
 */
public class Cluster
{
    private double[] centroid;
    private List<DataPoint> dataPoints;

    /**
     * Constructor for KMeans cluster.
     *
     * @param amountOfAttributes
     */
    public Cluster(int amountOfAttributes) {
        this.centroid = new double[amountOfAttributes];
        this.dataPoints = new ArrayList<DataPoint>();
    }

    /**
     * Returns the data points belonging to this cluster.
     *
     * @return data points.
     */
    public List<DataPoint> GetDataPoints() {
        return this.dataPoints;
    }
    
    public double[] GetCentroid()
    {
    	return this.centroid;
    }

    /**
     * Add data point to cluster and re-calculate centroid.
     *
     * @param dp DataPoint to add.
     */
    public void AddDataPoint(DataPoint dp) {
        this.dataPoints.add(dp);
    }

    /**
     * Remove data point from cluster and re-calculate centroid.
     *
     * @param dp DataPoint to remove.
     */
    public void RemoveDataPoint(DataPoint dp) {
        this.dataPoints.remove(dp);
    }

    /**
     * Set centroid values.
     *
     * @param newCentroid New centroid values.
     */
    public void SetCentroid(double[] newCentroid) {
        this.centroid = newCentroid;
    }

    /**
     * (Re)calculate centroid from data points.
     */
    public void CalculateCentroid() {
        // If empty, do nothing.
        if (this.dataPoints.size() <= 0) {
            return;
        }

        // Reset centroid data.
        for (int i = 0; i < centroid.length; i++) {
            centroid[i] = 0;
        }

        // Calculate sum of data points.
        for (DataPoint dp : this.dataPoints) {
            for (int i = 0; i < centroid.length; i++) {
                centroid[i] += dp.getAttribute(i);
            }
        }

        // Calculate mean values.
        int amountOfDataPoints = this.dataPoints.size();
        for (int i = 0; i < centroid.length; i++) {
            centroid[i] = (centroid[i] / amountOfDataPoints);
        }
    }

    /**
     * Calculates the Euclidean distance between data point and cluster
     * centroid.
     *
     * @param dataPoint The data point.
     * @return The distance between given data point and cluster centroid.
     */
    public double DistanceTo(DataPoint dataPoint) {
        double result = 0;

        for (int i = 0; i < this.centroid.length; i++) {
            result += Math.pow((dataPoint.getAttribute(i) - this.centroid[i]), 2);
        }

        result = Math.sqrt(result);
        return result;
    }

    /**
     * Calculates the median attribute value.
     *
     * @param attributeIndex The attribute to get median of.
     * @return The median.
     */
    public String GetMedianAttribute(int attributeIndex) {
        Map<String, Integer> seen = new HashMap<String, Integer>();

        // Iterate over data points in this cluster, and get the non-empty ones.
        for (DataPoint dataPoint : this.dataPoints) {
            if (dataPoint.getOriginalAttribute(attributeIndex).equals("-")) {
                continue;
            }

            int valToPut = 1;
            if (seen.containsKey(dataPoint.getOriginalAttribute(attributeIndex))) {
                valToPut = seen.get(dataPoint.getOriginalAttribute(attributeIndex)) + 1;
            }

            seen.put(dataPoint.getOriginalAttribute(attributeIndex), valToPut);
        }

        // Determine the value appearing the most.
        int maxSeen = 0;
        String maxSeenKey = "";
        for (String key : seen.keySet()) {
            if (seen.get(key) > maxSeen) {
                maxSeen = seen.get(key);
                maxSeenKey = key;
            }
        }
        
        return maxSeenKey;
    }
}
