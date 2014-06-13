 public class Gpio {
    public static native int ioinit();

    public static native int iowrite(int a, int v);

    public static native int ioread(int a);

    public static native int iodeinit();

    static {
        System.loadLibrary("gpio");
    }
}
