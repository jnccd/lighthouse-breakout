package ForUni;

public class Control {
	Breakout parent;
	
	public Control(Breakout Parent)
	{
		this.parent = Parent;
	}
	
	public void Update()
	{
		if (parent.state == GameState.Playing)
			computePaddlControls();
	}
	
	private void computePaddlControls()
	{
		parent.m.paddlX = parent.mousePos.X / Breakout.pixelSizeX - parent.m.paddlWidth / 2;
		
		if (parent.m.paddlX < 0)
			parent.m.paddlX = 0;
		if (parent.m.paddlX > Breakout.housePixelsX - parent.m.paddlWidth)
			parent.m.paddlX = Breakout.housePixelsX - parent.m.paddlWidth;
	}
}
