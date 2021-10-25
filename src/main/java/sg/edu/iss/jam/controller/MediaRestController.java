package sg.edu.iss.jam.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import sg.edu.iss.jam.DTO.AndroidArtistVideoChannelDTO;
import sg.edu.iss.jam.DTO.AndroidMediaDTO;
import sg.edu.iss.jam.model.Channel;
import sg.edu.iss.jam.model.Media;
import sg.edu.iss.jam.model.MediaType;
import sg.edu.iss.jam.model.Subscribed;
import sg.edu.iss.jam.model.Tag;
import sg.edu.iss.jam.model.User;
import sg.edu.iss.jam.model.UserHistory;
import sg.edu.iss.jam.service.ArtistInterface;
import sg.edu.iss.jam.service.MediaServiceInterface;
import sg.edu.iss.jam.service.UserInterface;

@RestController
@RequestMapping("/api")
public class MediaRestController {

	@Autowired
	UserInterface uservice;

	@Autowired
	ArtistInterface aservice;

	// scy-videolandingpage
	@Autowired
	MediaServiceInterface mservice;

	// for recommendation
	@Autowired
	private RestTemplate restTemplate;

	String response_model1 = "";
	String response_model2 = "";
	String response_model3 = "";
	String response_model4 = "";
	List<String> recommendMediaNames_model1 = new ArrayList<String>();
	List<String> recommendMediaNames_model2 = new ArrayList<String>();
	List<String> recommendMediaNames_model3 = new ArrayList<String>();
	List<String> recommendMediaNames_model4 = new ArrayList<String>();

