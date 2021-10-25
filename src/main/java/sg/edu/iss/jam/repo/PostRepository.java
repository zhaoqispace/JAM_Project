package sg.edu.iss.jam.repo;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import sg.edu.iss.jam.model.Post;
import sg.edu.iss.jam.model.User;

public interface PostRepository extends JpaRepository<Post, Long> {
	
	@Query("SELECT p FROM Post p JOIN p.user u where u.userID =:userID")
	public Collection<Post> findPostsbyuserid(@Param("userID") Long userID);
	
	public List<Post> findByUser(User user);

}
