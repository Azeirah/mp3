import java.io.File;

public class MP3DecoderTest {

	public static void main(String[] args) {
		String rootPath = System.getProperty("user.dir");
		String _path = rootPath+"test.mp3";
		File _file = new File(_path);
		MP3Decoder decoder = new MP3Decoder(_file);
		decoder.initialize();
	}
}
