package sg.edu.iss.jam.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import sg.edu.iss.jam.model.MediaType;
import sg.edu.iss.jam.model.User;
import sg.edu.iss.jam.security.MyUserDetails;
import sg.edu.iss.jam.service.SearchInterface;
import sg.edu.iss.jam.service.UserInterface;

@Controller
@RequestMapping("/search")
public class SearchController {

	@Autowired
	SearchInterface sService;

	@Autowired
	UserInterface userService;

	@GetMapping("")
	public String Search(@RequestParam(value = "searchterm", required = false) String searchterm, Model model,
			@AuthenticationPrincipal MyUserDetails userDetails) {

		if (searchterm != null && searchterm.trim() != ("")) {
			model.addAttribute("searchUsers", sService.SearchUsersbyName(searchterm.trim()));
			model.addAttribute("searchVideos", sService.SearchMediabyVarious(searchterm.trim(), MediaType.Video));
			model.addAttribute("searchMusic", sService.SearchMediabyVarious(searchterm.trim(), MediaType.Music));
			model.addAttribute("searchAlbums", sService.SearchAlbumbyName(searchterm.trim()));
		} else {
			model.addAttribute("searchUsers", null);
			model.addAttribute("searchVideos", null);
			model.addAttribute("searchMusic", null);
			model.addAttribute("searchAlbums", null);
		}

		if (userDetails != null) {
			User user = userService.findUserByUserId(userDetails.getUserId());
			Long userid = user.getUserID();
			Long count = userService.getItemCountByUserID(user.getUserID());
			model.addAttribute("count", count);
			model.addAttribute("user", user);
			model.addAttribute("profileUrl", user.getProfileUrl());
			model.addAttribute("bannerUrl", user.getBannerUrl());
		}

		model.addAttribute("user", null);

		return "searchpage";
	}

}
