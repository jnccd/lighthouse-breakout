package ForUni;

import java.awt.Color;
import java.util.Random;

/**
 * An Extension-class that implements multiple Methods that don't fit into the other classes.
 */
public class Extensions {
	
	public static Random rdm = new Random();
	public static final float floatPI = (float)Math.PI;
	
	/**
	 * Returns a Color that is a mixture between c1 and c2, with a being the percentage of c2.
	 * @param c1 color 1
	 * @param c2 color 2
	 * @param a percentage of c2
	 * @return the mixed color
	 */
	public static Color lerp(Color c1, Color c2, float a)
	{
		
		if (c1 == null || c2 == null)
			throw new NullPointerException();
		
		if (a < 0)
			a = 0;
		
		if (a > 1)
			a = 1;
		
		float b = 1 - a;
		return new Color((int)(c1.getRed() * b + c2.getRed() * a), (int)(c1.getGreen() * b + c2.getGreen() * a), 
				(int)(c1.getBlue() * b + c2.getBlue() * a), (int)(c1.getAlpha() * b + c2.getAlpha() * a));
	}
	
	/**
	 * Adds the RGB values of 2 given Colors to create a new one.
	 * @param c1 color 1
	 * @param c2 color 2
	 * @return added color
	 */
	public static Color Add(Color c1, Color c2)
	{
		if (c1 == null || c2 == null)
			throw new NullPointerException();
		
		int red = c1.getRed() + c2.getRed();
		int green = c1.getGreen() + c2.getGreen();
		int blue = c1.getBlue() + c2.getBlue();
		int alpha = c1.getAlpha() + c2.getAlpha();
		
		if (red > 255)
			red = 255;
		if (green > 255)
			green = 255;
		if (blue > 255)
			blue = 255;
		if (alpha > 255)
			alpha = 255;
		
		return new Color(red, green, blue, alpha);
	}
	
	/**
	 * Multiplies the given color with f.
	 * @param c1 color 1
	 * @param f value to multiply with
	 * @return multiplied color
	 */
	public static Color Mult(Color c1, float f)
	{
		if (c1 == null)
			throw new NullPointerException();
		
		int red = (int)(c1.getRed() * f);
		int green = (int)(c1.getGreen() * f);
		int blue = (int)(c1.getBlue() * f);
		int alpha = (int)(c1.getAlpha() * f);
		
		if (red > 255)
			red = 255;
		if (green > 255)
			green = 255;
		if (blue > 255)
			blue = 255;
		if (alpha > 255)
			alpha = 255;
		
		return new Color(red, green, blue, alpha);
	}
	
	/**
	 * Returns the average color of a given array of colors.
	 * @param c the array of colors
	 * @return the average color
	 */
	public static Color Average(Color[] c)
	{
		int r = 0, g = 0, b = 0, a = 0;
		
		for (int i = 0; i < c.length; i++)
		{
			r += c[i].getRed();
			g += c[i].getGreen();
			b += c[i].getBlue();
			a += c[i].getAlpha();
		}
		
		return new Color(r / c.length, g / c.length, b / c.length, a / c.length);
	}
}