
import java.io.*;
//import java.util.Date;
 
public class MP3Decoder extends Thread {
 
    private Gpio gpio = new Gpio();
    private RandomAccessFile command;
    private File currentSong;
 //   private StreamStatus streamStatus;
    private long playedBytes = 0;
 
    public MP3Decoder(File mp3) {
        this.initialize();
        this.currentSong = mp3;
    }
 
//    public void run() {
//        this.streamStatus = StreamStatus.PLAY;
//        this.play();
//    }
 
    //het initialiseren van de mp3decoder
    public void initialize() {
        try {
            this.command = new RandomAccessFile("/dev/spidev1.0", "rw");
 
        byte[] mode = { 0x02, 0x00, 0x08, 0x26 };
        byte[] freq = { 0x02, 0x05, (byte) 0xAC, 0x45 };
        byte[] clock = { 0x02, 0x03, (byte) 0x9B, (byte) 0xE8 };
 
 
            this.sciwrite(mode);
            this.sciwrite(clock);
            this.sciwrite(freq);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
 
    //zorgt voor het schrijven van commandos naar de decoder
    private void sciwrite(byte[] bytes) {
        try {
            while (gpio.ioread(83)==1) {
                // nothing, wait till DREQ is 1
            }
            this.command.write(bytes);
 
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
 
    private void play() {
        try {
            File a = new File("/dev/spidev1.1");
            FileOutputStream data = new FileOutputStream(a);
            FileInputStream fis = new FileInputStream(this.currentSong);
             
            byte[] buffer = new byte[32];
            int length = 1;
            this.playedBytes = 0;
             
            //alleen in de loop blijven als het aantal gelezen bytes groter is dan 0 en de streamstatus is niet gelijk aan stop
//            while ((length = fis.read(buffer)) > 0 && this.streamStatus != StreamStatus.STOP) {
//                 
//                while (gpio.ioread(Pin.DREQ.getPinNumber()) == 0) {
//                    // do nothing
//                }
//                 
//                //om de tijd van het nummer bij te houden, gebruiken we het aantal gespeelde bytes als houvast
//                switch(this.streamStatus)
//                {
//                case PLAY:
//                    this.playedBytes += length;
//                    data.write(buffer, 0, length);
//                    break;
//                case PAUSE:
//                    while(this.streamStatus == StreamStatus.PAUSE)
//                    {
//                        //do nothing
//                    }
//                    break;
//                case FORWARD:
//                    this.playedBytes += 32;
//                    fis.skip((long) 32);
//                    break;
//                case REWIND:
//                    this.playedBytes -= 32;
//                    fis.skip((long) -32);
//                    break;
//                    default:
//                        //
//                }
//            }
 
            data.close();
            fis.close();
 
        } catch (FileNotFoundException e) {
            System.out.println("File not found:" + e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
 
    public void setVolume(int level) {
        try{
            if(level >= 0 && level <= 100)
            {
                // (250/100) is the ratio between 100% and the difference in volume
                int volume = (250 / 100) * level;
                // 'invert' it, because the highest is 0x00 and the lowest is 0xFE
                volume = 0xFE - volume;
                // and write it to the decoder
                byte[] bytes = {(byte) 0x02, (byte) 0x0B, (byte) volume, (byte) volume};
                this.sciwrite(bytes);
            }
            else
            {
                throw new Exception("Volume niet geldig");
            }
        } catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
    }
 
    public void setCurrentSong(File song)
    {
        this.currentSong = song;
    }
     
    public File getCurrentSong()
    {
        return this.currentSong;
    }
 
//    public void setStreamStatus(StreamStatus streamStatus) {
//        this.streamStatus = streamStatus;
//    }
     
//    public StreamStatus getStreamStatus()
//    {
//        return this.streamStatus;
//    }   
     
    public long getCurrentTime()
    {
        long playedBits = this.playedBytes * 8;
        long seconds = playedBits / (192*1024);
        return seconds;
    }
     
    public long getTotalTime()
    {
        long totalBits = this.currentSong.length() * 8;
        long seconds = totalBits / (192*1024);
        return seconds;
    }
 
}