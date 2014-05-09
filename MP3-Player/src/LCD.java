import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
//Now we just need a way to actually write them via the IO class.
public class LCD {
	private int width;
	private int height;

	public void init() throws IOException {

	}

	public void ledsOn() {

	}

	public void ledsOff() {

	}

	public void flash(int _n, int _t) {
		for (int i = 0; i < _n; i++) {
			ledsOn();
			Main.io.sleep(_t);
			ledsOff();
			Main.io.sleep(_t);
		}

	}

	public void setPixel(int x, int y) {

	}

	public void drawSinus() {
		for (int x = 0; x < width; x++) {
			float y = (float) (Math.sin(x / 8) * 8 + 16);
			setPixel(x, (int) y);
		}
	}

	public void intro(int[][] _array) {
		ledsOff();
		for (short x = 0; x < width; x++) {// LCD.intro.length
			for (short y = 0; y < height; y++) {// LCD.intro[x].length
				if (_array[y][x] == 1) {// LCD.intro[x][y] == 1
					setPixel(x, y);
				}
			}
		}
	}

	public void print(int[][] _array2D) {
		for (int x = 0; x < _array2D.length; x++) {
			for (int y = 0; y < _array2D[x].length; y++) {
				System.out.print(_array2D[x][y]);
			}
			System.out.println();
		}
	}

	public int[][] loadBMPImage(String BMPFileName) throws IOException {
		BufferedImage image = ImageIO.read(getClass().getResource(BMPFileName));
		int[][] _array2D = new int[image.getHeight()][image.getWidth()];

		for (int yPixel = 0; yPixel < image.getHeight(); yPixel++) {
			for (int xPixel = 0; xPixel < image.getWidth(); xPixel++) {
				int color = image.getRGB(xPixel, yPixel);
				if (color == Color.BLACK.getRGB()) {
					_array2D[yPixel][xPixel] = 1;
				} else {
					_array2D[yPixel][xPixel] = 0; // ?
				}
			}
		}
		return _array2D;
	}

	public int[][] invert(int[][] array2D) {
		for (int x = 0; x < array2D.length; x++) {
			for (int y = 0; y < array2D[x].length; y++) {
				int cell = array2D[x][y];
				if (cell == 1) {
					array2D[x][y] = 0;
				} else if (cell == 0) {
					array2D[x][y] = 1;
				}
			}
		}
		return array2D;
	}
}
