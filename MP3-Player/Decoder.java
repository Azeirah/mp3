import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.RandomAccessFile;

public class Decoder extends Thread {
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
	private int index = 0;
	private boolean playing = false;

	// right channel volume

	// 0x0000 = LOUD
	// 0xFEFE = silent
	public Decoder(Main parent) throws IOException {
		// sendInstruction();
		this.parent = parent;
		setSong(parent.player.songs.get(index));
		// byte[] clock = { 0x02, 0x03, -101, -24};
		// parent.io.writeSCI(freq);
		// parent.io.writeSCI(clock);
	}

	public void run() {
		try {
			play();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		if (index > parent.player.songs.size() - 1) {
			this.index = 0;
		} else {
			this.index = index;
		}

	}

	public void setSong(String songname) {
		String path = parent.rootPath + "/songs/" + songname;
		System.out.println("songname = [" + songname + "]");
		song = new File(path);
	}

	public void setVolume(int volume) {
		try {
			parent.io.setVolume(volume);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void stopPlaying() {
		playing = false;
	}

	public void play() throws IOException, Exception {
		byte[] init = { 2, 0, 8, 38 };
		byte[] clockf = { 2, 3, -101, -24 };
		byte[] audata = { 2, 5, -84, 69 };
		playing = true;
		if (song == null) {
			throw new Exception("You must select a song before trying to play");
		}
		while (parent.io.ioread(DREQ) == 0) {
			// wait 10 ns to give other threads the opportunity to do stuff
			// Util.sleep(0, 10);
			// System.out.println("DREQ is high, waiting for low");
		}
		parent.io.writeSCI(init);
		parent.io.writeSCI(clockf);
		parent.io.writeSCI(audata);

		RandomAccessFile audio = new RandomAccessFile(song, "r");
		System.out.println("The decoder is now playing music");
		byte[] buffer = new byte[32];

		while (audio.read(buffer) != -1 && playing) {
			while (parent.io.ioread(DREQ) == 0) {
				// Util.sleep(0, 10);
				// System.out.println("DREQ is high, waiting for low");
			}
			bytesPlayed += 32;
			parent.io.writeSDI(buffer);
			Util.sleep(1);
		}
		audio.close();

	}
}
