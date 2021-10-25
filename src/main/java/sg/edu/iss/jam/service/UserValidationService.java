package sg.edu.iss.jam.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sg.edu.iss.jam.model.User;
import sg.edu.iss.jam.repo.UserRepository;

@Service
public class UserValidationService {
	@Autowired
	private UserRepository urepo;

	public String validateUserEmail(User user) {

		String message = "";
		if (urepo.getUserByEmail(user.getEmail()) != null) {
			message = "An account associated with the email is already register to a user.";
		}

		return message;

	}

}
