package com.drcome.project.medical;

import java.util.Date;

import lombok.Data;

@Data
public class DoctorVO {
	private Integer doctorNo;
	private String subject;
	private String doctorName;
	private String doctorLicense;
	private String holiday;
	private String hospitalId;
	private Integer doctorTimeno;
	private String day;
	private String time;
	private Date registerDate;
}
