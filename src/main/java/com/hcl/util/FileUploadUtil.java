package com.hcl.util;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;


import org.springframework.web.multipart.MultipartFile;

public class FileUploadUtil {
	public static void saveFile(String savingDir, String fileName, MultipartFile multipartFile) throws IOException {
		Path p = Paths.get(savingDir);
		if (!Files.exists(p)) {
			Files.createDirectories(p);
		}

		try (InputStream inputStream = multipartFile.getInputStream()) {
			Path filePath = p.resolve(fileName);
			Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
		} catch (IOException ioe) {
			throw new IOException("Could not save image file: " + fileName, ioe);
		}
	}
	
	public static void deleteFile(String fileDir) {
		Path p = Paths.get(fileDir);
		if(Files.exists(p)) {
			File file = new File(fileDir);
			file.delete();
		}
	}
}
