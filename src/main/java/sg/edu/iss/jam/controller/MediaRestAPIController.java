package sg.edu.iss.jam.controller;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import reactor.core.publisher.Mono;
import sg.edu.iss.jam.service.MediaServiceImplementation;

@RestController
@RequestMapping("/api/media")
public class MediaRestAPIController {

	private static final String VIDEO_PATH = "src\\main\\resources\\static\\media\\video"; // CHANGE THIS
	private static final String AUDIO_PATH = "src\\main\\resources\\static\\media\\audio";

	@Autowired
	MediaServiceImplementation vidservice;

	@GetMapping("/video/{fileName}")
	public Mono<ResponseEntity<byte[]>> streamVideo(
			@RequestHeader(value = "Range", required = false) String httpRangeList,
			@PathVariable("fileName") String fileName) {
		return Mono.just(vidservice.getContent(VIDEO_PATH, fileName, httpRangeList, "video"));
	}

	@GetMapping("/audio/{fileName}")
	public Mono<ResponseEntity<byte[]>> streamAudio(
			@RequestHeader(value = "Range", required = false) String httpRangeList,
			@PathVariable("fileName") String fileName) {
		return Mono.just(vidservice.getContent(AUDIO_PATH, fileName, httpRangeList, "audio"));
	}

	@PostMapping("/api/fileupload")
	public void uploadFile(@RequestParam("multipartFile") MultipartFile uploadfile) {

		String UPLOAD_PATH = "C://uploads//";

		if (uploadfile.isEmpty()) {
			System.out.println("please select a file!");
		}

		try {
			byte[] bytes = uploadfile.getBytes();
			Path path = Paths.get(UPLOAD_PATH + uploadfile.getOriginalFilename());
			Files.write(path, bytes);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
