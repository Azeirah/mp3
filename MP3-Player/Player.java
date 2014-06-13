import java.io.File;
import java.util.ArrayList;
import java.util.Collections;

public class Player {// everything for playing the music
	Main parent;
	public int volume;
	Decoder decoder;
	Interface anInterface;
	final int volumeStep = 4;
	String rootPath = System.getProperty("user.dir");
	String songsPath = rootPath + "/songs/";
	// Holds all song paths
	ArrayList<String> songs = new ArrayList<String>();
	long time;
	long lastLCDUpdate;

	public Player(Decoder decoder, Interface anInterface, int volume, Main parent) {
		this.parent = parent;
		this.volume = volume;
		this.decoder = decoder;
		this.anInterface = anInterface;
		indexSongs();
		decoder.setSong(songs.get(decoder.getIndex()));
	}

	public void indexSongs() {
		File songsFolder = new File(songsPath);
		String[] filesInSongs = songsFolder.list();
		for (String str : filesInSongs) {
			if (str.contains("mp3")) {
				songs.add(str);
			}
		}
		Collections.sort(songs, String.CASE_INSENSITIVE_ORDER);
		System.out.println("Printing songs");
		System.out.println("|---------------------------|");
		for (String str : songs) {
			System.out.println("|  " + str);
		}
		System.out.println("|---------------------------|");
	}

	public void start() {
		int rotary;
		decoder.start();
		System.out.println("Starting player playing song");
		while (true) {
			rotary = anInterface.readRotaryDial();
			if (rotary == 2) {
				changeVolume(volumeStep);
			} else if (rotary == 0) {
				changeVolume(-volumeStep);
			}
			if (anInterface.readButtonLeft()) {
				System.out.println("Left button signal");
				// changeVolume(volumeStep);
				previous();
			}
			if (anInterface.readButtonRight()) {
				System.out.println("Right button signal");
				// changeVolume(-volumeStep);
				next();
			}
			if (anInterface.readButtonMiddle()) {
				System.out.println("Middle button signal");
				if (decoder.isPlaying()) {
					decoder.pause();
				} else {
					decoder.unpause();
				}
				Util.sleep(50);
			}
			//Updates 5 times a second
//			if(System.currentTimeMillis() > lastLCDUpdate + 200){
//				parent.lcd.update();
//				lastLCDUpdate = System.currentTimeMillis();
//			}
			
			// make sure the poor foxg20 doesn't burn out and die a painful
			// death. (and then reboot)
			Util.sleep(1);
			
		}
	}

	public void next() {
		decoder.stopPlaying();
		decoder.setIndex(decoder.getIndex() + 1);
	}

	public void previous() {
		decoder.stopPlaying();
		decoder.setIndex(decoder.getIndex() - 1);
	}

	public void playTrackNumber(int _N) {// plays track number _N
		if (_N < songs.size()) {
			decoder.setSong(songs.get(_N - 1));
			System.out.println("Song set to: " + songs.get(_N - 1));
		} else {
			System.out.println("That song doesn't exist!");
		}
	}

	public void playTrackName(String _name) {// plays track number N
		for (String str : songs) {
			if (str.equals(_name)) {
				decoder.setSong(str);
				return;
			}
		}
		System.out.println("That song is not in the /songs/ folder!");
	}

	public void changeVolume(int volumeSteps) {
		volume += volumeSteps;
		System.out.println("volume was: " + volume);
		if (volume > 230)
			volume = 230;
		if (volume < 90)
			volume = 90;

		decoder.setVolume(volume);
		System.out.println("changing volume by " + volumeSteps
				+ " the volume is now " + volume);
	}
}