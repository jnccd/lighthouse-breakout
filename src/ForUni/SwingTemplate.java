package ForUni;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import javax.swing.JComponent;
import javax.swing.JFrame;

public class SwingTemplate {
	private JFrame window = null;
	protected final Point windowSize;
	
	protected KeyEvent control;
	protected boolean mouseDown = false;
	protected Vector2 mousePos = new Vector2(0, 0);
	private final int mouseOffsetX = -6;
	private final int mouseOffsetY = -24;
	
	private int frameTime = 0;
	private float sleepTime = 0;
	protected float desieredFramerate;
	protected boolean drawFramerate = true;
	protected Color drawFramerateColor = Color.BLACK;
	
	/**
	 * This will prepare the window as well as the keyhooks and event listeners for the window
	 * @param Name The name for the window which will be displayed at its top right
	 * @param WindowSize The size of the window in pixel
	 * @param DesieredFramerate The framerate that the template will try to achieve in frames per second
	 */
	public SwingTemplate(String Name, Point WindowSize, int DesieredFramerate)
	{
		window = new JFrame(Name);
		control = new KeyEvent(window, 0, 0, 0, 0, KeyEvent.CHAR_UNDEFINED);
		this.windowSize = WindowSize;
		this.desieredFramerate = DesieredFramerate;
		
		setup();
		
		window.getContentPane().add(this.new Canvas());
		window.setVisible(true);
	}
	
	/**
	 * This will start the game loop
	 * all code after this method call will only ever be called when the game ends
	 * @throws Exception game errors
	 */
	public void start() throws Exception
	{
		while (true)
			pUpdate();
	}
	
	/**
	 * sets up the window and its key and mouse events
	 * 
	 * the result of the event will be stored inside values waiting for the game loop to use them
	 */
	private void setup()
	{
		window.setSize(windowSize.x, windowSize.y);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//		window.addMouseListener(new MouseAdapter() {
//            @Override
//            public void mousePressed(MouseEvent e) {
//            	//System.out.println("Mouse was pressed!");
//            	mousePos = new Vector2((float)e.getX() + mouseOffsetX, (float)e.getY() + mouseOffsetY);
//            	mouseDown = true;
//            }
//            @Override
//            public void mouseReleased(MouseEvent e) {
//            	//System.out.println("Mouse was released!");
//            	mousePos = new Vector2((float)e.getX() + mouseOffsetX, (float)e.getY() + mouseOffsetY);
//            	mouseDown = false;
//            }
//        });
		window.addMouseMotionListener(new MouseMotionAdapter() {
			public void mouseMoved(MouseEvent e) {
				mousePos = new Vector2((float)e.getX() + mouseOffsetX, (float)e.getY() + mouseOffsetY);
			}

//			public void mouseDragged(MouseEvent e) {
//				mousePos = new Vector2((float)e.getX() + mouseOffsetX, (float)e.getY() + mouseOffsetY);
//			}
		});
		window.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				control = e;
			}
			
			@Override
			public void keyReleased(KeyEvent e) {
				control = new KeyEvent(window, 0, 0, 0, 0, KeyEvent.CHAR_UNDEFINED);
			}
		});
	}
	
	/**
	 * the private update
	 * 
	 * calls the overridable update method and tries the keep the framerate stable at the "desieredFramerate"
	 * It also calls repaint on the windows content pane so the "Canvas.paint" method will be called and the updated data be drawn
	 * 
	 * @throws Exception game loop errors
	 */
	private void pUpdate() throws Exception
	{
		long StartTime = System.currentTimeMillis();
		
		update();
		//control = new KeyEvent(window, 0, 0, 0, 0, KeyEvent.CHAR_UNDEFINED);
		
		window.getContentPane().repaint();
		frameTime = (int)(System.currentTimeMillis() - StartTime);
		sleepTime = (1f / desieredFramerate * 1000f) - frameTime; if (sleepTime < 0) { sleepTime = 0; }
	    Thread.sleep((long)sleepTime);
	}
	
	/**
	 * provides a canvas class for the window
	 */
	private class Canvas extends JComponent{
		/**
		 * This paints to the windows content pane and is called when the content panes repaint method is called.
		 * 
		 * It calls the overridable draw method and draws the framerate if drawFramerate is true
		 * @param g the graphics to paint on
		 */
		public void paint(Graphics g){
			
			draw(g);
			
			g.setColor(drawFramerateColor);
			if (drawFramerate)
				g.drawString("FrameTime: " + frameTime + " FPS: " + (int)(1/(float)(frameTime + sleepTime)*1000) + " SleepTime: " + sleepTime, 12, 12);
		}
	}
	
	// Overridables
	/**
	 * the method in which the game logic will be computed
	 */
	public void update()
	{
		
	}
	/**
	 * the method in which the game can draw to the swing window
	 * @param g the graphics to paint on
	 */
	public void draw(Graphics g)
	{
		
	}
}