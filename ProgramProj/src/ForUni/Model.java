package ForUni;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;

import javax.imageio.ImageIO;

import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils.Collections;

public class Model {
	Breakout parent;

	float paddlX = 0.3f;
	final int paddlY = Breakout.housePixelsY - 2;
	Color paddlColor = Color.RED;
	final int paddlWidth = 7;
	final float paddlExtraHitbox = 0.25f;

	private int levelState = 1;
	private final int levelStates;
	
	final int particleSystemResolution = 3;

	private int gameTime = 0;
	private int gameOverTime = 0;
	private final int gameOverAnimationTime;
	private int gameWonTime = 0;
	private final int gameWonAnimationTime;
	
	private ScreenGameWon winScreen;
	private ScreenGameOver gameOverScreen;
	
	ArrayList<Ball> bAALLLLZ = new ArrayList<Ball>();
	ArrayList<Brick> bricks = new ArrayList<Brick>();
	ArrayList<Brick> removeList = new ArrayList<Brick>();
	float ballSpeedMult = 0.4f;
	float ballStartSpeed = 0.5f;
	
	ArrayList<BufferedImage> lvl = new ArrayList<BufferedImage>();
	BufferedImage game_over = null;
	BufferedImage game_won = null;
	
	public ParticleManager particles;
	
	public Model(Breakout parent) {
		gameOverAnimationTime = (int) (parent.desieredFramerate * 3);
		gameWonAnimationTime = (int) (parent.desieredFramerate * 4);
		this.parent = parent;
		
		try {
			game_over = ImageIO.read(getClass().getClassLoader().getResourceAsStream("gameover.bmp"));
			game_won = ImageIO.read(getClass().getClassLoader().getResourceAsStream("gamewon.bmp"));
		} catch (Exception e) {
			System.out.println("Levels loaded from files: " + String.valueOf(lvl.size()));
		}
		
		int index = 1;
		while (true)
		{
			try {
				String filename = "lvl" + String.valueOf(index) + ".bmp";
				lvl.add(ImageIO.read(getClass().getClassLoader().getResourceAsStream(filename)));
				index++;
			} catch (Exception e) {
				try {
					String filename = "lvl" + String.valueOf(index) + ".png";
					lvl.add(ImageIO.read(getClass().getClassLoader().getResourceAsStream(filename)));
					index++;
				} catch (Exception ex) {
					break;
				}
			}
		}
		System.out.println("Levels loaded from files: " + String.valueOf(lvl.size()));
		
		particles = new ParticleManager(parent.picture, particleSystemResolution);
		levelStates = lvl.size() + 1;
	}
	
