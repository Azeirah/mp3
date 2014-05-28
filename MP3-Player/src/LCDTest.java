import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Test;


public class LCDTest {

	@Test
	public void test() throws IOException {
		Main main = new Main();
		System.out.println("**STARTING UP**");
		main.initialize();
		Util.sleep(1000, 0);
		System.out.println("ready to go");
		//main.lcd.ledsOff();
		main.lcd.drawScreenElements();
	
	}

}
