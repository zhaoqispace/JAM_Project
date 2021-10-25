package sg.edu.iss.jam.DTO;

import java.util.List;

public class AndroidArtistVideoChannelDTO {

	private Long artistId;
	private int numberOfArtistVideos;
	private String artistVideoChannelName;
	private List<AndroidMediaDTO> artistVideos;
	private int numberOfSubscribers;
	private Boolean subscribeStatus;
	private String artistProfileUrl;

	public AndroidArtistVideoChannelDTO() {
		super();
	}

	public Long getArtistId() {
		return artistId;
	}

	public void setArtistId(Long artistId) {
		this.artistId = artistId;
	}

	public int getNumberOfArtistVideos() {
		return numberOfArtistVideos;
	}

	public void setNumberOfArtistVideos(int numberOfArtistVideos) {
		this.numberOfArtistVideos = numberOfArtistVideos;
	}

	public String getArtistVideoChannelName() {
		return artistVideoChannelName;
	}

	public void setArtistVideoChannelName(String artistVideoChannelName) {
		this.artistVideoChannelName = artistVideoChannelName;
	}

	public List<AndroidMediaDTO> getArtistVideos() {
		return artistVideos;
	}

	public void setArtistVideos(List<AndroidMediaDTO> artistVideos) {
		this.artistVideos = artistVideos;
	}

	public int getNumberOfSubscribers() {
		return numberOfSubscribers;
	}

	public void setNumberOfSubscribers(int numberOfSubscribers) {
		this.numberOfSubscribers = numberOfSubscribers;
	}

	public Boolean getSubscribeStatus() {
		return subscribeStatus;
	}

	public void setSubscribeStatus(Boolean subscribeStatus) {
		this.subscribeStatus = subscribeStatus;
	}

	public String getArtistProfileUrl() {
		return artistProfileUrl;
	}

	public void setArtistProfileUrl(String artistProfileUrl) {
		this.artistProfileUrl = artistProfileUrl;
	}

}
