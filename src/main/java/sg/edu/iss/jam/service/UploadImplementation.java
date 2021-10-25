package sg.edu.iss.jam.service;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class UploadImplementation implements UploadInterface {

	@Override
	public String store(MultipartFile inputfile, String savepath, String inputfilename) {

		if (!inputfile.isEmpty()) {

			MultipartFile file = inputfile;

			Path fileStorageLocation = Paths.get(savepath);
			String filename = inputfilename
					+ file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".") + 1);
			Path destinationFile = fileStorageLocation.resolve(Paths.get(filename)).toAbsolutePath();

			File pathAsFile = new File(savepath);

			if (!Files.exists(Paths.get(savepath))) {
				pathAsFile.mkdirs();
			}

			try (InputStream inputStream = file.getInputStream()) {
				Files.copy(inputStream, destinationFile, StandardCopyOption.REPLACE_EXISTING);
			} catch (IOException e) {
				e.printStackTrace();
			}

			System.out.println("File: " + file.getOriginalFilename() + "saved to " + destinationFile.toString() + "as "
					+ filename);
			return filename;

		}

		return null;
	}

	@Override
	public Boolean delete(String filepath) throws IOException {

		Path fileToDeletePath = Paths.get("src/main/resources/static" + filepath);

		try {
			Files.delete(fileToDeletePath);
		} catch (Exception e) {
			System.out.println(e);
		}

		return true;
	}

}
