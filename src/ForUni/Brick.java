package ForUni;

import java.awt.Color;

/**
 * Implements the destroyable Bricks.
 */
public class Brick {
	public int x;
	public int y;
	public final static int width = 4;
	public final static int height = 1;

	private Color Col;
	private Color startCol;
	private Color TargetCol;
	private int colorChangeTimer;
	private final int colorChangeTime = 25;

	Breakout parent;

	private int hp;
	private int hitCooldown = 0;
	public boolean spwansBall;

	/**
	 * Creates a new Brick depending on:
	 * @param x x-position of the brick
	 * @param y y-position of the brick
	 * @param startingHP starting hp of the brick
	 * @param parent parent game
	 * @param spwansBall does it spawn a ball on destruction
	 */
	public Brick(int x, int y, int startingHP, Breakout parent, boolean spwansBall) {
		this.x = x;
		this.y = y;
		hp = startingHP;
		this.parent = parent;
		this.spwansBall = spwansBall;
		updateColor(true);
	}

	/**
	 * Converts a Color to the corresponding BrickHP.
	 * @param c inputcolor
	 * @return brickHP
	 */
	public static int colorToBrickHP(Color c) {
		if (c.getRed() == 0 && c.getGreen() == 255 && c.getBlue() == 0) {
			// Color.Green
			return 1;
		} else if (c.getRed() == 224 && c.getGreen() == 192 && c.getBlue() == 0) {
			// Some shade of Orange
			return 2;
		} else if (c.getRed() == 0 && c.getGreen() == 0 && c.getBlue() == 255) {
			// Color.Blue
			return 3;
		} else if (c.getRed() == 255 && c.getGreen() == 0 && c.getBlue() == 0) {
			// Color.Red
			return 4;
		} else {
			return 1;
		}
	}

	/**
	 * If the Alpha of the color is not 255, the brick will spawn a ball
	 * (if the color is slightly transparent)
	 * @param c inputcolor
	 * @return true of it will spawn a ball, false if not
	 */
	public static boolean colorToBrickBallSpawn(Color c) {
		return c.getAlpha() < 255;
	}

	/**
	 * Updates the color of the brick
	 * @param immediate if true, color change is instant (without animation)
	 */
	public void updateColor(boolean immediate) {
		colorChangeTimer = 0;

		// Colors of the corresponding hp
		switch (hp) {
		case 1:
			TargetCol = Color.GREEN;
			break;

		case 2:
			TargetCol = Color.ORANGE;
			break;

		case 3:
			TargetCol = Color.BLUE;
			break;

		case 4:
			TargetCol = Color.RED;
			break;

		default:
			TargetCol = Color.MAGENTA;
			break;
		}

		if (immediate)
			colorChangeTimer = colorChangeTime;
		else
			startCol = Col;
	}

	/**
	 * Can the brick get hit?
	 * @return can the brick get hit?
	 */
	public boolean canBeHit() {
		return hitCooldown < 0;
	}

	/**
	 * Called when the brick gets hit.
	 * @param theBallResponsibleForThisHorrificDestruction ball that hits the brick
	 */
	public void onDestruction(Ball theBallResponsibleForThisHorrificDestruction) {
		if (canBeHit()) {
			hp--;
			updateColor(false);

			if (hp <= 0 && !parent.m.removeList.contains(this)) {
				parent.m.removeList.add(this);
				parent.m.particles.turnToParticles(this, theBallResponsibleForThisHorrificDestruction);

				if (spwansBall)
					parent.m.bAALLLLZ.add((new Ball(new Vector2(x + width / 2, y + 0.5f),
							new Vector2(0, parent.m.ballStartSpeed), 1, Color.YELLOW, parent)));

				if (parent.m.bricks.size() == 1) // this actually gets deleted from the list after the method call
					parent.m.gameWon();
			}

			hitCooldown = 2;
		}
	}

	/**
	 * Get current brickcolor
	 * @return current brickcolor
	 */
	public Color getColor() {
		return Col;
	}

	/**
	 * Get current brickHP
	 * @return current brickHP
	 */
	public int getHP() {
		return hp;
	}

	/**
	 * Draws the brick to the given canvas.
	 * @param Picture canvas (gamescreen)
	 */
	public void draw(Color[][] Picture) {
		hitCooldown--;
		colorChangeTimer++;

		// update coloranimation
		if (colorChangeTimer < colorChangeTime)
			Col = Extensions.lerp(startCol, TargetCol, colorChangeTimer / (float) colorChangeTime);
		else
			Col = TargetCol;

		// draws to the canvas
		for (int i = 0; i < width; i++)
			if (x + i >= 0 && x + i < Picture.length && y >= 0 && y < Picture[0].length)
				if (!spwansBall)
					if (i == 0 || i == width - 1)
						Picture[x + i][y] = Extensions.lerp(Col, Color.BLACK, 0.4f);
					else
						Picture[x + i][y] = Col;
				else if (i == 0 || i == width - 1)
					Picture[x + i][y] = Extensions.lerp(Col, Color.BLACK, 0.4f);
				else if (hp == 1)
					Picture[x + i][y] = Extensions.lerp(Col, Color.YELLOW, 0.8f);
				else
					Picture[x + i][y] = Extensions.lerp(Col, Color.YELLOW, 0.65f);
	}
}