	@GetMapping("/video/getallrecommendedvideos")
	public ResponseEntity<?> loginVideoLandingPage(@RequestParam("userID") long userID) {

		List<AndroidMediaDTO> androidGetAllVideosDTOList = new ArrayList<>();

		// if the code comes here, it means the user exists in database,
		// then check whether it's new user or not
		boolean hasUserHistoryVideo = true;
		List<UserHistory> userHistoryVideo = uservice.findUserHistoryByUserIdAndMediaType(userID, MediaType.Video);

		if (userHistoryVideo == null || userHistoryVideo.size() == 0) {
			hasUserHistoryVideo = false;
		}

		// If the user has no video UserHistory data (new user),
		// recommend items the same as genericvideolandingpage
		if (hasUserHistoryVideo == false) {

			List<Media> allVideos = mservice.getMediaByUserHistory(MediaType.Video, LocalDate.now().minusMonths(36));
			List<Media> topVideos = new ArrayList<Media>();

			for (Media m : allVideos) {
				if (m.getCreatedOn().compareTo(LocalDate.now().minusDays(180)) > 0)
					topVideos.add(m);
			}

			for (Media video : topVideos) {
				String tags = "";
				AndroidMediaDTO mediaDTO = new AndroidMediaDTO();
				mediaDTO.setMediaId(video.getId());
				mediaDTO.setArtistId(video.getChannel().getChannelUser().getUserID());
				mediaDTO.setMediaThumbnailUrl(video.getThumbnailUrl());
				mediaDTO.setArtistProfileThumbnailUrl(video.getChannel().getChannelUser().getProfileUrl());
				mediaDTO.setArtistName(video.getChannel().getChannelUser().getFullname());
				mediaDTO.setMediaTitle(video.getTitle());
				mediaDTO.setMediaDuration(video.getDuration());
				mediaDTO.getCreatedOn(video.getCreatedOn().format(DateTimeFormatter.ofPattern("dd MMMM yyyy")));
				mediaDTO.getUserHistorySize(String.valueOf(video.getUserHistory().size()));
				mediaDTO.getUserHistorySize(String.valueOf(video.getUserHistory().size()));
				for (Tag tag : video.getTagList()) {
					tags = tags + tag.getTagName() + " ";
					tags.trim();
				}
				mediaDTO.setTags(tags);
				mediaDTO.setMediaUrl(video.getMediaUrl());
				mediaDTO.setSubscribed(subCheck(video.getChannel().getChannelUser().getUserID(), userID));
				androidGetAllVideosDTOList.add(mediaDTO);
			}
			return new ResponseEntity<>(androidGetAllVideosDTOList, HttpStatus.OK);
		}

		// Else if the user has UserHistory data,
		// recommend items based on ML model

		else {

			List<Long> recommend_mediaid_list = new ArrayList<Long>();
			List<Media> recommend_medialist = new ArrayList<Media>();

			String url = "http://127.0.0.1:5000/model1?user_id={1}";
			ResponseEntity<String> responseEntity = restTemplate.getForEntity(url, String.class, userID);
			response_model1 = responseEntity.getBody();

			if (!response_model1.isEmpty()) {

				List<String> strList = new ArrayList<String>();
				strList = Arrays.asList(response_model1.split(","));
				for (String s : strList) {
					recommend_mediaid_list.add(Long.parseLong(s));
				}
			}

			for (Long id : recommend_mediaid_list) {
				Media recommendMedia = mservice.getMediaById(id);
				if (recommendMedia != null) {
					recommend_medialist.add(recommendMedia);
				}
			}

			Collections.shuffle(recommend_medialist);

			for (Media video : recommend_medialist) {
				String tags = "";
				AndroidMediaDTO mediaDTO = new AndroidMediaDTO();
				mediaDTO.setMediaId(video.getId());
				mediaDTO.setArtistId(video.getChannel().getChannelUser().getUserID());
				mediaDTO.setMediaThumbnailUrl(video.getThumbnailUrl());
				mediaDTO.setArtistProfileThumbnailUrl(video.getChannel().getChannelUser().getProfileUrl());
				mediaDTO.setArtistName(video.getChannel().getChannelUser().getFullname());
				mediaDTO.setMediaTitle(video.getTitle());
				mediaDTO.setMediaDuration(video.getDuration());
				mediaDTO.getCreatedOn(video.getCreatedOn().format(DateTimeFormatter.ofPattern("dd MMMM yyyy")));
				mediaDTO.getUserHistorySize(String.valueOf(video.getUserHistory().size()));
				for (Tag tag : video.getTagList()) {
					tags = tags + tag.getTagName() + " ";
					tags.trim();
				}
				mediaDTO.setTags(tags);
				mediaDTO.setMediaUrl(video.getMediaUrl());
				mediaDTO.setSubscribed(subCheck(video.getChannel().getChannelUser().getUserID(), userID));
				androidGetAllVideosDTOList.add(mediaDTO);
			}

			List<Media> allVideos = mservice.findAllVideos();
			Collections.shuffle(allVideos);
			for (Media video : allVideos) {
				if (!recommend_medialist.contains(video)) {
					String tags = "";
					AndroidMediaDTO mediaDTO = new AndroidMediaDTO();
					mediaDTO.setMediaId(video.getId());
					mediaDTO.setArtistId(video.getChannel().getChannelUser().getUserID());
					mediaDTO.setMediaThumbnailUrl(video.getThumbnailUrl());
					mediaDTO.setArtistProfileThumbnailUrl(video.getChannel().getChannelUser().getProfileUrl());
					mediaDTO.setArtistName(video.getChannel().getChannelUser().getFullname());
					mediaDTO.setMediaTitle(video.getTitle());
					mediaDTO.setMediaDuration(video.getDuration());
					mediaDTO.getCreatedOn(video.getCreatedOn().format(DateTimeFormatter.ofPattern("dd MMMM yyyy")));
					mediaDTO.getUserHistorySize(String.valueOf(video.getUserHistory().size()));
					for (Tag tag : video.getTagList()) {
						tags = tags + tag.getTagName() + " ";
						tags.trim();
					}
					mediaDTO.setTags(tags);
					mediaDTO.setMediaUrl(video.getMediaUrl());
					mediaDTO.setSubscribed(subCheck(video.getChannel().getChannelUser().getUserID(), userID));
					androidGetAllVideosDTOList.add(mediaDTO);
				}
			}
			return new ResponseEntity<>(androidGetAllVideosDTOList, HttpStatus.OK);
		}
	}

