package sg.edu.iss.jam.controller;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import sg.edu.iss.jam.model.Category;
import sg.edu.iss.jam.model.Channel;
import sg.edu.iss.jam.model.Media;
import sg.edu.iss.jam.model.MediaType;
import sg.edu.iss.jam.model.OrderDetails;
import sg.edu.iss.jam.model.Product;
import sg.edu.iss.jam.model.Subscribed;
import sg.edu.iss.jam.model.User;
import sg.edu.iss.jam.model.UserHistory;
import sg.edu.iss.jam.security.MyUserDetails;
import sg.edu.iss.jam.service.ArtistInterface;
import sg.edu.iss.jam.service.MediaServiceInterface;
import sg.edu.iss.jam.service.UserInterface;

@Controller
public class DashboardController {

	// dashboard for artist
	@Autowired
	ArtistInterface aservice;
	@Autowired
	UserInterface uservice;
	@Autowired
	MediaServiceInterface mservice;

	@GetMapping("/dashboard")
	public String showDashboard(Model model, @AuthenticationPrincipal MyUserDetails myUserDetails) {

		// video and music chart
		User artist = uservice.findUserByUserId(myUserDetails.getUserId());

		List<Media> artistVideos = new ArrayList<Media>();
		List<UserHistory> videoUserHistories = new ArrayList<UserHistory>();
		List<String> videotitle = new ArrayList<String>();
		List<Integer> viewVideoCounts = new ArrayList<Integer>();
		int videosnumber = 0;
		int totalVideosCounts = 0;

		List<Media> artistMusics = new ArrayList<Media>();
		List<UserHistory> musicUserHistories = new ArrayList<UserHistory>();
		List<String> musicTitle = new ArrayList<String>();
		List<Integer> listenMusicCounts = new ArrayList<Integer>();
		int musicsnumber = 0;
		int totalMusicsCounts = 0;

		List<Channel> artistChannels = (List<Channel>) artist.getChannels();

		for (Channel c : artistChannels) {
			if (c.getMediaType() == MediaType.Video) {
				artistVideos = (List<Media>) c.getChannelMediaList();
				videosnumber = (int) artistVideos.size();
				for (Media v : artistVideos) {
					videotitle.add(v.getTitle());
					videoUserHistories = uservice.findUserHistoryByMediaId(v.getId());
					viewVideoCounts.add(videoUserHistories.size());
				}
			}
		}
		for (int i = 0; i < viewVideoCounts.size(); i++) {
			totalVideosCounts = totalVideosCounts + viewVideoCounts.get(i);
		}

		for (Channel c : artistChannels) {
			if (c.getMediaType() == MediaType.Music) {
				artistMusics = (List<Media>) c.getChannelMediaList();
				musicsnumber = (int) artistMusics.size();
				for (Media v : artistMusics) {
					musicTitle.add(v.getTitle());
					musicUserHistories = uservice.findUserHistoryByMediaId(v.getId());
					listenMusicCounts.add(musicUserHistories.size());
				}
			}
		}

		for (int i = 0; i < listenMusicCounts.size(); i++) {
			totalMusicsCounts = totalMusicsCounts + listenMusicCounts.get(i);
		}

		// album_counts
		// find all view counts of videos --not use now
		int allviewcounts_videos = 0;
		for (int i = 0; i < viewVideoCounts.size(); i++) {
			allviewcounts_videos = allviewcounts_videos + viewVideoCounts.get(i);
		}

		// find all view counts of music

		model.addAttribute("videos", videotitle);
		model.addAttribute("videocounts", viewVideoCounts);
		model.addAttribute("videosnumber", videosnumber);
		model.addAttribute("totalvideoscount", totalVideosCounts);

		model.addAttribute("musics", musicTitle);
		model.addAttribute("musiccounts", listenMusicCounts);
		model.addAttribute("musicsnumber", musicsnumber);
		model.addAttribute("totalmusicscount", totalMusicsCounts);

		// Line chart with time series:

		// Find all userhistory
		List<UserHistory> allUserHistories = uservice.findAllUserHistory();
		int monthNow = LocalDateTime.now().getMonthValue();
		// I: video-find all video userhistory :
		List<Long> artistallVideoId = new ArrayList<Long>();
		Long videoID;
		for (Channel c : artistChannels) {
			if (c.getMediaType() == MediaType.Video) {
				artistVideos = (List<Media>) c.getChannelMediaList();
				for (int i = 0; i < artistVideos.size(); i++) {
					videoID = artistVideos.get(i).getId();
					artistallVideoId.add(videoID);
				}
			}
		}

		// Time in this year
		List<UserHistory> allVideoUserHistories = new ArrayList<UserHistory>();
		for (UserHistory uh : allUserHistories) {
			if (artistallVideoId.contains(uh.getMediaHistory().getId())
					&& uh.getDatetime().getYear() == LocalDateTime.now().getYear()) {
				allVideoUserHistories.add(uh);
			}
		}

		// Count video in a month interval during this year:
		List<Integer> videoCountInMonth = new ArrayList<Integer>();
		for (int i = 0; i < monthNow; i++) {
			videoCountInMonth.add(i, 0);
		}

		for (int i = 0; i < monthNow; i++) {
			int count = 0;
			for (UserHistory uh : allVideoUserHistories) {
				if (uh.getDatetime().getMonthValue() == i + 1) {
					count++;
					videoCountInMonth.set(i, count);
				}
			}
		}

		// Time in last year
		List<UserHistory> allVideoUserHistoriesLastyear = new ArrayList<UserHistory>();
		for (UserHistory uh : allUserHistories) {
			if (artistallVideoId.contains(uh.getMediaHistory().getId())
					&& uh.getDatetime().getYear() == LocalDateTime.now().getYear() - 1) {
				allVideoUserHistoriesLastyear.add(uh);
			}
		}

		// Count video in a month interval during last year:
		List<Integer> videoCountInMonthLastYear = new ArrayList<Integer>();
		for (int i = 0; i < 12; i++) {
			videoCountInMonthLastYear.add(i, 0);
		}

		for (int i = 0; i < 12; i++) {
			int count = 0;
			for (UserHistory uh : allVideoUserHistoriesLastyear) {
				if (uh.getDatetime().getMonthValue() == i + 1) {
					count++;
					videoCountInMonthLastYear.set(i, count);
				}
			}
		}

		// II: music-find all music userhistory:
		List<Long> artistallMusicId = new ArrayList<Long>();
		Long musicID;
		for (Channel c : artistChannels) {
			if (c.getMediaType() == MediaType.Music) {
				artistMusics = (List<Media>) c.getChannelMediaList();
				for (int i = 0; i < artistMusics.size(); i++) {
					musicID = artistMusics.get(i).getId();
					artistallMusicId.add(musicID);
				}
			}
		}
		// Time in this year
		List<UserHistory> allMusicUserHistories = new ArrayList<UserHistory>();
		for (UserHistory uh : allUserHistories) {
			if (artistallMusicId.contains(uh.getMediaHistory().getId())
					&& uh.getDatetime().getYear() == LocalDateTime.now().getYear()) {
				allMusicUserHistories.add(uh);
			}
		}

		// Count music in a month interval during this year:
		List<Integer> musicCountInMonth = new ArrayList<Integer>();
		for (int i = 0; i < monthNow; i++) {
			musicCountInMonth.add(i, 0);
		}

		for (int i = 0; i < monthNow; i++) {
			int count = 0;
			for (UserHistory uh : allMusicUserHistories) {
				if (uh.getDatetime().getMonthValue() == i + 1) {
					count++;
					musicCountInMonth.set(i, count);
				}
			}
		}

		// Time in last year
		List<UserHistory> allMusicUserHistoriesLastyear = new ArrayList<UserHistory>();
		for (UserHistory uh : allUserHistories) {
			if (artistallMusicId.contains(uh.getMediaHistory().getId())
					&& uh.getDatetime().getYear() == LocalDateTime.now().getYear() - 1) {
				allMusicUserHistoriesLastyear.add(uh);
			}
		}

		// Count music in a month interval during last year:
		List<Integer> musicCountInMonthLastyear = new ArrayList<Integer>();
		for (int i = 0; i < 12; i++) {
			musicCountInMonthLastyear.add(i, 0);
		}

		for (int i = 0; i < 12; i++) {
			int count = 0;
			for (UserHistory uh : allMusicUserHistoriesLastyear) {
				if (uh.getDatetime().getMonthValue() == i + 1) {
					count++;
					musicCountInMonthLastyear.set(i, count);
				}
			}
		}

		model.addAttribute("videodata", videoCountInMonth);
		model.addAttribute("videodatalastyear", videoCountInMonthLastYear);
		model.addAttribute("musicdata", musicCountInMonth);
		model.addAttribute("musicdatalastyear", musicCountInMonthLastyear);

		// Line chart with Subscribe
		// get artist's subscribers
		List<Subscribed> users_Subscribed_artist = uservice.getArtistSubscribed(myUserDetails.getUserId());
		List<Subscribed> users_Unsubscribed_artist = uservice.getArtistUnSubscribed(myUserDetails.getUserId());
		List<Subscribed> users_Subscribed_artist_1year = new ArrayList<Subscribed>();
		List<Subscribed> users_Unsubscribed_artist_1year = new ArrayList<Subscribed>();
		for (Subscribed s : users_Subscribed_artist) {
			if (s.getTimeSubscribed().getYear() == LocalDateTime.now().getYear()) {
				users_Subscribed_artist_1year.add(s);
			}
		}

		// get this year data
		// if use two
		// year:s.getTimeSubscribed().getYear()>=LocalDateTime.now().getYear()-1
		for (Subscribed s : users_Unsubscribed_artist) {
			if (s.getTimeSubscribed().getYear() == LocalDateTime.now().getYear()) {
				users_Unsubscribed_artist_1year.add(s);
			}
		}

		// get total subscriber before this year :
		List<Subscribed> users_Subscribed_artistr_beforethisyear = new ArrayList<Subscribed>();
		List<Subscribed> users_Unsubscribed_artist__beforethisyear = new ArrayList<Subscribed>();
		int countSubscribed_beforethisyear = 0;
		int countUnsubscribed_beforethisyear = 0;
		int totalSubscribed_beforethisyear = 0;

		for (Subscribed s : users_Subscribed_artist) {
			if (s.getTimeSubscribed().getYear() < LocalDateTime.now().getYear()) {
				users_Subscribed_artistr_beforethisyear.add(s);
				countSubscribed_beforethisyear = users_Subscribed_artistr_beforethisyear.size();
			}
		}
		for (Subscribed s : users_Unsubscribed_artist) {
			if (s.getTimeSubscribed().getYear() < LocalDateTime.now().getYear()) {
				users_Unsubscribed_artist__beforethisyear.add(s);
				countUnsubscribed_beforethisyear = users_Unsubscribed_artist__beforethisyear.size();
			}
		}

		totalSubscribed_beforethisyear = countSubscribed_beforethisyear - countUnsubscribed_beforethisyear;

		// get this year total subscriber and change
		List<Integer> subscriberCountInMonth = new ArrayList<Integer>();
		List<Integer> unsubscriberCountInMonth = new ArrayList<Integer>();
		List<Integer> subscribeChange = new ArrayList<Integer>();
		List<Integer> totalSubscribe = new ArrayList<Integer>();

		for (int i = 0; i < monthNow; i++) {
			subscriberCountInMonth.add(i, 0);
			unsubscriberCountInMonth.add(i, 0);
			subscribeChange.add(i, 0);
		}

		for (int i = 0; i < monthNow; i++) {
			int count = 0;
			for (Subscribed s : users_Subscribed_artist_1year) {
				if (s.getTimeSubscribed().getMonthValue() == i + 1) {
					count++;
					subscriberCountInMonth.set(i, count);
				}
			}
		}

		for (int i = 0; i < monthNow; i++) {
			int count = 0;
			for (Subscribed s : users_Unsubscribed_artist_1year) {
				if (s.getTimeSubscribed().getMonthValue() == i + 1) {
					count++;
					unsubscriberCountInMonth.set(i, count);
				}
			}
		}

		for (int i = 0; i < subscriberCountInMonth.size(); i++) {
			int number = subscriberCountInMonth.get(i) - unsubscriberCountInMonth.get(i);
			subscribeChange.set(i, number);
		}

		int firstMonthTotal = totalSubscribed_beforethisyear + subscribeChange.get(0);
		totalSubscribe.add(0, firstMonthTotal);
		for (int i = 1; i < subscribeChange.size(); i++) {
			int plus = totalSubscribe.get(i - 1) + subscribeChange.get(i);
			totalSubscribe.add(i, plus);
		}

		// time interval with month during this year:
		List<LocalDateTime> timePoints = new ArrayList<LocalDateTime>();
		timePoints.add(LocalDateTime.now());
		for (int i = 1; i < monthNow; i++) {
			timePoints.add(LocalDateTime.now().minusMonths(i));
		}

		List<String> stringTimePoint = new ArrayList<String>();
		for (int i = monthNow - 1; i >= 0; i--) {
			stringTimePoint.add(timePoints.get(i).toString().substring(0, 7));
		}

		model.addAttribute("changes", subscribeChange);
		model.addAttribute("total", totalSubscribe);
		model.addAttribute("datetime", stringTimePoint);

		// products chart

		List<Product> clothing = aservice.getProductListByArtistIDAndCategory(myUserDetails.getUserId(),
				Category.Clothing);
		List<Product> merchandise = aservice.getProductListByArtistIDAndCategory(myUserDetails.getUserId(),
				Category.Merchandise);
		List<Product> musiccollection = aservice.getProductListByArtistIDAndCategory(myUserDetails.getUserId(),
				Category.MusicCollection);

		// count of total product
		int totalproduct = clothing.size() + merchandise.size() + musiccollection.size();

		// find artist different product sold quantity in this year
		// -clothing
		List<OrderDetails> clothing_orderdetails = new ArrayList<OrderDetails>();
		List<Integer> clothingSoldWithMonth = new ArrayList<Integer>();
		for (int i = 1; i <= monthNow; i++) {
			int sum = 0;
			for (Product p : clothing) {
				clothing_orderdetails = (List<OrderDetails>) p.getOrderDetails();
				for (OrderDetails od : clothing_orderdetails) {
					if (od.getOrder().getOrderDate().getMonthValue() == i
							&& od.getOrder().getOrderDate().getYear() == LocalDateTime.now().getYear()) {
						sum = sum + od.getQuantity();
					}
				}
			}
			clothingSoldWithMonth.add(i - 1, sum);
		}

		// -merchandise
		List<OrderDetails> merchandise_orderdetails = new ArrayList<OrderDetails>();
		List<Integer> merchandiseSoldWithMonth = new ArrayList<Integer>();
		for (int i = 1; i <= monthNow; i++) {
			int sum = 0;
			for (Product p : merchandise) {
				merchandise_orderdetails = (List<OrderDetails>) p.getOrderDetails();
				for (OrderDetails od : merchandise_orderdetails) {
					if (od.getOrder().getOrderDate().getMonthValue() == i
							&& od.getOrder().getOrderDate().getYear() == LocalDateTime.now().getYear()) {
						sum = sum + od.getQuantity();
					}
				}
			}
			merchandiseSoldWithMonth.add(i - 1, sum);
		}

		// -musiccollection
		List<OrderDetails> music_orderdetails = new ArrayList<OrderDetails>();
		List<Integer> musicSoldWithMonth = new ArrayList<Integer>();
		for (int i = 1; i <= monthNow; i++) {
			int sum = 0;
			for (Product p : musiccollection) {
				music_orderdetails = (List<OrderDetails>) p.getOrderDetails();
				for (OrderDetails od : music_orderdetails) {
					if (od.getOrder().getOrderDate().getMonthValue() == i
							&& od.getOrder().getOrderDate().getYear() == LocalDateTime.now().getYear()) {
						sum = sum + od.getQuantity();
					}
				}
			}
			musicSoldWithMonth.add(i - 1, sum);
		}

		// find total quantity for each category
		int clothingTotalSold = 0;
		int merchandiseTotalSold = 0;
		int musicTotalSold = 0;
		for (int i = 0; i < clothingSoldWithMonth.size(); i++) {
			clothingTotalSold = clothingTotalSold + clothingSoldWithMonth.get(i);
			merchandiseTotalSold = merchandiseTotalSold + merchandiseSoldWithMonth.get(i);
			musicTotalSold = musicTotalSold + musicSoldWithMonth.get(i);
		}

		// find average quantity each month
		List<Integer> average = new ArrayList<Integer>();
		int clothingSold = 0;
		int merchandiseSold = 0;
		int musicSold = 0;
		for (int i = 0; i < clothingSoldWithMonth.size(); i++) {
			clothingSold = clothingSoldWithMonth.get(i);
			merchandiseSold = merchandiseSoldWithMonth.get(i);
			musicSold = musicSoldWithMonth.get(i);
			int monthAverage = (Integer) (clothingSold + merchandiseSold + musicSold) / 3;
			average.add(monthAverage);
		}

		model.addAttribute("profileUrl", myUserDetails.getProfileUrl());
		model.addAttribute("productnumber", totalproduct);
		model.addAttribute("clothing", clothingSoldWithMonth);
		model.addAttribute("merchandise", merchandiseSoldWithMonth);
		model.addAttribute("music", musicSoldWithMonth);
		model.addAttribute("clothingtotal", clothingTotalSold);
		model.addAttribute("merchandisetotal", merchandiseTotalSold);
		model.addAttribute("musictotal", musicTotalSold);
		model.addAttribute("average", average);
		model.addAttribute("user", artist);
		model.addAttribute("profileUrl", artist.getProducts());

		if (myUserDetails != null) {
			model.addAttribute("count", uservice.getItemCountByUserID(myUserDetails.getUserId()));
		}

		return "dashboard";
	}
}
