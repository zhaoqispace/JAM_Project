package sg.edu.iss.jam.model;
import java.time.LocalDate;
import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Set;
@Entity
public class Media {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Enumerated(EnumType.STRING)
	private MediaType mediaType;
	
	private String mediaUrl;
	
	private String title;
	
	private String description;
	
	private String duration;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate createdOn;
	
	private String publishStatus;
	
	private String thumbnailUrl;
	
	private int AlbumOrder;

	//relation with userhistory
	@OneToMany(mappedBy = "mediaHistory",cascade = CascadeType.REMOVE)
	private Collection<UserHistory> userHistory;
	
	//relation with comment
	@OneToMany(mappedBy = "mediaComment",cascade = CascadeType.REMOVE)
	private Collection<Comments> commentList;
	
	//relation with channel
	@ManyToOne
	private Channel channel;
	
	//relation with album for music
	@ManyToOne
	private Album album;

	//relation with playlists
	@ManyToMany(mappedBy = "mediaPlayList")
	private Collection<Playlists> playLists;
	
	//relation with playlistorder
	@OneToMany(mappedBy = "media",cascade = CascadeType.REMOVE)
	private Collection<PlaylistOrder> PlaylistOrder;
	
	//relation with tag
	@ManyToMany(cascade = {CascadeType.PERSIST,CascadeType.MERGE,CascadeType.DETACH})
	@JoinTable(name="tag_media_tag_list")
	private Set<Tag> tagList;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public MediaType getMediaType() {
		return mediaType;
	}

	public void setMediaType(MediaType mediaType) {
		this.mediaType = mediaType;
	}

	public String getMediaUrl() {
		return mediaUrl;
	}

	public void setMediaUrl(String mediaUrl) {
		this.mediaUrl = mediaUrl;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDuration() {
		return duration;
	}

	public void setDuration(String duration) {
		this.duration = duration;
	}

	public Media() {
		super();
	}

	public Media(MediaType mediaType, String mediaUrl, String title, String description, String duration,
			LocalDate createdOn, String publishStatus, String thumbnailUrl) {
		super();
		this.mediaType = mediaType;
		this.mediaUrl = mediaUrl;
		this.title = title;
		this.description = description;
		this.duration = duration;
		this.createdOn = createdOn;
		this.publishStatus = publishStatus;
		this.thumbnailUrl = thumbnailUrl;
	}

	public Media(MediaType mediaType, String mediaUrl, String title, String description, String duration,
			LocalDate createdOn, String publishStatus, String thumbnailUrl, int albumOrder,
			Collection<UserHistory> userHistory, Collection<Comments> commentList, Channel channel, Album album,
			Collection<Playlists> playLists, Collection<sg.edu.iss.jam.model.PlaylistOrder> playlistOrder,
			Set<Tag> tagList) {
		super();
		this.mediaType = mediaType;
		this.mediaUrl = mediaUrl;
		this.title = title;
		this.description = description;
		this.duration = duration;
		this.createdOn = createdOn;
		this.publishStatus = publishStatus;
		this.thumbnailUrl = thumbnailUrl;
		this.AlbumOrder = albumOrder;
		this.userHistory = userHistory;
		this.commentList = commentList;
		this.channel = channel;
		this.album = album;
		this.playLists = playLists;
		this.PlaylistOrder = playlistOrder;
		this.tagList = tagList;
	}

	public String getPublishStatus() {
		return publishStatus;
	}

	public void setPublishStatus(String publishStatus) {
		this.publishStatus = publishStatus;
	}

	public String getThumbnailUrl() {
		return thumbnailUrl;
	}

	public void setThumbnailUrl(String thumbnailUrl) {
		this.thumbnailUrl = thumbnailUrl;
	}

	public Collection<UserHistory> getUserHistory() {
		return userHistory;
	}

	public void setUserHistory(Collection<UserHistory> userHistory) {
		this.userHistory = userHistory;
	}

	public Collection<Comments> getCommentList() {
		return commentList;
	}

	public void setCommentList(Collection<Comments> commentList) {
		this.commentList = commentList;
	}

	public Channel getChannel() {
		return channel;
	}

	public void setChannel(Channel channel) {
		this.channel = channel;
	}

	
	public Collection<Playlists> getPlayLists() {
		return playLists;
	}

	public void setPlayLists(Collection<Playlists> playLists) {
		this.playLists = playLists;
	}

	public Collection<Tag> getTagList() {
		return tagList;
	}

	public void setTagList(Set<Tag> tagList) {
		this.tagList = tagList;
	}
	
	public String getDescription() {
		return description;
	}

	public void setDescription(String desc) {
		this.description = desc;
	}

	public int getAlbumOrder() {
		return AlbumOrder;
	}

	public void setAlbumOrder(int albumOrder) {
		AlbumOrder = albumOrder;
	}

	public Album getAlbum() {
		return album;
	}

	public void setAlbum(Album album) {
		this.album = album;
	}

	public Collection<PlaylistOrder> getPlaylistOrder() {
		return PlaylistOrder;
	}

	public void setPlaylistOrder(Collection<PlaylistOrder> playlistOrder) {
		PlaylistOrder = playlistOrder;
	}

	public LocalDate getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(LocalDate createdOn) {
		this.createdOn = createdOn;
	}
	
	
	
}
