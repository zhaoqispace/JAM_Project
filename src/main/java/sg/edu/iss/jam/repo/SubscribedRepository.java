package sg.edu.iss.jam.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import sg.edu.iss.jam.model.Subscribed;

public interface SubscribedRepository extends JpaRepository<Subscribed, Long> {
	
	@Query("SELECT s FROM Subscribed s WHERE s.subscriber.userID = :artistId ")
	List<Subscribed> findAllSubscribedBySubId(@Param("artistId") Long subscriberId);
	
	@Query("SELECT s FROM Subscribed s WHERE s.artist.userID = :artistId ")
	List<Subscribed> findAllFollowingByArtistId(@Param("artistId") Long artistId);

	@Query("SELECT s FROM Subscribed s WHERE s.artist.userID = :artistId "
			+ "AND s.subscribed = false")
	List<Subscribed> getArtistUnSubscribed(@Param("artistId") Long artistId);
	
	@Query("SELECT s FROM Subscribed s WHERE s.artist.userID = :artistId "
			+ "AND s.subscribed = true")
	List<Subscribed> getArtistSubscribed(@Param("artistId") Long artistId);
	
	@Query("SELECT s FROM Subscribed s WHERE s.subscriber.userID = :artistId "
			+ "AND s.subscribed = true")
	List<Subscribed> getSubscriptions(@Param("artistId") Long artistId);
	
	@Query("SELECT s FROM Subscribed s WHERE s.subscriber.userID = :artistId "
			+ "AND s.subscribed = false")
	List<Subscribed> getMyUnsubscribe(@Param("artistId") Long artistId);
	
//	@Query("SELECT s FROM Subscribed s WHERE s.artist.userID = :userID")
//	List<Subscribed> getArtistSubscribedUnsubscribed(@Param("userID") Long userId, @Param("loggedInUserId")Long loggedInUserId);
	
	@Query("SELECT s FROM Subscribed s WHERE s.artist.userID = :artistId "
			+ "AND s.subscriber.userID = :loggedInUserId "
			+ "AND s.subscribed = false")
	List<Subscribed> getArtistUnsubscribedByLoggInUserId(@Param("artistId") Long artistId, @Param("loggedInUserId")Long loggedInUserId);
	
	
	@Query("SELECT s FROM Subscribed s WHERE s.artist.userID = :artistId "
			+ "AND s.subscriber.userID = :loggedInUserId "
			+ "AND s.subscribed = true")
	List<Subscribed> getArtistSubscribedByLoggInUserId(@Param("artistId") Long artistId, @Param("loggedInUserId")Long loggedInUserId);


}
