/**
 * Created by student on 28-5-14.
 */
public class TestInputPins {

    public static void main(String[] args) {
        Gpio io = new Gpio();
        io.ioinit();
        Console c = new Console();

        int[] pins = {84, 85, 94, 95};

        while (true) {
            for (int i : pins) {
                System.out.print(io.ioread(i));
            }
            System.out.println();
        }
    }
}
