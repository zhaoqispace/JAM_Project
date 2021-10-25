package sg.edu.iss.jam;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordGenerator {
	
	public static void main(String[] args) {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		String rawPassword = "BTSOfficialpw";
		String encodedPassword = encoder.encode(rawPassword);
		//JayChoupw
		System.out.println(encodedPassword);
	}

}
