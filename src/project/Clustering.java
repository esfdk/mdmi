package project;

import java.util.List;
import project.kmeans.KMeans;
import project.enums.DataColumn;

/**
 * Clustering datamining algorithms.
 * 
 * @author Niklas Hansen
 */
public class Clustering {
    /**
     * Data clustering - static class.
     * Only contains static methods.
     */
    private Clustering()
    {
        // Static class - cannot be instantiated.
    }
    
    /**
     * Use the k-means algorithm on dataset, using attributes specified.
     * 
     * @param k The amount of clusters.
     * @param data The dataset to cluster.
     * @param attributes The attributes to cluster by.
     * @return k-means object.
     */
    public static KMeans KMeans(int k, String[][] data, List<DataColumn> attributes)
    {
        KMeans kmeans = new KMeans(k);
        kmeans.ClusterData(data, attributes);
        
        return kmeans;
    }
}
