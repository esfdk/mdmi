package jghandin;

/**
 * This class represents a Centroid for the k-means algorithm.
 * No comments on getters/setters, as it's not necessary
 * 
 * @author Jacob Grooss (jcgr@itu.dk)
 */
public class kMeansCentroid
{
	private int id;
	private double age, progSkill, uniStudy;
	
	/**
	 * A centroid for the kMeans algorithm.
	 */
	public kMeansCentroid(int id)
	{
		this(id, 0.0, 0.0, 0.0);
	}
	
	/**
	 * A centroid for the kMeans algorithm.
	 * 
	 * @param id The id of the centroid
	 * @param age The value for age
	 * @param progSkill The value for progSkill
	 * @param uniStudy The value for uniStudy
	 */
	public kMeansCentroid(int id, double age, double progSkill, double uniStudy)
	{
		this.id = id;
		this.age = age;
		this.progSkill = progSkill;
		this.uniStudy = uniStudy;
	}

	public int getId()
	{
		return id;
	}

	public double getAge()
	{
		return age;
	}

	public void setAge(double age)
	{
		this.age = age;
	}

	public double getProgSkill()
	{
		return progSkill;
	}

	public void setProgSkill(double progSkill)
	{
		this.progSkill = progSkill;
	}

	public double getUniStudy()
	{
		return uniStudy;
	}

	public void setUniStudy(double uniStudy)
	{
		this.uniStudy = uniStudy;
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
		return "[centroid=" + id + ", age=" + age + ", progSkill=" + progSkill + ", uniStudy=" + uniStudy + "]";
	}
}
