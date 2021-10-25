package sg.edu.iss.jam.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import sg.edu.iss.jam.model.User;
import sg.edu.iss.jam.repo.UserRepository;

public class UserDetailServiceImplementation implements UserDetailsService {
	
	@Autowired
	private UserRepository urepo;
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		User user = urepo.getUserByEmail(email);
		
		if (user == null) {
			throw new UsernameNotFoundException("Could not find user");
		}
		return new MyUserDetails(user);
	}

}
