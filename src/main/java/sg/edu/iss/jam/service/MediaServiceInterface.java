package sg.edu.iss.jam.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.http.ResponseEntity;

import sg.edu.iss.jam.model.Media;
import sg.edu.iss.jam.model.MediaType;
import sg.edu.iss.jam.model.UserHistory;

public interface MediaServiceInterface {

	public ResponseEntity<byte[]> getContent(String location, String fileName, String range, String contentTypePrefix);

	// genericlandingpage

	public List<Media> getMediaByTypeAndCount(MediaType mediaType);

	public List<Media> getMediaByUserHistory(MediaType mediaType, LocalDate lesscurrentdate);

	public Media getMediaById(Long id);

	// dashboard
	public List<Media> findAllVideos();

	public List<Media> findMediaByAlbumId(Long albumID);

	public List<Media> findAllMusics();

	public void saveUserHistory(UserHistory userhistory);
}
