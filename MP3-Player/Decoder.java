import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.RandomAccessFile;

import com.mpatric.mp3agic.ID3v2;
import com.mpatric.mp3agic.InvalidDataException;
import com.mpatric.mp3agic.Mp3File;
import com.mpatric.mp3agic.UnsupportedTagException;

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
    private int index = 0;
    private boolean playing = false;
    private boolean stopped = true;
    public boolean counting = false;
    public String title = "";
    public String artist = "";
    public long duration = 0;
    public long startTime = 0;

    // right channel volume

    // 0x0000 = LOUD
    // 0xFEFE = silent
    public Decoder(Main parent) throws IOException {
        // sendInstruction();
        this.parent = parent;
        // byte[] clock = { 0x02, 0x03, -101, -24};
        // parent.io.writeSCI(freq);
        // parent.io.writeSCI(clock);
    }

    public void run() {
    	setVolume(200);
        try {
            while (true) {
                play();
                Util.sleep(500);
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (Exception e) {
            // TODO Auto-generated catch block046
            e.printStackTrace();
        }
    }

    public boolean isPlaying() {
        return playing;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        int size = parent.player.songs.size();
        System.out.println("size of array is: " + size);
        System.out.println("index chosen is: " + index);
        if (index > size - 1) {
            System.out.println("Starting from beginning of songlist");
            this.index = 0;
        } else if (index < 0) {
            System.out.println("Starting from back of songlist");
            this.index = size - 1;
        } else {
            this.index = index;
        }
    }

    public void setSong(String songname) {
        String path = parent.rootPath + "/songs/" + songname;
        Mp3File meta = null;
		try {
			meta = new Mp3File(path);
		} catch (UnsupportedTagException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidDataException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (meta != null) {
			ID3v2 audio = meta.getId3v2Tag();
	        if (audio.getTitle() != null) {
	            title = audio.getTitle();
	        } else {
	            title = "Unknown title";
	        }

	        if (audio.getAlbum() != null) {
	            artist = audio.getArtist();
	        } else {
	            artist = "Unknown artist";
	        }
	        
	        duration = meta.getLengthInSeconds();
			System.out.println("duration = [" + duration + "]");
	        System.out.println("songname = [" + songname + "]");
	        System.out.println("title = [" + title + "]");
	        System.out.println("artist = [" + artist + "]");
	        //Gives this to the LCD's firstLine
	        String firstLine = title + "-" + artist;
	        for(int i = 0; i < firstLine.length(); i++){
	        	parent.lcd.firstLine[i] = firstLine.charAt(i);
	        }
		} else {
			System.out.println("Could not open audio file as meta");
		}
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

    public void pause() {
        playing = false;
    }

    public void unpause() {
        playing = true;
    }

    public void stopPlaying() {
        stopped = true;
        counting = false;
    }

    public void play() throws IOException, Exception {
        byte[] init = { 2, 0, 8, 38 };
        byte[] clockf = { 2, 3, -101, -24 };
        byte[] audata = { 2, 5, -84, 69 };
        playing = true;
        stopped = false;
        setSong(parent.player.songs.get(index));
        //Writes stuff on the LCD

        if (song == null) {
            throw new Exception("You must select a song before trying to play");
        }
        while (Gpio.ioread(DREQ) == 0) {
            // wait 10 ns to give other threads the opportunity to do stuff
            // Util.sleep(0, 10);
            // System.out.println("DREQ is high, waiting for low");
        }
        parent.io.writeSCI(init);
        parent.io.writeSCI(clockf);
        parent.io.writeSCI(audata);

        RandomAccessFile audio = new RandomAccessFile(song, "r");
        System.out.println("The decoder is now playing music");
        counting = true;
        startTime = System.currentTimeMillis();
        byte[] buffer = new byte[32];

        while (!stopped) {
            if (playing) {
                if (audio.read(buffer) != -1) {
                    while (Gpio.ioread(DREQ) == 0) {
                        // Util.sleep(0, 10);
                        // System.out.println("DREQ is high, waiting for low");
                    }
                    parent.io.writeSDI(buffer);
                } else {
                    stopped = true;
                    counting = false;
                }
            } else {
                System.out.println("I am paused");
                Util.sleep(200);
            }
        }
        audio.close();
        System.out.println("Stopping playing");
        parent.player.next();
    }
}
