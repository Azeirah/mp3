import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.RandomAccessFile;

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

    private File song;
    private int bytesPlayed = 0;


    // right channel volume

    // 0x0000 = LOUD
    // 0xFEFE = silent
    public Decoder(Main parent) throws IOException {
        // sendInstruction();
        this.parent = parent;
        setSong("test.mp3");
//        byte[] clock = { 0x02, 0x03, -101, -24};
//        parent.io.writeSCI(freq);
//        parent.io.writeSCI(clock);
    }

    public void setSong(String songname) {
        String path = parent.rootPath + "/songs/" + songname;
        System.out.println("songname = [" + songname + "]");
        song = new File(path);
    }

    public void play() throws IOException, Exception {
        byte[] init = {2, 0, 8, 38};
        byte[] clockf = {2, 3, -101, -24};
        byte[] audata = {2, 5, -84, 69};
        if (song == null) {
            throw new Exception("You must select a song before trying to play");
        }
        while (parent.io.ioread(DREQ) == 0) {
            // wait 10 ns to give other threads the opportunity to do stuff
//            Util.sleep(0, 10);
//            System.out.println("DREQ is high, waiting for low");
        }
        parent.io.writeSCI(init);
        parent.io.writeSCI(clockf);
        parent.io.writeSCI(audata);
        parent.io.setVolume(50);
        RandomAccessFile audio = new RandomAccessFile(new File("songs/test.mp3"), "r");
        byte[] buffer = new byte[32];

        while (true) {
            audio.read(buffer);
            while (parent.io.ioread(DREQ) == 0) {
//                Util.sleep(0, 10);
//                System.out.println("DREQ is high, waiting for low");
            }
            bytesPlayed += 32;
            parent.io.writeSDI(buffer);
        }
    }
}
