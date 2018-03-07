package ForUni;

import java.awt.Color;
import java.awt.image.BufferedImage;

/**
 * This class implements the "Game-Over" animation.
 */
public class ScreenGameOver {

	private Color[][] picture;
	private double stagesPerScreen = 60;
	private double framesPerScreenStage;
	private int stageCount;
	private int stage2Count;

	private BufferedImage game_over;

	/**
	 * Constructs a new Instance of this class with given parameters.
	 * @param startpic The displays current picture
	 * @param totalFrames total amount of frames in which the animation is shown
	 * @param game_over the "game_over" image
	 */
	public ScreenGameOver(Color[][] startpic, int totalFrames, BufferedImage game_over) {
		this.picture = startpic;
		this.framesPerScreenStage = totalFrames / stagesPerScreen;
		this.game_over = game_over;
		stageCount = 0;
		stage2Count = 0;
	}
	
	/**
	 * Returns the screen to be displayed at the given time.
	 * @param gameOverTime the time since the game has been lost
	 * @return the screen to be displayed
	 */
	public Color[][] getScreen(int gameOverTime) {
		if (gameOverTime > stageCount * framesPerScreenStage) {
			nextScreen();
			stageCount++;
			return picture;
		} else {
			return picture;
		}
	}
	
	/**
	 * The main animation is created here.
	 * Depending on the stageCount, a different change to the screen is made.
	 */
	private void nextScreen() {
		for (int y = 0; y < Breakout.housePixelsY; y++) {
			if (stageCount < Breakout.housePixelsX) {
				picture[stageCount][y] = Color.RED;
			}
		}
		if (stageCount > 0 && stageCount < 35) {
			for (int x = 0; x < stageCount; x++) {
				if (x < Breakout.housePixelsX) {
					for (int y = 0; y < Breakout.housePixelsY; y++) {
						picture[x][y] = picture[x][y].darker();
					}
				}
			}
		}
		if (stageCount >= 35 && stageCount < 49) {
			Color c;
			for (int x = 0; x < Breakout.housePixelsX; x++) {
				c = new Color(game_over.getRGB(x, stage2Count));
				if (c.getRGB() != Color.WHITE.getRGB()) {
					picture[x][stage2Count] = c;
				}
			}
			stage2Count++;
		}

	}

}
