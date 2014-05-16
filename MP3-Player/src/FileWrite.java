import java.io.*;

public class FileWrite {
    public static void main(String[] args) {
        try {
            RandomAccessFile raf = new RandomAccessFile("/dev/spidev1.0", "rw");
            byte[] sequence = {0x53, (byte) 0xEF, 0x6E, (byte) 6, 0, 0, 0, 0};
            for (byte instr : sequence) {
                raf.writeBytes(instr + "");
            }
            raf.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sleep(int a, int b) {
        try {
            Thread.sleep(a, b);
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
    }
}
