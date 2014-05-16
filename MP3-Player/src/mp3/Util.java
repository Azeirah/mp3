package mp3;

/**
 * Created by student on 16-5-14.
 */
public class Util {
    public static void sleep (int millis, int nanos) {
        try {
            Thread.currentThread().sleep(millis, nanos);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
