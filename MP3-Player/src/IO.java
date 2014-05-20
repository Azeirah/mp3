

public class IO {
	protected native int ioinit();
	protected native int iowrite(int a, int v);
	protected native int ioread(int a);
    protected native int iodeinit();

	static {
		System.loadLibrary("gpio");
	}
}
