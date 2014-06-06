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
    private Main parent;
    

    protected ScreenElement(Main parent, int locationX, int locationY) {
        this.locationX = locationX;
        this.locationY = locationY;
        this.parent = parent;
    }

    protected ScreenElement(Main parent, int locationX, int locationY, String _path)
            throws IOException {
        this.locationX = locationX;
        this.locationY = locationY;
        this.parent = parent;
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
//        for (int y = 0; y < element.getHeight(); y++) {
//            for (int x = 0; x < element.getWidth(); x++) {
//                if (dotArray[y][x]) {
//                    setPixel(x + element.getLocationX(),
//                            y + element.getLocationY());
//                    Main.console.printDebug("X");
//                } else {
//                    Main.console.printDebug(".");
//                }
//            }
//            System.out.println("");
//        }
      // draw(dotArray);
    }

//    protected void draw(boolean[][] _screen){
//    	String[][] _return;
//    	for(int i = 0; i < width;i++){
//    		for(int j = 0;j < 8;j++){
//    			for(int y= 0; y <8; y++){
//    				if(_screen[i][(8*j+y)]){
//    					
//    				}
//    			}
//    		}
//    	}
//    }
    
    protected void setPixel(int x, int y){
    	
	      byte column;
	      byte columnWrite;
	      byte page;
	      byte pageWrite;
	  	  String toWrite = "";
	      
	      if( x < 128 && y < 64){
	    	  //Determine which page to use
	    	  page = (byte) (y / 8);
	    	  
	    	  //Determine which column to use (Y)
	    	  if(x < 64){
	    		  column = (byte) x;
	    	  } else {
	    		  column = (byte) (x - 64);
	    	  }
	    	  
	    	  //Prep the page and column for writing
	    	  pageWrite = (byte) (page | 184);
	    	  columnWrite = (byte) (column | 64);
	    	  
	      } else {
	    		System.out.println("Something's wrong with your input for setPixel()");
	    		return;
	      }
	      
	      //Select page
	      System.out.print("Writing page: ");
	      parent.io.newWriteLCD(0, Util.byteToString(pageWrite));
	      
	      //Select column
	      System.out.print("Writing column: ");
	      parent.io.newWriteLCD(0, Util.byteToString(columnWrite));

	      //Update screen state
	      parent.lcd.screenState[x][y] = true;
	      
	      //Fetch screen-state which now has the added pixel to be set
	      for(int i = page * 8 + 7; i >= page * 8; i--){
	    	  if(parent.lcd.screenState[x][i]){
	    		  toWrite = toWrite.concat("1");
	    	  } else {
	    		  toWrite = toWrite.concat("0");
	    	  }
	      }
	      //Alter CS to write on the right display
	      if(x < 64){
	    	  parent.io.setPin(81,  1);
	      } else {
	    	  parent.io.setPin(81,  0);
	      }
	      //Actually write it to the LCD
	      System.out.print("Writing data: ");
	      parent.io.newWriteLCD(1, toWrite);
	      
	      //Put CS back on it's default (1)
	      parent.io.setPin(81, 1);
    }
    
    
    
    // Old stuff
//    protected void setPixel(int x, int y) {
//        byte column;
//        byte page;
//        byte columnMSN;
//        byte columnLSN;
//        byte pageWrite;
//    	String toWrite = "";
//        byte dataWrite;
//        
//    	//Check input
//    	if(x <= 240 && y <= 64){
//	    	//Figure out the column's Most significant and least significant nibble + the page to edit.
//	        column = (byte) x;
//	        
//	        columnMSN = (byte) (column >> 4);
//	        //Prep the nibbles for writing
//	        columnMSN = (byte) (columnMSN | 16);
//	        
//	        columnLSN = (byte) (column & 15);
//	        
//	    	page = (byte) (y / 8);
//	    	//Prep the page for writing
//	    	pageWrite = (byte) (page | 176);
//    	} else {
//    		System.out.println("Something's wrong with your input for setPixel()");
//    		return;
//    	}
//    	
//    	//Select the column
//		parent.io.writeBufferedLCD(0, columnMSN);
//		parent.io.writeBufferedLCD(0, columnLSN);
//		
//		//Select the page
//		parent.io.writeBufferedLCD(0, pageWrite);
//		
//    	//Update the screen state
//		LCD.screenState[x][y] = true;
//		
//		//Fetch screen-state which now has the added pixel to be set
//		for(int i = page * 8 + 7; i >= page * 8; i--){
//			if(LCD.screenState[x][i]){
//				toWrite = toWrite.concat("1");
//			} else {
//				toWrite = toWrite.concat("0");
//			}
//		}
//		dataWrite = (byte) Integer.parseInt(toWrite, 2);
//		
//		//Actually write it to the LCD
//		parent.io.writeBufferedLCD(1, dataWrite);
//    }

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
