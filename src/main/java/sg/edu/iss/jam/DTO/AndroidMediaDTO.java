package sg.edu.iss.jam.DTO;

public class AndroidMediaDTO {

	private Long mediaId;
	private Long artistId;
	private Long albumId;
	private String mediaThumbnailUrl;
	private String artistProfileThumbnailUrl;
	private String artistName;
	private String mediaTitle;
	private String mediaDuration;
	private String tags;
	private String albumName;
	private String mediaUrl;
	private String createdOn;
	private String userHistorySize;
	private Boolean subscribed;

	public AndroidMediaDTO() {
		super();
	}

	public String getMediaUrl() {
		return mediaUrl;
	}

	public void getMediaUrl(String mediaUrl) {
		this.mediaUrl = mediaUrl;
	}

	public void setMediaUrl(String mediaUrl) {
		this.mediaUrl = mediaUrl;
	}

	public Boolean getSubscribed() {
		return subscribed;
	}

	public void setSubscribed(Boolean subscribed) {
		this.subscribed = subscribed;
	}

	public String getMediaThumbnailUrl() {
		return mediaThumbnailUrl;
	}

	public void setMediaThumbnailUrl(String videoThumbnailUrl) {
		this.mediaThumbnailUrl = videoThumbnailUrl;
	}

	public String getArtistProfileThumbnailUrl() {
		return artistProfileThumbnailUrl;
	}

	public void setArtistProfileThumbnailUrl(String artistProfileThumbnailUrl) {
		this.artistProfileThumbnailUrl = artistProfileThumbnailUrl;
	}

	public String getArtistName() {
		return artistName;
	}

	public void setArtistName(String artistName) {
		this.artistName = artistName;
	}

	public String getMediaTitle() {
		return mediaTitle;
	}

	public void setMediaTitle(String videoTitle) {
		this.mediaTitle = videoTitle;
	}

	public String getMediaDuration() {
		return mediaDuration;
	}

	public void setMediaDuration(String videoDuration) {
		this.mediaDuration = videoDuration;
	}

	public String getTags() {
		return tags;
	}

	public void setTags(String tags) {
		this.tags = tags;
	}

	public Long getMediaId() {
		return mediaId;
	}

	public void setMediaId(Long mediaId) {
		this.mediaId = mediaId;
	}

	public Long getArtistId() {
		return artistId;
	}

	public void setArtistId(Long artistId) {
		this.artistId = artistId;
	}

	public String getCreatedOn() {
		return createdOn;
	}

	public void getCreatedOn(String createdOn) {
		this.createdOn = createdOn;
	}

	public void setCreatedOn(String createdOn) {
		this.createdOn = createdOn;
	}

	public String getUserHistorySize() {
		return userHistorySize;
	}

	public void getUserHistorySize(String userHistorySize) {
		this.userHistorySize = userHistorySize;
	}

	public void setUserHistorySize(String userHistorySize) {
		this.userHistorySize = userHistorySize;
	}

	public Long getAlbumId() {
		return albumId;
	}

	public void setAlbumId(Long albumId) {
		this.albumId = albumId;
	}

	public String getAlbumName() {
		return albumName;
	}

	public void setAlbumName(String albumName) {
		this.albumName = albumName;
	}

}
