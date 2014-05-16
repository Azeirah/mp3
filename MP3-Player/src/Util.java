import java.io.File;

/**
 * Created by student on 14-5-14.
 */
public class Util {
    public static String joinPath (String path1, String path2) {
        File file1 = new File(path1);
        File file2 = new File(file1, path2);
        return file2.getPath();
    }

    public static void sleep(int a, int b) {
        try {
            Thread.sleep(a, b);
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
    }
}
