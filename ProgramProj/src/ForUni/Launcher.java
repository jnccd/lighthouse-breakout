package ForUni;

import java.awt.Color;

public class Launcher {
	public static void main(String[] args)
	{
		try {
			
			Breakout game = new Breakout();
			game.start();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
