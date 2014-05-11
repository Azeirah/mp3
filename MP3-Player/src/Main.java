import java.io.IOException;

public class Main {
	//Declare Classes
	static LCD lcd;
	static Decoder decoder;
	static IO io;
	static Console console;
	static String rootPath;
	
	public static void main(String[] args) throws IOException {// The main function, the very start
		System.out.println("**STARTING UP**");
		initialize();
		io.sleep(1000);
		lcd.intro(lcd._intro);
	}

	public static void initialize() throws IOException {// Initialize all classes and objects
		System.out.println("  Initializing...");
		rootPath = System.getProperty("user.dir");
		System.out.println("    Working directory: "+rootPath);
		lcd = new LCD();
		lcd.init();
		System.out.println("    LCD created...");
		decoder = new Decoder();
		System.out.println("    Decoder created...");
		io = new IO();
		System.out.println("    IO created...");
		console = new Console();
		console.debug = false;
		System.out.println("    Console created...");
		lcd.init();
		System.out.println("  Done!");
		
	}
}
