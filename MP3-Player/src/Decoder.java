public class Decoder {
    private int RST = 82;
    private int DREQ = 83;
    private int SCLK = 66;
    private int CS = 67;
    private int SO = 68;
    private int SI = 69;
    private int BSYNC = 101;

    public Decoder() {
//        sendInstruction();
    }

    public boolean isReadyForReadWrite() {
        // The DREQ pin/signal is used to signal if VS1033’s 2048-byte FIFO is capable of receiving data. If
        // DREQ is high, VS1033 can take at least 32 bytes of SDI data or one SCI command. DREQ is turned low
        // when the stream buffer is too full and for the duration of a SCI command.
        return Main.io.readPin(DREQ);
    }

    public void sendBit(boolean bit) {
        Main.io.writePin(SI, bit);
        Main.io.writePin(SCLK, true);
        Main.io.writePin(SCLK, false);
    }

    public void sineTest(byte n) {
        byte[] sequence = {0x53, (byte) 0xEF, 0x6E, n, 0, 0, 0, 0};
        for (byte instr : sequence) {
            sendByte(instr);
        }
    }

    public void SCI_WRITE(byte address, short data) {
        sendInstruction((byte) 0x2, address, data);
    }

    public void sendByte(byte data) {
        Main.io.writePin(CS, false);
        while (!isReadyForReadWrite()) {
            Main.io.sleep(0, 25);
        }
        for (int i = 0; i < 8; i++) {
            sendBit((data >> i & 1) == 1);
        }
    }

    public void sendInstruction(byte instruction, byte address, short data) {
        Main.io.writePin(CS, false);
        while (!isReadyForReadWrite()) {
            Main.io.sleep(0, 25);
        }
        for (int i = 0; i < 8; i++) {
            sendBit((instruction >> i & 1) == 1);
        }
        for (int i = 0; i < 8; i++) {
            sendBit((address >> i & 1) == 1);
        }
        for (int i = 0; i < 16; i++) {
            sendBit((data >> i & 1) == 1);
        }
        Main.io.writePin(CS, true);
    }
}
