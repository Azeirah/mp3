import java.io.IOException;

import org.blinkenlights.jid3.ID3Exception;

public class Main {
    public static Console console;
    // Declare Classes
    public LCD lcd;
    public Decoder decoder;
    public General_IO io;
    public String rootPath;
    private Menu menu;
    public Player player;
    private boolean debug = true;
	public Interface anInterface;

    public static void main(String[] args) throws IOException, Exception {
        Main main = new Main();
        System.out.println("**STARTING UP**");
        main.initialize();
        Util.sleep(3000, 0);
        System.out.println("Ready to go");
		main.player.playTrackNumber(1); //Sets track number to 1
		System.out.println("start playing song");
		main.player.start();
    }

    private void init_pins() {
        System.out.println("Setting pins");
        int[] pins = {38, 39, 41, 42, 43, 54, 57, 58, 59, 60, 80, 81, 84, 85,
                94, 95};
        for (int pin : pins) {
            io.setPin(pin, 0);
        }
        System.out.println("Pins set to LOW");
    }

    public void init_io() throws IOException {
        System.out.println("Initializing IO ...");
        io = new General_IO();

        System.out.println("Done initializing IO");
    }

    private void init_path() {
        rootPath = System.getProperty("user.dir");
    }

    public void init_interface() {
    	System.out.println("Initialising interface ...");
    	anInterface = new Interface(io);
//    	anInterface.start();
    	System.out.println("Done initialising interface");
    }
    
    private void init_lcd() throws IOException {
        System.out.println("Initializing.LCD ...");
        lcd = new LCD(this);
 
        lcd.init(true);
        
        System.out.println("Done initializing LCD");
    }

    private void init_decoder() throws IOException {
        System.out.println("Initialing decoder ...");
        decoder = new Decoder(this);
        System.out.println("Done initializing decoder");
    }

    private void init_menu() {
        System.out.println("Initializing menu ...");
        menu = new Menu();
        System.out.println("Done initializing menu");
    }

    private void init_console() {
        System.out.println("Initializing console ...");
        console = new Console();
        System.out.println("Done initializing console");
    }

    private void init_player() throws ID3Exception {
        System.out.println("Initializing player ...");
        player = new Player(decoder, anInterface, 50);
        System.out.println("Done initializing player");
    }

    public void initialize() throws IOException, ID3Exception {// Initialize all
        init_path();
        init_io();
        init_pins();
        init_lcd();
        init_decoder();
//        init_console();
        init_interface();
//        init_menu();
        init_player();
        System.out.println("----------------------------------------");
        System.out.println("Done initializing everything! :)");
    }
}
