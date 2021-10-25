package sg.edu.iss.jam.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import sg.edu.iss.jam.model.Roles;
import sg.edu.iss.jam.model.User;

public class MyUserDetails implements UserDetails {

	private User user;

	public MyUserDetails(User user) {
		super();
		this.user = user;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {

		List<String> roles = new ArrayList<>();
		for (Roles r : user.getRoles()) {
			roles.add(r.getRole().toString());
		}
		List<SimpleGrantedAuthority> authorities = new ArrayList<>();

		for (int i = 0; i < roles.size(); i++) {
			authorities.add(new SimpleGrantedAuthority(roles.get(i)));
		}
		return authorities;

//		authorities.add(new SimpleGrantedAuthority("ROLE_"+user.getRole().toString()));
//		return authorities;

	}

	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return user.getPassword();
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return user.getEmail();
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return user.isEnabled();
	}

	public String getFirstName() {
		// TODO Auto-generated method stub
		return user.getFirstName();
	}

	public String getFullName() {
		return user.getFullname();
	}

	public String getProfileUrl() {
		return user.getProfileUrl();
	}

	public Long getUserId() {
		return user.getUserID();
	}

	public String getRole() {
		return user.getRole();
	}

}
