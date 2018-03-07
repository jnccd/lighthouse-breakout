package ForUni;

import java.awt.Color;
import java.awt.Graphics;

/**
 * Superclass of the other Views.
 */
public abstract class View {
	
	/**
	 * Draws the Color-array in the way defined by the subclass.
	 * @param g Graphics (used for Local view)
	 * @param picture the Color-array which has to be displayed.
	 * @param pixelSizeX pixel-size in x-Direction (for Local view)
	 * @param pixelSizeY pixel-size in y-Direction (for Local view)
	 */
	public abstract void draw(Graphics g, Color[][] picture, int pixelSizeX, int pixelSizeY);
}
