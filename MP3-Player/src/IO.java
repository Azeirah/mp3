import java.io.IOException;

public class IO {
	public native int ioinit();
	public native int iowrite(int a, int v);
	public native int ioread(int a);

	void init() throws IOException{
		System.loadLibrary("gpio");
	}

	public void sleep(int a, int b) {
		try {
			Thread.sleep(a, b);
		} catch (InterruptedException ex) {
			Thread.currentThread().interrupt();

		}

	}

	public void writeLCD(int _A0, int _RD, int _WR, int l){
		
	}
	
	public void writePin(int _N,boolean _val){
		if(_val){
			iowrite(_N,1);
		}else{
			iowrite(_N,0);
		}
	}
	
	public void writePin(int _N,int _val){
		iowrite(_N,_val);
	}
	
	public boolean readPin(int _N){
		boolean _value = false;
		if(ioread(_N)>0){
			_value = true;
		}
		return _value;
	}
	
	public void deInit() {
		deInit(); // Deinitialize GPIO lines
	}
}


// for (int i = 0; i < 200000; i++) {
// io.iowrite(80, 1); // Make output PB16 high
// System.out.println("PB30: " + io.ioread(94)); // Read status of input
// PB30
// io.iowrite(80, 0); // Make output PB16 low
// System.out.println("PB30: " + io.ioread(94)); // Read status of input
// PB30
// }