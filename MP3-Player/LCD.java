import java.io.IOException;

public class LCD {

    private Main parent;
    public char[] firstLine;

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
    //Draws the static chars which won't be changed
    public void staticChars(){
    	//Left arrow
    	parent.io.newWriteLCD(0, "11000000");
    	Util.sleep(5);
    	parent.io.newWriteLCD(1, "01111111");
    	Util.sleep(5);
    	
    	//Colon for time
    	parent.io.newWriteLCD(0, "11000100");
    	Util.sleep(5);
    	parent.io.newWriteLCD(1, "001101010");
    	Util.sleep(5);
    	
    	//Percentage
    	parent.io.newWriteLCD(0, "11001101");
    	Util.sleep(5);
    	parent.io.newWriteLCD(1, "00100101");
    	Util.sleep(5);
    	
    	//Right arrow
    	parent.io.newWriteLCD(0, "11001111");
    	Util.sleep(5);
    	parent.io.newWriteLCD(1, "01111110");
    	Util.sleep(5);
    	
    	goHome();
    	Util.sleep(5);
    	
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
    	
    	initCChars();
    	goHome();
    	
    	System.out.println("LCD display init done");
    	//READY TO GO
    }
    
    public void initCChars(){
    	//Set CGRAM to 0
    	parent.io.newWriteLCD(0, "01000000");
    	//PLAY CHARACTER
    	Util.sleep(5);
       	parent.io.newWriteLCD(1, "00010000");
    	Util.sleep(5);
       	parent.io.newWriteLCD(1, "00011000");
    	Util.sleep(5);
       	parent.io.newWriteLCD(1, "00011100");
    	Util.sleep(5);
       	parent.io.newWriteLCD(1, "00011110");
    	Util.sleep(5);
       	parent.io.newWriteLCD(1, "00011110");
    	Util.sleep(5);
       	parent.io.newWriteLCD(1, "00011100");
    	Util.sleep(5);
       	parent.io.newWriteLCD(1, "00011000");
    	Util.sleep(5);
       	parent.io.newWriteLCD(1, "00010000");
    	Util.sleep(5);
    	//Pause char
    	Util.sleep(5);
       	parent.io.newWriteLCD(1, "00000000");
    	Util.sleep(5);
       	parent.io.newWriteLCD(1, "00011011");
    	Util.sleep(5);
       	parent.io.newWriteLCD(1, "00011011");
    	Util.sleep(5);
       	parent.io.newWriteLCD(1, "00011011");
    	Util.sleep(5);
       	parent.io.newWriteLCD(1, "00011011");
    	Util.sleep(5);
       	parent.io.newWriteLCD(1, "00011011");
    	Util.sleep(5);
       	parent.io.newWriteLCD(1, "00011011");
    	Util.sleep(5);
       	parent.io.newWriteLCD(1, "00000000");
    	Util.sleep(5);
    }
    
    public void goHome(){
    	parent.io.newWriteLCD(0, "00000010");
    	Util.sleep(5);
    }
    
    public void writePlay(){
       	parent.io.newWriteLCD(0, "11001000");  
    	Util.sleep(5);
    	parent.io.newWriteLCD(1, "00000000");
    	Util.sleep(5);
    	goHome();
    	Util.sleep(5);
    }
    
    public void writePause(){
       	parent.io.newWriteLCD(0, "11001000");  
    	Util.sleep(5);  
    	parent.io.newWriteLCD(1, "00000001");
    	Util.sleep(5);
      	goHome();
    	Util.sleep(5);
    }
    
    public void clear(){
    	parent.io.newWriteLCD(0, "00000001");
    	Util.sleep(5);
    }
    
    public void writeVolume(){
    	//Getting the values to write (seperating them from 1 number)
    	int volumePercentage = ((parent.player.volume - 90) / 140) * 100;
    	int firstNum = volumePercentage % 10;
    	int middleNum = volumePercentage / 10;
    	if(middleNum  > 9){
    		middleNum = 0;
    	}
    	int lastNum = volumePercentage / 100;
    	
    	String writeSecondNum = "";
    	String writeLastNum = "";
    	
    	String writeFirstNum = Util.byteToString((byte) ((byte) firstNum & 30));
    	if(middleNum == 0 && lastNum < 1){
    		//Empty char
    		writeSecondNum = "00100000";
    	} else {
    		writeSecondNum = Util.byteToString((byte) ((byte) middleNum & 30));
    	}
    	
    	if(lastNum == 0){
    		writeLastNum = "00100000";
    	} else {
    		writeLastNum = Util.byteToString((byte) ((byte) lastNum & 30));
    	}
    	
    	//First num
    	parent.io.newWriteLCD(0, "11001100");
    	parent.io.newWriteLCD(1, writeFirstNum);
    	
    	//Second num
    	parent.io.newWriteLCD(0, "11001011");
		parent.io.newWriteLCD(1, writeSecondNum);
		
    	//Last num
    	parent.io.newWriteLCD(0, "11001010");
		parent.io.newWriteLCD(1, writeLastNum);
    }
    
    public void dataWrite(String data) {
        parent.io.newWriteLCD(1, data);
    	Util.sleep(5);
    }
    //This will update the screen, to be placed in the main loop
    public void update(){
    	//Draw title and artist
    	
    	//Draw play/pause thing
    	if(parent.decoder.isPlaying()){
    		writePlay();
    	} else {
    		writePause();
    	}
    	//Draw remainder of song
    	
    	//Draw volume percentage
    	writeVolume();
    	
    	
    	
    }
}
