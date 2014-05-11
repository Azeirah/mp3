import java.io.IOException;

public class Main {
	//Declare Classes
	static LCD lcd;
	static Decoder decoder;
	static IO io;
	static Console console;

	public static void main(String[] args) throws IOException {// The main function, the very start
		System.out.println("**STARTING UP**");
		initialize();
		io.sleep(1000);
		lcd.intro(lcd._intro);
	}

	public static void initialize() throws IOException {// Initialize all classes and objects
		System.out.print("Initializing...");
		lcd = new LCD();
		decoder = new Decoder();
		io = new IO();
		console = new Console();
		lcd.init();
		System.out.println("       Done!");
	}
}