	@GetMapping("/video/viewartistvideochannel")
	public ResponseEntity<?> viewArtistVideoChannel(@RequestParam("artistId") long artistId,
			@RequestParam("userId") long userId) {

		AndroidArtistVideoChannelDTO artistVideoChannel = new AndroidArtistVideoChannelDTO();
		String artistVideoChannelName = "";
		int numberOfArtistVideos = 0;
		int numberOfSubscribers = 0;
		List<Media> artistVideos = new ArrayList<Media>();
		List<AndroidMediaDTO> mediaList = new ArrayList<AndroidMediaDTO>();

		User artist = aservice.findById(artistId);

		// get artist's video channel and videos
		List<Channel> artistChannels = (List<Channel>) artist.getChannels();

		for (Channel c : artistChannels) {
			if (c.getMediaType() == MediaType.Video) {
				artistVideos = (List<Media>) c.getChannelMediaList();

				artistVideoChannelName = c.getChannelName();
				numberOfArtistVideos = c.getChannelMediaList().size();
			}
		}
		// convert to media dto
		for (Media video : artistVideos) {
			String tags = "";
			AndroidMediaDTO mediaDTO = new AndroidMediaDTO();
			mediaDTO.setMediaId(video.getId());
			mediaDTO.setArtistId(video.getChannel().getChannelUser().getUserID());
			mediaDTO.setMediaThumbnailUrl(video.getThumbnailUrl());
			mediaDTO.setArtistProfileThumbnailUrl(video.getChannel().getChannelUser().getProfileUrl());
			mediaDTO.setArtistName(video.getChannel().getChannelUser().getFullname());
			mediaDTO.setMediaTitle(video.getTitle());
			mediaDTO.setMediaDuration(video.getDuration());
			mediaDTO.getCreatedOn(video.getCreatedOn().format(DateTimeFormatter.ofPattern("dd MMMM yyyy")));
			mediaDTO.getUserHistorySize(String.valueOf(video.getUserHistory().size()));
			for (Tag tag : video.getTagList()) {
				tags = tags + tag.getTagName() + " ";
				tags.trim();
			}
			mediaDTO.setTags(tags);
			mediaDTO.setMediaUrl(video.getMediaUrl());
			mediaDTO.setSubscribed(subCheck(video.getChannel().getChannelUser().getUserID(), userId));

//			AndroidMediaDTO androidGetAllVideosDTO = new AndroidMediaDTO();
//			String tags = "";
//			androidGetAllVideosDTO.setArtistName(video.getChannel().getChannelUser().getFullname());
//			androidGetAllVideosDTO
//					.setArtistProfileThumbnailUrl(video.getChannel().getChannelUser().getProfileUrl());
//			androidGetAllVideosDTO.setMediaDuration(video.getDuration());
//			androidGetAllVideosDTO.setMediaUrl(video.getMediaUrl());
//			androidGetAllVideosDTO.setMediaThumbnailUrl(video.getThumbnailUrl());
//			androidGetAllVideosDTO.setMediaTitle(video.getTitle());
//			androidGetAllVideosDTO.setArtistId(video.getChannel().getChannelUser().getUserID());
//			for (Tag tag : video.getTagList()) {
//				tags = tags + tag.getTagName() + " ";
//				tags.trim();
//			}
//			androidGetAllVideosDTO.setTags(tags);
			// mediaList.add(androidGetAllVideosDTO);
			mediaList.add(mediaDTO);
		}
		// Subscribe/unsubscribe button section

		// Calcuate all subscribers of the artist

		Boolean subscribeStatus = false;
		String loggedInUserSubscribeErrorMsg = "";
		String totalNumberOfSubscribeErrorMsg = "";

		List<Subscribed> users_Unsubscribed = uservice.getArtistUnSubscribed(artistId);
		List<Subscribed> users_Subscribed = uservice.getArtistSubscribed(artistId);
		numberOfSubscribers = users_Subscribed.size() - users_Unsubscribed.size();
		if (numberOfSubscribers < 0) {
			totalNumberOfSubscribeErrorMsg = "The number of subscribers should not be less than 0, please check the database";
		}

		// check whether the current loggedIn user has subscribed the artist
		List<Subscribed> unsubscribed_loggedInUser = uservice.getArtistUnsubscribedByLoggInUserId(artistId, userId);
		List<Subscribed> subscribed_loggedInUser = uservice.getArtistSubscribedByLoggInUserId(artistId, userId);

		if (subscribed_loggedInUser.size() < unsubscribed_loggedInUser.size()
				|| (subscribed_loggedInUser == null && unsubscribed_loggedInUser != null)) {
			loggedInUserSubscribeErrorMsg = "The number of subscriptions true should not be less than the number of subscriptions false";
		}

		if (subscribed_loggedInUser.size() - unsubscribed_loggedInUser.size() > 1) {
			loggedInUserSubscribeErrorMsg = "The number of subscriptions true should only be 1 count bigger than the number of subscriptions false ";
		}

		if ((unsubscribed_loggedInUser == null && subscribed_loggedInUser == null)
				|| (subscribed_loggedInUser.size() == unsubscribed_loggedInUser.size())) {

			subscribeStatus = false;
		}

		if (subscribed_loggedInUser.size() - unsubscribed_loggedInUser.size() == 1) {
			subscribeStatus = true;
		}

		if (artistId == userId) {
			subscribeStatus = null;
		}

		artistVideoChannel.setArtistId(artistId);
		artistVideoChannel.setArtistProfileUrl(artist.getProfileUrl());
		artistVideoChannel.setArtistVideoChannelName(artistVideoChannelName);
		artistVideoChannel.setArtistVideos(mediaList);
		artistVideoChannel.setNumberOfArtistVideos(numberOfArtistVideos);
		artistVideoChannel.setNumberOfSubscribers(numberOfSubscribers);
		artistVideoChannel.setSubscribeStatus(subscribeStatus);

		return new ResponseEntity<>(artistVideoChannel, HttpStatus.OK);
	}

