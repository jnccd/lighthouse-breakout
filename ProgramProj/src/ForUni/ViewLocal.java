package ForUni;

import java.awt.Color;
import java.awt.Graphics;

public class ViewLocal extends View {
	
	@Override
	public void draw(Graphics g, Color[][] picture, int pixelSizeX, int pixelSizeY) {
		for (int x = 0; x < picture.length; x++)
			for (int y = 0; y < picture[0].length; y++)
			{
				g.setColor(picture[x][y]);
				g.fillRect(x * pixelSizeX, y * pixelSizeY, pixelSizeX, pixelSizeY);
			}
	}
}
