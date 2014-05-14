import java.io.IOException;

public class LCD extends ScreenElement {
	private int MOSI = 57;
	private int SCL = 58;
	private int A0 = 59;
	
	public LCD() throws IOException {
        super(0, 0);
        width = 240;
		height = 64;		
	}
	
	public void displayOn(){
		Main.io.writeBufferedLCD(0,(byte)0b10101111);
	}
	
	public void displayOff(){
		Main.io.writeBufferedLCD(0,(byte)0b10101110);
	}
	
	public void ledsOn() {
		Main.io.writeBufferedLCD(0,(byte)0b10100101);
	}

	public void ledsOff() {
		Main.io.writeBufferedLCD(0,(byte)0b10100100);
	}

	public void reset(){
		Main.io.writeBufferedLCD(0,(byte)0b11100010);
	}
	
	public void dataWrite(boolean _data){
		Main.io.writeLCD(1,_data);
	}
	
	public void flash(int _n, int _t) {
		for (int i = 0; i < _n; i++) {
			ledsOn();
			Main.io.sleep(_t,0);
			ledsOff();
			Main.io.sleep(_t,0);
		}
	}

	public void drawSinus() {
		for (int x = 0; x < width; x++) {
			float y = (float) (Math.sin(x / 8) * 8 + 16);
			setPixel(x, (int) y);
		}
	}

	public void draw(ScreenElement _element) {
		ledsOff();
		for (short x = 0; x < height; x++) {
			for (short y = 0; y < width; y++) {
				if (_element.getDotArray()[x][y]) {
					setPixel(x, y);
					Main.console.printDebug("█");
				} else {
					Main.console.printDebug(" ");
				}
			}
			System.out.println("");
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

	public int getMOSI() {
		return MOSI;
	}

	public int getSCL() {
		return SCL;
	}

	public int getA0() {
		return A0;
	}
}
