package sg.edu.iss.jam.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import sg.edu.iss.jam.model.MediaType;
import sg.edu.iss.jam.model.Playlists;

public interface PlaylistsRepository extends JpaRepository<Playlists, Long> {

	@Query("SELECT pl FROM Playlists pl WHERE pl.playlistUser.userID = :userID")
	List<Playlists> findPlaylistsByUserId(@Param("userID") long userID);
	
	@Query("SELECT pl FROM Playlists pl WHERE pl.playlistID = :playlistID")
	Playlists findPlaylistByPlaylistID(@Param("playlistID") long playlistID);
	
	@Query("SELECT pl FROM Playlists pl WHERE pl.playlistUser.userID = :userID AND pl.mediaType = :mediaType")
	List<Playlists> findPlaylistByUserIdAndMediaType(@Param("userID") long userID, @Param("mediaType") MediaType mediaType);
}
