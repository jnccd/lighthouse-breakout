package ForUni;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;

/**
 * This is the child of the swing template and actually implements the game
 */
public class Breakout extends SwingTemplate {
	final static int pixelSizeX = 13;
	final static int pixelSizeY = 40;
	final static int housePixelsX = 28;
	final static int housePixelsY = 14;
	final static int houseFramerate = 50;

	Color[][] picture = new Color[housePixelsX][housePixelsY];

	public Control c = new Control(this);
	public Model m = new Model(this);
	public ArrayList<View> views = new ArrayList<View>();

	GameState state;

	/**
	 * Prepares the control, model and view objects (which have no real purpose but were requested) 
	 * as well as the picture array which will be our canvas
	 */
	public Breakout() {
		super("Breakout", new Point(pixelSizeX * housePixelsX + 16, pixelSizeY * housePixelsY + 39), houseFramerate);

		drawFramerateColor = Color.RED;

		for (int x = 0; x < housePixelsX; x++)
			for (int y = 0; y < housePixelsY; y++)
				picture[x][y] = Color.BLACK;

		m.loadNewLevel();
		state = GameState.Playing;
		
		views.add(new ViewLocal());
		views.add(new ViewLightHouse("FynnNiklas", "API-TOK_//Mz-oSR5-gQn9-upe9-zU0+"));
	}

	@Override
	/**
	 * calls the control and models update methods
	 */
	public void update() {
		c.Update();
		m.Update();
	}

	@Override
	/**
	 * First the model draws itself to the picture array then all view objects within the view list get called to draw the picture array
	 */
	public void draw(Graphics g) {
		if (m != null && state == GameState.Playing)
			m.drawToPicture();

		if (views != null)
			for (View v : views)
				v.draw(g, picture, pixelSizeX, pixelSizeY);
	}

}
