package ForUni;

/**
 * Computes user inputs.
 */
public class Control {
	Breakout parent;
	
	/**
	 * New Instance of the controls
	 * @param Parent parent game
	 */
	public Control(Breakout Parent)
	{
		this.parent = Parent;
	}
	
	/**
	 * Called on each update of the game.
	 */
	public void Update()
	{
		// only compute controls if game is running
		if (parent.state == GameState.Playing)
			computePaddlControls();
	}
	
	/**
	 * Sets the paddle to the x-position specified by the mouse-cursor (or cheatmode)
	 */
	private void computePaddlControls()
	{
		parent.m.paddlX = parent.mousePos.X / Breakout.pixelSizeX - parent.m.paddlWidth / 2;
		
		if (parent.m.paddlX < 0)
			parent.m.paddlX = 0;
		if (parent.m.paddlX > Breakout.housePixelsX - parent.m.paddlWidth)
			parent.m.paddlX = Breakout.housePixelsX - parent.m.paddlWidth;
		
		// cheatmode
		if (parent.control.getKeyChar() == 'c')
		{
			float ballX = parent.m.getLowestBall().getXPos();
			parent.m.paddlX = ballX - parent.m.paddlWidth / 2 + Extensions.rdm.nextFloat() * 6 - 3;
			
			if (parent.m.paddlX < 0)
				parent.m.paddlX = 0;
			if (parent.m.paddlX > Breakout.housePixelsX - parent.m.paddlWidth)
				parent.m.paddlX = Breakout.housePixelsX - parent.m.paddlWidth;
		}
	}
}
