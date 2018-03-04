package ForUni;

import java.awt.Color;
import java.awt.Graphics;

public abstract class View {
	public abstract void draw(Graphics g, Color[][] picture, int pixelSizeX, int pixelSizeY);
}
