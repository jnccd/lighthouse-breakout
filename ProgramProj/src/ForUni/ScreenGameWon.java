package ForUni;

import java.awt.Color;
import java.awt.image.BufferedImage;

public class ScreenGameWon {

	private Color[][] picture;
	private double stagesPerScreen = 60;
	private double framesPerScreenStage;
	private int stageCount;
	private boolean blink;
	
	private Color c1;
	private Color c2;

	private BufferedImage game_won;

	public ScreenGameWon(Color[][] startpic, int totalFrames, BufferedImage game_won) {
		this.picture = startpic;
		this.framesPerScreenStage = totalFrames / stagesPerScreen;
		this.game_won = game_won;
		stageCount = 0;
		blink = true;
		
		c1 = Color.RED;
		c2 = Color.BLUE;
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
		if (stageCount < 15) {
			Color c;
			for (int y = 0; y < stageCount; y++) {
				for (int x = 0; x < Breakout.housePixelsX; x++) {
					c = new Color(game_won.getRGB(x, y));
					if (c.getRGB() != Color.WHITE.getRGB()) {
						picture[x][Breakout.housePixelsY - stageCount + y] = c;
					} else {
						picture[x][Breakout.housePixelsY - stageCount + y] = Color.BLACK;
					}
				}
			}
		}
		if(stageCount > 15 && stageCount % 6 == 0) {
			for(int x = 0; x < Breakout.housePixelsX; x++) {
				picture[x][0] = blink ? c1 : c2;
				picture[x][Breakout.housePixelsY-1] = blink ? c2 : c1;
				blink = !blink;
			}
			for(int y = 1; y < Breakout.housePixelsY - 1; y++) {
				picture[0][y] = blink ? c2 : c1;
				picture[Breakout.housePixelsX-1][y] = blink ? c1 : c2;
				blink = !blink;
			}
			blink = !blink;
		}
	}

}
