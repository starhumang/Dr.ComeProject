package com.drcome.project.admin;

import java.util.Date;

import lombok.Data;

@Data
public class hospitalVO {
	
	String hospitalId;
	String hospitalPw;
	String hospitalName;
	String address;
	String phone;
	String hospitalImg;
	String mainSubject;
	String directorName;
	String directorPhone;
	String directorLicense;
	String hospitalStatus;
	String holiday;
	String opentime;
	String closetime;
	Date joindate;
}
