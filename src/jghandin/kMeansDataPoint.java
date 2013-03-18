package jghandin;

/**
 * This class represents a point of data for the k-means algorithm.
 * No comments on getters/setters, as it's not necessary
 * 
 * @author Jacob Grooss (jcgr@itu.dk)
 */
public class kMeansDataPoint
{
	private int originalID, age, dob, progSkill, uniStudy, clusterId;
	
	/**
	 * A data point that holds data about a person. 
	 * 
	 * @param originalID The value for originalID. Is used to find the point in
	 * 					 the original dataset, if necessary.
	 * @param age The value for age
	 * @param dob The value for dob
	 * @param progSkill The value for progSkill
	 * @param uniStudy The value for uniStudy
	 */
	public kMeansDataPoint(int originalID, int age, int dob, int progSkill, int uniStudy)
	{
		this.originalID = originalID;
		this.age = age;
		this.dob = dob;
		this.progSkill = progSkill;
		this.uniStudy = uniStudy;
	}

	public int getOriginalID()
	{
		return originalID;
	}

	public int getAge()
	{
		return age;
	}

	public int getDob()
	{
		return dob;
	}

	public int getProgSkill()
	{
		return progSkill;
	}

	public int getUniStudy()
	{
		return uniStudy;
	}

	public int getClusterId()
	{
		return clusterId;
	}

	public void setClusterId(int cluster)
	{
		this.clusterId = cluster;
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
		return "kMeansDataPoint [originalID=" + originalID + ", age=" + age
				+ ", cluster=" + clusterId + "]";
	}

	/**
	 * Auto-generated hashcode
	 * 
	 * @return
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + age;
		result = prime * result + dob;
		result = prime * result + originalID;
		result = prime * result + progSkill;
		result = prime * result + uniStudy;
		return result;
	}

	/**
	 * Auto-generated equals
	 * 
	 * @param obj
	 * @return
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		kMeansDataPoint other = (kMeansDataPoint) obj;
		if (age != other.age)
			return false;
		if (dob != other.dob)
			return false;
		if (originalID != other.originalID)
			return false;
		if (progSkill != other.progSkill)
			return false;
		if (uniStudy != other.uniStudy)
			return false;
		return true;
	}
}