package ForUni;

/**
 * contains the main method
 */
public class Launcher {
	/**
	 * starts the breakout game using the swing template
	 * @param args the arguments the program is started with
	 */
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
