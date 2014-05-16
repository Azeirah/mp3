/**
 * Created by student on 15-5-14.
 */
public class PinHighTest {
    public static void main(String args[]) {
        System.out.println("Starting up test");
        GPIO io = new GPIO();
        io.ioinit();
        io.iowrite(57, 1);
        Util.sleep(15000, 0);
        io.iowrite(57, 0);
        for (int i = 0; i < 10; i++) {
            io.iowrite(57, 1);
            Util.sleep(500, 0);
            io.iowrite(57, 0);
            Util.sleep(500, 0);
	    System.out.println("For Loop" + i);
        }
        io.iodeinit();
	System.out.println("Ending test");

    }
}
