/**
 * Created by student on 26-5-14.
 */
public class Interface /*extends Thread*/ {
    private General_IO io;
    private boolean initial = false;
    private boolean lastLeftButton = false,
    				lastRightButton = false,
    				lastMiddleButton = false;
    private int lastRotarySignal = 0;
    private boolean lastA = false,
    				lastB = false;
    
//    
//    public void run() {
//    	while (true) {
//    		// read all inputs, other objects can access the signals with the getters.
//    		// ex: anInterface.getRotaryDialSignal() == 2; // if the rotary dial is rotating cw (right)
//    		read();
//    		Util.sleep(2);
//    	}
//    }
    
    public Interface () {
//    	super("hardware buttons interface thread");
//    	System.out.println("thread: " + this.getName() + ". has started");
    }

	public Interface(General_IO io) {
        this.io = io;
    }

    public boolean debounce(int pin) {
        boolean result[] = new boolean[3];
        for (int i = 0; i < 3; i++) {
            result[i] = Gpio.ioread(pin) == 1;
        }
        for (boolean res : result) {
            // als het resultaat false is, dan false returnen, anders doorchecken.
            // als ze alledrie niet false zijn dan returnt deze functie true
        	if (!res) {
                return res;
            }
        }
        return true;
    }

    public boolean readButtonLeft() {
    	boolean state = (!debounce(Pins.DrukknopR.getNumber())) && debounce(Pins.DrukknopL.getNumber()); 
    	if (state != lastLeftButton) {
    		lastLeftButton = state;
    		if (state) return true;
    	}
    	lastLeftButton = state;
    	return false;
    }

    public boolean readButtonRight() {
    	boolean state = (!debounce(Pins.DrukknopL.getNumber())) && debounce(Pins.DrukknopR.getNumber());
    	if (state != lastRightButton) {
    		lastRightButton = state;
    		if (state) return true;
    	}
    	lastRightButton = state;
    	return false;
    }

    /**
     * 0 = left
     * 1 = nothing
     * 2 = right
     * -1 = ERRORERINO
     *
     * @return int
     */
    public int readRotaryDial() {
        // 0 = left, 1 = nothing, 2 = right, -1 = error
        // ???
        // ___|-------|_____   :A
        // ______|-------|____ :B
        // Linksom A eerst, B tweede
        //
        // ______|-------|____ :A
        // ___|-------|_____   :B
        // Rechtsom B eerst, A tweede
    	
    	// rotary dial werkt als volgt
    	// ____|-----|__________|--------------------|___ A
    	// _____|-----|__________|--------------------|__ B
    	//     ->   ->         ->                    ->    = switch
    	//
    	// ___|----|______|---------|___ A
    	// __|----|______|---------|____ B
    	//   <-  <-     <-        <-      = switch
    	// hij switcht wanneer er geklikt aan een van de twee kanten
    	// als je linksom draait dan zal A klikken voor B
    	// als je rechtsom draait dan zal B klikken voor A
    	
    	// dus:
    	// 1: read initial state (high of low)
    	// 2: loop 3x over pins heen
    	// 3: als een van de twee pins verandert van status
    	//   4: return 0 als A
    	//   4: return 2 als B
    	// 5: return 1
//        boolean A, B;        
//        
//        A = Gpio.ioread(Pins.RDA.getNumber()) == 1;
//    	B = Gpio.ioread(Pins.RDB.getNumber()) == 1;
//    	    	
//    	if (A && B) {
//    		initial = true;
//    	} else if (!A && !B) {
//    		initial = false;
//    	}
//        
//        A = Gpio.ioread(Pins.RDA.getNumber()) == 1;
//        B = Gpio.ioread(Pins.RDB.getNumber()) == 1;
////        Util.sleep(8, 0);
//        if (A != B) {
//        	if (A != initial) {
//                return 0;
//            } else if (B != initial) {
//                return 2;
//            }
//        }
//        
//        return 1;
    	int A, B;
    	
    	A = Gpio.ioread(Pins.RDA.getNumber());
    	B = Gpio.ioread(Pins.RDB.getNumber());
    	// if both are high
    	//   set signal high                1
    	// else if both are low
    	//   set signal low                 2
    	// else if they are unequal
    	//   while signal is equal to A     3
    	//     read A                       4
    	//     if A is not equal to signal  
    	//     return 2                     5
    	//   while signal is equal to B     6
    	//     read B                       7
    	//     if B is not equal to signal  
    	//     return 0                     8
    	// return 1

    	// rotary dial werkt als volgt
    	// ____|-----|__________|--------------------|___ A
    	// _____|-----|__________|--------------------|__ B
    	//     ->   ->         ->                    ->    = switch
    	// 2   3
    	//  
    	// ___|----|______|---------|___ A
    	// __|----|______|---------|____ B
    	//   <-  <-     <-        <-      = switch
    	
    	// set signal high if both are high
    	int maxLoops = 500;
    	int loops = 0;
    	if (A == 1 && B == 1) {
    		lastRotarySignal = 1;
    	}
    	// set signal low if both are low
    	else if (A == 0 && B == 0) {
    		lastRotarySignal = 0;
    	}
    	// if they are unequal
    	if (A != B) {
    		// while the signal is equal to a
    		while (lastRotarySignal == A && loops < maxLoops) {
    			loops++;
    			// read pin
    			A = Gpio.ioread(Pins.RDA.getNumber());
//    	    	B = Gpio.ioread(Pins.RDB.getNumber());
    	    	// if the pin changed, return 2
    			if (A != lastRotarySignal)
    	    		return 2;
    		}
    		while (lastRotarySignal == B && loops < maxLoops) {
    			loops++;
    			// read pin
    	    	B = Gpio.ioread(Pins.RDB.getNumber());
    	    	// if the pin changed, return 0
    	    	if (B != lastRotarySignal)
    	    		return 0;
    		}
    	}
    	return 1;
    }

    public boolean readButtonMiddle() {
    	boolean state = debounce(Pins.DrukknopL.getNumber()) && debounce(Pins.DrukknopR.getNumber()); 
        if (state != lastMiddleButton) {
        	lastMiddleButton = state;
        	if (state) {
        		return state;
        	}
        }
        lastMiddleButton = state;
    	return false;
    }
}
