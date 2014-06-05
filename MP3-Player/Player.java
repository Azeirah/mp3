import java.io.IOException;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class Player {// everything for playing the music
	int volume = 200;
	Decoder decoder;
	Interface anInterface;
	final int volumeStep = 2;
	String rootPath = System.getProperty("user.dir");
	String songsPath = rootPath + "/songs/";
	//Holds all song paths
	ArrayList<String> songs = new ArrayList<String>();
	
	public Player(Decoder decoder, Interface anInterface, int volume) {
		this.volume = volume;
		this.decoder = decoder;
		this.anInterface = anInterface;
		this.decoder.setVolume(this.volume);
		indexSongs();
		decoder.setSong(songs.get(decoder.getIndex()));

	}

	public void indexSongs(){
		File songsFolder = new File(songsPath);
		String[] filesInSongs = songsFolder.list();
		for(String str : filesInSongs){
			if(str.contains("mp3")){
				songs.add(str);
			}
		}
		Collections.sort(songs, String.CASE_INSENSITIVE_ORDER);
		System.out.println("Printing songs");
		System.out.println("|---------------------------|");
		for(String str : songs){
			System.out.println("|  " + str);
		}
		System.out.println("|---------------------------|");		
	}
	
	public void start() {
		int rotary;
		decoder.start();
		System.out.println("Starting player playing song");
		while (true) {
			anInterface.read();
			rotary = anInterface.getRotaryDialSignal();
			if (rotary == 2) {
				changeVolume(volumeStep);
			} else if (rotary == 0) {
				changeVolume(-volumeStep);
			}
			if (anInterface.isLeftButtonSignal()) {
				System.out.println("Right button signal");
				//changeVolume(volumeStep);
				previous();
			}
			if (anInterface.isRightButtonSignal()) {
				System.out.println("Right button signal");
				//changeVolume(-volumeStep);
				next();
			}
			// make sure the poor foxg20 doesn't burn out and die a painful
			// death. (and then reboot)
			Util.sleep(2);
		}
	}

	public void next(){
		decoder.stopPlaying();
		decoder.setIndex(decoder.getIndex() + 1);
		try {
			decoder.play();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void previous(){
		decoder.stopPlaying();
		decoder.setIndex(decoder.getIndex() - 1);
		try {
			decoder.play();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void playTrackNumber(int _N) {// plays track number _N
		if(_N < songs.size()){
			decoder.setSong(songs.get(_N - 1));
			System.out.println("Song set to: " + songs.get(_N - 1) );
		} else {
			System.out.println("That song doesn't exist!");
		}
	}

	public void playTrackName(String _name) {// plays track number N
		for(String str : songs){
			if(str.equals(_name)){
				decoder.setSong(str);
				return;
			}
		}
		System.out.println("That song is not in the /songs/ folder!");
	}

	public void setTrackNumber(int _N) {

	}

	public void changeVolume(int volumeSteps) {
		volume += volumeSteps;
		if (volume > 220)
			volume = 220;
		if (volume < 170)
			volume = 170;
		decoder.setVolume(volume);
		System.out.println("changing volume by " + volumeSteps
				+ " the volume is now " + volume);
	}
}