import java.io.IOException;

public class LCD extends ScreenElement {

    private Main parent;
    
    //Keeps track of the screen's state and gets updated by setPixel()
    protected static boolean[][] screenState;
    
    
    public LCD(Main parent) throws IOException {
        super(0, 0);
        width = 240;
        height = 64;
        this.parent = parent;
    }
    
    public void LCInit(){
    	System.out.println("Initialising LCD");
    	parent.io.setPin(57, 0);
    	parent.io.setPin(58, 0);
    	//Dummy
    	resOff();
    	Util.sleep(250, 0);
    	resOn();
    	Util.sleep(250, 0);
    	//For real this time
    	resOff();
    	Util.sleep(250, 0);
    	resOn();
    	bias();
    	ADC();
    	SHL();
    	line0();
    	powerControl();
    	resistorRatio();
    	referenceVoltage();
    	displayOn();
    	//Page adress 0
    	parent.io.writeBufferedLCD(0, Util.stringToByte("10110000"));
    	//Column adress 0
    	parent.io.writeBufferedLCD(0, Util.stringToByte("00010000"));
    	parent.io.writeBufferedLCD(0, Util.stringToByte("00000000"));	
    	System.out.println("LCD initialised");
    }
    
    //Turns !RES on and off
    public void resOn(){
    	parent.io.setPin(59, 1);
    }
    
    public void resOff(){
    	parent.io.setPin(59,  0);
    }
    
    //Sets the bias(?)
    public void bias(){
    	parent.io.writeBufferedLCD(0, Util.stringToByte("10100010"));
    }
    
    //Makes sure there is no flip on X-Axis
    public void ADC(){
    	parent.io.writeBufferedLCD(0, Util.stringToByte("10100000"));
    }
    
    //Makes sure there is no flip on Y-Axis
    public void SHL(){
    	parent.io.writeBufferedLCD(0, Util.stringToByte("11000000"));
    }
    
    //Makes sure RAM address 0 = Page 0 D0
    public void line0(){
    	parent.io.writeBufferedLCD(0, Util.stringToByte("01000000"));
    }
    
    //Gradually turns on the voltage follower/regulator and convertor
    public void powerControl(){
    	parent.io.writeBufferedLCD(0, Util.stringToByte("00101100"));
    	Util.sleep(50, 0);
    	parent.io.writeBufferedLCD(0, Util.stringToByte("00101110"));
    	Util.sleep(50, 0);
    	parent.io.writeBufferedLCD(0, Util.stringToByte("00101111"));
    	Util.sleep(50, 0);
    }
    
    //Sets resistor ratio(?)
    public void resistorRatio(){
    	parent.io.writeBufferedLCD(0, Util.stringToByte("00100110"));
    }
    
    public void referenceVoltage(){
    	parent.io.writeBufferedLCD(0, Util.stringToByte("10000001"));
    	parent.io.writeBufferedLCD(0, Util.stringToByte("00011010"));
    }
    
    public void displayOn() {
        parent.io.writeBufferedLCD(0, Util.stringToByte("10101111"));
    }

    public void displayOff() {
        parent.io.writeBufferedLCD(0, Util.stringToByte("10101110"));
    }

    public void ledsOn() {
        parent.io.writeBufferedLCD(0, Util.stringToByte("10100101"));
    }

    public void ledsOff() {
        parent.io.writeBufferedLCD(0, Util.stringToByte("10100100"));
    }

    public void reset() {
        parent.io.writeBufferedLCD(0, Util.stringToByte("11100010"));
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