	@GetMapping("/music/getallrecommendedmusics")
	public ResponseEntity<?> loginMusicLandingPage(@RequestParam("userID") long userID) {

		List<AndroidMediaDTO> androidGetAllMusicDTOList = new ArrayList<>();

		// if the code comes here, it means the user exists in database,
		// then check whether it's new user or not
		boolean hasUserHistoryMusic = true;
		List<UserHistory> userHistoryMusic = uservice.findUserHistoryByUserIdAndMediaType(userID, MediaType.Music);
		if (userHistoryMusic == null || userHistoryMusic.size() == 0) {
			hasUserHistoryMusic = false;
		}

		// If the user has no music UserHistory data (new user),
		// recommend items the same as genericmusiclandingpage
		if (hasUserHistoryMusic == false) {

			List<Media> allMusics = mservice.getMediaByUserHistory(MediaType.Music, LocalDate.now().minusMonths(36));
			List<Media> topMusics = new ArrayList<Media>();

			for (Media m : allMusics) {
				if (m.getCreatedOn().compareTo(LocalDate.now().minusDays(180)) > 0)
					topMusics.add(m);
			}

			for (Media music : topMusics) {
				String tags = "";
				AndroidMediaDTO mediaDTO = new AndroidMediaDTO();
				mediaDTO.setMediaId(music.getId());
				mediaDTO.setArtistId(music.getChannel().getChannelUser().getUserID());
				mediaDTO.setMediaThumbnailUrl(music.getThumbnailUrl());
				mediaDTO.setArtistProfileThumbnailUrl(music.getChannel().getChannelUser().getProfileUrl());
				mediaDTO.setArtistName(music.getChannel().getChannelUser().getFullname());
				mediaDTO.setMediaTitle(music.getTitle());
				mediaDTO.setMediaDuration(music.getDuration());
				mediaDTO.getCreatedOn(music.getCreatedOn().format(DateTimeFormatter.ofPattern("dd MMMM yyyy")));
				mediaDTO.getUserHistorySize(String.valueOf(music.getUserHistory().size()));
				for (Tag tag : music.getTagList()) {
					tags = tags + tag.getTagName() + " ";
					tags.trim();
				}
				mediaDTO.setTags(tags);
				mediaDTO.setMediaUrl(music.getMediaUrl());
				mediaDTO.setSubscribed(subCheck(music.getChannel().getChannelUser().getUserID(), userID));
				mediaDTO.setAlbumId(music.getAlbum().getAlbumID());
				mediaDTO.setAlbumName(music.getAlbum().getAlbumDescription());
				androidGetAllMusicDTOList.add(mediaDTO);
			}
			return new ResponseEntity<>(androidGetAllMusicDTOList, HttpStatus.OK);
		}

		// Else if the user has UserHistory data,
		// recommend items based on ML model
		else {
			List<Long> recommend_mediaid_list = new ArrayList<Long>();
			List<Media> recommend_medialist = new ArrayList<Media>();

			String url = "http://127.0.0.1:5000/model2?user_id={1}";
			ResponseEntity<String> responseEntity = restTemplate.getForEntity(url, String.class, userID);
			response_model2 = responseEntity.getBody();

			if (!response_model2.isEmpty()) {

				List<String> strList = new ArrayList<String>();
				strList = Arrays.asList(response_model2.split(","));
				for (String s : strList) {
					recommend_mediaid_list.add(Long.parseLong(s));
				}
			}

			for (Long id : recommend_mediaid_list) {
				Media recommendMedia = mservice.getMediaById(id);
				if (recommendMedia != null) {
					recommend_medialist.add(recommendMedia);
				}
			}
			Collections.shuffle(recommend_medialist);

			for (Media music : recommend_medialist) {
				String tags = "";
				AndroidMediaDTO mediaDTO = new AndroidMediaDTO();
				mediaDTO.setMediaId(music.getId());
				mediaDTO.setArtistId(music.getChannel().getChannelUser().getUserID());
				mediaDTO.setMediaThumbnailUrl(music.getThumbnailUrl());
				mediaDTO.setArtistProfileThumbnailUrl(music.getChannel().getChannelUser().getProfileUrl());
				mediaDTO.setArtistName(music.getChannel().getChannelUser().getFullname());
				mediaDTO.setMediaTitle(music.getTitle());
				mediaDTO.setMediaDuration(music.getDuration());
				mediaDTO.getCreatedOn(music.getCreatedOn().format(DateTimeFormatter.ofPattern("dd MMMM yyyy")));
				mediaDTO.getUserHistorySize(String.valueOf(music.getUserHistory().size()));
				for (Tag tag : music.getTagList()) {
					tags = tags + tag.getTagName() + " ";
					tags.trim();
				}
				mediaDTO.setTags(tags);
				mediaDTO.setMediaUrl(music.getMediaUrl());
				mediaDTO.setSubscribed(subCheck(music.getChannel().getChannelUser().getUserID(), userID));
				mediaDTO.setAlbumId(music.getAlbum().getAlbumID());
				mediaDTO.setAlbumName(music.getAlbum().getAlbumDescription());
				androidGetAllMusicDTOList.add(mediaDTO);
			}

			List<Media> allMusics = mservice.findAllMusics();
			Collections.shuffle(allMusics);
			for (Media music : allMusics) {
				if (!recommend_medialist.contains(music)) {
					String tags = "";
					AndroidMediaDTO mediaDTO = new AndroidMediaDTO();
					mediaDTO.setMediaId(music.getId());
					mediaDTO.setArtistId(music.getChannel().getChannelUser().getUserID());
					mediaDTO.setMediaThumbnailUrl(music.getThumbnailUrl());
					mediaDTO.setArtistProfileThumbnailUrl(music.getChannel().getChannelUser().getProfileUrl());
					mediaDTO.setArtistName(music.getChannel().getChannelUser().getFullname());
					mediaDTO.setMediaTitle(music.getTitle());
					mediaDTO.setMediaDuration(music.getDuration());
					mediaDTO.getCreatedOn(music.getCreatedOn().format(DateTimeFormatter.ofPattern("dd MMMM yyyy")));
					mediaDTO.getUserHistorySize(String.valueOf(music.getUserHistory().size()));
					for (Tag tag : music.getTagList()) {
						tags = tags + tag.getTagName() + " ";
						tags.trim();
					}
					mediaDTO.setTags(tags);
					mediaDTO.setMediaUrl(music.getMediaUrl());
					mediaDTO.setSubscribed(subCheck(music.getChannel().getChannelUser().getUserID(), userID));
					mediaDTO.setAlbumId(music.getAlbum().getAlbumID());
					mediaDTO.setAlbumName(music.getAlbum().getAlbumDescription());
					androidGetAllMusicDTOList.add(mediaDTO);
				}
			}
			return new ResponseEntity<>(androidGetAllMusicDTOList, HttpStatus.OK);
		}
	}

