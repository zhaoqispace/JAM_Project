package sg.edu.iss.jam.repo;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import sg.edu.iss.jam.model.Album;
import sg.edu.iss.jam.model.Channel;
import sg.edu.iss.jam.model.Media;
import sg.edu.iss.jam.model.MediaType;

public interface MediaRepository extends JpaRepository<Media, Long> {

	@Query("SELECT m FROM Media m JOIN m.playLists pl WHERE pl.playlistID = :playlistID")
	List<Media> findMediaListByPlayListID(@Param("playlistID") Long playlistID);

	@Query("SELECT m FROM Media m WHERE m.mediaType = :mediaType AND m.id = :id")
	Media findMediaByMediaTypeAndMediaId(@Param("mediaType") MediaType mediaType, @Param("id") Long id);

	@Query("SELECT m FROM Media m WHERE m.mediaType = :mediaType")
	List<Media> findAllMediaByMediaType(@Param("mediaType") MediaType mediaType);

	// Find Media Count by channel
	@Query("Select count(distinct m) from Media m Join m.channel c where c.channelID =:channelid AND m.mediaType =:mediaType")
	public int CountMediaByChannel(@Param("channelid") long channelid, @Param("mediaType") MediaType video);

	@Query("Select count (distinct m) from Media m Join m.album a where a.AlbumID =:AlbumID")
	public int CountMediaByAlbum(@Param("AlbumID") long AlbumID);

	public Collection<Media> findBychannel(Channel channel);

	@Query("SELECT DISTINCT m FROM Media m WHERE m.album = :album AND m.channel.mediaType = :mediaType")
	List<Media> findMediaByAlbumAndMediaType(@Param("album") Album album, @Param("mediaType") MediaType mediaType);

	@Query("Select m FROM Media m JOIN m.album al WHERE al.AlbumID =:AlbumID ORDER BY m.AlbumOrder ")
	public List<Media> findByalbum(Long AlbumID);

	// generic videolandingpage
	@Query("Select m from Media m join m.userHistory uh " + "where m.mediaType=:mediatype "
			+ "group by m order by count(distinct uh.id) desc")
	public List<Media> getMediaByTypeAndCount(@Param("mediatype") MediaType mediaType);

	@Query("Select m from Media m join m.userHistory uh " + "where m.mediaType=:mediatype "
			+ "and uh.datetime>=Date(:lesscurrentdate)" + "group by m order by count(distinct uh.id) desc")
	public List<Media> getMediaByUserHistory(@Param("mediatype") MediaType mediaType,
			@Param("lesscurrentdate") LocalDate lesscurrentdate);

	// dashboard
	@Query("Select m FROM Media m WHERE m.mediaType = 'Video'")
	List<Media> findAllVideos();

	@Query("Select m from Media m join m.album a where a.AlbumID=:AlbumID")
	public List<Media> findMediaByAlbumId(@Param("AlbumID") long AlbumID);

	@Query("Select m FROM Media m WHERE m.mediaType = 'Music'")
	List<Media> findAllMusics();

	@Query("Select DISTINCT m from Media m JOIN m.tagList t JOIN m.channel c JOIN c.channelUser u "
			+ "WHERE  m.mediaType =:mediaType " + "AND (" + "m.title LIKE %:searchterm% "
			+ "or CONCAT(u.firstName,' ',u.lastName) LIKE %:searchterm% " + "or t.tagName LIKE %:searchterm%" + ") ")
	public Collection<Media> getMediasfromVarious(@Param("searchterm") String searchterm,
			@Param("mediaType") MediaType mediaType);

}
