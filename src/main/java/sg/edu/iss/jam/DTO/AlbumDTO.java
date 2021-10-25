package sg.edu.iss.jam.DTO;

import sg.edu.iss.jam.model.Album;

public class AlbumDTO {

	Album album;

	int SongCount;

	int ViewCount;

	public AlbumDTO(Album album, int songCount, int viewCount) {
		super();
		this.album = album;
		SongCount = songCount;
		ViewCount = viewCount;
	}

	public Album getAlbum() {
		return album;
	}

	public void setAlbum(Album album) {
		this.album = album;
	}

	public int getSongCount() {
		return SongCount;
	}

	public void setSongCount(int songCount) {
		SongCount = songCount;
	}

	public int getViewCount() {
		return ViewCount;
	}

	public void setViewCount(int viewCount) {
		ViewCount = viewCount;
	}

}
