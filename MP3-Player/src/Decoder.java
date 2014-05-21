public class Decoder {
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
    // channel volume, least significant byte =
    // right channel volume

    // 0x0000 = LOUD
    // 0xFEFE = silent
    public Decoder(Main parent) {
        // sendInstruction();
        this.parent = parent;
    }

    public void play() {
        while (DREQ == 0) {
            // wait 10 ns to give other threads the opportunity to do stuff
            Util.sleep(0, 10);
        }
    }

    private Main parent;

    public boolean isReadyForReadWrite() {
        // The DREQ pin/signal is used to signal if VS1033’s 2048-byte FIFO is
        // capable of receiving data. If
        // DREQ is high, VS1033 can take at least 32 bytes of SDI data or one
        // SCI command. DREQ is turned low
        // when the stream buffer is too full and for the duration of a SCI
        // command.
        return parent.io.readPin(DREQ);
    }
}
