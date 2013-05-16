package project.kmeans;

import java.util.ArrayList;
import java.util.List;
import project.enums.DataColumn;

/**
 * K-means algorithm.
 * 
 * @author Niklas Hansen
 */
public class KMeans
{
    private int k;
    private String[][] data;
    public int[] attributeIndexes;
    private List<Cluster> clusters;
    private List<DataPoint> dataPoints;
    
    /**
     * Constructor for KMeans.
     * 
     * @param k Amount of clusters.
     */
    public KMeans(int k)
    {
        this.k = k;
    }
    
    /**
     * Do clustering of data on specified columns.
     * 
     * @param data Data to cluster.
     * @param attributes Columns to cluster by.
     */
    public void ClusterData(String[][] data, List<DataColumn> attributes)
    {
        this.data = data;
        
        // Convert enum attributes to indexes.
        this.attributeIndexes = new int[attributes.size()];
        for (int i = 0; i < attributeIndexes.length; i++)
        {
            this.attributeIndexes[i] = attributes.get(i).ordinal();
        }
        
        // TODO: Reconsider the following.
        int amountOfAttributes = this.attributeIndexes.length;
        
        this.clusters = new ArrayList<Cluster>();
        for (int i = 0; i < this.k; i++)
        {
            this.clusters.add(new Cluster(amountOfAttributes));
        }
        
        this.makeDataPoints();
        this.establishClusters();
        this.distributeDataPoints();
        this.doClustering();
    }
    
    /**
     * Add a new data point to some cluster and do clustering.
     * 
     * @param dataPoint The data point to add.
     */
    public void AddDataPoint(DataPoint dataPoint)
    {
        this.clusters.get(0).AddDataPoint(dataPoint);
        this.doClustering();
    }
    
    /**
     * Returns the clusters.
     */
    public List<Cluster> GetClusters()
    {
        return this.clusters;
    }
    
    /**
     * Converting raw data to data points.
     */
    private void makeDataPoints()
    {
        this.dataPoints = new ArrayList<DataPoint>();
        
        for (String[] dataLine : this.data)
        {
            dataPoints.add(new DataPoint(dataLine, this.attributeIndexes));
        }
    }
    
    /**
     * Establish the k-means clusters.
     */
    private void establishClusters()
    {
        // Init arrays for lowest and highest values.
        double[] lowest = new double[this.attributeIndexes.length];
        double[] highest = new double[this.attributeIndexes.length];
        
        for (int i = 0; i < this.attributeIndexes.length; i++)
        {
            lowest[i] = Double.MAX_VALUE;
            highest[i] = Double.MIN_VALUE;
        }
        
        // Loop through datapoints
        for (DataPoint dataPoint : this.dataPoints)
        {
            // Loop through attributes
            for (int i = 0; i < this.attributeIndexes.length; i++)
            {
                if (dataPoint.getAttribute(i) < lowest[i]) lowest[i] = dataPoint.getAttribute(i);
                if (dataPoint.getAttribute(i) > highest[i]) highest[i] = dataPoint.getAttribute(i);
            }
        }
        
        // Set centroid values, spread out over the data set.
        for (int i = 0; i < this.clusters.size(); i++)
        {
            double[] newCentroid = new double[this.attributeIndexes.length];
            
            for (int j = 0; j < newCentroid.length; j++)
            {
                double interval = (highest[j] - lowest[j]) / (this.k /*- 1*/);
                
                newCentroid[j] = lowest[j] + (interval * (i+1));
            }
            
            this.clusters.get(i).SetCentroid(newCentroid);
        }
    }
    
    /**
     * Initial distribution of the data points to the clusters.
     */
    private void distributeDataPoints()
    {
        // Loop through the data points.
        for (DataPoint dataPoint : this.dataPoints)
        {
            int indexClosestCluster = -1;
            double distance = Double.MAX_VALUE;
            
            // Loop through the clusters, and calculate the distance.
            for (int i = 0; i < this.clusters.size(); i++)
            {
                double distanceToCluster = this.clusters.get(i).DistanceTo(dataPoint);
                
                // Is this cluster closer?
                if (distanceToCluster < distance)
                {
                    indexClosestCluster = i;
                    distance = distanceToCluster;
                }
            }
            
            // Assign data point to closest cluster.
            this.clusters.get(indexClosestCluster).AddDataPoint(dataPoint);
        }
        
        // Calculate centroid values from the new data points.
        for (Cluster cluster : this.clusters)
        {
            cluster.CalculateCentroid();
        }
    }
    
    /**
     * Do the clustering.
     */
    private void doClustering()
    {
        boolean changed = false;
        int iterations = 0;
        
        do
        {
            changed = false;
            iterations++;
            
            // Loop through the data points, and calculate the distance to current cluster.
            for (DataPoint dataPoint : this.dataPoints)
            {
                boolean changeCluster = false;
                int indexNewCluster = -1;
                int indexOldCluster = this.getClusterIndexFromDataPoint(dataPoint);
                double currDist = this.clusters.get(indexOldCluster).DistanceTo(dataPoint);

                // Determine whether a data point is closer to another cluster.
                for (int i = 0; i < this.clusters.size(); i++)
                {
                    double newDist = this.clusters.get(i).DistanceTo(dataPoint);

                    if (newDist < currDist)
                    {
                        changeCluster = true;
                        indexNewCluster = i;
                        currDist = newDist;
                    }
                }

                // If other cluster is closer, then switch to that cluster.
                if (changeCluster)
                {
                    this.clusters.get(indexOldCluster).RemoveDataPoint(dataPoint);
                    this.clusters.get(indexNewCluster).AddDataPoint(dataPoint);
                    
                    changed = true;
                }
            }
            
            // Calculate centroid values from new data points, if anything changed.
            if (changed)
            {
                for (Cluster cluster : this.clusters)
                {
                    cluster.CalculateCentroid();
                }
            }
            
        } while (changed && iterations < 100); // Loop while changes are made (within 100 iterations max).
    }
    
    /**
     * Get cluster index from data point.
     * 
     * @param dataPoint The data point.
     * @return The cluster index.
     */
    public int getClusterIndexFromDataPoint(DataPoint dataPoint)
    {
        // Loop through the clusters...
        for (int i = 0; i < this.clusters.size(); i++)
        {
            // ... and check if they contain the given data point.
            if (this.clusters.get(i).GetDataPoints().contains(dataPoint))
            {
                return i;
            }
        }
        
        // If not found, return -1.
        return -1;
    }
}
