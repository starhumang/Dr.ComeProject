package com.drcome.project.medical.service;

import java.util.Date;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
public class QnaVO {
	private MultipartFile[] uploadFiles;

	public MultipartFile[] getUploadFiles() {
		return uploadFiles;
	}

	public void setUploadFiles(MultipartFile[] uploadFiles) {
		this.uploadFiles = uploadFiles;
	}
	
	private String title;
	private String content;
	private String userId;
	private String categoryStatus;
	private Integer ansCode;
	private String fileName;
	private String ansStatus;
	private Integer qnaNo;
	private String hospitalId;
	private String hospitalName;
	private Date wdate;
	private Date udate;
	
	List<NoticeAttachVO> files;
}
