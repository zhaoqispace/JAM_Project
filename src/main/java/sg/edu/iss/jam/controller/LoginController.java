package sg.edu.iss.jam.controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import sg.edu.iss.jam.model.Album;
import sg.edu.iss.jam.model.Channel;
import sg.edu.iss.jam.model.Media;
import sg.edu.iss.jam.model.MediaType;
import sg.edu.iss.jam.model.Role;
import sg.edu.iss.jam.model.Roles;
import sg.edu.iss.jam.model.ShoppingCart;
import sg.edu.iss.jam.model.User;
import sg.edu.iss.jam.repo.ChannelRepository;
import sg.edu.iss.jam.repo.RolesRepository;
import sg.edu.iss.jam.repo.ShoppingCartRepository;
import sg.edu.iss.jam.repo.UserRepository;
import sg.edu.iss.jam.service.UserInterface;
import sg.edu.iss.jam.service.UserValidationService;

@Controller
@RequestMapping("/login")
public class LoginController {

	@Autowired
	UserInterface userService;

	@Autowired
	RolesRepository rrepo;

	@Autowired
	ShoppingCartRepository screpo;

	@Autowired
	ChannelRepository crepo;

	@Autowired
	UserRepository urepo;

	@Autowired
	UserValidationService validationService;

	@RequestMapping("/")
	public String login(Model model) {

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
			return "index";
		}
		return "redirect:/";
	}

	@RequestMapping("/signup")
	public String signup(Model model) {
		User user = new User();
		model.addAttribute("user", user);
		model.addAttribute("isArtist", user.isArtist());
		return "signupForm";
	}

	@RequestMapping("/save")
	public String saveUserForm(@ModelAttribute("user") @Valid User user, BindingResult bindingResult, Model model) {
		Collection<Roles> roles = new ArrayList<Roles>();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
		String err = validationService.validateUserEmail(user);
		if (!err.isEmpty()) {
			ObjectError error = new ObjectError("globalError", err);
			bindingResult.addError(error);
		}

		if (bindingResult.hasErrors()) {
			return "signupForm";
		}

		if ((user.getDisplayName() == null && (!user.isArtist()))
				||(user.isArtist())) {
			String rawPassword = user.getPassword();
			BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
			String encodedPassword = encoder.encode(rawPassword);
			user.setPassword(encodedPassword);
			user.setEnabled(true);
			user.setBannerUrl("/images/JAM_LOGO.png");

			if (user.getProfileUrl() == null) {
				user.setProfileUrl("/images/default_user.jpg");
			}
		}

		if (user.isArtist() == true) {
			user.setRoles(roles);
			Roles a = new Roles(Role.Artist, user);
			ShoppingCart s = new ShoppingCart(user, null);
			String creationTime = LocalDateTime.now().format(formatter).toString();
			String videoChannelDes = user.getFullname() + ", " + "This is your Video Channel";
			String videoChannelName = user.getFullname() + " Video Channel";
			String musicChannelDes = user.getFullname() + ", " + "This is your Music Channel";
			String musicChannelName = user.getFullname() + " Music Channel";
			Collection<Media> channelMediaList = new ArrayList<>();
			Collection<Album> albumslist = new ArrayList<>();
			Channel v = new Channel(videoChannelName, videoChannelDes, MediaType.Video, creationTime, channelMediaList,
					user, albumslist);
			Channel m = new Channel(musicChannelName, musicChannelDes, MediaType.Music, creationTime, channelMediaList,
					user, albumslist);
			userService.updateUser(user);
			rrepo.save(a);
			screpo.save(s);
			crepo.save(v);
			crepo.save(m);
			model.addAttribute("user", userService.findUserByUserId(user.getUserID()));
			model.addAttribute("profileUrl", user.getProfileUrl());
			return "signup2";
		}

		else {

			if (user.getDisplayName() != null) {
				List<Roles> r1 = rrepo.findByRoleUser(user);
				for (Roles r : r1) {
					rrepo.deleteById(r.getId());
				}
				user.setRoles(roles);
				user.setArtist(true);
				Roles a = new Roles(Role.Artist, user);
				ShoppingCart s = new ShoppingCart(user, null);
				String creationTime = LocalDateTime.now().format(formatter).toString();
				String videoChannelDes = user.getFullname() + ", " + "This is your Video Channel";
				String videoChannelName = user.getFullname() + " Video Channel";
				String musicChannelDes = user.getFullname() + ", " + "This is your Music Channel";
				String musicChannelName = user.getFullname() + " Music Channel";
				Collection<Media> channelMediaList = new ArrayList<>();
				Collection<Album> albumslist = new ArrayList<>();
				Channel v = new Channel(videoChannelName, videoChannelDes, MediaType.Video, creationTime,
						channelMediaList, user, albumslist);
				Channel m = new Channel(musicChannelName, musicChannelDes, MediaType.Music, creationTime,
						channelMediaList, user, albumslist);
				userService.updateUser(user);
				rrepo.save(a);
				screpo.save(s);
				crepo.save(v);
				crepo.save(m);

				return "redirect:/home/";
			}

			else {
				user.setRoles(roles);
				Roles b = new Roles(Role.Customer, user);
				ShoppingCart s = new ShoppingCart(user, null);
				userService.updateUser(user);
				rrepo.save(b);
				screpo.save(s);
				return "successSignUp";
			}
		}

	}

	@RequestMapping("/update")
	public String saveArtistForm(@ModelAttribute("user") User user, BindingResult bindingResult) {
		userService.updateUser(user);

		return "successSignUp";
	}

}