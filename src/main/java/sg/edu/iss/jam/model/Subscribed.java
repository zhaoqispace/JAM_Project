package sg.edu.iss.jam.model;
import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Subscribed {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long subscribedID;
	
	private LocalDateTime TimeSubscribed;
	
	private boolean subscribed;

	@ManyToOne
	private User artist;
	
	@ManyToOne
	private User subscriber;

	public Long getSubscribedID() {
		return subscribedID;
	}

	public void setSubscribedID(Long subscribedID) {
		this.subscribedID = subscribedID;
	}

	public LocalDateTime getTimeSubscribed() {
		return TimeSubscribed;
	}

	public void setTimeSubscribed(LocalDateTime timeSubscribed) {
		TimeSubscribed = timeSubscribed;
	}

	public boolean isSubscribed() {
		return subscribed;
	}

	public void setSubscribed(boolean subscribed) {
		this.subscribed = subscribed;
	}

	public User getArtist() {
		return artist;
	}

	public void setArtist(User artist) {
		this.artist = artist;
	}

	public User getSubscriber() {
		return subscriber;
	}

	public void setSubscriber(User subscriber) {
		this.subscriber = subscriber;
	}
}

