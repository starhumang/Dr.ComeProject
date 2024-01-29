package com.drcome.project.doctor.service;

import java.util.Date;

import lombok.Data;

@Data
public class PatientVO {
	
	private String reserveNo;
	private String userName;
	private String gender;
	private Date birthday;
	private String symptom;
	


}
