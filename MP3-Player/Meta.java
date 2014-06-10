
import java.io.*;

import com.mpatric.mp3agic.Mp3File;

public class Meta {
	
	public static String formatTime(long time) {
		String formatted = "";
		int minutes = (int) time/60;
		int seconds = (int) time%60;
		// dit kan sowieso makelijker, zeg, in een à twee lijntjes bijvoorbeeld, maar dit werkt :>
		// btw dit werkt niet meer wanneer een liedje langer dan 99 minuten lang is :)
		if (("" + minutes).length () == 0) {
			formatted += "00";
		} else if (("" + minutes).length() == 1) {
			formatted += "0" + minutes;
		} else {
			formatted += minutes;
		}
		formatted += ":";
		if (("" + seconds).length() == 0) {
			formatted += "00";
		} else if (("" + seconds).length() == 1) {
			formatted += "0" + seconds;
		} else {
			formatted += seconds;
		}
		return formatted;
	}

	public static void main(String[] args) throws Exception {
		Mp3File file = new Mp3File("demons-and-wizards-fiddler-on-the-green.mp3");
		System.out.println(file.getLengthInSeconds());
		System.out.println(Meta.formatTime(file.getLengthInSeconds()));
	}
}