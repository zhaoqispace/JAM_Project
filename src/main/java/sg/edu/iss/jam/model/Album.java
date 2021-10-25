package sg.edu.iss.jam.model;

import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class Album {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long AlbumID;
	
	private String AlbumDescription;
	
	private String AlbumImgURL;
	
	//relation to channel
	@ManyToOne
	private Channel channel;
	
	//relation to media
	@OneToMany(mappedBy = "album")
	private Collection<Media> AlbumMedia;

	
	public Album() {
		super();
	}

	public Album(String albumDescription, String albumImgURL, Channel channel, Collection<Media> albumMedia) {
		super();
		AlbumDescription = albumDescription;
		AlbumImgURL = albumImgURL;
		this.channel = channel;
		AlbumMedia = albumMedia;
	}

	public Long getAlbumID() {
		return AlbumID;
	}

	public void setAlbumID(Long albumID) {
		AlbumID = albumID;
	}

	public String getAlbumDescription() {
		return AlbumDescription;
	}

	public void setAlbumDescription(String albumDescription) {
		AlbumDescription = albumDescription;
	}

	public String getAlbumImgURL() {
		return AlbumImgURL;
	}

	public void setAlbumImgURL(String albumImgURL) {
		AlbumImgURL = albumImgURL;
	}

	public Channel getChannel() {
		return channel;
	}

	public void setChannel(Channel channel) {
		this.channel = channel;
	}

	public Collection<Media> getAlbumMedia() {
		return AlbumMedia;
	}

	public void setAlbumMedia(Collection<Media> albumMedia) {
		AlbumMedia = albumMedia;
	}
	
}
