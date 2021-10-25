package sg.edu.iss.jam.controller;

import java.io.UnsupportedEncodingException;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import net.bytebuddy.utility.RandomString;
import sg.edu.iss.jam.model.User;
import sg.edu.iss.jam.service.UserInterface;
import sg.edu.iss.jam.service.UserNotFoundException;
import sg.edu.iss.jam.service.Utility;

@Controller
public class ForgotPasswordController {
	@Autowired
	UserInterface uService;

	@Autowired
	JavaMailSender mailSender;

	@GetMapping("/forgot_password")
	public String showForgotPasswordForm(Model model) {
		model.addAttribute("pageTitle", "Forgot Password");

		return "forgot_password_form";
	}

	@PostMapping("/forgot_password")
	public String processForgotPasswordForm(Model model, HttpServletRequest request) {

		String email = request.getParameter("email");
		String token = RandomString.make(45);

		try {
			uService.updateResetPasswordToken(token, email);

			String resetPasswordLink = Utility.getSiteURL(request) + "/reset_password?token=" + token;

			sendEmail(email, resetPasswordLink);

			model.addAttribute("message", "We have sent a reset password link to your email, please check.");

		} catch (UserNotFoundException ex) {
			model.addAttribute("error", ex.getMessage());
		} catch (UnsupportedEncodingException | MessagingException e) {
			model.addAttribute("error", "Error while sending email.");
		}

		model.addAttribute("pageTitle", "Forgot Password");

		return "forgot_password_form";
	}

	private void sendEmail(String email, String resetPasswordLink)
			throws MessagingException, UnsupportedEncodingException {
		MimeMessage message = mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message);

		helper.setFrom("contact@gmail.com", "Support");
		helper.setTo(email);

		String subject = "Here's the link to reset your password";
		String content = "<p>Hello,</p>" + "You have requested to reset your password. </p>"
				+ "<p>Click the link below to change your password: </p>" + "<p><b><a href=\"" + resetPasswordLink
				+ "\">Change my password</a></b></p>"
				+ "<p>Ignore this email if you do remember your password, or you have not made the request</p>";

		helper.setSubject(subject);
		helper.setText(content, true);

		mailSender.send(message);
	}

	@GetMapping("/reset_password")
	public String showResetPasswordForm(@Param(value = "token") String token, Model model) {

		User user = uService.get(token);
		if (user == null) {
			model.addAttribute("title", "Reset your password");
			model.addAttribute("message", "Invalid Token");
			return "message";
		}

		model.addAttribute("token", token);
		model.addAttribute("pageTitle", "Reset your password");
		return "reset_password_form";

	}

	@RequestMapping("/reset_password")
	public String processRestPassword(HttpServletRequest request, Model model) {
		String token = request.getParameter("token");
		String password = request.getParameter("password");

		User user = uService.get(token);
		uService.updatePassword(user, password);

		model.addAttribute("message", "You have successfully changed your password.");

		return "message";
	}

}
