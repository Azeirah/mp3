
public class Main {
	//Declare Classes
	static LCD lcd;
	static Decoder decoder;
	static IO io;
	
	public static void main(String[] args) {//The main function, the very start
		System.out.println("42ES04C MP3-PLAYER");
		initialize();
		io.sleep(1000);
		System.out.println("Yeah, dat werkt");
	}
	
	public static void initialize(){//Initialize all classes and objects
		lcd = new LCD();
		decoder = new Decoder();
		io = new IO();
	}
	


}
