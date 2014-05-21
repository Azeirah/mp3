import java.io.IOException;

public class Main {
    // Declare Classes
    LCD lcd;
    Decoder decoder;
    General_IO io;
    public static Console console;
    String rootPath;
    Menu menu;
    Player player;
    boolean debug = true;

    public static void main(String[] args) throws IOException {
        Main main = new Main();
        System.out.println("**STARTING UP**");
        main.initialize();
        Util.sleep(1000, 0);
        main.lcd.drawScreenElements();
        main.lcd.ledsOff();
        main.player.setTrackNumber(1);
        main.player.playTrackNumber(1);// mp3.Player plays first track
    }

    private void init_io() {
        System.out.println("Initializing IO ...");
        io = new General_IO();
        io.ioinit();
        System.out.println("Done initializing IO");
    }

    private void init_path() {
        rootPath = System.getProperty("user.dir");
    }

    private void init_lcd() throws IOException {
        System.out.println("Initializing.LCD ...");
        lcd = new LCD(this);
        ScreenElement intro = new ScreenElement(0, 0, rootPath);
        lcd.addScreenElement(intro);
        System.out.println("Done initializing LCD");
    }

    private void init_decoder() {
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
        player = new Player();
        System.out.println("Done initializing player");
    }

    public void initialize() throws IOException {// Initialize all
        init_path();
        init_io();
        init_lcd();
        init_decoder();
        init_console();
        init_menu();
        init_player();
        System.out.println("----------------------------------------");
        System.out.println("Done initializing everything! :)");
    }
}
