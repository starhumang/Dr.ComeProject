package com.drcome.project.common.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileUploadService {

	@Value("${file.upload.path}")
	private String uploadPath;

	@PostMapping("/uploadsAjax")
	@ResponseBody
	public List<String> uploadFiles(@RequestPart MultipartFile[] uploadFiles) {

		List<String> imageList = new ArrayList<>();

		for (MultipartFile uploadFile : uploadFiles) {
//			if (uploadFile.getContentType().startsWith("image") == false) {
//				System.err.println("this file is not image type");
//				return null;
//			}

			String originalName = uploadFile.getOriginalFilename();
			String fileName = originalName.substring(originalName.lastIndexOf("//") + 1);

//			System.out.println("fileName : " + fileName);

			 String folderPath = makeFolder();
	         String uuid = UUID.randomUUID().toString();
	         String uploadFileName = folderPath + File.separator + uuid + "_" + fileName;

	         String saveName = uploadPath + File.separator + uploadFileName;

	         Path savePath = Paths.get(saveName);
	         try {
	            uploadFile.transferTo(savePath);
	         } catch (IOException e) {
	            e.printStackTrace();
	         }
	         imageList.add(setImagePath(uploadFileName));
	         
//	         System.out.println(savePath);
	      }
		
	      return imageList;
	}

	private String makeFolder() {
		String str = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));
		// LocalDate를 문자열로 포멧
		String folderPath = str.replace("/", File.separator);
		
	      File uploadPathFolder = new File(uploadPath, folderPath);
	      if (!uploadPathFolder.exists()) {
	         uploadPathFolder.mkdirs();
	      }
	      return folderPath;
	}

	private String setImagePath(String uploadFileName) {
		return uploadFileName.replace(File.separator, "/");
	}

}
