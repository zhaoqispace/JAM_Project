package sg.edu.iss.jam.model;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Comments {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String dateTime;
	
	private String userComment;
	
	//relation with media
	@ManyToOne
	private Media mediaComment;
	
	//relation with user
	@ManyToOne
	private User commentUser;

	public Comments() {
		super();
	}

	public Comments(String dateTime, String userComment, Media mediaComment, User commentUser) {
		super();
		this.dateTime = dateTime;
		this.userComment = userComment;
		this.mediaComment = mediaComment;
		this.commentUser = commentUser;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDateTime() {
		return dateTime;
	}

	public void setDateTime(String dateTime) {
		this.dateTime = dateTime;
	}

	public String getUserComment() {
		return userComment;
	}

	public void setUserComment(String userComment) {
		this.userComment = userComment;
	}

	public Media getMediaComment() {
		return mediaComment;
	}

	public void setMediaComment(Media mediaComment) {
		this.mediaComment = mediaComment;
	}

	public User getCommentUser() {
		return commentUser;
	}

	public void setCommentUser(User commentUser) {
		this.commentUser = commentUser;
	}
	
	

}
