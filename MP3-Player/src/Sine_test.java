import java.io.IOException;
import java.io.RandomAccessFile;

public class Sine_test {
    RandomAccessFile SCI;
    RandomAccessFile SDI;
    Gpio gpio = new Gpio();

    public static void main(String[] args) throws IOException {
        Sine_test test = new Sine_test();
        test.gpio.ioinit();
        test.setTestMode();
        byte[] sineSequence = {0x53, (byte) 0xEF, 0x6E, 0x30, 0, 0, 0, 0};
        byte[] stopSequence = {0x45, 0x78, 0x69, 0x74, 0, 0, 0, 0};
        for (int i = 0; i < 10; i++) {
            System.out.println("beep");
            test.writeSDI(sineSequence);
            test.sleep(500, 0);
            test.writeSDI(stopSequence);
            test.sleep(500, 0);
        }
    }

    public Sine_test() throws IOException {
        this.SCI = new RandomAccessFile("/dev/spidev1.0", "rw");
        this.SDI = new RandomAccessFile("/dev/spidev1.1", "rw");
    }

    public void setTestMode() throws IOException {
        // mode1 and mode2 are two separate bytes that form the SCI_MODE short
        byte[] sequence = {0x02, 0x0, 0x08, 0x20};
        // 0x02 = write
        // 0x0 = SCI_MODE register
        // 0x08 en 0x20 is de SCI_MODE, in dit geval is 0x20 = 5e bit = SM_TESTS
        writeSCI(sequence);
    }

    public void writeSCI(byte[] sequence) throws IOException {
        gpio.iowrite(67, 1);
        SCI.write(sequence);
        gpio.iowrite(67, 0);
    }

    public void writeSDI(byte[] sequence) throws IOException {
        gpio.iowrite(67, 0);
        SDI.write(sequence);
        gpio.iowrite(67, 1);
    }

    public void sleep(int a, int b) {
        try {
            Thread.sleep(a, b);
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
    }
}
