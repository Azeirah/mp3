import java.io.IOException;

public class Main {
    public static Console console;
    // Declare Classes
    public LCD lcd;
    public Decoder decoder;
    public General_IO io;
    public String rootPath;
    private Menu menu;
    private Player player;
    private boolean debug = true;
	public Interface anInterface;

    public static void main(String[] args) throws IOException, Exception {
        Main main = new Main();
        System.out.println("**STARTING UP**");
        main.initialize();
        Util.sleep(500, 0);
        System.out.println("Ready to go");
        main.io.newWriteLCD(0, "10101010");
        Util.sleep(100, 0);
        main.io.newWriteLCD(0, "01010101");
        Util.sleep(100, 0);
        main.io.newWriteLCD(0, "10000000");
        Util.sleep(100, 0);
        main.io.newWriteLCD(0, "11000000");
        Util.sleep(100, 0);
        main.io.newWriteLCD(0, "11100000");
        Util.sleep(100, 0);
        main.io.newWriteLCD(0, "11110000");
        System.out.println("Test doner kebab");
//		main.player.setTrackNumber(1);
//		main.player.playTrackNumber(1);// mp3.Player plays first trackmain
		System.out.println("start playing song");
		main.player.start();
    }

    private void init_pins() {
        System.out.println("Setting pins");
        int[] pins = {38, 39, 41, 42, 43, 54, 57, 58, 59, 60, 80, 81, 84, 85,
                94, 95};
        for (int pin : pins) {
            Gpio.iowrite(pin, 0);
        }
        System.out.println("Pins set to LOW");
    }

    public void init_io() throws IOException {
        System.out.println("Initializing IO ...");
        io = new General_IO();
        io.ioinit();
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
        System.out.println("Path: " + rootPath + "/res/images/Intro.bmp");
        ScreenElement intro = new ScreenElement(0, 0, rootPath
                + "/res/images/Intro.bmp");
        lcd.addScreenElement(intro);
        
        //reset pin, CS pin etc.
        io.setPin(80,  1);
        //Assuming 1 = CS1H and CS2L = U2. 0 = CS1L and CS2H = U3 
        io.setPin(81, 1);
        lcd.reset();
        
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

    private void init_player() {
        System.out.println("Initializing player ...");
        player = new Player(decoder, anInterface, 50);
        System.out.println("Done initializing player");
    }

    public void initialize() throws IOException {// Initialize all
//        init_path();
        init_io();
//        init_pins();
//        init_lcd();
        init_decoder();
//        init_console();
        init_interface();
//        init_menu();
        init_player();
        System.out.println("----------------------------------------");
        System.out.println("Done initializing everything! :)");
    }
}
