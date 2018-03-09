package ForUni;

import java.awt.Color;

/**
 * This class implements the Ball.
 */
public class Ball {
	Breakout parent;
	Vector2 pos, vel;
	float radius;
	Color col;
	final static float movementPerUpdate = 0.1f;
	
	/**
	 * Creates a new Ball with specified position, velocity, size and color.
	 * @param Pos starting position of the ball
	 * @param Vel starting velocity of the ball
	 * @param Size size of the ball
	 * @param Col color of the ball
	 * @param Parent parent gameclass
	 */
	public Ball(Vector2 Pos, Vector2 Vel, float Size, Color Col, Breakout Parent)
	{
		this.pos = Pos;
		this.vel = Vel;
		this.radius = Size;
		this.parent = Parent;
		this.col = Col;
	}
	
	/**
	 * Computes the next Position, velocity and collisions.
	 * The movement is divided into smaller segments to make collision computation more reliable.
	 */
	void update()
	{
		Vector2 aVel = getActualVel();
		float velLength = aVel.Length();
		int Segments = (int)Math.ceil(velLength / movementPerUpdate);
		float SegmentLength = velLength / Segments;
		
		for (float f = 0; f <= velLength; f += SegmentLength)
		{
			// Velocity length doesnt matter for Border and Paddl collision
			computeBorderCollision();
			computePaddlCollision();
			computeBrickCollision(getActualVel().Normalize().Multiply(SegmentLength));
			
			pos = pos.Add(getActualVel().Normalize().Multiply(SegmentLength));
		}
	}
	/**
	 * Checks if the Ball hits any Bricks in the next Position update.
	 * @param velI update velocity
	 */
	private void computeBrickCollision(Vector2 velI)
	{
		Vector2 posX = new Vector2(pos.X + velI.X, pos.Y);
		Vector2 posY = new Vector2(pos.X, pos.Y +  velI.Y);
		
		for (Brick B : parent.m.bricks)
		{
			// Collision on x-Axis
			if (posX.X > B.x && posX.X - radius < B.x + Brick.width && 
				posX.Y > B.y && posX.Y - radius < B.y + Brick.height)
			{
				B.onDestruction(this);
				vel.X *= -1;
				break;
			}
			// Collision on y-Axis
			else if (posY.X > B.x && posY.X - radius < B.x + Brick.width && 
					 posY.Y > B.y && posY.Y - radius < B.y + Brick.height)
			{
				B.onDestruction(this);
				vel.Y *= -1;
				break;
			}
		}
	}
	/**
	 * Checks if the Ball hits the Paddl in the next position update.
	 */
	private void computePaddlCollision()
	{
		if ((int)pos.Y == parent.m.paddlY && pos.X > parent.m.paddlX - parent.m.paddlExtraHitbox &&
											 pos.X < parent.m.paddlX + parent.m.paddlWidth + parent.m.paddlExtraHitbox && vel.Y > 0)
		{
			// Adds velocity on x-Axis depending on where the paddle is hit
			vel.X = (pos.X - (parent.m.paddlX + parent.m.paddlWidth / 2) + vel.X) / 4;
			vel.Y *= -1;
		}
	}
	/**
	 * Checks if the Ball hits any border of the screen.
	 */
	private void computeBorderCollision()
	{
		if (pos.X - radius < 0 && vel.X < 0)
			vel.X *= -1;
		if (pos.X + radius > Breakout.housePixelsX + 0.5 && vel.X > 0)
			vel.X *= -1;
		if (pos.Y - radius < 0 && vel.Y < 0)
			vel.Y *= -1;
		if (pos.Y + radius > Breakout.housePixelsY + 3)
			lostInTheVoid();
	}
	/**
	 * Called when ball crosses the bottom border (ball gets removed).
	 */
	private void lostInTheVoid()
	{
		parent.m.bAALLLLZ.remove(this);
		
		// if no balls are left, game over
		if (parent.m.bAALLLLZ.isEmpty())
			parent.m.gameLost();
	}
	/**
	 * Ball gets faster when its further up on the screen and the game goes on longer to make it more interesting.
	 * @return actual used velocity depending on ballheight and gametime
	 */
	private Vector2 getActualVel()
	{
		float heightMultiplier = ((Breakout.housePixelsY - pos.Y) / Breakout.housePixelsY) / 2 + 0.75f;
		return vel.Multiply(parent.m.ballSpeedMult * heightMultiplier);
	}
	/**
	 * Get x-Pos of the ball
	 * @return x position
	 */
	public float getXPos()
	{
		return pos.X;
	}
	/**
	 * Get y-Pos of the ball
	 * @return y position
	 */
	public float getYPos()
	{
		return pos.Y;
	}
	
	/**
	 * First version of the draw-method for the ball
	 * @param Picture canvas (gamescreen)
	 */
	void simpleDraw(Color[][] Picture)
	{
		if ((int)pos.X > 0 && (int)pos.X < Picture.length && (int)pos.Y > 0 && (int)pos.Y < Picture[0].length)
			Picture[(int)pos.X][(int)pos.Y] = col;
	}
	/**
	 * Better version of the draw-method
	 * The Ball is simplified to a rectangle, this draws all pixels in which the ball exists depending on the overlapping percentage
	 * @param Picture canvas (gamescreen)
	 */
	void advancedDraw(Color[][] Picture)
	{
		float UpperLeftX = pos.X - radius;
		float UpperLeftY = pos.Y - radius;
		if (UpperLeftX > 0 && UpperLeftX < Breakout.housePixelsX && UpperLeftY > 0 && UpperLeftY < Breakout.housePixelsY)
			Picture[(int)UpperLeftX][(int)UpperLeftY] = Extensions.lerp(Picture[(int)UpperLeftX][(int)UpperLeftY], col, ((1 - (pos.X % 1)) * (1 - (pos.Y % 1))));
		
		float UpperRightX = pos.X;
		float UpperRightY = pos.Y - radius;
		if (UpperRightX > 0 && UpperRightX < Breakout.housePixelsX && UpperRightY > 0 && UpperRightY < Breakout.housePixelsY)
			Picture[(int)UpperRightX][(int)UpperRightY] = Extensions.lerp(Picture[(int)UpperRightX][(int)UpperRightY], col, ((pos.X % 1) * (1 - (pos.Y % 1))));
		
		float LowerLeftX = pos.X - radius;
		float LowerLeftY = pos.Y;
		if (LowerLeftX > 0 && LowerLeftX < Breakout.housePixelsX && LowerLeftY > 0 && LowerLeftY < Breakout.housePixelsY)
			Picture[(int)LowerLeftX][(int)LowerLeftY] = Extensions.lerp(Picture[(int)LowerLeftX][(int)LowerLeftY], col, ((1 - (pos.X % 1)) * (pos.Y % 1)));
		
		float LowerRightX = pos.X;
		float LowerRightY = pos.Y;
		if (LowerRightX > 0 && LowerRightX < Breakout.housePixelsX && LowerRightY > 0 && LowerRightY < Breakout.housePixelsY)
			Picture[(int)LowerRightX][(int)LowerRightY] = Extensions.lerp(Picture[(int)LowerRightX][(int)LowerRightY], col, ((pos.X % 1) * (pos.Y % 1)));
	}
}