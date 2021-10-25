package sg.edu.iss.jam.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import sg.edu.iss.jam.model.MediaType;
import sg.edu.iss.jam.model.UserHistory;

public interface UserHistoryRepository extends JpaRepository<UserHistory, Long> {

	@Query("SELECT uh FROM UserHistory uh JOIN uh.mediaHistory m WHERE m.id = :id")
	public List<UserHistory> findUserHistoryByMediaId(@Param("id") Long id);
	
	@Query("Select count(distinct h) from UserHistory h Join h.mediaHistory m where m.id =:mediaID")
	public int CountViewsByMedia(@Param("mediaID") long mediaID);
	
	@Query("Select count(distinct h) from UserHistory h Join h.mediaHistory m Join m.album a where a.AlbumID=:AlbumID")
	public int CountViewsByAlbum(@Param("AlbumID") long AlbumID);
	
	@Query("SELECT uh FROM UserHistory uh WHERE uh.historyUser.userID = :userID")
	public List<UserHistory> findUserHistoryByUserId(@Param("userID") Long userID);
	
	@Query("SELECT uh FROM UserHistory uh WHERE uh.historyUser.userID = :userId "
			+ "AND uh.mediaHistory.mediaType = :mediaType")
	public List<UserHistory> findUserHistoryByUserIdAndMediaType(@Param("userId") Long userId, @Param("mediaType") MediaType mediaType);


	

}