	@GetMapping("/music/getalbummusic")
	public ResponseEntity<?> getAlbumMusic(@RequestParam("albumID") long albumID) {
		List<Media> musicList = mservice.findMediaByAlbumId(albumID);

		List<AndroidMediaDTO> androidGetAllMusicDTOList = new ArrayList<>();

		for (Media music : musicList) {
			String tags = "";
			AndroidMediaDTO mediaDTO = new AndroidMediaDTO();
			mediaDTO.setMediaId(music.getId());
			mediaDTO.setArtistId(music.getChannel().getChannelUser().getUserID());
			mediaDTO.setMediaThumbnailUrl(music.getThumbnailUrl());
			mediaDTO.setArtistProfileThumbnailUrl(music.getChannel().getChannelUser().getProfileUrl());
			mediaDTO.setArtistName(music.getChannel().getChannelUser().getFullname());
			mediaDTO.setMediaTitle(music.getTitle());
			mediaDTO.setMediaDuration(music.getDuration());
			mediaDTO.getCreatedOn(music.getCreatedOn().format(DateTimeFormatter.ofPattern("dd MMMM yyyy")));
			mediaDTO.getUserHistorySize(String.valueOf(music.getUserHistory().size()));
			for (Tag tag : music.getTagList()) {
				tags = tags + tag.getTagName() + " ";
				tags.trim();
			}
			mediaDTO.setTags(tags);
			mediaDTO.setMediaUrl(music.getMediaUrl());
			mediaDTO.setAlbumId(music.getAlbum().getAlbumID());
			mediaDTO.setAlbumName(music.getAlbum().getAlbumDescription());
			androidGetAllMusicDTOList.add(mediaDTO);

		}
		return new ResponseEntity<>(androidGetAllMusicDTOList, HttpStatus.OK);

	}

