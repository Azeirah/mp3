import java.io.*;

public class Sine_test {
    public static void main(String[] args) throws IOException {
        byte[] sineSequence = {0x53, (byte) 0xEF, 0x6E, 0x30, 0, 0, 0, 0};
        byte[] stopSequence = {0x45, 0x78, 0x69, 0x74};
        writeSequence(sineSequence);
        sleep(500, 0);
        writeSequence(stopSequence);
    }

    public static void writeSequence(byte[] sequence) throws IOException {
        RandomAccessFile raf = new RandomAccessFile("/dev/spidev1.0", "rw");
        for (byte instruction : sequence) {
            raf.writeBytes(instruction + "");
        }
        raf.close();
    }

    public static void sleep(int a, int b) {
        try {
            Thread.sleep(a, b);
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
    }
}
