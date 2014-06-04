import java.io.IOException;

public class Player {// everything for playing the music
	int volume = 200;
	Decoder decoder;
	Interface anInterface;
	final int volumeStep = 2;

	public Player(Decoder decoder, Interface anInterface, int volume) {
		this.volume = volume;
		this.decoder = decoder;
		this.anInterface = anInterface;
		this.decoder.setVolume(this.volume);
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
				changeVolume(volumeStep);
			}
			if (anInterface.isRightButtonSignal()) {
				System.out.println("Right button signal");
				changeVolume(-volumeStep);
			}
			// make sure the poor foxg20 doesn't burn out and die a painful
			// death. (and then reboot)
			Util.sleep(2);
		}
	}

	public void playTrackNumber(int _N) {// plays track number _N

	}

	public void playTrackName(String _name) {// plays track number N

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