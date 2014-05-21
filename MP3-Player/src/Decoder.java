import java.io.IOException;

public class Decoder {
    // channel volume, least significant byte =
    private Main parent;
    // Declare pins
    private int RST = 82;
    private int DREQ = 83;
    private int SCLK = 66;
    private int CS = 67;
    private int SO = 68;
    private int SI = 69;

    private int BSYNC = 101;
    // Declare instructions
    private byte WRITE = 0x02;

    private byte READ = 0x03;
    // Declare addresses
    private byte MODE = 0x00;
    private byte STATUS = 0x01;
    private byte BASSTREBLE = 0x02;
    private byte CLOCKFREQ = 0x03;
    private byte DECODE_TIME = 0x04;
    private byte AUDATA = 0x05;
    private byte WRAM = 0x06;
    private byte WRAMADRESS = 0x07;
    private byte VOLUME = 0x0B; // When using this: Most significant byte = left

    // right channel volume

    // 0x0000 = LOUD
    // 0xFEFE = silent
    public Decoder(Main parent) {
        // sendInstruction();
        this.parent = parent;
    }

    public void play() throws IOException {
        while (DREQ == 0) {
            // wait 10 ns to give other threads the opportunity to do stuff
            Util.sleep(0, 10);
        }
        parent.io.setSCI_MODE((byte) 0x0C, (byte) 0x00);

    }
}
