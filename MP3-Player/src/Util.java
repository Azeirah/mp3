public class Util {
    public static void sleep(int millis, int nanos) {
        try {
            Thread.currentThread().sleep(millis, nanos);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static String joinPath(String path, String path2) {
        return path + "/" + path2;
    }
}
