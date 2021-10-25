package sg.edu.iss.jam.DTO;

import sg.edu.iss.jam.model.Media;

public class MediaDTO {
	
	
	Media media;
	
	int viewcount;
	
	int commentscount;
	
	String tags;
	
	public MediaDTO(Media media, int viewcount, int commentscount, String tags) {
		super();
		this.media = media;
		this.viewcount = viewcount;
		this.commentscount = commentscount;
		this.tags = tags;
	}

	public Media getMedia() {
		return media;
	}

	public void setMedia(Media media) {
		this.media = media;
	}

	public int getViewcount() {
		return viewcount;
	}

	public void setViewcount(int viewcount) {
		this.viewcount = viewcount;
	}


	public String getTags() {
		return tags;
	}

	public void setTags(String tags) {
		this.tags = tags;
	}


	public int getCommentscount() {
		return commentscount;
	}


	public void setCommentscount(int commentscount) {
		this.commentscount = commentscount;
	}
	
	
	

}
