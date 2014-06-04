/**
 * Created by student on 26-5-14.
 */
public class Interface /*extends Thread*/ {
    private General_IO io;
    private boolean leftButtonSignal = false,
    				rightButtonSignal = false,
    				middleButtonSignal = false;
    private int rotaryDialSignal;
    private boolean initial = false;
    private boolean leftSignalReady = false,
    				rightSignalReady = false,
    				middleSignalReady = false,
    				rotarySignalReady = false;
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
    
    public boolean isLeftButtonSignal() {
    	if (leftSignalReady) {
    		leftSignalReady = false;
    		return leftButtonSignal;
    	}
		return false;
	}

	public boolean isRightButtonSignal() {
		if (rightSignalReady) {
			rightSignalReady = false;
			return rightButtonSignal;
		}
		return false;
	}

	public boolean isMiddleButtonSignal() {
		if (middleSignalReady) {
			middleSignalReady = false;
			return middleButtonSignal;
		}
		return false;
	}

	public int getRotaryDialSignal() {
		if (rotarySignalReady) {
			rotarySignalReady = false;
			return rotaryDialSignal;
		}
		return 1;
	}

	public Interface(General_IO io) {
        this.io = io;
    }

    public boolean debounce(int pin) {
        boolean result[] = new boolean[3];
        for (int i = 0; i < 3; i++) {
            result[i] = Gpio.ioread(pin) == 1;
            
            Util.sleep(0, 12);
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
    
    public void read() {
    	// read from all inputs
    	// read a button, set the signal to not previous signal && readButton()
    	// This means that you will get one true when readButton becomes true
    	leftButtonSignal = readButtonLeft();
    	rightButtonSignal = readButtonRight();
    	middleButtonSignal = readButtonMiddle();
    	// example:
    	// signal:     ____|-|___________________|-|___readButtonMiddle
    	// readButton: ____|---------------|_____|--|
    	// rotary dial already returns the correct value when changes occur, so we can just
    	// read it and copy it to signal.
    	rotaryDialSignal = readRotaryDial();
    	leftSignalReady = true;
    	rightSignalReady = true;
    	middleSignalReady = true;
    	rotarySignalReady = true;
    }

    public boolean readButtonLeft() {
//        System.out.println("button left is: " + (Gpio.ioread(Pins.DrukknopL.getNumber()) == 1));
    	return (!debounce(Pins.DrukknopR.getNumber())) && debounce(Pins.DrukknopL.getNumber());
    }

    public boolean readButtonRight() {
        return (!debounce(Pins.DrukknopL.getNumber())) && debounce(Pins.DrukknopR.getNumber());
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
    	// ____|-----|__________|--------------------|__
    	//     ->   ->         ->                    ->
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
        boolean A, B;
        A = io.ioread(Pins.RDA.getNumber()) == 1;
    	B = io.ioread(Pins.RDB.getNumber()) == 1;
    	    	
    	if (A && B) {
    		initial = true;
    	} else if (!A && !B) {
    		initial = false;
    	}
        
        A = io.ioread(Pins.RDA.getNumber()) == 1;
        B = io.ioread(Pins.RDB.getNumber()) == 1;
//        Util.sleep(8, 0);
        if (A != B) {
        	if (A != initial) {
                return 0;
            } else if (B != initial) {
                return 2;
            }
        }
        
        return 1;
    }

    public boolean readButtonMiddle() {
        return debounce(Pins.DrukknopL.getNumber()) && debounce(Pins.DrukknopR.getNumber());
    }
}
