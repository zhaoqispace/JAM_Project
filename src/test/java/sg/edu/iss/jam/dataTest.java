package sg.edu.iss.jam;

import org.springframework.beans.factory.annotation.Autowired;

import sg.edu.iss.jam.repo.RolesRepository;
import sg.edu.iss.jam.repo.UserRepository;

public class dataTest {
	
	@Autowired
	private UserRepository urepo;
	@Autowired
	private RolesRepository rrepo;
	
	public static void main(String[] args) {
		
		System.out.println();
		
	}

}
