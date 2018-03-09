package ForUni;

import java.awt.Color;

/**
 * Implements particles
 */
public class Particle {
	ParticleManager parent;
	Vector2 pos, vel, origin;
	Color col;
	int maxX, maxY, lifeTimer = 0, lifeTime;
	
	/**
	 * Builds new particle object
	 * 
	 * @param pos start position
	 * @param vel start velocity
	 * @param col color
	 * @param parent manager
	 * @param maxX X-Border
	 * @param maxY Y-Border
	 * @param lifeTime The time until the particle is deleted in frames
	 * @param origin coordinates of the object this originates from
	 */
	public Particle(Vector2 pos, Vector2 vel, Color col, ParticleManager parent, int maxX, int maxY, int lifeTime, Vector2 origin)
	{
		this.pos = pos;
		this.vel = vel;
		this.col = col;
		this.maxX = maxX;
		this.maxY = maxY;
		this.parent = parent;
		this.lifeTime = lifeTime;
		this.origin = origin;
	}
	
	/**
	 * computes particle logic
	 */
	public void update()
	{
		lifeTimer++;
		
		// border collision
		if (pos.X < 0 && vel.X < 0)
			vel.X *= -1;
		if (pos.X > maxX && vel.X > 0)
			vel.X *= -1;
		if (pos.Y < 0 && vel.Y < 0)
			vel.Y *= -1;
		if (pos.Y > maxY && vel.Y > 0)
			vel.Y *= -1;
		
		//Vector2 toOrigin = origin.Subtract(pos);
		//vel = vel.Add(new Vector2(toOrigin.Y, -toOrigin.X / 2).Divide(50 * toOrigin.LengthSquared()));
		
		// Gravity
		vel.Y += 0.01f;
		
		// Screen particle friction
		vel = vel.Multiply(0.993f);
		pos = pos.Add(vel);
		
		// death
		if (lifeTimer > lifeTime)
			parent.removeList.add(this);
	}
	
	/**
	 * draws particle to the specific canvas
	 * @param Picture the canvas object
	 */
	public void draw(Color[][] Picture)
	{
		if ((int)pos.X > 0 && (int)pos.X < Picture.length && (int)pos.Y > 0 && (int)pos.Y < Picture[0].length)
			Picture[(int)pos.X][(int)pos.Y] = Extensions.lerp(col, Color.BLACK, lifeTimer / (float)lifeTime);
	}
}
