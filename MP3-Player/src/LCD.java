import java.io.IOException;

public class LCD extends ScreenElement {
    static int MOSI = 57;
    static int SCL = 58;
    static int A0 = 59;
    private Main parent;
    
    //Keeps track of the screen's state and gets updated by setPixel()
    protected static boolean[][] screenState;
    
    
    public LCD(Main parent) throws IOException {
        super(0, 0);
        width = 240;
        height = 64;
        this.parent = parent;
    }

    public void displayOn() {
        parent.io.writeBufferedLCD(0, (byte) 10101111);
    }

    public void displayOff() {
        parent.io.writeBufferedLCD(0, (byte) 10101110);
    }

    public void ledsOn() {
        parent.io.writeBufferedLCD(0, (byte) 10100101);
    }

    public void ledsOff() {
        parent.io.writeBufferedLCD(0, (byte) 10100100);
    }

    public void reset() {
        parent.io.writeBufferedLCD(0, (byte) 11100010);
    }

    public void dataWrite(boolean _data) {
        parent.io.writeLCD(1, _data);
    }

    public void flash(int _n, int _t) {
        for (int i = 0; i < _n; i++) {
            ledsOn();
            Util.sleep(_t, 0);
            ledsOff();
            Util.sleep(_t, 0);
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
