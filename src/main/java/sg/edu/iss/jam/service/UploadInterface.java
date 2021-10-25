package sg.edu.iss.jam.service;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

public interface UploadInterface {

	String store(MultipartFile thumbFile, String savepath, String filename);

	Boolean delete(String filepath) throws IOException;

}
