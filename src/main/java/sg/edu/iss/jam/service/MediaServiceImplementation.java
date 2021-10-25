package sg.edu.iss.jam.service;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import sg.edu.iss.jam.model.Media;
import sg.edu.iss.jam.model.MediaType;
import sg.edu.iss.jam.model.UserHistory;
import sg.edu.iss.jam.repo.MediaRepository;
import sg.edu.iss.jam.repo.UserHistoryRepository;

@Service
public class MediaServiceImplementation implements MediaServiceInterface {

	// generic landing page
	@Autowired
	MediaRepository mediarepo;

	@Autowired
	UserHistoryRepository uhrepo;

	@Override
	public List<Media> getMediaByTypeAndCount(MediaType mediaType) {
		return mediarepo.getMediaByTypeAndCount(mediaType);
	}

	@Override
	public List<Media> getMediaByUserHistory(MediaType mediaType, LocalDate lesscurrentdate) {
		return mediarepo.getMediaByUserHistory(mediaType, lesscurrentdate);
	}

	public static final int BYTE_RANGE = 128;

	@Override
	public ResponseEntity<byte[]> getContent(String location, String fileName, String range, String contentTypePrefix) {
		long rangeStart = 0;
		long rangeEnd;
		byte[] data;
		Long fileSize;
		String fileType = null;
		if (contentTypePrefix == "video") {
			fileType = "mp4";
		} else if (contentTypePrefix == "audio") {
			fileType = "mp3";
		}

		fileName = fileName + "." + fileType;
		try {
			fileSize = Optional.ofNullable(fileName)
					.map(file -> Paths.get(getFilePath(location, contentTypePrefix), file)).map(this::sizeFromFile)
					.orElse(0L);
			if (range == null) {
				return ResponseEntity.status(HttpStatus.OK).header("Content-Type", contentTypePrefix + "/" + fileType)
						.header("Content-Length", String.valueOf(fileSize))
						.body(readByteRange(location, contentTypePrefix, fileName, rangeStart, fileSize - 1));
			}
			String[] ranges = range.split("-");
			rangeStart = Long.parseLong(ranges[0].substring(6));
			if (ranges.length > 1) {
				rangeEnd = Long.parseLong(ranges[1]);
			} else {
				rangeEnd = fileSize - 1;
			}
			if (fileSize < rangeEnd) {
				rangeEnd = fileSize - 1;
			}
			data = readByteRange(location, contentTypePrefix, fileName, rangeStart, rangeEnd);
		} catch (IOException e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
		String contentLength = String.valueOf((rangeEnd - rangeStart) + 1);
		return ResponseEntity.status(HttpStatus.PARTIAL_CONTENT)
				.header("Content-Type", contentTypePrefix + "/" + fileType).header("Accept-Ranges", "bytes")
				.header("Content-Length", contentLength)
				.header("Content-Range", "bytes" + " " + rangeStart + "-" + rangeEnd + "/" + fileSize).body(data);
	}

	public byte[] readByteRange(String location, String contentTypePrefix, String filename, long start, long end)
			throws IOException {
		Path path = Paths.get(getFilePath(location, contentTypePrefix), filename);
		try (InputStream inputStream = (Files.newInputStream(path));
				ByteArrayOutputStream bufferedOutputStream = new ByteArrayOutputStream()) {
			byte[] data = new byte[BYTE_RANGE];
			int nRead;
			while ((nRead = inputStream.read(data, 0, data.length)) != -1) {
				bufferedOutputStream.write(data, 0, nRead);
			}
			bufferedOutputStream.flush();
			byte[] result = new byte[(int) (end - start) + 1];
			System.arraycopy(bufferedOutputStream.toByteArray(), (int) start, result, 0, result.length);
			return result;
		}
	}

	public String getFilePath(String location, String contentTypePrefix) {
		URI uri = null;
		if (contentTypePrefix == "video") {
			uri = Paths.get("src\\main\\resources\\static\\media\\video").toUri();
		} else if (contentTypePrefix == "audio") {
			uri = Paths.get("src\\main\\resources\\static\\media\\audio").toUri();
		}
		return new File(uri.getPath()).getAbsolutePath();
	}

	public Long sizeFromFile(Path path) {
		try {
			return Files.size(path);
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		return 0L;
	}

	@Override
	public Media getMediaById(Long id) {
		return mediarepo.getById(id);
	}

	// dashboard
	@Override
	public List<Media> findAllVideos() {
		return mediarepo.findAllVideos();
	}

	@Override
	public List<Media> findAllMusics() {
		return mediarepo.findAllMusics();
	}

	@Override
	public List<Media> findMediaByAlbumId(Long albumID) {

		return mediarepo.findMediaByAlbumId(albumID);
	}

	@Override
	public void saveUserHistory(UserHistory userhistory) {
		uhrepo.save(userhistory);
	}

}