	@PostMapping("/addUserHistory")
	public ResponseEntity<?> addUserHistory(@RequestParam("mediaID") long mediaID,
			@RequestParam("userID") long userID) {
		UserHistory userhistory = new UserHistory(LocalDateTime.now(), uservice.findUserByUserId(userID),
				mservice.getMediaById(mediaID));
		mservice.saveUserHistory(userhistory);
		String count = String.valueOf(mservice.getMediaById(mediaID).getUserHistory().size());
		return new ResponseEntity<>(count, HttpStatus.OK);
	}

	@GetMapping("/subscribebutton")
	public ResponseEntity<?> subscribe(@RequestParam("artistID") long artistID, @RequestParam("userID") long userID) {

		Boolean subscribeStatus = null;

		User customer = uservice.findUserByUserId(userID);
		User artist = aservice.findById(artistID);
		List<Subscribed> unsubscribed_loggedInUser = uservice.getArtistUnsubscribedByLoggInUserId(artistID, userID);
		List<Subscribed> subscribed_loggedInUser = uservice.getArtistSubscribedByLoggInUserId(artistID, userID);

		if ((unsubscribed_loggedInUser == null && subscribed_loggedInUser == null)
				|| (subscribed_loggedInUser.size() == unsubscribed_loggedInUser.size())) {

			Subscribed newSubscription = new Subscribed();
			newSubscription.setSubscribed(true);
			newSubscription.setArtist(artist);
			newSubscription.setSubscriber(customer);
			newSubscription.setTimeSubscribed(LocalDateTime.now());

			uservice.saveSubscribed(newSubscription);

			subscribeStatus = true;
		}

		if (subscribed_loggedInUser.size() - unsubscribed_loggedInUser.size() == 1) {

			Subscribed newUnsubscription = new Subscribed();
			newUnsubscription.setSubscribed(false);
			newUnsubscription.setArtist(artist);
			newUnsubscription.setSubscriber(customer);
			newUnsubscription.setTimeSubscribed(LocalDateTime.now());

			uservice.saveSubscribed(newUnsubscription);

			subscribeStatus = false;
		}

		return new ResponseEntity<>(subscribeStatus, HttpStatus.OK);

	}

