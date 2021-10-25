package sg.edu.iss.jam.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Sessions {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long sessionID;
	
	//relation with user
	@ManyToOne
	private User sessionUser;

	public Sessions() {
		super();
	}

	public Sessions(User sessionUser) {
		super();
		this.sessionUser = sessionUser;
	}

	public Long getSessionID() {
		return sessionID;
	}

	public void setSessionID(Long sessionID) {
		this.sessionID = sessionID;
	}

	public User getSessionUser() {
		return sessionUser;
	}

	public void setSessionUser(User sessionUser) {
		this.sessionUser = sessionUser;
	}
	
	
}
