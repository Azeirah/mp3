import java.io.IOException;

public class Main {
	// Declare Classes
	static LCD lcd;
	static Decoder decoder;
	static IO io;
	static Console console;
	static String rootPath;
	static Menu menu;
	static Player player;
	static boolean debug = true;

	public static void main(String[] args) throws IOException {
		System.out.println("**STARTING UP**");
		initialize();
		io.sleep(1000,0);
		lcd.drawScreenElements();
		lcd.ledsOff();
		player.setTrackNumber(1);
		player.playTrackNumber(1);// Player plays first track
	}

	public static void initialize() throws IOException {// Initialize all
														// classes and objects
		System.out.println("  Initializing...");
		rootPath = System.getProperty("user.dir");
		System.out.println("    Working directory: " + rootPath);
		lcd = new LCD();
		ScreenElement _intro = new ScreenElement(0,0,Main.rootPath + "/res/images/Intro.bmp");
		lcd.addScreenElement(_intro);
		//lcd.init();
		System.out.println("    LCD created...");
		decoder = new Decoder();
		System.out.println("    Decoder created...");
		io = new IO();
		if (!debug) {
			io.init();
			io.ioinit(); // Initialize GPIO lines
		}
		System.out.println("    IO created...");
		console = new Console();
		console.debug = true;
		System.out.println("    Console created...");
		menu = new Menu();
		System.out.println("    Menu created...");
		player = new Player();
		System.out.println("    Player created...");
		System.out.println("  Done!");
	}
}
