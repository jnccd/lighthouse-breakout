package ForUni;

import java.awt.Color;
import java.awt.image.BufferedImage;

public class ScreenGameOver {

	private Color[][] picture;
	private double stagesPerScreen = 60;
	private double framesPerScreenStage;
	private int stageCount;
	private int stage2Count;

	private BufferedImage game_over;

	public ScreenGameOver(Color[][] startpic, int totalFrames, BufferedImage game_over) {
		this.picture = startpic;
		this.framesPerScreenStage = totalFrames / stagesPerScreen;
		this.game_over = game_over;
		stageCount = 0;
		stage2Count = 0;
	}
	
	public Color[][] getScreen(int gameWonTime) {
		if (gameWonTime > stageCount * framesPerScreenStage) {
			nextScreen();
			stageCount++;
			return picture;
		} else {
			return picture;
		}
	}
	
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
