

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
		for (int i = 0; i < aoID3Tag.length; i++) {
			if (aoID3Tag[i] instanceof ID3V1_0Tag) {
				ID3V1_0Tag oID3V1_0Tag = (ID3V1_0Tag) aoID3Tag[i];
				if (oID3V1_0Tag.getTitle().length() > 0) {
					setTitle(oID3V1_0Tag.getTitle());
				} else {
					setTitle("Title Unknown");
				}
				if (oID3V1_0Tag.getAlbum().length() > 0) {
					setAlbum(oID3V1_0Tag.getAlbum());
				} else {
					setAlbum("Album Unknown");
				}
				if (oID3V1_0Tag.getArtist().length() > 0) {
					setArtist(oID3V1_0Tag.getArtist());
				} else {
					setArtist("Artist Unknown");
				}
				if (oID3V1_0Tag.getYear().length() > 0) {
					setYear(oID3V1_0Tag.getYear());
				} else {
					setYear("Year Unknown");
				}
				if (oID3V1_0Tag.getGenre() != null) {
					setGenre(oID3V1_0Tag.getGenre());
				} else {
					setYear("Genre Unknown");
				}
			}
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