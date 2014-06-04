import java.io.IOException;
//Whole lot of this needs to be rewritten
public class LCD extends ScreenElement {

    //Keeps track of the screen's state and gets updated by setPixel()
    protected static boolean[][] screenState;
    private Main parent;


    public LCD(Main parent) throws IOException {
        super(0, 0);
        width = 240;
        height = 64;
        this.parent = parent;
    }

    public void displayOn() {
        parent.io.newWriteLCD(0, "00111111");
    }

    public void displayOff() {
    	parent.io.newWriteLCD(0, "00111110");
    }
    
    public void reset() {
        parent.io.setPin(80, 0);
        Util.sleep(0, 1500);
        parent.io.setPin(80, 1);
        displayOn();
    }

    public void dataWrite(String _data) {
        parent.io.newWriteLCD(1, _data);
    }

//    public void flash(int _n, int _t) {
//        for (int i = 0; i < _n; i++) {
//            ledsOn();
//            Util.sleep(_t, 0);
//            ledsOff();
//            Util.sleep(_t, 0);
//        }
//    }

    public void drawSinus() {
        for (int x = 0; x < width; x++) {
            float y = (float) (Math.sin(x / 8) * 8 + 16);
            setPixel(x, (int) y);
        }
    }

    //Rewrite(?)
    public void draw(ScreenElement _element) {
        //ledsOff();
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
