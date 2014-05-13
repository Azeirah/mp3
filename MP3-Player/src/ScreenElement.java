import javax.imageio.ImageIO;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;

public class ScreenElement {
	/**
	 * 
	 */
	protected boolean[][] dotArray;
	protected int width;
	protected int height;
	protected int locationX;
	protected int locationY;
	protected ArrayList<ScreenElement> childElements = new ArrayList<ScreenElement>();
	private boolean loaded = false;

	protected ScreenElement(int locationX, int locationY) {
		this.locationX = locationX;
		this.locationY = locationY;
	}
	
	protected ScreenElement(int locationX, int locationY,String _path) throws IOException {
		this.locationX = locationX;
		this.locationY = locationY;
		importImage(_path);
	}

	protected void addScreenElement(ScreenElement element) {
		childElements.add(element);
	}

	protected void removeScreenElement(ScreenElement element) {
		childElements.remove(element);
	}

	public boolean hasChildElements() {
		return childElements.size() == 0;
	}

	protected void drawScreenElements() {
		// teken al je eigen elementen.
		// Als je eigen elementen ook kinder elementen hebben, teken die dan
		// ook.
		// Dit maakt het makkelijk om bijvoorbeeld een knop met een randje te
		// maken. Dan kan de rand altijd zwart blijven
		// en dan kan je de binnenkant inverteren, bijvoorbeeld als je de knop
		// selecteert.
		for (ScreenElement element : childElements) {
			if (element.hasChildElements()) {
				element.drawScreenElements();
			}
			drawScreenElement(element);
		}
	}

	protected void drawScreenElement(ScreenElement element) {
		boolean[][] dotArray = element.getDotArray();
		for (int x = 0; x < element.getWidth(); x++) {
			for (int y = 0; y < element.getHeight(); y++) {
				if (dotArray[y][x]){
					setPixel(x + element.getLocationX(),
							y + element.getLocationY());
					Main.console.printDebug("X");
				}else{
					Main.console.printDebug(".");
				}
			}
		}
	}

	protected void setPixel(int x, int y) {
		// something something IO.writeLCD?
		//System.out.println("DE SETPIXEL METHODE IS LEEG, DEZE MOET NOG GEMAAKT WORDEN.");
	}

	public void fromBMPFile(File BMPFileName) throws IOException {
		BufferedImage image = ImageIO.read(BMPFileName);
		width = image.getWidth();
		height = image.getHeight();
		boolean[][] _array2D = new boolean[height][width];

		for (int yPixel = 0; yPixel < image.getHeight(); yPixel++) {
			for (int xPixel = 0; xPixel < image.getWidth(); xPixel++) {
				int color = image.getRGB(xPixel, yPixel);
				_array2D[yPixel][xPixel] = color == Color.BLACK.getRGB();
			}
		}
		dotArray = _array2D;
		loaded = true;
	}

	public void importImage(String _path) throws IOException {
		File _file = new File(_path);
		fromBMPFile(_file);
	}

	public void saveSerializedData(String filename) {
		if (loaded) {
			try {
				FileOutputStream fileOut = new FileOutputStream(filename);
				ObjectOutputStream out = new ObjectOutputStream(fileOut);
				out.writeObject(dotArray);
				out.close();
				fileOut.close();
			} catch (IOException i) {
				i.printStackTrace();
			}
		} else {
			System.out
					.println("Please load a DotArray from a BMP or serialized file before trying to serialize it.");
		}
	}

	public void invert() {
		for (int x = 0; x < dotArray.length; x++) {
			for (int y = 0; y < dotArray[x].length; y++) {
				dotArray[x][y] = !dotArray[x][y];
			}
		}
	}

	public void fromSerializedFile(String serializedFileName) {
		boolean[][] _array2D = null;
		try {
			FileInputStream fileIn = new FileInputStream(serializedFileName);
			ObjectInputStream in = new ObjectInputStream(fileIn);
			_array2D = (boolean[][]) in.readObject();
			in.close();
			fileIn.close();
		} catch (IOException i) {
			i.printStackTrace();
		} catch (ClassNotFoundException i) {
			i.printStackTrace();
		}
		dotArray = _array2D;
		loaded = true;
		width = dotArray[0].length;
		height = dotArray.length;
	}

	public boolean[][] getDotArray() {
		return dotArray;
	}

	public boolean isLoaded() {
		return loaded;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public int getLocationX() {
		return locationX;
	}

	public int getLocationY() {
		return locationY;
	}
}
