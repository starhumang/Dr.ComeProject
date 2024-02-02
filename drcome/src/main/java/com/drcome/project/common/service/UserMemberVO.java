package com.drcome.project.common.service;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class UserMemberVO {
	
	private MultipartFile[] uploadFiles;
	
	public MultipartFile[] getUploadFiles() {
        return uploadFiles;
    }
	
	public void setUploadFiles(MultipartFile[] uploadFiles) {
        this.uploadFiles = uploadFiles;
    }
	
	private String userId;
	private String userPw;
	private String userName;
	private String phone;
	
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date birthday;
	
	private char gender;
	private String identification;
	
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date joinDate;
	
	private String grade;	
	private String userStatus;

}
