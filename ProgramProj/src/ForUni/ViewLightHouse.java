package ForUni;

import java.awt.Color;
import java.awt.Graphics;
import java.io.IOException;

public class ViewLightHouse extends View {

	private LighthouseDisplay display;

	public ViewLightHouse(String username, String token) {
		display = new LighthouseDisplay(username, token);
		
		try {
			display.connect();
		} catch (Exception e) {
			System.out.println("Connection failed: " + e.getMessage());
			e.printStackTrace();
		}
	}

	
	
	private void updateColors(Color[][] colors) {
		byte[] data = new byte[14*28*3];
		int counter = 0;
		
		for(int y = 0; y < 14; y++) {
			for(int x = 0; x < 28; x++) {
				data[counter] = (byte) colors[x][y].getRed();
				data[counter + 1] = (byte) colors[x][y].getGreen();
				data[counter + 2] = (byte) colors[x][y].getBlue();
				counter += 3;
			}
		}
		sendData(data);
	}
	
	private void sendData(byte[] data) {
		try {
			display.send(data);
		} catch (IOException e) {
			System.out.println("Connection failed: " + e.getMessage());
			e.printStackTrace();
		}
	}



	@Override
	public void draw(Graphics g, Color[][] picture, int pixelSizeX, int pixelSizeY) {
		updateColors(picture);
	}

}
