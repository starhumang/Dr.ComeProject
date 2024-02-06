package com.drcome.project.medical.service;

import java.util.Date;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
public class HospitalVO {
	
	private MultipartFile[] uploadFiles;
	
	public MultipartFile[] getUploadFiles() {
        return uploadFiles;
    }
	
	public void setUploadFiles(MultipartFile[] uploadFiles) {
        this.uploadFiles = uploadFiles;
    }
	
	private String hospitalId;
	private String hospitalPw;
	private String hospitalName;
	private String address;
	private String phone;
	private String hospitalImg;
	private String mainSubject;
	private String directorName;
	private String directorPhone;
	private String directorLicense;
	private String hospitalStatus;
	private String holiday;
	private String opentime;
	private String closetime;
	private Date joindate;

}
