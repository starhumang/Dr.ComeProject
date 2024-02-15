package com.drcome.project.main.service;

import java.util.Date;

import lombok.Data;

@Data
public class ClinicPayVO {
	private int clinicNo;
	private String hospitalName;
	private String DoctorName;
	private String subject;
	private String userName;
	private Date clinicDate;
	private String pay;
}
