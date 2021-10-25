
package sg.edu.iss.jam.controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.Nullable;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import sg.edu.iss.jam.model.Post;
import sg.edu.iss.jam.model.Subscribed;
import sg.edu.iss.jam.model.User;
import sg.edu.iss.jam.repo.PostRepository;
import sg.edu.iss.jam.repo.SubscribedRepository;
import sg.edu.iss.jam.repo.UserRepository;
import sg.edu.iss.jam.security.MyUserDetails;
import sg.edu.iss.jam.service.ArtistInterface;
import sg.edu.iss.jam.service.HomeInterface;
import sg.edu.iss.jam.service.UserInterface;

@Controller
@RequestMapping("/home")
public class HomeController {

	@Autowired
	UserInterface uService;

	@Autowired
	ArtistInterface adService;


	@Autowired
	SubscribedRepository srepo;

	@Autowired
	UserRepository urepo;

	@Autowired
	HomeInterface hService;

	@Autowired
	PostRepository postrepo;

	@GetMapping("/")
	public String goToHome(Model model, @AuthenticationPrincipal MyUserDetails userDetails) {

		User user = null;
		user = uService.findUserByUserId(userDetails.getUserId());

		model.addAttribute("user", uService.findUserByUserId(userDetails.getUserId()));
		model.addAttribute("profileUrl", user.getProfileUrl());
		model.addAttribute("bannerUrl", user.getBannerUrl());
		model.addAttribute("userID", user.getUserID());
		model.addAttribute("homeLinkActive", true);
		if (userDetails != null) {
			model.addAttribute("count", uService.getItemCountByUserID(userDetails.getUserId()));
		}

		model.addAttribute("followers", ((srepo.getArtistSubscribed(user.getUserID())).size()
				- (srepo.getArtistUnSubscribed(user.getUserID())).size()));
		model.addAttribute("following",
				((srepo.getSubscriptions(user.getUserID())).size() - srepo.getMyUnsubscribe(user.getUserID()).size()));

		List<Subscribed> subs = srepo.getArtistSubscribed(user.getUserID());
		subs.stream().forEach(x -> System.out.print(" " + x.getSubscriber().getUserID()));

		Long count = uService.getItemCountByUserID(userDetails.getUserId());
		model.addAttribute("count", count);

		return "home";
	}

	@RequestMapping("/edituser")
	public String editUser(Model model, @AuthenticationPrincipal MyUserDetails userDetails) {

		User user = uService.findUserByUserId(userDetails.getUserId());

		if (userDetails != null) {
			model.addAttribute("count", uService.getItemCountByUserID(userDetails.getUserId()));
		}

		model.addAttribute("user", uService.findUserByUserId(userDetails.getUserId()));
		model.addAttribute("followers", ((srepo.getArtistSubscribed(user.getUserID())).size()
				- (srepo.getArtistUnSubscribed(user.getUserID())).size()));
		model.addAttribute("following",
				((srepo.getSubscriptions(user.getUserID())).size() - srepo.getMyUnsubscribe(user.getUserID()).size()));

		return "editUserForm";
	}

	@RequestMapping("/saveuser")
	public String saveUser(Model model, @Valid @ModelAttribute("user") User user,
			@AuthenticationPrincipal MyUserDetails userDetails) {

		uService.updateUser(user);

		if (userDetails != null) {
			model.addAttribute("count", uService.getItemCountByUserID(userDetails.getUserId()));
		}
		model.addAttribute("user", user);
		model.addAttribute("followers", ((srepo.getArtistSubscribed(user.getUserID())).size()
				- (srepo.getArtistUnSubscribed(user.getUserID())).size()));
		model.addAttribute("following",
				((srepo.getSubscriptions(user.getUserID())).size() - srepo.getMyUnsubscribe(user.getUserID()).size()));
		return "edtiSuccess";
	}

	@RequestMapping("/updateuser")
	public String updateUser(Model model, @AuthenticationPrincipal MyUserDetails userDetails) {

		model.addAttribute("user", uService.findUserByUserId(userDetails.getUserId()));

		return "signup2";
	}

	@RequestMapping("/subscribers/{userID}")
	public String viewSubs(Model model, @AuthenticationPrincipal MyUserDetails userDetails,
			@PathVariable("userID") Long userID) {
		User user = uService.findUserByUserId(userID);

		List<User> subUsers = uService.getUserSubs(userID);

		if (userDetails != null) {
			model.addAttribute("count", uService.getItemCountByUserID(userDetails.getUserId()));
		}

		model.addAttribute("subscribers", subUsers);
		model.addAttribute("user", uService.findUserByUserId(userDetails.getUserId()));
		model.addAttribute("fullName", user.getFullname());
		model.addAttribute("userID", user.getUserID());
		model.addAttribute("profileUrl", user.getProfileUrl());
		model.addAttribute("followers", ((srepo.getArtistSubscribed(user.getUserID())).size()
				- (srepo.getArtistUnSubscribed(user.getUserID())).size()));
		model.addAttribute("following",
				((srepo.getSubscriptions(user.getUserID())).size() - srepo.getMyUnsubscribe(user.getUserID()).size()));

		return "subscribers";
	}