	public void Update() {
		switch (parent.state) {
		case Playing:
			
			for (int i = 0; i < bAALLLLZ.size(); i++)
				bAALLLLZ.get(i).update();
			
			parent.m.bricks.removeAll(removeList);
			
			gameTime++;
			ballSpeedMult = 0.4f + gameTime / parent.desieredFramerate / 95;
			
			particles.update();
			
			break;

		case Dead:
			
			if (gameOverTime == 0) {
				gameOverScreen = new ScreenGameOver(parent.picture, gameOverAnimationTime, game_over);
			}
			parent.picture = gameOverScreen.getScreen(gameOverTime);
			gameOverTime++;
			
			if (gameOverTime >= gameOverAnimationTime)
				respawn();
			
			break;
			
		case Won:
			
			if (gameWonTime == 0) {
				winScreen = new ScreenGameWon(parent.picture, gameWonAnimationTime, game_won);
			}
			parent.picture = winScreen.getScreen(gameWonTime);
			gameWonTime++;
			
			if (gameWonTime >= gameWonAnimationTime)
				respawn();
			
			break;
		}
	}
	protected void loadNewLevel() {
		gameOverTime = 0;
		gameWonTime = 0;
		bAALLLLZ.clear();
		bAALLLLZ.add(new Ball(new Vector2(Breakout.housePixelsX / 2, Breakout.housePixelsY - 2),
				new Vector2(0, -ballStartSpeed), 1, Color.YELLOW, parent));
		
		bricks.clear();
		if (levelState == 0)
		{
			for (int x = 0; x < 7; x++)
				for (int y = 0; y < 9; y++) {
					boolean dropsBall = x == Breakout.housePixelsX / Brick.width / 2;

					switch (y) {
					case 0:
						bricks.add(new Brick(x * Brick.width, y, 3, parent, dropsBall));
						break;
						
					case 1:
						bricks.add(new Brick(x * Brick.width, y, 2, parent, dropsBall));
						break;

					case 2:
						bricks.add(new Brick(x * Brick.width, y, 2, parent, dropsBall));
						break;

					case 3:
						bricks.add(new Brick(x * Brick.width, y, 1, parent, dropsBall));
						break;

					case 4:
						bricks.add(new Brick(x * Brick.width, y, 1, parent, dropsBall));
						break;

					case 5:
						bricks.add(new Brick(x * Brick.width, y, 1, parent, false));
						break;
					}
				}
		}
		else
		{
			Color c;
			for (int x = 0; x < 7; x++)
				for (int y = 0; y < 9; y++) {
					c = new Color(lvl.get(levelState - 1).getRGB(x, y));
					if (c.getRGB() != Color.WHITE.getRGB())
					{
						if (levelState == 1 && x == 6 && y == 5 || levelState == 1 && x == 1 && y == 7 || 
								levelState == 2 && x == 2 && y == 3 || levelState == 2 && x == 3 && y == 3 || levelState == 2 && x == 4 && y == 3|| 
								levelState == 3 && x == 2 && y == 3 || levelState == 3 && x == 4 && y == 3 ||
								levelState == 4 && x == 1 && y == 2 ||
								levelState == 6 && x == 3 && y == 1 || levelState == 6 && x == 4 && y == 1)
							bricks.add(new Brick(x * Brick.width, y, Brick.colorToBrickHP(c), 
									parent, true));
						else
							bricks.add(new Brick(x * Brick.width, y, Brick.colorToBrickHP(c), 
									parent, Brick.colorToBrickBallSpawn(c)));
					}
				}
		}
		
		levelState++;
		if (levelState >= levelStates)
			levelState = 0;
	}
	public Ball getLowestBall()
	{
		return bAALLLLZ.stream().min(Comparator.comparing(Ball::getYPos)).
				orElse(new Ball(new Vector2(1, 1), new Vector2(0, 0), 0, Color.WHITE, parent));
	}
	public void gameLost() {
		gameTime = 0;
		parent.state = GameState.Dead;
	}
	public void gameWon() {
		gameTime = 0;
		parent.state = GameState.Won;
		drawToPicture();
	}
	public void respawn() {
		particles.clear();
		loadNewLevel();
		parent.state = GameState.Playing;
	}
	
	public void drawToPicture() {
		clearPicture();
		
		particles.draw(parent.picture);
		
		for (int i = 0; i < bAALLLLZ.size(); i++)
			bAALLLLZ.get(i).advancedDraw(parent.picture);
		
		for (int i = 0; i < bricks.size(); i++)
			bricks.get(i).draw(parent.picture);
		
		drawPaddltoPicture();
	}
	private void clearPicture() {
		for (int x = 0; x < Breakout.housePixelsX; x++)
			for (int y = 0; y < Breakout.housePixelsY; y++)
				parent.picture[x][y] = Color.BLACK;
	}
	private void drawPaddltoPicture() {
		float RightPercentage = paddlX % 1;
		float LeftPercentage = 1 - RightPercentage;

		parent.picture[(int) paddlX][paddlY] = Extensions.lerp(parent.picture[(int) paddlX][paddlY], paddlColor,
				LeftPercentage);

		for (int i = 1; i < paddlWidth; i++)
			parent.picture[(int) paddlX + i][paddlY] = paddlColor;

		if (RightPercentage != 0)
			parent.picture[(int) paddlX + paddlWidth][paddlY] = Extensions
					.lerp(parent.picture[(int) paddlX + paddlWidth][paddlY], paddlColor, RightPercentage);
	}
}
