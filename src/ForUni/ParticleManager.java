package ForUni;

import java.awt.Color;
import java.util.ArrayList;

/**
 * Manages a bunch of particles
 */
public class ParticleManager {
	Color[][] superPicture;
	ArrayList<Particle> particles;
	int superSamplingMult;
	public ArrayList<Particle> removeList;
	
	/**
	 * Creates new particle manager object
	 * @param originalPicture original canvas
	 * @param superSamplingMult super sampling multiplier
	 */
	public ParticleManager(Color[][] originalPicture, int superSamplingMult)
	{
		this.superSamplingMult = superSamplingMult;
		
		superPicture = new Color[originalPicture.length * superSamplingMult]
				[originalPicture[0].length * superSamplingMult];
		
		clearSuper();
		
		particles = new ArrayList<Particle>();
		removeList = new ArrayList<Particle>();
	}
	/**
	 * Turns a brick into a bunch of particles
	 * @param b the original brick object
	 * @param ba the evil ball who destroyed the innocent brick
	 */
	public void turnToParticles(Brick b, Ball ba)
	{
		// Clone the innocent brick and draw it to a fake screen so we can use those 
		// fake screen pixels later to color our particles
		Brick clone = new Brick(0, 0, b.getHP() + 1, null, b.spwansBall);
		Color[][] fakePicture = new Color[Brick.width][Brick.height];
		clone.draw(fakePicture);
		
		// particle rectangle width and height
		int w = Brick.width * superSamplingMult;
		int h = Brick.height * superSamplingMult;
		
		// Add the particles 
		for (int x = 0; x < w; x++)
			for (int y = 0; y < h; y++)
	
				particles.add((new Particle(
						// starting position
						new Vector2(b.x * superSamplingMult + x, b.y * superSamplingMult + y),
						// starting velocity (away from the middle of the brick)
						new Vector2(x - w / 2f, (y - h / 4f)).Divide(Extensions.rdm.nextFloat() * 10 + 3f), 
						//new Vector2(0, 0), 
						//new Vector2((x - w / 2f) * (x - w / 2f), (y - h / 2f) * (y - h / 2f)).Divide(15),
						
						// color of the part of the brick the particle starts in
						fakePicture[x / superSamplingMult][y / superSamplingMult],
						// manager for this particle
						this, 
						// super sampled canvas width and height
						superPicture.length, superPicture[0].length,
						// particle lifetime in frames
						80, 
						// center of the spawning brick
						new Vector2(b.x * superSamplingMult + w / 2f, b.y * superSamplingMult + h / 2f))));
	}
	/**
	 * clear the super sampled canvas
	 */
	private void clearSuper()
	{
		for (int x = 0; x < superPicture.length; x++)
			for (int y = 0; y < superPicture[0].length; y++)
				superPicture[x][y] = Color.BLACK;
	}
	/**
	 * clear the particle list
	 */
	public void clear() {
		particles.clear();
	}
	
	/**
	 * update all the particles
	 */
	public void update()
	{
		for (int i = 0; i < particles.size(); i++)
			particles.get(i).update();
		
		particles.removeAll(removeList);
	}
	
	/**
	 * draw all the particles
	 * @param c canvas
	 */
	public void draw(Color[][] c)
	{
		// draw to the super sampled canvas
		clearSuper();
		for (int i = 0; i < particles.size(); i++)
			particles.get(i).draw(superPicture);
		
		// draw the super sampled canvas to the original canvas
		for (int i = 0; i < c.length; i++)
			for (int j = 0; j < c[0].length; j++)
			{
				Color[] d = new Color[superSamplingMult * superSamplingMult];
				for (int x = 0; x < superSamplingMult; x++)
					for (int y = 0; y < superSamplingMult; y++)
						d[x + y * superSamplingMult] = superPicture[x + i * superSamplingMult][y + j * superSamplingMult];
				
				Color avg = Extensions.Average(d);
				avg = Extensions.Mult(avg, superSamplingMult / 2);
				c[i][j] = Extensions.Add(c[i][j], avg);
			}
	}
}
