import java.io.IOException;

//Now we just need a way to actually write them via the IO class.
public class LCD extends ScreenElement {
    /**
     * @throws IOException 
	 * 
	 */
	public LCD() throws IOException {
        super(0, 0);
        width = 240;
		height = 64;
	}
	

	
	public void displayOn(){
		Main.io.writeLCD(0,1,0,10101111);
	}
	
	public void displayOff(){
		Main.io.writeLCD(0,1,0,10101110);
	}
	
	public void ledsOn() {
		Main.io.writeLCD(0,1,0,10100101);
	}

	public void ledsOff() {
		Main.io.writeLCD(0,1,0,10100100);
	}

	public void reset(){
		Main.io.writeLCD(0,1,0,11100010);
	}
	
	public void dataWrite(byte _data){
		Main.io.writeLCD(1,1,0,_data);
	}
//	public byte dataRead(){
//		Main.io.writeLCD(1,0,1,0);
//		return 0;
//	}
	public void flash(int _n, int _t) {
		for (int i = 0; i < _n; i++) {
			ledsOn();
			Main.io.sleep(_t);
			ledsOff();
			Main.io.sleep(_t);
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
}
