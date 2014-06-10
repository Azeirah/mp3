import java.io.IOException;

public class LCD {

    private Main parent;


    public LCD(Main parent) throws IOException {
        this.parent = parent;
    }
    
    public void test(){
    	//Writes the a character
    	parent.io.newWriteLCD(1, "01100001");
    }
    
    //Writes the character at the current position of the cursor
    public void writeChar(char character){
    	byte toWrite = (byte) character;
    	parent.io.newWriteLCD(1, Util.byteToString(toWrite));
    	Util.sleep(5);
    }
    
    //Writes something on the first line of the display, used to display the title
    public void writeFirstLine(String str){
    	//Returns the cursor home
    	parent.io.newWriteLCD(0, "00000010");
    	
    	//Write it all (everything after the 16th character gets cut off due to limitations of the screen. Maybe make some scrolling stuff later?)
    	if(str.length() <= 64){
	    	for(int i = 0; i <= str.length() - 1; i++){
	    		writeChar(str.charAt(i));
	    	}
    	}
    }
    
    public void init4Bit(boolean cursor){
    	Util.sleep(110);
		// Function set
		parent.io.writeLCD4Bits(0, "0011");
    	Util.sleep(5);
		parent.io.writeLCD4Bits(0, "0011");
    	Util.sleep(5);
		parent.io.writeLCD4Bits(0, "0011");
    	Util.sleep(5);
		// set 4 bit
		parent.io.writeLCD4Bits(0, "0010");
    	Util.sleep(5);
		// set 4 bit
		parent.io.writeLCD4Bits(0, "0010");
		parent.io.writeLCD4Bits(0, "1000");
    	Util.sleep(5);
		// Display off
		parent.io.writeLCD4Bits(0, "0000");
		parent.io.writeLCD4Bits(0, "1000");
    	Util.sleep(5);
		// Display Clear
		parent.io.writeLCD4Bits(0, "0000");
		parent.io.writeLCD4Bits(0, "0001");
    	Util.sleep(5);
		// Entry mode set
		parent.io.writeLCD4Bits(0, "0000");
		parent.io.writeLCD4Bits(0, "1000");
    	Util.sleep(5);
		// Display on
		parent.io.writeLCD4Bits(0, "0000");
		parent.io.writeLCD4Bits(0, "1111");
    	Util.sleep(5);
    }
    
    public void init(boolean cursor){
    	Util.sleep(110);
    	//3 x function set
    	parent.io.newWriteLCD(0, "00110000");
    	Util.sleep(5);
    	parent.io.newWriteLCD(0, "00110000");
    	Util.sleep(5);
    	parent.io.newWriteLCD(0, "00110000");
    	Util.sleep(5);
    	//Actual function set
    	parent.io.newWriteLCD(0, "00111000");
    	Util.sleep(5);
    	//Display off
    	parent.io.newWriteLCD(0, "00001000");
    	Util.sleep(5);
    	//Clear
    	parent.io.newWriteLCD(0, "00000001");
    	Util.sleep(5);
    	//Entry mode set
    	parent.io.newWriteLCD(0, "00000110");
    	Util.sleep(5);
    	
    	//Turn on LCD
    	if(cursor){
    		//Cursor on with blink
    		parent.io.newWriteLCD(0, "00001111");
    	} else {
    		//No cursor
    		parent.io.newWriteLCD(0, "00001100");
    	}
    	
    	
    	System.out.println("LCD display init done");
    	//READY TO GO
    }
    
    public void clear(){
    	parent.io.newWriteLCD(0, "00000001");
    }
    
    public void dataWrite(String data) {
        parent.io.newWriteLCD(1, data);
    }
    
    
}