	public Boolean subCheck(Long artistID, Long userID) {
		Boolean subscribeStatus = null;

		List<Subscribed> unsubscribed_loggedInUser = uservice.getArtistUnsubscribedByLoggInUserId(artistID, userID);
		List<Subscribed> subscribed_loggedInUser = uservice.getArtistSubscribedByLoggInUserId(artistID, userID);

		if ((unsubscribed_loggedInUser == null && subscribed_loggedInUser == null)
				|| (subscribed_loggedInUser.size() == unsubscribed_loggedInUser.size())) {

			subscribeStatus = true;
		}

		else if (subscribed_loggedInUser.size() - unsubscribed_loggedInUser.size() == 1) {

			subscribeStatus = false;
		}
		return subscribeStatus;
	}
}

//	@GetMapping("/video/getallvideos")
//	public ResponseEntity<?> getAllVideos() {
//		List<Media> allVideos = mservice.findAllVideos();
//		Collections.shuffle(allVideos);
//		List<AndroidGetAllVideosDTO> androidGetAllVideosDTOList = new  ArrayList<>();
//		for (Media video : allVideos) {
//			String tags = "";
//			AndroidGetAllVideosDTO androidGetAllVideosDTO = new AndroidGetAllVideosDTO();
//			androidGetAllVideosDTO.setArtistName(video.getChannel().getChannelUser().getFullname());
//			androidGetAllVideosDTO.setArtistProfileThumbnailUrl(video.getChannel().getChannelUser().getProfileUrl());
//			androidGetAllVideosDTO.setVideoDuration(video.getDuration());
//			androidGetAllVideosDTO.setVideoThumbnailUrl(video.getThumbnailUrl());
//			androidGetAllVideosDTO.setVideoTitle(video.getTitle());
//			for (Tag tag: video.getTagList()) {
//				tags = tags + tag.getTagName() + " ";
//				tags.trim();
//			}
//			androidGetAllVideosDTO.setTags(tags);
//			androidGetAllVideosDTOList.add(androidGetAllVideosDTO);
//		}
//		return new ResponseEntity<>(androidGetAllVideosDTOList, HttpStatus.OK);
//	}

//	@GetMapping("/video/getallvideos")
//	public ResponseEntity<?> getAllVideos() {
//		List<Media> allVideos = mservice.findAllVideos();
//		return new ResponseEntity<>(allVideos, HttpStatus.OK);
//	}
