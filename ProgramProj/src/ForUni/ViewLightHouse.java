package ForUni;

import java.awt.Color;
import java.awt.Graphics;
import java.io.IOException;

/**
 * The View-class that implements the Lighthouse.
 */
public class ViewLightHouse extends View {

	private LighthouseDisplay display;

	/**
	 * Constructs a new LighthouseView with given Username and API-Token.
	 * 
	 * @param username
	 *            the Username of the Account
	 * @param token
	 *            the API-Token (has to be the latest version)
	 */
	public ViewLightHouse(String username, String token) {
		display = new LighthouseDisplay(username, token);

		try {
			display.connect();
		} catch (Exception e) {
			System.out.println("Connection failed: " + e.getMessage());
			e.printStackTrace();
		}
	}

	/**
	 * Encodes a 2-dimensional Color-array to a byte-array
	 * 
	 * @param colors
	 *            the 2-dim. Color-array
	 */
	private void updateColors(Color[][] colors) {
		byte[] data = new byte[14 * 28 * 3];
		int counter = 0;

		for (int y = 0; y < 14; y++) {
			for (int x = 0; x < 28; x++) {
				data[counter++] = (byte) colors[x][y].getRed();
				data[counter++] = (byte) colors[x][y].getGreen();
				data[counter++] = (byte) colors[x][y].getBlue();
			}
		}
		sendData(data);
	}

	/**
	 * Sends the Data (Colors) to the lighthouse.
	 * 
	 * @param data
	 *            Colors encoded in a byte-array
	 */
	private void sendData(byte[] data) {
		try {
			display.send(data);
		} catch (IOException e) {
			System.out.println("Connection failed: " + e.getMessage());
			e.printStackTrace();
		}
	}

	/**
	 * Draws the given Color-array to the lighthouse.
	 */
	public void draw(Graphics g, Color[][] picture, int pixelSizeX, int pixelSizeY) {
		updateColors(picture);
	}

}
