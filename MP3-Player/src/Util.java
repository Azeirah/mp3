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
    
    public static byte stringToByte(String input){
		byte result = 0;
		byte compare = 0;
		for (int i = 0; i < 8; i++){
			if (input.charAt(i) == '1'){
				compare = 1;
			}
			else if (input.charAt(i) == '0'){
				compare = 0;
			}
			result = (byte) ((result <<= 1) | compare);
		}
		return result;
	}
    
    //Turns a byte into a string using the format newLCDWrite accepts ("10101010", "11110000", etc.)
	public static String byteToString(byte a){
		byte b;
		String c = "";
		for(int i = 7; i >= 0 ; i--){
			b = (byte) (a >> i);
			b = (byte) (b & 1);
			if(b > 0){
				c = c.concat("1");
			} else {
				c = c.concat("0");
			}
		}
		return c;
	}
}
