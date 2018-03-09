package ForUni;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Comparator;

import javax.imageio.ImageIO;

/**
 * The main model of the game, it all comes together here.
 */
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
	
	/**
	 * The model of the game.
	 * @param parent parent game
	 */
	public Model(Breakout parent) {
		gameOverAnimationTime = (int) (parent.desieredFramerate * 3);
		gameWonAnimationTime = (int) (parent.desieredFramerate * 4);
		this.parent = parent;
		
		// load all images (gamescreens)
		try {
			game_over = ImageIO.read(getClass().getClassLoader().getResourceAsStream("gameover.bmp"));
			game_won = ImageIO.read(getClass().getClassLoader().getResourceAsStream("gamewon.bmp"));
		} catch (Exception e) {
			System.out.println("Levels loaded from files: " + String.valueOf(lvl.size()));
		}
		
		// load images (levels)
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
		
		// initialize particlemanager
		particles = new ParticleManager(parent.picture, particleSystemResolution);
		// total levelcount (+1, because one level is hardcoded)
		levelStates = lvl.size() + 1;
	}
	
	/**
	 * Called everytime the game is updated.
	 */
	public void Update() {
		//switching between gamestates
		switch (parent.state) {
		case Playing:
			
			//update all balls
			for (int i = 0; i < bAALLLLZ.size(); i++)
				bAALLLLZ.get(i).update();
			
			//remove hit bricks
			parent.m.bricks.removeAll(removeList);
			
			gameTime++;
			ballSpeedMult = 0.4f + gameTime / parent.desieredFramerate / 95;
			
			particles.update();
			
			break;

		case Dead:
			
			if (gameOverTime == 0)
				// initialize new gameoverscreen with the current picture
				gameOverScreen = new ScreenGameOver(parent.picture, gameOverAnimationTime, game_over);
			
			// get picture that should be displayed
			parent.picture = gameOverScreen.getScreen(gameOverTime);
			gameOverTime++;
			
			// if animation time is over, restart the game
			if (gameOverTime >= gameOverAnimationTime)
				respawn();
			
			break;
			
		case Won:
			
			if (gameWonTime == 0) 
				// initialize new gamewonscreen with the current picture
				winScreen = new ScreenGameWon(parent.picture, gameWonAnimationTime, game_won);
			
			// get picture that should be displayed
			parent.picture = winScreen.getScreen(gameWonTime);
			gameWonTime++;
			
			// if animation time is over, restart the game
			if (gameWonTime >= gameWonAnimationTime)
				respawn();
			
			break;
		}
	}
	/**
	 * Loads the next level.
	 */
	protected void loadNewLevel() {
		gameOverTime = 0;
		gameWonTime = 0;
		// add one ball in starting position
		bAALLLLZ.clear();
		bAALLLLZ.add(new Ball(new Vector2(Breakout.housePixelsX / 2, Breakout.housePixelsY - 2),
				new Vector2(0, -ballStartSpeed), 1, Color.YELLOW, parent));
		
		// build the level
		bricks.clear();
		if (levelState == 0)
		{
			// hardcoded level
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
			// build level based on image
			Color c;
			for (int x = 0; x < 7; x++)
				for (int y = 0; y < 9; y++) {
					c = new Color(lvl.get(levelState - 1).getRGB(x, y));
					if (c.getRGB() != Color.WHITE.getRGB())
					{
						// manual transparency inplementation for ballspawns because java couldnt handle image-transparency
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
		
		// prepare for next loading
		levelState++;
		if (levelState >= levelStates)
			levelState = 0;
	}
	/**
	 * Returns the lowest ball on the canvas.
	 * @return the lowest ball
	 */
	public Ball getLowestBall()
	{
		return bAALLLLZ.stream().max(Comparator.comparing(Ball::getYPos)).
				orElse(new Ball(new Vector2(1, 1), new Vector2(0, 0), 0, Color.WHITE, parent));
	}
	/**
	 * called when game-over
	 */
	public void gameLost() {
		gameTime = 0;
		parent.state = GameState.Dead;
	}
	/**
	 * called when game is won
	 */
	public void gameWon() {
		gameTime = 0;
		parent.state = GameState.Won;
	}
	/**
	 * start new match (next level)
	 */
	public void respawn() {
		particles.clear();
		loadNewLevel();
		parent.state = GameState.Playing;
	}
	
	/**
	 * Draws the complete canvas.
	 */
	public void drawToPicture() {
		clearPicture();
		
		// lowest layer (least important) gets drawn first, then overridden if needed
		// draw particles
		particles.draw(parent.picture);
		
		// draw balls
		for (int i = 0; i < bAALLLLZ.size(); i++)
			bAALLLLZ.get(i).advancedDraw(parent.picture);
		
		// draw bricks
		for (int i = 0; i < bricks.size(); i++)
			bricks.get(i).draw(parent.picture);
		
		// draw paddle
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
