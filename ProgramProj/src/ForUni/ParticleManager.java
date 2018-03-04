package ForUni;

import java.awt.Color;
import java.util.ArrayList;

public class ParticleManager {
	Color[][] superPicture;
	ArrayList<Particle> particles;
	int superSamplingMult;
	public ArrayList<Particle> removeList;
	
	public ParticleManager(Color[][] originalPicture, int superSamplingMult)
	{
		this.superSamplingMult = superSamplingMult;
		
		superPicture = new Color[originalPicture.length * superSamplingMult]
				[originalPicture[0].length * superSamplingMult];
		
		clearSuper();
		
		particles = new ArrayList<Particle>();
		removeList = new ArrayList<Particle>();
	}
	
	public void turnToParticles(Brick b, Ball ba)
	{
		Brick clone = new Brick(0, 0, b.getHP() + 1, null, b.spwansBall);
		Color[][] fakePicture = new Color[b.width][b.height];
		clone.draw(fakePicture);
		
		int w = b.width * superSamplingMult;
		int h = b.height * superSamplingMult;
		
		for (int x = 0; x < w; x++)
			for (int y = 0; y < h; y++)
				particles.add((new Particle(new Vector2(b.x * superSamplingMult + x, b.y * superSamplingMult + y),  
						new Vector2(x - w / 2f, (y - h / 4f) * 1.5f).Divide(Extensions.rdm.nextFloat() * 15 + 3f), 
						fakePicture[x / superSamplingMult][y / superSamplingMult], this, 
						superPicture.length, superPicture[0].length, 80, 
						new Vector2(b.x * superSamplingMult + w / 2f, b.y * superSamplingMult + h / 2f))));
	}
	private void clearSuper()
	{
		for (int x = 0; x < superPicture.length; x++)
			for (int y = 0; y < superPicture[0].length; y++)
				superPicture[x][y] = Color.BLACK;
	}
	public void clear() {
		particles.clear();
	}
	
	public void update()
	{
		for (int i = 0; i < particles.size(); i++)
			particles.get(i).update();
		
		particles.removeAll(removeList);
	}
	
	public void draw(Color[][] c)
	{
		clearSuper();
		
		for (int i = 0; i < particles.size(); i++)
			particles.get(i).draw(superPicture);
		
		for (int i = 0; i < c.length; i++)
			for (int j = 0; j < c[0].length; j++)
			{
				Color[] d = new Color[superSamplingMult * superSamplingMult];
				for (int x = 0; x < superSamplingMult; x++)
					for (int y = 0; y < superSamplingMult; y++)
						d[x + y * superSamplingMult] = superPicture[x + i * superSamplingMult][y + j * superSamplingMult];
				
				Color avg = Extensions.Average(d);
				avg = Extensions.Mult(avg, 2f);
				c[i][j] = Extensions.Add(c[i][j], avg);
			}
	}
}