	@RequestMapping("/following/{userID}")
	public String viewFollowing(Model model, @AuthenticationPrincipal MyUserDetails userDetails,
			@PathVariable("userID") Long userID) {
		User user = uService.findUserByUserId(userID);

		List<User> followUsers = uService.getFollowing(userID);

		if (userDetails != null) {
			model.addAttribute("count", uService.getItemCountByUserID(userDetails.getUserId()));
		}

		model.addAttribute("followingUsers", followUsers);
		model.addAttribute("user", urepo.findById(userDetails.getUserId()).get());
		model.addAttribute("profileUrl", user.getProfileUrl());
		model.addAttribute("fullName", user.getFullname());
		model.addAttribute("followers", ((srepo.getArtistSubscribed(user.getUserID())).size()
				- (srepo.getArtistUnSubscribed(user.getUserID())).size()));
		model.addAttribute("following",
				((srepo.getSubscriptions(user.getUserID())).size() - srepo.getMyUnsubscribe(user.getUserID()).size()));

		return "following";
	}
	// --------------------Posts Methods------------------//

	@GetMapping("/getposts/{userid}")
	public String getPosts(Model model, @AuthenticationPrincipal MyUserDetails userDetails,
			@PathVariable("userid") Long userid) {

		// logged in user
		User loggedinuser = uService.findUserByUserId(userDetails.getUserId());

		// User who's page you are viewing
		User targetuser = uService.findUserByUserId(userid);

		model.addAttribute("posts", hService.getPostsbyID(targetuser.getUserID()));
		model.addAttribute("profileUrl", userDetails.getProfileUrl());
		model.addAttribute("firstName", userDetails.getFirstName());

		return "/Fragments/PostContent";
	}

	// Ajax Controller to post/edit Content
	@PostMapping("/postpost")
	public String postPosts(Model model, @AuthenticationPrincipal MyUserDetails userDetails,
			@RequestParam(value = "submittedContent") String submittedContent,
			@Nullable @RequestParam(value = "postID") Long postID) {

		// logged in user
		User loggedinuser = uService.findUserByUserId(userDetails.getUserId());
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

		Post post = new Post();

		// Add New Post
		if (postID == null) {
			post.setContent(submittedContent);
			post.setDateTime(LocalDateTime.now().format(formatter).toString());
			post.setUser(loggedinuser);
			hService.SavePost(post);
		} else {
			hService.getPostbyID(postID);
			post.setContent(submittedContent);
			post.setDateTime(LocalDateTime.now().format(formatter).toString());
			post.setUser(loggedinuser);
			hService.SavePost(post);
		}

		model.addAttribute("posts", hService.getPostsbyID(loggedinuser.getUserID()));
		model.addAttribute("profileUrl", userDetails.getProfileUrl());
		model.addAttribute("firstName", userDetails.getFirstName());

		return "/Fragments/PostContent";
	}

	@PostMapping("/deletepost")
	public String deletePosts(Model model, @AuthenticationPrincipal MyUserDetails userDetails,
			@RequestParam(value = "postID") Long postID) {

		// logged in user
		User loggedinuser = uService.findUserByUserId(userDetails.getUserId());

		if (hService.deletepost(hService.getPostbyID(postID))) {
			model.addAttribute("error", "Post not deleted");
		}

		model.addAttribute("posts", hService.getPostsbyID(loggedinuser.getUserID()));

		return "redirect:/home/";
	}

	@GetMapping("/profile/{userID}")
	public String viewUserProfile(Model model, @AuthenticationPrincipal MyUserDetails userDetails,
			@PathVariable("userID") Long userID) {

		if (userID == userDetails.getUserId().longValue()) {

			return "redirect:/home/";
		} else {

			User viewer = uService.findUserByUserId(userDetails.getUserId());
			User viewee = uService.findUserByUserId(userID);
			List<Post> posts = postrepo.findByUser(viewee);

			List<Post> PostsSorted = posts.stream().sorted(Comparator.comparing(Post::getDateTime).reversed())
					.collect(Collectors.toList());

			model.addAttribute("profileUrl", viewer.getProfileUrl());
			model.addAttribute("user", viewer);

			model.addAttribute("vieweeProfileUrl", viewee.getProfileUrl());
			model.addAttribute("vieweeFullName", viewee.getFullname());
			model.addAttribute("userID", userID);
			model.addAttribute("bannerUrl", viewee.getBannerUrl());
			model.addAttribute("followers", ((srepo.getArtistSubscribed(viewee.getUserID())).size()
					- (srepo.getArtistUnSubscribed(viewee.getUserID())).size()));
			model.addAttribute("following", ((srepo.getSubscriptions(viewee.getUserID())).size()
					- srepo.getMyUnsubscribe(viewee.getUserID()).size()));
			model.addAttribute("posts", PostsSorted);

			return "viewArtistProfile";
		}
	}

	@RequestMapping("/feed")
	public String viewFeed(Model model, @AuthenticationPrincipal MyUserDetails userDetails) {

		User user = uService.findUserByUserId(userDetails.getUserId());

		List<User> following = uService.getFollowing(user.getUserID());

		List<Post> myFollowingPosts = new ArrayList<>();

		for (User u : following) {
			List<Post> temp = postrepo.findByUser(u);
			for (Post p : temp) {
				myFollowingPosts.add(p);
			}
		}

		List<Post> myFollowingPostSorted = myFollowingPosts.stream()
				.sorted(Comparator.comparing(Post::getDateTime).reversed()).collect(Collectors.toList());

		if (userDetails != null) {
			model.addAttribute("count", uService.getItemCountByUserID(userDetails.getUserId()));
		}

		model.addAttribute("feedLinkActive", true);
		model.addAttribute("user", user);
		model.addAttribute("posts", myFollowingPostSorted);
		model.addAttribute("userID", user.getUserID());
		model.addAttribute("followers", ((srepo.getArtistSubscribed(user.getUserID())).size()
				- srepo.getArtistUnSubscribed(user.getUserID()).size()));
		model.addAttribute("following",
				((srepo.getSubscriptions(user.getUserID())).size() - srepo.getMyUnsubscribe(user.getUserID()).size()));

		return "feed";
	}

}
