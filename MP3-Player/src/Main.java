import java.io.IOException;

public class Main {
	// Declare Classes
	static LCD lcd;
	static Decoder decoder;
	static Console console;
	static String rootPath;
	static Menu menu;
	static Player player;
	static boolean debug = true;

	public static void main(String[] args) throws IOException {
		System.out.println("**STARTING UP**");
		Main main = new Main();
        main.initialize();
		lcd.drawScreenElements();
		lcd.ledsOff();
		player.setTrackNumber(1);
		player.playTrackNumber(1);// Player plays first track
	}

	public void initialize() throws IOException {// Initialize all
														// classes and objects
		Console console = new Console();
        console.debug = true;
        console.printDebug("  Initializing...");
//		rootPath = System.getProperty("user.dir");
        rootPath = this.getClass().getClassLoader().getResource("").getPath();
        console.printDebug("root: " + rootPath);

        console.printDebug("    Working directory: " + rootPath);
		lcd = new LCD();
		ScreenElement _intro = new ScreenElement(0, 0, Main.rootPath + "/../res/images/Intro.bmp");
		lcd.addScreenElement(_intro);
		//lcd.init();
        console.printDebug("    LCD created...");
        IO io = new IO();
        io.ioinit();
        decoder = new Decoder();
        decoder.sineTest((byte) 6);
        console.printDebug("Initiating sine test sequence.");
        console.printDebug("    Decoder created...");
        console.printDebug("    IO created...");
		console = new Console();
		console.debug = true;
        console.printDebug("    Console created...");
		menu = new Menu();
        console.printDebug("    Menu created...");
		player = new Player();
        console.printDebug("    Player created...");
        console.printDebug("  Done!");
	}
}
