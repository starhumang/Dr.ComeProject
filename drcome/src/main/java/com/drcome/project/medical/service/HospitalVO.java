package com.drcome.project.medical.service;

import java.util.Date;

import lombok.Data;

@Data
public class HospitalVO {
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
