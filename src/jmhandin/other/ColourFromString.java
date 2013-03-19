package jmhandin.other;

import java.awt.Color;
import java.lang.reflect.Field;

/* 
 * JCommon : a free general purpose class library for the Java(tm) platform
 * 
 *
 * (C) Copyright 2000-2005, by Object Refinery Limited and Contributors.
 * 
 * Project Info:  http://www.jfree.org/jcommon/index.html
 *
 * This library is free software; you can redistribute it and/or modify it 
 * under the terms of the GNU Lesser General Public License as published by 
 * the Free Software Foundation; either version 2.1 of the License, or 
 * (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful, but 
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY 
 * or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public 
 * License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, 
 * USA.  
 *
 * [Java is a trademark or registered trademark of Sun Microsystems, Inc. 
 * in the United States and other countries.]
 * 
 * -------------------
 * PaintUtilities.java
 * -------------------
 * (C) Copyright 2003-2005, by Object Refinery Limited.
 *
 * Original Author:  David Gilbert (for Object Refinery Limited);
 * Contributor(s):   -;
 *
 * $Id: PaintUtilities.java,v 1.10 2007/11/02 17:50:37 taqua Exp $
 *
 * Changes
 * -------
 * 13-Nov-2003 : Version 1 (DG);
 * 04-Oct-2004 : Renamed PaintUtils --> PaintUtilities (DG);
 * 23-Feb-2005 : Rewrote equal() method with less indenting required (DG);
 *
 */
public class ColourFromString {

	  public static Color stringToColor(final String value) {
	    if (value == null) {
	      return Color.black;
	    }
	    try {
	      // get color by hex or octal value
	      return Color.decode(value);
	    } catch (NumberFormatException nfe) {
	      // if we can't decode lets try to get it by name
	      try {
	        // try to get a color by name using reflection
	        final Field f = Color.class.getField(value);

	        return (Color) f.get(null);
	      } catch (Exception ce) {
	        // if we can't get any color return black
	        return Color.black;
	      }
	    }
	  }
	}