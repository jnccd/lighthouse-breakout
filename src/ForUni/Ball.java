package ForUni;

import java.awt.Color;

public class Ball {
	Breakout parent;
	Vector2 pos, vel;
	float radius;
	Color col;
	final static float movementPerUpdate = 0.1f;
	
	public Ball(Vector2 Pos, Vector2 Vel, float Size, Color Col, Breakout Parent)
	{
		this.pos = Pos;
		this.vel = Vel;
		this.radius = Size;
		this.parent = Parent;
		this.col = Col;
	}
	
	void update()
	{
		Vector2 aVel = getActualVel();
		float velLength = aVel.Length();
		int Segments = (int)Math.ceil(velLength / movementPerUpdate);
		float SegmentLength = velLength / Segments;
		
		for (float f = 0; f <= velLength; f += SegmentLength)
		{
			computeBorderCollision();
			computePaddlCollision();
			computeBrickCollision(getActualVel().Normalize().Multiply(SegmentLength));
			
			pos = pos.Add(getActualVel().Normalize().Multiply(SegmentLength));
		}
	}
	private void computeBrickCollision(Vector2 velI)
	{
		Vector2 posX = new Vector2(pos.X + velI.X, pos.Y);
		Vector2 posY = new Vector2(pos.X, pos.Y +  velI.Y);
		
		for (Brick B : parent.m.bricks)
		{
			if (posX.X > B.x && posX.X - radius < B.x + Brick.width && 
				posX.Y > B.y && posX.Y - radius < B.y + Brick.height)
			{
				B.onDestruction(this);
				vel.X *= -1;
				break;
			}
			else if (posY.X > B.x && posY.X - radius < B.x + Brick.width && 
					 posY.Y > B.y && posY.Y - radius < B.y + Brick.height)
			{
				B.onDestruction(this);
				vel.Y *= -1;
				break;
			}
		}
	}
	private void computePaddlCollision()
	{
		if ((int)pos.Y == parent.m.paddlY && pos.X > parent.m.paddlX - parent.m.paddlExtraHitbox &&
											 pos.X < parent.m.paddlX + parent.m.paddlWidth + parent.m.paddlExtraHitbox && vel.Y > 0)
		{
			vel.X = (pos.X - (parent.m.paddlX + parent.m.paddlWidth / 2) + vel.X) / 4;
			vel.Y *= -1;
		}
	}
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
	private void lostInTheVoid()
	{
		parent.m.bAALLLLZ.remove(this);
		
		if (parent.m.bAALLLLZ.isEmpty())
			parent.m.gameLost();
	}
	Vector2 getActualVel()
	{
		float heightMultiplier = ((Breakout.housePixelsY - pos.Y) / Breakout.housePixelsY) / 2 + 0.75f;
		return vel.Multiply(parent.m.ballSpeedMult * heightMultiplier);
	}
	public float getXPos()
	{
		return pos.X;
	}
	public float getYPos()
	{
		return pos.Y;
	}
	
	void simpleDraw(Color[][] Picture)
	{
		if ((int)pos.X > 0 && (int)pos.X < Picture.length && (int)pos.Y > 0 && (int)pos.Y < Picture[0].length)
			Picture[(int)pos.X][(int)pos.Y] = col;
	}
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