package sg.edu.iss.jam.repo;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import sg.edu.iss.jam.model.Media;
import sg.edu.iss.jam.model.Tag;

public interface TagRepository extends JpaRepository<Tag, Long> {

	@Query("SELECT t FROM Tag t JOIN t.mediaTagList m WHERE m.id = :id")
	List<Tag> findTagsByMediaId(@Param("id") Long id);
	
	public Collection<Tag> findByMediaTagList(Media media);
	
	Tag findByTagName(String tagName);
}
