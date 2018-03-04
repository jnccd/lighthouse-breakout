package ForUni;

import java.awt.Color;

public class Particle {
	ParticleManager parent;
	Vector2 pos, vel, origin;
	Color col;
	int maxX, maxY, lifeTimer = 0, lifeTime;
	
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
	
	public void update()
	{
		lifeTimer++;
		
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
		vel.Y += 0.01f;
		
		vel = vel.Multiply(0.999f);
		pos = pos.Add(vel);
		
		if (lifeTimer > lifeTime)
			parent.removeList.add(this);
	}
	
	void draw(Color[][] Picture)
	{
		if ((int)pos.X > 0 && (int)pos.X < Picture.length && (int)pos.Y > 0 && (int)pos.Y < Picture[0].length)
			Picture[(int)pos.X][(int)pos.Y] = Extensions.lerp(col, Color.BLACK, lifeTimer / (float)lifeTime + 0.2f);
	}
}
