package lab_3;

import lab_3.data.Bruises;
import lab_3.data.Cap_Color;
import lab_3.data.Cap_Shape;
import lab_3.data.Cap_Surface;
import lab_3.data.Class_Label;
import lab_3.data.GIll_Size;
import lab_3.data.Gill_Attachment;
import lab_3.data.Gill_Color;
import lab_3.data.Gill_Spacing;
import lab_3.data.Habitat;
import lab_3.data.Odor;
import lab_3.data.Population;
import lab_3.data.Ring_Number;
import lab_3.data.Ring_Type;
import lab_3.data.Spore_Print_Color;
import lab_3.data.Stalk_Color_Above_Ring;
import lab_3.data.Stalk_Color_Below_Ring;
import lab_3.data.Stalk_Root;
import lab_3.data.Stalk_Shape;
import lab_3.data.Stalk_Surface_Above_Ring;
import lab_3.data.Stalk_Surface_Below_Ring;
import lab_3.data.Veil_Color;
import lab_3.data.Veil_Type;

/**
 * The Mushroom class is used to contain the data for each mushroom found in the datafile.
 * 
 * @author Anders Hartzen
 *
 */
public class Mushroom {

	/**
	 * The attribute to built a classifier for.
	 */
	public Class_Label m_Class;
	
	public Cap_Shape m_cap_shape;
	
	public Cap_Surface m_cap_surface;
	
	public Cap_Color m_cap_color;
	
	public Bruises m_bruises;
	
	public Odor m_odor;
	
	public Gill_Attachment m_gill_attach;
	
	public Gill_Spacing m_gill_spacing;
	
	public GIll_Size m_gill_size;
	
	public Gill_Color m_gill_color;
	
	public Stalk_Shape m_stalk_shape;
	
	public Stalk_Root m_stalk_root;
	
	public Stalk_Surface_Above_Ring m_stalk_surface_above;
	
	public Stalk_Surface_Below_Ring m_stalk_surface_below;
	
	public Stalk_Color_Above_Ring m_stalk_color_above;
	
	public Stalk_Color_Below_Ring m_stalk_color_below;
	
	public Veil_Type m_veil_type;
	
	public Veil_Color m_veil_color;
	
	public Ring_Number m_ring_number;
	
	public Ring_Type m_ring_type;

	public Spore_Print_Color m_spore_color;
		
	public Population m_population;
	
	public Habitat m_habitat;
	
	@Override
	public String toString() {
		return "Mushroom [m_Class=" + m_Class + ", m_cap_shape=" + m_cap_shape
				+ ", m_cap_surface=" + m_cap_surface + ", m_cap_color="
				+ m_cap_color + ", m_bruises=" + m_bruises + ", m_odor="
				+ m_odor + ",\n m_gill_attach=" + m_gill_attach
				+ ", m_gill_spacing=" + m_gill_spacing + ", m_gill_size="
				+ m_gill_size + ", m_gill_color=" + m_gill_color
				+ ", m_stalk_shape=" + m_stalk_shape + ", m_stalk_root="
				+ m_stalk_root + ",\n m_stalk_surface_above="
				+ m_stalk_surface_above + ", m_stalk_surface_below="
				+ m_stalk_surface_below + ", m_stalk_color_above="
				+ m_stalk_color_above + ", m_stalk_color_below="
				+ m_stalk_color_below + ",\n m_veil_type=" + m_veil_type
				+ ", m_veil_color=" + m_veil_color + ", m_ring_number="
				+ m_ring_number + ", m_ring_type=" + m_ring_type
				+ ", m_spore_color=" + m_spore_color + ", m_population="
				+ m_population + ", m_habitat=" + m_habitat + "]";
	}
	
	public static int distance(Mushroom a, Mushroom b)
	{
		int dist = 0;
		
		if(!a.m_cap_shape.equals(b.m_cap_shape)) dist++;
		
		if(!a.m_cap_surface.equals(b.m_cap_surface)) dist++;
		
		if(!a.m_cap_color.equals(b.m_cap_color)) dist++;
		
		if(!a.m_bruises.equals(b.m_bruises)) dist++;
		
		if(!a.m_odor.equals(b.m_odor)) dist++;
		
		if(!a.m_gill_attach.equals(b.m_gill_attach)) dist++;
		
		if(!a.m_gill_spacing.equals(b.m_gill_spacing)) dist++;
		
		if(!a.m_gill_size.equals(b.m_gill_size)) dist++;
		
		if(!a.m_gill_color.equals(b.m_gill_color)) dist++;
		
		if(!a.m_stalk_shape.equals(b.m_stalk_shape)) dist++;
		
		if(!a.m_stalk_root.equals(b.m_stalk_root)) dist++;
		
		if(!a.m_stalk_surface_above.equals(b.m_stalk_surface_above)) dist++;
		
		if(!a.m_stalk_surface_below.equals(b.m_stalk_surface_below)) dist++;
		
		if(!a.m_stalk_color_above.equals(b.m_stalk_color_above)) dist++;
		
		if(!a.m_stalk_color_below.equals(b.m_stalk_color_below)) dist++;
		
		if(!a.m_veil_type.equals(b.m_veil_type)) dist++;
		
		if(!a.m_veil_color.equals(b.m_veil_color)) dist++;
		
		if(!a.m_ring_number.equals(b.m_ring_number)) dist++;
		
		if(!a.m_ring_type.equals(b.m_ring_type)) dist++;
		
		if(!a.m_spore_color.equals(b.m_spore_color)) dist++;
		
		if(!a.m_population.equals(b.m_population)) dist++;
		
		if(!a.m_habitat.equals(b.m_habitat)) dist++;
		
		return dist;
	}
	
}
