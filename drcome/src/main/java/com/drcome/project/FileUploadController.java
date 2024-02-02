package com.drcome.project;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class FileUploadController {
	
	@Autowired
	private FileUploadService fileUploadService;
	
	@PostMapping("/uploadsAjax")
    @ResponseBody
    public List<String> uploadFile(@RequestPart MultipartFile[] uploadFiles) {
        return fileUploadService.uploadFiles(uploadFiles);
    }

}
