public class Util {
    public static void sleep(int millis, int nanos) {
        try {
            Thread.currentThread().sleep(millis, nanos);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
