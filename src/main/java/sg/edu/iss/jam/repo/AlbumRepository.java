package sg.edu.iss.jam.repo;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import sg.edu.iss.jam.model.Album;

public interface AlbumRepository extends JpaRepository<Album, Long>  {
	
	@Query("SELECT a from Album a join a.channel c where c.channelID =:channelID")
	Collection<Album> findBychannelID(@Param("channelID") Long channelID);
	
	@Query("Select a from Album a WHERE a.AlbumDescription LIKE %:searchterm%")
	Collection<Album> findAlbumsLikeName(@Param("searchterm")String searchterm);

}
