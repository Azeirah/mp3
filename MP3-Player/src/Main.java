public class Main {
	// Declare Classes
	static LCD lcd;
	static Decoder decoder;
	static IO io;
	static ConsoleInput console;

	public static void main(String[] args) {// The main function, the very start
		System.out.println("**STARTING UP**");
		initialize();
		io.sleep(1000);
		console.printPrompt(console.inString("What is your name?"));
	}

	public static void initialize() {// Initialize all classes and objects
		System.out.print("Initializing...");
		lcd = new LCD();
		decoder = new Decoder();
		io = new IO();
		console = new ConsoleInput();
		System.out.println("       Done!");
	}
}
