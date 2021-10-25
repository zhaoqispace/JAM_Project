package sg.edu.iss.jam.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import sg.edu.iss.jam.model.Roles;
import sg.edu.iss.jam.model.User;

public interface RolesRepository extends JpaRepository<Roles, Long> {
	
	List<Roles> findByRoleUser(User roleUser);

}
