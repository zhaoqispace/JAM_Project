package sg.edu.iss.jam.repo;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import sg.edu.iss.jam.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
	
	@Query("SELECT u FROM User u where u.email = :email")
	public User getUserByEmail(@Param("email") String email);
	
//	
//	@Query("SELECT s FROM Subscribed s JOIN s.user u where u.userID = :userid")
//	List<Subscribed>getListofFollowingByUserId(@Param("userid") long userId);
//	
	@Query("Select u from User u where u.firstName LIKE %:firstName%")
	public Collection<User> getusersbyfirstname(@Param("firstName")String firstName);
	
	@Query("Select u from User u where u.lastName LIKE %:lastName%")
	public Collection<User> getusersbylastname(@Param("lastName")String lastName);
	
	@Query("Select u from User u where CONCAT(u.firstName,' ',u.lastName) LIKE %:name%")
	public Collection<User> getusersbyname(@Param("name")String name);
	
	public User findByResetPasswordToken(String token);
	
	public User findByEmail(String email);
	
	
}
