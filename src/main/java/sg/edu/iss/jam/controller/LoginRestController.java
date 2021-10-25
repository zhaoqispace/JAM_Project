package sg.edu.iss.jam.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import sg.edu.iss.jam.model.Role;
import sg.edu.iss.jam.model.Roles;
import sg.edu.iss.jam.model.User;
import sg.edu.iss.jam.service.UserInterface;

@RestController
@RequestMapping("/api")
public class LoginRestController {
	
	@Autowired
	UserInterface userService;
	
	
	@PostMapping("/register")
    public Map<String, Long> registerUser(@Valid @RequestBody User newUser) {
		Map<String,Long> map  = new HashMap<>();
        List<User> users = userService.getAllUser();
        for (User user : users) {
            if (user.getEmail().equals(newUser.getEmail())) {
				System.out.println("User Already exists!");
                map.put("Result", 0L);
                return map;
            }
        }
        
        Collection<Roles> roles = new ArrayList<Roles>();
        String rawPassword = newUser.getPassword();
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		String encodedPassword = encoder.encode(rawPassword);
		newUser.setPassword(encodedPassword);
		newUser.setProfileUrl("/images/default_user.jpg");
		newUser.setRoles(roles);
		Roles b = new Roles(Role.Customer, newUser);
		User user = userService.saveUser(newUser);
		map.put("Result", user.getUserID());
		userService.saveRole(b);
        return map;
    }
    
    @PostMapping("/login")
    public Map<String, Long> loginUser(@Valid @RequestBody User user) {
    	BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    	Map<String,Long> map  = new HashMap<>();
        List<User> users = userService.getAllUser(); 
        for (User other : users) {
            if (other.getEmail().equals(user.getEmail())&& passwordEncoder.matches(user.getPassword(), other.getPassword())) {
            	map.put("Result", other.getUserID());
                return map;
            }
        }
        map.put("Result",0L);
        return map;
    }
}
