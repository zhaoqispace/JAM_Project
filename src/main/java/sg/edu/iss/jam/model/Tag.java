package sg.edu.iss.jam.model;

import java.util.Collection;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

@Entity
public class Tag {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long tagID;
	
	private String tagName;
	
	//relation with media
	@ManyToMany(mappedBy = "tagList")
	private Set<Media> mediaTagList;

	public Tag() {
		super();
	}	

	public Tag(String tagName, Set<Media> mediaTagList) {
		super();
		this.tagName = tagName;
		this.mediaTagList = mediaTagList;
	}

	public Long getTagID() {
		return tagID;
	}

	public void setTagID(Long tagID) {
		this.tagID = tagID;
	}

	public String getTagName() {
		return tagName;
	}

	public void setTagName(String tagName) {
		this.tagName = tagName;
	}

	public Collection<Media> getMediaTagList() {
		return mediaTagList;
	}

	public void setMediaTagList(Set<Media> mediaTagList) {
		this.mediaTagList = mediaTagList;
	}
	
	

}
