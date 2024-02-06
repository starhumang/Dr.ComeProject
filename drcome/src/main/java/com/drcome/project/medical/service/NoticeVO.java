package com.drcome.project.medical.service;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
public class NoticeVO {
	private MultipartFile[] uploadFiles;

	public MultipartFile[] getUploadFiles() {
		return uploadFiles;
	}

	public void setUploadFiles(MultipartFile[] uploadFiles) {
		this.uploadFiles = uploadFiles;
	}
	
	private String title;
	private String content;
	private String fileName;
	private Integer noticeNo;
	private String hospitalId;

}
