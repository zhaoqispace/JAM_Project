package sg.edu.iss.jam.DTO;

import sg.edu.iss.jam.model.Channel;

public class ChannelDTO {

	Channel channel;

	int VideoCount;

	int MusicCount;

	public ChannelDTO(Channel channel, int videoCount, int musicCount) {
		super();
		this.channel = channel;
		VideoCount = videoCount;
		MusicCount = musicCount;
	}

	public ChannelDTO(Channel channel) {
		this.channel = channel;

	}

	public Channel getChannel() {
		return channel;
	}

	public void setChannel(Channel channel) {
		this.channel = channel;
	}

	public int getVideoCount() {
		return VideoCount;
	}

	public void setVideoCount(int videoCount) {
		VideoCount = videoCount;
	}

	public int getMusicCount() {
		return MusicCount;
	}

	public void setMusicCount(int musicCount) {
		MusicCount = musicCount;
	}

}
