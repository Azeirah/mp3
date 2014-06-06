

import java.io.*;

import org.blinkenlights.jid3.*;
import org.blinkenlights.jid3.v1.*;
import org.blinkenlights.jid3.v1.ID3V1Tag.Genre;

public class Meta {
	private String title;
	private String artist;
	private String album;
	private String year;
	private Genre genre;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getArtist() {
		return artist;
	}

	public void setArtist(String artist) {
		this.artist = artist;
	}

	public String getAlbum() {
		return album;
	}

	public void setAlbum(String album) {
		this.album = album;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String string) {
		this.year = string;
	}

	public Genre getGenre() {
		return genre;
	}

	public void setGenre(Genre genre2) {
		this.genre = genre2;
	}

	public Meta(String _path) throws ID3Exception {
		File oSourceFile = new File(_path);
		MediaFile oMediaFile = new MP3File(oSourceFile);
		ID3Tag[] aoID3Tag = oMediaFile.getTags();
//		System.out.println(aoID3Tag[1]);
		
		// V OVERBODIG?
		
		for (int i = 0; i < aoID3Tag.length; i++) {
//			System.out.println(aoID3Tag[i]);
			String tag = aoID3Tag[i].toString();
			if (tag.startsWith("Song")) {
				setTitle(tag.substring(tag.indexOf("= [") + 3, tag.length() - 1));
			}
			else if (tag.startsWith("Art")) {
				System.out.println("FUCKKKKKKKKKKKK");
				setAlbum(tag.substring(tag.indexOf("= [") + 3, tag.length() - 1));
			}
//			if (aoID3Tag[i] instanceof ID3V1_0Tag) {
//				ID3V1_0Tag oID3V1_0Tag = (ID3V1_0Tag) aoID3Tag[i];
//				if (oID3V1_0Tag.getTitle().length() > 0&&oID3V1_0Tag.getTitle()!=null) {
//					setTitle(oID3V1_0Tag.getTitle());
//				} else {
//					setTitle("Title Unknown");
//				}
//				if (oID3V1_0Tag.getAlbum().length() > 0 && oID3V1_0Tag.getAlbum() != null) {
//					setAlbum(oID3V1_0Tag.getAlbum());
//				} else {
//					setAlbum("Album Unknown");
//				}
//				if (oID3V1_0Tag.getArtist().length() > 0 && oID3V1_0Tag.getArtist() != null) {
//					setArtist(oID3V1_0Tag.getArtist());
//				} else {
//					setArtist("Artist Unknown");
//				}
//				if (oID3V1_0Tag.getYear().length() > 0) {
//					setYear(oID3V1_0Tag.getYear());
//				} else {
//					setYear("Year Unknown");
//				}
//				if (oID3V1_0Tag.getGenre() != null) {
//					setGenre(oID3V1_0Tag.getGenre());
//				} else {
//					setYear("Genre Unknown");
//				}
//			}
			if(oMediaFile.getID3V2Tag().getTitle() != null && !oMediaFile.getID3V2Tag().getTitle().equals("null") ){
				setTitle(oMediaFile.getID3V2Tag().getTitle());
				System.out.println("Hoi");
			} else {
				System.out.println("Niet hoi");
				setTitle("Unknown");
			}
			if(oMediaFile.getID3V2Tag().getArtist() != null && !oMediaFile.getID3V2Tag().getArtist().equals("null")){
				setArtist(oMediaFile.getID3V2Tag().getArtist());
			} else {
				setArtist("Unknown");
			}
			
//			System.out.println("Dit is de artiest: " + oMediaFile.getID3V2Tag().getArtist());
//			System.out.println("Dit is de titel: " + oMediaFile.getID3V2Tag().getTitle());
		}
	}

	public static void main(String[] args) throws Exception {
		Meta main = new Meta("D:/Bon Jovi - Have a nice day.mp3");
		System.out.println(main.getTitle());
		System.out.println(main.getAlbum());
		System.out.println(main.getArtist());
		System.out.println(main.getYear());
		System.out.println(main.getGenre());
	}
}