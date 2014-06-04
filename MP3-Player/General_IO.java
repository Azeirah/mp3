import java.io.IOException;
import java.io.RandomAccessFile;

public class General_IO extends Gpio {
	RandomAccessFile SCI;
	RandomAccessFile SDI;
	int[] LCDPins = { 38, 39, 41, 42, 43, 54, 57, 58 };

	public General_IO() throws IOException {
		this.SCI = new RandomAccessFile("/dev/spidev1.0", "rw");
		this.SDI = new RandomAccessFile("/dev/spidev1.1", "rw");
		for (Pins pin : Pins.values()) {
			setPin(pin.getNumber(), 0);
		}
	}
    
    //For old display, outdated
    public void writeLCD(int _A0, boolean _l) {
        Util.sleep(0, 50);
        setPin(Pins.LCDA0.getNumber(), _A0);
        setPin(Pins.LCDSI.getNumber(), _l);
        setPin(Pins.LCDSCL.getNumber(), true);
        Util.sleep(0, 50);
        setPin(Pins.LCDSCL.getNumber(), false);
    }


	public void writeBufferedLCD(int _A0, byte _l) {
		for (int i = 8; i > 0; i--) {
			writeLCD(_A0, (_l >> i & 1) == 1);
			System.out.print((_l >> i & 1) == 1);
		}
		System.out.println("");
	}

	// Writes to the LCD, instruction is in the format of "10101010", "1111000"
	// etc.
	public void newWriteLCD(int DI, String instruction) {
		// Validate format
		if (instruction.length() != 8) {
			System.out.println("Incorrect input format for writing to LCD");
			return;
		}

		for (int i = 0; i < 7; i++) {
			if (instruction.charAt(i) != '0' && instruction.charAt(i) != '1') {
				System.out.println("Incorrect input format for writing to LCD");
			}
		}

		// Prepare pins
		setPin(59, DI);
		setPin(81, 1);
		Util.sleep(0, 140);

		for (int i = 0; i < 8; i++) {
			if (instruction.charAt(7 - i) == '1') {
				setPin(LCDPins[i], 1);
			} else {
				setPin(LCDPins[i], 0);
			}
		}

		// Stuff it into the LCD
		setPin(60, 1);
		Util.sleep(0, 500);
		setPin(60, 0);
		Util.sleep(0, 30);

		// Put pins on low again
		for (int i : LCDPins) {
			setPin(i, 0);
		}
		setPin(81, 0);
		setPin(59, 0);
	}

	public void setPin(int pin, int pinState) {
		iowrite(pin, pinState);
	}

	public void setPin(int pin, boolean pinState) {
		iowrite(pin, (pinState) ? 1 : 0);
	}

	public void writeSCI(byte[] sequence) throws IOException {
		// iowrite(67, 1);
		SCI.write(sequence);
		// iowrite(67, 0);
	}

	public void writeSDI(byte[] sequence) throws IOException {
		// iowrite(67, 0);
		SDI.write(sequence);
		// iowrite(67, 1);
	}

	public void setSCI_MODE(byte b1, byte b2) throws IOException {
		byte[] sequence = { 0x02, 0x0, b1, b2 };
		writeSCI(sequence);
	}

	public boolean readPin(int _N) {
		boolean _value = false;
		if (ioread(_N) > 0) {
			_value = true;
		}
		return _value;
	}

	public void setVolume(int volume) throws IOException {
		// TODO: FIX DIS SHIT, IT'S VERY BROKEN.
		byte vol = (byte) (255 - volume);
		// byte vol = (byte) 40;
		byte[] sequence = { (byte) 0x2, (byte) 0xB, vol, vol };
		writeSCI(sequence);
	}
}
