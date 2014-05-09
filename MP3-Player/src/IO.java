public class IO {
	//We need all the pin allocations to get here, I guess
	public void sleep(int a) {
		try {
			Thread.sleep(a);
		} catch (InterruptedException ex) {
			Thread.currentThread().interrupt();

		}

	}
}
