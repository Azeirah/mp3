import java.io.IOException;
import java.io.RandomAccessFile;

public class General_IO extends Gpio {
    RandomAccessFile SCI;
    RandomAccessFile SDI;

    public General_IO() throws IOException {
        this.SCI = new RandomAccessFile("/dev/spidev1.0", "rw");
        this.SDI = new RandomAccessFile("/dev/spidev1.1", "rw");
    }

    public void writeBufferedLCD(int _A0, byte _l) {
        for (int i = 8; i > 0; i--) {
            writeLCD(_A0, (_l >> i & 1) == 1);
            System.out.print((_l >> i & 1) == 1);
        }
        System.out.println("");
    }

    public void writeLCD(int _A0, boolean _l) {
    	Util.sleep(0,50);
        setPin(Pins.LCDA0.getNumber(), _A0);
        setPin(Pins.LCDSI.getNumber(), _l);
        setPin(Pins.LCDSCL.getNumber(), true);
        Util.sleep(0,50);
        setPin(Pins.LCDSCL.getNumber(), false);
    }

    public void setPin(int pin, int pinState) {
        iowrite(pin, pinState);
    }

    public void setPin(int pin, boolean pinState) {
        iowrite(pin, (pinState) ? 1 : 0);
    }

    public void writeSCI(byte[] sequence) throws IOException {
//        iowrite(67, 1);
        SCI.write(sequence);
//        iowrite(67, 0);
    }

    public void writeSDI(byte[] sequence) throws IOException {
//        iowrite(67, 0);
        SDI.write(sequence);
//        iowrite(67, 1);
    }

    public void setSCI_MODE(byte b1, byte b2) throws IOException {
        byte[] sequence = {0x02, 0x0, b1, b2};
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
        //TODO: FIX DIS SHIT, IT'S VERY BROKEN.
        //byte vol = (byte) ((volume / 100) * 256);
    	byte vol = (byte)40;
        byte[] sequence = {(byte) 0x2, (byte) 0xB, vol, vol};
        writeSCI(sequence);
    }
}