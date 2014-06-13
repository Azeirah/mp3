import java.io.IOException;
import java.util.ArrayList;

public class LCD {

    private Main parent;
    public char[] firstLine = new char[100];
    private long lastShift;
    private long lastRemainderDraw;
    public int lastVolumeWritten;
    private int offset = 0;
    public LCD(Main parent) throws IOException {
        this.parent = parent;
        lastShift = System.currentTimeMillis();
        lastRemainderDraw = System.currentTimeMillis();
        for(int i = 0; i < 100; i++){
        	firstLine[i] = '\n';
        }
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
    
 
    //Draws the static chars which won't be changed
    public void staticChars(){
    	//Left arrow
    	parent.io.newWriteLCD(0, "11000000");
    	parent.io.newWriteLCD(1, "01111111");
    	
    	//Percentage
    	parent.io.newWriteLCD(0, "11001101");
    	parent.io.newWriteLCD(1, "00100101");
    	
    	//Right arrow
    	parent.io.newWriteLCD(0, "11001111");
    	parent.io.newWriteLCD(1, "01111110");
    	
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
    	//Display off
    	parent.io.newWriteLCD(0, "00001000");
    	//Clear
    	parent.io.newWriteLCD(0, "00000001");
    	//Entry mode set
    	parent.io.newWriteLCD(0, "00000110");
    	
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
    	
    	staticChars();
    	
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
    	Util.sleep(1);
    	parent.io.newWriteLCD(1, "00000000");
    	Util.sleep(1);
    	goHome();
    	Util.sleep(1);
    }
    
    public void writePause(){
       	parent.io.newWriteLCD(0, "11001000");  
    	Util.sleep(1);  
    	parent.io.newWriteLCD(1, "00000001");
    	Util.sleep(1);
      	goHome();
    	Util.sleep(1);
    }
    
    public void clear(){
    	parent.io.newWriteLCD(0, "00000001");
    	Util.sleep(5);
    }
    
    public void writeRemainder(){
    	
    	if(System.currentTimeMillis() > lastRemainderDraw + 1000){     	
        	long progress = (System.currentTimeMillis() - parent.decoder.startTime) / 1000;
        	//Remainder in seconds(!!!)
        	long remainder = (parent.decoder.duration - progress);
        	String writeRemains = Meta.formatTime(remainder);
        	//Go to the correct spot
        	parent.io.newWriteLCD(0, "11000010");
        	for(int i = 0; i < 5; i++){
        		writeChar(writeRemains.charAt(i));
        	}
        	goHome();
        	
        	lastRemainderDraw = System.currentTimeMillis();
    	}

    }
    
    

    
    public void dataWrite(String data) {
        parent.io.newWriteLCD(1, data);
    	Util.sleep(5);
    }
    //This will update the screen, to be placed in the main loop
    public void update(){

    	
    	//Draw title and artist, will only draw a second after it last drew itself
    	writeFirstLine();
    	//Draw play/pause thing
    	if(parent.decoder.isPlaying()){
    		writePlay();
    	} else {
    		writePause();
    	}
    	//Draw remainder of song
    	if(parent.decoder.counting){
        	writeRemainder();
    	}

    	
    	//Draw volume percentage
    	writeVolume();  	
    	
    }
    
    //Writes the volume on the 2nd line of the screen
    public void writeVolume(){
    	if(lastVolumeWritten != (int) (((parent.player.volume - 90) / 1.4))){
	    	//Getting the values to write (seperating them from 1 number)
	    	int volumePercentage = (int) (((parent.player.volume - 90) / 1.4));
	    	int firstNum = volumePercentage % 10;
	    	int middleNum = volumePercentage / 10;
	    	if(middleNum  > 9){
	    		middleNum = 0;
	    	}
	    	int lastNum = volumePercentage / 100;
	    	
	    	String writeSecondNum = "";
	    	String writeLastNum = "";
	    	String writeFirstNum = Util.byteToString((byte) ((byte) firstNum | 48));
	    	if(middleNum == 0 && lastNum < 1){
	    		//Empty char
	    		writeSecondNum = "00100000";
	    	} else {
	    		writeSecondNum = Util.byteToString((byte) ((byte) middleNum | 48));
	    	}
	    	
	    	if(lastNum == 0){
	    		writeLastNum = "00100000";
	    	} else {
	    		writeLastNum = Util.byteToString((byte) ((byte) lastNum | 48));
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
			
			lastVolumeWritten = (int) (((parent.player.volume - 90) / 1.4));
    	}
    	
    }

    //Writes something on the first line of the display, used to display the title
    public void writeFirstLine(){
    	String firstLineStr = parent.decoder.title + "-" + parent.decoder.artist;
    	//Returns the cursor home
    	parent.io.newWriteLCD(0, "00000010");
    	
    	//Checks if a second has passed from the last shift
    	if(System.currentTimeMillis() > lastShift + 750){
    		//OMG, DRAW THE DAMN STUFF
    		
    		offset++;
    		//Will reset the offset if need be
    		if(firstLineStr.length() - offset < 16){
    			offset = 0;
    		}
    		
    		//Draws on the first line
    		for(int i = offset; i < offset + 16; i++){
    			//Little note, uninitialised spots in a char array are a blank space instead of null
    			if(firstLine[i] == '\n'){
    				break;
    			}
    			writeChar(firstLine[i]);		
    		}
    		
    		//Set the new lastShift
    		lastShift = System.currentTimeMillis();
    		
    	}
    }
}
